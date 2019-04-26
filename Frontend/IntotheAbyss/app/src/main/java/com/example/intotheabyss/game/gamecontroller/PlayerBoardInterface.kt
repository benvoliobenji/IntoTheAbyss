package com.example.intotheabyss.game.gamecontroller

import android.graphics.Canvas
import android.view.MotionEvent
import com.example.intotheabyss.game.player.Player

interface PlayerBoardInterface {
    fun drawPlayerBoard(canvas: Canvas, playerList: HashMap<String, Player>)
    fun getPlayerBoardAction(playerList: HashMap<String, Player>, x: Float, y: Float, action: MotionEvent)
    fun getPlayerGroupAction(x: Float, y: Float, action: MotionEvent)
}