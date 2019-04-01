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
    private var rect: Rect = Rect()
    var playerIdle = true

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

        setPlayerImage()
        setAnimState()
        val pWidth = playerImage.width
        val pHeight = playerImage.height

        animState++
        if (animState == 6) {
            animState = 0
        }
//        if (lastX != x) {
//            setPlayerImage()
//        }

        val paint = Paint()
        paint.color = Color.WHITE
        paint.style = Paint.Style.FILL
        paint.textSize = 30.toFloat()

//        player.draw(canvas, (x-minX)*tileSize, (y-minY)*tileSize)
        val left = (x-minX)*tileSize
        val top = (y-minY)*tileSize
        val pos = Rect(left,top,left+3*tileSize/2,top+3*tileSize/2)

        canvas.drawBitmap(playerImage, rect, pos, null)
//        canvas.drawBitmap(playerImage,(x-minX)*tileSize.toFloat(), (y-minY)*tileSize.toFloat(),null)
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
    private fun setPlayerImage() {
        when(dX) {
            -1 -> {
                if (playerIdle) {
                    playerImage = BitmapFactory.decodeResource(context.resources, com.example.intotheabyss.R.drawable.char_idle_left)
                } else{
                    playerImage = BitmapFactory.decodeResource(context.resources, com.example.intotheabyss.R.drawable.char_walk_left)
                }
            }
            1 -> {
                if (playerIdle) {
                    playerImage = BitmapFactory.decodeResource(context.resources, com.example.intotheabyss.R.drawable.char_idle_right)
                } else {
                    playerImage = BitmapFactory.decodeResource(context.resources, com.example.intotheabyss.R.drawable.char_walk_right)
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
            }
            1 -> {
                if (playerIdle) {
                    playerImage = BitmapFactory.decodeResource(context.resources, com.example.intotheabyss.R.drawable.char_idle_up)
                } else {
                    playerImage = BitmapFactory.decodeResource(context.resources, com.example.intotheabyss.R.drawable.char_walk_up)
                }
            }
        }
    }

    /**
     * Creates a Rect() object that will be the subset of the sprite sheet to display
     */
    private fun setAnimState() {
        val pWidth = playerImage.width
        rect = Rect((pWidth/6).toInt()*(animState),0,(pWidth/6).toInt()*(animState+1),192)
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
