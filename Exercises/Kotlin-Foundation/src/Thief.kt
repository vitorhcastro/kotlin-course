class Thief(name: String, age: String, gender: String, val assassin: Boolean) : Player(name, age, gender) {
    override fun introduce() {
        super.introduce()
        if (assassin) {
            println("I can kill my target without anybody even noticing.")
        } else {
            println("I take in the shadows.")
        }
    }
}