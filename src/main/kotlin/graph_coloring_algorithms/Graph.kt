package graph_coloring_algorithms

import kotlin.math.pow
import kotlin.math.sqrt

data class Graph(val vertices: Int, val edges: List<Pair<Int, Int>>) {
    val adjacencyList: List<MutableList<Int>> = List(vertices) { mutableListOf() }
    val coordinates: List<Pair<Double, Double>> = List(vertices) { generateRandomCoordinates() }

    init {
        edges.forEach { (u, v) ->
            adjacencyList[u].add(v)
            adjacencyList[v].add(u)
        }
    }
    fun getWeight(u: Int, v: Int): Double {
        val x1 = coordinates[u].first
        val y1 = coordinates[u].second
        val x2 = coordinates[v].first
        val y2 = coordinates[v].second

        return sqrt((x2 - x1).pow(2) + (y2 - y1).pow(2))
    }

    private fun generateRandomCoordinates(): Pair<Double, Double> {
        val x = Math.random() * 100
        val y = Math.random() * 100
        return x to y
    }

    companion object {
        fun printColoredGraph(graph: Graph, colors: List<Int>) {
            val colorMap = mapOf(
                0 to "red",
                1 to "blue",
                2 to "green",
                3 to "yellow",
                4 to "orange",
                5 to "pink"
            )

            println("Vertices:")
            for (vertex in 0 until graph.vertices) {
                println("$vertex [color=${colorMap[colors[vertex]]}]")
            }

            println("Edges:")
            for ((u, v) in graph.edges) {
                println("$u -- $v")
            }
        }
    }
}