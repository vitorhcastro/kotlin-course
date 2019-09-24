fun main(args: Array<String>) {
    val rebels = arrayListOf("Leiah", "Luke", "Han Solo", "Mon Mothma")

    val rebelVehicles =
        hashMapOf("Han Solo" to "Millenium Falcon", "Luke" to "Landspeeder", "Boba Fett" to "Rocket Pack")

    for (rebel in rebels) {
        println("Name: $rebel")
    }

    for ((key, value) in rebelVehicles) {
        println("$key gets around in their $value")
    }

    var x = 10

    while (x > 0) {
        println(x)
        x--
    }

    do {
        println(x)
        x++
    } while (x <= 10)
}