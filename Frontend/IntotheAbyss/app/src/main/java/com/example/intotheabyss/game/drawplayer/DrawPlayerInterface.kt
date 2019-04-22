package com.example.intotheabyss.game.drawplayer

import android.content.Context
import android.graphics.Canvas
import android.graphics.Point
import com.example.intotheabyss.game.entity.player.Player

interface DrawPlayerInterface {
    fun drawPlayer(canvas: Canvas, player: Player, gAction: Int)
    fun setPlayerImage(dX: Int, dY: Int, context: Context, gAction: Int)
    fun updateBoundaries(player: Player): Point
}