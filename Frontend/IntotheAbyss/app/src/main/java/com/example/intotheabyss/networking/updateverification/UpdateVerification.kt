package com.example.intotheabyss.networking.updateverification

import com.example.intotheabyss.game.GameState
import com.example.intotheabyss.networking.Network
import com.example.intotheabyss.networking.volleynetwork.VolleyNetworkInterface

class UpdateVerification(var posX: Int, var posY: Int, var floorNum: Int): UpdateVerificationInterface {
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