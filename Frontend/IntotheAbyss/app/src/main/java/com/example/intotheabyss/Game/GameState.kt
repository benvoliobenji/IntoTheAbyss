package com.example.intotheabyss.game

import com.example.intotheabyss.dungeonassets.Tile
import com.example.intotheabyss.player.Player
import kotlin.random.Random

class GameState {
    var myPlayer: Player = Player()
    var level = arrayOf<Array<Tile>>()

    fun newLevel() {
        var xLocation = Random.nextInt(0, 100)
        var yLocation = Random.nextInt(0, 50)

        while(true) {
            if (level[yLocation][xLocation].isPassable) {
                myPlayer.x = xLocation
                myPlayer.y = yLocation
                break
            } else {
                xLocation = Random.nextInt(0, 100)
                yLocation = Random.nextInt(0, 50)
            }
        }
    }
}