package turingMachine

import java.io.File

enum class CellValue(val character: String) {
    BLANK("_"), A("a"), B("b"), C("c");

    companion object {
        @Throws(Exception::class)
        fun getCellValueFromString(stateString: String): CellValue {
            return when (stateString) {
                "_" -> BLANK
                "a" -> A
                "b" -> B
                "c" -> C
                else -> throw Exception("Invalid cell character.")
            }
        }
    }

}

//Tape movement
enum class Direction {
    NO_MOVE, LEFT, RIGHT;

    companion object {
        @Throws(Exception::class)
        fun getDirectionFromString(moveDirection: String): Direction {
            return when (moveDirection) {
                "N" -> NO_MOVE
                "L" -> LEFT
                "R" -> RIGHT
                else -> throw Exception("Invalid direction character.")
            }
        }
    }
}
