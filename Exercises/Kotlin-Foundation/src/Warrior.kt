class Warrior(name: String, age: String, gender: String, val knight: Boolean) : Player(name, age, gender) {
    override fun introduce() {
        super.introduce()
        if (knight) {
            println("I'll protect you all my life.")
        } else {
            println("I live for battle.")
        }
    }
}