fun main() {
    println("Day 1 - Result:")
    val input = readInput("Day1")
    println("Part one: ${Day1.partOne(input)}")
    println("Part two: ${Day1.partTwo(input)}")
}

object Day1 {
    fun partOne(input: List<String>): Number {
        val report = input.map { it.toInt() }
        var increases = 0
        for (i in 1 until report.size) {
            if (report[i] > report[i - 1]) {
                increases++
            }
        }

        return increases
    }

    fun partTwo(input: List<String>): Number {
        val report = input.map { it.toInt() }
        var increases = 0
        for (i in 1 until report.size - 2) {
            val first = report[i - 1] + report[i] + report[i + 1]
            val second = report[i] + report[i + 1] + report[i + 2]
            if (second > first) {
                increases++
            }
        }

        return increases
    }
}
