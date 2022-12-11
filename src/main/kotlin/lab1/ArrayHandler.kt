package lab1

import java.util.*

object ArrayHandler {

    fun createNRandomArrays(arrayNumber: Int, size: Int, randomTo: Int): Array<IntArray> {
        val array: Array<IntArray> = Array(arrayNumber) { IntArray(size) }
        (0 until arrayNumber).forEach { i ->
            (0 until size).forEach { j ->
                array[i][j] = randomIntNumberBetween(randomTo)
            }
        }
        return array
    }

    private fun randomIntNumberBetween(to: Int): Int {
        return Random().nextInt(0, to)
    }

    fun printNArrays(array: Array<IntArray>) {
        array.forEach { printArray(it) }
    }
    private fun printArray(array: IntArray) = println(array.contentToString())

}