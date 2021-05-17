package xarmant

import org.junit.jupiter.api.Assertions.assertNotNull
import org.eclipse.jgit.api.Git
import org.junit.jupiter.api.TestInstance
import org.junit.jupiter.api.AfterAll
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.BeforeAll
import java.io.File

import xarmant.xgit.Repo
import xarmant.xgit.Files

// https://www.codota.com/code/java/methods/org.eclipse.jgit.api.Git/init
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class JGitFase2Test {

   var repo: Repo? = null
   var work_dir: File? = Const.REPO_PATH.toFile()


   @BeforeAll
   fun init() {
      // giving
      Files.delete_create_dir(Const.REPO_PATH)
      // when
      repo = Repo.ini_bare(Const.REPO_PATH)
      // then
      assertNotNull(repo)
      repo!!.inspect()
   }

   @AfterAll
   fun finalize() {
      assertNotNull(repo)
      repo!!.close()
   }

   // ApplyCommand apply() Returns a command object to execute a apply command
   @Test
   fun aply_command() {
      assertNotNull(repo)
      val command: org.eclipse.jgit.api.ApplyCommand = repo!!.git().apply()
//		command.setPatch()
      // command.call().getUpdatedFiles()
   }

   // BlameCommand blame() Returns a command object to execute a blame command
   @Test
   fun blamne_command() {
      assertNotNull(repo)
      val command: org.eclipse.jgit.api.BlameCommand = repo!!.git().blame()
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
      assertNotNull(repo)
      val command: org.eclipse.jgit.api.CherryPickCommand = repo!!.git().cherryPick()
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
      assertNotNull(repo)
      val command: org.eclipse.jgit.api.LsRemoteCommand = repo!!.git().lsRemote()
      // command.call()
   }

   // MergeCommand merge() Returns a command object to execute a Merge command
   @Test
   fun merge_command() {
      assertNotNull(repo)
      val command: org.eclipse.jgit.api.MergeCommand = repo!!.git().merge()
      // command.call()      
   }

   //////////////////////////////////////////////////////////////////////
   // AddNoteCommand () Returns a command to add notes to an object
   @Test
   fun notesAdd_command() {
      assertNotNull(repo)
      val command: org.eclipse.jgit.api.AddNoteCommand = repo!!.git().notesAdd()
      // command.call()      
   }

   // ListNotesCommand () Returns a command to list all notes
   @Test
   fun notesList_command() {
      assertNotNull(repo)
      val command: org.eclipse.jgit.api.ListNotesCommand = repo!!.git().notesList()
      // command.call()      
   }

   // RemoveNoteCommand notesRemove() Returns a command to remove notes on an object
   @Test
   fun notesRemove_command() {
      assertNotNull(repo)
      val command: org.eclipse.jgit.api.RemoveNoteCommand = repo!!.git().notesRemove()
      // command.call()      
   }

   // ShowNoteCommand notesShow() Returns a command to show notes on an object
   @Test
   fun notesShow_command() {
      assertNotNull(repo)
      val command: org.eclipse.jgit.api.ShowNoteCommand = repo!!.git().notesShow()
      // command.call()      
   }

   //////////////////////////////////////////////////////////////////////
   // RebaseCommand rebase() Returns a command object to execute a Rebase command
   @Test
   fun rebase_command() {
      assertNotNull(repo)
      val command: org.eclipse.jgit.api.RebaseCommand = repo!!.git().rebase()
      // command.call()      
   }

   // ReflogCommand reflog() Returns a command object to execute a reflog command
   @Test
   fun reflog_command() {
      assertNotNull(repo)
      val command: org.eclipse.jgit.api.ReflogCommand = repo!!.git().reflog()
      // command.call()      
   }

   //////////////////////////////////////////////////////////////////////
   // SubmoduleAddCommand () Returns a command object to execute a submodule add command
   @Test
   fun submoduleAdd_command() {
      assertNotNull(repo)
      val command: org.eclipse.jgit.api.SubmoduleAddCommand = repo!!.git().submoduleAdd()
      // command.call()
   }


   // SubmoduleInitCommand ( Returns a command object to execute a submodule init command
   @Test
   fun submoduleInit_command() {
      assertNotNull(repo)
      val command: org.eclipse.jgit.api.SubmoduleInitCommand = repo!!.git().submoduleInit()
      // command.call()
   }

   // SubmoduleStatusCommand submoduleStatus() Returns a command object to execute a submodule status command
   @Test
   fun submoduleStatus_command() {
      assertNotNull(repo)
      val command: org.eclipse.jgit.api.SubmoduleStatusCommand = repo!!.git().submoduleStatus()
      // command.call()
   }

   // SubmoduleSyncCommand submoduleSync() Returns a command object to execute a submodule sync command
   @Test
   fun submoduleSync_command() {
      assertNotNull(repo)
      val command: org.eclipse.jgit.api.SubmoduleSyncCommand = repo!!.git().submoduleSync()
      // command.call()
   }

   // SubmoduleUpdateCommand submoduleUpdate() Returns a command object to execute a submodule update command
   @Test
   fun submoduleUpdate_command() {
      assertNotNull(repo)
      val command: org.eclipse.jgit.api.SubmoduleUpdateCommand = repo!!.git().submoduleUpdate()
      // command.call()
   }

}
