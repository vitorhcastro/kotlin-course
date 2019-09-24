fun main(args: Array<String>) {
    val str = "May the force be with you."
    println(str)

    println()

    val rawCrawl = """A long time ago,
        |in a galaxy
        |far, far, away...
        |BUMM BUMM BUMMMM
    """.trimMargin()
    println(rawCrawl)

    println()

    for (char in str) {
        println(char)
    }

    println()

    val contentEquals = str.contentEquals("May the force be with you.")
    println(contentEquals)

    println()

    val contains = str.contains("Force", false)
    println(contains)

    println()

    val uppercase = str.toUpperCase()
    val lowercase = str.toLowerCase()
    println(uppercase)
    println(lowercase)

    println()

    val num = 6
    val stringNum = num.toString()
    println(stringNum)

    println()

    val subsequence = str.subSequence(4, 13)
    println(subsequence)

    println()

    val luke = "Luke Skywalker"
    val lightSaberColor = "green"
    val vehicle = "landspeeder"
    val age = 27

    println(luke + " has a " + lightSaberColor + " lightsaber and drives a " + vehicle + "and is " + age + " years old.")
    println("$luke has a $lightSaberColor lightsaber and drives a $vehicle and is $age years old.")

    println()
    println("Luke's full name $luke has ${luke.length} characters.")
}