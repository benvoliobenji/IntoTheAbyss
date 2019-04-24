package com.example.intotheabyss.game

import android.content.Context
import android.util.AttributeSet
import android.view.SurfaceHolder
import android.view.SurfaceView
import com.example.intotheabyss.dungeonassets.Wall
import com.example.intotheabyss.game.player.Player
import android.content.res.Resources
import android.graphics.*
import android.util.Log
import android.view.MotionEvent
import com.example.intotheabyss.game.drawplayer.DrawPlayer
import com.example.intotheabyss.utils.TileTypes
import com.example.intotheabyss.dungeonassets.Tile
import com.example.intotheabyss.game.drawplayer.DrawPlayerInterface
import com.example.intotheabyss.game.gamecontroller.GameController
import com.example.intotheabyss.game.gamecontroller.GameControllerInterface
import com.example.intotheabyss.game.levelhandler.LevelHandler
import com.example.intotheabyss.game.levelhandler.LevelHandlerInterface

class GameView(context: Context, attributes: AttributeSet) : SurfaceView(context, attributes), SurfaceHolder.Callback {

    var debug = true //set to true to get a generic level, false to get a level from DB

    private val thread: GameThread
    private var gameState: GameState? = null

    private var gameControllerInterface: GameControllerInterface
    private var drawPlayerInterface: DrawPlayerInterface
    private val levelHandlerInterface: LevelHandlerInterface = LevelHandler()
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

    var pList = false

    /**
     * This is kinda like a constructor. Just code that needs to be ran on startup. A lot of initialization of stuff, etc
     */
    init {

        // Don't really know what this does
        holder.addCallback(this)

        // This instantiates the game thread when we start the game
        thread = GameThread(holder, this)
        gameControllerInterface = GameController(this)
        drawPlayerInterface = DrawPlayer(this, playerImage)


        try {
            player = gameState!!.myPlayer
        } catch (e: java.lang.Exception) {
            e.printStackTrace()
        }
    }

    /**
     * More code that is a bit of a mystery to me
     */
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

    /**
     * On game init need to do some start up things (get threads going)
     * @param p0 The SurfaceHolder object that I have know idea what it does
     */
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

    /**
     * I have no idea what this is for. I got some skelaton code from online and I have yet found a reason to use
     * this method.
     */
    override fun surfaceChanged(p0: SurfaceHolder?, p1: Int, p2: Int, p3: Int) {
        //TODO: ??? Don't really know what to do here, yet!
    }

    /**
     * This is where we will update game variables. The calling thread will call this before the draw ;method
     */
    fun update() {
        gameControllerInterface!!.updatePlayerLocation()
//        gAction = gameControllerInterface!!.getAction(event!!.x, event!!.y, event!!.action)
        gAction = gameControllerInterface!!.getAction(event!!.x, event!!.y, event!!.action)
        checkNewLevel(gameState!!, gameControllerInterface)

        pList = gameControllerInterface!!.getPList(event!!.x, event!!.y, event!!.action, pList)

        gameState!!.myPlayer = player!!   //Not sure if this is necessary - but it couldn't hurt
//        println("Gamestate level = ${gameState!!.myPlayer.floorNumber}")
        var p = drawPlayerInterface.updateBoundaries(player!!)      //Make sure screen follows player around
        minX = p.x
        minY = p.y
    }

    /**
     * Method to check if we need to advance to the next level.
     * It just checks if an action has been performed AND the player is on the stairs
     *
     * @param g GameState variable that will be modified to reflect a new level is needed
     * @param gc The GameControllerInterface that does nothing but I'm not going to remove RIGHT now for fear of losing shit
     */
    fun checkNewLevel(g: GameState, gc: GameControllerInterface) {


        if (gAction > 0) {
            if (lvlArray[player!!.y][player!!.x].type == TileTypes.STAIR) {
                player!!.floorNumber++
                gameState!!.loading = true //Indicate that we want a new level
                println("Attempting to descend level. Currently at ${player!!.floorNumber}")
//                gAction = 0
            }
        }
    }

    /**
     * This is where we will draw things onto the game "canvas"
     * @param canvas The Canvas object that we will be drawing to
     */
    override fun draw(canvas: Canvas) {
        if (!pList) {
            super.draw(canvas)
            drawBG(canvas)

            drawPlayerInterface.drawPlayer(dX, dY, context, canvas, player!!, gAction)
            gameControllerInterface.drawController(canvas)
            drawOtherPlayers(canvas)
        } else  {

            canvas.drawColor(Color.BLACK)
        }

        var paint = Paint()
        paint.color = Color.RED
        paint.textSize = 100f

        if (pList) {
            canvas.drawText("TEST", 0f, 100f, paint)
            drawOtherPlayers(canvas)
        }
    }

    /**
     * How we get info from screen touch events.
     * @param event The motionEvent object obtained from the screen
     *
     * I probably butchered this, but it works so YOLO
     */
    override fun dispatchTouchEvent(event: MotionEvent?): Boolean {
        event!!

        this.event = gameControllerInterface.setEvent(event)
        return true

//        Removing the super call seems dangerous, but it fixed my problems so idk
//        return super.dispatchTouchEvent(event)
    }


    /**
     * Method to set the gameState for this object to reference
     * @param gState The gameState variable to set gameState equal to
     */
     fun setGameState(gState: GameState)  {
        gameState = gState
    }

    /**
     *Method to set the level to a generic level if no connection established
     */
    private fun setLevel() {
        lvlArray = levelHandlerInterface.genericLevel(lvlSize.x, lvlSize.y)
    }

    /**
     * Method to draw the background (aka the level)
     * @Param canvas - the canvas object to draw to
     */
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

    /**
    Function to draw other entities. This includes other players AND monsters
     *@param canvas The canvas object to draw to
     */
    private fun drawOtherPlayers(canvas: Canvas) {

        var testPlayer = Player("test", "pid", 10, 0, 15, 15)
        var playerList = gameState!!.playersInLevel
        playerList["test"] = testPlayer
        Log.i("playerList", gameState!!.playersInLevel.toString())
//        var otherPlayers = gameState!!.playersInLevel.asIterable()
        var otherPlayers = playerList.asIterable()

        var curPlayer: Player

        if (gameState!!.playersInLevel.isNotEmpty()) {
            for (key in gameState!!.playersInLevel.keys) {
                var otherPlayer = gameState!!.playersInLevel[key]

                if (isVisible(gameState!!.myPlayer, otherPlayer!!)) {
                    drawPlayerInterface.drawPlayer(0, 0, context, canvas, otherPlayer, otherPlayer.actionStatus)
                }
            }
        }
//        //Iterate through all entities in the list
//        while (otherPlayers.iterator().hasNext())   {
//            curPlayer = otherPlayers.iterator().next().value
//
//            if (isVisible(gameState!!.myPlayer, curPlayer)) {   //Check if entity should be visible
//                drawPlayerInterface.drawPlayer(0, 0, context, canvas, curPlayer, curPlayer.actionStatus)    //Draw player if so
//            }
//        }

        if (pList)  {
            var i = 0
            var buttonSize = 25f
            var paint = Paint()
            paint.color = Color.WHITE
            var rect = Rect(25, i*buttonSize.toInt(), 250, (i+3)*buttonSize.toInt())
            canvas.drawRect(rect, paint)
            paint.color = Color.BLACK
            paint.textSize = 35f
            canvas.drawText("Player Name", 25f, rect.exactCenterY(), paint)

            //TODO: LOOK AT ABOVE CODE FOR CHANGES
            otherPlayers = playerList.asIterable()
            while (otherPlayers.iterator().hasNext())   {
                var rect = Rect(25, i*buttonSize.toInt(), 125, (i+3)*buttonSize.toInt())
                canvas.drawRect(rect, paint)
            }
        }
    }

    /**
    Function to determine if one player is visible from the other.
    *@Param p1 - the originating player to start from
    *@Param p2 - Next player. Checking to see if in LOS of p1
    *@Return true if a straight line can be drawn from p1 -> p2 without intersecting a wall.
    *@Return false otherwise.
     */
    private fun isVisible(p1: Player, p2: Player) : Boolean {
        var xDif = (p2.x - p1.x).toDouble()
        var yDif = (p2.y - p1.y).toDouble()
        var distAway = Math.sqrt(xDif*xDif+yDif*yDif)

        var x = p1.x.toDouble()
        var y = p1.y.toDouble()

        xDif = xDif/distAway    //Get x component of unit vector (x,y)
        yDif = yDif/distAway    //Get y component of unit vector (x,y)

        var change = 0

        while (change < distAway) {
            x += xDif   //Add unit vector components
            y += yDif   //Add unit vector components
            change++    //Increase distance traveled by 1 unit

            //Check if collision with wall
            if ((lvlArray[x.toInt()][y.toInt()].type == TileTypes.WALL) || (lvlArray[x.toInt()+1][y.toInt()].type == TileTypes.WALL) ||
                (lvlArray[x.toInt()][y.toInt()+1].type == TileTypes.WALL) || (lvlArray[x.toInt()+1][y.toInt()].type == TileTypes.WALL)) {
                return false
            }
        }

        //No collision, so display
        return true
    }

    companion object {
        private var tile: Tile = Wall()
    }
}
