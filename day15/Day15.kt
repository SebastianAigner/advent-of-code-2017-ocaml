fun main(args: Array<String>) {
    val partOne = Judge(listOf(Generator(512, 16807, 1), Generator(191, 48271, 1))).evaluate(40.million())
    println("Tally for Part One: $partOne")
    val partTwo = Judge(listOf(Generator(512, 16807, 4), Generator(191, 48271, 8))).evaluate(5.million())
    println("Tally for Part Two: $partTwo")
}

fun Int.million(): Int {
    return this * 1_000_000
}

class Generator(val seed: Int, val factor: Int, val divisor: Int) {
    var currentValue = seed.toLong()
    fun run(): Int {
        currentValue *= factor
        currentValue %= 2147483647
        return if(currentValue % divisor == 0L) currentValue.toInt() else run()
    }
}

class Judge(val generators: List<Generator>) {
    fun evaluate(times: Int): Int {
        var count = 0
        repeat(times) {
            val results = generators.map { it.run() }.map { it.toString(2).takeLast(16) }
            if(results.distinct().size == 1) {
                count++
            }
        }
        return count
    }
}