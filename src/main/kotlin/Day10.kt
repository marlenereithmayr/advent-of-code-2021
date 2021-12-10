fun main() {
    println("Day 10 - Result:")
    val input = readInput("Day10")
    println("Part one: ${Day10.partOne(input)}")
    println("Part two: ${Day10.partTwo(input)}")
}

object Day10 {
    private val openToCloseTags = mapOf(
        "(" to ")",
        "[" to "]",
        "{" to "}",
        "<" to ">",
    )

    private val openingTags = listOf(
        "(",
        "[",
        "{",
        "<",
    )

    private val syntaxErrorScore = mapOf(
        ")" to 3,
        "]" to 57,
        "}" to 1197,
        ">" to 25137,
    )

    fun partOne(input: List<String>): Number {
        return input
            .map { it.split("") }
            .map {
                it.filter { number -> number.isNotBlank() }
            }.mapNotNull { getFirstInvalidChar(it) }
            .sumOf { bracket ->
                syntaxErrorScore[bracket]!!
            }
    }

    fun partTwo(input: List<String>): Number {
        val inputData = input
            .map { it.split("") }
            .map {
                it.filter { number -> number.isNotBlank() }
            }.filter { getFirstInvalidChar(it) == null }
            .map { getIncompleteChars(it) }
            .map {
                it.fold(0L) { result, current ->
                    result * 5 + getAutocompleteScore(current)
                }
            }

        return inputData.sorted()[inputData.size / 2]
    }

    private fun getAutocompleteScore(character: String): Int {
        return when (character) {
            ")" -> 1
            "]" -> 2
            "}" -> 3
            ">" -> 4
            else -> error("Not allowed")
        }
    }

    private fun getFirstInvalidChar(subSystemSyntaxLine: List<String>): String? {
        val openingTags: MutableList<String> = mutableListOf()
        subSystemSyntaxLine.forEach { value ->
            if (this.openingTags.contains(value)) {
                openingTags.add(value)
            } else {
                val correctClosingTag = openToCloseTags[openingTags.last()]
                if (correctClosingTag == value) {
                    openingTags.removeLast()
                } else {
                    return value
                }
            }
        }
        return null
    }

    private fun getIncompleteChars(subSystemSyntaxLine: List<String>): List<String> {
        val openingTags: MutableList<String> = mutableListOf()
        subSystemSyntaxLine.forEach { value ->
            if (this.openingTags.contains(value)) {
                openingTags.add(value)
            } else {
                openingTags.removeLast()
            }
        }
        return openingTags.map { openToCloseTags[it]!! }.reversed()
    }
}
