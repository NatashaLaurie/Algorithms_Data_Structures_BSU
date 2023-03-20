package turingMachine

import java.io.File

internal fun String.isBlankOrComment(): Boolean = this.isBlank() || this.isComment()

private fun String.isComment(): Boolean = this.startsWith(";")

private fun String.Companion.empty(): String = ""

internal fun readProgramFromFile(filepath: String): Array<String> = File(filepath).readLines().toTypedArray()

internal fun readInputFromFile(filepath: String): String = File(filepath).readLines().firstOrNull() ?: String.empty()