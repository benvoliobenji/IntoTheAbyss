package com.example.intotheabyss

import android.content.Context
import com.example.intotheabyss.game.GameState
import com.example.intotheabyss.networking.Network
import com.example.intotheabyss.networking.volleynetwork.VolleyNetwork
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.whenever
import org.junit.Test

@Test
fun returnCorrectlySizedDungeon() {
    val mockNetwork = mock<Network>()
    val gameState = GameState()
    val context = mock<Context>()
    val mockVolleyNetwork = mock<VolleyNetwork>()
    whenever(mockVolleyNetwork.retrieveNewDungeonLevel(1, mockNetwork)).then(mockVolleyNetwork.gameState)
}