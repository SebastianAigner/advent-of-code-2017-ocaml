

fun main(args: Array<String>) {
    val puzzleInput = "157,222,1,2,177,254,0,228,159,140,249,187,255,51,76,30"
    val partOne = calculateKnotHash((0 until 256).toList(), puzzleInput.split(",").map { it.toInt() }, 1)
    println("Part One Solution: ${partOne[0]*partOne[1]}")
    calculateHashes(prepInput(""))
    calculateHashes(prepInput("AoC 2017"))
    calculateHashes(prepInput(puzzleInput))
}

fun calculateHashes(stepSizes: List<Int>) {
    val list = (0 until 256).toList()
    val res = calculateKnotHash(list, stepSizes, 64)
    val denseHash = generateHexString(res)
    println("Dense Hash: $denseHash")
}

fun generateHexString(l: List<Int>): String {
    val blocks = l.chunked(16)
    return blocks.map {
        it.reduce {acc, int -> acc xor int}
    }.joinToString("") {
        it.toString(16).padStart(2, '0')
    }
}

fun prepInput(input: String): List<Int> {
    return input.map { it.toInt() } + listOf(17, 31, 73, 47, 23)
}


fun getCircularSublist(list: List<Int>, from: Int, to: Int): List<Int> {
    return if (to < list.size) {
        list.subList(from, to)
    } else {
        list.subList(from, list.size) + list.subList(0, to % list.size)
    }

}

private fun calculateKnotHash(list: List<Int>, inputLengths: List<Int>, times: Int): List<Int> {
    val list = list.toMutableList()

    var index = 0
    var skipSize = 0

    repeat(times) {
        val inLens = inputLengths.toMutableList()
        while (!inLens.isEmpty()) {
            val currentInputLength = inLens.removeAt(0)
            val sublist = getCircularSublist(list, index, index + currentInputLength).reversed()
            for (i in 0 until sublist.size) {
                list[(i + index) % list.size] = sublist[i]
            }
            index += currentInputLength
            index += skipSize
            index %= list.size
            skipSize++
        }
    }
    return list
}