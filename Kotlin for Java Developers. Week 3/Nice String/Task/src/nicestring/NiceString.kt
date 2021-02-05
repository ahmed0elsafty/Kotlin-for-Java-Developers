package nicestring

fun String.isNice(): Boolean {
    var satistfied = 0
    fun containAnySeq(): Boolean {
        return this.contains("ba") || this.contains("bu") || this.contains("be")
    }

    fun containDoubles(): Boolean {
        for ((i, _) in this.withIndex()) {
            if (i == this.length - 1) break
            if (this[i] == this[i + 1]) {
                return true
            } else {
                continue
            }
        }
        return false
    }

    fun containThreeVowels(): Boolean {
        val count = this.count { it in listOf('a', 'e', 'i', 'o', 'u') }
        return count >= 3
    }
    if (!containAnySeq()) satistfied++
    if (containDoubles()) satistfied++
    if (containThreeVowels()) satistfied++
    return satistfied >= 2
}