package com.example.intotheabyss.game

import com.example.intotheabyss.dungeonassets.Tile
import com.example.intotheabyss.game.monster.Monster
import com.example.intotheabyss.game.player.Player

class GameState {
    var myPlayer: Player = Player()
    var level = arrayOf<Array<Tile>>()
    var loading: Boolean = true
    var playersInLevel: HashMap<String, Player> = hashMapOf()
    var monstersInLevel: HashMap<String, Monster> = hashMapOf()
}