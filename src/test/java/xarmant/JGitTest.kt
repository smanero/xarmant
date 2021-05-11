package xarmant

import org.eclipse.jgit.util.FS
import org.eclipse.jgit.api.Git

import java.io.File
import java.nio.file.Files
import java.nio.file.Path
import java.lang.NoSuchMethodException
import org.junit.jupiter.api.Assertions.assertNotNull
import org.junit.jupiter.api.Assertions.fail
import org.junit.jupiter.api.AfterAll
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import org.eclipse.jgit.dircache.DirCache
import org.eclipse.jgit.dircache.DirCacheTree
import org.eclipse.jgit.revwalk.RevCommit
import org.eclipse.jgit.api.CreateBranchCommand.SetupUpstreamMode
import org.eclipse.jgit.lib.Ref
import org.eclipse.jgit.revwalk.RevWalk
import org.eclipse.jgit.lib.StoredConfig
import org.eclipse.jgit.blame.BlameResult
import org.eclipse.jgit.lib.NullProgressMonitor
import org.eclipse.jgit.lib.ObjectId
import org.eclipse.jgit.merge.MergeStrategy


// https://www.codota.com/code/java/methods/org.eclipse.jgit.api.Git/init
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class JGitTest {

   val path: String = "/home/nelko/prjs/proyecto1"

   var jgit: Git? = null


   @BeforeAll
   fun init() {
      Files.deleteIfExists(Path.of("/tmp/test"))
      Files.createDirectory(Path.of("/tmp/test"))
      jgit = Git.init().setDirectory(File("/tmp/test")).call()
      /*Files.walk(Path.of("/tmp/test"))
  .sorted(Comparator.reverseOrder())
  .map(Path::toFile)
  .forEach(File::delete)
    Files.deleteIfExists(Path.of("/tmp/test"))
    val jgit = Git.init().setDirectory(File("/tmp/test")).call()
    newCommitNewFile(jgit, "A")
    newCommitNewFile(jgit, "B")
    newCommitNewFile(jgit, "C")
    jgit.checkout().setCreateBranch(true).setName("brancha").call();
    newCommitNewFile(jgit, "G")
    newCommitNewFile(jgit, "H")
    newCommitNewFile(jgit, "I")
    newCommitNewFile(jgit, "J")
    newCommitNewFile(jgit, "K")
    newCommitNewFile(jgit, "L")
    newCommitNewFile(jgit, "M")
    newCommitNewFile(jgit, "N")
    newCommitNewFile(jgit, "O")
    newCommitNewFile(jgit, "P")
    newCommitNewFile(jgit, "Q")
    jgit.checkout().setCreateBranch(true).setName("branchb").call();
    newCommitNewFile(jgit, "R")
    newCommitNewFile(jgit, "S")
    newCommitNewFile(jgit, "T")
    newCommitNewFile(jgit, "U")
    newCommitNewFile(jgit, "V")
    jgit.checkout().setCreateBranch(false).setName("master").call();
    newCommitNewFile(jgit, "E")
    newCommitNewFile(jgit, "F")
    jgit.checkout().setCreateBranch(true).setName("branchc").call();
    newCommitNewFile(jgit, "W")
    newCommitNewFile(jgit, "X")
    newCommitNewFile(jgit, "Y")
    newCommitNewFile(jgit, "Z")
       */
   }

   @AfterAll
   fun finalize() {
      assertNotNull(jgit)
      jgit!!.close()
   }

   // InitCommand init() Create an empty Git repository or reinitialize an existing one
   @Test
   fun init_command() {
      Files.deleteIfExists(Path.of("/tmp/test"))
      Files.createDirectory(Path.of("/tmp/test"))
      val bare: Boolean = true
      val directory: File = File("/tmp/test")
      val gitDir: File = File("/tmp/test")

      //jgit = Git.init().setDirectory(File("/tmp/test")).call()
      val command: org.eclipse.jgit.api.InitCommand = Git.init()
      command.setFs(FS.DETECTED)
      command.setBare(bare)
      command.setDirectory(directory)
      command.setGitDir(gitDir)
      jgit = command.call()
      repo_inspect(jgit!!.getRepository())
   }

   fun repo_inspect(repo: org.eclipse.jgit.lib.Repository) {
      //Gson().toJson(repo, org.eclipse.jgit.lib.Repository::class.java)
      repo.getBranch()
//      repo.getAllRefs()
//      repo.getAllRefsByPeeledObjectId()
//      repo.getConfig()
//      repo.getDirectory()
//      repo.getFS()
//      repo.getFullBranch()
//      repo.getGitwebDescription()
//      repo.getIdentifier()
//      repo.getIndexFile()
//      repo.getListenerList()
//      repo.getObjectDatabase()
//      repo.getRefDatabase()
//      repo.getReflogReader()
//      repo.getRemoteName()
//      repo.getRemoteNames()
//      repo.getRepositoryState()
//      repo.getTags()
//      repo.getWorkTree()
   }

   // OpenCommand open() Open repository
   @Test
   fun open_command() {
      // dir - the repository to open. May be either the GIT_DIR, or the working tree directory that contains .git.
      // fs - filesystem abstraction to use when accessing the repository. 
      jgit = Git.open(File(path), FS.DETECTED)
      repo_inspect(jgit!!.getRepository())
   }

   // AddCommand add() Returns a command object to execute a Add command
   @Test
   fun add_command() {
      assertNotNull(jgit)
      val filename: String = "test.txt"
      Files.createFile(Path.of("/tmp/test/$filename"))
      val dirC: DirCache = jgit!!.add().addFilepattern(filename).call();
      val tree: DirCacheTree = dirC.getCacheTree(true)
      // recorrer recursivamente el arbol
      tree_inspect(dirC.getCacheTree(true))
   }

   fun tree_inspect(tree: org.eclipse.jgit.dircache.DirCacheTree) {
      tree.getNameString()
//      tree.getObjectId()
//      tree.getPathString()
//      tree.getChildCount()
//      tree.getChild(0)
   }

   // ApplyCommand apply() Returns a command object to execute a apply command
   @Test
   fun aply_command() {
      assertNotNull(jgit)
      val command: org.eclipse.jgit.api.ApplyCommand = jgit!!.apply()
//		command.setPatch()
      command.call().getUpdatedFiles()
   }

   // BlameCommand blame() Returns a command object to execute a blame command
   @Test
   fun blamne_command() {
      assertNotNull(jgit)
      val command: org.eclipse.jgit.api.BlameCommand = jgit!!.blame()
//      command.setDiffAlgorithm(DiffAlgorithm)
//      command.setFilePath()
//      command.setFollowFileRenames()
//      command.setStartCommit()
//      command.setTextComparator()
      val result: BlameResult = command.call()
//      result.getResultContents()
//      result.getResultPath()
//      result.getSourceAuthor(0)
//      result.getSourceCommit(0)
//      result.getSourceCommitter(0)
//      result.getSourceLine(0)
//      result.getSourcePath(0)
//      result.computeAll()
   }

   //////////////////////////////////////////////////////////////////////
   // CreateBranchCommand branchCreate() Returns a command object used to create branches
   @Test
   fun branchCreate_command() {
      assertNotNull(jgit)
      val command: org.eclipse.jgit.api.CreateBranchCommand = jgit!!.branchCreate()
      command.setName("").setForce(true).setUpstreamMode(SetupUpstreamMode.TRACK)
//      val rv: RevCommit = RevCommit()
//      command.setStartPoint("").setStartPoint(rv)
//      SetupUpstreamMode.SET_UPSTREAM
//      SetupUpstreamMode.TRACK
//      SetupUpstreamMode.NOTRACK                           
      val result: Ref = command.call()
      ref_inspect(result)
   }

   fun ref_inspect(ref: org.eclipse.jgit.lib.Ref) {
      ref.getName()
//      ref.getLeaf()
//      ref.getObjectId()
//      ref.getPeeledObjectId()
//      ref.getStorage()
//      ref.getTarget()
//      ref.getUpdateIndex()
   }

   fun ref_inspect(refList: List<org.eclipse.jgit.lib.Ref>) {
   }

   // DeleteBranchCommand branchDelete() Returns a command object used to delete branches
   @Test
   fun branchDelete_command() {
      assertNotNull(jgit)
      val repo: org.eclipse.jgit.lib.Repository = jgit!!.getRepository()
      var currentBranch: String = repo.getFullBranch();
      var currentRef: Ref = repo.findRef(currentBranch);
      var walk: RevWalk = RevWalk(repo);
      var tip: RevCommit = walk.parseCommit(repo.resolve("HEAD"));
      var base: RevCommit = walk.parseCommit(repo.resolve(currentBranch));
      if (currentBranch.startsWith("refs/heads/")) {
         var shortenedName: String = currentBranch.substring("refs/heads/".length);
         var cfg: StoredConfig = repo.getConfig();
         cfg.unsetSection("branch", shortenedName);
         cfg.save();
      }

      val command: org.eclipse.jgit.api.DeleteBranchCommand = jgit!!.branchDelete()
      command.setBranchNames("").setForce(true)
      // lista de branches eliminados
      val result: List<String> = command.call()
   }

   // ListBranchCommand branchList() Returns a command object used to list branches
   @Test
   fun branchList_command() {
      assertNotNull(jgit)
      val command: org.eclipse.jgit.api.ListBranchCommand = jgit!!.branchList()
      // busqueda de branches por comentario en el commit
      command.setContains("")
      command.setListMode(org.eclipse.jgit.api.ListBranchCommand.ListMode.REMOTE).setListMode(null)
      // lista de branches eliminados
      val result: List<Ref> = command.call()
      ref_inspect(result)
   }

   // RenameBranchCommand branchRename() Returns a command object used to rename branches
   @Test
   fun branchRename_command() {
      assertNotNull(jgit)
      val command: org.eclipse.jgit.api.RenameBranchCommand = jgit!!.branchRename()
//      command.setOldName("").setNewName("")
      val result: Ref = command.call()
      ref_inspect(result)
   }

   //////////////////////////////////////////////////////////////////////
   // CheckoutCommand checkout() Returns a command object to execute a checkout command
   @Test
   fun checkout_command() {
      assertNotNull(jgit)
      val command: org.eclipse.jgit.api.CheckoutCommand = jgit!!.checkout()
      //      val rc: RevCommit = RevCommit.parse(byte[] {})
//      command.setAllPaths(true).setCreateBranch(true).setForce(true)
//         .setForced(true).setForceRefUpdate(true).setName("").setOrphan(false)
//         .setProgressMonitor(NullProgressMonitor.INSTANCE).setStage(null)
//         .setStartPoint("").setStartPoint(rc)
//         .setUpstreamMode(null)
      val result: Ref = command.call()
      ref_inspect(result)
   }

   // CherryPickCommand cherryPick() Returns a command object to execute a cherry-pick command
   @Test
   fun cherryPick_command() {
      assertNotNull(jgit)
      val command: org.eclipse.jgit.api.CherryPickCommand = jgit!!.cherryPick()
//      command.setMainlineParentNumber(0).setNoCommit(true).setOurCommitName("")
//      	.setProgressMonitor(NullProgressMonitor.INSTANCE).setReflogPrefix("")
//      	.setStrategy(MergeStrategy.RECURSIVE)
      val result: org.eclipse.jgit.api.CherryPickResult = command.call()
//      result.getCherryPickedRefs()
//      result.getFailingPaths()
//      result.getNewHead()
//      result.getStatus()
   }


   // CleanCommand clean() Returns a command object to execute a clean command
   @Test
   fun clean_command() {
      assertNotNull(jgit)
      val command: org.eclipse.jgit.api.CleanCommand = jgit!!.clean()
      command.call()
   }

   // CommitCommand commit() Returns a command object to execute a Commit command
   @Test
   fun commit_command() {
      assertNotNull(jgit)
      val filename: String = "test.txt"
      val command: org.eclipse.jgit.api.CommitCommand = jgit!!.commit()
      command.setMessage("Commit $filename")
      command.call();
   }

   // DiffCommand diff() Returns a command object to execute a diff command
   @Test
   fun diff_command() {
      assertNotNull(jgit)
      val command: org.eclipse.jgit.api.DiffCommand = jgit!!.diff()
      command.call()
   }

   // FetchCommand fetch() Returns a command object to execute a Fetch command
   @Test
   fun fetch_command() {
      assertNotNull(jgit)
      val command: org.eclipse.jgit.api.FetchCommand = jgit!!.fetch()
      command.call()
   }

   //////////////////////////////////////////////////////////////////////
   // StatusCommand status() Returns a command object to execute a status command
   @Test
   fun status_command() {
      assertNotNull(jgit)
      val command: org.eclipse.jgit.api.StatusCommand = jgit!!.status()
      command.call()
   }

   fun repo_inspect() {
      assertNotNull(jgit)
      val repo: org.eclipse.jgit.lib.Repository = jgit!!.getRepository()
   }

   // LogCommand log() Returns a command object to execute a Log command
   @Test
   fun logcommand() {
      assertNotNull(jgit)
      val command: org.eclipse.jgit.api.LogCommand = jgit!!.log()
      command.call()
   }

   // LsRemoteCommand lsRemote() Returns a command object to execute a ls-remote command
   @Test
   fun lsRemote_command() {
      assertNotNull(jgit)
      val command: org.eclipse.jgit.api.LsRemoteCommand = jgit!!.lsRemote()
      command.call()

   }

   // MergeCommand merge() Returns a command object to execute a Merge command
   @Test
   fun merge_command() {
      assertNotNull(jgit)
      val command: org.eclipse.jgit.api.MergeCommand = jgit!!.merge()
      command.call()      
   }

   //////////////////////////////////////////////////////////////////////
   // AddNoteCommand () Returns a command to add notes to an object
   @Test
   fun notesAdd_command() {
      assertNotNull(jgit)
      val command: org.eclipse.jgit.api.AddNoteCommand = jgit!!.notesAdd()
      command.call()      
   }

   // ListNotesCommand () Returns a command to list all notes
   @Test
   fun notesList_command() {
      assertNotNull(jgit)
      val command: org.eclipse.jgit.api.ListNotesCommand = jgit!!.notesList()
      command.call()      
   }

   // RemoveNoteCommand notesRemove() Returns a command to remove notes on an object
   @Test
   fun notesRemove_command() {
      assertNotNull(jgit)
      val command: org.eclipse.jgit.api.RemoveNoteCommand = jgit!!.notesRemove()
      command.call()      
   }

   // ShowNoteCommand notesShow() Returns a command to show notes on an object
   @Test
   fun notesShow_command() {
      assertNotNull(jgit)
      val command: org.eclipse.jgit.api.ShowNoteCommand = jgit!!.notesShow()
      command.call()      
   }

   //////////////////////////////////////////////////////////////////////
   // PullCommand pull() Returns a command object to execute a Pull command
   @Test
   fun pull_command() {
      assertNotNull(jgit)
      val command: org.eclipse.jgit.api.PullCommand = jgit!!.pull()
      command.call()      
   }

   // PushCommand push() Returns a command object to execute a Push command
   @Test
   fun push_command() {
      assertNotNull(jgit)
      val command: org.eclipse.jgit.api.PushCommand = jgit!!.push()
      command.call()      
   }

   // RebaseCommand rebase() Returns a command object to execute a Rebase command
   @Test
   fun rebase_command() {
      assertNotNull(jgit)
      val command: org.eclipse.jgit.api.RebaseCommand = jgit!!.rebase()
      command.call()      
   }

   // ReflogCommand reflog() Returns a command object to execute a reflog command
   @Test
   fun reflog_command() {
      assertNotNull(jgit)
      val command: org.eclipse.jgit.api.ReflogCommand = jgit!!.reflog()
      command.call()      
   }

   // ResetCommand reset() Returns a command object to execute a reset command
   @Test
   fun reset_command() {
      assertNotNull(jgit)
      val command: org.eclipse.jgit.api.ResetCommand = jgit!!.reset()
      command.call()      
   }

   // RevertCommand revert() Returns a command object to execute a revert command
   @Test
   fun revert_command() {
      assertNotNull(jgit)
      val command: org.eclipse.jgit.api.RevertCommand = jgit!!.revert()
      command.call()      
   }

   // RmCommand rm() Returns a command object to execute a rm command
   @Test
   fun rm_command() {
      assertNotNull(jgit)
      val command: org.eclipse.jgit.api.RmCommand = jgit!!.rm()
      command.call()      
   }

   //////////////////////////////////////////////////////////////////////
   // StashApplyCommand () Returns a command object used to apply a stashed commit
   @Test
   fun stashApply_command() {
      assertNotNull(jgit)
      val command: org.eclipse.jgit.api.StashApplyCommand = jgit!!.stashApply()
      command.call()      
   }

   // StashCreateCommand stashCreate() Returns a command object used to create a stashed commit
   @Test
   fun stashCreate_command() {
      assertNotNull(jgit)
      val command: org.eclipse.jgit.api.StashCreateCommand = jgit!!.stashCreate()
      command.call()      
   }

   // StashDropCommand () Returns a command object used to drop a stashed commit
   @Test
   fun stashDrop_command() {
      assertNotNull(jgit)
      val command: org.eclipse.jgit.api.StashDropCommand = jgit!!.stashDrop()
      command.call()      
   }

   // StashListCommand () Returns a command object used to list stashed commits
   @Test
   fun stashList_command() {
      assertNotNull(jgit)
      val command: org.eclipse.jgit.api.StashListCommand = jgit!!.stashList()
      command.call()      
   }

   //////////////////////////////////////////////////////////////////////
   // SubmoduleAddCommand () Returns a command object to execute a submodule add command
   @Test
   fun submoduleAdd_command() {
      assertNotNull(jgit)
      val command: org.eclipse.jgit.api.SubmoduleAddCommand = jgit!!.submoduleAdd()
      command.call()      
   }


   // SubmoduleInitCommand ( Returns a command object to execute a submodule init command
   @Test
   fun submoduleInit_command() {
      assertNotNull(jgit)
      val command: org.eclipse.jgit.api.SubmoduleInitCommand = jgit!!.submoduleInit()
      command.call()      
   }

   // SubmoduleStatusCommand submoduleStatus() Returns a command object to execute a submodule status command
   @Test
   fun submoduleStatus_command() {
      assertNotNull(jgit)
      val command: org.eclipse.jgit.api.SubmoduleStatusCommand = jgit!!.submoduleStatus()
      command.call()      
   }

   // SubmoduleSyncCommand submoduleSync() Returns a command object to execute a submodule sync command
   @Test
   fun submoduleSync_command() {
      assertNotNull(jgit)
      val command: org.eclipse.jgit.api.SubmoduleSyncCommand = jgit!!.submoduleSync()
      command.call()      
   }

   // SubmoduleUpdateCommand submoduleUpdate() Returns a command object to execute a submodule update command
   @Test
   fun submoduleUpdate_command() {
      assertNotNull(jgit)
      val command: org.eclipse.jgit.api.SubmoduleUpdateCommand = jgit!!.submoduleUpdate()
      command.call()      
   }

   //////////////////////////////////////////////////////////////////////
   // TagCommand tag() Returns a command object to execute a Tag command
   @Test
   fun tag_command() {
      assertNotNull(jgit)
      val command: org.eclipse.jgit.api.TagCommand = jgit!!.tag()
      command.call()      
   }

   // DeleteTagCommand tagDelete() Returns a command object used to delete tags
   @Test
   fun tagDelete_command() {
      assertNotNull(jgit)
      val command: org.eclipse.jgit.api.DeleteTagCommand = jgit!!.tagDelete()
      command.call()      
   }

   // ListTagCommand tagList() Returns a command object used to list tags
   @Test
   fun tagList_command() {
      assertNotNull(jgit)
      val command: org.eclipse.jgit.api.ListTagCommand = jgit!!.tagList()
      command.call()      
   }

}
