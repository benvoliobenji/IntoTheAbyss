package com.example.intotheabyss.game.gamecontroller

import android.graphics.Point
import android.view.MotionEvent
import com.example.intotheabyss.game.GameView
import com.example.intotheabyss.game.drawplayer.gameView

//Ranges for movement buttons
private var leftXRange: ClosedFloatingPointRange<Float> = 0f..10f
private var middleXRange: ClosedFloatingPointRange<Float> = 0f..10f
private var rightXRange: ClosedFloatingPointRange<Float> = 0f..10f
private var middleYRange: ClosedFloatingPointRange<Float> = 0f..10f
private var upYRange: ClosedFloatingPointRange<Float> = 0f..10f
private var downYRange: ClosedFloatingPointRange<Float> = 0f..10f


//Range for action button
private var actionX: ClosedFloatingPointRange<Float> = 0f..10f
private var actionY: ClosedFloatingPointRange<Float> = 0f..10f

private var buttonSize = 10f

private var gameView: GameView? = null

class GameControllerHelper(g: GameView): GameControllerHelperInterface    {

    init{
        gameView = g
        buttonSize = ((gameView!!.sWidth)/4).toFloat()

        leftXRange = 0f..buttonSize
        middleXRange = buttonSize/2..(3/2)*buttonSize
        rightXRange = buttonSize..2*buttonSize
        middleYRange = (gameView!!.sHeight.toFloat() - (3/2)*buttonSize)..(gameView!!.sHeight.toFloat() - buttonSize/2)
        upYRange = (gameView!!.sHeight.toFloat() - buttonSize)..(gameView!!.sHeight.toFloat() - 0f)
        downYRange = (gameView!!.sHeight.toFloat() - 2*buttonSize)..(gameView!!.sHeight.toFloat() - buttonSize)

        actionX = gameView!!.sWidth*0.75f..gameView!!.sWidth.toFloat()
        actionY = gameView!!.sHeight*0.5f..gameView!!.sHeight.toFloat()
    }

    override fun checkActionRange(x: Float, y: Float, action: Int): Boolean {
                if ((actionX.contains(x)) and (actionY.contains(y)) and (action != MotionEvent.ACTION_UP)) {
                    return true
                }
        return false
    }

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
}