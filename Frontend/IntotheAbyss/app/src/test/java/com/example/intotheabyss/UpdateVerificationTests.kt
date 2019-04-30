package com.example.intotheabyss

import com.example.intotheabyss.game.GameState
import com.example.intotheabyss.game.entity.player.Player
import com.example.intotheabyss.networking.Network
import com.example.intotheabyss.networking.updateverification.UpdateVerification
import com.example.intotheabyss.networking.updateverification.UpdateVerificationType
import com.example.intotheabyss.networking.volleynetwork.VolleyNetwork
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.times
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.whenever
import org.junit.Assert
import org.junit.Before
import org.junit.Test

class UpdateHandlerTests {
    companion object {
        var gameState = GameState()
        var mockVolleyNetwork = mock<VolleyNetwork>()
        var mockKryoNetwork = mock<Network>()
        var updateVerification = UpdateVerification(gameState.myPlayer.x, gameState.myPlayer.y,
            gameState.myPlayer.floor)
    }

    @Before
    fun prepareTest() {
        gameState = GameState()
        updateVerification = UpdateVerification(gameState.myPlayer.x, gameState.myPlayer.y,
            gameState.myPlayer.floor)
    }

    @Test
    fun updateVerificationReceivesCorrectInfoOnNewPlayer() {
        var fakeVolleyNetwork = FakeVolleyNetwork(gameState)
        whenever(mockVolleyNetwork.retrievePlayerData("1", true,"player")).then {
            fakeVolleyNetwork.retrievePlayerData("1", true,"player")
        }

        updateVerification.verifyGameState(gameState, mockKryoNetwork, mockVolleyNetwork)

        Assert.assertEquals(updateVerification.posX, gameState.myPlayer.x)
        Assert.assertEquals(updateVerification.posY, gameState.myPlayer.y)
        Assert.assertEquals(updateVerification.floorNum, gameState.myPlayer.floor)
    }

    @Test
    fun updateVerificationReceivesCorrectInfoOnPlayerInDatabase() {
        var fakeVolleyNetwork = FakeVolleyNetwork(gameState)
        whenever(mockVolleyNetwork.retrievePlayerData("2", false,"oldPlayer")).then {
            fakeVolleyNetwork.retrievePlayerData("2", false, "oldPlayer")
        }

        updateVerification.verifyGameState(gameState, mockKryoNetwork, mockVolleyNetwork)

        Assert.assertEquals(updateVerification.posX, gameState.myPlayer.x)
        Assert.assertEquals(updateVerification.posY, gameState.myPlayer.y)
        Assert.assertEquals(updateVerification.floorNum, gameState.myPlayer.floor)
    }

//    @Test
//    fun updateVerificationInvokesUpdatePositionWhenPlayerMoves() {
//        gameState.myPlayer.x = 20
//        gameState.myPlayer.y = 55
//        updateVerification.verifyGameState(gameState, mockKryoNetwork, mockVolleyNetwork)
//
//        verify(mockKryoNetwork, times(1)).updatePosition(gameState.myPlayer.ID,
//            gameState.myPlayer.floor - 1, gameState.myPlayer.floor, gameState.myPlayer.x, gameState.myPlayer.y)
//    }

    @Test
    fun updateVerificationInvokesNewDungeonLevelOnFloorTransition() {
        gameState.myPlayer.floor = 1
        updateVerification.verifyGameState(gameState, mockKryoNetwork, mockVolleyNetwork)

        verify(mockVolleyNetwork, times(1)).retrieveNewDungeonLevel(gameState.myPlayer.floor,
            mockKryoNetwork)

    }

    @Test
    fun updateVerificationShouldDoNothingWhenNothingHappens() {
        val verification = updateVerification.verifyGameState(gameState, mockKryoNetwork, mockVolleyNetwork)
        Assert.assertEquals(verification, UpdateVerificationType.NONE)
    }

    @Test
    fun updateVerificationShouldNotRemoveAliveEnemies() {
        gameState.entitiesInLevel["player2"] = Player()
        gameState.entitiesInLevel["player2"]!!.health = 10
        updateVerification.cleanUpEntities(gameState)

        Assert.assertEquals(1, gameState.entitiesInLevel.size)
    }

    @Test
    fun updateVerificationSuccessfullyCleansUpDeadEnemies() {
        gameState.entitiesInLevel["player2"] = Player()
        gameState.entitiesInLevel["player2"]!!.health = -1
        updateVerification.cleanUpEntities(gameState)

        Assert.assertEquals(true, gameState.entitiesInLevel.isEmpty())
    }
}