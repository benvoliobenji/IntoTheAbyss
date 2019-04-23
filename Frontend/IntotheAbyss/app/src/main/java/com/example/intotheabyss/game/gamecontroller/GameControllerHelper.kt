package com.example.intotheabyss.game.gamecontroller

import android.graphics.*
import android.view.MotionEvent
import com.example.intotheabyss.game.GameView
import com.example.intotheabyss.game.drawplayer.gameView

//Ranges for movement buttons
var leftXRange: ClosedFloatingPointRange<Float> = 0f..10f
var middleXRange: ClosedFloatingPointRange<Float> = 0f..10f
var rightXRange: ClosedFloatingPointRange<Float> = 0f..10f
var middleYRange: ClosedFloatingPointRange<Float> = 0f..10f
var upYRange: ClosedFloatingPointRange<Float> = 0f..10f
var downYRange: ClosedFloatingPointRange<Float> = 0f..10f


//Range for action button
private var actionX: ClosedFloatingPointRange<Float> = 0f..10f
private var actionY: ClosedFloatingPointRange<Float> = 0f..10f

private var playersX: ClosedFloatingPointRange<Float> = 0f..10f
private var playersY: ClosedFloatingPointRange<Float> = 0f..10f

private var playersCloseX: ClosedFloatingPointRange<Float> = 0f..10f
private var playersCloseY: ClosedFloatingPointRange<Float> = 0f..10f

private var buttonSize = 5f
var controllerPaint = Paint()

class GameControllerHelper(g: GameView): GameControllerHelperInterface    {

    init{
        gameView = g    //Initialize which gameView we will be referencing
        buttonSize = gameView!!.sHeight.toFloat() * 10 / 64 //Determine proper size for buttons.

        controllerPaint.color = Color.LTGRAY    //Initialize paint for buttons
        controllerPaint.alpha = 50

        //Set up appropriate ranges for movement buttons. Long and skinny kinda.
        leftXRange = 0f..2*buttonSize
        middleXRange = 2*buttonSize..3* buttonSize
        rightXRange = 3*buttonSize..5*buttonSize
        middleYRange = (gameView!!.sHeight.toFloat()*27/64)..(gameView!!.sHeight.toFloat()*37/64)
        downYRange = (gameView!!.sHeight.toFloat()*27/64 - 2* buttonSize)..(gameView!!.sHeight.toFloat()*27/64)
        upYRange = (gameView!!.sHeight.toFloat()*37/64)..(gameView!!.sHeight.toFloat()*37/64 + 2* buttonSize)

        //Set up range for the action inputs. Bottom right corner of screen.
        actionX = gameView!!.sWidth*0.75f..gameView!!.sWidth.toFloat()
        actionY = gameView!!.sHeight*0.5f..gameView!!.sHeight.toFloat()

        //Set up range for open playerBoard button. Top right corner. Only active when playerBoard is off.
        playersX = (gameView!!.sWidth - 3* buttonSize)..gameView!!.sWidth.toFloat()
        playersY = (0.75* buttonSize).toFloat()..(1.5* buttonSize).toFloat()

        //Set up range for close playerBoard. Top left corner, only active when playerBoard is on.
        playersCloseX = (0f)..3*buttonSize
        playersCloseY = (0.75* buttonSize).toFloat()..(1.5* buttonSize).toFloat()
    }

    /**
     * Check if the action button was pressed. Checks if input coordinates fall in range
     * of the action button. Also checks that the action status of the input wasn't ACTION_UP
     *
     * @param x The x-coordinate of the input
     * @param y The y-coordinate of the input.
     * @param action The action status of the user input.
     * @return A boolean value. True if an action has occurred. False if not.
     */
    override fun checkActionRange(x: Float, y: Float, action: Int): Boolean {
                if ((actionX.contains(x)) and (actionY.contains(y)) and (action != MotionEvent.ACTION_UP)) {
                    return true
                }
        return false
    }

    /**
     * Function to check if the playerListButton has been pressed.
     * Returns if or if not it has been.
     *
     * @param x x-coordinate of the input
     * @param y y-coordinate of the input
     * @param action Action status of the user input
     * @param boolean Whether or not the playerListBoard is alraedy up or not.
     * @return Returns boolean if button not pressed. Returns inverse of boolean if button is pressed.
     */
    override fun checkPlayerListButton(x: Float, y: Float, action: Int, boolean: Boolean): Boolean {
        if (!boolean) {
            if ((playersX.contains(x)) and (playersY.contains(y)) and (action != MotionEvent.ACTION_UP)) {
                System.out.println("HIEHILSHLIE")
                return true
            }
        } else {
            if ((playersCloseX.contains(x)) and (playersCloseY.contains(y)) and (action != MotionEvent.ACTION_UP)) {
                return false
            }
        }


        return boolean
    }

    /**
     * Method to check if the player is moving
     * @param curX The last x position of the player
     * @param curY The last y position of the plaeyr
     *
     * This method will set dx and dy to the appropriate value.
     * These will be used to determine which direction the player is facing
     */
    override fun checkMovementDir(curX: Float, curY: Float): Point {
        val newX: Int
        val newY: Int

        if ((rightXRange.contains(curX)) and (middleYRange.contains(curY))) {
            newX = gameView!!.player!!.x + 1
            if (gameView!!.lvlArray[gameView!!.player!!.y][newX].isPassable) {
                gameView!!.playerIdle = false
                return Point(1,0)
            }
        } else if ((leftXRange.contains(curX)) and (middleYRange.contains(curY))) {
            newX = gameView!!.player!!.x - 1
            if (gameView!!.lvlArray[gameView!!.player!!.y][newX].isPassable) {
                gameView!!.playerIdle = false
                return Point(-1,0)
            }
        }

        if ((middleXRange.contains(curX)) and (upYRange.contains(curY))) {
            newY = gameView!!.player!!.y + 1
            if (gameView!!.lvlArray[newY][gameView!!.player!!.x].isPassable) {
                gameView!!.playerIdle = false
                return Point(0,1)
            }
        } else if ((middleXRange.contains(curX)) and (downYRange.contains(curY)) and (curY != 0f)) {
            newY = gameView!!.player!!.y - 1
            if (newY < gameView!!.lvlArray.size) {
                if (gameView!!.lvlArray[newY][gameView!!.player!!.x].isPassable) {
                    gameView!!.playerIdle = false
                    return Point(0,-1)
                }
            }
        }

    return Point(0,0)
    }

    /**
     * Method to draw the action button
     * @param canvas The Canvas object that we will be drawing on
     */
    override fun drawAction(canvas: Canvas) {

        val rect = Rect(actionX.start.toInt(), actionY.start.toInt(), actionX.endInclusive.toInt(), actionY.endInclusive.toInt())
        canvas.drawRect(rect, controllerPaint)

        drawPlayerBoardButton(canvas)
    }

    /**
     * Method to draw the player movement keys
     * @param canvas The Canvas object that we will be drawing on
     */
    override fun drawMovement(canvas: Canvas) {

        val rect = Rect(leftXRange.start.toInt(), middleYRange.start.toInt(), leftXRange.endInclusive.toInt(), middleYRange.endInclusive.toInt())
        canvas.drawRect(rect, controllerPaint)

        rect.left = rightXRange.start.toInt()
        rect.right = rightXRange.endInclusive.toInt()
        canvas.drawRect(rect, controllerPaint)

        rect.left = middleXRange.start.toInt()
        rect.right = middleXRange.endInclusive.toInt()
        rect.bottom = upYRange.endInclusive.toInt()
        rect.top = upYRange.start.toInt()
        canvas.drawRect(rect, controllerPaint)

        rect.bottom = downYRange.endInclusive.toInt()
        rect.top = downYRange.start.toInt()
        canvas.drawRect(rect, controllerPaint)
    }

    /**
     * Method to draw the player list functionality
     * @param canvas The Canvas object that we will be drawing on
     */
    override fun drawPlayerBoardButton(canvas: Canvas) {
        val rect = Rect(playersX.start.toInt(), playersY.start.toInt(), playersX.endInclusive.toInt(), playersY.endInclusive.toInt())
        val paint = Paint()
        paint.color = Color.WHITE
        paint.alpha = 80
        canvas.drawRect(rect, paint)
        paint.color = Color.WHITE
        paint.alpha = 100
        paint.textSize = 64f
        canvas.drawText("Player list", rect.left.toFloat(), rect.centerY().toFloat()+25f, paint)
    }

    /**
     * Method to draw the exitPlayerListBoard button.
     * Only draws button if playerListBoard is currently active.
     *
     * @param canvas The canvas that is being drawn to.
     */
    override fun drawExitButton(canvas: Canvas) {
        val rect = Rect(playersCloseX.start.toInt(), playersCloseY.start.toInt(), playersCloseX.endInclusive.toInt(), playersCloseY.endInclusive.toInt())
        val paint = Paint()
        paint.color = Color.WHITE
        paint.alpha = 80
        canvas.drawRect(rect, paint)
        paint.color = Color.WHITE
        paint.alpha = 100
        paint.textSize = 64f
        canvas.drawText("Exit", rect.left.toFloat(), rect.centerY().toFloat()+25f, paint)
    }
}