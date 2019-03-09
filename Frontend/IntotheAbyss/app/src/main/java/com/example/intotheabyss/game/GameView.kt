package com.example.intotheabyss.game

import android.content.Context
import android.util.AttributeSet
import android.view.SurfaceHolder
import android.view.SurfaceView
import com.example.intotheabyss.dungeonassets.Floor
import com.example.intotheabyss.dungeonassets.Wall
import com.example.intotheabyss.player.Player
import android.content.res.Resources
import android.graphics.*
import android.view.MotionEvent
import com.example.intotheabyss.utils.TileTypes
import com.example.intotheabyss.dungeonassets.Tile

class GameView(context: Context, attributes: AttributeSet) : SurfaceView(context, attributes), SurfaceHolder.Callback {

    private val thread: GameThread
    private var gameState: GameState? = null

    //Screen dimensions
    private val sWidth = Resources.getSystem().displayMetrics.widthPixels
    private val sHeight = Resources.getSystem().displayMetrics.heightPixels


    private val tileSize = 64
    //How many tiles will fit on screen
    private val dimWidth: Int = sWidth / tileSize
    private val dimHeight: Int = sHeight / tileSize

    //declare game objects
//    private var player: Player = gameState.myPlayer
    private var player: Player? = null
    private var lvlArray = Array(100) { Array(50) { tile } }

    //Assets
    private var floorImage: Bitmap = BitmapFactory.decodeResource(context.resources, com.example.intotheabyss.R.drawable.floor)
    private var wallImage: Bitmap = BitmapFactory.decodeResource(context.resources, com.example.intotheabyss.R.drawable.wall)

    //Variables for reading player input
    var input: MotionEvent? = null
    var downTime: Long = 0
    var eventTime: Long = 0
    var action: Int = 0
    var iX: Float = 0.toFloat()     //x coord of finger press
    var iY: Float = 0.toFloat()     //y coord of finger press
    var metaState: Int = 0

    //Tracking time for player speed
    private var lastTime: Long = 0
    private var currentTime: Long = 10000   //Just some number significantly longer than last time to start

    //Variables for following player
    private val xBuffer: Int = 5
    private val yBuffer: Int = 5
    private var minX: Int = 0
    private var minY: Int = 0


    init {

        // Don't really know what this does
        holder.addCallback(this)

        // This instantiates the game thread when we start the game
        thread = GameThread(holder, this)

        //TODO: Testing only, remove later
        val wall = Wall()
        val floor = Floor()

        for (i in 0..(lvlArray.size-1)) {
            for (j in 0..(lvlArray[0].size-1)) {
                if ((i == 0) or (i == 99) or (j == 0) or (j == 49)) {
                    lvlArray[i][j] = wall
                } else {
                    lvlArray[i][j] = floor
                }
            }
        }

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
        player!!.setX(player!!.getX())
        player!!.setY(player!!.getY())
        var newX: Int
        var newY: Int
        var moved = false
        val waitTime: Long = 100

        if (action == MotionEvent.ACTION_UP) {
            iX = 0f
            iY = 0f
        }

        if (iX > sWidth * 3/4) {
            newX = player!!.getX() + 1
            if (lvlArray[player!!.getY()][newX]!!.isPassable) {
                currentTime = System.currentTimeMillis()
                if ((currentTime - lastTime > waitTime) or (moved)) {
                    player!!.setX(newX)
                    moved = true
                    lastTime = System.currentTimeMillis()
                }
            }
        } else if ((iX < sWidth / 4) and (iX != 0f)) {
            newX = player!!.getX() - 1
            if (lvlArray[player!!.getY()][newX]!!.isPassable) {
                currentTime = System.currentTimeMillis()
                if ((currentTime - lastTime > waitTime) or (moved)) {
                    player!!.setX(newX)
                    moved = true
                    lastTime = System.currentTimeMillis()
                }
            }
        }

        if (iY > sHeight * 3/4) {
            newY = player!!.getY() + 1
            if (lvlArray[newY][player!!.getX()]!!.isPassable) {
                currentTime = System.currentTimeMillis()
                if ((currentTime - lastTime > waitTime) or (moved)) {
                    player!!.setY(newY)
                    moved = true
                    lastTime = System.currentTimeMillis()
                }
            }
        } else if ((iY < sWidth / 4) and (iY != 0f)) {
            newY = player!!.getY() - 1
            if (newY < lvlArray.size) {
                if (lvlArray[newY][player!!.getX()]!!.isPassable) {
                    currentTime = System.currentTimeMillis()
                    if ((currentTime - lastTime > waitTime) or (moved)) {
                        player!!.setY(newY)
                        moved = true
                        lastTime = System.currentTimeMillis()
                    }
                }
            }
        }

//        iX = 0f
//        iY = 0f

        updateBoundaries(player!!)
    }

    /**
     * This is where we will draw things onto the game "canvas"
     */
    override fun draw(canvas: Canvas) {
        super.draw(canvas)
        drawBG(canvas, player!!)
        drawPlayer(canvas, player!!)
//        println("X: $iX, Y: $iY")
    }

    override fun dispatchTouchEvent(event: MotionEvent?): Boolean {
        iX = event!!.x
        iY = event!!.y
        action = event.action
        return true

//        Removing the super call seems dangerous, but it fixed my problems so idk
//        return super.dispatchTouchEvent(event)
    }

    private fun updateBoundaries(player: Player) {
        val x = player.getX()
        val y = player.getY()

        if (x - xBuffer < minX) {
            minX--
        } else if ((x + xBuffer) > (minX + dimWidth)) {
            minX++
        }

        if (y - yBuffer < minY) {
            minY--
        } else if ((y + yBuffer) > (minY + dimHeight)) {
            minY++
        }
    }

    private fun drawPlayer(canvas: Canvas, player: Player)  {
        val x = player.getX()
        val y = player.getY()


        val paint = Paint()
        paint.color = Color.WHITE
        paint.style = Paint.Style.FILL
        paint.textSize = 30.toFloat()

        player!!.draw(canvas, (x-minX)*tileSize, (y-minY)*tileSize)
        canvas.drawText("Player location: (" + player.getX().toString() + "," + player.getY().toString() + ")",25.toFloat(), 50.toFloat(), paint)
    }

    fun setGameState(gState: GameState)  {
        gameState = gState
    }

    fun drawBG(canvas: Canvas, player: Player) {
        //Wall/floor objects to display - will explore more advanced ways of displaying objects (sprites and whatnot)


        //TODO: Replace lvlArray with level, when possible
        //getting lvlArray from gameState for now, but this should be changed to be level object
        //val level: Level = gameState.level






        //image variable - will maybe be updated to be more efficient later
        var image: Bitmap = BitmapFactory.decodeResource(resources, com.example.intotheabyss.R.drawable.panda)


        //Loop through all tiles to be displayed, and a few others to minimize lag
        for (i in minX..minX+dimWidth) {
            if ((i > -1) and (i < 50))
            for (j in minY..minY+dimHeight) {
                if ((j > -1) and (j < 100)) {
                    //Try to get the filetype, and then print image - should only fail if undefined tile (aka not on map)
                    if (lvlArray[j][i]!!.type == TileTypes.FLOOR) {  //Set image to floorImage
                        image = floorImage
                    } else if (lvlArray[j][i]!!.type == TileTypes.WALL) {
                        image = wallImage                                   //Set image to wallImage
                    }
                    //Try to print the damn thing
                    canvas.drawBitmap(image, (((i-minX) * tileSize)).toFloat(), (((j-minY) * tileSize)+1).toFloat(), null)
                }
            }
        }
    }

    companion object {
        private var tile: Tile? = null
    }

}
