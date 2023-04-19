package se.nt1c.authservice

import org.junit.jupiter.api.Test
import java.lang.Math.abs
import java.lang.Math.floor
import java.math.BigDecimal
import java.math.RoundingMode


class AuthServiceApplicationTests {

    @Test
    fun zxc() {
        println(getResult(3))
    }

    fun getResult(n: Int): Int {
        if (n <= 2) return 0
        var lower = BigDecimal(1)
        var upper = BigDecimal(0)
        for (i in 2..n)
            for (j in 1 until i) {
                lower = lower.multiply(BigDecimal(i))
            }
        println(lower)

        for (i in 2..n)
            for (j in 1 until i) {
                upper = upper.plus(lower.divide(BigDecimal(i), RoundingMode.FLOOR).multiply(BigDecimal(j)))
            }
        println(upper)
        return upper.divide(lower, RoundingMode.FLOOR).intValueExact()
    }

    fun reducedProperFractions(n: Int): List<Pair<Int, Int>> {
        val fractions = mutableListOf<Pair<Int, Int>>()
        for (i in 2..n) {
            for (j in 1 until i) {
                if (getGCD(i, j) == 1) {
                    fractions.add(Pair(j, i))
                }
            }
        }
        return fractions
    }

    fun sumOfFractionsParts(n: Int): Pair<Int, Int> {
        val fractions = reducedProperFractions(n)
        val numeratorSum = fractions.sumOf { it.first }
        val denominatorSum = fractions.sumOf { it.second }
        return Pair(numeratorSum, denominatorSum)
    }

    @Test
    fun dsad() {
        println(sumOfFractionsParts(3))
    }

    fun getGCD(input1: Int, input2: Int): Int {
        var myResult = 1
        var i = 1
        while (i <= input1 && i >= input2) {
            if (input1 % i == 0 && input2 % i == 0)
                myResult = i
            ++i
        }
        return myResult
    }


    fun lcm(a: Int, b: Int): Int {
        return kotlin.math.abs(a * b) / getGCD(a, b)
    }

    fun commonDenominator(fractions: List<Pair<Int, Int>>): Int {
        val denominators = fractions.map { it.second }
        var lcmDenominator = denominators[0]
        for (d in denominators.drop(1)) {
            lcmDenominator = lcm(lcmDenominator, d)
        }
        return lcmDenominator
    }

    fun commonFraction(fractions: List<Pair<Int, Int>>): Pair<Int, Int> {
        val lcmDenominator = commonDenominator(fractions)
        var sumNumerators = 0
        for (f in fractions) {
            val numerator = f.first * (lcmDenominator / f.second)
            sumNumerators += numerator
        }
        val gcdNumerators = getGCD(sumNumerators, lcmDenominator)
        val commonNumerator = sumNumerators / gcdNumerators
        val commonDenominator = lcmDenominator / gcdNumerators
        return Pair(commonNumerator, commonDenominator)
    }

    @Test
    fun getRes(): Int {
        var n = 3
//        if (n <= 2) return 0
        var list = mutableListOf<Pair<Int, Int>>()
        for (i in 2..n)
            for (j in 1 until i) {
                list.add(Pair<Int, Int>(j, i))
            }
        val commonDenominator = commonFraction(list)
        return floor((commonDenominator.first.toDouble() / commonDenominator.second.toDouble())).toInt()
    }
}

