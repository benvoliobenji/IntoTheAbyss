package com.example.intotheabyss.game

import android.content.Context
import android.util.AttributeSet
import android.view.Display
import android.view.SurfaceHolder
import android.view.SurfaceView
import com.example.intotheabyss.R.drawable
import com.example.intotheabyss.dungeonassets.Floor
import com.example.intotheabyss.dungeonassets.Wall
import com.example.intotheabyss.player.Player
import com.example.intotheabyss.dungeonassets.Level
import android.R.attr.y
import android.R.attr.x
import android.R
import android.content.res.Resources
import android.graphics.*
import android.util.DisplayMetrics
import android.view.WindowManager
import com.example.intotheabyss.utils.TileTypes


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

    fun drawBG(canvas: Canvas, player: Player) {
        //Wall/floor objects to display - will explore more advanced ways of displaying objects (sprites and whatnot)
        val wall: Wall = Wall()
        val floor: Floor = Floor()
        val tileSize = 32

        //getting lvlArray from gameState for now, but this should be changed to be level object
        //val level: Level = gameState.level
        val lvlArray = gameState!!.level

        //Screen dimensions
        val sWidth = Resources.getSystem().displayMetrics.widthPixels
        val sHeight = Resources.getSystem().displayMetrics.heightPixels

        //How many tiles will fit on screen
        val dimWidth: Int = sWidth / tileSize
        val dimHeight: Int = sHeight / tileSize

        //image variable - will maybe be updated to be more efficient later
        var image: Bitmap = BitmapFactory.decodeResource(R.drawable.panda)

        //Loop through all tiles to be displayed, and a few others to minimize lag
        for (i in -2..dimWidth+2) {
            for (j in -2..dimHeight+2) {
                //Try to get the filetype, and then print image - should only fail if undefined tile (aka not on map)
                try{
                    if (lvlArray.get(i).get(j).typel == TileTypes.FLOOR) {  //Set image to floorImage
                        image = floorImage
                    } else if (lvlArray.get(i).get(j).typel == TileTypes.WALL) {
                        image = wallImage                                   //Set image to wallImage
                    }
                    try {   //Try to print the damn thing
                        canvas.drawBitmap(image, (j*tileSize).toFloat(), (i*tileSize).tofloat(), null)
                    } catch (e: java.lang.Exception) {
                        print(e.printStackTrace())
                    }
                } catch(e: java.lang.Exception) {
                    e.printStackTrace()
                }

            }
        }
    }

}
