fun main() {
    println("Day 8 - Result:")
    val input = readInput("Day8")
    println("Part one: ${Day8.partOne(input)}")
    println("Part two: ${Day8.partTwo(input)}")
}

object Day8 {
    fun partOne(input: List<String>): Number {
        val digits = input
            .map { it.split("|") }
            .map { it.last() }
            .map { it.split(" ") }
            .map { it.filter { digits -> digits.isNotBlank() }.map { digits -> digits.length } }

        val uniqueNumbers = listOf(2, 3, 7, 4)
        return digits.sumOf { it.count { digit -> uniqueNumbers.contains(digit) } }
    }

    fun partTwo(input: List<String>): Number {
        val digitInput = input
            .map { it.split("|") }
            .map { digits ->
                digits.map { it.split(" ") }
                    .map { it.filter { digits -> digits.isNotBlank() } }
            }

        var totalOutputValue = 0
        digitInput.forEach { digits ->
            val fourDigitValue = mutableMapOf<Int, String>()
            val entryValue = digits.first()
            fourDigitValue[1] = entryValue.single { it.length == 2 }
            fourDigitValue[8] = entryValue.single { it.length == 7 }
            fourDigitValue[4] = entryValue.single { it.length == 4 }
            fourDigitValue[7] = entryValue.single { it.length == 3 }
            val fiveChars = entryValue.filter { it.length == 5 }
            val sixChars = entryValue.filter { it.length == 6 }

            fourDigitValue[3] = fiveChars.single { equalLineAmount(it, fourDigitValue[7]!!) == 3 }
            fourDigitValue[9] = sixChars.single { equalLineAmount(it, fourDigitValue[3]!!) == 5 }
            fourDigitValue[0] =
                sixChars.single { it != fourDigitValue[9] && equalLineAmount(it, fourDigitValue[7]!!) == 3 }
            fourDigitValue[6] = sixChars.single { it != fourDigitValue[0] && it != fourDigitValue[9] }
            fourDigitValue[5] =
                fiveChars.single { equalLineAmount(it, fourDigitValue[9]!!) == 5 && it != fourDigitValue[3] }
            fourDigitValue[2] = fiveChars.single { it != fourDigitValue[3] && it != fourDigitValue[5] }

            var output = ""
            digits.last().map { digit ->
                for (i in 0..9) {
                    if (fourDigitValue[i]!!.alphabetized() == digit.alphabetized()) {
                        output += i.toString()
                    }
                }
            }

            totalOutputValue += output.toInt()
        }

        return totalOutputValue
    }

    private fun String.alphabetized(): String = String(toCharArray().apply { sort() })

    private fun equalLineAmount(digit: String, number: String) =
        digit.split("").filter { digits -> digits.isNotBlank() }.count {
            number.contains(it)
        }
}
