import kotlin.math.max
import kotlin.math.min

fun main() {
    println("Day 5 - Result:")
    val input = readInput("Day5")
    val partOne = Day5.partOne(input)
    println("Part one: $partOne")
    val partTwo = Day5.partTwo(input)
    println("Part two: $partTwo")
}

data class Coordinates(
    val sourceX: Int,
    val destX: Int,
    val sourceY: Int,
    val destY: Int
)

data class Points(
    val destX: Int,
    val destY: Int,
)

data class Counter(
    val points: Points,
)

object Day5 {
    fun partOne(input: List<String>): Number {
        val coordinatesList = getCoordinates(input)
        return getEqualLineMatches(coordinatesList).map { it.points }.groupingBy { it }.eachCount()
            .filter { it.value > 1 }.size
    }

    fun partTwo(input: List<String>): Number {
        val coordinatesList = getCoordinates(input)
        return getEqualLineMatchesWithDiagonals(coordinatesList).map { it.points }.groupingBy { it }.eachCount()
            .filter { it.value > 1 }.size
    }


    private fun getEqualLineMatches(coordinatesList: List<Coordinates>): MutableList<Counter> {
        val matches: MutableList<Counter> = mutableListOf()

        coordinatesList.forEach { coordinates ->
            when {
                coordinates.sourceX == coordinates.destX -> {
                    for (i in min(coordinates.sourceY, coordinates.destY)..max(
                        coordinates.sourceY,
                        coordinates.destY
                    )) {
                        val newCoordinates = Points(coordinates.sourceX, i)
                        matches.add(Counter(newCoordinates))
                    }
                }
                coordinates.sourceY == coordinates.destY -> {
                    for (i in min(coordinates.sourceX, coordinates.destX)..max(
                        coordinates.sourceX,
                        coordinates.destX
                    )) {
                        val newCoordinates = Points(i, coordinates.destY)
                        matches.add(Counter(newCoordinates))
                    }
                }
            }
        }
        return matches
    }

    private fun getEqualLineMatchesWithDiagonals(coordinatesList: List<Coordinates>): MutableList<Counter> {
        val matches: MutableList<Counter> = mutableListOf()

        coordinatesList.forEach { coordinates ->
            when {
                coordinates.sourceX == coordinates.destX -> {
                    for (i in min(coordinates.sourceY, coordinates.destY)..max(
                        coordinates.sourceY,
                        coordinates.destY
                    )) {
                        val newCoordinates = Points(coordinates.sourceX, i)
                        matches.add(Counter(newCoordinates))
                    }
                }
                coordinates.sourceY == coordinates.destY -> {
                    for (i in min(coordinates.sourceX, coordinates.destX)..max(
                        coordinates.sourceX,
                        coordinates.destX
                    )) {
                        val newCoordinates = Points(i, coordinates.destY)
                        matches.add(Counter(newCoordinates))
                    }
                }
                else -> {
                    val xValues = getNumbersInRange(coordinates.sourceX, coordinates.destX)
                    val yValues = getNumbersInRange(coordinates.sourceY, coordinates.destY)
                    val points = xValues.zip(yValues) { x, y -> Points(x, y) }
                    points.forEach {
                        matches.add(Counter(it))
                    }
                }
            }
        }
        return matches
    }

    private fun getNumbersInRange(from: Int, to: Int): List<Int> {
        return if (from > to) {
            (to..from).map { it }.reversed()
        } else {
            (from..to).map { it }
        }
    }

    private fun getCoordinates(input: List<String>): List<Coordinates> {
        val coordinatesList = input.map {
            val points = it.split("->").map { test -> test.split(",") }
            Coordinates(
                points.first()[0].filter { string -> !string.isWhitespace() }.toInt(),
                points.last()[0].filter { string -> !string.isWhitespace() }.toInt(),
                points.first()[1].filter { string -> !string.isWhitespace() }.toInt(),
                points.last()[1].filter { string -> !string.isWhitespace() }.toInt(),
            )
        }
        return coordinatesList
    }
}
