package com.example.intotheabyss.game.gamecontroller

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

    //Ranges for movement buttons
    private val leftXRange: ClosedFloatingPointRange<Float> = 0f..buttonSize
    private val middleXRange: ClosedFloatingPointRange<Float> = buttonSize/2..(3/2)*buttonSize
    private val rightXRange: ClosedFloatingPointRange<Float> = buttonSize..2*buttonSize
    private val middleYRange: ClosedFloatingPointRange<Float> = (gameView.sHeight.toFloat() - (3/2)*buttonSize)..(gameView.sHeight.toFloat() - buttonSize/2)
    private val upYRange: ClosedFloatingPointRange<Float> = (gameView.sHeight.toFloat() - buttonSize)..(gameView.sHeight.toFloat() - 0f)
    private val downYRange: ClosedFloatingPointRange<Float> = (gameView.sHeight.toFloat() - 2*buttonSize)..(gameView.sHeight.toFloat() - buttonSize)

    //Range for action button
    private val actionX: ClosedFloatingPointRange<Float> = gameView.sWidth*0.75f..gameView.sWidth.toFloat()
    private val actionY: ClosedFloatingPointRange<Float> = gameView.sHeight*0.5f..gameView.sHeight.toFloat()

    //Tracking time for player speed
    private var lastTime: Long = 0
    private var currentTime: Long = 10000   //Just some number significantly longer than last time to start

    //Action timer
    var lastTimeAction: Long = 0
    var thisTimeAction: Long = 1000
    var actionTimer: Long = 500

    //Don't know why I need to do this
    var gameView: GameView = gameView

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
        var gAction: Int = 0
//        System.out.print("Get Action\n")
        thisTimeAction = System.currentTimeMillis()
        if (thisTimeAction - lastTimeAction > actionTimer) {
            if (input != null) {
                for (i in 0..input!!.pointerCount - 1) {
//                    curX = input!!.getX(i)
//                    curY = input!!.getY(i)
                    aAction = input!!.actionMasked
                    if ((actionX.contains(x)) and (actionY.contains(y)) and (action != MotionEvent.ACTION_UP)) {
                        System.out.print("Get Action called\n")
                        lastTimeAction = thisTimeAction
//                        gameView.setGAction(1)
                        gAction = 1
                    } else {
//                        gAction = 0
                    }
                    }
                }
            }
        //}
        return gAction
    }

    override fun updatePlayerLocation() {
        var newX: Int
        var newY: Int
        var moved = false
        val waitTime: Long = 100
        var curX = 0f
        var curY = 0f


        if (input != null) {
            for (i in 0..input!!.pointerCount - 1) {
                curX = input!!.getX(i)
                curY = input!!.getY(i)
                mAction = input!!.actionMasked
                if (mAction == MotionEvent.ACTION_UP) {
//                    gameView.dX = 0
//                    gameView.dY = 0
                    gameView.playerIdle = true
                    break
                }
                if ((rightXRange.contains(curX)) and (middleYRange.contains(curY))) {
                    newX = gameView.player!!.x + 1
                    if (gameView.lvlArray[gameView.player!!.y][newX].isPassable) {
                        currentTime = System.currentTimeMillis()
                        if ((currentTime - lastTime > waitTime) or (moved)) {
                            gameView.player!!.x = (newX)
                            moved = true
                            gameView.dX = 1
                            gameView.dY = 0
                            gameView.playerIdle = false
                            lastTime = System.currentTimeMillis()
                        }
                    }
                } else if ((leftXRange.contains(curX)) and (middleYRange.contains(curY))) {
                    newX = gameView.player!!.x - 1
                    if (gameView.lvlArray[gameView.player!!.y][newX].isPassable) {
                        currentTime = System.currentTimeMillis()
                        if ((currentTime - lastTime > waitTime) or (moved)) {
                            gameView.player!!.x = (newX)
                            moved = true
                            gameView.dX = -1
                            gameView.dY = 0
                            gameView.playerIdle = false
                            lastTime = System.currentTimeMillis()
                        }
                    }
                } else {
//                    gameView.dX = 0
                }

                if ((middleXRange.contains(curX)) and (upYRange.contains(curY))) {
                    newY = gameView.player!!.y + 1
                    if (gameView.lvlArray[newY][gameView.player!!.x].isPassable) {
                        currentTime = System.currentTimeMillis()
                        if ((currentTime - lastTime > waitTime) or (moved)) {
                            gameView.player!!.y = newY
                            gameView.dX = 0
                            gameView.dY = -1
                            gameView.playerIdle = false
                            lastTime = System.currentTimeMillis()
                        }
                    }
                } else if ((middleXRange.contains(curX)) and (downYRange.contains(curY)) and (curY != 0f)) {
                    newY = gameView.player!!.y - 1
                    if (newY < gameView.lvlArray.size) {
                        if (gameView.lvlArray[newY][gameView.player!!.x].isPassable) {
                            currentTime = System.currentTimeMillis()
                            if ((currentTime - lastTime > waitTime) or (moved)) {
                                gameView.player!!.y = newY
                                gameView.dX = 0
                                gameView.dY = 1
                                gameView.playerIdle = false
                                lastTime = System.currentTimeMillis()
                            }
                        }
                    }
                } else {
//                    gameView.dY = 0
                }
            }
        }
    }
}