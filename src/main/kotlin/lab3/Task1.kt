package lab3

import java.util.*

class Graph<T>(
    private val vertices: List<T>
) {
    val adjacencyList = vertices.associateWith { mutableListOf<T>() }.toMutableMap()
    private val edges = mutableListOf<Pair<T, T>>()

    fun addEdge(from: T, to: T) {
        if (from !in adjacencyList || to !in adjacencyList) {
            throw IllegalArgumentException("Vertex not found")
        }
        adjacencyList[from]?.add(to)
        edges.add(from to to)
    }


    fun printGraph() {
        for (vertex in adjacencyList.keys) {
            print("$vertex -> ")
            for (adjacentVertex in adjacencyList[vertex]!!) {
                print("$adjacentVertex ")
            }
            println()
        }
    }


    private fun getAdjacentVertices(vertex: T) = adjacencyList[vertex]!!

    fun getNumberOfVertices() = vertices.size

    fun getConnectivityComponents(): List<List<T>> {
        val components = mutableListOf<List<T>>()
        val visited = mutableListOf<T>()
        for (vertex in adjacencyList.keys) {
            if (vertex !in visited) {
                components.add(getConnectivityComponents(vertex, visited))
            }
        }
        return components
    }

    private fun getConnectivityComponents(vertex: T, visited: MutableList<T>): List<T> {
        visited.add(vertex)
        val connectedComponent = mutableListOf(vertex)
        for (adjacentVertex in getAdjacentVertices(vertex)) {
            if (adjacentVertex !in visited) {
                connectedComponent.addAll(getConnectivityComponents(adjacentVertex, visited))
            }
        }
        return connectedComponent
    }

    fun isEuler(): Boolean {
        var hasAllVerticesEvenDegree = true
        vertices.forEach {
            if (getAdjacentVertices(it).size % 2 != 0) hasAllVerticesEvenDegree = false
        }
        var connectivityComponentsWithEdges = 0
        getConnectivityComponents().forEach {
            if (it.size > 1) connectivityComponentsWithEdges++
        }

        return connectivityComponentsWithEdges <= 1 && hasAllVerticesEvenDegree
    }

    fun findEulerCycle(): List<T> {
        val cycle = mutableListOf<T>()
        val edges = edges.toMutableList()
        var vertex = vertices[0]
        cycle.add(vertex)
        while (edges.isNotEmpty()) {
            val nextVertex = edges.find { it.first == vertex }?.second
            if (nextVertex != null) {
                cycle.add(nextVertex)
                edges.remove(vertex to nextVertex)
                vertex = nextVertex
            } else {
                val prevVertex = edges.find { it.second == vertex }?.first
                if (prevVertex != null) {
                    cycle.add(prevVertex)
                    edges.remove(prevVertex to vertex)
                    vertex = prevVertex
                }
            }
        }
        return cycle
    }

    fun isBipartite(): Boolean {
        val visited = mutableMapOf<T, Boolean>()
        val queue = LinkedList<T>()
        for (vertex in adjacencyList.keys)
            if (vertex !in visited) {
                queue.add(vertex)
                visited[vertex] = true
                while (queue.isNotEmpty()) {
                    val currentVertex = queue.poll()
                    getAdjacentVertices(currentVertex).forEach { adjacentVertex ->
                        if (adjacentVertex !in visited) {
                            queue.add(adjacentVertex)
                            visited[adjacentVertex] = !visited[currentVertex]!!
                        } else if (visited[adjacentVertex] == visited[currentVertex]) {
                            return false
                        }
                    }
                }
            }
        return true
    }

    fun findParts(): List<List<T>> {
        val parts = mutableListOf<List<T>>()
        val visited = mutableMapOf<T, Boolean>()
        val queue = LinkedList<T>()
        val part1 = mutableListOf<T>()
        val part2 = mutableListOf<T>()
        for (vertex in adjacencyList.keys) {
            if (vertex !in visited) {
                queue.add(vertex)
                visited[vertex] = true
                while (queue.isNotEmpty()) {
                    val currentVertex = queue.poll()
                    for (adjacentVertex in getAdjacentVertices(currentVertex)) {
                        if (adjacentVertex !in visited) {
                            queue.add(adjacentVertex)
                            visited[adjacentVertex] = !visited[currentVertex]!!
                        }
                    }
                }
            }
        }
        visited.forEach { (t, u) ->  if (!u) {
            part1.add(t)
        } else part2.add(t)}
        parts.add(part1)
        parts.add(part2)
        return parts
    }
}


private fun initRandomEdges(randomVertices: List<Int>, graph: Graph<Int>) {
    for (i in 0..50) {
        val vertex1 = randomVertices.random()
        val vertex2 = randomVertices.random()
        graph.addEdge(vertex1, vertex2)
    }
}

fun main() {
    val vertices = List(20) { it + 1 }
    val graph = Graph(vertices)
    initRandomEdges(vertices, graph)
    graph.printGraph()
    println("Connectivity components: " + graph.getConnectivityComponents())
    println("Is Eulerian: " + graph.isEuler())
    if (graph.isEuler()) {
        println("Eulerian cycle: " + graph.findEulerCycle())
    }
    println("Is Bipartite: " + graph.isBipartite())
    if (graph.isBipartite()) {
        println("Fractions: " + graph.findParts())
    }

    println()

    //Eulerian graph
    val vertices2 = List(5) { it + 1 }
    val graph2 = Graph(vertices2)
    graph2.addEdge(1, 2)
    graph2.addEdge(2, 3)
    graph2.addEdge(3, 4)
    graph2.addEdge(4, 5)
    graph2.addEdge(5, 1)
    graph2.addEdge(1, 3)
    graph2.addEdge(3, 5)
    graph2.addEdge(5, 2)
    graph2.addEdge(2, 4)
    graph2.addEdge(4, 1)
    graph2.printGraph()
    println("Is Eulerian: " + graph2.isEuler())
    if (graph2.isEuler()) {
        println("Eulerian cycle: " + graph2.findEulerCycle())
    }

    //Bipartite graph1
    val verticesBipartite = List(4) { it + 1 }
    val graph3 = Graph(verticesBipartite)
    graph3.addEdge(1, 3)
    graph3.addEdge(1, 4)
    graph3.addEdge(2, 3)
    graph3.addEdge(2, 4)
    graph3.printGraph()
    println("Is Bipartite: " + graph3.isBipartite())
    if (graph3.isBipartite()) {
        println("Fractions: " + graph3.findParts())
    }

    //Bipartite graph2
    val verticesBipartite2 = List(9) { it + 1 }
    val graph4 = Graph(verticesBipartite2)
    graph4.addEdge(1, 2)
    graph4.addEdge(2, 3)
    graph4.addEdge(3, 4)
    graph4.addEdge(4, 1)
    graph4.addEdge(3, 5)
    graph4.addEdge(5, 6)
    graph4.addEdge(5, 7)
    graph4.addEdge(5, 8)
    graph4.addEdge(8, 9)
    graph4.printGraph()
    println("Is Bipartite: " + graph4.isBipartite())
    if (graph4.isBipartite()) {
        println("Fractions: " + graph4.findParts())
    }
}