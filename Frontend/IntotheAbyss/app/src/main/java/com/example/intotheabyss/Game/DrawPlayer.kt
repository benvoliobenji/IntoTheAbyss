package com.example.intotheabyss.Game

import android.content.Context
import android.graphics.*
import com.example.intotheabyss.game.GameView
import com.example.intotheabyss.player.Player

var gameView: GameView? = null
private var playerImage: Bitmap? = null
private var animState = 0
const val tileSize = 64
private var rect: Rect = Rect()
private var playerIdle = true
private var animCount = 7

class DrawPlayer(gView: GameView, pImage: Bitmap) {

    init {
        gameView = gView
        playerImage = pImage
    }

    fun drawPlayer(canvas: Canvas, player: Player, gAction: Int)  {
        val x = player.x
        val y = player.y

        drawAction(canvas, gAction)
        setAnimState()

        animState++
        if (animState == 6) {
            animState = 0
        }

        val paint = Paint()
        paint.color = Color.WHITE
        paint.style = Paint.Style.FILL
        paint.textSize = 30.toFloat()

        val left = (x- gameView!!.minX)*tileSize
        val top = (y- gameView!!.minY)*tileSize
        val pos = Rect(left,top,left+3*tileSize/2,top+3*tileSize/2)

        canvas.drawBitmap(playerImage, rect, pos, null)
        canvas.drawText("Player location: (${player.x},${player.y})",25f, 50f, paint)

        drawHealth(player, canvas)
        drawUserName(player, canvas)
    }

    fun setPlayerImage(dX: Int, dY: Int, context: Context, gAction: Int) {
        if (gAction > 0) {
            animState = 0
        }

        when(dX) {
            -1 -> {
                if (playerIdle) {
                    playerImage = BitmapFactory.decodeResource(context.resources, com.example.intotheabyss.R.drawable.char_idle_left)
                } else{
                    playerImage = BitmapFactory.decodeResource(context.resources, com.example.intotheabyss.R.drawable.char_walk_left)
                }
                if (animCount < 6) {
                    animCount++
                    playerImage = BitmapFactory.decodeResource(context.resources, com.example.intotheabyss.R.drawable.char_atk_left)
                }
            }
            1 -> {
                if (playerIdle) {
                    playerImage = BitmapFactory.decodeResource(context.resources, com.example.intotheabyss.R.drawable.char_idle_right)
                } else {
                    playerImage = BitmapFactory.decodeResource(context.resources, com.example.intotheabyss.R.drawable.char_walk_right)
                }
                if (animCount < 6) {
                    animCount++
                    playerImage = BitmapFactory.decodeResource(context.resources, com.example.intotheabyss.R.drawable.char_atk_right)
                }
            }
        }
        when(dY) {
            -1 -> {
                if (playerIdle) {
                    playerImage = BitmapFactory.decodeResource(context.resources, com.example.intotheabyss.R.drawable.char_idle_down)
                } else {
                    playerImage = BitmapFactory.decodeResource(context.resources, com.example.intotheabyss.R.drawable.char_walk_down)
                }
                if (animCount < 6) {
                    animCount++
                    playerImage = BitmapFactory.decodeResource(context.resources, com.example.intotheabyss.R.drawable.char_atk_down)
                }
            }
            1 -> {
                if (playerIdle) {
                    playerImage = BitmapFactory.decodeResource(context.resources, com.example.intotheabyss.R.drawable.char_idle_up)
                }  else {
                    playerImage = BitmapFactory.decodeResource(context.resources, com.example.intotheabyss.R.drawable.char_walk_up)
                }
                if (animCount < 6) {
                    animCount++
                    playerImage = BitmapFactory.decodeResource(context.resources, com.example.intotheabyss.R.drawable.char_atk_up)
                }
            }
        }
    }

    /**
     * Creates a Rect() object that will be the subset of the sprite sheet to display
     */
    private fun setAnimState() {
        val pWidth = playerImage!!.width
        rect = Rect((pWidth/6)*(animState),0,(pWidth/6)*(animState+1),192)
    }

    private fun drawAction(canvas: Canvas, gAction: Int) {
        if (gAction > 0) {
            val paint = Paint()
            paint.color = Color.RED
            paint.style = Paint.Style.FILL
            paint.textSize = 80f
            canvas.drawText("Action", 500f, 500f, paint)

            animCount = 0
        }
    }

    private fun drawHealth(player: Player, canvas: Canvas) {
        val paint = Paint()
        paint.color = Color.RED
        paint.style = Paint.Style.FILL_AND_STROKE
        paint.textSize = 80f
        canvas.drawText("Health: ${player.health}/10", 1500f, 100f, paint)
    }

    private fun drawUserName(player: Player, canvas: Canvas) {
        val paint = Paint()
        paint.color = Color.WHITE
        paint.style = Paint.Style.FILL
        paint.textSize = 30f

        player.playerName = "joevt"
        canvas.drawText(player.playerName, (player.x- gameView!!.minX)* tileSize.toFloat(), (player.y- gameView!!.minY)* tileSize.toFloat()-5f, paint)
    }
}