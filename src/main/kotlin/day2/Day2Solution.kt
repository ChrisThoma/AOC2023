package day2

import Solution

class Day2Solution : Solution {

    override val inputPath: String = "src/main/kotlin/day2/Day2Input.txt"
    /*
    Determine which games would have been possible if the bag had been loaded with only 12 red cubes, 13 green cubes, and 14 blue cubes. What is the sum of the IDs of those games?

    Ex:
        Game 1: 3 blue, 4 red; 1 red, 2 green, 6 blue; 2 green
        Game 2: 1 blue, 2 green; 3 green, 4 blue, 1 red; 1 green, 1 blue
        Game 3: 8 green, 6 blue, 20 red; 5 blue, 4 red, 13 green; 5 green, 1 red
        Game 4: 1 green, 3 red, 6 blue; 3 green, 6 red; 3 green, 15 blue, 14 red
        Game 5: 6 red, 1 blue, 3 green; 2 blue, 1 red, 2 green

     */
    val maxRed = 12
    val maxGreen = 13
    val maxBlue = 14

    override fun printSolution() {
        printPart1()
        printPart2()
    }

    private fun printPart1() {
        var gameNumber = 0
        var sum = 0
        readFile().forEachLine { line ->
            gameNumber++

            val stringWithoutGameId = line.substringAfter(':')
            val hands = stringWithoutGameId.split(';')
            print(hands)
            if (isGamePossibleGivenHands(hands)) {
                println(" $gameNumber is possible")
                sum += gameNumber
            } else {
                println(" $gameNumber is not possible")
            }
        }
        println("$sum")
    }

    private fun isGamePossibleGivenHands(hands: List<String>) : Boolean {
        hands.forEach { hand ->
            val colorDraws = hand.split(',')
            if (!isGamePossibleForDraws(colorDraws)) {
                return false
            }
        }
        return true
    }

    private fun isGamePossibleForDraws(colorDraws: List<String>) : Boolean {
        colorDraws.forEach { draw ->
            val trimmed = draw.trim()
            val components = trimmed.split(' ')

            val colorCount = components[0].toInt()
            if (colorCount > 14) { return false }

            val colorName = components[1]
             when (colorName) {
                "red" -> {
                    if (colorCount > maxRed) { return false }
                }

                "green" -> {
                    if (colorCount > maxGreen) { return false }
                }
            }
        }
        return true
    }

    private fun printPart2() {
        var sum = 0
        readFile().forEachLine { line ->
            val stringWithoutGameId = line.substringAfter(':')
            val hands = stringWithoutGameId.split(';')
            val maxColorCountMap = mutableMapOf("red" to 0, "green" to 0, "blue" to 0)
            hands.forEach { hand ->
                val pulls = hand.split(',')
                pulls.forEach { pull ->
                    val trimmedPull = pull.trim()
                    val components = trimmedPull.split(' ')
                    val color = components[1]
                    val count = components[0].toInt()
                    if (count > (maxColorCountMap[color] ?: 0)) {
                        maxColorCountMap[color] = count
                    }
                }
            }
            print("hands = $hands")
            val power = maxColorCountMap.values.reduce { power, value -> power * value }
            print(" || power = $power")
            sum += power
            println(" || sum = $sum")
        }
    }
}