package com.example.intotheabyss.networking.updateverification

import com.example.intotheabyss.game.GameState
import com.example.intotheabyss.networking.Network
import com.example.intotheabyss.networking.volleynetwork.VolleyNetworkInterface

interface UpdateVerificationInterface {
    fun verifyGameState(gameState: GameState, network: Network,
                        volleyNetworkInterface: VolleyNetworkInterface): UpdateVerificationType
}