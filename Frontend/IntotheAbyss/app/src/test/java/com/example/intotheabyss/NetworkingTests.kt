package com.example.intotheabyss

import com.example.intotheabyss.game.GameState
import com.example.intotheabyss.game.entity.entityaction.Attack
import com.example.intotheabyss.game.entity.entityaction.EntityAction
import com.example.intotheabyss.game.entity.entityaction.EntityActionType
import com.example.intotheabyss.game.entity.player.Player
import com.example.intotheabyss.networking.Network
import com.google.gson.Gson
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.whenever
import org.junit.Test
import java.util.*

class NetworkingTests {
    companion object {
        var gameState = GameState()
        var mockNetwork =  mock<Network>()
        var networkQueue: Queue<EntityAction> = LinkedList<EntityAction>()
    }

    @Test
    fun testAttackFunction() {
        var attackingPlayer = Player("attacker", "1", 10, 0, 12, 13)
        var attackedPlayer = Player("attacked", "2", 10, 0, 11, 13)

        gameState.entitiesInLevel[attackingPlayer.ID] = attackingPlayer
        gameState.entitiesInLevel[attackedPlayer.ID] = attackedPlayer

        whenever(mockNetwork.attackPlayer("attacker", "attacked", 5)).then {
            val gson = Gson()
            val attack = Attack("attacked", 5)
            val jsonPacket = gson.toJson(attack)
            val attackPacket = EntityAction("attackerID", EntityActionType.ATTACK, gameState.myPlayer.floor, jsonPacket)
            networkQueue.add(attackPacket)
        }


    }
}