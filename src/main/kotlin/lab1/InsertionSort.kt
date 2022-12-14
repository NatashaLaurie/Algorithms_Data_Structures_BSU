package lab1

object InsertionSort {

    fun insertionSort(array: IntArray, left: Int, right: Int) {
        var counter = 0
        for (i in left..right) { // n times (n = right - left)
            val key = array[i] // n-1 times
            counter+=2
            var j = i // n-1 times
            counter++
            while (j > left && array[j - 1] > key) { // 1 time in sorted array, n(n+1)/2 times in reversed sorted array,
                array[j] = array[j - 1]
                counter+=2
                j--
                counter+=2
            }
            array[j] = key // n-1 times
            counter+2
        }
        //println(counter)
    }

}