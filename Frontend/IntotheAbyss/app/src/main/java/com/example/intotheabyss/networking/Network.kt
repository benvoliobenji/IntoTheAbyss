package com.example.intotheabyss.networking

import android.util.Log
import com.esotericsoftware.kryonet.Client
import com.esotericsoftware.kryonet.Connection
import com.esotericsoftware.kryonet.Listener
import com.example.intotheabyss.dungeonassets.Floor
import com.example.intotheabyss.dungeonassets.Stair
import com.esotericsoftware.minlog.Log as kryolog
import com.example.intotheabyss.game.GameState
import com.example.intotheabyss.dungeonassets.Tile
import com.example.intotheabyss.dungeonassets.Wall
import com.example.intotheabyss.game.entity.Entity
import com.example.intotheabyss.game.entity.entityaction.Attack
import com.example.intotheabyss.game.entity.entityaction.EntityAction
import com.example.intotheabyss.game.entity.entityaction.EntityActionType
import com.example.intotheabyss.game.entity.entityaction.Move
import com.example.intotheabyss.game.entity.monster.Monster
import com.example.intotheabyss.networking.packets.*
import com.example.intotheabyss.game.entity.player.Player
import java.io.IOException

import com.example.intotheabyss.utils.TileTypes
import com.google.gson.Gson
import org.json.JSONObject

/**
 * This class is the implementation of the Kryonet library, which is our websocket implementation.
 * The class, on invocation of connect(), creates listeners that grab packets from the server asynchronously and modify
 * GameState to reflect the changes made by the server.
 *
 * This class also sends packets to the server via TCP/UDP connections to the server. This allows the server to have the
 * most up-to-date information from all players in order to make game logic decision and AI determinations.
 *
 * @constructor Builds an instance of Network class. This should only be called once.
 * @param gameState The instance of client's GameState.
 * @author Benjamin Vogel
 */
class Network(private var gameState: GameState): Listener() {
    private var client: Client = Client()
    private val ip: String = "http://cs309-ad-4.misc.iastate.edu:8080"
    private val tcpPort: Int = 44444
    private val udpPort: Int = 44445

    /**
     * Establishes a connection to the server.
     * Upon establishment of connection, spin up listeners for asynchronous data transfer to/from server.
     */
    fun connect() {
        // For logging if need be
        kryolog.TRACE()
        client = Client(16384, 65536)


        client.kryo.apply {
            register(ConnectionPackage::class.java)

            // Register packets needed to transfer the map from server to client
            register(MapRequestPacket::class.java)
            register(MapPacket::class.java)
            register(TileTypes::class.java)
            register(Tile::class.java)
            register(Wall::class.java)
            register(Floor::class.java)
            register(Array<Tile>::class.java)
            register(Array<Array<Tile>>::class.java)
            register(Stair::class.java)

            register(PlayerPacket::class.java)
            register(EntityAction::class.java)
            register(EntityActionType::class.java)
        }


        //Add the class registration when we get to this part
        client.start()
        client.addListener(this)
        try {
            // Attempt to connect within a 5000 ms window before timing out
            client.connect(5000, ip, tcpPort, udpPort)
            Log.d("Networking", "Sending Floor Request")
            client.sendTCP(ConnectionPackage(gameState.myPlayer.ID))

        } catch (e: IOException) {
            e.printStackTrace()
        }
    }


    /**
     * This method is what Kryonet listeners use upon receiving a packet from the server.
     * This method attempts to determine which type the packet it is and then handles the packet based on what type
     * the packet is.
     *
     * @param c The connection between the Client and Server.
     * @param o The packet data that was received from the server.
     */
    override fun received(c: Connection, o: Any) {
        if (o is ConnectionPackage) {
            Log.i("Networking", o.playerID)
            val connectionResponse = ConnectionPackage("Client says hello!")
            client.sendTCP(connectionResponse)
        }
        if (o is PlayerPacket) {
            var thisPlayer = Player(o.playerID, o.playerName, o.health, o.floorNum, o.posX, o.posY)
            gameState.myPlayer = thisPlayer
        }
        if (o is EntityAction) {
            var action = EntityAction(o.performerID, o.actionType, o.floor, o.payload)

            // Make sure we only process events based on our floor
            if (action.floor == gameState.myPlayer.floor) {

                when(action.actionType){
                    EntityActionType.ADD -> handleAddAction(action)

                    EntityActionType.MOVE -> handleMoveAction(action)

                    EntityActionType.ATTACK -> handleAttackAction(action)

                    // What happens if the player is in a group already?
                    // Does the front end support invites yet?
                    // How does the back end handle these events?
                    EntityActionType.JOIN -> {
                        // TODO: ADD LOGIC FOR JOIN
                    }
                    EntityActionType.REQUEST -> {
                        //TODO: ADD LOGIC FOR REQUEST
                    }
                     EntityActionType.KICK -> {
                        // TODO: ADD LOGIC FOR KICK
                    }
                    else -> Log.i("EntityAction", "Unknown EntityActionType" + o.actionType)
                }
            }
        }
        // This will be where we verify the objects that have been sent over the connection
        // Will verify the instance of each object and then call functions based on the object type
    }

    /**
     * Constructs a PlayerLocationPacket and sends it to the server via TCP.
     * @param playerID The ID of the player that has moved.
     * @param oldFloor The floor the player was previously on.
     * @param floor The floor that the player is currently on.
     * @param posX The current x-coordinate of the player.
     * @param posY The current y-coordinate of the player.
     */
    fun updatePosition(playerID: String, oldFloor: Int, floor: Int, posX: Int, posY: Int) {
        val gson = Gson()
        val movement = Move(Pair(posX, posY), floor)
        val jsonPacket = gson.toJson(movement)
        val positionPacket = EntityAction(playerID, EntityActionType.MOVE, oldFloor, jsonPacket)
        client.sendTCP(positionPacket)
    }

    /**
     * Constructs an EntityActionPacket with the payload of an Attack and sends it to the server via TCP.
     * @param attackedID The person attacking.
     * @param attackedID The person being attacked.
     * @param damage The amount of damage dealt.
     */
    fun attackPlayer(attackerID: String, attackedID: String, damage: Int) {
        val gson = Gson()
        val attack = Attack(attackedID, damage)
        val jsonPacket = gson.toJson(attack)
        val attackPacket = EntityAction(attackerID, EntityActionType.ATTACK, gameState.myPlayer.floor, jsonPacket)
        client.sendTCP(attackPacket)
    }

    /**
     * Handles the ADD action of an EntityAction.
     *
     * @param action The EntityAction packet that was sent over Kryonet.
     */
    private fun handleAddAction(action: EntityAction) {
        var json = JSONObject(action.payload)
        if (action.performerID == gameState.myPlayer.ID) {
            gameState.myPlayer.ID = json.getString("playerID")
            gameState.myPlayer.playerName = json.getString("username")
            gameState.myPlayer.x = json.getInt("posX")
            gameState.myPlayer.y = json.getInt("posY")
            gameState.myPlayer.floor = json.getInt("floor")
            gameState.myPlayer.health = json.getInt("health")
        } else {
            // Regardless if it's in the hash map or not, it will be modified or added the same way
            var gson = Gson()

            // If it has a username, then it is a Player, else it is a Monster
            if (json.optString("username") != "") {
                var newPlayer = gson.fromJson<Player>(json.toString(), Player::class.java)
                gameState.entitiesInLevel[newPlayer.ID] = newPlayer
            } else {
                var newMonster = gson.fromJson<Monster>(json.toString(), Monster::class.java)
                gameState.entitiesInLevel[newMonster.ID] = newMonster
            }
        }
    }

    /**
     * Handles the movement logic for the entities on the floor.
     *
     * @param action The EntityActionPacket received from Kryonet
     */
    private fun handleMoveAction(action: EntityAction) {
        var json = JSONObject(action.payload)
        var gson = Gson()
        var entityUnderMovement = gameState.entitiesInLevel[action.performerID]
        var moveAction = gson.fromJson<Move>(json.toString(), Move::class.java)

        // Verify that the entity is still on this floor
        if (moveAction.floorMovedTo == gameState.myPlayer.floor) {
            entityUnderMovement!!.x = moveAction.location.first
            entityUnderMovement.y = moveAction.location.second
            entityUnderMovement.floor = moveAction.floorMovedTo

            gameState.entitiesInLevel[action.performerID] = entityUnderMovement
        } else {
            gameState.entitiesInLevel.remove(action.performerID)
        }
    }

    /**
     * Handles the attack action logic for the entities on the floor.
     *
     * @param action The EntityActionPacket recieved from Kryonet
     */
    private fun handleAttackAction(action: EntityAction) {
        var json = JSONObject(action.payload)
        var gson = Gson()
        var attackAction = gson.fromJson<Attack>(json.toString(), Attack::class.java)
        var entityAttacked = gameState.entitiesInLevel[attackAction.attackID]
        entityAttacked!!.health -= attackAction.dmg

        // Remove the entity from the level if their health is less than 0
        if(entityAttacked!!.health <= 0) {
            gameState.entitiesInLevel.remove(entityAttacked.ID)
        }

        gameState.entitiesInLevel[attackAction.attackID] = entityAttacked
    }
}
