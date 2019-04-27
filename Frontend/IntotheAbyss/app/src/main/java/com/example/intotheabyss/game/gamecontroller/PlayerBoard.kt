package com.example.intotheabyss.game.gamecontroller

import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Rect
import android.view.MotionEvent
import com.example.intotheabyss.game.GameView
import com.example.intotheabyss.game.entity.Entity
import com.example.intotheabyss.game.entity.EntityType
import com.example.intotheabyss.game.entity.player.Player
import com.example.intotheabyss.game.entity.player.Role

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

    override fun drawPlayerBoard(canvas: Canvas, entityList: HashMap<String, Entity>) {
        rectArray.clear()
        groupRectArray.clear()
        keyArray.clear()
        groupKeyArray.clear()

        var playerList: HashMap<String, Player> = hashMapOf()

        for (entity in entityList.values) {
            if (entity.type == EntityType.PLAYER) {
                playerList[entity.ID] = entity as Player
            }
        }

        var i = 0
        for (p in playerList.keys) {
            val player = playerList[p]
            playerList(player!!, canvas, i)

            keyArray.add(p)
            i++
        }

        i = 0
        for (g in gameView.player!!.party)  {
            groupList(g, canvas, i, g.ID)
            i++
        }
    }

    private fun playerList(player: Player, canvas: Canvas, i: Int)  {
        val rect = Rect(25, ((2+i)*bSize).toInt(), 25 + 5*bSize.toInt(), (3+i)*bSize.toInt())
        canvas.drawRect(rect, playerBoardPaint)
        canvas.drawText(player.playerName, 25f, rect.exactCenterY(), playerTextPaint)

        rectArray.add(rect)
    }

    private fun groupList(player: Player, canvas: Canvas, i: Int, p: String)   {
        val right = gameView.sWidth
        val offset = right - 25 - 5*bSize.toInt()
        val rect = Rect(offset, ((2+i)*bSize).toInt(), right - 25, (3+i)*bSize.toInt())
        canvas.drawRect(rect, friendPaint)
        canvas.drawText(player.playerName, offset.toFloat(), rect.exactCenterY(), playerTextPaint)

        groupRectArray.add(rect)
        groupKeyArray.add(p)
    }

    override fun getPlayerBoardAction(entityList: HashMap<String, Entity>, x: Float, y: Float, action: MotionEvent) {
        var playerList: HashMap<String, Player> = hashMapOf()

        for (entities in entityList.values) {
            if (entities.type == EntityType.PLAYER) {
                playerList[entities.ID] = entities as Player
            }
        }

        for (r in rectArray)    {
            if ((lastX != x) and (lastY != y)) {
                if ((isInRect(x, y, r)) and (!removed)) {
                    val i = rectArray.indexOf(r)
                    val s = keyArray[i]

                    var newMember = playerList[s]!!
                   // if (newMember.)

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
        if (player.party.isEmpty())  {
            if (gameView.player!!.party.isEmpty() and (gameView.player!!.role != Role.ADMIN))   {
                gameView.player!!.role = Role.GROUP_LEADER
            }
            gameView.player!!.party.add(player)
        }

        return false
    }

    private fun removeFromGroup(key: String)    {
        if (gameView.player!!.role == Role.GROUP_LEADER) {
            gameView.player!!.party.remove(gameView.gameState!!.entitiesInLevel[key])
        }
        if ((gameView.player!!.party.isEmpty()) and (gameView.player!!.role != Role.ADMIN))   {
            gameView.player!!.role = Role.PLAYER
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