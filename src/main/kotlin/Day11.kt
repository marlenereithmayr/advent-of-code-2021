fun main() {
    println("Day 11 - Result:")
    val input = readInput("Day11")
    println("Part one: ${Day11.partOne(input)}")
    println("Part two: ${Day11.partTwo(input)}")
}

data class Octopuses(
    val energyLevelGrid: MutableList<MutableList<Int>>,
    val flashes: Int,
)

data class Position(
    val x: Int,
    val y: Int,
)

object Day11 {
    fun partOne(input: List<String>): Number {
        var energyLevelGrid = input.parseEnergyLevel()

        var flashes = 0
        repeat(100) {
            energyLevelGrid.increaseValues()
            val octopuses = getUpdatedEnergyLevel(energyLevelGrid)
            flashes += octopuses.flashes
            energyLevelGrid = octopuses.energyLevelGrid
        }

        return flashes
    }

    fun partTwo(input: List<String>): Number {
        var energyLevel = input.parseEnergyLevel()

        var counter = 0
        var flashesPerStep = 0
        while (flashesPerStep != 100) {
            energyLevel.increaseValues()
            val octopuses = getUpdatedEnergyLevel(energyLevel)
            flashesPerStep = octopuses.flashes
            energyLevel = octopuses.energyLevelGrid
            counter++
        }

        return counter
    }

    private fun List<String>.parseEnergyLevel(): MutableList<MutableList<Int>> {
        return map { it.split("") }
            .map {
                it.filter { number -> number.isNotBlank() }
                    .map { number -> number.toInt() }.toMutableList()
            }.toMutableList()
    }

    private fun MutableList<MutableList<Int>>.increaseValues() {
        for (row in indices) {
            for (column in this[row].indices) {
                this[row][column] += 1
            }
        }
    }

    private fun getUpdatedEnergyLevel(
        energyLevel: MutableList<MutableList<Int>>,
    ): Octopuses {
        var flashAmount = 0
        while (energyLevel.any { octopusRow -> octopusRow.any { it > 9 } }) {
            for (row in energyLevel.indices) {
                for (column in energyLevel[row].indices) {
                    if (energyLevel[row][column] > 9) {
                        energyLevel[row][column] = 0
                        energyLevel.increaseNeighboursByOne(row, column)
                        flashAmount++
                    }
                }
            }
        }
        return Octopuses(energyLevel, flashAmount)
    }

    private fun MutableList<MutableList<Int>>.increaseNeighboursByOne(
        rowNumber: Int,
        columnNumber: Int
    ) {
        val neighbours = this.getNeighbourPositions(rowNumber, columnNumber)
        neighbours.forEach {
            if (this[it.x][it.y] != 0)
                this[it.x][it.y]++
        }
    }

    private fun MutableList<MutableList<Int>>.getNeighbourPositions(
        rowNumber: Int,
        columnNumber: Int
    ): List<Position> {
        val neighbours: MutableList<Position> = mutableListOf()
        for (row in -1..1) {
            for (column in -1..1) {
                if (row != 0 || column != 0) {
                    val rowNumber = rowNumber + row
                    val columnNumber = columnNumber + column
                    if (rowNumber in 0 until size) {
                        if (columnNumber >= 0 && columnNumber < this[rowNumber].size) {
                            neighbours.add(Position(rowNumber, columnNumber))
                        }
                    }
                }
            }
        }

        return neighbours
    }
}
