package com.example.intotheabyss.game

import com.example.intotheabyss.GameEvent.GameEvent
import com.example.intotheabyss.dungeonassets.Tile
import com.example.intotheabyss.game.monster.Monster
import com.example.intotheabyss.game.player.Player
import java.util.*
import kotlin.collections.HashMap

class GameState {
    var myPlayer: Player = Player()
    var level = arrayOf<Array<Tile>>()
    var loading: Boolean = true
    var playersInLevel: HashMap<String, Player> = hashMapOf()
    var monstersInLevel: HashMap<String, Monster> = hashMapOf()
    //var charactersInLevel: HashMap<String, EntityCharacter> = hashMapOf()
    var eventQueue = LinkedList<GameEvent>()
}