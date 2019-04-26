package com.example.intotheabyss.game.drawplayer

import android.content.Context
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Point
import com.example.intotheabyss.game.entity.player.Player

interface DrawPlayerInterface {
    fun drawPlayer(dX: Int, dY: Int, context: Context, canvas: Canvas, player: com.example.intotheabyss.game.entity.Entity, gAction: Int, isPlayer: Boolean)
    fun setPlayerImage(dX: Int, dY: Int, context: Context, gAction: Int, player: com.example.intotheabyss.game.entity.Entity): Bitmap
    fun updateBoundaries(player: Player): Point
}