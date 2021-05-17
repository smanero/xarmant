package xarmant

import java.nio.file.Path
import java.nio.file.Paths
import java.io.BufferedWriter
import java.nio.charset.Charset
import java.net.URL
import java.io.IOException
import java.io.File
import com.google.gson.Gson

object Const {
   val REPO_URL: URL = URL("https://github.com/smanero/web.git")
   val REPO_PATH: Path = Path.of("/tmp/test/xarmant")

   fun write_in_file(file: String, text: String) {
      val path: Path = Paths.get(REPO_PATH.toString(), file)
      try {
         val writer: BufferedWriter = java.nio.file.Files.newBufferedWriter(path, Charset.forName("UTF-8"))
         writer.write(text)
      } catch (ex: IOException) {
         ex.printStackTrace()
      }
   }

//       Path path = Paths.get(url.toURI());

   fun ref_inspect(ref: org.eclipse.jgit.lib.Ref) {
      var json = Gson().toJson(ref!!, org.eclipse.jgit.lib.Ref::class.java)
      System.out.println(json)
//      ref.getName()
//      ref.getLeaf()
//      ref.getObjectId()
//      ref.getPeeledObjectId()
//      ref.getStorage()
//      ref.getTarget()
//      ref.getUpdateIndex()
   }

   fun ref_inspect(refList: List<org.eclipse.jgit.lib.Ref>) {
      refList!!.forEach(x -> ref_inspect(x))
   }

   fun tree_inspect(tree: org.eclipse.jgit.dircache.DirCacheTree) {
      var json = Gson().toJson(tree!!, org.eclipse.jgit.dircache.DirCacheTree::class.java)
      System.out.println(json)
//      tree.getNameString()
//      tree.getObjectId()
//      tree.getPathString()
//      tree.getChildCount()
//      tree.getChild(0)
   }

   fun dircache_inspect(dirCache: org.eclipse.jgit.dircache.DirCache) {
      var json = Gson().toJson(dirCache!!, org.eclipse.jgit.dircache.DirCache::class.java)
      System.out.println(json)
   }
}


