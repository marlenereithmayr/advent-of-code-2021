fun main() {
    println("Day 3 - Result:")
    val input = readInput("Day3")
    val partOne = Day3.partOne(input)
    println("Part one: $partOne")
    val partTwo = Day3.partTwo(input)
    println("Part two: $partTwo")
}

object Day3 {
    fun partOne(input: List<String>): Number {
        val numbers = parseInput(input)
        var gamma = ""
        var epsilon = ""
        for (i in 0 until numbers[0].size) {
            val (zero, one) = getBinaryValueForPosition(numbers, i)
            if (zero > one) {
                gamma += "0"
                epsilon += "1"
            }
            if (one > zero) {
                gamma += "1"
                epsilon += "0"
            }
        }

        return gamma.toInt(2) * epsilon.toInt(2)
    }

    fun partTwo(input: List<String>): Number {
        val numbers = parseInput(input)
        val oxygen = getOxygenRating(numbers)
        val co2 = getCo2Rating(numbers)

        return oxygen.toInt(2) * co2.toInt(2)
    }

    private fun parseInput(input: List<String>) = input
        .map { it.split("") }
        .map {
            it.filter { number -> number.isNotBlank() }
        }

    private fun getCo2Rating(co2Rating: List<List<String>>): String {
        val co2RatingMutable = co2Rating.toMutableList()
        var x = 0
        while (co2RatingMutable.size > 1) {
            val (zero, one) = getBinaryValueForPosition(co2RatingMutable, x)
            if (zero > one) {
                co2RatingMutable.removeIf { it[x].toInt() == 0 }
            } else {
                co2RatingMutable.removeIf { it[x].toInt() == 1 }
            }
            x++
        }
        return co2RatingMutable.first().joinToString("")
    }

    private fun getOxygenRating(oxygenRating: List<List<String>>): String {
        val oxygenRatingMutable = oxygenRating.toMutableList()
        var i = 0
        while (oxygenRatingMutable.size > 1) {
            val (zero, one) = getBinaryValueForPosition(oxygenRatingMutable, i)
            if (zero > one) {
                oxygenRatingMutable.removeIf { it[i].toInt() == 1 }
            } else {
                oxygenRatingMutable.removeIf { it[i].toInt() == 0 }
            }
            i++
        }
        return oxygenRatingMutable.first().joinToString("")
    }

    private fun getBinaryValueForPosition(
        numbers: List<List<String>>,
        i: Int
    ): Pair<Int, Int> {
        val zero = numbers.count { it[i].toInt() == 0 }
        val one = numbers.count { it[i].toInt() == 1 }
        return Pair(zero, one)
    }
}
