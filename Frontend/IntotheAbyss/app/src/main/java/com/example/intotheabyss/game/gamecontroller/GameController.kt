package com.example.intotheabyss.game.gamecontroller

import android.graphics.Canvas
import android.view.MotionEvent
import com.example.intotheabyss.game.GameView

class GameController(gameView: GameView): GameControllerInterface  {
    //Variables for reading player input
    var input: MotionEvent? = null
    var downTime: Long = 0
    var eventTime: Long = 0
    var aAction: Int = 0
    var mAction: Int = 0
    var iX: Float = 0.toFloat()     //x coord of finger press
    var iY: Float = 0.toFloat()     //y coord of finger press
    var metaState: Int = 0

//    val buttonSize = 450f
    private val buttonSize = ((gameView.sWidth)/4).toFloat()

    //Tracking time for player speed
    private var lastTime: Long = 0
    private var currentTime: Long = 10000   //Just some number significantly longer than last time to start

    //Action timer
    var lastTimeAction: Long = 0
    var thisTimeAction: Long = 1000
    var actionTimer: Long = 500

    var gcHelper: GameControllerHelperInterface? = null

    //Don't know why I need to do this
    var gameView: GameView = gameView

    init {
        gcHelper = GameControllerHelper(gameView)
    }

    /**
     * Method to retrieve event(s) from GameView
     */
    override fun setEvent(event: MotionEvent): MotionEvent {
        input = event
        iX = input!!.x
        iY = input!!.y

        return event
    }

    /**
     * Parse touch events to see if action button occurs
     */
    override fun getAction(x: Float, y: Float, action: Int): Int {
        thisTimeAction = System.currentTimeMillis()
        if (thisTimeAction - lastTimeAction > actionTimer) {
            if (gcHelper!!.checkActionRange(x, y, action)) {
                        lastTimeAction = thisTimeAction
                        return 1
                    }
                }
        return 0
        }

    override fun updatePlayerLocation() {
        var newX: Int
        var newY: Int
        var flag = false
        val waitTime: Long = 100
        var curX = 0f
        var curY = 0f

        if (input != null) {
            for (i in 0..input!!.pointerCount - 1) {
                if (input!!.actionMasked == MotionEvent.ACTION_UP) {
                    com.example.intotheabyss.game.drawplayer.gameView!!.playerIdle = true
                    flag = true
                }
                var x = input!!.getX(i)
                var y = input!!.getY(i)

                System.out.println("$x, $y")

                var p = gcHelper!!.checkMovementDir(x, y)
                System.out.println("$p")
                if ((p.x != 0) or (p.y != 0) and (!flag)) {
                    currentTime = System.currentTimeMillis()
                    if (currentTime - lastTime > waitTime) {
                        System.out.println("$p")

                        System.out.println("Got here")
                        gameView.player!!.x = gameView.player!!.x + p.x
                        gameView.player!!.y = gameView.player!!.y + p.y
                        gameView.dX = p.x
                        gameView.dY = -p.y
                        gameView.playerIdle = false
                        lastTime = System.currentTimeMillis()
                        break
                    }
                }
            }
        }
    }

    override fun drawController(canvas: Canvas)   {
        gcHelper!!.drawMovement(canvas)
        gcHelper!!.drawAction(canvas)
    }
}