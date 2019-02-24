package com.example.intotheabyss.game

import android.content.Context
import android.graphics.BitmapFactory
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import android.view.SurfaceHolder
import android.view.SurfaceView
import com.example.intotheabyss.R
import com.example.intotheabyss.player.Player

class GameView(context: Context, attributes: AttributeSet) : SurfaceView(context, attributes), SurfaceHolder.Callback {

    private val thread: GameThread
    private var gameState: GameState? = null

    //declare game objects
//    private var player: Player = Player() //Should be coming from gameState.myPlayer??
//    private var player: Player = gameState.myPlayer
    private var player: Player? = null


    init {

        // Don't really know what this does
        holder.addCallback(this)

        // This instantiates the game thread when we start the game
        thread = GameThread(holder, this)

        try {
            player = gameState!!.myPlayer
        } catch (e: java.lang.Exception) {
            e.printStackTrace()
        }
    }

    override fun surfaceDestroyed(p0: SurfaceHolder?) {
        //When surface is "destroyed", stop the thread
        var retry = true
        while (retry) {
            try {
                thread.setRunning(false)
                thread.join()
            } catch (e: Exception) {
                e.printStackTrace()
            }

            retry = false
        }
    }

    override fun surfaceCreated(p0: SurfaceHolder?) {
        if (player == null) {
            try {
                player = gameState!!.myPlayer
            } catch (e: java.lang.Exception)    {
                e.printStackTrace()
            }
        }
        //Set image assets for game objects
        player!!.setImage(BitmapFactory.decodeResource(resources, R.drawable.panda))
        //Start the game thread
        thread.setRunning(true)
        thread.start()
    }

    override fun surfaceChanged(p0: SurfaceHolder?, p1: Int, p2: Int, p3: Int) {
        //TODO: ??? Don't really know what to do here, yet!
    }

    /**
     * This is where we will update game variables
     */
    fun update() {
        //Comment out if you don't want arbitrary player movement
        //player!!.setX(player!!.getX()+1)
        //player!!.setY(player!!.getY()+1)
        player!!.setX(player!!.getX())
        player!!.setY(player!!.getY())
    }

    /**
     * This is where we will draw things onto the game "canvas"
     */
    override fun draw(canvas: Canvas) {
        super.draw(canvas)
        drawPlayer(canvas, player!!)
    }

    fun drawPlayer(canvas: Canvas, player: Player)  {
        var x = player.getX()
        var y = player.getY()

        val paint = Paint()
        paint.color = Color.WHITE
        paint.style = Paint.Style.FILL
        paint.textSize = 30.toFloat()

        player!!.draw(canvas, x, y)
        canvas.drawText("Player location: (" + player.getX().toString() + "," + player.getY().toString() + ")",25.toFloat(), 50.toFloat(), paint)
    }

    fun setGameState(gState: GameState)  {
        gameState = gState
    }

}
