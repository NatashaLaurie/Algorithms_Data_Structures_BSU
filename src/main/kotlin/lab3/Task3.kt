package lab3

import java.util.*

class WeightedGraph(private val vertices: Int) {
    var edges: Int = 0
    private val adj: Array<LinkedList<Edge>> = Array(vertices) { LinkedList() }

    fun vertices() = vertices

    class Edge(val u: Int, val v: Int, val weight: Int) : Comparable<Edge> {
        override fun compareTo(other: Edge): Int {
            return this.weight.compareTo(other.weight)
        }

        fun other(s: Int): Int {
            if (s == u) return v
            if (s == v) return u
            throw IllegalArgumentException()
        }
    }

    fun addEdge(v: Int, w: Int, weight: Int) {
        val edge = Edge(v, w, weight)
        adj[v].add(edge)
        adj[w].add(edge)
        edges++
    }

    fun adjacentEdges(v: Int): Queue<Edge> {
        return adj[v]
    }

    fun print() {
        println("$vertices vertices, $edges edges")
        for (v in 0 until vertices) {
            print("$v: ")
            for (e in adj[v]) {
                print("${e.other(v)}(${e.weight}) ")
            }
            println()
        }
    }
}

class Kruskal(G: WeightedGraph) {
    private var weight: Double = 0.0
    private var edges: Queue<WeightedGraph.Edge> = LinkedList()

    init {
        val pq = PriorityQueue<WeightedGraph.Edge>(G.vertices(), compareBy { it.weight })
        for (v in 0 until G.vertices()) {
            for (e in G.adjacentEdges(v)) {
                pq.add(e)
            }
        }

        val set = DisjointSet(G.vertices())
        while (!pq.isEmpty()) {
            val edge = pq.poll()
            if (!set.connected(edge.u, edge.v)) {
                edges.add(edge)
                set.union(edge.u, edge.v)
                weight += edge.weight
            }
        }
    }

    fun print() {
        println("Edge\tWeight")
        for (e in edges) {
            println("${e.u} - ${e.v}\t${e.weight}")
        }
        println("Weight: $weight")
    }
}

class DisjointSet(val size: Int) {
    private val parent = IntArray(size)
    private val rank = ByteArray(size)
    private var count = size

    init {
        for (i in parent.indices) {
            parent[i] = i
        }
    }

    fun connected(v: Int, w: Int): Boolean {
        return find(v) == find(w)
    }

    private fun find(v: Int): Int {
        var v = v
        while (parent[v] != v) {
            parent[v] = parent[parent[v]]
            v = parent[v]
        }
        return v
    }

    fun union(v: Int, w: Int) {
        val rootV = find(v)
        val rootW = find(w)
        if (rootV == rootW) return
        if (rank[rootV] > rank[rootW]) {
            parent[rootW] = rootV
        } else if (rank[rootW] > rank[rootV]) {
            parent[rootV] = rootW
        } else {
            parent[rootV] = rootW
            rank[rootW]++
        }
        count--
    }
}

fun main() {
    val G = WeightedGraph(17)
    G.addEdge(1, 2, 3)
    G.addEdge(1, 6,1)
    G.addEdge(2, 5, 3)
    G.addEdge(2, 12, 4)
    G.addEdge(2, 13, 2)
    G.addEdge(2, 14, 5)
    G.addEdge(3, 9, 4)
    G.addEdge(3, 12, 1)
    G.addEdge(4, 9, 4)
    G.addEdge(4, 14, 2)
    G.addEdge(5, 6, 2)
    G.addEdge(5, 11, 4)
    G.addEdge(5, 16, 2)
    G.addEdge(7, 8, 3)
    G.addEdge(7, 13, 2)
    G.addEdge(8, 14, 2)
    G.addEdge(9, 12, 3)
    G.addEdge(9, 14, 4)
    G.addEdge(10, 14, 5)
    G.addEdge(11, 13, 4)
    G.addEdge(14, 15, 6)
    G.print()
    println()

    val minSpanningTree = Kruskal(G)
    minSpanningTree.print()
}