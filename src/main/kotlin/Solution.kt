import java.io.File

interface Solution {
    val inputPath : String
    fun printSolution()
    fun readFile() : File {
        return File(inputPath)
    }
}