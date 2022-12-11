package lab1

import lab1.ArrayHandler.boxMullerRandomArray
import lab1.ArrayHandler.createArray
import lab1.ArrayHandler.createNRandomArrays
import lab1.ArrayHandler.swapArray
import lab1.QuickSort.quickSortHoare
import lab1.QuickSort.quickSortLomuto
import lab1.QuickSort.quickSortMedianOfThree
import lab1.QuickSort.quickSortMiddleKey
import lab1.QuickSort.quickSortRandomKey
import lab1.QuickSort.quickSortSecondKey
import java.util.*


object QuickSort {

    fun quickSortMiddleKey(array: IntArray): IntArray {
        if (array.size < 2) return array
        val pivot = array[array.size / 2]
        val less = array.filter { it < pivot }.toIntArray()
        val equal = array.filter { it == pivot }.toIntArray()
        val greater = array.filter { it > pivot }.toIntArray()
        return quickSortMiddleKey(less) + equal + quickSortMiddleKey(greater)
    }

    fun quickSortSecondKey(array: IntArray): IntArray {
        if (array.size < 2) return array
        val key = array[1]
        val less = array.filter { it < key }.toIntArray()
        val equal = array.filter { it == key }.toIntArray()
        val greater = array.filter { it > key }.toIntArray()
        return quickSortSecondKey(less) + equal + quickSortSecondKey(greater)
    }

    fun quickSortMedianOfThree(array: IntArray): IntArray {
        if (array.size < 2) return array
        val pivot = medianOfThree(array)
        val less = array.filter { it < pivot }.toIntArray()
        val equal = array.filter { it == pivot }.toIntArray()
        val greater = array.filter { it > pivot }.toIntArray()
        return quickSortMedianOfThree(less) + equal + quickSortMedianOfThree(greater)
    }

    fun medianOfThree(array: IntArray): Int {
        val low = 0
        val high = array.size - 1
        val mid = (low + high) / 2
        if (array[low] > array[mid]) {
            swapArray(array, low, mid)
        }
        if (array[low] > array[high]) {
            swapArray(array, low, high)
        }
        if (array[mid] > array[high]) {
            swapArray(array, mid, high)
        }
        return array[mid]
    }

    fun quickSortRandomKey(array: IntArray): IntArray {
        if (array.size < 2) return array
        val pivot = array[Random().nextInt(0, array.size - 1)]
        val less = array.filter { it < pivot }.toIntArray()
        val equal = array.filter { it == pivot }.toIntArray()
        val greater = array.filter { it > pivot }.toIntArray()
        return quickSortRandomKey(less) + equal + quickSortRandomKey(greater)
    }

    fun quickSortHoare(array: IntArray, low: Int, high: Int): IntArray {
        if (array.size < 2) return array
        if (low < high) {
            /* pi is partitioning index,
            arr[p] is now at right place */
            val pi: Int = partitionHoare(array, low, high)
            // Separately sort elements before
            // partition and after partition
            quickSortHoare(array, low, pi)
            quickSortHoare(array, pi + 1, high)
        }
        return array
    }

    private fun partitionHoare(array: IntArray, low: Int, high: Int): Int {
        val pivot = array[low]
        var i = low - 1
        var j = high + 1
        while (true) {
            // Find leftmost element greater
            // than or equal to pivot
            do {
                i++
            } while (array[i] < pivot)
            // Find rightmost element smaller
            // than or equal to pivot
            do {
                j--
            } while (array[j] > pivot)

            // If two pointers met.
            if (i >= j) return j
            val temp = array[i]
            array[i] = array[j]
            array[j] = temp
            // swap(arr[i], arr[j]);
        }
    }

    fun quickSortLomuto(array: IntArray, low: Int, high: Int): IntArray {
        if (array.size < 2) return array
        if (low < high) {
            val pivot: Int = partitionLomuto(array, low, high)
            quickSortLomuto(array, low, pivot - 1)
            quickSortLomuto(array, pivot + 1, high)
        }
        return array
    }

    private fun partitionLomuto(array: IntArray, low: Int, high: Int): Int {
        val pivot = array[high]
        var i = low
        for (j in low until high) { // 3
            if (array[j] <= pivot) { // 4
                swapArray(array, i, j)
                i += 1
            }
        }
        swapArray(array, i, high) // 6
        return i

    }

}

fun main() {
    val size = 20
    val seed = 10000L
    val uniformArray = createArray(size, 50, Random(seed))
    val gaussArray = boxMullerRandomArray(size, Random(seed))
    println("Middle key uniform array: " + quickSortMiddleKey(uniformArray).contentToString())
    println("Middle key gauss distribution array: " + quickSortMiddleKey(gaussArray).contentToString())
    println("Second key uniform array: " + quickSortSecondKey(uniformArray).contentToString())
    println("Second key gauss distribution array: " + quickSortSecondKey(gaussArray).contentToString())
    println("Median of three key uniform array: " + quickSortMedianOfThree(uniformArray).contentToString())
    println("Median of three key gauss distribution array: " + quickSortMedianOfThree(gaussArray).contentToString())
    println("Random key uniform array: " + quickSortRandomKey(uniformArray).contentToString())
    println("Random key gauss distribution array: " + quickSortRandomKey(gaussArray).contentToString())
    println("Hoare uniform array: " + quickSortHoare(uniformArray, 0 , uniformArray.size-1).contentToString())
    println("Hoare gauss distribution array: " + quickSortHoare(gaussArray, 0 , uniformArray.size-1).contentToString())
    println("Lomuto uniform array: " + quickSortLomuto(uniformArray, 0 , uniformArray.size-1).contentToString())
    println("Lomuto gauss distribution array: " + quickSortLomuto(gaussArray, 0 , uniformArray.size-1).contentToString())

    printTime()
}

fun printTime() {
    val size = 1000
    val seed = 10000L
    val gaussArray = boxMullerRandomArray(size, Random(seed))

    val start1 = System.currentTimeMillis()
    quickSortMiddleKey(gaussArray)
    val end1 = System.currentTimeMillis()
    println("Middle key sort time: ${end1 - start1} ms")

    val start2 = System.currentTimeMillis()
    quickSortSecondKey(gaussArray)
    val end2 = System.currentTimeMillis()
    println("Second key sort time: ${end2 - start2} ms")

    val start3 = System.currentTimeMillis()
    quickSortMedianOfThree(gaussArray)
    val end3 = System.currentTimeMillis()
    println("Median of three sort time: ${end3 - start3} ms")

    val start4 = System.currentTimeMillis()
    quickSortRandomKey(gaussArray)
    val end4 = System.currentTimeMillis()
    println("Random key sort time: ${end4 - start4} ms")

    val start5 = System.currentTimeMillis()
    quickSortHoare(gaussArray, 0, gaussArray.size - 1)
    val end5 = System.currentTimeMillis()
    println("Hoare sort time: ${end5 - start5} ms")

    val start6 = System.currentTimeMillis()
    quickSortLomuto(gaussArray, 0, gaussArray.size - 1)
    val end6 = System.currentTimeMillis()
    println("Lomuto array sort time: ${end6 - start6} ms")
}