package graph_coloring_algorithms

import kotlin.math.pow
import kotlin.math.sqrt

/**
 *The Christofides algorithm is expected to provide a better approximation to the optimal
 * path length than the 2-replacement algorithm, but the actual results may vary depending
 * on the specific graph generated.
 */

class Christofides(private val graph: Graph) {
    private val mst: Graph
    private val oddDegreeVertices: List<Int>
    private val completeGraph: Graph
    private val minMatching: Graph
    private val eulerianCircuit: List<Int>
    private val hamiltonianPath: List<Int>

    init {
        mst = computeMinimumSpanningTree()
        oddDegreeVertices = findOddDegreeVertices()
        completeGraph = constructCompleteGraph()
        minMatching = computeMinimumMatching()
        eulerianCircuit = computeEulerianCircuit()
        hamiltonianPath = computeHamiltonianPath()
    }

    private fun computeMinimumSpanningTree(): Graph {
        val mstEdges = mutableListOf<Pair<Int, Int>>()
        val visited = BooleanArray(graph.vertices)
        val key = DoubleArray(graph.vertices) { Double.POSITIVE_INFINITY }
        val parent = IntArray(graph.vertices) { -1 }

        key[0] = 0.0

        for (i in 0 until graph.vertices - 1) {
            val u = minKey(key, visited)
            visited[u] = true

            for (v in 0 until graph.vertices) {
                val weight = graph.getWeight(u, v)
                if (weight != 0.0 && !visited[v] && weight < key[v]) {
                    parent[v] = u
                    key[v] = weight
                }
            }
        }

        for (v in 1 until graph.vertices) {
            mstEdges.add(parent[v] to v)
        }

        return Graph(graph.vertices, mstEdges)
    }

    private fun minKey(key: DoubleArray, visited: BooleanArray): Int {
        var min = Double.POSITIVE_INFINITY
        var minIndex = -1

        for (v in 0 until graph.vertices) {
            if (!visited[v] && key[v] < min) {
                min = key[v]
                minIndex = v
            }
        }

        return minIndex
    }

    private fun findOddDegreeVertices(): List<Int> {
        val degrees = MutableList(graph.vertices) { 0 }

        for (edge in mst.edges) {
            degrees[edge.first]++
            degrees[edge.second]++
        }

        return degrees.mapIndexedNotNull { index, degree ->
            if (degree % 2 != 0) index else null
        }
    }

    private fun constructCompleteGraph(): Graph {
        val completeEdges = mutableListOf<Pair<Int, Int>>()

        for (i in oddDegreeVertices.indices) {
            for (j in i + 1 until oddDegreeVertices.size) {
                val u = oddDegreeVertices[i]
                val v = oddDegreeVertices[j]
                completeEdges.add(u to v)
            }
        }

        return Graph(graph.vertices, completeEdges)
    }

    fun computeMinimumMatching(): Graph {
        val matchingEdges = mutableListOf<Pair<Int, Int>>()
        val visited = BooleanArray(completeGraph.vertices)
        val pairU = IntArray(completeGraph.vertices) { -1 }
        val pairV = IntArray(completeGraph.vertices) { -1 }

        for (u in 0 until completeGraph.vertices) {
            if (pairU[u] == -1) {
                for (v in 0 until completeGraph.vertices) {
                    if (!visited[v] && completeGraph.getWeight(u, v) != 0.0) {
                        visited[v] = true

                        if (pairV[v] == -1 || findAugmentingPath(pairV[v], visited, pairU, pairV)) {
                            pairU[u] = v
                            pairV[v] = u
                            break
                        }
                    }
                }

                visited.fill(false)
            }
        }

        for (u in 0 until completeGraph.vertices) {
            if (pairU[u] != -1) {
                matchingEdges.add(u to pairU[u])
            }
        }

        return Graph(completeGraph.vertices, matchingEdges)
    }

    private fun findAugmentingPath(v: Int, visited: BooleanArray, pairU: IntArray, pairV: IntArray): Boolean {
        visited[v] = true

        for (u in 0 until completeGraph.vertices) {
            if (!visited[u] && completeGraph.getWeight(v, u) != 0.0) {
                visited[u] = true

                if (pairU[u] == -1 || findAugmentingPath(pairU[u], visited, pairU, pairV)) {
                    pairU[u] = v
                    pairV[v] = u
                    return true
                }
            }
        }

        return false
    }

    private fun computeEulerianCircuit(): List<Int> {
        val circuit = mutableListOf<Int>()
        val graphCopy = minMatching.copy()

        // Select an arbitrary starting vertex
        var currentVertex = 0

        while (graphCopy.adjacencyList[currentVertex].isNotEmpty()) {
            val nextVertex = graphCopy.adjacencyList[currentVertex].removeAt(0)
            graphCopy.adjacencyList[nextVertex].remove(currentVertex)
            circuit.add(currentVertex)
            currentVertex = nextVertex
        }

        // Add the last vertex to close the circuit
        circuit.add(currentVertex)

        return circuit
    }

    private fun computeHamiltonianPath(): List<Int> {
        val visited = BooleanArray(graph.vertices)
        val path = mutableListOf<Int>()

        for (vertex in eulerianCircuit) {
            if (!visited[vertex]) {
                visited[vertex] = true
                path.add(vertex)
            }
        }

        return path
    }

    private fun computeDistance(u: Int, v: Int): Double {
        val x1 = graph.coordinates[u].first
        val y1 = graph.coordinates[u].second
        val x2 = graph.coordinates[v].first
        val y2 = graph.coordinates[v].second

        return sqrt((x2 - x1).pow(2) + (y2 - y1).pow(2))
    }

    fun getApproximatePath(): List<Int> {
        return hamiltonianPath
    }

    private fun calculatePathWeight(path: List<Int>): Double {
        var weight = 0.0
        for (i in 0 until path.size - 1) {
            weight += computeDistance(path[i], path[i + 1])
        }
        weight += computeDistance(path.last(), path.first())
        return weight
    }

    fun getApproximatePathWeight(): Double {
        return calculatePathWeight(hamiltonianPath)
    }
}

/*
    Approximate Path: [0, 1, 6, 4, 3]
    Approximate Weight: 201.29077154201644
 */

fun main() {
    val edges = listOf(
        Pair(0, 5),
        Pair(0, 5),
        Pair(0, 7),
        Pair(2, 1),
        Pair(2, 5),
        Pair(2, 7),
        Pair(4, 1),
        Pair(4, 3),
        Pair(4, 7),
        Pair(6, 1),
        Pair(6, 3),
        Pair(6, 5)

    )

    val graph = Graph(8, edges)

    val christofides = Christofides(graph)
    val approximatePath = christofides.getApproximatePath()
    val approximateWeight = christofides.getApproximatePathWeight()

    println("Approximate Path: $approximatePath")
    println("Approximate Weight: $approximateWeight")
}



