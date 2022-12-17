package lab2

import lab1.ArrayHandler.createArray
import lab2.Search.interpolationSearch
import lab2.Search.search
import java.util.*

object Search {

    fun search(array: IntArray, target: Int): Int {
        var low = 0
        var high = array.size - 1
        var mid: Int
        var compareOperations = 0
        while (low <= high) {
            mid = (low + high) / 2
            if (array[mid] == target) {
                compareOperations++
                println("Number of compare operations: $compareOperations")
                return mid
            }
            if (array[mid] > target) {
                high = mid - 1
                compareOperations++
            } else {
                compareOperations++
                low = mid + 1
            }
        }
        println("Number of compare operations: $compareOperations")
        return -1
    }


    // useful))000
    fun searchKotlin(nums: IntArray, target: Int): Int {
        return nums.binarySearch(target)
    }

    fun interpolationSearch(array: IntArray, target: Int): Int {
        var left = 0
        var right = array.size - 1
        var compareOperations = 0
        while (left <= right) {
            compareOperations++
            val middle = left + (target - array[left]) * (right - left) / (array[right] - array[left])
            if (array[middle] == target) {
                compareOperations++
                println("Number of compare operations: $compareOperations")
                return middle
            } else if (array[middle] < target) {
                compareOperations++
                left = middle + 1
            } else {
                compareOperations++
                right = middle - 1
            }
        }
        println("Number of compare operations: $compareOperations")
        return -1
    }

}

fun main() {
    val array: IntArray = createArray(100, 50, Random(1000L)).sortedArray()
    val target = array.random()
    println("binary search")
    println("Random array: ${array.joinToString()}")
    println("target: $target")
    val binarySearch = search(array, target)

    println("interpolation search")
    val interpolationSearch = interpolationSearch(array, target)
}