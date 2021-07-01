open class Room(val name: String) {
    protected open val dangerLevel = 5
    var monsterList = listOf(Goblin(), Bone(), Troll())
    open var monster: Monster? = monsterList.shuffled().first()

    fun description() = "位置: $name" + "   危險級別: $dangerLevel\n" +
    "出現事件: ${monster?.description ?: "沒有"}"

    open fun load() = "一望無際的景色"
}

open class TownSquare : Room("城鎮中心") {
    override val dangerLevel = super.dangerLevel - 3
    private var bellSound = "GWONG~~"
    override var monster: Monster? = Rogue()

    final override fun load() = "居民們因為你的到來而歡呼\n${ringBell()}"
    private fun ringBell() = "$bellSound 鐘樓響起鐘聲宣布你的到來"
}

open class Goddess : Room("女神像") {
    override val dangerLevel = super.dangerLevel - 5
    override var monster: Monster? = null

    final override fun load() = "受女神庇護之地，請享受短暫悠閒時光"
}