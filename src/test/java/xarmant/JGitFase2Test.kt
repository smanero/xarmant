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
class JGitFase2Test {

   val path: String = "/home/nelko/prjs/proyecto1"

   var jgit: Git? = null


   @BeforeAll
   fun init() {
      Files.walk(Path.of("/tmp/test"))
         .sorted(Comparator.reverseOrder())
         .map(Path::toFile)
         .forEach(File::delete)
      Files.deleteIfExists(Path.of("/tmp/test"))
      Files.createDirectory(Path.of("/tmp/test"))
      jgit = Git.init().setDirectory(File("/tmp/test")).call()
   }

   @AfterAll
   fun finalize() {
      assertNotNull(jgit)
      jgit!!.close()
   }

   // ApplyCommand apply() Returns a command object to execute a apply command
   @Test
   fun aply_command() {
      assertNotNull(jgit)
      val command: org.eclipse.jgit.api.ApplyCommand = jgit!!.apply()
//		command.setPatch()
      // command.call().getUpdatedFiles()
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
      // val result: BlameResult = command.call()
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
   // CherryPickCommand cherryPick() Returns a command object to execute a cherry-pick command
   @Test
   fun cherryPick_command() {
      assertNotNull(jgit)
      val command: org.eclipse.jgit.api.CherryPickCommand = jgit!!.cherryPick()
//      command.setMainlineParentNumber(0).setNoCommit(true).setOurCommitName("")
//      	.setProgressMonitor(NullProgressMonitor.INSTANCE).setReflogPrefix("")
//      	.setStrategy(MergeStrategy.RECURSIVE)
      // val result: org.eclipse.jgit.api.CherryPickResult = command.call()
//      result.getCherryPickedRefs()
//      result.getFailingPaths()
//      result.getNewHead()
//      result.getStatus()
   }

   //////////////////////////////////////////////////////////////////////
   // LsRemoteCommand lsRemote() Returns a command object to execute a ls-remote command
   @Test
   fun lsRemote_command() {
      assertNotNull(jgit)
      val command: org.eclipse.jgit.api.LsRemoteCommand = jgit!!.lsRemote()
      // command.call()
   }

   // MergeCommand merge() Returns a command object to execute a Merge command
   @Test
   fun merge_command() {
      assertNotNull(jgit)
      val command: org.eclipse.jgit.api.MergeCommand = jgit!!.merge()
      // command.call()      
   }

   //////////////////////////////////////////////////////////////////////
   // AddNoteCommand () Returns a command to add notes to an object
   @Test
   fun notesAdd_command() {
      assertNotNull(jgit)
      val command: org.eclipse.jgit.api.AddNoteCommand = jgit!!.notesAdd()
      // command.call()      
   }

   // ListNotesCommand () Returns a command to list all notes
   @Test
   fun notesList_command() {
      assertNotNull(jgit)
      val command: org.eclipse.jgit.api.ListNotesCommand = jgit!!.notesList()
      // command.call()      
   }

   // RemoveNoteCommand notesRemove() Returns a command to remove notes on an object
   @Test
   fun notesRemove_command() {
      assertNotNull(jgit)
      val command: org.eclipse.jgit.api.RemoveNoteCommand = jgit!!.notesRemove()
      // command.call()      
   }

   // ShowNoteCommand notesShow() Returns a command to show notes on an object
   @Test
   fun notesShow_command() {
      assertNotNull(jgit)
      val command: org.eclipse.jgit.api.ShowNoteCommand = jgit!!.notesShow()
      // command.call()      
   }

   //////////////////////////////////////////////////////////////////////
   // RebaseCommand rebase() Returns a command object to execute a Rebase command
   @Test
   fun rebase_command() {
      assertNotNull(jgit)
      val command: org.eclipse.jgit.api.RebaseCommand = jgit!!.rebase()
      // command.call()      
   }

   // ReflogCommand reflog() Returns a command object to execute a reflog command
   @Test
   fun reflog_command() {
      assertNotNull(jgit)
      val command: org.eclipse.jgit.api.ReflogCommand = jgit!!.reflog()
      // command.call()      
   }

   //////////////////////////////////////////////////////////////////////
   // SubmoduleAddCommand () Returns a command object to execute a submodule add command
   @Test
   fun submoduleAdd_command() {
      assertNotNull(jgit)
      val command: org.eclipse.jgit.api.SubmoduleAddCommand = jgit!!.submoduleAdd()
      // command.call()
   }


   // SubmoduleInitCommand ( Returns a command object to execute a submodule init command
   @Test
   fun submoduleInit_command() {
      assertNotNull(jgit)
      val command: org.eclipse.jgit.api.SubmoduleInitCommand = jgit!!.submoduleInit()
      // command.call()
   }

   // SubmoduleStatusCommand submoduleStatus() Returns a command object to execute a submodule status command
   @Test
   fun submoduleStatus_command() {
      assertNotNull(jgit)
      val command: org.eclipse.jgit.api.SubmoduleStatusCommand = jgit!!.submoduleStatus()
      // command.call()
   }

   // SubmoduleSyncCommand submoduleSync() Returns a command object to execute a submodule sync command
   @Test
   fun submoduleSync_command() {
      assertNotNull(jgit)
      val command: org.eclipse.jgit.api.SubmoduleSyncCommand = jgit!!.submoduleSync()
      // command.call()
   }

   // SubmoduleUpdateCommand submoduleUpdate() Returns a command object to execute a submodule update command
   @Test
   fun submoduleUpdate_command() {
      assertNotNull(jgit)
      val command: org.eclipse.jgit.api.SubmoduleUpdateCommand = jgit!!.submoduleUpdate()
      // command.call()
   }

}
