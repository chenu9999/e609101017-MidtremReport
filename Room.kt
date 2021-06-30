open class Room(val name: String) {
    protected open val dangerLevel = 5
    open var monster: Monster? = Goblin()

    fun description() = "位置: $name" + "   危險級別: $dangerLevel\n" +
    "出現怪物: ${monster?.description ?: "沒有"}"

    open fun load() = "這裡沒什麼好看的"
}

open class TownSquare : Room("城鎮中心") {
    override val dangerLevel = super.dangerLevel - 3
    private var bellSound = "GWONG~~"
    override var monster: Monster? = null

    final override fun load() = "居民們因為你的到來而歡呼\n${ringBell()}"
    private fun ringBell() = "$bellSound 鐘樓響起鐘聲宣布你的到來"
}