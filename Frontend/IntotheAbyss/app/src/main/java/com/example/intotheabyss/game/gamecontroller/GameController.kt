package com.example.intotheabyss.game.gamecontroller

import android.graphics.Canvas
import android.view.MotionEvent
import android.view.MotionEvent.*
import com.example.intotheabyss.game.GameView

class GameController(
    private var gameView: GameView
): GameControllerInterface  {

    //Variables for reading player input
    private var input: MotionEvent? = null

    private var iX: Float = 0.toFloat()     //x coord of finger press
    private var iY: Float = 0.toFloat()     //y coord of finger press

    //Tracking time for player speed
    private var lastTime: Long = 0
    private var currentTime: Long = 10000   //Just some number significantly longer than last time to start

    //Action timer
    private var lastTimeAction: Long = 0
    private var thisTimeAction: Long = 1000
    private var actionTimer: Long = 500

    var gcHelper: GameControllerHelperInterface? = null

    init {
        gcHelper = GameControllerHelper(gameView)
    }

    /**
     * Method to retrieve event(s) from GameView. This is in reference to MotionEvents aka user input
     *
     * @param event The MotionEvent last recorded from phone.
     */
    override fun setEvent(event: MotionEvent): MotionEvent {
        input = event
        iX = input!!.x
        iY = input!!.y

        return event
    }

    /**
     * Parse touch events to see if action button occurs.
     * Will only accept a player action at a certain interval.
     * Checks if player has touched in the action button range.
     *
     * @param x x-coordinate of the user input
     * @param y y-coordinate of the user input
     * @param action the action status of the user input. If ACTION_UP, will not perform. Otherwise will.
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

    /**
     * Parse touch event to see if user pressing playerBoard button.
     * Looks for user input at button for playerBoard.
     *
     * @param x x-coordinate of user input
     * @param y y-coordinate of user input
     * @param action Action status of user input. If ACTION_UP, nothing happens. Otherwise opperates normally.
     * @param bool Variables to maintain if we are currently looking at the playerBoard. Don't want button to be "on" if currently viewing.
     */
    override fun getPList(x: Float, y: Float, action: Int, bool: Boolean): Boolean {
        if (gcHelper!!.checkPlayerListButton(x, y, action, bool)) {
            return true
        }
        return false
    }

    /**
     * Updates the player location based on the user input and the game controller.
     * Uses gameControllerHelper to determine which, if any, buttons were pressed.
     * Then, if next location is valid, player will be moved to new location.
     */
    override fun updatePlayerLocation() {
        var flag = false
        val waitTime: Long = 100

        if (input != null) {
            for (i in 0 until input!!.pointerCount) {   //Check all inputs
                if (input!!.actionMasked == ACTION_UP) {//Make sure they aren't players releasing button
                    com.example.intotheabyss.game.drawplayer.gameView!!.playerIdle = true
                    flag = true
                }

                //Check if new inputs are in button ranges
                val x = input!!.getX(i)
                val y = input!!.getY(i)
                val p = gcHelper!!.checkMovementDir(x, y)

                //If time to move
                if ((p.x != 0) or (p.y != 0) and (!flag)) {
                    currentTime = System.currentTimeMillis()
                    if (currentTime - lastTime > waitTime) {

                        //Move player, update direction variable.
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

    /**
     * Method to call helper method to draw controlls.
     *
     * @param canvas The canvas object that will be drawn to.
     */
    override fun drawController(canvas: Canvas)   {
        gcHelper!!.drawMovement(canvas)
        gcHelper!!.drawAction(canvas)
    }

    /**
     * Method to draw the exit button. Will call on helper method.
     *
     * @param canvas The canvas object that will be drawn to.
     */
    override fun drawExitButton(canvas: Canvas) {
        gcHelper!!.drawExitButton(canvas)
    }
}