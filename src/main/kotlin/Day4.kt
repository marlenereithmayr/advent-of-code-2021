fun main() {
    println("Day 4 - Result:")
    val input = readInput("Day4")
    val partOne = Day4.partOne(input)
    println("Part one: $partOne")
    val partTwo = Day4.partTwo(input)
    println("Part two: $partTwo")
}

data class BoardNumber(
    val number: Int,
    var isMarked: Boolean = false,
)

object Day4 {
    fun partOne(input: List<String>): Number {
        val numbers = input[0].split(",").map(String::toInt)
        val playBoards = getPlayBoards(input)
        var finished: Boolean

        numbers.forEach { number ->
            playBoards.forEach { playBoard ->
                markNumber(playBoard, number)
                finished = isFinishedHorizontal(playBoard)
                if (!finished) {
                    finished = isFinishedVertically(playBoard)
                }

                if (finished) {
                    val notMarkedValue = getNotMarkedValue(playBoard)
                    return number * notMarkedValue
                }
            }
        }
        return 1
    }


    fun partTwo(input: List<String>): Number {
        val numbers = input[0].split(",").map(String::toInt)
        val playBoards = getPlayBoards(input)
        var finished: Boolean

        var lastItem = 0
        numbers.forEach { number ->
            playBoards.forEach { playBoard ->
                if (!isFinishedHorizontal(playBoard) && !isFinishedVertically(playBoard)) {
                    markNumber(playBoard, number)

                    finished = isFinishedHorizontal(playBoard)
                    if (!finished) {
                        finished = isFinishedVertically(playBoard)
                    }

                    if (finished) {
                        val notMarkedValue = getNotMarkedValue(playBoard)
                        lastItem = number * notMarkedValue
                    }
                }
            }
        }
        return lastItem
    }

    private fun getNotMarkedValue(playBoard: List<List<BoardNumber>>) =
        playBoard.map { row ->
            row.filter { !it.isMarked }.sumOf { it.number }
        }.sum()

    private fun isFinishedHorizontal(
        playBoard: List<List<BoardNumber>>,
    ): Boolean {
        var finished = false
        playBoard.map { row ->
            val field = row.filter { !it.isMarked }
            if (field.isEmpty()) {
                finished = true
            }
        }
        return finished
    }

    private fun markNumber(playBoard: List<List<BoardNumber>>, number: Int) {
        playBoard.map { row ->
            val field = row.singleOrNull() { it.number == number }
            if (field != null) {
                field.isMarked = true
            }
        }
    }

    private fun isFinishedVertically(
        playBoard: List<List<BoardNumber>>,
    ): Boolean {
        var finished = false
        for (i in 0..4) {
            val notMarked = playBoard.map {
                it[i]
            }.filter { !it.isMarked }
            if (notMarked.isEmpty()) {
                finished = true
            }
        }
        return finished
    }

    private fun getPlayBoards(input: List<String>) = input
        .drop(1)
        .chunked(6)
        .map { line ->
            line.filter { it.isNotBlank() }
                .map { item ->
                    val numbers = item.split(" ").filter { it.isNotBlank() }
                    numbers.map { number ->
                        BoardNumber(number.toInt())
                    }
                }
        }
}
