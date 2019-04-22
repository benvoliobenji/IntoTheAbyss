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
val partition = gameView!!.sHeight.toFloat()/64
var controllerPaint = Paint()

private var gameView: GameView? = null

class GameControllerHelper(g: GameView): GameControllerHelperInterface    {

    init{
        gameView = g
        buttonSize = gameView!!.sHeight.toFloat() * 10 / 64

        controllerPaint.color = Color.LTGRAY
        controllerPaint.alpha = 50

        leftXRange = 0f..2*buttonSize
        middleXRange = 2*buttonSize..3* buttonSize
        rightXRange = 3*buttonSize..5*buttonSize
        middleYRange = (gameView!!.sHeight.toFloat()*27/64)..(gameView!!.sHeight.toFloat()*37/64)
        downYRange = (gameView!!.sHeight.toFloat()*27/64 - 2* buttonSize)..(gameView!!.sHeight.toFloat()*27/64)
        upYRange = (gameView!!.sHeight.toFloat()*37/64)..(gameView!!.sHeight.toFloat()*37/64 + 2* buttonSize)

        actionX = gameView!!.sWidth*0.75f..gameView!!.sWidth.toFloat()
        actionY = gameView!!.sHeight*0.5f..gameView!!.sHeight.toFloat()

        playersX = (gameView!!.sWidth - 3* buttonSize)..gameView!!.sWidth.toFloat()
        playersY = (0.75* buttonSize).toFloat()..(1.5* buttonSize).toFloat()

        playersCloseX = (0f)..3*buttonSize
        playersCloseY = (0.75* buttonSize).toFloat()..(1.5* buttonSize).toFloat()
    }

    /**
     * Check if the action button was pressed
     */
    override fun checkActionRange(x: Float, y: Float, action: Int): Boolean {
                if ((actionX.contains(x)) and (actionY.contains(y)) and (action != MotionEvent.ACTION_UP)) {
                    return true
                }
        return false
    }

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
        var newX: Int
        var newY: Int
        var moved = false
        val waitTime: Long = 100
        var mAction: Int

        System.out.println("$curX, $curY")


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

        var rect = Rect(actionX.start.toInt(), actionY.start.toInt(), actionX.endInclusive.toInt(), actionY.endInclusive.toInt())
        canvas.drawRect(rect, controllerPaint)

        drawPlayerBoardButton(canvas)
    }

    /**
     * Method to draw the player movement keys
     * @param canvas The Canvas object that we will be drawing on
     */
    override fun drawMovement(canvas: Canvas) {

        var rect = Rect(leftXRange.start.toInt(), middleYRange.start.toInt(), leftXRange.endInclusive.toInt(), middleYRange.endInclusive.toInt())
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
        var rect = Rect(playersX.start.toInt(), playersY.start.toInt(), playersX.endInclusive.toInt(), playersY.endInclusive.toInt())
        var paint = Paint()
        paint.color = Color.WHITE
        paint.alpha = 80
        canvas.drawRect(rect, paint)
        paint.color = Color.WHITE
        paint.alpha = 100
        paint.textSize = 64f
        canvas.drawText("Player list", rect.left.toFloat(), rect.centerY().toFloat()+25f, paint)
    }
}