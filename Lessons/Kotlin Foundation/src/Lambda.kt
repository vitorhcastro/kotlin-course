fun main(args: Array<String>) {

    val printMessage = { message: String -> println(message) }
    printMessage("Hello World!")

    val sumA = { x: Int, y: Int -> x + y }
    println(sumA(5, 3))

    val sumB: (Int, Int) -> Int = { x, y -> x + y }
    println(sumB(10, 4))

    fun downloadData(url: String, completion: () -> Unit) {
        // sent a download request
        // we got back data
        // there were no network errors
        completion()
    }

    // call downloadDataFunction
    downloadData("fakeUrl.com", {
        println("The code in this block will only run after the completion()")
    })

    fun downloadCarData(url: String, completion: (Car) -> Unit) {
        // sent a download request
        // we got back car data
        val car = Car("Tesla", "Model X", "Blue")
        completion(car)
    }

    downloadCarData("fakeUrl.com") { car ->
        println(car.color)
        println(car.make)
        println(car.model)
    }

    downloadCarData("fakeUrl.com") {
        println(it.color)
        println(it.make)
        println(it.model)
    }

    fun downloadTruckData(url: String, completion: (Truck?, Boolean) -> Unit) {
        // make the web request
        // we get the results back
        val webRequestSuccess = false
        if (webRequestSuccess) {
            val truck = Truck("Ford", "F-150", 4000)
            completion(truck, true)
        } else {
            completion(null, false)
        }
    }

    downloadTruckData("fakeUrl.com") { truck, success ->
        if (success == true) {
            truck?.tow()
        } else {
            println("Something went wrong!")
        }
    }
}