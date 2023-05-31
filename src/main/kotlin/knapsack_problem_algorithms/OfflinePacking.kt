package knapsack_problem_algorithms

data class PackingItem(val id: Int, val weight: Int)

data class Bin(val id: Int, val capacity: Int, val items: MutableList<PackingItem> = mutableListOf()) {
    fun remainingCapacity(): Int {
        return capacity - items.sumOf { it.weight }
    }
}

fun offlinePacking(items: List<PackingItem>, binCapacity: Int): List<Bin> {
    val bins = mutableListOf<Bin>()

    for (item in items) {
        var bestBin: Bin? = null
        var bestRemainingCapacity = Int.MAX_VALUE

        for (bin in bins) {
            val remainingCapacity = bin.remainingCapacity()
            if (remainingCapacity >= item.weight && remainingCapacity < bestRemainingCapacity) {
                bestBin = bin
                bestRemainingCapacity = remainingCapacity
            }
        }

        if (bestBin != null) {
            bestBin.items.add(item)
        } else {
            val newBin = Bin(bins.size + 1, binCapacity)
            newBin.items.add(item)
            bins.add(newBin)
        }
    }

    return bins
}

/*
       Bin 1:
       Item 1 - Weight: 5
       Item 2 - Weight: 3
       Item 4 - Weight: 2
       Remaining capacity: 0

       Bin 2:
       Item 3 - Weight: 7
       Item 8 - Weight: 3
       Remaining capacity: 0

       Bin 3:
       Item 5 - Weight: 4
       Item 6 - Weight: 6
       Remaining capacity: 0

       Bin 4:
       Item 7 - Weight: 5
       Remaining capacity: 5
 */
fun main() {
    val items = listOf(
        PackingItem(1, 5),
        PackingItem(2, 3),
        PackingItem(3, 7),
        PackingItem(4, 2),
        PackingItem(5, 4),
        PackingItem(6, 6),
        PackingItem(7, 5),
        PackingItem(8, 3)
    )

    val binCapacity = 10

    val packedBins1 = offlinePacking(items, binCapacity)

    for (bin in packedBins1) {
        println("Bin ${bin.id}:")
        for (item in bin.items) {
            println("  Item ${item.id} - Weight: ${item.weight}")
        }
        println("Remaining capacity: ${bin.remainingCapacity()}")
        println()
    }

}