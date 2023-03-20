package turingMachine

private const val COMMAND_LENGTH: Int = 5
private val POSITIVE_HALT_STATE = State()
private val NEGATIVE_HALT_STATE = State()

fun runProgram(initialState: State, initialTapeState: Tape) {
    println("[START] Started running program.")

    var currentState = initialState
    initialTapeState.printTape()

    while (currentState != POSITIVE_HALT_STATE && currentState != NEGATIVE_HALT_STATE) {
        val currentRule = currentState.getRuleForCellValue(initialTapeState.currentCellValue())
        with(initialTapeState) {
            this.applyRule(currentRule)
            this.printTape()
        }
        currentState = currentRule.nextState
    }
    println(
        when (currentState) {
            POSITIVE_HALT_STATE -> "haltY"
            else -> "haltN"
        }
    )
}


//Finds which state should be first
fun computeInitialState(stateMap: Map<String, State>, program: Array<String>): State {
    var i = 0
    while (program[i].isBlankOrComment()) //skip blank lines
        i++

    val firstCommand = program[i].split(" ")

    return when (val initialState: State? = stateMap[firstCommand[0]]) {
        null -> POSITIVE_HALT_STATE
        else -> initialState
    }

}

fun parseStateRules(program: Array<String>): Map<String, State> {
    var stateMap: Map<String, State> = buildStates(program) //first, build a map of states
    stateMap = buildRules(program, stateMap) //then build rule object and link them to states
    return stateMap
}

fun buildRules(program: Array<String>, stateMap: Map<String, State>): Map<String, State> {

    for ((i, rule: String) in program.withIndex()) {
        if (rule.isBlankOrComment()) //skip lines which are not commands
            continue

        val commands = rule.split(" ")
        if (commands.size == COMMAND_LENGTH) {
            val state: State? = stateMap[commands[0]]
            val nextState: State? = stateMap[commands[4]]

            if (state != null && nextState != null)
                state.addRule(commands[1], commands[2], commands[3], nextState)
        } else {
            throw Exception("Rule in line $i is invalid")
        }
    }

    return stateMap
}

fun buildStates(program: Array<String>): Map<String, State> {
    val stateMap = HashMap<String, State>()
    stateMap["haltY"] = POSITIVE_HALT_STATE
    stateMap["haltN"] = NEGATIVE_HALT_STATE

    for ((i, rule: String) in program.withIndex()) {
        if (rule.isBlankOrComment())
            continue

        val commands = rule.split(" ")
        if (commands.size == COMMAND_LENGTH) {
            stateMap[commands[0]] = State()
        } else {
            throw Exception("Rule in line $i is invalid")
        }
    }

    return stateMap
}
