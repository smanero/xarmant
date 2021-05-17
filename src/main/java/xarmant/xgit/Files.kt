package xarmant.xgit

import java.nio.file.Path
import java.nio.file.Files
import java.io.File

object Files {

   fun delete_create_dir(path: Path) {
      if (path.toFile().exists()) {
         java.nio.file.Files.walk(path)
            .sorted(Comparator.reverseOrder())
            .map(Path::toFile)
            .forEach(File::delete)
      }
      java.nio.file.Files.deleteIfExists(path)
      java.nio.file.Files.createDirectory(path)
   }

   fun create_file(parentPath: Path, path: Path) : Path {
      var fullPath = Path.of(parentPath.toString(), path.toString())
      if (!fullPath.toFile().exists()) {
         java.nio.file.Files.createFile(fullPath)
      }
      return fullPath
   }
}