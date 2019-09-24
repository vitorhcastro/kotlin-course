fun main(args: Array<String>) {
    var name: String = "JonnyB"
    // name = null

    var nullableName: String? = "Do I really exist?"
    // nullableName = null

    // Null check
    var length = 0
    if (nullableName != null) {
        length = nullableName.length
    } else {
        length = -1
    }
    println(length)

    val l = if (nullableName != null) nullableName.length else -1
    println(l)

    // Second method is Safe Call Operator ?

    println(nullableName?.length)

    // Third method is Elvis Operator

    val len = nullableName?.length ?: -1
    val noName = nullableName ?: "No one knows me..."
    println(noName)

    // !!
    println(nullableName!!.length)
}