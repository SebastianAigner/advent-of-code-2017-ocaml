import java.util.*
import kotlin.math.absoluteValue
import kotlin.math.max

interface HexVector {
    fun manhattan(): Int
    fun move(d: Direction)
}

class HexVector3(var x: Int, var y: Int, var z: Int): HexVector {
    fun optimize() {
        print("optimizing $this ")
        while((x > 0 && y > 0) || (x > 0 && z > 0) || (y > 0 && z > 0)) {
            x--
            y--
            z--
        }
        while((x < 0 && y < 0) || (y < 0 && z < 0) || (x < 0 && z < 0)) {
            x++
            y++
            z++
        }
        println("to $this")
    }
    override fun manhattan(): Int {
        optimize()
        return x.absoluteValue + y.absoluteValue + z.absoluteValue
    }

    override fun move(d: Direction) {
        when(d) {
            Direction.NORTH -> {
                z++
            }
            Direction.NORTHEAST -> {
                y--
            }
            Direction.SOUTHEAST -> {
                x++
            }
            Direction.SOUTH -> {
                z--
            }
            Direction.SOUTHWEST -> {
                y++
            }
            Direction.NORTHWEST -> {
                x--
            }
        }
    }

    override fun toString(): String {
        return "$x/$y/$z"
    }
}

fun getDirections(input: String): List<Direction> {
    return input.split(",").map {
        when(it) {
            "n" -> Direction.NORTH
            "ne" -> Direction.NORTHEAST
            "se" -> Direction.SOUTHEAST
            "s" -> Direction.SOUTH
            "sw" -> Direction.SOUTHWEST
            "nw" -> Direction.NORTHWEST
            else -> {
                throw InputMismatchException("encountered unknown direction $it")
            }
        }
    }
}

fun distance(input: String, hexVector: HexVector): Int {
    getDirections(input).forEach { hexVector.move(it) }
    println(hexVector)
    return hexVector.manhattan()
}

fun maxDistance(input: String): Int {
    val hexVector = HexVector3(0,0,0)
    var maxDistance = 0
    getDirections(input).forEach {
        hexVector.move(it)
        hexVector.optimize()
        maxDistance = max(maxDistance, hexVector.manhattan())
    }
    return maxDistance
}

enum class Direction {
    NORTH,
    NORTHEAST,
    SOUTHEAST,
    SOUTH,
    SOUTHWEST,
    NORTHWEST,
}

fun main(args: Array<String>) {
    println("Distance: ${distance(input, HexVector3(0,0,0))}")
    println("Max Distance: ${maxDistance(input)}")
}




class HexVector2(var x: Int, var y: Int): HexVector {
    override fun manhattan(): Int {
        return (x.absoluteValue + y.absoluteValue + (-x-y).absoluteValue)/2
    }

    override fun move(d: Direction) {
        when(d) {
            Direction.NORTH -> {
                y++
            }
            Direction.NORTHEAST -> {
                x++
            }
            Direction.SOUTHEAST -> {
                x++
                y--
            }
            Direction.SOUTH -> {
                y--
            }
            Direction.SOUTHWEST -> {
                x--
            }
            Direction.NORTHWEST -> {
                x--
                y++
            }
        }
    }

    override fun toString(): String {
        return "$x, $y"
    }

    fun toHexVector3(): HexVector3 {
        return HexVector3(x,y,-(x+y))
    }
}

private val input = """nw,n,n,se,ne,ne,ne,s,s,se,se,se,nw,se,s,s,s,se,se,s,s,s,s,sw,sw,s,s,s,s,sw,se,sw,ne,s,s,sw,n,sw,sw,sw,se,sw,sw,nw,nw,sw,sw,sw,sw,nw,sw,nw,nw,nw,sw,nw,nw,nw,nw,nw,nw,nw,nw,nw,sw,nw,nw,ne,n,nw,nw,nw,nw,n,n,n,ne,sw,nw,n,s,nw,sw,nw,nw,ne,n,nw,s,ne,n,n,nw,n,n,se,n,n,n,ne,n,s,s,n,ne,n,n,n,n,ne,ne,se,ne,ne,ne,n,sw,n,n,s,n,ne,ne,ne,se,ne,n,ne,ne,s,sw,n,ne,ne,ne,s,ne,n,sw,ne,ne,ne,ne,nw,ne,n,ne,ne,nw,ne,ne,ne,ne,ne,ne,ne,ne,ne,ne,ne,ne,ne,se,ne,ne,ne,ne,se,ne,ne,s,ne,se,se,sw,ne,ne,ne,ne,ne,ne,ne,se,se,nw,ne,s,ne,s,sw,ne,ne,ne,se,se,ne,ne,n,se,se,ne,se,ne,se,se,se,s,ne,se,nw,se,sw,se,se,se,se,se,se,se,se,se,se,s,n,se,sw,se,se,se,s,se,s,se,se,se,se,sw,s,se,ne,se,se,ne,s,se,s,se,s,se,se,ne,se,se,se,se,se,s,se,se,s,se,nw,se,se,s,nw,s,n,se,s,s,se,se,n,ne,se,s,n,se,se,s,se,se,se,se,se,s,s,se,s,s,s,s,n,s,s,s,n,s,nw,s,s,s,se,sw,s,s,se,s,s,s,s,nw,se,sw,s,s,n,s,se,s,s,s,s,n,s,s,se,s,sw,sw,s,s,s,s,s,s,s,s,s,s,s,s,sw,sw,s,se,s,s,sw,s,s,sw,n,s,s,sw,s,s,sw,s,s,sw,sw,s,sw,ne,s,s,s,s,sw,s,s,n,s,nw,s,sw,sw,s,nw,s,s,s,s,s,n,sw,s,sw,sw,sw,nw,n,s,sw,sw,sw,sw,s,sw,sw,sw,s,se,s,sw,sw,sw,sw,sw,s,sw,s,s,sw,n,n,sw,s,n,s,sw,sw,sw,sw,sw,nw,sw,sw,s,s,sw,s,sw,sw,sw,sw,se,sw,sw,s,nw,sw,sw,s,sw,sw,sw,s,s,sw,sw,s,ne,sw,ne,sw,sw,sw,sw,sw,sw,sw,se,nw,sw,sw,sw,sw,sw,sw,sw,ne,n,sw,sw,sw,sw,sw,sw,se,sw,sw,s,sw,sw,n,sw,se,s,nw,sw,sw,nw,nw,se,sw,sw,sw,sw,sw,s,n,nw,sw,nw,ne,ne,sw,sw,sw,nw,nw,sw,sw,sw,nw,s,nw,sw,nw,sw,se,sw,sw,nw,n,sw,sw,sw,sw,nw,sw,sw,n,sw,sw,sw,ne,sw,sw,nw,s,sw,nw,nw,ne,sw,sw,sw,sw,sw,sw,nw,n,n,sw,ne,sw,nw,nw,ne,sw,nw,se,nw,nw,nw,sw,sw,sw,nw,sw,sw,sw,nw,sw,sw,sw,sw,nw,sw,ne,sw,nw,nw,nw,sw,nw,s,se,nw,sw,nw,s,n,ne,nw,sw,sw,nw,nw,nw,nw,nw,ne,sw,sw,sw,sw,sw,sw,nw,nw,sw,nw,ne,sw,nw,sw,nw,s,n,nw,nw,nw,nw,nw,s,nw,sw,sw,nw,sw,nw,ne,nw,nw,nw,nw,sw,nw,nw,sw,nw,nw,sw,sw,sw,sw,nw,sw,nw,nw,nw,nw,nw,ne,s,nw,nw,ne,nw,s,nw,nw,s,nw,nw,nw,ne,nw,nw,nw,nw,nw,nw,nw,nw,nw,nw,nw,nw,nw,nw,nw,nw,se,nw,nw,nw,nw,se,nw,se,nw,nw,nw,nw,nw,nw,nw,nw,nw,nw,nw,nw,nw,ne,nw,nw,nw,nw,nw,nw,nw,nw,nw,nw,nw,s,nw,nw,nw,n,nw,nw,nw,n,nw,nw,se,nw,n,nw,nw,nw,nw,nw,nw,n,s,nw,nw,nw,s,s,nw,nw,nw,nw,n,nw,nw,nw,n,nw,nw,nw,nw,s,s,nw,n,nw,nw,nw,n,nw,n,s,n,se,n,nw,nw,n,nw,nw,n,s,ne,n,n,nw,nw,n,ne,nw,se,sw,se,n,nw,sw,n,n,sw,se,n,n,ne,sw,nw,sw,s,n,nw,n,nw,nw,sw,sw,nw,n,ne,nw,n,n,se,n,nw,n,n,n,n,sw,n,nw,ne,ne,n,s,n,s,n,n,n,n,nw,nw,nw,n,n,n,nw,n,n,n,n,s,sw,nw,nw,n,n,n,n,sw,ne,sw,nw,n,n,n,nw,nw,nw,n,se,n,n,nw,n,se,s,se,n,nw,nw,nw,n,n,n,n,ne,n,n,n,n,nw,n,n,nw,se,nw,nw,nw,n,nw,nw,ne,ne,n,nw,n,n,n,n,n,n,n,n,n,n,se,n,n,nw,n,se,ne,n,n,n,n,n,sw,n,n,n,n,n,se,n,n,n,ne,n,n,n,s,ne,n,n,n,nw,n,n,n,n,nw,s,n,n,n,n,n,nw,n,n,n,nw,n,se,n,n,n,n,n,n,n,n,ne,nw,nw,n,nw,n,nw,n,nw,n,n,n,n,n,sw,ne,ne,n,n,n,sw,se,ne,n,nw,n,s,n,n,ne,se,n,sw,n,n,ne,n,nw,n,n,ne,n,n,nw,n,n,n,n,s,n,n,ne,ne,n,n,s,n,ne,se,s,ne,s,n,n,n,se,n,n,n,se,n,sw,n,n,sw,n,n,n,se,n,ne,n,n,ne,n,n,ne,ne,n,n,n,n,n,n,n,n,n,ne,ne,n,n,nw,n,ne,ne,ne,ne,n,ne,n,sw,ne,n,n,n,n,n,ne,ne,ne,n,ne,ne,sw,ne,ne,se,ne,ne,ne,s,n,n,n,n,nw,nw,sw,ne,n,n,n,s,ne,ne,n,n,n,n,n,n,n,ne,se,n,s,n,ne,ne,n,nw,ne,n,ne,s,nw,ne,ne,s,n,ne,n,ne,ne,ne,sw,nw,ne,ne,ne,ne,se,n,n,n,ne,ne,n,n,ne,ne,n,ne,ne,ne,n,s,n,ne,ne,ne,ne,sw,ne,ne,n,ne,n,nw,n,n,n,n,sw,ne,n,n,ne,n,ne,ne,n,n,n,se,n,ne,ne,n,n,se,ne,ne,se,sw,ne,ne,ne,n,se,nw,nw,ne,sw,se,ne,ne,se,n,sw,ne,ne,ne,ne,n,ne,se,ne,n,ne,n,ne,ne,ne,n,n,ne,n,n,ne,n,sw,ne,n,ne,s,n,ne,ne,ne,ne,ne,n,ne,nw,s,ne,ne,n,n,ne,ne,ne,ne,ne,n,se,n,n,ne,nw,ne,ne,ne,ne,n,ne,sw,n,ne,nw,ne,sw,n,ne,nw,ne,ne,ne,n,ne,ne,se,n,s,n,ne,ne,ne,ne,s,n,n,s,ne,ne,ne,se,s,nw,ne,ne,n,ne,ne,sw,se,ne,ne,nw,ne,ne,sw,n,se,ne,ne,sw,ne,sw,ne,ne,ne,s,sw,ne,ne,ne,n,s,ne,ne,ne,nw,s,ne,se,ne,n,ne,ne,ne,nw,nw,n,se,ne,ne,ne,se,se,ne,ne,ne,nw,ne,s,s,ne,ne,ne,n,ne,ne,ne,ne,ne,ne,ne,nw,ne,s,ne,ne,ne,s,ne,se,ne,nw,ne,se,ne,n,ne,ne,ne,ne,ne,se,ne,ne,se,s,s,ne,nw,ne,ne,s,ne,ne,sw,ne,ne,ne,ne,sw,ne,ne,ne,ne,ne,ne,ne,ne,ne,ne,ne,ne,ne,sw,ne,ne,ne,s,ne,nw,ne,ne,se,ne,ne,ne,ne,se,ne,ne,se,nw,ne,ne,ne,se,se,ne,ne,se,ne,ne,ne,ne,se,se,ne,ne,se,ne,ne,ne,n,ne,n,sw,ne,ne,ne,ne,ne,ne,ne,ne,ne,ne,ne,ne,ne,ne,ne,se,se,se,ne,ne,ne,ne,ne,ne,ne,se,sw,ne,ne,se,ne,ne,nw,se,ne,s,nw,ne,s,ne,ne,ne,se,n,ne,ne,se,ne,ne,ne,ne,ne,se,se,ne,ne,se,ne,se,ne,ne,ne,ne,ne,nw,se,ne,sw,sw,sw,ne,nw,ne,ne,se,ne,ne,ne,se,sw,ne,se,ne,ne,ne,ne,ne,n,ne,ne,ne,n,ne,nw,nw,ne,ne,se,se,ne,se,se,ne,n,se,se,ne,ne,ne,ne,ne,sw,se,se,s,ne,ne,ne,se,ne,ne,ne,ne,ne,ne,se,ne,se,ne,ne,s,se,se,ne,ne,se,ne,nw,ne,se,se,se,s,ne,se,s,ne,ne,se,s,ne,se,ne,se,se,se,se,ne,se,se,ne,se,sw,ne,se,se,se,ne,ne,n,ne,se,se,se,n,se,se,se,se,ne,ne,se,ne,sw,ne,ne,sw,se,ne,ne,ne,ne,ne,se,ne,ne,nw,se,sw,ne,ne,se,se,ne,ne,ne,ne,se,ne,se,ne,se,s,se,se,se,se,se,ne,se,ne,se,ne,se,ne,n,se,ne,se,se,se,nw,ne,sw,se,ne,ne,ne,ne,n,se,se,se,se,se,se,se,s,sw,se,ne,ne,se,se,se,ne,se,ne,se,se,ne,nw,ne,s,se,se,se,n,s,se,se,se,se,se,se,se,se,sw,se,ne,se,sw,se,se,se,se,se,se,nw,ne,ne,sw,nw,se,se,se,sw,se,se,sw,ne,se,se,s,se,ne,ne,se,ne,s,ne,se,s,sw,se,se,se,se,sw,se,se,s,ne,ne,ne,se,se,se,ne,sw,se,se,ne,se,se,nw,se,se,s,se,n,nw,ne,sw,ne,se,se,nw,se,sw,se,se,se,ne,se,se,sw,se,se,se,n,ne,ne,ne,se,se,nw,se,ne,ne,se,se,se,se,n,nw,ne,se,se,sw,se,nw,se,s,ne,se,se,se,se,se,se,n,ne,se,se,s,se,se,ne,n,se,se,se,se,sw,s,se,se,nw,se,se,se,se,se,se,se,se,se,se,se,se,se,n,se,se,se,ne,n,se,n,se,se,sw,se,sw,nw,se,se,ne,se,se,n,se,ne,se,sw,se,se,se,se,se,ne,se,se,se,se,se,se,ne,se,se,se,se,se,se,sw,n,ne,se,se,n,se,se,sw,nw,se,se,se,se,se,se,ne,se,ne,ne,n,sw,se,s,se,se,s,se,se,se,se,se,ne,se,se,se,ne,n,se,se,se,se,s,s,n,se,se,s,se,se,se,se,se,se,se,se,s,se,se,se,se,se,se,nw,se,se,se,se,se,ne,n,se,se,ne,s,se,se,se,se,se,se,se,se,s,ne,se,sw,s,se,se,sw,n,s,se,se,se,sw,se,se,nw,se,nw,se,s,se,se,se,se,s,nw,se,se,se,se,se,se,se,se,s,se,se,se,se,n,se,s,se,se,se,n,nw,s,se,n,n,se,ne,se,se,se,se,n,se,se,se,nw,se,se,nw,s,se,s,n,se,se,se,se,se,se,se,se,se,se,s,se,se,s,se,se,se,se,se,ne,sw,n,se,se,se,se,se,nw,se,n,se,s,se,sw,s,se,se,se,nw,se,sw,ne,se,sw,n,se,se,se,se,n,se,se,se,se,s,sw,se,se,se,se,sw,se,sw,se,sw,se,se,se,se,se,nw,se,sw,sw,ne,se,ne,se,se,s,n,se,s,s,se,s,n,se,sw,s,sw,se,se,s,s,se,se,se,sw,ne,n,se,se,se,se,se,s,se,s,s,s,ne,se,se,s,se,s,sw,s,s,s,s,se,se,se,nw,s,se,se,sw,s,se,se,se,s,se,sw,s,nw,se,s,s,ne,se,s,se,se,s,nw,s,s,se,s,se,s,se,se,se,se,se,s,s,se,s,se,se,se,se,se,se,sw,nw,sw,se,s,s,se,se,sw,s,n,s,sw,s,se,ne,se,s,s,s,s,ne,se,ne,se,sw,s,se,se,s,se,ne,se,nw,se,se,n,se,se,nw,nw,s,se,se,n,se,se,s,s,ne,se,s,s,s,sw,se,s,s,ne,s,s,se,s,ne,nw,se,s,sw,sw,s,s,s,s,se,sw,s,sw,sw,s,ne,ne,se,se,se,se,se,n,n,se,se,se,s,s,n,se,se,n,se,s,sw,s,sw,se,se,s,se,sw,s,sw,se,se,se,n,s,nw,ne,s,s,s,ne,se,sw,sw,ne,s,s,s,s,s,s,sw,nw,s,se,s,s,se,se,se,s,s,sw,se,s,s,se,se,s,sw,s,se,s,se,s,se,se,se,se,s,s,nw,s,s,ne,ne,se,nw,s,s,s,s,s,se,se,s,se,s,n,se,s,s,s,nw,ne,ne,s,se,se,s,se,se,nw,se,sw,s,s,se,s,ne,nw,s,nw,se,s,s,s,s,n,n,ne,s,nw,s,se,s,s,se,se,s,se,s,s,s,s,se,se,sw,se,se,s,s,n,s,s,s,sw,s,s,se,n,s,s,se,s,s,ne,s,s,s,s,s,s,se,s,se,s,sw,se,s,se,s,s,se,s,se,s,s,s,s,se,sw,s,se,s,se,s,s,sw,se,s,s,s,s,s,se,s,s,s,se,s,n,s,s,s,se,s,s,s,n,sw,s,s,s,s,s,se,se,s,se,s,s,s,s,s,se,s,s,s,s,s,s,s,n,s,s,ne,s,s,s,s,s,s,sw,s,n,se,s,s,s,se,nw,s,s,s,s,s,sw,se,s,s,s,s,se,s,s,ne,s,nw,s,s,s,se,s,s,ne,s,s,s,s,s,s,s,s,n,se,s,s,s,s,s,sw,s,s,s,ne,se,nw,s,s,s,se,s,s,s,s,se,s,sw,s,s,s,s,s,ne,s,s,s,s,s,s,s,nw,nw,s,s,se,s,se,ne,n,s,nw,s,s,s,ne,s,nw,s,n,s,s,s,s,s,s,s,s,s,s,s,s,ne,sw,sw,s,s,s,se,s,s,s,sw,s,s,s,s,s,s,s,s,sw,s,s,s,s,nw,s,s,s,s,nw,s,nw,s,s,s,ne,se,s,s,s,s,s,s,s,s,sw,s,s,sw,s,s,s,s,s,n,s,s,s,s,s,s,s,s,n,s,sw,s,s,s,s,n,sw,s,s,s,s,s,s,s,s,sw,s,n,s,s,s,s,s,ne,sw,s,s,s,s,sw,s,s,n,n,s,s,s,s,nw,s,s,s,s,s,se,sw,s,s,s,s,s,nw,s,s,sw,s,ne,s,se,s,s,s,s,ne,s,s,s,s,s,nw,s,sw,nw,s,s,s,s,s,s,s,n,sw,s,n,ne,se,s,s,s,ne,s,se,s,s,ne,n,sw,s,s,s,s,s,n,sw,s,s,s,se,s,s,s,nw,s,s,s,s,s,ne,n,sw,sw,sw,s,s,sw,s,sw,s,s,sw,s,nw,s,s,n,sw,s,s,sw,se,s,s,s,ne,sw,sw,s,s,se,s,s,s,s,s,s,s,ne,s,s,s,s,s,s,s,s,s,s,s,s,s,s,n,se,nw,s,sw,s,sw,ne,s,sw,s,s,s,s,s,nw,s,s,s,s,s,s,s,s,s,sw,s,s,s,s,s,se,s,s,s,s,s,nw,n,ne,nw,s,s,n,sw,s,sw,ne,s,se,s,s,sw,s,s,s,sw,sw,s,s,s,ne,s,se,sw,s,s,ne,s,s,n,s,sw,s,sw,sw,s,s,nw,s,s,s,s,s,nw,nw,n,s,sw,sw,ne,s,s,nw,s,s,s,s,s,s,s,sw,n,se,s,s,sw,sw,s,s,s,sw,s,s,sw,sw,s,n,s,s,n,s,sw,sw,s,s,s,s,sw,s,s,sw,sw,s,se,s,se,s,s,sw,ne,sw,nw,s,s,s,n,s,sw,s,sw,s,s,s,se,s,s,s,s,s,s,n,sw,s,s,s,s,sw,ne,sw,s,s,s,s,sw,sw,sw,sw,s,s,s,nw,sw,s,n,sw,sw,s,s,s,s,s,s,s,sw,s,s,sw,sw,s,sw,s,s,sw,s,s,sw,s,sw,sw,s,s,s,n,sw,ne,s,nw,s,nw,ne,s,s,nw,s,sw,sw,sw,sw,nw,se,s,nw,s,sw,s,ne,nw,sw,s,s,s,s,se,sw,ne,s,nw,s,sw,s,sw,s,ne,nw,sw,sw,s,sw,sw,s,s,nw,s,n,ne,ne,s,s,s,s,s,sw,s,s,ne,sw,s,s,nw,se,s,s,se,s,sw,se,sw,s,s,s,nw,sw,se,s,s,sw,sw,s,s,sw,s,sw,s,n,s,nw,nw,n,nw,sw,sw,sw,s,sw,sw,s,sw,s,sw,sw,s,s,sw,s,s,nw,sw,sw,sw,s,nw,s,n,sw,se,n,ne,sw,sw,se,sw,nw,sw,s,sw,s,s,s,s,s,n,s,sw,sw,s,s,sw,s,sw,ne,ne,sw,s,s,ne,sw,ne,sw,s,n,sw,s,s,sw,se,sw,s,nw,sw,sw,sw,sw,sw,sw,nw,sw,s,s,s,n,se,sw,s,s,s,sw,s,sw,s,s,s,s,sw,se,sw,ne,s,s,sw,sw,sw,s,ne,sw,sw,sw,s,sw,sw,sw,sw,s,s,s,sw,sw,sw,sw,sw,n,sw,ne,s,sw,sw,sw,s,sw,sw,sw,se,sw,sw,sw,ne,s,s,sw,s,sw,sw,sw,s,sw,nw,se,sw,s,s,sw,s,ne,s,sw,sw,s,sw,sw,s,sw,sw,s,s,s,sw,sw,s,s,s,s,n,s,ne,s,s,s,sw,s,nw,ne,sw,sw,s,sw,s,s,s,nw,sw,sw,sw,n,se,sw,sw,sw,sw,sw,sw,sw,s,sw,sw,sw,s,sw,sw,sw,sw,ne,s,ne,sw,ne,ne,s,sw,sw,s,s,sw,s,sw,s,sw,sw,s,s,sw,sw,s,sw,s,s,se,sw,se,s,sw,se,sw,ne,sw,ne,nw,n,sw,sw,sw,ne,s,ne,sw,sw,sw,s,sw,s,sw,sw,sw,sw,nw,nw,sw,s,sw,se,sw,se,sw,sw,sw,sw,s,n,sw,sw,sw,sw,s,s,sw,s,ne,sw,sw,sw,sw,nw,se,sw,sw,sw,sw,sw,sw,sw,s,sw,s,s,sw,sw,sw,sw,sw,s,ne,s,ne,sw,sw,sw,sw,sw,sw,sw,sw,sw,sw,sw,sw,sw,sw,s,ne,nw,sw,se,sw,sw,sw,sw,sw,sw,sw,s,s,sw,s,sw,s,sw,sw,s,s,s,sw,sw,sw,sw,sw,s,sw,sw,s,sw,nw,sw,sw,nw,sw,s,sw,s,s,s,s,sw,s,n,s,s,sw,sw,sw,sw,sw,s,sw,s,nw,sw,sw,sw,s,sw,sw,sw,se,sw,sw,s,s,sw,sw,sw,sw,n,sw,s,se,sw,sw,se,s,sw,sw,sw,s,sw,nw,s,sw,sw,ne,sw,sw,sw,sw,ne,sw,s,sw,sw,ne,sw,s,s,sw,sw,sw,s,se,sw,sw,s,ne,nw,s,sw,n,se,sw,se,sw,sw,sw,sw,s,sw,ne,sw,s,sw,sw,se,sw,s,sw,sw,sw,sw,sw,sw,n,n,ne,s,sw,sw,s,sw,sw,sw,sw,sw,sw,sw,se,sw,sw,s,sw,s,sw,nw,sw,sw,s,sw,se,sw,sw,sw,nw,sw,s,ne,se,ne,n,sw,sw,sw,s,sw,s,s,sw,sw,sw,ne,s,sw,se,sw,sw,s,sw,n,n,sw,sw,nw,sw,s,sw,sw,sw,sw,sw,sw,sw,s,sw,sw,ne,ne,sw,sw,sw,sw,sw,sw,se,sw,sw,sw,sw,s,sw,sw,se,sw,sw,ne,sw,nw,sw,sw,sw,sw,sw,sw,sw,s,s,sw,sw,nw,sw,sw,sw,se,s,nw,sw,sw,sw,s,sw,sw,sw,sw,sw,ne,sw,nw,sw,se,sw,se,sw,sw,sw,sw,sw,sw,sw,sw,sw,se,n,sw,sw,ne,sw,ne,s,ne,sw,sw,sw,sw,sw,sw,sw,sw,sw,se,sw,sw,sw,sw,sw,sw,sw,sw,sw,sw,sw,sw,sw,sw,sw,sw,sw,s,ne,sw,sw,sw,ne,sw,ne,nw,sw,sw,sw,n,sw,sw,sw,nw,sw,sw,sw,sw,sw,sw,se,sw,se,sw,sw,sw,sw,sw,sw,sw,sw,sw,nw,sw,sw,sw,s,sw,se,n,ne,se,n,sw,sw,sw,sw,sw,ne,se,sw,sw,sw,se,sw,nw,n,sw,sw,n,sw,sw,sw,sw,sw,ne,n,sw,n,sw,sw,sw,sw,nw,nw,sw,nw,sw,sw,nw,sw,sw,s,sw,sw,sw,s,sw,n,s,se,sw,nw,sw,sw,sw,sw,sw,sw,sw,sw,sw,nw,ne,nw,n,n,sw,sw,sw,se,n,sw,n,nw,sw,sw,sw,s,sw,sw,ne,sw,sw,sw,sw,sw,sw,sw,sw,sw,se,s,se,nw,sw,n,sw,ne,sw,sw,n,n,nw,sw,n,sw,nw,se,sw,sw,n,sw,nw,sw,sw,sw,sw,sw,sw,sw,se,sw,sw,sw,ne,sw,sw,nw,sw,sw,sw,sw,n,sw,sw,sw,sw,sw,nw,sw,ne,nw,n,sw,sw,sw,sw,ne,sw,n,sw,sw,sw,n,sw,sw,sw,ne,ne,sw,sw,s,sw,sw,ne,sw,sw,sw,nw,sw,ne,sw,sw,sw,sw,sw,sw,sw,sw,nw,sw,sw,sw,sw,sw,sw,sw,nw,nw,sw,nw,sw,sw,sw,sw,sw,sw,sw,sw,sw,sw,sw,s,sw,nw,sw,se,sw,nw,nw,sw,sw,sw,nw,sw,sw,sw,nw,nw,sw,sw,nw,sw,se,nw,sw,sw,sw,nw,s,sw,n,sw,sw,sw,s,sw,sw,sw,sw,se,ne,sw,sw,sw,se,sw,sw,sw,nw,sw,sw,nw,se,sw,nw,nw,sw,nw,sw,sw,se,sw,s,sw,s,sw,sw,nw,sw,sw,nw,sw,sw,ne,sw,sw,sw,se,sw,sw,sw,n,sw,n,sw,sw,sw,nw,s,sw,sw,sw,se,sw,ne,nw,sw,ne,sw,sw,nw,nw,se,nw,sw,sw,sw,sw,nw,nw,nw,sw,ne,se,nw,nw,s,sw,nw,nw,sw,nw,sw,n,nw,sw,s,sw,sw,sw,n,nw,sw,sw,s,sw,nw,nw,n,sw,sw,sw,sw,nw,sw,nw,sw,sw,sw,nw,sw,nw,s,nw,sw,ne,sw,sw,nw,se,sw,sw,sw,s,sw,sw,nw,n,sw,sw,sw,sw,nw,sw,s,sw,sw,sw,se,nw,nw,sw,se,nw,se,nw,sw,nw,nw,sw,sw,nw,sw,sw,sw,sw,sw,sw,sw,nw,sw,nw,sw,s,sw,sw,sw,sw,sw,nw,nw,nw,sw,nw,sw,nw,sw,sw,sw,se,sw,sw,sw,ne,sw,sw,sw,sw,nw,ne,sw,s,nw,sw,nw,se,sw,sw,nw,sw,sw,sw,sw,nw,ne,sw,sw,nw,sw,ne,sw,sw,sw,nw,se,s,sw,sw,sw,sw,s,nw,nw,sw,sw,nw,nw,sw,nw,sw,sw,nw,nw,sw,sw,se,sw,sw,sw,nw,nw,nw,sw,sw,s,nw,nw,sw,sw,sw,nw,sw,n,sw,nw,sw,sw,sw,s,sw,nw,ne,sw,sw,sw,nw,sw,nw,nw,nw,sw,nw,nw,nw,s,sw,sw,sw,nw,sw,nw,sw,sw,sw,nw,s,sw,nw,sw,ne,s,sw,sw,sw,se,sw,nw,sw,sw,sw,sw,n,sw,ne,nw,sw,nw,sw,nw,sw,nw,nw,s,sw,nw,se,nw,ne,s,nw,nw,sw,sw,sw,sw,sw,s,sw,sw,sw,sw,n,sw,nw,sw,sw,sw,ne,sw,ne,sw,nw,sw,sw,n,sw,nw,ne,nw,sw,nw,sw,sw,sw,sw,n,se,ne,sw,nw,n,nw,sw,n,nw,sw,se,nw,se,sw,sw,nw,n,sw,sw,sw,se,n,nw,sw,sw,sw,nw,sw,nw,nw,ne,ne,s,n,s,sw,se,ne,ne,nw,sw,nw,s,sw,sw,sw,n,sw,se,nw,sw,nw,sw,sw,nw,nw,ne,se,nw,nw,s,nw,nw,se,sw,nw,nw,sw,se,nw,nw,nw,sw,sw,s,sw,sw,sw,se,sw,sw,s,sw,sw,nw,nw,ne,ne,sw,sw,sw,sw,nw,nw,nw,sw,s,se,sw,sw,nw,sw,s,sw,sw,nw,sw,se,sw,sw,sw,sw,sw,nw,n,n,sw,sw,sw,nw,sw,sw,nw,nw,ne,sw,sw,s,sw,nw,nw,nw,se,sw,nw,nw,n,sw,sw,sw,nw,n,sw,se,se,sw,sw,sw,se,sw,sw,sw,nw,nw,sw,n,nw,sw,nw,se,nw,sw,sw,sw,ne,nw,nw,nw,sw,sw,sw,nw,n,nw,nw,nw,sw,nw,nw,nw,nw,sw,nw,n,sw,sw,sw,sw,nw,s,sw,sw,sw,sw,sw,sw,nw,n,sw,nw,s,sw,n,sw,nw,sw,nw,sw,nw,sw,nw,sw,n,sw,se,nw,nw,n,sw,nw,nw,nw,nw,nw,nw,s,n,sw,nw,nw,sw,sw,nw,n,se,nw,nw,ne,ne,sw,nw,sw,sw,nw,nw,sw,nw,nw,n,sw,nw,sw,sw,s,nw,sw,nw,nw,sw,nw,sw,nw,nw,nw,nw,nw,nw,se,nw,nw,se,nw,sw,sw,sw,sw,sw,sw,nw,ne,sw,sw,sw,ne,sw,nw,nw,sw,nw,ne,sw,ne,sw,sw,sw,sw,n,nw,nw,sw,nw,se,nw,ne,nw,nw,nw,sw,nw,nw,sw,s,sw,sw,ne,nw,nw,nw,sw,sw,sw,sw,se,ne,sw,sw,sw,nw,sw,s,sw,nw,sw,n,se,nw,nw,n,sw,nw,sw,sw,sw,sw,nw,se,nw,nw,sw,nw,nw,sw,nw,nw,sw,sw,se,ne,nw,ne,nw,n,nw,nw,sw,sw,nw,sw,sw,sw,sw,sw,nw,nw,nw,sw,sw,n,nw,nw,nw,se,se,nw,n,nw,n,sw,s,nw,nw,nw,se,nw,se,nw,sw,s,sw,sw,nw,se,nw,nw,nw,nw,sw,nw,sw,sw,nw,sw,nw,nw,nw,nw,nw,sw,nw,nw,nw,nw,nw,nw,nw,sw,nw,nw,nw,nw,sw,nw,nw,nw,se,nw,s,nw,s,se,nw,nw,nw,sw,nw,se,s,ne,nw,nw,nw,nw,nw,nw,sw,nw,se,ne,nw,nw,s,nw,nw,nw,n,nw,nw,s,nw,nw,nw,se,nw,se,nw,s,nw,ne,sw,nw,n,nw,nw,nw,sw,sw,nw,nw,nw,nw,ne,nw,nw,nw,n,nw,nw,nw,sw,nw,sw,sw,nw,s,se,nw,nw,nw,se,nw,nw,nw,nw,s,nw,nw,sw,nw,nw,nw,sw,sw,n,ne,nw,n,se,sw,n,nw,sw,nw,sw,sw,nw,n,nw,nw,sw,nw,sw,nw,nw,s,nw,nw,sw,se,sw,nw,nw,nw,nw,se,nw,nw,se,sw,nw,nw,nw,se,se,sw,ne,n,nw,sw,nw,nw,nw,n,nw,se,nw,nw,nw,s,nw,nw,nw,nw,sw,sw,ne,nw,nw,sw,s,sw,nw,sw,s,sw,s,ne,nw,nw,ne,ne,n,s,sw,nw,n,nw,nw,sw,s,sw,nw,sw,n,nw,nw,n,s,nw,nw,nw,nw,s,nw,nw,nw,se,nw,sw,nw,nw,se,sw,nw,nw,nw,ne,sw,nw,se,se,sw,nw,ne,nw,sw,nw,nw,nw,nw,nw,nw,nw,nw,s,nw,sw,n,sw,nw,se,sw,sw,nw,nw,nw,n,se,nw,nw,se,nw,sw,nw,s,sw,nw,s,se,nw,sw,nw,sw,nw,nw,nw,sw,nw,sw,nw,s,nw,nw,nw,nw,nw,sw,nw,s,nw,sw,nw,nw,sw,s,nw,ne,nw,nw,nw,se,nw,nw,sw,sw,s,sw,nw,nw,nw,nw,nw,nw,nw,nw,s,se,s,nw,nw,n,sw,se,nw,nw,se,nw,nw,nw,nw,nw,ne,sw,nw,ne,nw,nw,nw,s,nw,nw,nw,nw,nw,nw,nw,nw,nw,nw,nw,nw,nw,nw,n,se,nw,nw,sw,n,nw,nw,nw,nw,nw,nw,nw,nw,s,s,nw,nw,ne,nw,nw,nw,nw,nw,nw,nw,nw,nw,nw,n,nw,nw,nw,nw,nw,nw,s,nw,nw,nw,sw,nw,nw,nw,nw,s,nw,nw,nw,se,nw,n,sw,nw,nw,nw,nw,nw,ne,n,s,nw,nw,ne,nw,nw,nw,nw,nw,nw,nw,se,n,nw,nw,nw,se,se,nw,nw,sw,nw,nw,nw,s,nw,nw,nw,nw,nw,nw,nw,nw,nw,nw,nw,nw,sw,nw,s,nw,nw,nw,nw,nw,sw,nw,nw,s,n,s,nw,sw,nw,nw,nw,nw,sw,nw,nw,n,n,nw,nw,nw,nw,nw,nw,nw,s,nw,nw,nw,nw,nw,nw,nw,nw,nw,nw,nw,nw,nw,nw,nw,nw,nw,nw,nw,nw,nw,nw,n,nw,nw,nw,nw,nw,nw,ne,nw,nw,sw,ne,nw,nw,ne,nw,ne,sw,s,se,se,n,se,ne,nw,se,ne,ne,ne,n,ne,ne,n,se,n,n,ne,n,n,s,n,sw,nw,nw,n,n,nw,nw,nw,n,n,nw,nw,nw,nw,nw,nw,nw,n,n,n,nw,sw,nw,nw,nw,nw,ne,ne,nw,ne,sw,nw,n,sw,nw,n,sw,sw,n,nw,sw,s,sw,sw,sw,sw,sw,s,s,ne,sw,sw,s,sw,s,sw,sw,sw,nw,s,sw,s,s,s,n,s,s,s,s,s,s,ne,s,s,s,s,s,s,se,nw,s,s,ne,ne,sw,s,n,s,s,se,s,s,se,s,s,s,s,se,se,s,s,se,s,se,se,ne,se,se,se,sw,se,se,se,se,s,se,se,ne,ne,s,n,se,ne,se,se,se,se,se,n,se,se,se,se,sw,se,se,ne,ne,se,ne,se,sw,se,se,ne,s,s,se,ne,se,se,se,sw,se,ne,ne,se,s,nw,se,se,se,ne,nw,se,se,ne,ne,se,ne,ne,se,se,se,se,n,se,sw,ne,ne,se,ne,s,se,se,ne,ne,ne,se,ne,sw,sw,ne,se,sw,ne,ne,ne,ne,ne,sw,ne,ne,ne,ne,ne,sw,ne,ne,ne,ne,ne,n,n,ne,ne,s,ne,ne,ne,ne,n,n,se,ne,ne,ne,s,ne,n,ne,ne,ne,se,ne,n,ne,ne,sw,nw,n,ne,s,ne,n,nw,ne,s,ne,ne,n,ne,n,ne,n,n,ne,ne,n,se,ne,n,s,ne,n,n,s,ne,sw,se,n,sw,s,n,se,sw,sw,n,ne,n,n,n,n,nw,sw,n,n,n,n,n,n,ne,n,nw,s,n,n,sw,n,n,ne,n,n,n,n,n,nw,n,n,n,n,se,n,n,n,n,n,nw,ne,n,sw,s,s,n,nw,n,nw,sw,sw,n,nw,se,n,sw,n,nw,nw,sw,n,n,n,se,nw,n,nw,n,n,n,n,s,n,ne,n,n,nw,n,nw,s,n,nw,nw,n,ne,sw,nw,se,n,ne,n,se,nw,n,nw,nw,n,s,nw,n,n,nw,nw,n,se,nw,nw,n,nw,nw,nw,nw,s,nw,nw,n,nw,ne,n,nw,nw,nw,ne,nw,nw,n,nw,sw,nw,ne,nw,n,n,nw,nw,n,nw,nw,n,nw,n,nw,nw,nw,nw,nw,nw,ne,nw,nw,nw,nw,nw,n,nw,nw,nw,nw,nw,nw,se,nw,nw,nw,s,nw,nw,nw,nw,nw,nw,nw,nw,sw,nw,nw,nw,nw,ne,nw,nw,ne,nw,nw,nw,s,nw,nw,n,sw,nw,nw,nw,nw,se,nw,nw,nw,nw,nw,nw,nw,nw,nw,s,ne,nw,nw,nw,nw,nw,nw,sw,nw,nw,nw,ne,ne,sw,nw,nw,sw,nw,nw,s,ne,sw,sw,nw,ne,sw,nw,n,nw,sw,sw,sw,nw,nw,nw,sw,sw,nw,nw,sw,sw,nw,sw,nw,se,nw,nw,nw,sw,sw,s,nw,sw,sw,se,nw,n,n,sw,nw,s,nw,se,sw,sw,sw,sw,nw,nw,s,nw,sw,nw,sw,sw,sw,se,nw,ne,sw,sw,sw,sw,sw,sw,sw,sw,nw,nw,sw,sw,sw,sw,sw,sw,sw,n,nw,nw,sw,sw,sw,sw,se,nw,sw,nw,nw,sw,nw,sw,nw,sw,nw,s,sw,nw,sw,nw,sw,sw,sw,sw,sw,sw,nw,sw,ne,nw,nw,sw,sw,nw,ne,nw,n,sw,sw,sw,sw,sw,sw,sw,sw,se,sw,s,nw,ne,nw,sw,s,sw,sw,sw,sw,ne,n,sw,nw,ne,sw,nw,sw,sw,sw,sw,sw,s,sw,sw,n,s,n,sw,sw,sw,sw,n,sw,sw,sw,sw,se,sw,nw,sw,sw,s,sw,sw,sw,sw,n,sw,sw,sw,se,sw,sw,s,n,n,s,ne,n,ne,sw,sw,sw,n,sw,s,sw,n,sw,se,sw,s,nw,sw,sw,sw,sw,sw,sw,sw,sw,n,se,sw,sw,sw,n,s,sw,sw,sw,se,se,sw,se,sw,ne,s,sw,sw,sw,nw,se,sw,sw,sw,sw,s,sw,n,sw,sw,sw,sw,se,s,nw,se,sw,sw,ne,s,sw,sw,sw,sw,sw,sw,s,s,s,s,sw,sw,s,s,sw,n,se,sw,se,s,sw,ne,s,s,sw,n,s,s,sw,s,s,sw,sw,n,sw,s,s,ne,se,s,s,se,s,s,ne,s,se,sw,s,sw,sw,s,sw,s,s,s,nw,sw,s,s,s,sw,sw,s,s,sw,s,sw,se,s,s,s,sw,s,sw,s,nw,s,s,sw,s,s,s,s,n,s,sw,n,se,sw,sw,ne,sw,sw,s,s,sw,sw,s,s,s,s,s,s,s,nw,sw,s,ne,s,s,s,sw,s,n,ne,sw,ne,s,s,s,ne,s,s,s,s,s,s,s,s,nw,sw,n,sw,n,s,nw,sw,ne,sw,sw,s,s,sw,s,sw,s,s,s,s,sw,s,n,s,sw,sw,sw,s,s,s,s,sw,s,s,s,sw,s,s,s,s,sw,s,s,s,s,s,s,s,s,s,s,sw,sw,sw,s,n,s,s,nw,s,s,n,s,s,s,ne,s,s,s,nw,se,s,s,s,s,s,s,sw,se,ne,s,s,s,s,s,s,s,nw,s,se,s,s,s,s,s,s,s,s,s,s,n,s,s,ne,se,s,se,s,s,se,s,s,sw,s,s,s,se,s,n,s,s,s,s,s,s,s,s,s,s,se,se,ne,s,s,s,s,se,s,s,nw,n,sw,s,s,sw,s,ne,s,se,s,s,s,se,s,se,s,se,s,s,s,s,s,s,s,se,ne,se,ne,s,s,s,se,se,se,s,s,ne,se,s,se,se,s,s,s,s,sw,se,ne,s,s,s,s,s,s,s,s,n,sw,s,s,se,nw,s,s,s,s,s,s,s,s,s,s,nw,s,n,s,se,n,sw,se,s,se,n,se,s,s,se,s,s,s,s,s,se,nw,se,n,s,s,se,se,s,se,ne,nw,s,s,nw,se,se,s,s,se,se,se,nw,se,nw,n,se,s,se,nw,s,se,s,s,se,sw,se,se,s,se,s,se,s,se,se,se,s,s,se,nw,s,se,se,se,se,s,se,s,se,se,se,ne,se,se,s,n,se,s,nw,se,se,nw,se,s,s,ne,sw,se,se,sw,n,nw,se,se,se,s,s,se,se,se,se,sw,s,ne,s,se,s,n,se,se,se,s,s,s,s,se,se,se,ne,se,n,se,se,s,se,se,se,se,s,se,s,se,s,nw,se,s,se,se,s,se,se,s,se,se,se,se,se,se,se,se,se,se,se,se,nw,se,se,se,s,se,se,se,se,se,s,sw,se,se,se,s,se,se,se,se,se,se,se,se,se,se,se,n,se,se,se,se,se,s,se,se,nw,s,se,ne,n,se,se,se,se,se,n,n,se,ne,n,se,se,se,s,se,n,s,se,sw,se,s,sw,se,se,se,se,se,se,n,se,se,se,se,se,se,se,sw,se,se,se,s,se,nw,se,se,se,se,se,se,se,se,s,se,sw,se,se,ne,se,se,se,se,se,se,se,se,sw,nw,se,se,se,se,nw,se,se,s,se,se,n,nw,nw,se,n,se,se,se,se,s,se,se,nw,se,nw,se,n,se,se,se,nw,se,se,se,se,n,se,se,se,se,se,se,se,ne,n,se,ne,se,se,se,se,n,se,se,sw,se,ne,se,nw,se,nw,se,se,ne,n,ne,se,se,se,se,se,se,se,se,se,se,se,se,se,se,se,se,se,se,se,se,se,ne,ne,se,sw,se,se,se,se,se,s,se,nw,n,nw,se,sw,se,se,sw,se,se,se,se,ne,se,se,sw,se,se,se,se,se,se,ne,se,ne,sw,se,se,se,se,ne,ne,se,se,sw,se,se,se,sw,ne,ne,se,ne,nw,se,se,se,se,se,se,se,s,se,ne,se,ne,se,ne,s,ne,s,ne,se,ne,se,n,se,se,se,se,se,se,n,ne,se,ne,se,se,se,ne,se,se,s,sw,sw,se,nw,ne,ne,se,se,sw,ne,se,se,se,ne,ne,ne,se,ne,nw,se,ne,ne,s,n,s,ne,se,ne,se,ne,ne,se,ne,ne,se,se,s,se,se,nw,se,ne,se,ne,ne,se,se,ne,ne,se,se,se,ne,nw,ne,se,ne,ne,se,se,ne,ne,se,se,ne,nw,s,s,sw,ne,ne,sw,se,ne,ne,ne,s,n,se,nw,se,nw,sw,se,sw,se,se,ne,se,se,se,se,nw,se,se,ne,se,ne,se,n,n,ne,se,se,sw,ne,se,ne,se,ne,ne,ne,ne,ne,ne,ne,se,ne,se,ne,s,ne,se,se,ne,ne,se,ne,ne,se,se,ne,se,ne,s,ne,ne,se,ne,s,se,ne,ne,se,se,s,ne,ne,se,n,ne,ne,se,ne,nw,ne,ne,se,se,s,ne,se,se,ne,ne,se,ne,sw,se,ne,se,sw,se,ne,s,n,ne,se,se,ne,se,ne,ne,se,ne,se,se,ne,se,ne,ne,ne,ne,se,ne,ne,ne,sw,se,ne,s,s,ne,ne,ne,ne,ne,se,se,se,ne,sw,se,ne,ne,sw,ne,ne,ne,nw,se,s,ne,sw,ne,se,se,nw,ne,sw,se,ne,n,nw,se,nw,ne,ne,ne,n,s,se,se,ne,ne,se,sw,ne,ne,s,ne,ne,ne,se,ne,ne,sw,ne,sw,n,s,se,ne,se,ne,ne,ne,ne,ne,ne,ne,ne,nw,ne,se,se,sw,ne,ne,ne,ne,ne,se,nw,ne,se,ne,ne,s,ne,n,ne,ne,sw,ne,ne,ne,ne,se,ne,sw,se,ne,n,ne,n,ne,ne,ne,ne,ne,n,n,ne,sw,se,ne,se,ne,sw,ne,ne,s,se,ne,se,ne,ne,ne,ne,ne,n,se,ne,s,ne,ne,ne,se,ne,ne,ne,nw,ne,ne,ne,se,n,ne,ne,ne,ne,nw,nw,se,ne,ne,ne,ne,ne,ne,ne,sw,ne,ne,ne,ne,ne,ne,ne,ne,ne,ne,n,ne,ne,ne,ne,ne,s,ne,ne,ne,ne,ne,ne,ne,nw,ne,ne,ne,ne,ne,se,sw,se,ne,n,ne,ne,ne,ne,ne,ne,ne,ne,n,ne,s,ne,s,ne,ne,ne,sw,nw,ne,se,se,ne,ne,n,ne,ne,sw,ne,se,ne,ne,ne,n,ne,ne,ne,ne,ne,ne,ne,ne,ne,s,ne,ne,se,se,ne,ne,nw,ne,ne,se,s,ne,s,ne,n,ne,s,ne,ne,n,ne,ne,ne,s,ne,n,n,ne,sw,ne,s,ne,nw,ne,ne,ne,sw,sw,ne,s,ne,n,ne,ne,sw,ne,ne,ne,nw,ne,ne,ne,ne,s,ne,ne,n,ne,ne,ne,ne,ne,ne,ne,sw,ne,ne,sw,ne,s,nw,nw,ne,ne,ne,sw,ne,ne,se,n,ne,n,ne,ne,ne,ne,ne,ne,ne,se,ne,ne,sw,ne,ne,n,ne,se,nw,ne,ne,ne,ne,ne,ne,ne,sw,s,ne,ne,ne,ne,ne,ne,ne,sw,ne,se,ne,ne,ne,ne,ne,n,sw,ne,ne,ne,ne,ne,n,n,ne,n,ne,ne,ne,ne,n,sw,ne,n,ne,ne,ne,ne,ne,sw,nw,ne,ne,ne,ne,n,se,ne,ne,ne,ne,n,ne,n,ne,ne,ne,se,ne,ne,ne,se,ne,sw,n,ne,ne,n,n,ne,ne,nw,ne,ne,n,n,nw,ne,ne,ne,sw,n,ne,nw,ne,ne,nw,n,n,se,n,ne,n,ne,ne,n,n,nw,ne,sw,ne,n,n,s,n,ne,nw,n,ne,se,ne,n,ne,n,ne,s,ne,n,sw,n,n,ne,n,ne,ne,n,n,n,n,n,ne,ne,ne,s,s,ne,ne,n,n,ne,n,n,ne,ne,ne,n,se,ne,se,ne,ne,nw,ne,n,n,sw,ne,n,ne,ne,ne,n,ne,n,s,ne,ne,ne,n,n,nw,nw,n,ne,ne,n,ne,s,n,n,se,n,n,ne,ne,ne,ne,n,ne,n,ne,n,ne,ne,ne,ne,n,nw,n,ne,n,se,ne,ne,ne,n,n,ne,n,ne,n,sw,ne,n,n,sw,se,ne,n,nw,n,n,n,ne,sw,s,n,ne,sw,ne,ne,ne,ne,ne,n,ne,se,ne,n,s,n,n,ne,ne,s,n,n,ne,ne,n,n,n,n,n,n,ne,n,n,ne,ne,n,ne,n,n,ne,n,n,ne,ne,nw,n,n,n,ne,nw,ne,nw,ne,ne,n,n,n,n,s,s,n,n,ne,n,ne,n,n,n,ne,n,ne,n,n,ne,n,ne,n,se,n,n,ne,ne,ne,n,sw,n,ne,se,s,ne,n,nw,s,s,ne,ne,n,ne,n,ne,n,n,se,se,n,n,n,n,ne,sw,ne,n,s,n,n,ne,s,ne,ne,ne,n,n,ne,n,ne,ne,ne,n,n,n,n,ne,n,n,n,ne,se,n,ne,se,n,n,nw,se,n,n,ne,n,n,n,n,n,n,n,n,n,sw,n,n,ne,nw,n,n,n,n,n,ne,nw,n,se,ne,n,n,n,n,n,n,ne,n,n,n,n,n,ne,n,ne,ne,sw,n,n,ne,sw,n,ne,n,ne,n,n,n,n,n,se,n,ne,n,n,n,nw,ne,ne,n,n,n,n,n,n,nw,n,n,n,ne,s,se,ne,n,ne,ne,n,n,n,n,n,n,n,n,ne,n,n,n,ne,n,ne,ne,n,ne,nw,n,n,n,n,n,n,s,n,n,ne,n,n,s,n,ne,n,n,n,nw,n,n,n,n,n,n,ne,n,n,n,n,ne,ne,ne,s,ne,se,n,n,n,s,n,n,s,n,n,sw,n,nw,ne,n,n,n,s,n,n,n,n,n,n,n,nw,n,n,n,n,n,n,n,n,n,ne,n,n,n,n,ne,n,ne,n,n,n,n,nw,ne,n,n,n,n,n,n,n,n,se,n,s,sw,se,n,n,sw,n,n,n,sw,ne,s,n,n,n,n,nw,n,n,se,nw,n,n,n,sw,sw,n,n,n,n,n,n,n,n,ne,sw,n,n,n,ne,n,n,nw,s,nw,n,n,n,ne,ne,n,n,sw,s,n,nw,n,n,n,n,n,n,se,ne,n,n,n,n,s,ne,ne"""