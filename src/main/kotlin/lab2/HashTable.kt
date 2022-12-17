package lab2

class HashTable<K, V>(private val size: Int = 100) {

    var collisionCounterLinearProbing = 0
    var collisionCounterDoubleHash = 0

    private val array: Array<MutableList<Pair<K, V>>> = Array(size) { mutableListOf() }

    fun putOverflowChains(key: K, value: V) {
        val hashIndex = getHashIndex(key)
        val list = array[hashIndex]
        list.add(key to value)
    }

    fun getOverflowChains(key: K): V? {
        val hashIndex = getHashIndex(key)
        val list = array[hashIndex]
        return list.find { it.first == key }?.second
    }

    fun removeOverflowChains(key: K) {
        val hashIndex = getHashIndex(key)
        val list = array[hashIndex]
        val pair = list.find { it.first == key }
        if (pair != null) {
            list.remove(pair)
        }
    }

    fun getLinearProbing(key: K): V? {
        var index = getHashIndex(key)
        while (array[index].isNotEmpty()) {
            if (array[index].first().first == key) {
                return array[index].first().second
            } else {
                index++
                index %= size
            }

        }
        return null
    }

    fun putLinearProbing(key: K, value: V) {
        var index = getHashIndex(key)
        while (array[index].isNotEmpty()) {
            index++
            index %= size
            collisionCounterLinearProbing++
        }
        array[index].add(key to value)
    }

    private fun getHashIndex(key: K): Int {
        val hash = key.hashCode()
        return (hash * 0.6180339887 % 1 * size).toInt()
    }

    private fun getOtherHashIndex(key: Int): Int {
        return ((key % 7 + 1) + key) % size
    }

    fun getDoubleHashValue(key: K): V? {
        var index = getHashIndex(key)
        return if (array[index].first().first == key) {
            array[index].first().second
        } else {
            index = getOtherHashIndex(index)
            array[index].first().second
        }
    }

    fun putDoubleHash(key: K, value: V) {
        var index = getHashIndex(key)
        if (array[index].isNotEmpty()) {
            index = getOtherHashIndex(index)
            collisionCounterDoubleHash++
        }
        array[index].add(key to value)
    }

    fun print() {
        for (i in array.indices) {
            println("$i: ${array[i]}")
        }
    }


    fun printCollisions() {
        println(array.count { it.size > 1 })
    }

    fun printMaxCollisions() {
        println(array.maxOf { it.size })
    }

    fun printAverageCollisions() {
        println(array.sumOf { it.size } / array.size.toDouble())
    }

    fun printLoadFactor() {
        println(array.sumOf { it.size } / array.size.toDouble())
    }

    fun printEmptyCells() {
        println(array.count { it.isEmpty() })
    }

    fun putOverflowChainsExperiment(key: K, value: V, i: Double) {
        val hashIndex = getHashIndexExperiment(key, i)
        val list = array[hashIndex]
        val pair = list.find { it.first == key }
        if (pair != null) {
            list.remove(pair)
        }
        list.add(key to value)
    }

    private fun getHashIndexExperiment(key: K, i: Double): Int {
        val hash = key.hashCode()
        return (hash * (0.6180339887 + i) % 1 * size).toInt()
    }
}


private fun putValuesChain(a: Array<String>, htc: HashTable<String, Int>) {
    a.indices.forEach { i -> htc.putOverflowChains(a[i], i) }
}

private fun putValuesChainExperiment(a: Array<String>, htc: HashTable<String, Int>) {
    for (i in 0..30) {
        a.indices.forEach { j -> htc.putOverflowChainsExperiment(a[j], j, 0.00001 * i) }
        htc.printCollisions()
    }

}

private fun putValuesLinear(a: Array<String>, htl: HashTable<String, Int>) {
    a.indices.forEach { i -> htl.putLinearProbing(a[i], i) }
}

private fun putValuesDouble(a: Array<String>, htd: HashTable<String, Int>) {
    a.indices.forEach { i -> htd.putDoubleHash(a[i], i) }
}

private fun initAlphabetArray() = Array(26) { i -> (i + 97).toChar().toString() }

fun main() {
    val a: Array<String> = initAlphabetArray()
    val hashTableChain = HashTable<String, Int>(a.size)
    putValuesChain(a, hashTableChain)
    hashTableChain.print()
    println("collisions:")
    hashTableChain.printCollisions()
    println("find s ${hashTableChain.getOverflowChains("s")}")

    val hashTableLinear = HashTable<String, Int>(a.size)
    putValuesLinear(a, hashTableLinear)
    hashTableLinear.print()
    println("collisions: ${hashTableLinear.collisionCounterLinearProbing}")
    println("find y ${hashTableLinear.getLinearProbing("y")}")

    val hashTableDouble = HashTable<String, Int>(40)
    putValuesDouble(a, hashTableDouble)
    hashTableDouble.print()
    println("collisions: ${hashTableDouble.collisionCounterDoubleHash}")
    println("find y ${hashTableDouble.getDoubleHashValue("y")}")

    println("-----------ЭКСПЕРИМЕНТЫ)))--------------")
    val hashTableChainExperiments = HashTable<String, Int>(40)
    putValuesChainExperiment(a, hashTableChainExperiments)

}