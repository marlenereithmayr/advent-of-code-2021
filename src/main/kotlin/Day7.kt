import kotlin.math.abs

fun main() {
    println("Day 7 - Result:")
    val input = readInput("Day7")
    val partOne = Day7.partOne(input)
    println("Part one: $partOne")
    val partTwo = Day7.partTwo(input)
    println("Part two: $partTwo")
}

object Day7 {
    fun partOne(input: List<String>): Number {
        val position = input.first().split(",").map { it.toInt() }
        val max = position.maxOrNull()!!
        val fuelPerPosition = Array(max) { 0 }
        for (i in 0 until max) {
            position.forEach {
                fuelPerPosition[i] += abs(it - i)
            }
        }
        return fuelPerPosition.minOrNull()!!
    }

    fun partTwo(input: List<String>): Number {
        val position = input.first().split(",").map { it.toInt() }
        val max = position.maxOrNull()!!
        val fuelPerPosition = Array(max) { 0 }

        for (i in 0 until max) {
            position.forEach {
                val steps = abs(it - i)
                var additionalFuel = 0
                for (amount in 1 until  steps) {
                    additionalFuel += amount
                }
                fuelPerPosition[i] += steps + additionalFuel
            }
        }
        return fuelPerPosition.minOrNull()!!
    }
}
