package graph_coloring_algorithms

/**
 * DSatur (Maximum Degree + Saturation Degree) algorithm for graph coloring
 */

class DSatur(private val graph: Graph) {
    private val colors: MutableList<Int> = MutableList(graph.vertices) { -1 }
    private val saturation: MutableList<Int> = MutableList(graph.vertices) { 0 }
    private val degree: MutableList<Int> = MutableList(graph.vertices) { 0 }
    private var maxSaturationVertex: Int = 0

    init {
        computeSaturationAndDegree()
    }

    private fun computeSaturationAndDegree() {
        for (vertex in 0 until graph.vertices) {
            saturation[vertex] = getSaturation(vertex)
            degree[vertex] = getDegree(vertex)

            if (saturation[vertex] > saturation[maxSaturationVertex]) {
                maxSaturationVertex = vertex
            }
        }
    }

    private fun getSaturation(vertex: Int): Int {
        val usedColors = mutableSetOf<Int>()
        graph.adjacencyList[vertex].forEach { neighbor ->
            if (colors[neighbor] != -1) {
                usedColors.add(colors[neighbor])
            }
        }
        return usedColors.size
    }

    private fun getDegree(vertex: Int): Int {
        return graph.adjacencyList[vertex].size
    }

    fun colorGraph(): List<Int> {
        colors[maxSaturationVertex] = 0

        for (i in 1 until graph.vertices) {
            val availableColors = mutableSetOf<Int>()

            graph.adjacencyList[maxSaturationVertex].forEach { neighbor ->
                if (colors[neighbor] != -1) {
                    availableColors.add(colors[neighbor])
                }
            }

            var color = 0
            while (availableColors.contains(color)) {
                color++
            }

            colors[maxSaturationVertex] = color

            graph.adjacencyList[maxSaturationVertex].forEach { neighbor ->
                saturation[neighbor] = getSaturation(neighbor)
                degree[neighbor] = getDegree(neighbor)
            }

            maxSaturationVertex = saturation.indices
                .filter { colors[it] == -1 }
                .maxByOrNull { saturation[it] }
                ?: break
        }

        return colors
    }
}

/**
 * DSatur coloring:
    Vertices:
    0 [color=red]
    1 [color=blue]
    2 [color=red]
    3 [color=blue]
    4 [color=red]
    5 [color=blue]
    6 [color=red]
    7 [color=blue]

    Edges:
    0 -- 5
    0 -- 5
    0 -- 7
    2 -- 1
    2 -- 5
    2 -- 7
    4 -- 1
    4 -- 3
    4 -- 7
    6 -- 1
    6 -- 3
    6 -- 5
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
    val colors = DSatur(graph)

    println("DSatur coloring:")
    Graph.printColoredGraph(graph, colors.colorGraph())
}