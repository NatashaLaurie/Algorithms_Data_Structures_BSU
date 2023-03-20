package turingMachine

class State {

    private val ruleMap = hashMapOf<CellValue, StateRule>()

    @Throws(Exception::class)
    fun addRule(currentCellState: String, nextCellState: String, direction: String, nextState: State) {
        if (!ruleExists(currentCellState)) {
            ruleMap[CellValue.getCellValueFromString(currentCellState)] =
                StateRule(nextCellState, direction, nextState)
        } else {
            throw Exception("Rule already exists")
        }

    }

    internal fun getRuleForCellValue(cellValue: CellValue): StateRule = ruleMap[cellValue]!!

    private fun ruleExists(currentCellState: String): Boolean =
        ruleMap.containsKey(CellValue.getCellValueFromString(currentCellState))
}