package com.example.intotheabyss.game

import com.example.intotheabyss.dungeonassets.Tile
import com.example.intotheabyss.game.entity.Entity
import com.example.intotheabyss.game.entity.player.Player

class GameState {
    var myPlayer: Player = Player()
    var level = arrayOf<Array<Tile>>()
    var loading: Boolean = true
    var entitiesInLevel: HashMap<String, Entity> = hashMapOf()
}