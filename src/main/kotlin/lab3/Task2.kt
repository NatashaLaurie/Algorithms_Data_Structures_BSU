package lab3

import java.lang.Integer.min

class FloydWarshall(
    private var adjacencyMatrix: Array<IntArray>,
) {
    private var referenceMatrix: Array<IntArray> = Array(adjacencyMatrix.size) { IntArray(adjacencyMatrix.size) }

    init {
        for (i in adjacencyMatrix.indices) {
            for (j in adjacencyMatrix.indices) {
                referenceMatrix[i][j] = j + 1
            }
        }
    }

    fun findShortestPathsFloydWarshall() {
        println()
        println("L0:")
        printMatrix(adjacencyMatrix)
        println("S0:")
        printMatrix(referenceMatrix)
        for (k in adjacencyMatrix.indices) {
            println()
            println("-----------------------------------")
            if (checkNegativeLengthCircles()) {
            for (i in adjacencyMatrix.indices) {
                for (j in adjacencyMatrix.indices) {
                    if (adjacencyMatrix[i][k] < inf && adjacencyMatrix[k][j] < inf) {
                        adjacencyMatrix[i][j] = min(adjacencyMatrix[i][j], adjacencyMatrix[i][k] + adjacencyMatrix[k][j])
                        referenceMatrix[i][j] = referenceMatrix[i][k]
                    }
                }
            }
            println("L${k + 1}:")
            printMatrix(adjacencyMatrix)
            println("S${k + 1}")
            printMatrix(referenceMatrix)
            } else throw IllegalStateException("There are circle of negative length, algorithm is useless")
        }
    }
    private fun checkNegativeLengthCircles(): Boolean {
        for (i in adjacencyMatrix.indices) {
            if ( adjacencyMatrix[i][i] < 0) return false
        }
        return true
    }

    private fun printMatrix(matrix: Array<IntArray>) {
        for (i in matrix.indices) {
            print("[ ")
            for (j in matrix.indices) {
                if (matrix[i][j] == inf) {
                    print("inf")
                } else {
                    print(String.format("%2d", matrix[i][j]))
                }
                if (j != matrix.size - 1) {
                    print(", ")
                }
            }
            println(" ]")
        }
        println()
    }

    fun findVertexWithShortestPath(): Int {
        var min = inf
        var vertex = 0
        adjacencyMatrix.indices.forEach { i ->
            val sum = adjacencyMatrix.indices.sumOf { adjacencyMatrix[i][it] }
            if (sum < min) {
                min = sum
                vertex = i
            }
        }
        return vertex
    }
}


private fun generateRandomAdjacencyMatrix(size: Int): Array<IntArray> {
    val matrix = Array(size) { IntArray(size) }
    for (i in matrix.indices) {
        for (j in matrix.indices) {
            if (i == j) {
                matrix[i][j] = inf
            } else {
                val random = (0..10).random()
                if (random == 0) {
                    matrix[i][j] = inf

                } else matrix[i][j] = (-1..9).random() }
        }
    }
    return matrix
}

private const val inf = 999999

fun main() {
    val floydWarshall = FloydWarshall(generateRandomAdjacencyMatrix(5))
    floydWarshall.findShortestPathsFloydWarshall()
    println("Fire station point at: ${floydWarshall.findVertexWithShortestPath()}")

}