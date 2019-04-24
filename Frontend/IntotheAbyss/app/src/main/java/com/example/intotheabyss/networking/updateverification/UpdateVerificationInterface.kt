package com.example.intotheabyss.networking.updateverification

import com.example.intotheabyss.game.GameState
import com.example.intotheabyss.networking.Network
import com.example.intotheabyss.networking.volleynetwork.VolleyNetworkInterface

/**
 * An Interface for UpdateVerfication. This is designed to allow for dependency injection.
 * @author Benjamin Vogel
 */
interface UpdateVerificationInterface {
    fun verifyGameState(gameState: GameState, network: Network,
                        volleyNetworkInterface: VolleyNetworkInterface): UpdateVerificationType
}