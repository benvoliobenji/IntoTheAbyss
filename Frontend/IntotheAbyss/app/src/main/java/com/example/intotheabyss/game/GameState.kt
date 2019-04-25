package com.example.intotheabyss.game

import com.example.intotheabyss.dungeonassets.Tile
import com.example.intotheabyss.game.entity.Entity
import com.example.intotheabyss.game.entity.player.Player
import com.example.intotheabyss.game.event.Event
import java.util.*
import kotlin.collections.HashMap

/**
 * GameState acts as the barrier between the front end game logic and the back end networking logic. Both ends utilize
 * listeners which will constantly check and modify GameState based on what the server is telling the client or vice-
 * versa.
 *
 * GameState only holds values/objects/hashmaps and does not contain any methods to manipulate data.
 *
 * Doing this allows for a greater degree of modularity as the game logic has no knowledge or access to manipulate the
 * networking, and the networking should have no knowledge of the game logic itself. By doing this we could theoretically
 * swap out the game logic or the networking and the other parts should be none the wiser and the game should keep on
 * running just as it had before.
 *
 * @author Benjamin Vogel
 */
class GameState {
    var myPlayer: Player = Player()
    var level = arrayOf<Array<Tile>>()
    var loading: Boolean = true
    var entitiesInLevel: HashMap<String, Entity> = hashMapOf()
    var eventQueue: Queue<Event> = LinkedList<Event>()
}