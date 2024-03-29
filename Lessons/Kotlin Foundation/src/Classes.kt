//class Car constructor(var make: String, val model: String, val color: String) {
//
//    fun accelerate() {
//        println("vroom vroom")
//    }
//
//    fun details(){
//        println("This is a $color $make $model")
//    }
//}
//
//class Truck(var make: String, val model: String, val towingCapacity: Int) {
//    fun tow(){
//        println("taking the horses to the rodeo")
//    }
//
//    fun details(){
//        println("The $make $model has a towingCapacity of ${towingCapacity} kg")
//    }
//}

open class Vehicle(val make: String, val model: String) {
    open fun accelerate() {
        println("vroom vroom")
    }

    fun park() {
        println("parking the vehicle")
    }

    fun brake() {
        println("STOP")
    }
}

class Car(make: String, model: String, var color: String) : Vehicle(make, model) {
    override fun accelerate() {
        println("We are going ludicrous mode!")
        super.accelerate()
    }
}

class Truck(make: String, model: String, var towingCapacity: Int) : Vehicle(make, model) {
    fun tow() {
        println("headed to the mountain")
    }
}

fun main(args: Array<String>) {
    val tesla = Car("Tesla", "ModelS", "Red")
    tesla.accelerate()

    val truck = Truck("Ford", "F-150", 4000)

//    val car = Car("Toyota", "Avensis", "gray")
//    println(car.make)
//    println(car.model)
//    car.accelerate()
//    car.details()
//
//    val truck = Truck("Ford", "F-150", 4000)
//    truck.tow()
//    truck.details()
}