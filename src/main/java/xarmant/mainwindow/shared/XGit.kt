package xarmant.mainwindow.shared

import org.eclipse.jgit.api.Git
import org.eclipse.jgit.api.Status
import org.eclipse.jgit.diff.DiffEntry
import org.eclipse.jgit.lib.Ref
import org.eclipse.jgit.revplot.PlotWalk
import org.eclipse.jgit.revwalk.RevCommit
import org.eclipse.jgit.transport.RemoteConfig
import xarmant.mainwindow.infraestructure.XarmantProgressMonitor
import xarmant.mainwindow.infraestructure.jgit.JavaFxCommitList
import org.eclipse.jgit.revwalk.RevWalk
import org.eclipse.jgit.treewalk.CanonicalTreeParser
import org.eclipse.jgit.treewalk.EmptyTreeIterator
import org.eclipse.jgit.lib.ObjectLoader

import org.eclipse.jgit.treewalk.TreeWalk

import java.io.IOException
import java.nio.charset.StandardCharsets

import org.eclipse.jgit.diff.DiffFormatter
import org.eclipse.jgit.diff.EditList
import org.eclipse.jgit.dircache.DirCacheIterator
import org.eclipse.jgit.treewalk.FileTreeIterator
import xarmant.mainwindow.model.*


// Clase para wrapear JGit
class XGit(val config: GitContext, val monitor: XarmantProgressMonitor) {
    lateinit var git: Git
    lateinit var branches: List<Ref>
    lateinit var tags: List<Ref>
    lateinit var origins: List<RemoteConfig>
    lateinit var stashes: MutableCollection<RevCommit>

    fun updateData() {
        branches = git.branchList().call()
        tags = git.tagList().call()
        origins = git.remoteList().call()
        stashes = git.stashList().call()
    }

    fun status(): Status? {
        return git.status().call()
    }

    fun clone(): XGit {
        git = Git.cloneRepository()
            .setURI(config.url)
            .setDirectory(config.directory)
            .setProgressMonitor(this.monitor)
            .call()
        updateData()
        return this
    }

    fun open() : XGit{
        git = Git.open(config.directory)
        updateData()
        return this
    }

    fun push() {
        git.push()
            .setProgressMonitor(monitor)
            .call()
    }

    fun pull() {
        git.pull()
            .setProgressMonitor(monitor)
            .call()
    }

    fun getGraph(): MutableList<Commit> {
        val walk = PlotWalk(git.repository)
        val allRefs: Collection<Ref> = git.repository.refDatabase.getRefs()
        for (ref in allRefs) {
            walk.markStart(walk.parseCommit(ref.objectId))
        }
        val list = JavaFxCommitList()
        list.source(walk)
        list.fillTo(Int.MAX_VALUE)
        val history = mutableListOf<Commit>()
        list.forEach { history.add(Commit(it.fullMessage, it.name, it.authorIdent.name,
            "Not Cupported", it.commitTime, mutableSetOf(), it))}
        var status = status()
        history.add(0, Commit("Uncommited",
                "",
                "",
                "",
                0,
                status!!.changed,
                history[0].commit,
                history[0].plotCommit,
                CommitType.UNCOMMITED))
        return history
    }

    fun getChangesBetween(oldCommit: Commit, newCommit: Commit): List<FileChanges> {
        val res = changesBetweenCommits(oldCommit.plotCommit!!, newCommit.plotCommit!!)
        return res!!.map { FileChanges(it.oldPath, it.newPath, it.changeType.name, oldCommit, newCommit, Entry(it)) }
    }

    fun getChangesInCommit(commit: Commit): List<FileChanges> {
        val parent: RevCommit? = commit.plotCommit?.parents?.getOrElse(0) { commit.plotCommit!! }
        val res = if (parent == commit.plotCommit ){
            changesInFirstCommit(commit)
        } else {
            changesBetweenCommits(commit.plotCommit!!.parents[0], commit.plotCommit)
        }
        return res!!.map { FileChanges(it.oldPath, it.newPath, it.changeType.name,
                Commit(parent!!.fullMessage, parent.name, parent.authorIdent.name,
                        "Not Cupported", parent.commitTime, mutableSetOf(), parent),
                commit, Entry(it)) }
    }

    private fun changesInFirstCommit(commit: Commit): List<DiffEntry>? {
        return git.diff()
            .setOldTree(EmptyTreeIterator())
            .setNewTree(getCanonicalTree(commit.plotCommit!!))
            .call()
    }

    private fun changesInWorkingCopy(oldCommit: RevCommit) : List<DiffEntry>? {
        return git.diff()
                .setNewTree(FileTreeIterator(git.repository))
                .setOldTree(getCanonicalTree(oldCommit))
                .call()
    }

    private fun changesInIndex(oldCommit: RevCommit) : List<DiffEntry>? {
        return git.diff()
                .setNewTree(DirCacheIterator(git.repository.readDirCache()))
                .setOldTree(getCanonicalTree(oldCommit))
                .call()
    }

    private fun changesBetweenCommits(oldCommit: RevCommit, newCommit: RevCommit): List<DiffEntry>? {
        return git.diff()
            .setNewTree(getCanonicalTree(newCommit))
            .setOldTree(getCanonicalTree(oldCommit))
            .call()
    }

    private fun getCanonicalTree(fromCommit: RevCommit): CanonicalTreeParser {
        RevWalk(git.repository).use { walk ->
            val commit = walk.parseCommit(fromCommit)
            val tree = commit.tree.id
            git.repository.newObjectReader()
                .use { reader -> return CanonicalTreeParser(null, reader, tree) }
        }
    }

    fun buildDiff(selectedItem: FileChanges): ChangedFile {
        val old = getFileContentAtCommit(selectedItem.oldCommit.plotCommit!!, selectedItem.oldFilename)
        val new = getFileContentAtCommit(selectedItem.newCommit.plotCommit!!, selectedItem.filename)
        val editList = obtainEditList(selectedItem)
        return ChangedFile(old, new, editList)
    }

    private fun obtainEditList(selectedItem: FileChanges): EditList {
        DiffFormatter(null).use { formatter ->
            formatter.setRepository(git.repository)
            val fileHeader = formatter.toFileHeader(selectedItem.entry.entry)
            return fileHeader.toEditList()
        }
    }

    @Throws(IOException::class)
    private fun getFileContentAtCommit(commit: RevCommit, path: String): List<String> {
        TreeWalk.forPath(git.repository, path, commit.tree).use { treeWalk ->
            if (treeWalk == null) {
                return emptyList()
            }
            val blobId = treeWalk.getObjectId(0)
            git.repository.newObjectReader().use { objectReader ->
                val objectLoader: ObjectLoader = objectReader.open(blobId)
                val bytes = objectLoader.bytes
                return String(bytes, StandardCharsets.UTF_8).lines()
            }
        }
    }
}
