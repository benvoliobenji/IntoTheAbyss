package com.example.intotheabyss

import com.example.intotheabyss.game.GameState
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
            gameState.myPlayer.floorNumber)
    }

    @Before
    fun prepareTest() {
        gameState = GameState()
        updateVerification = UpdateVerification(gameState.myPlayer.x, gameState.myPlayer.y,
            gameState.myPlayer.floorNumber)
    }

    @Test
    fun updateVerificationReceivesCorrectInfoOnNewPlayer() {
        var fakeVolleyNetwork = FakeVolleyNetwork(gameState)
        whenever(mockVolleyNetwork.createNewPlayer("1")).then {
            fakeVolleyNetwork.createNewPlayer("1")
        }

        updateVerification.verifyGameState(gameState, mockKryoNetwork, mockVolleyNetwork)

        Assert.assertEquals(updateVerification.posX, gameState.myPlayer.x)
        Assert.assertEquals(updateVerification.posY, gameState.myPlayer.y)
        Assert.assertEquals(updateVerification.floorNum, gameState.myPlayer.floorNumber)
    }

    @Test
    fun updateVerificationReceivesCorrectInfoOnPlayerInDatabase() {
        var fakeVolleyNetwork = FakeVolleyNetwork(gameState)
        whenever(mockVolleyNetwork.retrievePlayerData("2")).then {
            fakeVolleyNetwork.retrievePlayerData("2")
        }

        updateVerification.verifyGameState(gameState, mockKryoNetwork, mockVolleyNetwork)

        Assert.assertEquals(updateVerification.posX, gameState.myPlayer.x)
        Assert.assertEquals(updateVerification.posY, gameState.myPlayer.y)
        Assert.assertEquals(updateVerification.floorNum, gameState.myPlayer.floorNumber)
    }

    @Test
    fun updateVerificationInvokesUpdatePositionWhenPlayerMoves() {
        gameState.myPlayer.x = 20
        gameState.myPlayer.y = 55
        updateVerification.verifyGameState(gameState, mockKryoNetwork, mockVolleyNetwork)

        verify(mockKryoNetwork, times(1)).updatePosition(gameState.myPlayer.playerID,
            gameState.myPlayer.floorNumber, gameState.myPlayer.x, gameState.myPlayer.y)
    }

    @Test
    fun updateVerificationInvokesNewDungeonLevelOnFloorTransition() {
        gameState.myPlayer.floorNumber = 1
        updateVerification.verifyGameState(gameState, mockKryoNetwork, mockVolleyNetwork)

        verify(mockVolleyNetwork, times(1)).retrieveNewDungeonLevel(gameState.myPlayer.floorNumber,
            mockKryoNetwork)

    }

    @Test
    fun updateVerificationShouldDoNothingWhenNothingHappens() {
        val verification = updateVerification.verifyGameState(gameState, mockKryoNetwork, mockVolleyNetwork)
        Assert.assertEquals(verification, UpdateVerificationType.NONE)
    }
}