fun main() {
    println("Day 5 - Result:")
    val input = readInput("Day6")
    val partOne = Day6.partOne(input)
    println("Part one: $partOne")
    val partTwo = Day6.partTwo(input)
    println("Part two: $partTwo")
}

object Day6 {
    fun partOne(input: List<String>): Number {
        var fishes = input.first().split(",").map{it.toInt()}
        for (i in 0..79) {
            var dead = 0
            fishes = fishes.map {
                if(it == 0) {
                    dead++
                    6
                } else {
                    it-1
                }
            }.toMutableList()
            fishes.addAll(List(dead) { 8 })
        }
        return fishes.count()
    }

    fun partTwo(input: List<String>): Number {
        val fishes = input.first().split(",").map{it.toInt()}
        var state = Array(9) {0L}

        fishes.forEach { fish ->
            state[fish]++
        }
        for (i in 0..255) {
            val newState = Array(9) {0L}
            for (pos in 1 until state.size) {
                newState[pos-1] = state[pos]
            }
            newState[8] += state[0]
            newState[6] += state[0]
            state = newState
        }

        return state.sum()
    }
}
