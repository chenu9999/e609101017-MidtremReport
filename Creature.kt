import java.util.*

interface Fightable {
    var healthPoints: Int
    val diceCount: Int
    val diceSides: Int
    val damageRoll: Int
        get() = (0 until diceCount).map {
            Random().nextInt(diceSides) + 1
        }.sum()
    //跑迴圈0..diceCount-1次 產生清單內容 隨機數值為0~5+1 最後加總

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
             description: String = "一隻討厭的地精",
             healthPoints: Int = 30) : Monster(name, description, healthPoints)
{
    override val diceCount = 2
    override val diceSides = 8
}