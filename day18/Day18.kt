import java.math.BigInteger
import kotlin.system.measureTimeMillis

fun main(args: Array<String>) {
    val runtime = measureTimeMillis { val prog = readInstructions(puzzleInput)
        //println(prog.joinToString("\n"))
        runProgram(prog) }
    println("time elapsed: $runtime ms")
}

fun runProgram(prog: List<Instruction>) {
    val state = mutableMapOf<Char,BigInteger>()
    var pc = 0
    var insc = 0
    state['#'] // the sound currently playing
    for(i in ('a'..'p')) {
        state[i] = BigInteger.ZERO
    }
    while(true) {
        var pcModified = false
        insc++
        if(pc > prog.lastIndex) {
            println("Program terminated.")
            println(state)
            return
        }
        val ins = prog[pc.toInt()]
        //println(state)
        //println("next statement #$pc: $ins")
        //Thread.sleep(10)
        val x =
        when(ins) {
            is Instruction.Snd -> state['#'] = state[ins.reg] ?: BigInteger.ZERO
            is Instruction.Set -> state[ins.regA] = state[ins.regB]!!
            is Instruction.SetI -> state[ins.reg] = ins.value
            is Instruction.Add -> state[ins.regA] = state[ins.regA]!! + state[ins.regB]!!
            is Instruction.AddI -> state[ins.regA] = state[ins.regA]!! + ins.value
            is Instruction.Mul -> state[ins.regA] = state[ins.regA]!! * state[ins.regB]!!
            is Instruction.MulI -> state[ins.regA] = state[ins.regA]!! * ins.value
            is Instruction.Mod -> state[ins.regA] = state[ins.regA]!! % state[ins.regB]!!
            is Instruction.ModI -> state[ins.regA] = state[ins.regA]!! % ins.value
            is Instruction.Rcv -> {
                val x = state[ins.regX]
                if(x != null && x != BigInteger.ZERO) {
                    val recovered = state['#']
                    if(recovered != null) {
                        println("Recovering $recovered")
                        if (recovered != BigInteger.ZERO) return
                        else {
                            //todo: bla
                        }
                    }
                    else {
                        //todo: bla
                    }
                }
                else {
                    //todo: bla
                }
            }
            is Instruction.Jgz -> {
                val a = state[ins.regA]
                val b = state[ins.regB]
                if(a != null && a > BigInteger.ZERO) {
                    pc += b!!.toInt()
                    pcModified = true
                }
                else {
                    //todo: bla
                }
            }
            is Instruction.JgzI -> {
                val a = state[ins.regA]
                if(a != null && a > BigInteger.ZERO) {
                    pc += ins.offset.toInt()
                    pcModified = true
                }
                else {
                    //todo: bla
                }
            }
            is Instruction.JgzII -> {
                if(ins.valA > BigInteger.ZERO) {
                    pc += ins.valB.toInt()
                    pcModified = true
                }
                else {
                    //todo: bla
                }
            }
        }
        if(!pcModified) pc++
    }
}

sealed class Instruction {
    data class Snd(val reg: Char): Instruction()
    data class Set(val regA: Char, val regB: Char): Instruction()
    data class SetI(val reg: Char, val value: BigInteger): Instruction()
    data class Add(val regA: Char, val regB: Char): Instruction()
    data class AddI(val regA: Char, val value: BigInteger): Instruction()
    data class Mul(val regA: Char, val regB: Char): Instruction()
    data class MulI(val regA: Char, val value: BigInteger): Instruction()
    data class Mod(val regA: Char, val regB: Char): Instruction()
    data class ModI(val regA: Char, val value: BigInteger): Instruction()
    data class Rcv(val regX: Char): Instruction()
    data class Jgz(val regA: Char, val regB: Char): Instruction()
    data class JgzI(val regA: Char, val offset: BigInteger): Instruction()
    data class JgzII(val valA: BigInteger, val valB: BigInteger): Instruction()
}

fun readInstructions(text: String): List<Instruction> {
    return text.trim().lines().map {
        val fields = it.split(" ")
        val params = fields.drop(1)
        val first = params[0].first()

        val second = if(params.size > 1) params[1] else "?"

        when(fields[0]) {
            "snd" -> Instruction.Snd(first)
            "set" -> {
                if(second.reg()) {
                    Instruction.Set(first, second.first())
                } else {
                    Instruction.SetI(first, second.toBigInteger())
                }
            }
            "add" -> {
                if(second.reg()) {
                    Instruction.Add(first, second.first())
                } else {
                    Instruction.AddI(first, second.toBigInteger())
                }
            }
            "mul" -> {
                if(second.reg()) {
                    Instruction.Mul(first, second.first())
                } else {
                    Instruction.MulI(first, second.toBigInteger())
                }
            }
            "mod" -> {
                if(second.reg()) {
                    Instruction.Mod(first, second.first())
                } else {
                    Instruction.ModI(first, second.toBigInteger())
                }
            }
            "jgz" -> {
                if(!params[0].first().isLetter()) {
                    Instruction.JgzII(params[0].toBigInteger(), second.toBigInteger())
                }
                else if(second.reg()) {
                    Instruction.Jgz(first, second.first())
                } else {
                    Instruction.JgzI(first, second.toBigInteger())
                }
            }
            "rcv" -> Instruction.Rcv(first)
            else -> {
                throw NotImplementedError()
            }
        }
    }
}


fun String.reg(): Boolean {
    return this.any { it.isLetter() }
}


/*

snd X plays a sound with a frequency equal to the value of X.
set X Y sets register X to the value of Y.
add X Y increases register X by the value of Y.
mul X Y sets register X to the result of multiplying the value contained in register X by the value of Y.
mod X Y sets register X to the remainder of dividing the value contained in register X by the value of Y (that is, it sets X to the result of X modulo Y).
rcv X recovers the frequency of the last sound played, but only when the value of X is not zero. (If it is zero, the command does nothing.)
jgz X Y jumps with an offset of the value of Y, but only if the value of X is greater than zero. (An offset of 2 skips the next instruction, an offset of -1 jumps to the previous instruction, and so on.)

 */

val exampleInput = "set a 1\n" +
        "add a 2\n" +
        "mul a a\n" +
        "mod a 5\n" +
        "snd a\n" +
        "set a 0\n" +
        "rcv a\n" +
        "jgz a -1\n" +
        "set a 1\n" +
        "jgz a -2"

private val puzzleInput = "set i 31\n" +
        "set a 1\n" +
        "mul p 17\n" +
        "jgz p p\n" +
        "mul a 2\n" +
        "add i -1\n" +
        "jgz i -2\n" +
        "add a -1\n" +
        "set i 127\n" +
        "set p 680\n" +
        "mul p 8505\n" +
        "mod p a\n" +
        "mul p 129749\n" +
        "add p 12345\n" +
        "mod p a\n" +
        "set b p\n" +
        "mod b 10000\n" +
        "snd b\n" +
        "add i -1\n" +
        "jgz i -9\n" +
        "jgz a 3\n" +
        "rcv b\n" +
        "jgz b -1\n" +
        "set f 0\n" +
        "set i 126\n" +
        "rcv a\n" +
        "rcv b\n" +
        "set p a\n" +
        "mul p -1\n" +
        "add p b\n" +
        "jgz p 4\n" +
        "snd a\n" +
        "set a b\n" +
        "jgz 1 3\n" +
        "snd b\n" +
        "set f 1\n" +
        "add i -1\n" +
        "jgz i -11\n" +
        "snd a\n" +
        "jgz f -16\n" +
        "jgz a -19"