package com.example.intotheabyss.game.gamecontroller

import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Rect
import android.util.Log
import android.view.MotionEvent
import com.example.intotheabyss.GameEvent.Add
import com.example.intotheabyss.GameEvent.Remove
import com.example.intotheabyss.game.GameView
import com.example.intotheabyss.game.drawplayer.gameView
import com.example.intotheabyss.game.player.Player

private var playerTextPaint = Paint()
private var playerBoardPaint = Paint()
private var friendPaint = Paint()
private var angryPaint = Paint()    //All the paint used for angry things like kicking people and leaving groups

private var rectArray = ArrayList<Rect>()
private var keyArray = ArrayList<String>()
private var groupRectArray = ArrayList<Rect>()
private var groupKeyArray = ArrayList<String>()

private var removed = false
private var lastX = 0f
private var lastY = 0f

private var leaveGroupRect: Rect? = null

class PlayerBoard(private val gameView: GameView,
                  private val bSize: Float) : PlayerBoardInterface    {

    init {
        playerTextPaint.color = Color.BLACK
        playerTextPaint.textSize = 70f

        playerBoardPaint.color = Color.WHITE
        playerBoardPaint.alpha = 90

        friendPaint.color = Color.GREEN
        friendPaint.alpha = 90

        angryPaint.color = Color.RED
        angryPaint.alpha = 90

        leaveGroupRect = Rect((gameView.sWidth - 25 - 2*bSize).toInt(), (gameView.sHeight - 10 - bSize).toInt(),
            gameView.sWidth - 25, gameView.sHeight - 10)
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

        if (gameView.player!!.group.size > 0)   {
            canvas.drawRect(leaveGroupRect, angryPaint)
            canvas.drawText("Leave Group", leaveGroupRect!!.left.toFloat(), leaveGroupRect!!.centerY().toFloat(), playerBoardPaint)
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
                if (checkLeaveGroup(x, y))  {
                    leaveGroup()
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
            val mP = gameView.player!!
            val add = Add(mP, player)
            gameView.gameState!!.eventQueue.add(add)
            gameView.player!!.group[key] = player
        }

        return false
    }

    private fun removeFromGroup(key: String)    {
        if (gameView.player!!.isGroupLeader) {
            val rem = Remove(gameView.player!!.group[key]!!)
            gameView.gameState!!.eventQueue.add(rem)
            gameView.player!!.group.remove(key)
        }
        if (gameView.player!!.group.size < 1)   {
            gameView.player!!.isGroupLeader = false
        }
    }

    private fun checkLeaveGroup(x: Float, y: Float): Boolean    {
        return isInRect(x, y, leaveGroupRect!!)
    }

    private fun leaveGroup()    {
        val rem = Remove(gameView.player!!)
        gameView.gameState!!.eventQueue.add(rem)
        gameView.player!!.group.clear()
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