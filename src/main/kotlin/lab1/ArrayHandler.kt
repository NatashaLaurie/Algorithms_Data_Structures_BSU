package lab1

import java.util.*
import kotlin.math.ln

object ArrayHandler {

    fun createNRandomArrays(arrayNumber: Int, size: Int, randomTo: Int, random: Random): Array<IntArray> {
        val array: Array<IntArray> = Array(arrayNumber) { IntArray(size) }
        (0 until arrayNumber).forEach { i ->
            (0 until size).forEach { j ->
                array[i][j] = randomIntNumberBetween(randomTo, random)
            }
        }
        return array
    }

    fun createArray(size: Int, randomTo: Int, random: Random) : IntArray {
        val array = IntArray(size)
        for (i in 0 until size) {
            array[i] = random.nextInt(0, randomTo)
        }
        return array
    }

    private fun randomIntNumberBetween(to: Int, random: Random): Int {
        return random.nextInt(0, to)
    }

    fun boxMullerRandomArray(size: Int, random: Random): IntArray {
        val array = IntArray(size)
        for (i in 0 until size) {
            array[i] = random.nextGaussian().toInt()
        }
        return array
    }

    fun printNArrays(array: Array<IntArray>) {
        array.forEach { printArray(it) }
    }
    private fun printArray(array: IntArray) = println(array.contentToString())

    fun swapArray(a: IntArray, b: Int, c: Int) {
        val temp = a[b]
        a[b] = a[c]
        a[c] = temp
    }

}