open class Player(val name: String, val age: String, val gender: String) {
    fun attack() {
        println("I'm gonna hit you!")
    }

    fun defend() {
        println("I've barely felt that...")
    }

    fun dodge() {
        println("You'll never even touch me.")
    }

    fun walk() {
        println("I'm outta here.")
    }

    open fun introduce(){
        println("Hello. My name is $name. I'm a $age year old $gender.")
    }
}