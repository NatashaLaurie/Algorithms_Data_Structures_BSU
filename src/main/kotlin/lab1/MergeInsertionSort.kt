package lab1

import lab1.InsertionSort.insertionSort
import lab1.MergeInsertionSort.hybridMergeInsertionSort


object MergeInsertionSort {

    private const val THRESHOLD: Int = 16

    fun hybridMergeInsertionSort(array: IntArray, left: Int, right: Int) {
        if (right - left > THRESHOLD) {
            val mid = (left + right) / 2
            hybridMergeInsertionSort(array, left, mid)
            hybridMergeInsertionSort(array, mid + 1, right)
            merge(array, left, mid, right)
        } else {
            insertionSort(array, left, right)
        }
    }


    private fun merge(array: IntArray, left: Int, mid: Int, right: Int) {
        val leftArray = array.copyOfRange(left, mid + 1)
        val rightArray = array.copyOfRange(mid + 1, right + 1)
        var i = 0
        var j = 0
        var k = left
        while (j < leftArray.size && i < rightArray.size) {
            if (leftArray[j] <= rightArray[i]) {
                array[k] = leftArray[j]
                j++
            } else {
                array[k] = rightArray[i]
                i++
            }
            k++
        }
        while (j < leftArray.size) {
            array[k] = leftArray[j]
            j++
            k++
        }
        while (i < rightArray.size) {
            array[k] = rightArray[i]
            i++
            k++
        }

    }

}

fun main(args: Array<String>) {
    println("Enter arrays amount, arrays length and arrays max element:")
    val (arraysNumber, arraySize, maxElement) = readLine()!!.split(" ").map { it.toInt() }

    val array = ArrayHandler.createNRandomArrays(arraysNumber, arraySize, maxElement)
    array.forEach {
        hybridMergeInsertionSort(it, 0, it.size - 1)
    }
    ArrayHandler.printNArrays(array)
}