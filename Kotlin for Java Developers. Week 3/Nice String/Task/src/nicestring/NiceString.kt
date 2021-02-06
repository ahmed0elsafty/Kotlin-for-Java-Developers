package nicestring

fun String.isNice(): Boolean {

//    val noBadSubString = setOf("ba", "be", "bu").none { this.contains(it) }
//    val hasDoubles = this.windowed(2).any{it[0]==it[1]}
//    val hasDoubles = (0 until lastIndex).any { this[it] == this[it + 1] }
    val hasThreeVowels = this.count { it in "aeiou" } >= 3
    val hasDoubles = this.zipWithNext().any { it.first == it.second }
    val noBadSubString = setOf("ba", "be", "bu").all { !this.contains(it) }

    return listOf(hasDoubles, hasThreeVowels, noBadSubString).filter { it }.size >= 2
}