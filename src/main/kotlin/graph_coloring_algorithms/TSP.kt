package graph_coloring_algorithms
import kotlin.math.sqrt

/**
 * Two-Opt algorithm for the Traveling Salesman Problem (TSP)
 */

data class City(val x: Double, val y: Double)

fun calculateDistance(city1: City, city2: City): Double {
    val deltaX = city1.x - city2.x
    val deltaY = city1.y - city2.y
    return sqrt(deltaX * deltaX + deltaY * deltaY)
}

fun calculateTourDistance(tour: List<City>): Double {
    var distance = 0.0
    for (i in 0 until tour.size - 1) {
        distance += calculateDistance(tour[i], tour[i + 1])
    }
    return distance + calculateDistance(tour[tour.size - 1], tour[0])
}

fun twoOptAlgorithm(tour: MutableList<City>) {
    var improvement = true
    while (improvement) {
        improvement = false
        for (i in 0 until tour.size - 2) {
            for (j in i + 2 until tour.size - 1) {
                val currentDistance = calculateDistance(tour[i], tour[i + 1]) +
                        calculateDistance(tour[j], tour[j + 1])
                val newDistance = calculateDistance(tour[i], tour[j]) +
                        calculateDistance(tour[i + 1], tour[j + 1])

                if (newDistance < currentDistance) {
                    tour.subList(i + 1, j + 1).reverse()
                    improvement = true
                }
            }
        }
    }
}

/*
    Output:
    Initial tour: [City(x=0.0, y=0.0), City(x=1.0, y=1.0),
                   City(x=2.0, y=2.0), City(x=3.0, y=3.0),
                   City(x=4.0, y=4.0), City(x=1.0, y=1.0),
                   City(x=2.0, y=2.0), City(x=3.0, y=3.0),
                   City(x=4.0, y=4.0)]
    Tour Distance: 19.798989873223334


    Improved Tour: [City(x=0.0, y=0.0), City(x=1.0, y=1.0),
                    City(x=1.0, y=1.0), City(x=2.0, y=2.0),
                    City(x=2.0, y=2.0), City(x=3.0, y=3.0),
                    City(x=3.0, y=3.0), City(x=4.0, y=4.0),
                    City(x=4.0, y=4.0)]
    Tour Distance: 11.313708498984761


 */

fun main() {
    // Create a list of cities
    val cities = listOf(
        City(0.0, 0.0),
        City(1.0, 1.0),
        City(2.0, 2.0),
        City(3.0, 3.0),
        City(4.0, 4.0),
        City(1.0, 1.0),
        City(2.0, 2.0),
        City(3.0, 3.0),
        City(4.0, 4.0),

    )

    // Generate an initial tour
    val initialTour = cities.toMutableList()
    println("Initial tour: $initialTour")
    println("Tour Distance: ${calculateTourDistance(initialTour)}")

    // Run the Two-Opt algorithm to improve the tour
    twoOptAlgorithm(initialTour)

    // Calculate the distance of the improved tour
    val tourDistance = calculateTourDistance(initialTour)

    // Print the improved tour and its distance
    println("Improved Tour: $initialTour")
    println("Tour Distance: $tourDistance")
}
