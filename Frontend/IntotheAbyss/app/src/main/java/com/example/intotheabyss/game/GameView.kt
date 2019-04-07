package com.example.intotheabyss.game

import android.content.Context
import android.util.AttributeSet
import android.view.SurfaceHolder
import android.view.SurfaceView
import com.example.intotheabyss.dungeonassets.Floor
import com.example.intotheabyss.dungeonassets.Wall
import com.example.intotheabyss.game.player.Player
import android.content.res.Resources
import android.graphics.*
import android.view.MotionEvent
import com.example.intotheabyss.game.drawplayer.DrawPlayer
import com.example.intotheabyss.utils.TileTypes
import com.example.intotheabyss.dungeonassets.Tile
import com.example.intotheabyss.game.gamecontroller.GameController
import com.example.intotheabyss.game.levelhandler.LevelHandler

class GameView(context: Context, attributes: AttributeSet) : SurfaceView(context, attributes), SurfaceHolder.Callback {

    var debug = true //set to true to get a generic level, false to get a level from DB

    private val thread: GameThread
    private var gameState: GameState? = null

    private var gameController: GameController
    private var drawPlayer: DrawPlayer
    private val levelHandler = LevelHandler()
    var gAction = 0         //GameController sets to 0 if no action, something else if there is
    var event = MotionEvent.obtain(0, 0, MotionEvent.ACTION_UP, 0f, 0f, 0)

    //Screen dimensions
    var sWidth = Resources.getSystem().displayMetrics.widthPixels
    var sHeight = Resources.getSystem().displayMetrics.heightPixels


    private val tileSize = 64
    //How many tiles will fit on screen
    val dimWidth: Int = sWidth / tileSize
    val dimHeight: Int = sHeight / tileSize

    //Variables for displaying player
    var player: Player? = null
    var dX: Int = 0 //If player facing left, dX=-1; if facing right, dX=1; If neither, dX=0 (not currently supported)
    var dY: Int = 0 //If player facing up, dY=1; if facing down, dY=-1; If neither, dY=0 (this is now the only supported mode)
    var playerIdle = true   //True if player not moving, false if player is moving

    //Action timer variables
    var lastTimeAction: Long = 0
    var thisTimeAction: Long = 1000
    var actionTimer: Long = 500

    //Vars for creating default levels
    private val lvlSize: Point = Point(100, 25)
    var lvlArray = Array(lvlSize.y) { Array(lvlSize.x) { tile } }
    private var validLevel = false

    //Bitmaps
    private var floorImage: Bitmap = BitmapFactory.decodeResource(context.resources, com.example.intotheabyss.R.drawable.floor)
    private var wallImage: Bitmap = BitmapFactory.decodeResource(context.resources, com.example.intotheabyss.R.drawable.wall)
    private val stairsImage: Bitmap = BitmapFactory.decodeResource(context.resources, com.example.intotheabyss.R.drawable.stairs)
    private var playerImage: Bitmap = BitmapFactory.decodeResource(context.resources, com.example.intotheabyss.R.drawable.char_idle_down)

    //Variables for following player
    private val xBuffer: Int = 5
    private val yBuffer: Int = 5
    var minX: Int = 0
    var minY: Int = 0
    var offsetPoint = Point(0,0)


    init {

        // Don't really know what this does
        holder.addCallback(this)

        // This instantiates the game thread when we start the game
        thread = GameThread(holder, this)
        gameController = GameController(this)
        drawPlayer = DrawPlayer(this, playerImage)


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
        player!!.setImage(BitmapFactory.decodeResource(resources, com.example.intotheabyss.R.drawable.char_idle_down))
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
        gameController!!.updatePlayerLocation()
//        gAction = gameController!!.getAction(event!!.x, event!!.y, event!!.action)
        System.out.println("$gAction")
        checkNewLevel(gameState!!, gameController)
        gameState!!.myPlayer = player!!   //Not sure if this is necessary - but it couldn't hurt
//        println("Gamestate level = ${gameState!!.myPlayer.floorNumber}")
        var p = drawPlayer.updateBoundaries(player!!)      //Make sure screen follows player around
        minX = p.x
        minY = p.y
    }

    fun testUpdate(testDrawPlayer: DrawPlayer, testGameController: GameController) {
        offsetPoint = testDrawPlayer.updateBoundaries(Player())
//        testGameController!!.updatePlayerLocation()
//        gAction = gameController!!.getAction(event!!.x, event!!.y, event!!.action)
//        System.out.println("$gAction")
//        checkNewLevel()
//        gameState!!.myPlayer = player!!   //Not sure if this is necessary - but it couldn't hurt
//        println("Gamestate level = ${gameState!!.myPlayer.floorNumber}")
//        var p = testDrawPlayer.updateBoundaries(player!!)      //Make sure screen follows player around
//        minX = p.x
//        minY = p.y
    }

    fun checkNewLevel(g: GameState, gc: GameController) {
        gAction = gc.getAction(event!!.x, event!!.y, event!!.action)

        if (gAction > 0) {
            if (lvlArray[player!!.y][player!!.x].type == TileTypes.STAIR) {
                player!!.floorNumber++
                g.loading = true //Indicate that we want a new level
                println("Attempting to descend level. Currently at ${player!!.floorNumber}")
//                gAction = 0
            }
        }
    }

    fun testCheckNewLevel(g: GameState, gc: GameController) {
        val gAction = gc.getAction(event!!.x, event!!.y, event!!.action)

        if (gAction > 0) {
            if (true) {
//                player!!.floorNumber++
                g.loading = true //Indicate that we want a new level
//                println("Attempting to descend level. Currently at ${player!!.floorNumber}")
//                gAction = 0
            }
        }
    }

    /**
     * This is where we will draw things onto the game "canvas"
     */
    override fun draw(canvas: Canvas) {
        super.draw(canvas)
        drawBG(canvas)

        drawPlayer.setPlayerImage(dX,dY,context,gAction)
        drawPlayer.drawPlayer(canvas,player!!, gAction)
    }

    /**
     * How we get info from screen touch events.
     */
    override fun dispatchTouchEvent(event: MotionEvent?): Boolean {
        event!!

        this.event = gameController.setEvent(event)
        return true

//        Removing the super call seems dangerous, but it fixed my problems so idk
//        return super.dispatchTouchEvent(event)
    }



     fun setGameState(gState: GameState)  {
        gameState = gState
    }

    private fun setLevel() {
        lvlArray = levelHandler.genericLevel(lvlSize.x, lvlSize.y)
    }

    private fun drawBG(canvas: Canvas) {
        if (debug) {
            setLevel()
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
                setLevel()
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

    companion object {
        private var tile: Tile = Wall()
    }
}
