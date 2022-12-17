package lab2

class Node(
    var value: Int,
    var leftChild: Node? = null,
    var rightChild: Node? = null
)

private class BinaryTree {
    private var root: Node? = null

    fun insert(value: Int) {
        root = insert(root, value)
    }

    private fun insert(node: Node?, value: Int): Node {
        node ?: return Node(value)
        if (value < node.value) {
            node.leftChild = insert(node.leftChild, value)
        } else {
            node.rightChild = insert(node.rightChild, value)
        }
        return node
    }

    // Функция печатает значения бинарного дерева поиска в симметричном порядке.
    // То есть в отсортированном порядке
    fun traverseInOrder(): List<Int> {
        return mutableListOf<Int>().apply { traverseInOrder(root, this) }
    }

    private fun traverseInOrder(node: Node?, nodes: MutableList<Int>) {
        if (node != null) {
            traverseInOrder(node.leftChild, nodes)
            nodes.add(node.value)
            traverseInOrder(node.rightChild, nodes)
        }
    }

    // Функция печатает значения бинарного дерева поиска в обратном симметричном порядке.
    // То есть в обратном отсортированном порядке
    fun reverseTraverseInOrder(): List<Int> {
        return mutableListOf<Int>().apply { reverseTraverseInOrder(root, this) }
    }

    private fun reverseTraverseInOrder(node: Node?, nodes: MutableList<Int>) {
        if (node != null) {
            reverseTraverseInOrder(node.rightChild, nodes)
            nodes.add(node.value)
            reverseTraverseInOrder(node.leftChild, nodes)
        }
    }

    // Функция печатает значения бинарного дерева поиска в прямом порядке.
    fun traversePreOrder(): List<Int> {
        return mutableListOf<Int>().apply { traversePreOrder(root, this) }
    }

    private  fun traversePreOrder(node: Node?, nodes: MutableList<Int>) {
        if (node != null) {
            nodes.add(node.value)
            traversePreOrder(node.leftChild, nodes)
            traversePreOrder(node.rightChild, nodes)
        }
    }

    // Функция печатает значения бинарного дерева поиска в обратном порядке.
    fun traversePostOrder(): List<Int> {
        return mutableListOf<Int>().apply { traversePostOrder(root, this) }
    }

    private fun traversePostOrder(node: Node?, nodes: MutableList<Int>) {
        if (node != null) {
            traversePostOrder(node.leftChild, nodes)
            traversePostOrder(node.rightChild, nodes)
            nodes.add(node.value)
        }
    }

    fun balance() {
        val list = this.traverseInOrder()
        this.root = null
        this.balance(list)
    }

    private fun balance(list: List<Int>) {
        if (list.isEmpty()) return
        val middle = list.size / 2
        this.insert(list[middle])
        this.balance(list.subList(0, middle))
        this.balance(list.subList(middle + 1, list.size))
    }

    // нахождение k-го минимального ключа в дереве
    fun findKMin(k: Int): Int {
        val list = this.traverseInOrder()
        return list[k - 1]
    }


    fun printTree() {
        printTree(root, "", true)
    }

    fun printTree(current: Node?, prefix: String, isLeft: Boolean) {
        if (current != null) {
            printTree(current.rightChild, "$prefix${if (isLeft) "│   " else "    "}", false)
            println("$prefix${if (isLeft) "└── " else "┌── "}${current.value}")
            printTree(current.leftChild, "$prefix${if (isLeft) "    " else "│   "}", true)
        }
    }
}

fun main() {
    val tree = BinaryTree()
    val size = 20
    val maxElement = 20
    repeat((0 until size).count()) { tree.insert((0..maxElement).random()) }
    tree.printTree()
    println()
    println("Traverse in order: " + tree.traverseInOrder())
    println("Traverse in order reverse: " + tree.reverseTraverseInOrder())
    println("Traverse pre order: " + tree.traversePreOrder())
    println("Traverse post order: " + tree.traversePostOrder())
    println("Second min key: " + tree.findKMin(5))
    println()
    println("After balancing:")
    tree.balance()
    tree.printTree()
}