package com.example.intotheabyss.game
import android.content.Context
import android.view.MotionEvent


import android.content.res.Resources
import android.graphics.*
import android.util.AttributeSet
import android.view.SurfaceHolder
import android.view.SurfaceView
import com.example.intotheabyss.dungeonassets.Tile
import com.example.intotheabyss.dungeonassets.Wall
import com.example.intotheabyss.game.entity.player.Player
import com.example.intotheabyss.game.drawplayer.DrawPlayer
import com.example.intotheabyss.game.drawplayer.DrawPlayerInterface
import com.example.intotheabyss.game.entity.Entity
import com.example.intotheabyss.game.event.AttackEvent
import com.example.intotheabyss.game.event.DeathEvent
import com.example.intotheabyss.game.event.EventType
import com.example.intotheabyss.game.gamecontroller.GameController
import com.example.intotheabyss.game.gamecontroller.GameControllerInterface
import com.example.intotheabyss.game.gamecontroller.PlayerBoard
import com.example.intotheabyss.game.gamecontroller.PlayerBoardInterface
import com.example.intotheabyss.game.levelhandler.LevelHandler
import com.example.intotheabyss.game.levelhandler.LevelHandlerInterface
import com.example.intotheabyss.utils.TileTypes
import kotlin.math.roundToInt
import kotlin.random.Random

class GameView(context: Context, attributes: AttributeSet) : SurfaceView(context, attributes), SurfaceHolder.Callback {

    var debug = true //set to true to get a generic level, false to get a level from DB
    private var dead = false
    var deathActivity = false
    var kicked = false
    var loaded = false

    private val thread: GameThread
    var gameState: GameState? = null

    private var gameControllerInterface: GameControllerInterface
    private var drawPlayerInterface: DrawPlayerInterface
    private val levelHandlerInterface: LevelHandlerInterface = LevelHandler()
    private var playerBoard: PlayerBoardInterface? = null

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
    private var floorImage: Bitmap =
        BitmapFactory.decodeResource(context.resources, com.example.intotheabyss.R.drawable.floor)
    private var wallImage: Bitmap =
        BitmapFactory.decodeResource(context.resources, com.example.intotheabyss.R.drawable.wall)
    private val stairsImage: Bitmap =
        BitmapFactory.decodeResource(context.resources, com.example.intotheabyss.R.drawable.stairs)
    private var playerImage: Bitmap =
        BitmapFactory.decodeResource(context.resources, com.example.intotheabyss.R.drawable.char_idle_down)

    //Variables for following player
    var minX: Int = 0
    var minY: Int = 0

    //Variables relating to playerBorad
    private var pList = false
    private var playerTextPaint = Paint()
    private var bSize = sHeight.toFloat() * 10 / 64
    private var playerBoardPaint = Paint()
    private val infoPaint = Paint()

    /**
     * This is kinda like a constructor. Just code that needs to be ran on startup. A lot of initialization of stuff, etc
     */
    init {
        holder.addCallback(this)

        thread = GameThread(holder, this)
        gameControllerInterface = GameController(this)
        drawPlayerInterface = DrawPlayer(this, playerImage)

        playerTextPaint.color = Color.BLACK
        playerTextPaint.textSize = 70f

        playerBoardPaint.color = Color.WHITE
        playerBoardPaint.alpha = 100
        playerBoardPaint.textSize = 20f

        infoPaint.color = Color.WHITE
        infoPaint.textSize = 30f

        playerBoard = PlayerBoard(this, bSize)
        try {
            player = gameState!!.myPlayer
        } catch (e: java.lang.Exception) {
            e.printStackTrace()
        }

        Thread.sleep(5000)
        loaded = true
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
            } catch (e: java.lang.Exception) {
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

    }

    /**
     * This is where we will update game variables. The calling thread will call this before the draw ;method
     */
    fun update() {
        if (!dead) {
            if (player!!.x == 13)   {
                player!!.health--
            }
            if ((player!!.x == 33) and (player!!.y == 15))  {
                player!!.health += Random.nextInt(-2,3)
            }

            updatePlayer()
            updateEvents()

            if (pList) {
                playerBoard!!.getPlayerBoardAction(gameState!!.entitiesInLevel, event.x, event.y, event)
                playerBoard!!.getPlayerGroupAction(event.x, event.y)
            }
            if (player!!.health < 1) {
                dead = true
                val death = DeathEvent(player!!.ID)
                gameState!!.eventQueue.add(death)
            }
        }   else    {
            if (gameState!!.eventQueue.isEmpty())   {

                deathActivity = true
            }
        }
    }

    /**
     * Method to do some general updates for the Player
     * Just a bunch of calls that were ugly, so I abstracted to a class
     */
    private fun updatePlayer()  {
        gameControllerInterface.updatePlayerLocation()
        gAction = gameControllerInterface.getAction(event!!.x, event!!.y, event!!.action)
        if (gAction == 1)   {
            player!!.action = 1
        }
        checkNewLevel()

        pList = gameControllerInterface.getPList(event!!.x, event!!.y, event!!.action, pList)
//        gameState!!.myPlayer = player!!   //Not sure if this is necessary - but it couldn't hurt
        player = gameState!!.myPlayer

        val p = drawPlayerInterface.updateBoundaries(player!!)      //Make sure screen follows player around
        minX = p.x
        minY = p.y
    }

    /**
     * Method to update the event queue that is retrieved from the server.
     */
    private fun updateEvents()  {
        while (!gameState!!.eventQueueDisplay.isEmpty())  {
            val event = gameState!!.eventQueueDisplay.poll()
            if (event.type == EventType.ATTACK) {
                gameState!!.entitiesInLevel[event.performerID]!!.action = 1
            } else if (event.type == EventType.DISCONNECT)  {
                kicked = true
            }
        }
    }

    /**
     * Method to check if we need to advance to the next level.
     * It just checks if an action has been performed AND the player is on the stairs
     */
    private fun checkNewLevel() {
        if (gAction > 0) {
            if (lvlArray[player!!.y][player!!.x].type == TileTypes.STAIR) {
                player!!.floor++
                gameState!!.loading = true //Indicate that we want a new level
            }
        }
    }

    /**
     * Method to put attack event in gameState.eventQueue
     *
     * @param dx x direction player is facing
     * @param dy y direction player is facing
     */
    fun checkAttack(dx: Int, dy: Int)   {
        val atkX = player!!.x + dx
        val atkY = player!!.y + dy

        if (gameState!!.entitiesInLevel.isNotEmpty()) {
            for (p in gameState!!.entitiesInLevel) {
                if ((p.value.x == atkX) and (p.value.y == atkY) and !checkInGroup(p.key)) {
                    val i = Random.nextInt(0, 1000)
                    var dmg = Random.nextInt(1, 3)
                    if (i == 5) {
                        dmg = 7
                    }
                    val atk = AttackEvent(player!!.ID, p.key, dmg)
                    gameState!!.eventQueue.add(atk)
                }
            }
        }
    }

    /**
     * Check if a player is in my group.
     *
     * @param s The ID of the player that we're checking
     */
    private fun checkInGroup(s: String): Boolean {
        for (p in player!!.party)   {
            if (p.ID == s)  {
                return true
            }
        }

        return false
    }

    /**
     * This is where we will draw things onto the game "canvas"
     * @param canvas The Canvas object that we will be drawing to
     */
    override fun draw(canvas: Canvas) {


        if (!dead) {
            if (!pList) {
                super.draw(canvas)
                drawBG(canvas)

                drawPlayerInterface.drawPlayer(dX, dY, context, canvas, player!!, gAction, true)
                player!!.action = 0
                gameControllerInterface.drawController(canvas)
                drawOtherPlayers(canvas)

                canvas.drawText("Level: ${player!!.floor}", 40f, 100f, infoPaint)
            } else {
                canvas.drawColor(Color.BLACK)
                playerBoard!!.drawPlayerBoard(canvas, gameState!!.entitiesInLevel)
                gameControllerInterface.drawExitButton(canvas)
            }
        }   else    {
            canvas.drawColor(Color.BLACK)
            val deathPaint = Paint()
            deathPaint.color = Color.RED
            deathPaint.textSize = 50f
            canvas.drawText("You died", sHeight.toFloat()/2, sWidth.toFloat()/2, deathPaint)
        }

        if ((!loaded) or (lvlArray.size < 5))    {
            canvas.drawColor(Color.CYAN)
            val p = Paint()
            p.color = Color.BLACK
            p.textSize = 60f
            canvas.drawText("Loading: please wait a few seconds", 25f, 500f, p)
            canvas.drawText("Hint: Try hitting your opponent while avoiding getting hit.", 25f, 650f, p)
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
        lvlArray = gameState!!.level
        if (debug) {
//            setLevel()
            lvlArray = gameState!!.level
        } else {
            lvlArray = gameState!!.level
        }

        //If offline, try adding in the ValidLevel thing to fix it
        if (lvlArray.isNullOrEmpty()) {
            if (gameState!!.level.isNotEmpty()) {
                lvlArray = gameState!!.level
            } else {
//                setLevel()
            }
            validLevel = true
        }

        //image variable - will maybe be updated to be more efficient later
        var image: Bitmap = BitmapFactory.decodeResource(resources, com.example.intotheabyss.R.drawable.panda)

        //Loop through all tiles to be displayed, and a few others to minimize lag
        for (i in minX..minX + dimWidth) {
            if ((i > -1) and (i < lvlSize.x))
                for (j in minY..minY + dimHeight) {
                    if ((j > -1) and (j < lvlSize.y)) {
                        //Try to get the filetype, and then print image - should only fail if undefined tile (aka not on map)
                        if (lvlArray[j][i].type == TileTypes.FLOOR) {  //Set image to floorImage
                            image = floorImage
                        } else if (lvlArray[j][i].type == TileTypes.WALL) {
                            image = wallImage                                   //Set image to wallImage
                        } else if (lvlArray[j][i].type == TileTypes.STAIR) {
                            image = stairsImage
//                            println("Stairs at: ($j,$i)")
                        }

                        canvas.drawBitmap(
                            image,
                            (((i - minX) * tileSize)).toFloat(),
                            (((j - minY) * tileSize) + 1).toFloat(),
                            null
                        )
                    }
                }
        }
    }

    /**
    Function to draw other entities. This includes other players AND monsters
     *@param canvas The canvas object to draw to
     */
    private fun drawOtherPlayers(canvas: Canvas) {

        if (gameState!!.entitiesInLevel.isNotEmpty()) {
            var i = 0

            for (key in gameState!!.entitiesInLevel.keys) {
                val otherEntity = gameState!!.entitiesInLevel[key]
                gameState!!.entitiesInLevel[key]!!.action = 0

                val otherPlayer = otherEntity as Player

                if (otherPlayer.floor == player!!.floor) {
                    if (!pList) {
                        drawPlayerInterface.drawPlayer(
                            0,
                            0,
                            context,
                            canvas,
                            otherPlayer,
                            otherPlayer.action,
                            false
                        )
                    } else {
                        val rect = Rect(
                            25, ((2 + i) * bSize).toInt(), 25 + 3 * bSize.toInt(),
                            (3 + i) * bSize.toInt()
                        )
                        canvas.drawRect(rect, playerBoardPaint)
                        canvas.drawText(
                            gameState!!.entitiesInLevel[key]!!.ID, 25f,
                            rect.exactCenterY(), playerTextPaint
                        )
                    }
                }
            }
        }
    }

    /**
    Function to determine if one player is visible from the other.
     Apparently this code doesn't work, so its not being used.
     No time to fix :/

     *@Param p1 - the originating player to start from
     *@Param p2 - Next player. Checking to see if in LOS of p1
     *@Return true if a straight line can be drawn from p1 -> p2 without intersecting a wall.
     *@Return false otherwise.
     */
    private fun isVisible(p1: Player, p2: Entity): Boolean {
        var xDif = (p2.x - p1.x).toDouble()
        var yDif = (p2.y - p1.y).toDouble()
        val distAway = Math.sqrt(xDif * xDif + yDif * yDif)

        var x = p1.x.toDouble()
        var y = p1.y.toDouble()

        xDif /= distAway    //Get x component of unit vector (x,y)
        yDif /= distAway    //Get y component of unit vector (x,y)

        var change = 0

        while (change < distAway) {
            x += xDif   //Add unit vector components
            y += yDif   //Add unit vector components
            change++    //Increase distance traveled by 1 unit

            if ((x < 0) or (x > lvlArray[0].size) or (y < 0) or (y > lvlArray.size)) return true

            //Check if collision with wall
            if (lvlArray[x.roundToInt()][y.roundToInt()].type == TileTypes.WALL) {
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
