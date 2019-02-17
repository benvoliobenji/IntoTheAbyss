<<<<<<< HEAD:Frontend/IntotheAbyss/app/src/main/java/com/example/intotheabyss/activities/game/GameView2.kt
package com.example.intotheabyss.activities.activities.game
=======
package com.example.intotheabyss
>>>>>>> 174633a56e0e8f9946f053207d89514bb75ad192:Frontend/IntotheAbyss/app/src/main/java/com/example/intotheabyss/Game/GameView.kt

import android.content.Context
import android.graphics.BitmapFactory
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import android.view.SurfaceHolder
import android.view.SurfaceView
<<<<<<< HEAD:Frontend/IntotheAbyss/app/src/main/java/com/example/intotheabyss/activities/game/GameView2.kt
import com.example.intotheabyss.activities.game.GameThread2
=======
import com.example.intotheabyss.player.Player
>>>>>>> 174633a56e0e8f9946f053207d89514bb75ad192:Frontend/IntotheAbyss/app/src/main/java/com/example/intotheabyss/Game/GameView.kt


class GameView2(context: Context, attributes: AttributeSet) : SurfaceView(context, attributes), SurfaceHolder.Callback {

    private val thread: GameThread2

    //declare game objects
    private var player: Player = Player()

    init {

        // Don't really know what this does
        holder.addCallback(this)

        // This instantiates the game thread when we start the game
        thread = GameThread2(holder, this)
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
        player!!.setX(player!!.getX()+1)
        player!!.setY(player!!.getY()+1)
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
        paint.textSize = 24.toFloat()

        player!!.draw(canvas, x, y)
        canvas.drawText("Player location: (" + player.getX().toString() + "," + player.getY().toString() + ")",25.toFloat(), 50.toFloat(), paint)
    }

}
