import java.util.*

fun main(args: Array<String>) {
    val solutionOne = shortCircuitSpinlocker(343,2017,2017)
    println("Solution 1: $solutionOne")
    val solutionTwo = shortCircuitSpinlocker(343, 50_000_000, 0)
    println("Solution 2: $solutionTwo")
}

fun shortCircuitSpinlocker(stepSize: Int, repetitions: Int, targetValue: Int): Int {
    val b = Buffer(stepSize)
    repeat(repetitions) {
        b.insert()
    }
    val res = b.getResultingList()
    println(res)
    return res[res.indexOf(targetValue)+1]
}

class Buffer(val stepSize: Int) {
    val linkedBuf = LinkedList<Int>().apply { add(0) }
    var ctr = 0
    var pos = 0
    fun insert() {
        pos += stepSize
        pos %= linkedBuf.size
        ctr++
        linkedBuf.add(pos, ctr)
        pos++
        if(ctr % 100_000 == 0) {
            println(ctr)
        }
        //println("new list $linkedBuf")
    }

    fun getResultingList(): List<Int> {
        return linkedBuf
    }
}
