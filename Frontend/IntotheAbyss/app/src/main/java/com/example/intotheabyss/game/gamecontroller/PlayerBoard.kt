package com.example.intotheabyss.game.gamecontroller

import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Rect
import com.example.intotheabyss.game.GameView
import com.example.intotheabyss.game.player.Player

private var playerTextPaint = Paint()
private var playerBoardPaint = Paint()

class PlayerBoard(private val canvas: Canvas, private val gameView: GameView,
                  private val bSize: Float) : PlayerBoardInterface    {

    init {
        playerTextPaint.color = Color.BLACK
        playerTextPaint.textSize = 70f

        playerBoardPaint.color = Color.WHITE
        playerBoardPaint.alpha = 90
    }

    override fun drawPlayerBoard(playerList: HashMap<String, Player>) {
        var i = 0
        for (p in playerList) {
            val player = p.value
            val rect = Rect(25, ((2+i)*bSize).toInt(), 25 + 3*bSize.toInt(), (3+i)*bSize.toInt())
            canvas.drawRect(rect, playerBoardPaint)
            canvas.drawText(player.playerName, 25f, rect.exactCenterY(), playerTextPaint)
            i++
        }
    }

    override fun getPlayerBoardAction(playerList: HashMap<String, Player>) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}