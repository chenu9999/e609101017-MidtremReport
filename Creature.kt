import java.util.*

interface Fightable {
    var healthPoints: Int
    val diceCount: Int
    val diceSides: Int
    val damageRoll: Int
        get() = (0 until diceCount).map {
            Random().nextInt(diceSides) + 1
        }.sum()

    fun attack(opponent: Fightable): Int
}

abstract class Monster(val name: String,
                       val description: String,
                       override var healthPoints: Int) : Fightable {
    override fun attack(opponent: Fightable): Int {
        val damageDealt = damageRoll
        opponent.healthPoints -= damageDealt
        return damageDealt
    }
}

class Goblin(name: String = "小妖精",
             description: String = "小妖精從暗處露出冷笑",
             healthPoints: Int = 30) : Monster(name, description, healthPoints)
{
    override val diceCount = 2
    override val diceSides = 8
}

class Bone(name: String = "骷髏",
             description: String = "骷髏從下方破土而出",
             healthPoints: Int = 45) : Monster(name, description, healthPoints)
{
    override val diceCount = 3
    override val diceSides = 9
}

class Troll(name: String = "巨魔",
           description: String = "巨魔從上方俯視你",
           healthPoints: Int = 60) : Monster(name, description, healthPoints)
{
    override val diceCount = 1
    override val diceSides = 60
}

class Rogue(name: String = "地痞流氓",
            description: String = "地痞流氓從暗巷中露出貪婪的目光",
            healthPoints: Int = 60) : Monster(name, description, healthPoints)
{
    override val diceCount = 1
    override val diceSides = 3
}