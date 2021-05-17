package xarmant

import org.junit.jupiter.api.Assertions.assertNotNull
import org.eclipse.jgit.util.FS
import java.nio.file.Path
import org.eclipse.jgit.api.CreateBranchCommand.SetupUpstreamMode
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
class JGitTest {

   var repo: Repo? = null
   var work_dir: File? = Const.REPO_PATH.toFile()

   @BeforeAll
   fun init_command_create_empty_repo_or_reinit_existing_one() {
      // giving
      Files.delete_create_dir(Const.REPO_PATH)
      // when
      repo = Repo.ini(Const.REPO_PATH)
      // then
      assertNotNull(repo)
      repo!!.inspect()
   }

   @AfterAll
   fun finalize() {
      // then
      assertNotNull(repo)
      repo!!.close()
   }


   @Test
   fun open_command_after_close() {
      // giving
      repo!!.close()
      // when
      repo = Repo.open(Const.REPO_PATH)
      // then
      assertNotNull(repo)
      repo!!.inspect()
   }

   @Test
   fun add_command_only_one_file_by_filepattern() {
      // giving
      val filename: String = "test1.txt"
      Files.create_file(Const.REPO_PATH, Path.of("$filename"))
      Const.write_in_file("test2.txt", "linea-1")
      Const.write_in_file("test2.txt", "linea-2")
      // when
      val command: org.eclipse.jgit.api.AddCommand = repo!!.git().add()
      command.setUpdate(true)
      command.setWorkingTreeIterator()
      command.addFilepattern(filename)
      val dirC: DirCache = command.call();
      // then
      assertNotNull(dirC)
      Const.dircache_inspect(dirC)
      //val tree: DirCacheTree = dirC.getCacheTree(true)
      // recorrer recursivamente el arbol
   }

   @Test
   fun clean_command() {
      assertNotNull(repo)
      val command: org.eclipse.jgit.api.CleanCommand = repo!!.git().clean()
      command.setDryRun().setCleanDirectories().setForce().setIgnore().setPaths()
      val paths: Set<String> = command.call()
   }

   @Test
   fun commit_command() {
      assertNotNull(repo)
      val filename: String = "test.txt"
      val command: org.eclipse.jgit.api.CommitCommand = repo!!.git().commit()
//      command.setAll(true).setAllowEmpty(false).setAmend(true)
//      command.setAuthor(PersonIdent).setAuthor("", "")
//      command.setCommitter(PersonIdent).setCommitter("", "")
//      command.setSign().setSigningKey()
//      command.setCredentialsProvider()
//      command.setGpgConfig().setGpgSigner()
//      command.setHookErrorStream(System.err)
//      command.setHookOutputStream(System.out)
//      command.setInsertChangeId(true)
//      command.setNoVerify(true)
//      command.setOnly("")
//      command.setReflogComment("")
//      command.setMessage("").setMessage("Commit $filename")
      val rv: RevCommit = command.call();
   }

   // DiffCommand diff() Returns a command object to execute a diff command
   @Test
   fun diff_command() {
      assertNotNull(repo)
      val command: org.eclipse.jgit.api.DiffCommand = repo!!.git().diff()
//      command.setCached(true).setContextLines(0).setDestinationPrefix("")
//      command.setNewTree().setOldTree()
//      command.setOutputStream()
//      command.setPathFilter()
//      command.setProgressMonitor()
//      command.setShowNameAndStatusOnly(true)
//      command.setSourcePrefix("")
      val list = List<DiffEntry> = command.call()
   }

   //////////////////////////////////////////////////////////////////////
   @Test
   fun status_command() {
      assertNotNull(repo)
      val command: org.eclipse.jgit.api.StatusCommand = repo!!.git().status()
//      command.addPath("")
//      command.setIgnoreSubmodules()
//      command.setProgressMonitor()
//      command.setWorkingTreeIt()
//      var status: Status = command.call()
   }

   @Test
   fun log_command() {
      assertNotNull(repo)
      val command: org.eclipse.jgit.api.LogCommand = repo!!.git().log()
//      command.add()
//      command.addPath("")
//      command.addRange()
//      command.setMaxCount().setRevFilter().setSkip()
//      var rvList: Iterable<RevCommit> = command.call()
   }

   //////////////////////////////////////////////////////////////////////
   @Test
   fun branchCreate_command() {
      assertNotNull(repo)
      val command: org.eclipse.jgit.api.CreateBranchCommand = repo!!.git().branchCreate()
      command.setName("").setForce(true).setUpstreamMode(SetupUpstreamMode.TRACK)
//      val rv: RevCommit = RevCommit()
//      command.setStartPoint("").setStartPoint(rv)
//      SetupUpstreamMode.SET_UPSTREAM
//      SetupUpstreamMode.TRACK
//      SetupUpstreamMode.NOTRACK                           
      val result: Ref = command.call()
      Const.ref_inspect(result)
   }

   @Test
   fun branchDelete_command() {
      assertNotNull(repo)
      val repo: org.eclipse.jgit.lib.Repository = repo!!.git().getRepository()
      /*   
      var currentBranch: String = repo.getFullBranch();
      var currentRef: Ref = repo!!.findRef(currentBranch);
      var walk: RevWalk = RevWalk(repo);
      var tip: RevCommit = walk.parseCommit(repo.resolve("HEAD"));
      var base: RevCommit = walk.parseCommit(repo.resolve(currentBranch));
      if (currentBranch.startsWith("refs/heads/")) {
         var shortenedName: String = currentBranch.substring("refs/heads/".length);
         var cfg: StoredConfig = repo.getConfig();
         cfg.unsetSection("branch", shortenedName);
         cfg.save();
      }
      */
      val command: org.eclipse.jgit.api.DeleteBranchCommand = repo!!.git().branchDelete()
      command.setBranchNames("").setForce(true)
      // lista de branches eliminados
      //val result: List<String> = command.call()
   }

   @Test
   fun branchList_command() {
      assertNotNull(repo)
      val command: org.eclipse.jgit.api.ListBranchCommand = repo!!.git().branchList()
      // busqueda de branches por comentario en el commit
      command.setContains("")
      command.setListMode(org.eclipse.jgit.api.ListBranchCommand.ListMode.REMOTE).setListMode(null)
      // lista de branches eliminados
      //val result: List<Ref> = command.call()
      //Const.ref_inspect(result)
   }

   @Test
   fun branchRename_command() {
      assertNotNull(repo)
      val command: org.eclipse.jgit.api.RenameBranchCommand = repo!!.git().branchRename()
//      command.setOldName("").setNewName("")
      //val result: Ref = command.call()
      //Const.ref_inspect(result)
   }

   //////////////////////////////////////////////////////////////////////
   @Test
   fun checkout_command() {
      assertNotNull(repo)
//      val rc: RevCommit = RevCommit.parse(byte[] {})
      val command: org.eclipse.jgit.api.CheckoutCommand = repo!!.git().checkout()
//      command.setAllPaths(true).setCreateBranch(true).setForce(true)
//         .setForced(true).setForceRefUpdate(true).setName("").setOrphan(false)
//         .setProgressMonitor(NullProgressMonitor.INSTANCE).setStage(null)
//         .setStartPoint("").setStartPoint(rc)
//         .setUpstreamMode(null)
//      val result: Ref = command.call()
//      Const.ref_inspect(result)
   }

   //////////////////////////////////////////////////////////////////////
   @Test
   fun fetch_command() {
      assertNotNull(repo)
      val command: org.eclipse.jgit.api.FetchCommand = repo!!.git().fetch()
//      command.setCallback().setCheckFetchedObjects()
//      command.setCredentialsProvider()
//      command.setDryRun().setForceUpdate()
//      command.setInitialBranch()
//      command.setProgressMonitor()
//      command.setRecurseSubmodules()
//      command.setRefSpecs()
//      command.setRemote()
//      command.setRemoveDeletedRefs()
//      command.setTagOpt()
//      command.setThin()
//      command.setTimeout()
//      command.setTransportConfigCallback()
//      var fr: FetchResult = command.call()
   }

   @Test
   fun pull_command() {
      assertNotNull(repo)
      val command: org.eclipse.jgit.api.PullCommand = repo!!.git().pull()
//      command.setCredentialsProvider()
//      command.setFastForward()
//      command.setProgressMonitor()
//      command.setRebase()
//      command.setRecurseSubmodules()
//      command.setRemote()
//      command.setRemoteBranchName()
//      command.setStrategy()
//      command.setTagOpt()
//      command.setTimeout()
//      command.setTransportConfigCallback()
//      var pr: PullResult = command.call()
   }

   @Test
   fun push_command() {
      assertNotNull(repo)
      val command: org.eclipse.jgit.api.PushCommand = repo!!.git().push()
//      command.add(ref)
//      command.add("")
//      command.setAtomic(true).setDryRun(true).setForce(true)
//      command.setOutputStream().setProgressMonitor()
//      command.setPushAll().setPushOptions().setPushTags()
//      command.setReceivePack().setRefLeaseSpecs().setRefSpecs()
//      command.setRemote("")
//      command.setThin().setTimeout().setTransportConfigCallback()
//      command.setCredentialsProvider()
//      var prList: Iterable<PushResult> = command.call()
   }

   @Test
   fun reset_command() {
      assertNotNull(repo)
      val command: org.eclipse.jgit.api.ResetCommand = repo!!.git().reset()
//      command.addPath().setMode().setProgressMonitor().setRef("")
//      var ref: Ref = command.call()
   }

   @Test
   fun revert_command() {
      assertNotNull(repo)
      val command: org.eclipse.jgit.api.RevertCommand = repo!!.git().revert()
//      command.setOurCommitName("").setProgressMonitor().setStrategy(MergeStrategy)
//      var rv: RevCommit = command.call()
   }

   @Test
   fun rm_command() {
      assertNotNull(repo)
      val command: org.eclipse.jgit.api.RmCommand = repo!!.git().rm()
      command.addFilepattern("")
      command.setCached(true)
      //var dc:  DirCache = command.call()      
   }

   //////////////////////////////////////////////////////////////////////
   @Test
   fun stashApply_command() {
      assertNotNull(repo)
      val command: org.eclipse.jgit.api.StashApplyCommand = repo!!.git().stashApply()
//      command.setApplyIndex(true)
//      command.setApplyUntracked(true)
//      command.setRestoreIndex(true).setRestoreUntracked(true)
//      command.setStashRef("").setStrategy(MergeStrategy)
//      var oid: ObjectId = command.call()
   }

   @Test
   fun stashCreate_command() {
      assertNotNull(repo)
      val command: org.eclipse.jgit.api.StashCreateCommand = repo!!.git().stashCreate()
      command.setIncludeUntracked(true).setIndexMessage("").setPerson(PersonIdent)
      command.setRef("").setWorkingDirectoryMessage("")
//      var rv: RevCommit = command.call()
   }

   @Test
   fun stashDrop_command() {
      assertNotNull(repo)
      val command: org.eclipse.jgit.api.StashDropCommand = repo!!.git().stashDrop()
//      command.setAll(true).setStashRef(0)
//      val oid: ObjectId = command.call()
   }

   @Test
   fun stashList_command() {
      assertNotNull(repo)
      val command: org.eclipse.jgit.api.StashListCommand = repo!!.git().stashList()
//      command.set
//      val rvList: Collection<RevCommit> = command.call()
   }

   //////////////////////////////////////////////////////////////////////
   @Test
   fun tag_command() {
      assertNotNull(repo)
      val command: org.eclipse.jgit.api.TagCommand = repo!!.git().tag()
//      command.setAnnotated(true).setForceUpdate(true)
//      command.setName("").setMessage("")
//      command.setCredentialsProvider().setGpgConfig().setGpgSigner()
//      command.setObjectId().setSigned().setSigningKey().setTagger("")
//      val ref: Ref = command.call()
//      Const.ref_inspect(ref)
   }

   @Test
   fun tagDelete_command() {
      assertNotNull(repo)
      val command: org.eclipse.jgit.api.DeleteTagCommand = repo!!.git().tagDelete()
//      command.setTags(List<String>)
//      val paths : List<String> = command.call()
   }

   @Test
   fun tagList_command() {
      assertNotNull(repo)
      val command: org.eclipse.jgit.api.ListTagCommand = repo!!.git().tagList()
//      command.set
//      val refList : List<Ref>  = command.call()      
   }

}
