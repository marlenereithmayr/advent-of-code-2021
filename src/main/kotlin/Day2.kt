fun main() {
    println("Day 2 - Result:")
    val input = readInput("Day2")
    println("Part one: ${Day2.partOne(input)}")
    println("Part two: ${Day2.partTwo(input)}")
}

object Day2 {
    data class Movement(
        val direction: String,
        val steps: Int,
    )

    fun partOne(input: List<String>): Number {
        val commands = parseInput(input)
        var depth = 0
        var horizontal = 0
        commands.forEach { movement ->
            when (movement.direction) {
                "forward" -> {
                    horizontal += movement.steps
                }
                "down" -> {
                    depth += movement.steps
                }
                "up" -> {
                    depth -= movement.steps
                }
            }
        }

        return depth * horizontal
    }

    fun partTwo(input: List<String>): Number {
        val commands = parseInput(input)
        var aim = 0
        var horizontal = 0
        var depth = 0
        commands.forEach { movement ->
            when (movement.direction) {
                "forward" -> {
                    horizontal += movement.steps
                    depth += aim * movement.steps
                }
                "down" -> {
                    aim += movement.steps
                }
                "up" -> {
                    aim -= movement.steps
                }
            }
        }

        return depth * horizontal
    }

    private fun parseInput(input: List<String>) = input.map {
        it.split(" ")
    }.map { direction -> Movement(direction[0], direction[1].toInt()) }
}
