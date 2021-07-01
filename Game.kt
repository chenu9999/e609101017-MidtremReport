import kotlin.system.exitProcess

fun main() {
    Game.play()
}

object Game {
    private val player = Player("Madrigal")
    private var currentRoom: Room = TownSquare()

    private var worldMap = listOf(
        listOf(currentRoom, Room("平原"), Room("山丘")),
        listOf(Room("山谷"), Room("濕地"), Room("洞穴")),
        listOf(Room("森林"), Goddess(), Room("高原")))


    init {
        println("歡迎，冒險者")
        player.castFireball(5)
    }

    fun play() {
        while (true) {
            println(currentRoom.description())
            println(currentRoom.load())

            //玩家狀態
            printPlayerStatus(player)

            print(">>> 輸入你的命令: ")
            val order = readLine()
            println(GameInput(order).processCommand())
            println("------------------------------------------------------------------")
            if (order == "quit" || order == "exit") break
        }
    }

    private fun printPlayerStatus(player: Player) {
        val statusFormatString = "(血量:${player.healthPoints}) (光環顏色:${player.auraColor()}) " +
                "(運氣:${if (player.isBlessed) "好" else "壞"}) -> ${player.name} ${player.formatHealthStatus()}"
        println(statusFormatString)
    }

    private class GameInput(arg: String?) {
        private val input = arg ?: ""
        val command = input.split(" ")[0]
        val argument = input.split(" ").getOrElse(1, { "" })

        fun processCommand() = when (command.toLowerCase()) {
            "fight" -> fight()
            "move" -> move(argument)
            "quit","exit" -> quit(player)
            "map" -> printMap(player)
            "ring" -> if (currentRoom.name == "城鎮中心") {ringBell("GWONG~~")} else {"此處無法敲鐘"}
            else -> commandNotFound()
        }

        private fun commandNotFound() = "不正確的命令"

        private fun quit(player: Player) = "再見，${player.name}，歡迎再來玩"
    }

    private fun move(directionInput: String) =
        try {
            val direction = Direction.valueOf(directionInput.toUpperCase())
            val newPosition = direction.updateCoordinate(player.currentPosition)
            if (!newPosition.isInBounds) {
                throw IllegalStateException("$direction" + "超過邊界")
            }

            val newRoom = worldMap[newPosition.y][newPosition.x]
            player.currentPosition = newPosition
            currentRoom = newRoom
            "你往$direction" + "移動到${newRoom.name}" + "\n${newRoom.load()}"
        } catch (e: Exception) {
            "無效方向: $directionInput"
        }

    private fun fight() = currentRoom.monster?.let {
        while (player.healthPoints > 0 && it.healthPoints > 0) {
            slay(it)
            Thread.sleep(1000) //延遲
        }
        "戰鬥結束"
    } ?: "這裡沒有怪物"

    private fun slay(monster: Monster) {
        println("${monster.name} 對 ${player.name} 造成 ${monster.attack(player)} 傷害")
        println("${player.name} 對 ${monster.name} 造成 ${player.attack(monster)} 傷害")

        if (player.healthPoints <= 0) {
            println(">>>> 你已經被打敗，感謝你的參與 <<<<")
            exitProcess(0)
        }

        if (monster.healthPoints <= 0) {
            println(">>>> ${monster.name} 已經被打敗 <<<<")
            currentRoom.monster = null
        }
    }

    private fun printMap(player: Player) {
        val x: Int = player.currentPosition.x
        val y: Int = player.currentPosition.y
        println("目前位置座標:$x,$y")
        for (i in 0..1) {
            for (j in 0..2) {
                if (j == x && i == y) {
                    print("X ")
                } else {
                    print("O ")
                }
                if (i == 1 && j == 1) break
            }
            println()
        }
    }

    fun ringBell(bellSound: String) = "$bellSound 鐘樓響起鐘聲宣布你的到來"
}