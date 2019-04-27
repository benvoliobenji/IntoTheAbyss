package com.example.intotheabyss.networking.updateverification

import android.util.Log
import com.example.intotheabyss.game.GameState
import com.example.intotheabyss.game.event.AttackEvent
import com.example.intotheabyss.game.event.EventType
import com.example.intotheabyss.game.event.KickEvent
import com.example.intotheabyss.game.event.RequestEvent
import com.example.intotheabyss.networking.Network
import com.example.intotheabyss.networking.volleynetwork.VolleyNetworkInterface

/**
 * UpdateVerification is a class that checks for differences between its values (x, y, floor) and those stored in
 * GameState. If they are different, then UpdateVerification will call methods to the different networks to inform the
 * server and get correct information to GameState.
 *
 * @constructor Constructs an UpdateVerification object with the parameters as initial reference points.
 * @param posX The initial x reference point to verify against GameState.
 * @param posY The initial y reference point to verify against GameState.
 * @param floorNum The initial floor reference point to verify against GameState.
 * @author Benjamin Vogel
 */
class UpdateVerification(var posX: Int, var posY: Int, var floorNum: Int): UpdateVerificationInterface {
    /**
     * Given a GameState object, UpdateVerification will run GameState's values against its own. If there is a
     * difference in any of the values, call the corresponding network methods to notify the server. UpdateVerification
     * will then update it's own values to keep current with GameState.
     *
     * @param gameState The GameState object UpdateVerification should run against.
     * @param network The Kryonet network instance to call updates the the server if needed.
     * @param volleyNetworkInterface The VolleyNetwork instance to call for a new floor if needed.
     * @return An UpdateVerificationType that lets the user know if anything was updated.
     */
    override fun verifyGameState(gameState: GameState, network: Network,
                                 volleyNetworkInterface: VolleyNetworkInterface): UpdateVerificationType {

        // Handle if the player has moved
        if ((posX != gameState.myPlayer.x) or (posY != gameState.myPlayer.y)) {
            posX = gameState.myPlayer.x
            posY = gameState.myPlayer.y
            network.updatePosition(gameState.myPlayer.ID, floorNum - 1, floorNum, posX, posY)

            return UpdateVerificationType.POSITION
        }

        // Handle if the player has moved floors
        if (floorNum != gameState.myPlayer.floor) {
            floorNum = gameState.myPlayer.floor
            volleyNetworkInterface.retrieveNewDungeonLevel(floorNum, network)
            return UpdateVerificationType.LEVEL
        }

        // Handle if there is something in the eventQueue
        if (gameState.eventQueue.isNotEmpty()) {
            var event = gameState.eventQueue.peek()
            when(event.type) {
                EventType.ATTACK -> {
                    var attack: AttackEvent = gameState.eventQueue.remove() as AttackEvent
                    network.attackPlayer(attack.performerID, attack.performedID, attack.damage)
                }
                EventType.KICK -> {
                    val kick: KickEvent = gameState.eventQueue.remove() as KickEvent
                    network.kickPlayer(kick)
                }
                EventType.REQUEST -> {
                    val request: RequestEvent = gameState.eventQueue.remove() as RequestEvent
                    network.requestPlayer(request)
                }
                EventType.DISCONNECT -> {
                    network.disconnect()
                    gameState.eventQueue.remove()
                }
                else -> Log.i("EventType", "Unknown EventType" + event.type)
            }
            return UpdateVerificationType.EVENT
        }
        return UpdateVerificationType.NONE
    }

    /**
     * Iterates through the entities in the current level, and removes them from the hash map if their health is at
     * or below 0.
     * @param gameState The GameState object the client is currently using.
     */
    override fun cleanUpEntities(gameState: GameState) {
        for(key in gameState.entitiesInLevel.keys) {
            val entity = gameState.entitiesInLevel[key]

            if (entity!!.health <= 0) {
                gameState.entitiesInLevel.remove(key)
            }
        }
    }
}