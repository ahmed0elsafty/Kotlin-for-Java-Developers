package mastermind

data class Evaluation(val rightPosition: Int, val wrongPosition: Int)

fun evaluateGuess(secret: String, guess: String): Evaluation {
    val secretMap = mutableMapOf<Int, Char>()
    val guessMap = mutableMapOf<Int, Char>()
    for ((index, char) in secret.withIndex()) {
        secretMap[index] = char
    }
    for ((index, char) in guess.withIndex()) {
        guessMap[index] = char
    }
    fun getRightPositions(): Map<Int, Char> {
        val rightPositions = mutableMapOf<Int, Char>()
        for ((i,char) in guessMap){
            if (secretMap[i]==guessMap[i]) rightPositions[i] = char
        }
        return rightPositions
    }

    fun getNumOfCharInString(char: Char, s: String): Int {
        var count = 0
        for (i in s) {
            if (i == char) count++
        }
        return count
    }

    fun getWrongPositions(rightPositions: Map<Int, Char>): Int {
        var wrongPositions = 0
        for ((i, char) in rightPositions) {
            secretMap.remove(i, char)
            guessMap.remove(i,char)
        }
        for ((i, c) in guessMap) {
            val secretString = secretMap.values.joinToString("")
            val count = getNumOfCharInString(c, secretString)
            if (count > 0){
                wrongPositions++
                val index = secretMap.values.indexOf(c)
                secretMap.remove(index)
            }
        }

        return wrongPositions
    }

    val mapOfRightPositions = getRightPositions()
    val rightPosition = mapOfRightPositions.size
    val wrongPosition = getWrongPositions(mapOfRightPositions)
    return Evaluation(rightPosition, wrongPosition)
}

