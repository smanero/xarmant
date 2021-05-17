package xarmant.xgit

import java.nio.file.Path
import org.eclipse.jgit.util.FS
import org.eclipse.jgit.api.Git
import com.google.gson.Gson

class Repo {
   var xgit: Git? = null
   var owner: PersonIdent? = null

   constructor(jgit: Git?) {
      this.xgit = jgit
      this.owner = PersonIdent(this.xgit!!.getRepository())
   }

   fun git(): Git {
      return this.xgit!!
   }

   fun owner(): PersonIdent {
      return this.owner!!
   }

   companion object {
      fun ini(dir: Path?): Repo {
         val command: org.eclipse.jgit.api.InitCommand = Git.init()
         command.setFs(FS.DETECTED)
         // bare = true --> directory == gitDir
         // bare = false --> directory <> gitDir
         command.setBare(false)
         command.setDirectory(dir!!.toFile())
         //command.setGitDir(dir!!.toFile())
         var jgit: Git? = command.call()
         return Repo(jgit)
      }

      fun open(dir: Path?): Repo {
         // dir - the repository to open. May be either the GIT_DIR, or the working tree directory that contains .git.
         // fs - filesystem abstraction to use when accessing the repository. 
         var jgit: Git? = Git.open(dir!!.toFile(), FS.DETECTED)
         return Repo(jgit)
      }
   }

   fun inspect() {
      var repo = this.xgit!!.getRepository()
      var repoJson = Gson().toJson(repo!!, org.eclipse.jgit.lib.Repository::class.java)
      System.out.println(repoJson)
//      repo.getBranch()
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

   fun close() {
      this.xgit!!.close()
   }

}