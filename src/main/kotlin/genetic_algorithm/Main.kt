package genetic_algorithm

/**
 * Genetic algorithm for solving equation :
 * x1^powx11 * x2^powx12 * x3^powx13 * x4^powx14 * x5^powx15 +
 * x1^powx21* x2^powx22 * x3^powx23 * x4^powx24 * x5^powx25 +
 * x3^powx31 * x2^powx32 * x3^powx33 * x4^powx34 * x5^powx35 +
 * x1^powx41 * x2^powx42 * x3^powx43 * x4^powx44 * x5^powx45 +
 * x1^powx51 * x2^powx52 * x3^powx53 * x4^powx54 * x5^powx55 = Result


 VARIANT 12
 * Output:
    1) x1 = 120, x2 = 0, x3 = 24, x4 = -143, x5 = -50
    Iterations: 664
    Error rate: 0.0

    2) x1 = -168, x2 = 21, x3 = 183, x4 = 0, x5 = -1
    Iterations: 3315
    Error rate: 0.0

    3) x1 = 2, x2 = 0, x3 = 62, x4 = 164, x5 = -2
    Iterations: 1591
    Error rate: 0.0

 */


fun main() {
    printSolution(
        DiophantineEquationSolver(
            intArrayOf(
                0, 0, 0, 0, 1,
                2, 1, 1, 1, 2,
                0, 2, 1, 2, 2,
                2, 2, 1, 1, 1,
                1, 2, 1, 1, 2
            ), -50
        )
    )

    printSolution(
        DiophantineEquationSolver(
            intArrayOf(
                0, 0, 0, 0, 0,
                0, 1, 1, 1, 2,
                1, 2, 0, 2, 0,
                1, 2, 0, 1, 1,
                0, 1, 0, 0, 2
            ), 22
        )
    )

    printSolution(
        DiophantineEquationSolver(
            intArrayOf(
                0, 0, 0, 1, 0,
                0, 0, 1, 0, 1,
                1, 2, 1, 0, 0,
                2, 2, 2, 1, 1,
                1, 2, 2, 1, 2
            ), 40
        )
    )
}
