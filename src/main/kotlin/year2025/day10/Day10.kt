package year2025.day10

import utils.getDataLinesWithYear

fun main() {
    val isDemo = false
    val machines =
        getDataLinesWithYear(10, 2025, if (isDemo) arrayOf("demo") else emptyArray()).map(Machine::fromString)

    val result = machines.map { it.minButtonPressCount() }.sumOf {
        println(it)
        it
    }

    println(result)
}