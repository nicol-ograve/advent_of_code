package year2025.day10

import kotlin.math.pow


data class Machine(
    val requiredState: Array<Boolean>, val buttons: Array<Array<Int>>, val joltage: Array<Int>
) {

    fun applyButton(state: Array<Boolean>, buttons: Array<Int>): Array<Boolean> {
        val newState = state.copyOf()
        buttons.forEach {
            newState[it] = !state[it]
        }
        return newState
    }

    fun minButtonPressCount(): Int {
        val queue = mutableListOf(Step(0, Array(requiredState.size) { false }, 0))
        val minSteps = hashMapOf(Pair(0, 0))

        while (queue.isNotEmpty()) {
            val (intValue, state, steps) = queue.removeFirst()

            val newSteps = steps + 1
            buttons.forEach {
                val newState = applyButton(state, it)
                val newIntValue = Converter.parseState(newState)

                val currentSteps = minSteps[newIntValue]
                if (currentSteps == null || currentSteps > newSteps) {
                    minSteps[newIntValue] = newSteps
                    queue.add(Step(newIntValue, newState, newSteps))
                }
            }


        }
        return minSteps[Converter.parseState(requiredState)]!!
    }


    companion object {
        fun fromString(line: String): Machine {
            val parts = line.split(Regex("[\\[\\]{}]")).map { it.trim() }.filter { it.isNotEmpty() }


            return Machine(
                parts[0].map { it == '#' }.toTypedArray(), parts[1].split(" ").map {
                    it.replace("(", "").replace(")", "").split(",").map { buttonIndex -> buttonIndex.toInt() }
                        .toTypedArray()
                }.toTypedArray(), parts[2].split(",").map { it.toInt() }.toTypedArray()
            )
        }
    }
}

data class Step(val intValue: Int, val state: Array<Boolean>, val steps: Int)

private class Converter {
    companion object {
        private val pows = Array(10) {
            2.0.pow(it).toInt()
        }

        fun parseState(array: Array<Boolean>): Int {
            return array.foldIndexed(0) { index, sum, active ->
                sum + if (active) pows[index] else 0
            }
        }
    }
}