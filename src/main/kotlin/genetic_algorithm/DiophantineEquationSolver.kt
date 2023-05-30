package genetic_algorithm

import kotlin.math.abs
import kotlin.math.pow
import kotlin.random.Random


class Probability {
    companion object {
        private val rand = Random(System.currentTimeMillis())

        fun comeTrue(probability: Double): Boolean {
            return rand.nextDouble() < probability
        }

        fun shouldPerform(): Boolean {
            return rand.nextBoolean()
        }

    }
}


class DiophantineEquationSolver(private val powers: IntArray, private val result: Int) {
    companion object {
        private const val minValue = -300
        private const val maxValue = 300
        private const val populationCount = 5
        private const val mutationProbability1 = 0.2
        private const val mutationProbability2 = 0.6
    }

    fun initializePopulation(): List<Individual> {
        val individuals = mutableListOf<Individual>()
        for (i in 0 until populationCount) {
            individuals.add(Individual.generate(minValue, maxValue))
        }
        return individuals
    }



    fun substitution(individuals: List<Individual>): List<Individual> {
        val selectedIndividuals = performFitnessProportionateSelection(individuals)
        val children = crossbreeding(selectedIndividuals)
        val mutatedChildren = performMutation(children, mutationProbability1, mutationProbability2)
        return performSubstitution(individuals, mutatedChildren)
    }


    //FPS
    private fun performFitnessProportionateSelection(population: List<Individual>): List<Pair<Individual, Individual>> {
        //additional array for individuals allowed to cross
        //Individuals for crossing are selected only from this array randomly,
        // so if a string is present in it in multiple instances, then it has more chances
        // "inherit" in history ;).
        val selected = mutableListOf<Individual>()
        //the ratio fi/f cp, where fi is the fitness of each individual,
        // and the fср is the average fitness in the population
        var fitness: Double
        for (individual in population) {
            //fi/fср
            fitness = calculateProbabilityToBeSelected(population, individual)
            //Next, we will write individuals to an additional array according to the following rule:
            //let's take the whole part of the fi/fср and write current individual into the additional array
            //exactly as many times
            val timesToBeSelected = fitness.toInt()
            repeat(timesToBeSelected) {
                selected.add(individual)
            }
            //using a random value, we will determine whether we will write this string again:
            // if random value is greater than the fractional part of the ratio, then "yes", if not, then "no"
            val probabilityToBeSelectedLastTime = fitness - timesToBeSelected
            if (Random.nextDouble() < probabilityToBeSelectedLastTime) {
                selected.add(individual)
            }
        }

        return generateUniquePairs(selected)
    }


    private fun generateUniquePairs(individuals: List<Individual>): List<Pair<Individual, Individual>> {
        val pairs = mutableListOf<Pair<Individual, Individual>>()

        for (i in 0 until individuals.size - 1) {
            val individual1 = individuals[i]

            for (j in i + 1 until individuals.size) {
                val individual2 = individuals[j]

                if (individual1 != individual2) {
                    val pair = individual1 to individual2

                    // Check if the pair or its reversed version already exists in the list
                    if (!pairs.contains(pair) && !pairs.contains(pair.swap())) {
                        pairs.add(pair)
                    }
                }
            }
        }

        return pairs
    }

    private fun <T, R> Pair<T, R>.swap(): Pair<R, T> {
        return second to first
    }

    private fun crossbreeding(parents: List<Pair<Individual, Individual>>): List<Individual> {
        val children = mutableListOf<Individual>()

        for (i in parents.indices) {
            val parent1 = parents[i].first
            val parent2 = parents[i].second
            if (Probability.shouldPerform()) {
                children.add(performOnePointCrossover(parent1, parent2))
            } else {
                children.add(performMultiPointCrossover(parent1, parent2))
            }


        }
        return children
    }

    private fun performOnePointCrossover(parent1: Individual, parent2: Individual): Individual {
        return Individual(
            parent1.x1,
            parent1.x2,
            parent2.x3, // Crossover point for 'x3' gene
            parent2.x4,
            parent2.x5
        )
    }

    private fun performMultiPointCrossover(parent1: Individual, parent2: Individual): Individual {
        return Individual(
            parent1.x1,
            parent2.x2, // Crossover point for 'x2' gene
            parent1.x3,
            parent2.x4, // Crossover point for 'x4' gene
            parent1.x5
        )
    }

    private fun performMutation(population: List<Individual>, p1: Double, p2: Double): List<Individual> {
        val mutatedPopulation = population.sortedBy { calculateFitness(it) }
        return mutatedPopulation.mapIndexed { index, individual ->
            if (index < 2) {
                mutateIndividual(p1, individual)
            } else {
                mutateIndividual(p2, individual)
            }

        }
    }

    //We replace the least suitable
    //individuals of the old population
    //with the most suitable individuals from the children
    private fun performSubstitution(oldPopulation: List<Individual>, children: List<Individual>): List<Individual> {
        val sortedOldPopulation = oldPopulation.sortedByDescending { calculateFitness(it) }
        val sortedChildren = children.sortedBy { calculateFitness(it) }
        val newPopulation = sortedOldPopulation.toMutableList()
        for (i in sortedChildren.indices) {
            if (i >= newPopulation.size) break
            if (calculateFitness(newPopulation[i]) > calculateFitness(sortedChildren[i]))
                newPopulation[i] = sortedChildren[i]
        }
        return newPopulation
    }

    private fun mutateIndividual(
        p1: Double,
        individual: Individual
    ) = Individual(
        if (Probability.comeTrue(p1)) Individual.generateGenome(
            minValue,
            maxValue
        ) else individual.x1,
        if (Probability.comeTrue(p1)) Individual.generateGenome(
            minValue,
            maxValue
        ) else individual.x2,
        if (Probability.comeTrue(p1)) Individual.generateGenome(
            minValue,
            maxValue
        ) else individual.x3,
        if (Probability.comeTrue(p1)) Individual.generateGenome(
            minValue,
            maxValue
        ) else individual.x4,
        if (Probability.comeTrue(p1)) Individual.generateGenome(
            minValue,
            maxValue
        ) else individual.x5
    )

    //отношение fi/fср, где fi - присособленность каждой особи, а fср среднюю приспособленность в популяции
    private fun calculateProbabilityToBeSelected(population: List<Individual>, individual: Individual): Double {
        val sum = (population.sumOf { calculateFitness(it) })
        val averageFitness = sum / population.size
        return calculateFitness(individual) / averageFitness
    }


    fun calculateFitness(individual: Individual): Double {
        return abs(getResult(individual) - result)
    }

    fun getResult(individual: Individual): Double {
        val terms = listOf(
            individual.x1.toDouble().pow(powers[0]) * individual.x2.toDouble().pow(powers[1]) * individual.x3.toDouble()
                .pow(powers[2]) * individual.x4.toDouble().pow(powers[3]) * individual.x5.toDouble().pow(powers[4]),
            individual.x1.toDouble().pow(powers[5]) * individual.x2.toDouble().pow(powers[6]) * individual.x3.toDouble()
                .pow(powers[7]) * individual.x4.toDouble().pow(powers[8]) * individual.x5.toDouble().pow(powers[9]),
            individual.x1.toDouble().pow(powers[10]) * individual.x2.toDouble()
                .pow(powers[11]) * individual.x3.toDouble().pow(powers[12]) * individual.x4.toDouble()
                .pow(powers[13]) * individual.x5.toDouble().pow(powers[14]),
            individual.x1.toDouble().pow(powers[15]) * individual.x2.toDouble()
                .pow(powers[16]) * individual.x3.toDouble().pow(powers[17]) * individual.x4.toDouble()
                .pow(powers[18]) * individual.x5.toDouble().pow(powers[19]),
            individual.x1.toDouble().pow(powers[20]) * individual.x2.toDouble()
                .pow(powers[21]) * individual.x3.toDouble().pow(powers[22]) * individual.x4.toDouble()
                .pow(powers[23]) * individual.x5.toDouble().pow(powers[24])
        )
        return terms.sum()
    }

    fun getErrorRate(individual: Individual): Double {
        return getResult(individual) - result
    }
}

fun printSolution(equationSolver: DiophantineEquationSolver) {

    var population = equationSolver.initializePopulation()
    var iterations = 0
    var bestIndividual = population[0]
    var bestFitness = equationSolver.calculateFitness(bestIndividual)
    while (bestFitness > 0) {
        println("iteration $iterations")
        println("error rate: $bestFitness")
        println("-----------------------")
        val newPopulation = equationSolver.substitution(population)
        population = newPopulation
        bestIndividual = newPopulation.minByOrNull { equationSolver.calculateFitness(it) }!!
        bestFitness = bestIndividual.let { equationSolver.calculateFitness(it) }
        iterations++
    }
    println("x1 = ${bestIndividual.x1}, x2 = ${bestIndividual.x2}, x3 = ${bestIndividual.x3}, x4 = ${bestIndividual.x4}, x5 = ${bestIndividual.x5}")
    println("Iterations: $iterations")
    println("Error rate: ${equationSolver.getErrorRate(bestIndividual)}")
}