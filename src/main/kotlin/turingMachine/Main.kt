package turingMachine

private const val PROGRAM_ADD_LETTER_PATH = "src/main/resources/turingmachine/program1"
private const val INPUT1_1_PATH = "src/main/resources/turingmachine/input1_1"
private const val INPUT1_2_PATH = "src/main/resources/turingmachine/input1_2"
private const val INPUT1_3_PATH = "src/main/resources/turingmachine/input1_3"

private const val PROGRAM_IS_PALINDROME_PATH = "src/main/resources/turingmachine/program2"
private const val INPUT2_1_PATH = "src/main/resources/turingmachine/input2_1"
private const val INPUT2_2_PATH = "src/main/resources/turingmachine/input2_2"
private const val INPUT2_3_PATH = "src/main/resources/turingmachine/input2_3"


fun main() {

    val program1: Array<String> = readProgramFromFile(PROGRAM_ADD_LETTER_PATH)
    val stateMap1 = parseStateRules(program1)
    val initialState1 = computeInitialState(stateMap1, program1)

    runProgram(initialState1, Tape(readInputFromFile(INPUT1_1_PATH)))
    runProgram(initialState1, Tape(readInputFromFile(INPUT1_2_PATH)))
    runProgram(initialState1, Tape(readInputFromFile(INPUT1_3_PATH)))


    val program2: Array<String> = readProgramFromFile(PROGRAM_IS_PALINDROME_PATH)
    val stateMap2 = parseStateRules(program2)
    val initialState2 = computeInitialState(stateMap2, program1)

    runProgram(initialState2, Tape(readInputFromFile(INPUT2_1_PATH)))
    runProgram(initialState2, Tape(readInputFromFile(INPUT2_2_PATH)))
    runProgram(initialState2,  Tape(readInputFromFile(INPUT2_3_PATH)))

}

