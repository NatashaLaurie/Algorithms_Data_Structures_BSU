package lab1

import lab1.MergeInsertionSort.counterInsertion
import lab1.MergeInsertionSort.counterMerge
import lab1.MergeInsertionSort.hybridMergeInsertionSort
import java.util.*


object MergeInsertionSort {

    private const val THRESHOLD: Int = 16
    var counterInsertion = 0;
    var counterMerge = 0;

    fun hybridMergeInsertionSort(array: IntArray, left: Int, right: Int) {
        if (right - left > THRESHOLD) {
            val mid = (left + right) / 2
            counterMerge++
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
        for (k in left..right){
            counterMerge++
            if ( (i <= leftArray.size - 1) && ( (j >= rightArray.size) || (leftArray[i] <= rightArray[j]) ) ){
                array[k] = leftArray[i]
                i++
            }else {
                array[k] = rightArray[j]
                j++
            }
        }

    }
    private fun insertionSort(array: IntArray, left: Int, right: Int): Int {
        for (i in left..right) { // n times (n = right - left)
            val key = array[i] // n-1 times
            counterInsertion +=2
            var j = i // n-1 times
            counterInsertion++
            while (j > left && array[j - 1] > key) { // 1 time in sorted array, n(n+1)/2 times in reversed sorted array,
                array[j] = array[j - 1]
                counterInsertion +=2
                j--
                counterInsertion +=2
            }
            array[j] = key // n-1 times
            counterInsertion +=2
        }
        return counterInsertion
    }

}

fun main(args: Array<String>) {
    println("Enter arrays amount, arrays length and arrays max element:")
    val (arraysNumber, arraySize, maxElement) = readLine()!!.split(" ").map { it.toInt() }

    val array = ArrayHandler.createNRandomArrays(arraysNumber, arraySize, maxElement, Random(1000L))
    val start1 = System.currentTimeMillis()
    array.forEach {
        hybridMergeInsertionSort(it, 0, it.size - 1)
    }
    ArrayHandler.printNArrays(array)
    val end1 = System.currentTimeMillis()
    println("Middle key sort time: ${end1 - start1} ms")
    println("number of elementary operations: $counterMerge")
}