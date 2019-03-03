package com.example.intotheabyss.game

import android.content.Context
import android.util.AttributeSet
import android.view.Display
import android.view.SurfaceHolder
import android.view.SurfaceView
import com.example.intotheabyss.R.*
import com.example.intotheabyss.dungeonassets.Floor
import com.example.intotheabyss.dungeonassets.Wall
import com.example.intotheabyss.player.Player
import com.example.intotheabyss.dungeonassets.Level
import android.R.attr.y
import android.R.attr.x
import android.content.res.Resources
import android.graphics.*
import android.graphics.drawable.BitmapDrawable
import android.util.DisplayMetrics
import android.view.WindowManager
import com.example.intotheabyss.utils.TileTypes
import com.example.intotheabyss.R
import com.example.intotheabyss.dungeonassets.Tile

class GameView(context: Context, attributes: AttributeSet) : SurfaceView(context, attributes), SurfaceHolder.Callback {

    private val thread: GameThread
    private var gameState: GameState? = null

    //declare game objects
//    private var player: Player = Player() //Should be coming from gameState.myPlayer??
//    private var player: Player = gameState.myPlayer
    private var player: Player? = null
    private var floorImage: Bitmap = BitmapFactory.decodeResource(context.resources, com.example.intotheabyss.R.drawable.floor)
    private var wallImage: Bitmap = BitmapFactory.decodeResource(context.resources, com.example.intotheabyss.R.drawable.wall)


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
        player!!.setImage(BitmapFactory.decodeResource(resources, com.example.intotheabyss.R.drawable.panda))
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
        drawBG(canvas, player!!)
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
        val tileSize = 64

        //TODO: Replace lvlArray with level, when possible
        //getting lvlArray from gameState for now, but this should be changed to be level object
        //val level: Level = gameState.level
        val lvlArray = Array(100) { Array(50) { tile } }

        //Screen dimensions
        val sWidth = Resources.getSystem().displayMetrics.widthPixels
        val sHeight = Resources.getSystem().displayMetrics.heightPixels

        //How many tiles will fit on screen
        val dimWidth: Int = sWidth / tileSize
        val dimHeight: Int = sHeight / tileSize

        //image variable - will maybe be updated to be more efficient later
        var image: Bitmap = BitmapFactory.decodeResource(resources, com.example.intotheabyss.R.drawable.panda)

        //TODO: Remove later (when replacaed by gamestate)
        //Debugging
        for (i in 0..(lvlArray.size-1)) {
            for (j in 0..(lvlArray[0].size-1)) {
                if ((i == 0) or (i == 100) or (j == 0) or (j == 50)) {
                    lvlArray[i][j] = wall
                } else {
                    lvlArray[i][j] = floor
                }
            }
        }

        //Loop through all tiles to be displayed, and a few others to minimize lag
        for (i in 0..dimWidth) {
            if ((i > -1) and (i < 100))
            for (j in 0..dimHeight) {
                if ((j > -1) and (j < 50)) {
                    //Try to get the filetype, and then print image - should only fail if undefined tile (aka not on map)
                    if (lvlArray[i][j]!!.type1 == TileTypes.FLOOR) {  //Set image to floorImage
                        image = floorImage
                    } else if (lvlArray[i][j]!!.type1 == TileTypes.WALL) {
                        image = wallImage                                   //Set image to wallImage
                    }
                    //Try to print the damn thing
                    canvas.drawBitmap(image, ((i * tileSize)+1).toFloat(), ((j * tileSize)+1).toFloat(), null)
                }
            }
        }
    }

    companion object {
        private var tile: Tile? = null
    }

}
