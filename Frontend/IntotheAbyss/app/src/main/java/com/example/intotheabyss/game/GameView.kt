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
import android.support.annotation.FloatRange
import android.view.MotionEvent
import com.example.intotheabyss.utils.TileTypes
import com.example.intotheabyss.dungeonassets.Tile

class GameView(context: Context, attributes: AttributeSet) : SurfaceView(context, attributes), SurfaceHolder.Callback {

    private var debug = true //set to true to get a generic level, false to get a level from DB

    private val thread: GameThread
    private var gameState: GameState? = null

    private var gameController: GameController
    var gAction = 0         //GameController sets to 0 if no action, something else if there is

    //Screen dimensions
    val sWidth = Resources.getSystem().displayMetrics.widthPixels
    val sHeight = Resources.getSystem().displayMetrics.heightPixels


    private val tileSize = 64
    //How many tiles will fit on screen
    private val dimWidth: Int = sWidth / tileSize
    private val dimHeight: Int = sHeight / tileSize

    //declare game objects
    var player: Player? = null
    var dX: Int = 0 //If player facing left, dX=-1; if facing right, dX=1; If neither, dX=0 (not currently supported)
    var dY: Int = 0 //If player facing up, dY=1; if facing down, dY=-1; If neither, dY=0 (this is now the only supported mode)
    var lastX: Int = 0
    var lastY: Int = 0
    private var animState: Int = 0 //Currently there are 3 supported walking animations. This keeps track of which was last displayed

    private val lvlSize: Point = Point(100, 25)
    var lvlArray = Array(lvlSize.y) { Array(lvlSize.x) { tile } }
    private var validLevel = false

    //Assets
    private var floorImage: Bitmap = BitmapFactory.decodeResource(context.resources, com.example.intotheabyss.R.drawable.floor)
    private var wallImage: Bitmap = BitmapFactory.decodeResource(context.resources, com.example.intotheabyss.R.drawable.wall)
    private val stairsImage: Bitmap = BitmapFactory.decodeResource(context.resources, com.example.intotheabyss.R.drawable.stairs)
    var playerImage: Bitmap = BitmapFactory.decodeResource(context.resources, com.example.intotheabyss.R.drawable.stairs)

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
        gameController = GameController(this)

        try {
            player = gameState!!.myPlayer
        } catch (e: java.lang.Exception) {
            e.printStackTrace()
        }
    }

    private fun genericLevel() {
        val wall = Wall()
        val floor = Floor()

        for (i in 0 until lvlArray.size) {
            for (j in 0 until lvlArray[i].size) {
                if ((i == 0) or (i == lvlArray.size-1) or (j == 0) or (j == lvlArray[0].size-1)) {
                    lvlArray[i][j] = wall
                } else {
                    lvlArray[i][j] = floor
                }
            }
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

        //updatePlayerLocation()    //Player movment - iX,iY is coordinate of touch event
        gameController!!.updatePlayerLocation()
        gameController!!.getAction()
        checkNewLevel()
        gameState!!.myPlayer = player!!   //Not sure if this is necessary - but it couldn't hurt
        println("Gamestate level = ${gameState!!.myPlayer.floorNumber}")
        updateBoundaries(player!!)      //Make sure screen follows player around
    }

    private fun checkNewLevel() {
        if (gAction > 0) {
            if (lvlArray[player!!.y][player!!.x].type == TileTypes.STAIR) {
                player!!.floorNumber++
                gameState!!.loading = true //Not sure if this is the purpose of it or not
                println("Attempting to descend level. Currently at ${player!!.floorNumber}")
                gAction = 0
            }
        }
    }

    /**
     * This is where we will draw things onto the game "canvas"
     */
    override fun draw(canvas: Canvas) {
        super.draw(canvas)
        drawBG(canvas)
        drawPlayer(canvas, player!!)
        drawAction(canvas)
    }

    /**
     * How we get info from screen touch events.
     */
    override fun dispatchTouchEvent(event: MotionEvent?): Boolean {
        event!!

//        iY = event.y
//        action = event.action
        gameController.setEvent(event)
        return true

//        Removing the super call seems dangerous, but it fixed my problems so idk
//        return super.dispatchTouchEvent(event)
    }

    private fun updateBoundaries(player: Player) {
        val x = player.x
        val y = player.y

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
//        val x = player.getX()
//        val y = player.getY()
        val x = player.x
        val y = player.y

        if (lastX != x) {
            setPlayerImage(animState, dX)
        }

        val paint = Paint()
        paint.color = Color.WHITE
        paint.style = Paint.Style.FILL
        paint.textSize = 30.toFloat()

//        player.draw(canvas, (x-minX)*tileSize, (y-minY)*tileSize)
        canvas.drawBitmap(playerImage,(x-minX)*tileSize.toFloat(), (y-minY)*tileSize.toFloat(),null)
        canvas.drawText("Player location: (${player.x},${player.y})",25f, 50f, paint)

        lastX = x
        lastY = y
    }

    fun setGameState(gState: GameState)  {
        gameState = gState
    }

    private fun drawBG(canvas: Canvas) {
        if (debug) {
            genericLevel()
//            debug = false
        } else {
            lvlArray = gameState!!.level
        }
        //val level: Level = gameState.level

        //If offline, try adding in the ValidLevel thing to fix it
        if (/*!validLevel or */(lvlArray.isNullOrEmpty())) {
            if (gameState!!.level.isNotEmpty()) {
                lvlArray = gameState!!.level
            }
            else {
                genericLevel()
            }
            validLevel = true
        }



        //image variable - will maybe be updated to be more efficient later
        var image: Bitmap = BitmapFactory.decodeResource(resources, com.example.intotheabyss.R.drawable.panda)


        //Loop through all tiles to be displayed, and a few others to minimize lag
        for (i in minX..minX+dimWidth) {
            if ((i > -1) and (i < lvlSize.x))
            for (j in minY..minY+dimHeight) {
                if ((j > -1) and (j < lvlSize.y)) {
                    //Try to get the filetype, and then print image - should only fail if undefined tile (aka not on map)
                    if (lvlArray[j][i].type == TileTypes.FLOOR) {  //Set image to floorImage
                        image = floorImage
                    } else if (lvlArray[j][i].type == TileTypes.WALL) {
                        image = wallImage                                   //Set image to wallImage
                    } else if (lvlArray[j][i].type == TileTypes.STAIR) {
                        image = stairsImage
                    }
                    //Try to print the damn thing
                    canvas.drawBitmap(image, (((i-minX) * tileSize)).toFloat(), (((j-minY) * tileSize)+1).toFloat(), null)
                }
            }
        }
    }

    /**
     * Function to animate the walking of the player. Depending on the direction of walking & the last displayed image,
     * a different image will be set to be displayed
     */
    private fun setPlayerImage(state: Int, dir: Int) {
//        when (dir == -1) {        //Player walking left
//
//        } else if (dir == 1) {  //Player walking right
//
//        } else {                //Player walking up/down
//
//        }
        when(dir) {
            -1 -> when(state) {
                0 -> {
                    playerImage = BitmapFactory.decodeResource(context.resources, com.example.intotheabyss.R.drawable.walk_left0)
                    animState = 1
                }
                1 -> {
                    playerImage = BitmapFactory.decodeResource(context.resources, com.example.intotheabyss.R.drawable.walk_left1)
                    animState = 2
                }
                2 -> {
                    playerImage = BitmapFactory.decodeResource(context.resources, com.example.intotheabyss.R.drawable.walk_left2)
                    animState = 0
                }
            }
            0 -> when(state) {
                0 -> {
                    playerImage = BitmapFactory.decodeResource(context.resources, com.example.intotheabyss.R.drawable.walk_left0)
                    //animState = 1
                }
                1 -> {
                    playerImage = BitmapFactory.decodeResource(context.resources, com.example.intotheabyss.R.drawable.walk_left1)
                    //animState = 2
                }
                2 -> {
                    playerImage = BitmapFactory.decodeResource(context.resources, com.example.intotheabyss.R.drawable.walk_left2)
                    //animState = 0
                }
            }
            1 -> when(state) {
                0 -> {
                    playerImage = BitmapFactory.decodeResource(context.resources, com.example.intotheabyss.R.drawable.walk_right0)
                    animState = 1
                }
                1 -> {
                    playerImage = BitmapFactory.decodeResource(context.resources, com.example.intotheabyss.R.drawable.walk_right1)
                    animState = 2
                }
                2 -> {
                    playerImage = BitmapFactory.decodeResource(context.resources, com.example.intotheabyss.R.drawable.walk_right2)
                    animState = 0
                }
            }
        }
    }

    private fun drawAction(canvas: Canvas) {
        if (gAction > 0) {
            val paint = Paint()
            paint.color = Color.RED
            paint.style = Paint.Style.FILL
            paint.textSize = 80f
            canvas.drawText("Action", 500f, 500f, paint)
        }
        gAction = 0
    }

    companion object {
        private var tile: Tile = Wall()
    }
}
