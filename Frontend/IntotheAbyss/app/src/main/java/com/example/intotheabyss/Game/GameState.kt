package com.example.intotheabyss.game

import android.graphics.BitmapFactory
import com.example.intotheabyss.R
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
                myPlayer.setX(yLocation)
                myPlayer.setY(xLocation)
                break
            } else {
                xLocation = Random.nextInt(0, 100)
                yLocation = Random.nextInt(0, 50)
            }
        }
    }
}