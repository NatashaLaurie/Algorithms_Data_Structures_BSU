package genetic_algorithm

import java.util.*

class Individual(var x1: Int, var x2: Int, var x3: Int, var x4: Int, var x5: Int) {
    companion object {
        private val rand = Random(System.currentTimeMillis())

        fun generate(minValue: Int, maxValue: Int): Individual {
            return Individual(
                rand.nextInt(minValue, maxValue),
                rand.nextInt(minValue, maxValue),
                rand.nextInt(minValue, maxValue),
                rand.nextInt(minValue, maxValue),
                rand.nextInt(minValue, maxValue)
            )
        }

        fun generateGenome(minValue: Int, maxValue: Int): Int {
            return rand.nextInt(minValue, maxValue)
        }
    }

    override fun toString(): String {
        return "($x1, $x2, $x3, $x4, $x5)"
    }
}