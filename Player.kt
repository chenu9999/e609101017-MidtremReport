import java.io.File

class Player(_name: String,
             override var healthPoints: Int = 100,
             val isBlessed: Boolean,
             private val isImmortal: Boolean) :Fightable {
    var name = _name
        get() = "來自$hometown" + "的${field.capitalize()}"
        private set(value) {
            field = value.trim()
        }

    val hometown by lazy { selectHometown() }
    var currentPosition = Coordinate(0, 0)


    init {
        require(healthPoints > 0, {"血量必須大於0"})
        require(name.isNotBlank(), {"必須設定玩家名字"})
    }

    constructor(name: String) : this(name,
        isBlessed = true,
        isImmortal = false) {
        if (name.toLowerCase() == "kar") healthPoints = 40
    }

    private fun selectHometown() = File("data/towns")
        .readText()
        .split("\r\n")
        .shuffled()
        .first()

    fun auraColor(): String {
        val auraVisible = isBlessed && healthPoints > 50 || isImmortal
        val karma = (Math.pow(Math.random(), (110 - healthPoints) / 100.0) * 20).toInt()
        val auraColor = if (auraVisible) when (karma) {
            in 16..20 -> "綠色光環"
            in 11..15 -> "紫色光環"
            in 6..10 -> "橘黃色光環"
            in 0..5 -> "紅色光環"
            else -> "無光環"
        } else "無光環"
        return auraColor
    }

    fun formatHealthStatus() =
        when (healthPoints) {
            100 -> "狀態極佳"
            in 90..99 -> "有一些小擦傷"
            in 75..89 -> if (isBlessed) {
                "雖有一些傷口，但恢復很快"
            } else {
                "有一些傷口"
            }
            in 15..74 -> "嚴重受傷"
            else -> "狀態不妙"
        }

    fun castFireball(numFireBalls: Int = 2): Int {
        println("已喝下$numFireBalls" + "杯Fireball")
        return numFireBalls
    }

    override val diceCount = 3

    override val diceSides = 6

    override fun attack(opponent: Fightable): Int {
        val damageDealt = if (isBlessed) {
            damageRoll * 2
        } else {
            damageRoll
        }
        opponent.healthPoints -= damageDealt
        return damageDealt
    }
}