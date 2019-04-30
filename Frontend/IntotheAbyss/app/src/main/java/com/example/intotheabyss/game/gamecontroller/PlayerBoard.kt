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
import com.example.intotheabyss.game.event.KickEvent
import com.example.intotheabyss.game.event.RemoveEvent
import com.example.intotheabyss.game.event.RequestEvent

private var playerTextPaint = Paint()
private var playerBoardPaint = Paint()
private var friendPaint = Paint()
private var angryPaint = Paint()

private var rectArray = ArrayList<Rect>()
private var keyArray = ArrayList<String>()
private var groupRectArray = ArrayList<Rect>()
private var groupKeyArray = ArrayList<String>()

private var removed = false
private var lastX = 0f
private var lastY = 0f

private var leaveGroupRect = Rect(0,0,0,0)

private var scrollOffset = 0
private var scrollUpRect = Rect(0,0,0,0)
private var scrollDownRect = Rect(0,0,0,0)

private var kickDelay: Long = 50
private var lastKick: Long = 0
private var thisKick: Long = 100

class PlayerBoard(private val gameView: GameView,
                  private val bSize: Float) : PlayerBoardInterface    {

    init {
        playerTextPaint.color = Color.BLACK
        playerTextPaint.textSize = 70f

        playerBoardPaint.color = Color.WHITE
        playerBoardPaint.alpha = 90
        playerBoardPaint.textSize = 35f

        friendPaint.color = Color.GREEN
        friendPaint.alpha = 90

        angryPaint.color = Color.RED
        angryPaint.alpha = 90

        leaveGroupRect = Rect((gameView.sWidth - 25 - 2*bSize).toInt(), (gameView.sHeight - 10 - bSize).toInt(),
            gameView.sWidth - 25, gameView.sHeight - 10)

        scrollUpRect = Rect(25+5*bSize.toInt(), 25, 25+6*bSize.toInt(), 165)
        scrollDownRect = Rect(25+5*bSize.toInt(), 180, 25+6*bSize.toInt(), 320)
    }

    override fun drawPlayerBoard(canvas: Canvas, entityList: HashMap<String, Entity>) {
        rectArray.clear()
        groupRectArray.clear()
        keyArray.clear()
        groupKeyArray.clear()

        val playerList: HashMap<String, Player> = hashMapOf()

        drawScroll(canvas)
        for (entity in entityList.values) {
            if (entity.type == EntityType.PLAYER) {
                playerList[entity.ID] = entity as Player
            }
        }

        var i = 0 - scrollOffset
        for (p in playerList.keys) {
            if (i >= 0) {
                val player = playerList[p]
                playerList(player!!, canvas, i)

                keyArray.add(p)
            }
            i++
        }

        i = 0 - scrollOffset
        for (g in gameView.player!!.party)  {
            if (i >= 0) {
                groupList(g, canvas, i, g.ID)
            }
            i++
        }

        if (gameView.player!!.party.size > 0)   {
            canvas.drawRect(leaveGroupRect, angryPaint)
            canvas.drawText("Leave Group", leaveGroupRect.left.toFloat(), leaveGroupRect.centerY().toFloat(), playerBoardPaint)
        }
    }

    private fun playerList(player: Player, canvas: Canvas, i: Int)  {
        val top = ((2+i)*bSize).toInt()
        val bot = (3+i)*bSize.toInt()
        val rect = Rect(25, top, 25 + 5*bSize.toInt(), bot)
        canvas.drawRect(rect, playerBoardPaint)
        canvas.drawText(player.playerName, 25f, rect.exactCenterY(), playerTextPaint)

        if (gameView.player!!.role == Role.ADMIN)   {
            val rem = Rect(rect.right, top, rect.right + bSize.toInt(), bot)
            canvas.drawRect(rem, angryPaint)
        }

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
        checkScrollButtons(x, y)
//        println("Scroll offset: $scrollOffset")
        val playerList: HashMap<String, Player> = hashMapOf()

        for (entities in entityList.values) {
            if (entities.type == EntityType.PLAYER) {
                playerList[entities.ID] = entities as Player
            }
        }

        if (checkIfInputMoved(x, y)) {
//            println("Input moved")
            for (r in rectArray)    {
                val i = rectArray.indexOf(r)
                var s = ""
                if (keyArray.size > i) {
                    s = keyArray[i]
                }   else    break

                if (isInRect(x, y, r)) {
//                    println("In rect, add player")
                    var newMember = playerList[s]!!
                    addToGroup(newMember)
                    lastX = x
                    lastY =y
                }
                if (gameView.player!!.role == Role.ADMIN) {
                    val rem = Rect(r.right, r.top, r.right + bSize.toInt(), r.bottom)
                    if (isInRect(x, y, rem))    {
                        kickPlayer(s)
                        lastY = y
                        lastX = x
                    }
                }
            }
        }
        removed = false
    }

    override fun getPlayerGroupAction(x: Float, y: Float) {
        for (r in groupRectArray)   {
            if (checkIfInputMoved(x, y))  {
                if (isInRect(x, y, r)) {
                    if (thisKick - lastKick > kickDelay) {
                        lastKick = thisKick
                        thisKick = System.currentTimeMillis()
                        removeFromGroup(groupKeyArray[groupRectArray.indexOf(r)])
                        lastY = y
                        lastX = x
                    }
                }
            }
        }

        if ((lastX != x) and (lastY != y) and (isInRect(x, y, leaveGroupRect))) {
            leaveGroup()
        }
    }


    /**
     * Method to add a player to a group
     *
     * @param player Player being added
     */
    private fun addToGroup(player: Player)    {
        if (player.party.isEmpty())  {
            if (!checkForID(player.ID, gameView.player!!.party)) {

                //Code required to add a new player to the group after all checks performed on my end
                gameView.player!!.party.add(player)
                val reqEvent = RequestEvent(gameView.player!!.ID, player.ID)
                gameView.gameState!!.eventQueue.add(reqEvent)

                if ((gameView.player!!.party.size == 1) and (gameView.player!!.role != Role.ADMIN))   {
                    gameView.player!!.role = Role.GROUP_LEADER
                }
            }
        }
    }

    private fun removeFromGroup(key: String)    {
        val myPlayer = gameView.player!!
        if (myPlayer.role == Role.GROUP_LEADER || myPlayer.role == Role.ADMIN) {
            for (p in myPlayer.party)  {
                if (key.equals(p.ID))   {

                    gameView.player!!.party.remove(p)
                    val kickEvent = KickEvent(myPlayer.ID, key)
                    gameView.gameState!!.eventQueue.add(kickEvent)

                    val k = keyArray.indexOf(key)
                    groupRectArray.remove(groupRectArray[k])
                    keyArray.remove(key)
                }
            }
        }
        if ((gameView.player!!.party.isEmpty()) and (gameView.player!!.role != Role.ADMIN))   {
            gameView.player!!.role = Role.PLAYER
        }
    }

    private fun kickPlayer(key: String) {
        val kickEvent = KickEvent(gameView.player!!.ID, key)
        gameView.gameState!!.eventQueue.add(kickEvent)

        for (p in gameView.gameState!!.entitiesInLevel) {
            if (p.key == key)   {
                println("Removing")
                gameView.gameState!!.entitiesInLevel.remove(p.key)
            }
        }
//        gameView.gameState!!.entitiesInLevel.remove(key)
    }

    private fun leaveGroup()    {
        val s = gameView.player!!.ID
        val rem = RemoveEvent(s)
        gameView.gameState!!.eventQueue.add(rem)
        gameView.player!!.party.clear()
    }

    /**
     * Method to check if the action happened inside a specific rect
     *
     * @param x x-coordinate of input
     * @param y y-coordinate of input
     * @param rect Rect we're checking
     */
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

    /**
     * Drawing the scroll buttons
     *
     * @param canvas The canvas we're drawing on
     */
    private fun drawScroll(canvas: Canvas)  {
        canvas.drawRect(scrollUpRect, playerBoardPaint)
        canvas.drawText("Up", scrollUpRect.left.toFloat(), scrollUpRect.centerY().toFloat(), playerBoardPaint)

        canvas.drawRect(scrollDownRect, playerBoardPaint)
        canvas.drawText("Down", scrollDownRect.left.toFloat(), scrollDownRect.centerY().toFloat(), playerBoardPaint)
    }

    /**
     * Method to check if scroll buttons have been used, updates scroll offset
     */
    private fun checkScrollButtons(x: Float, y: Float)    {
        if (checkIfInputMoved(x, y)) {
            if (isInRect(x, y, scrollUpRect)) {
                scrollOffset++
                lastY = y
                lastX = x
            }

            if (isInRect(x, y, scrollDownRect)) {
                scrollOffset--
                lastY = y
                lastX = x
            }
        }
    }

    /**
     * Method to check if inputs moved. Update last input location
     */
    private fun checkIfInputMoved(x: Float, y: Float): Boolean  {
        if ((lastX != x) or (lastY != y))   {
            return true
        }
        return false
    }

    /**
     * Function to check if playerID is in the playerList given
     *
     * @param s ID of the player we are checking for
     * @param playerList PlayerList that we are checking inside of
     */
    private fun checkForID(s: String, pList: MutableList<Player>): Boolean  {
        if (pList.isNotEmpty()) {
            for (i in 0..pList.size-1) {
                if (s.equals(pList[i].ID)) {
                    return true
                }
            }
        }
        return false
    }
}