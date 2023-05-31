package knapsack_problem_algorithms

import java.lang.Integer.max

data class KnapsackItem(val weight: Int, val cost: Int)

fun knapsack(items: Array<KnapsackItem>, capacity: Int): Int {
    val n = items.size
    val dp = Array(n + 1) { IntArray(capacity + 1) }

    for (i in 1..n) {
        for (j in 1 until capacity + 1) {
            if (items[i - 1].weight > j) {
                dp[i][j] = dp[i - 1][j]
            } else {
                dp[i][j] = max(
                    dp[i - 1][j],
                    dp[i - 1][j - items[i - 1].weight] + items[i - 1].cost
                )
            }
        }
    }
    // Print dynamic programming table like КМиСО
    println("Dynamic Programming Table:")
    for (i in 0..n) {
        for (j in 0..capacity) {
            print("${dp[i][j]}\t")
        }
        println()
    }

    return dp[n][capacity]
}

/*
Dynamic Programming Table like on КМиСО:
0	0	0	0	0	0
0	6	6	6	6	6
0	6	10	16	16	16
0	6	10	16	18	22
Maximum total value: 22
 */

fun main() {
    val items = arrayOf(
        KnapsackItem(1, 6),
        KnapsackItem(2, 10),
        KnapsackItem(3, 12)
    )
    val capacity = 5

    val maxTotalValue = knapsack(items, capacity)
    println("Maximum total value: $maxTotalValue")
}