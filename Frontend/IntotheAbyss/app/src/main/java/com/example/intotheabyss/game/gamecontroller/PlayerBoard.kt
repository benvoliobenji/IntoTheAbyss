package com.example.intotheabyss.game.gamecontroller

import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Rect
import android.util.Log
import android.view.MotionEvent
import com.example.intotheabyss.game.GameView
import com.example.intotheabyss.game.player.Player

private var playerTextPaint = Paint()
private var playerBoardPaint = Paint()
private var friendPaint = Paint()

private var rectArray = ArrayList<Rect>()
private var keyArray = ArrayList<String>()
private var groupRectArray = ArrayList<Rect>()
private var groupKeyArray = ArrayList<String>()

private var removed = false
private var lastX = 0f
private var lastY = 0f

class PlayerBoard(private val gameView: GameView,
                  private val bSize: Float) : PlayerBoardInterface    {

    init {
        playerTextPaint.color = Color.BLACK
        playerTextPaint.textSize = 70f

        playerBoardPaint.color = Color.WHITE
        playerBoardPaint.alpha = 90

        friendPaint.color = Color.GREEN
        friendPaint.alpha = 90
    }

    override fun drawPlayerBoard(canvas: Canvas, playerList: HashMap<String, Player>) {
        rectArray.clear()
        groupRectArray.clear()
        keyArray.clear()
        groupKeyArray.clear()

        var i = 0
        for (p in playerList.keys) {
            val player = playerList[p]
            playerList(player!!, canvas, i)

            keyArray.add(p)
            i++
        }

        i = 0
        for (g in gameView.player!!.group)  {
            groupList(g.value, canvas, i, g.key)
            i++
        }
    }

    private fun playerList(player: Player, canvas: Canvas, i: Int)  {
        val rect = Rect(25, ((2+i)*bSize).toInt(), 25 + 5*bSize.toInt(), (3+i)*bSize.toInt())
        canvas.drawRect(rect, playerBoardPaint)
        canvas.drawText(player!!.playerName, 25f, rect.exactCenterY(), playerTextPaint)

        rectArray.add(rect)
    }

    private fun groupList(player: Player, canvas: Canvas, i: Int, p: String)   {
        val right = gameView.sWidth
        val offset = right - 25 - 5*bSize.toInt()
        val rect = Rect(offset, ((2+i)*bSize).toInt(), right - 25, (3+i)*bSize.toInt())
        canvas.drawRect(rect, friendPaint)
        canvas.drawText(player!!.playerName, offset.toFloat(), rect.exactCenterY(), playerTextPaint)

        groupRectArray.add(rect)
        groupKeyArray.add(p)
    }

    override fun getPlayerBoardAction(playerList: HashMap<String, Player>, x: Float, y: Float, action: MotionEvent) {
        for (r in rectArray)    {
            if ((lastX != x) and (lastY != y)) {
                if ((isInRect(x, y, r)) and (!removed)) {
                    val i = rectArray.indexOf(r)
                    val s = keyArray[i]

//                    gameView.gameState!!.playersInLevel.remove(s)
                    addToGroup(playerList[s]!!, s)
                    removed = true
                    lastX = x
                    lastY =y
                }
            }
        }
        removed = false
    }

    override fun getPlayerGroupAction(x: Float, y: Float, action: MotionEvent) {
        for (r in groupRectArray)   {
            if ((lastX != x) and (lastY != y))  {
                if ((isInRect(x, y, r)) and (!removed)) {
                    removeFromGroup(groupKeyArray[groupRectArray.indexOf(r)])
                }
            }
        }
    }

    private fun addToGroup(player: Player, key: String): Boolean    {
        if (player.group.isEmpty())  {
            if (gameView.player!!.group.size < 1)   {
                gameView.player!!.isGroupLeader = true
            }
            gameView.player!!.group[key] = player
        }

        return false
    }

    private fun removeFromGroup(key: String)    {
        if (gameView.player!!.isGroupLeader) {
            gameView.player!!.group.remove(key)
        }
        if (gameView.player!!.group.size < 1)   {
            gameView.player!!.isGroupLeader = false
        }
    }

    private fun isInRect(x: Float, y: Float, rect: Rect): Boolean   {
        val l = rect.left.toFloat()
        val r = rect.right.toFloat()
        val b = rect.bottom.toFloat()
        val t = rect.top.toFloat()


        if ((l < x) and (x < r))    {
            if ((b > y) and (y > t))    {
                return true
            }
        }
        return false
    }
}