package lab1

import lab1.ArrayHandler.createNRandomArrays
import lab1.ArrayHandler.printNArrays
import lab1.ArrayHandler.swapArray
import lab1.InsertionSort.insertionSort
import lab1.QuickInsertionSort.quickInsertionSort
import java.util.*

object QuickInsertionSort {

    private const val THRESHOLD: Int = 16

    fun quickInsertionSort(array: IntArray, l: Int, r: Int) {
        var left = l
        var right = r
        while (left < right) {
            // switch to insertion sort if the size is 16 or smaller
            if (right - left < THRESHOLD) {
                insertionSort(array, left, right)
                break
            } else {
                val pivot = partition(array, left, right)

                // tail call optimizations – recur on the smaller subarray
                if (pivot - left < right - pivot) {
                    quickInsertionSort(array, left, pivot - 1)
                    left = pivot + 1
                } else {
                    quickInsertionSort(array, pivot + 1, right)
                    right = pivot - 1
                }
            }
        }
    }


    private fun partition(array: IntArray, l: Int, r: Int): Int {
        var left = l
        var right = r
        val pivot = array[(left + right) / 2] // 4) Pivot Point
        while (left <= right) {
            while (array[left] < pivot) left++ // 5) Find the elements on left that should be on right

            while (array[right] > pivot) right-- // 6) Find the elements on right that should be on left

            // 7) Swap elements, and move left and right indices
            if (left <= right) {
                swapArray(array, left, right)
                left++
                right--
            }
        }
        return left
    }


}

fun main(args: Array<String>) {
    println("Enter arrays amount, arrays length and arrays max element:")
    val (arraysNumber, arraySize, maxElement) = readLine()!!.split(" ").map { it.toInt() }

    val array = createNRandomArrays(arraysNumber, arraySize, maxElement, Random(1000L))
    printNArrays(array)

    array.forEach {
        quickInsertionSort(it, 0, it.size - 1)
    }
    printNArrays(array)
}

