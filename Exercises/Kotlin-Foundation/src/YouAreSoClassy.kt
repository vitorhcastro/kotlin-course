fun main(args: Array<String>) {
    var kyle = Warrior("Kyle", "16", "male", false)
    kyle.introduce()
    kyle.attack()
    kyle.dodge()
    kyle.defend()
    kyle.walk()

    var kiara = Thief("Kiara", "19", "female", true)
    kiara.introduce()
    kiara.attack()
    kiara.dodge()
    kiara.defend()
    kiara.walk()
}