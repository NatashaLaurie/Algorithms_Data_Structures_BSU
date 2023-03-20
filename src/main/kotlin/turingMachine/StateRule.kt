package turingMachine

class StateRule @Throws(Exception::class)
constructor(nextCellValue: String, moveDirection: String, internal val nextState: State) {

    internal val nextCellValue: CellValue = CellValue.getCellValueFromString(nextCellValue)
    internal val direction: Direction = Direction.getDirectionFromString(moveDirection)

}