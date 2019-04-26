package com.example.intotheabyss.game.gamecontroller

import com.example.intotheabyss.game.player.Player

interface PlayerBoardInterface {
    fun drawPlayerBoard(playerList: HashMap<String, Player>)
    fun getPlayerBoardAction(playerList: HashMap<String, Player>)
}