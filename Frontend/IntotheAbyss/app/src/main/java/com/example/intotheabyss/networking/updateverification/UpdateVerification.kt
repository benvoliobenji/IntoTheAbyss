package com.example.intotheabyss.networking.updateverification

import com.example.intotheabyss.game.GameState
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
        if ((posX != gameState.myPlayer.x) or (posY != gameState.myPlayer.y)) {
            posX = gameState.myPlayer.x
            posY = gameState.myPlayer.y
            network.updatePosition(gameState.myPlayer.ID, floorNum, posX, posY)

            return UpdateVerificationType.POSITION
        }

        if (floorNum != gameState.myPlayer.floor) {
            floorNum = gameState.myPlayer.floor
            volleyNetworkInterface.retrieveNewDungeonLevel(floorNum, network)
            return UpdateVerificationType.LEVEL
        }
        return UpdateVerificationType.NONE
    }
}