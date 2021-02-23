package rationals

import java.math.BigInteger
import java.math.BigInteger.ONE
import java.math.BigInteger.ZERO


class Rational(n: BigInteger, d: BigInteger = ONE) : Comparable<Rational> {
    val numerator: BigInteger
    val denominator: BigInteger

    init {
        require(d != ZERO) { "Denominator must be non-Zero" }
        val gcd = n.gcd(d)
        when {
            n > ZERO && d < ZERO -> {
                numerator = (n * -ONE) / gcd
                denominator = (d * -ONE) / gcd
            }
            n < ZERO && d < ZERO -> {
                numerator = (n * -ONE) / gcd
                denominator = (d * -ONE) / gcd
            }
            else -> {
                numerator = n / gcd
                denominator = d / gcd
            }
        }
    }

    override fun toString(): String {
        return when (denominator) {
            ONE -> "$numerator"
            else -> "$numerator/$denominator"
        }
    }

    override fun equals(other: Any?): Boolean {
        return if (other is Rational) {
            numerator == other.numerator && denominator == other.denominator
        } else false
    }

    override fun compareTo(other: Rational): Int {
        return when {
            numerator * other.denominator > other.numerator * denominator -> 1
            numerator * other.denominator < other.numerator * denominator -> -1
            else -> 0
        }
    }

    operator fun plus(other: Rational): Rational {
        val num = numerator * other.denominator + other.numerator * denominator
        val den = denominator * other.denominator
        val gcd = num.gcd(den)
        return Rational(num / gcd, den / gcd)
    }

    operator fun minus(other: Rational): Rational {
        val n = numerator * other.denominator - other.numerator * denominator
        val d = denominator * other.denominator
        val gcd = n.gcd(d)
        return Rational(n / gcd, d / gcd)
    }

    operator fun times(other: Rational): Rational {
        val n = numerator * other.numerator
        val d = denominator * other.denominator
        val gcd = n.gcd(d)
        return Rational(n / gcd, d / gcd)
    }

    operator fun div(other: Rational): Rational {
        val n = numerator * other.denominator
        val d = other.numerator * denominator
        val gcd = n.gcd(d)
        return Rational(n / gcd, d / gcd)
    }

    operator fun unaryMinus(): Rational {
        return Rational(numerator * -ONE, denominator)
    }


    override fun hashCode(): Int {
        var result = numerator.hashCode()
        result = 31 * result + denominator.hashCode()
        return result
    }

}

fun String.toRational(): Rational? {
    fun String.toBigIntegerOrFail()=
        toBigIntegerOrNull() ?: throw IllegalAccessException("Expecting Rational in the form of 'n/d' or 'n', was: '$this'")


    if (!contains('/')) {
        return toBigIntegerOrNull()?.let { Rational(it) }
    }
    val (numer, denom) = split("/")
    return Rational(numer.toBigIntegerOrFail() , denom.toBigIntegerOrFail())

}

infix fun Int.divBy(d: Int): Rational = Rational(toBigInteger(), d.toBigInteger())

infix fun Long.divBy(d: Long): Rational = Rational(toBigInteger(), d.toBigInteger())

infix fun BigInteger.divBy(d: BigInteger): Rational = Rational(this, d)

fun main() {
    val half = 1 divBy 2
    val third = 1 divBy 3

    val sum: Rational = half + third
    println(5 divBy 6 == sum)

    val difference: Rational = half - third
    println(1 divBy 6 == difference)

    val product: Rational = half * third
    println(1 divBy 6 == product)

    val quotient: Rational = half / third
    println(3 divBy 2 == quotient)

    val negation: Rational = -half
    println(-1 divBy 2 == negation)

    println((2 divBy 1).toString() == "2")
    println((-2 divBy 4).toString() == "-1/2")
    println("117/1098".toRational().toString() == "13/122")
    println("117 / 1098".toRational().toString() == "13/122")

    val twoThirds = 2 divBy 3
    println(half < twoThirds)

    println(half in third..twoThirds)

    println(2000000000L divBy 4000000000L == 1 divBy 2)

    println(
        "912016490186296920119201192141970416029".toBigInteger() divBy
                "1824032980372593840238402384283940832058".toBigInteger() == 1 divBy 2
    )
    val withZeroDeno = 1 divBy 0
    print(withZeroDeno)
}



