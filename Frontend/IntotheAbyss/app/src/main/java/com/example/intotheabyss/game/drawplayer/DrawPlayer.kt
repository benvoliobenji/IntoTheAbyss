package com.example.intotheabyss.game.drawplayer

import android.content.Context
import android.graphics.*
import com.example.intotheabyss.game.GameView
import com.example.intotheabyss.game.player.Player

var gameView: GameView? = null
private var playerImage: Bitmap? = null
var animState = 0
const val tileSize = 64
private var rect: Rect = Rect()
private var playerIdle = true
var animCount = 7

//Variables for following player
private const val xBuffer: Int = 5
private const val yBuffer: Int = 5
var minX: Int = 0
var minY: Int = 0

class DrawPlayer(gView: GameView, pImage: Bitmap): DrawPlayerInterface {

    init {
        gameView = gView
        playerImage = pImage
    }

    override fun drawPlayer(dX: Int, dY: Int, context: Context, canvas: Canvas, player: Player, gAction: Int, isPlayer: Boolean)  {
        val x = player.x
        val y = player.y

        val paint = Paint()
        paint.color = Color.WHITE
        paint.style = Paint.Style.FILL
        paint.textSize = 30.toFloat()

        drawAction(gAction)
        val image = setPlayerImage(dX, dY, context, player.actionStatus, player)
        setAnimState(image)


        if (isPlayer) {
            animState++
            if (animState == 6) {
                animState = 0
            }
            canvas.drawText("Player location: (${player.x},${player.y})",25f, 50f, paint)
            drawHealth(player, canvas)
        }

        val left = (x- gameView!!.minX)* tileSize
        val top = (y- gameView!!.minY)* tileSize
        val pos = Rect(left,top,left+3* tileSize /2,top+3* tileSize /2)

        canvas.drawBitmap(
            image,
            rect, pos, null)

        drawUserName(player, canvas)
    }

    override fun setPlayerImage(dX: Int, dY: Int, context: Context, gAction: Int, player: Player): Bitmap {
        var isMonster = false
        if ((!player.playerID.isBlank()) && (player.playerID.length>2)) {
            if ((player.playerID[0] == 'M') && player.playerID[1] == 'M') {
                isMonster = true
            }
        }

        var image = BitmapFactory.decodeResource(context.resources, com.example.intotheabyss.R.drawable.char_idle_left)
        if (player.actionStatus == 1)   {
            animState = 0
        }

        when(dX) {
            -1 -> {
                if (playerIdle) {
                    image = BitmapFactory.decodeResource(context.resources, com.example.intotheabyss.R.drawable.char_idle_left)
                } else{
                    image = BitmapFactory.decodeResource(context.resources, com.example.intotheabyss.R.drawable.char_walk_left)
                }
                if (animCount < 6) {
                    animCount++
                    image = BitmapFactory.decodeResource(context.resources, com.example.intotheabyss.R.drawable.char_atk_left)
                }
                if (isMonster) {
                    image = BitmapFactory.decodeResource(context.resources, com.example.intotheabyss.R.drawable.skel_walk_left)
                }
            }
            1 -> {
                if (playerIdle) {
                    image = BitmapFactory.decodeResource(context.resources, com.example.intotheabyss.R.drawable.char_idle_right)
                } else {
                    image = BitmapFactory.decodeResource(context.resources, com.example.intotheabyss.R.drawable.char_walk_right)
                }
                if (animCount < 6) {
                    animCount++
                    image = BitmapFactory.decodeResource(context.resources, com.example.intotheabyss.R.drawable.char_atk_right)
                }
                if (isMonster) {
                    image = BitmapFactory.decodeResource(context.resources, com.example.intotheabyss.R.drawable.skel_walk_right)
                }
            }
        }
        when(dY) {
            -1 -> {
                if (playerIdle) {
                    image = BitmapFactory.decodeResource(context.resources, com.example.intotheabyss.R.drawable.char_idle_down)
                } else {
                    image = BitmapFactory.decodeResource(context.resources, com.example.intotheabyss.R.drawable.char_walk_down)
                }
                if (animCount < 6) {
                    animCount++
                    image = BitmapFactory.decodeResource(context.resources, com.example.intotheabyss.R.drawable.char_atk_down)
                }
                if (isMonster) {
                    image = BitmapFactory.decodeResource(context.resources, com.example.intotheabyss.R.drawable.skel_walk_down)
                }
            }
            1 -> {
                if (playerIdle) {
                    image = BitmapFactory.decodeResource(context.resources, com.example.intotheabyss.R.drawable.char_idle_up)
                }  else {
                    image = BitmapFactory.decodeResource(context.resources, com.example.intotheabyss.R.drawable.char_walk_up)
                }
                if (animCount < 6) {
                    animCount++
                    image = BitmapFactory.decodeResource(context.resources, com.example.intotheabyss.R.drawable.char_atk_up)
                }
                if (isMonster) {
                    image = BitmapFactory.decodeResource(context.resources, com.example.intotheabyss.R.drawable.skel_walk_up)
                }
            }
        }
        return image
    }

    /**
     * Creates a Rect() object that will be the subset of the sprite sheet to display
     */
    private fun setAnimState(image: Bitmap) {
        val pWidth = image.width
        rect = Rect((pWidth/6)*(animState),0,(pWidth/6)*(animState +1),192)
    }

    private fun drawAction(gAction: Int) {
        if (gAction > 0) {
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

        canvas.drawText(player.playerName, (player.x- gameView!!.minX)* tileSize.toFloat(), (player.y- gameView!!.minY)* tileSize.toFloat()-5f, paint)
    }

    override fun updateBoundaries(player: Player): Point {
        val x = player.x
        val y = player.y

        if (x - xBuffer < minX) {
            minX--
        } else if ((x + xBuffer) > (minX + gameView!!.dimWidth)) {
            minX++
        }

        if (y - yBuffer < minY) {
            minY--
        } else if ((y + yBuffer) > (minY + gameView!!.dimHeight)) {
            minY++
        }

        return Point(minX, minY)
    }

}