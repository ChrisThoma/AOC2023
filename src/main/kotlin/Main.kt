import day1.Day1Solution
import day2.Day2Solution

fun main(args: Array<String>) {
    println("Hello World!")

    // Try adding program arguments via Run/Debug configuration.
    // Learn more about running applications: https://www.jetbrains.com/help/idea/running-applications.html.
    println("Program arguments: ${args.joinToString()}")

//    val d1 : Solution = Day1Solution()
//    d1.printSolution()

    val d2 : Solution = Day2Solution()
    d2.printSolution()
}