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
                if (schematic[pair.first][pair.second].isDigit()) {
                    val num = searchValidNumber(schematic, pair.first, pair.second)
                    if (num >= 0) {
                    println("valid number: $num")
                        sum += num
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
            if (schematic[pair.first][pair.second].isDigit()) {
                val num = searchValidNumber(schematic, pair.first, pair.second)
                if (num >= 0) {
                    println("valid number: $num")
                    sum += num
                }
            }

            if (pair.second < schematic[pair.first].size - 1) {
                queue.add(Pair(pair.first, pair.second + 1))
            }
        }

        println("sum = $sum")
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
}