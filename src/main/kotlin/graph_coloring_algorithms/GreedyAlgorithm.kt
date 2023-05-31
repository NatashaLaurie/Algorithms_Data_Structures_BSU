package graph_coloring_algorithms

/**
 * Greedy coloring algorithm
 */

fun greedyColoring(graph: Graph): List<Int> {
    val colors = MutableList(graph.vertices) { -1 }
    colors[0] = 0

    val availableColors = BooleanArray(graph.vertices)

    for (vertex in 1 until graph.vertices) {
        graph.adjacencyList[vertex].forEach { neighbor ->
            if (colors[neighbor] != -1) {
                availableColors[colors[neighbor]] = true
            }
        }

        var color = 0
        while (color < graph.vertices && availableColors[color]) color++

        colors[vertex] = color

        graph.adjacencyList[vertex].forEach { neighbor ->
            if (colors[neighbor] != -1) availableColors[colors[neighbor]] = false
        }
    }

    return colors
}

/*
    Greedy coloring:
    Vertices:
    0 [color=red]
    1 [color=red]
    2 [color=blue]
    3 [color=red]
    4 [color=blue]
    5 [color=green]
    6 [color=blue]
    7 [color=green]

    Edges:
    0 -- 3
    0 -- 5
    0 -- 7
    2 -- 1
    2 -- 5
    2 -- 7
    4 -- 1
    4 -- 3
    4 -- 7
    7 -- 1
    7 -- 3
    7 -- 5
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
    val colors = greedyColoring(graph)

    println("Greedy coloring:")
    Graph.printColoredGraph(graph, colors)
}