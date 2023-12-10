package day3

import Solution
import java.io.File
import java.lang.StringBuilder
import java.util.*

class Day3Solution : Solution {
    val schematic = mutableListOf<MutableList<Char>>()
    override val inputPath = "src/main/kotlin/day3/Day3Input.txt"

    override fun printSolution() {
        val schematic = mutableListOf<MutableList<Char>>()
        val scanner = Scanner(readFile())
        schematic.add(scanner.nextLine().toMutableList())
        schematic.add(scanner.nextLine().toMutableList())
        var sum = 0
        val queue: Queue<Pair<Int, Int>> = LinkedList()

        while (scanner.hasNextLine()) {
            schematic.add(scanner.nextLine().toMutableList())
            queue.add(Pair(0, 0))
            while (!queue.isEmpty()) {
                val pair = queue.poll()
                if (schematic[pair.first][pair.second] == '*') {

                    val firstNum = searchForNumber(schematic, pair.first, pair.second)
                    val secondNum = searchForNumber(schematic, pair.first, pair.second)
                    if (firstNum >= 0 && secondNum >= 0 && firstNum != secondNum) {
                        sum += firstNum * secondNum
                    }
                }

                if (pair.second < schematic[pair.first].size - 1) {
                    queue.add(Pair(pair.first, pair.second + 1))
                }
                if (pair.first < schematic.size - 2) {
                    queue.add(Pair(pair.first + 1, pair.second))
                }
            }
            schematic.removeAt(0)
        }

        queue.add(Pair(1, 0))
        while (!queue.isEmpty()) {
            val pair = queue.poll()
            if (schematic[pair.first][pair.second] == '*') {
                val firstNum = searchForNumber(schematic, pair.first, pair.second)
                val secondNum = searchForNumber(schematic, pair.first, pair.second)
                if (firstNum >= 0 && secondNum >= 0 && firstNum != secondNum) {
                    println("valid numbers: $firstNum, $secondNum = ${firstNum*secondNum}")
                    sum += firstNum * secondNum
                }
            }

            if (pair.second < schematic[pair.first].size - 1) {
                queue.add(Pair(pair.first, pair.second + 1))
            }
        }

        println("sum = $sum")
    }

    private fun searchForNumber(schematic: List<MutableList<Char>>, i: Int, j: Int) : Int {
        if (i - 1 >= 0) {
            if (isDigit(schematic, i-1, j)) {
                return createNumber(schematic, i-1, j)
            }

            if (j - 1 >= 0) {
                if (isDigit(schematic, i-1, j-1)) {
                    return createNumber(schematic, i-1, j-1)
                }
            }

            if (j + 1 < schematic[i-1].size) {
                if (isDigit(schematic, i-1, j+1)) {
                    return createNumber(schematic, i-1, j+1)
                }
            }
        }

        if (i + 1 < schematic.size) {
            if (isDigit(schematic, i+1, j)) {
                return createNumber(schematic, i+1, j)
            }

            if (j + 1 < schematic[i].size) {
                if (isDigit(schematic, i+1, j+1)) {
                    return createNumber(schematic, i+1, j+1)
                }
            }

            if (j - 1 >= 0) {
                if (isDigit(schematic, i + 1, j-1)) {
                    return createNumber(schematic, i+1, j-1)
                }
            }
        }

        if (j - 1 >= 0) {
            if (isDigit(schematic, i, j-1)) {
                return createNumber(schematic, i, j-1)
            }
        }
        if (j + 1 < schematic[i].size) {
            if (isDigit(schematic, i, j+1)) {
                return createNumber(schematic, i, j+1)
            }
        }

        return -1
    }

    private fun createNumber(schematic: List<MutableList<Char>>, i: Int, j: Int) : Int {
        val stringBuilder = StringBuilder()
        for (x in j downTo 0) {
            if (!schematic[i][x].isDigit()) {
                break
            }
            stringBuilder.insert(0, schematic[i][x])
            schematic[i][x] = '.'

        }
        for (x in j+1..< schematic[i].size) {
            if (!schematic[i][x].isDigit()) {
                break
            }
            stringBuilder.append(schematic[i][x])
            schematic[i][x] = '.'
        }
        return stringBuilder.toString().toInt()
    }

    private fun searchValidNumber(schematic: List<MutableList<Char>>, i: Int, j: Int) : Int {
        var isValidNumber = false
        val numberBuilder = StringBuilder()
        for (x in j..<schematic[i].size) {
            if (!schematic[i][x].isDigit()) {
                break
            }
            numberBuilder.append(schematic[i][x])
            schematic[i][x] = '.'
            isValidNumber = isValidNumber || hasSymbolNeighbor(schematic, i, x)
        }
        return if (isValidNumber) {
            numberBuilder.toString().toInt()
        } else {
            -1
        }
    }

    private fun hasSymbolNeighbor(schematic: List<MutableList<Char>>, i: Int, j: Int) : Boolean {
        if (i - 1 >= 0) {
            if (isSymbol(schematic, i-1, j)) {
                return true
            }

            if (j - 1 >= 0) {
                if (isSymbol(schematic, i-1, j-1)) {
                    return true
                }
            }

            if (j + 1 < schematic[i-1].size) {
                if (isSymbol(schematic, i-1, j+1)) {
                    return true
                }
            }
        }

        if (i + 1 < schematic.size) {
            if (isSymbol(schematic, i+1, j)) {
                return true
            }

            if (j + 1 < schematic[i].size) {
                if (isSymbol(schematic, i+1, j+1)) {
                    return true
                }
            }

            if (j - 1 >= 0) {
                if (isSymbol(schematic, i + 1, j-1)) {
                    return true
                }
            }
        }

        if (j - 1 >= 0) {
            if (isSymbol(schematic, i, j-1)) {
                return true
            }
        }
        if (j + 1 < schematic[i].size) {
            if (isSymbol(schematic, i, j+1)) {
                return true
            }
        }

        return false
    }

    private fun isSymbol(schematic: List<MutableList<Char>>, i: Int, j: Int) : Boolean {
        return schematic[i][j] != '.' && !schematic[i][j].isDigit()
    }

    private fun isDigit(schematic: List<MutableList<Char>>, i:Int, j:Int) : Boolean {
        return schematic[i][j].isDigit()
    }
}