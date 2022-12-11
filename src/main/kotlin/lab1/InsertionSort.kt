package lab1

object InsertionSort {

    fun insertionSort(array: IntArray, left: Int, right: Int) {
        var counter = 0
        for (i in left..right) { // n times (n = right - left)
            val key = array[i] // n-1 times
            var j = i // n-1 times
            while (j > left && array[j - 1] > key) { // 1 time in sorted array, n(n+1)/2 times in reversed sorted array,
                counter++
                array[j] = array[j - 1]
                j--
            }
            array[j] = key // n-1 times
        }
        println(counter)
    }

}