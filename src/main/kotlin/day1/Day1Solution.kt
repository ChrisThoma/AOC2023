package day1

import Solution
import java.io.File
import java.util.Scanner

class Day1Solution : Solution {

    val numberMap = mapOf('0' to "zero", '1' to "one", '2' to "two", '3' to "three", '4' to "four", '5' to "five", '6' to "six", '7' to "seven", '8' to "eight", '9' to "nine")
    override val inputPath = "src/main/kotlin/day1/Day1Input.txt"
    /*
    the calibration value can be found by combining the first digit and the last digit (in that order) to form a single two-digit number.
    For example:
        1abc2
        pqr3stu8vwx
        a1b2c3d4e5f
        treb7uchet

     In this example, the calibration values of these four lines are 12, 38, 15, and 77. Adding these together produces 142.

     Consider your entire calibration document. What is the sum of all of the calibration values?

     --- Part Two ---

    Your calculation isn't quite right. It looks like some of the digits are actually spelled out with letters: one, two, three, four, five, six, seven, eight, and nine also count as valid "digits".
     */

    override fun printSolution() {
        printPart1Solution()
        printPart2Solution()
    }

    fun printPart1Solution() {
        var sum = 0
        readFile().forEachLine { line ->
            val firstDigit = findFirstDigit(line)
            val secondDigit = findFirstDigit(line.reversed())
            val number = "$firstDigit$secondDigit".toInt()
            sum += number
            println("LINE: $line || $firstDigit$secondDigit")
        }
        println("sum = $sum")
    }

    fun findFirstDigit(text: String) : Char {
        text.forEach {
            if (it.isDigit()) { return it }
        }
        return '0'
    }

    fun printPart2Solution() {
        var sum = 0
        readFile().forEachLine { line ->
            val numberPositionList = mutableListOf<Pair<Int, Char>>()
            val lastNumberPositionList = mutableListOf<Pair<Int, Char>>()
            numberMap.forEach { (num, word) ->
                val numPosition = line.indexOf(num)
                if (numPosition >= 0) {
                    numberPositionList.add(Pair(numPosition, num))
                }
                val wordPosition = line.indexOf(word)
                if (wordPosition >= 0) {
                    numberPositionList.add(Pair(wordPosition, num))
                }

                val lastNumPosition = line.lastIndexOf(num)
                if (lastNumPosition >= 0) {
                    lastNumberPositionList.add(Pair(lastNumPosition, num))
                }
                val lastWordPosition = line.lastIndexOf(word)
                if (lastWordPosition >= 0) {
                    lastNumberPositionList.add(Pair(lastWordPosition, num))
                }
            }

            numberPositionList.sortBy { pair ->
                pair.first
            }
            lastNumberPositionList.sortByDescending { pair ->
                pair.first
            }
            val number = "${numberPositionList.first().second}${lastNumberPositionList.first().second}"
            println("$line || $number")
            sum += number.toInt()
        }
        println("$sum")
    }
}