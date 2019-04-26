package com.example.intotheabyss.game.gamecontroller

import android.graphics.Canvas
import android.view.MotionEvent
import com.example.intotheabyss.game.entity.Entity
import com.example.intotheabyss.game.entity.player.Player

interface PlayerBoardInterface {
    fun drawPlayerBoard(canvas: Canvas, entityList: HashMap<String, Entity>)
    fun getPlayerBoardAction(entityList: HashMap<String, Entity>, x: Float, y: Float, action: MotionEvent)
    fun getPlayerGroupAction(x: Float, y: Float, action: MotionEvent)
}