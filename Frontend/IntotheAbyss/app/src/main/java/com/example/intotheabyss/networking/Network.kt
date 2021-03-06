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
import com.example.intotheabyss.game.entity.EntityType
import com.example.intotheabyss.game.entity.entityaction.*
import com.example.intotheabyss.game.entity.monster.Monster
import com.example.intotheabyss.networking.packets.*
import com.example.intotheabyss.game.entity.player.Player
import com.example.intotheabyss.game.entity.player.Role
import com.example.intotheabyss.game.event.*
import java.io.IOException

import com.example.intotheabyss.utils.TileTypes
import com.google.gson.*
import org.json.JSONArray
import org.json.JSONObject
import java.util.*

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
    private val ip: String = "cs309-ad-4.misc.iastate.edu"
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

            register(DisconnectPacket::class.java)
            register(Player::class.java)
            register(Hashtable::class.java)
        }


        //Add the class registration when we get to this part
        client.start()
        client.addListener(this)
        try {
            // Attempt to connect within a 5000 ms window before timing out
            client.connect(5000, ip, tcpPort, udpPort)
            Log.d("Networking", "Sending Player ID")
            Log.i("Networking", gameState.myPlayer.ID)
            if (gameState.myPlayer.ID == null || gameState.myPlayer.ID == "") {
                Log.d("Networking", gameState.myPlayer.playerName)
                Log.d("Networking", "Player ID is null or empty")
            }
            var connection = ConnectionPackage(gameState.myPlayer.ID)
            Log.d("Networking", connection.userID)
            client.sendTCP(connection)

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
            Log.i("Networking", o.userID)
            val connectionResponse = ConnectionPackage(gameState.myPlayer.ID)
            client.sendTCP(connectionResponse)
        }
        if (o is PlayerPacket) {
            var thisPlayer = Player(o.playerID, o.playerName, o.health, o.floorNum, o.posX, o.posY)
            gameState.myPlayer = thisPlayer
        }
        if (o is EntityAction) {
            var action = EntityAction(o.performerID, o.actionType, o.floor, o.payload)
            Log.i("EntityAction", o.actionType.toString())

            // Make sure we only process events based on our floor
//            if (action.floor == gameState.myPlayer.floor) {

                when(action.actionType){
                    EntityActionType.ADD -> handleAddAction(action)

                    EntityActionType.MOVE -> handleMoveAction(action)

                    EntityActionType.ATTACK -> handleAttackAction(action)

                    EntityActionType.JOIN -> handleJoinAction(action)

                    EntityActionType.KICK -> handleKickAction(action)

                    EntityActionType.REMOVE -> handleRemoveAction(action)

                    else -> Log.i("EntityAction", "Unknown EntityActionType" + o.actionType)
                }
//            }
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

        // Edge case for floor 0
        val prevFloor = if (oldFloor < 0) 0 else oldFloor

        val movement = Move(posX, posY, floor)
        val jsonPacket = gson.toJson(movement)
        val positionPacket = EntityAction(playerID, EntityActionType.MOVE, prevFloor, jsonPacket)
        Log.i("Movement", positionPacket.floor.toString())
        Log.i("Movement", jsonPacket.toString())

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

    fun requestPlayer(request: RequestEvent) {
        val requestPacket = EntityAction(request.performerID, EntityActionType.REQUEST,
            gameState.myPlayer.floor, request.performedID)
        client.sendTCP(requestPacket)
    }

    fun kickPlayer(kick: KickEvent) {
        val kickPacket = EntityAction(kick.performerID, EntityActionType.KICK, gameState.myPlayer.floor,
            kick.performedID)
        client.sendTCP(kickPacket)
    }

    fun death(death: DeathEvent) {
        val deathPacket = EntityAction(death.performerID, EntityActionType.DEATH, gameState.myPlayer.floor,
            death.performerID)
        val disconnectPacket = DisconnectPacket(gameState.myPlayer.ID, gameState.myPlayer.floor)
        client.sendTCP(disconnectPacket)
        client.sendTCP(deathPacket)
        client.stop()
        client.close()
    }

    fun disconnect() {
        val disconnectPacket = DisconnectPacket(gameState.myPlayer.ID, gameState.myPlayer.floor)
        client.sendTCP(disconnectPacket)
        client.stop()
        client.close()
    }

    /**
     * Handles the ADD action of an EntityAction.
     *
     * @param action The EntityAction packet that was sent over Kryonet.
     */
    private fun handleAddAction(action: EntityAction) {
        var gsonBuilder = GsonBuilder()
        gsonBuilder.setLongSerializationPolicy(LongSerializationPolicy.STRING)
        var gson = gsonBuilder.create()
        var json = JSONObject(action.payload)
        Log.i("AddAction", json.toString())

        if (gameState.myPlayer.ID != action.performerID) {
                var newPlayer = Player()
                newPlayer.x = json.getInt("posX")
                newPlayer.y = json.getInt("posY")
                newPlayer.playerName = json.getString("username")
                newPlayer.floor = action.floor
                Log.i("ADD", json.getInt("ID").toString())
                newPlayer.ID = action.performerID

                Log.i("ADD", newPlayer.ID)

                gameState.entitiesInLevel[newPlayer.ID] = newPlayer

                Log.i("AddAction", gameState.entitiesInLevel.isNotEmpty().toString())

                Log.i("ADD", gameState.entitiesInLevel[newPlayer.ID].toString())
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
//        if (moveAction.floorMovedTo == gameState.myPlayer.floor) {
            Log.i("MOVE", entityUnderMovement.toString())
            entityUnderMovement!!.x = moveAction.posX
            entityUnderMovement.y = moveAction.posY
            entityUnderMovement.floor = moveAction.floorMovedTo

            gameState.entitiesInLevel[action.performerID] = entityUnderMovement
//        } else {
//            gameState.entitiesInLevel.remove(action.performerID)
//        }
    }

    /**
     * Handles the attack action logic for the entities on the floor.
     *
     * @param action The EntityActionPacket received from Kryonet
     */
    private fun handleAttackAction(action: EntityAction) {
        var gsonBuilder = GsonBuilder()
        gsonBuilder.setLongSerializationPolicy(LongSerializationPolicy.STRING)
        var gson = gsonBuilder.create()
        var attackAction = gson.fromJson<Attack>(action.payload, Attack::class.java)
        // var attackAction = gson.fromJson<Attack>(json.toString(), Attack::class.java)

        var entityAttacked: Player
        if (gameState.myPlayer.ID == attackAction.attackedID) {
            entityAttacked = gameState.myPlayer
        } else {
            entityAttacked = gameState.entitiesInLevel[attackAction.attackedID]!! as Player
        }

        entityAttacked.health -= attackAction.dmg

        var attackEvent = AttackEvent(entityAttacked.ID, action.performerID, attackAction.dmg)
        gameState.eventQueueDisplay.add(attackEvent)

        // Remove the entity from the level if their health is less than 0
        if(entityAttacked.health <= 0) {
            if (entityAttacked.ID == gameState.myPlayer.ID) {
                death(DeathEvent(gameState.myPlayer.ID))
            }else if (entityAttacked.type == EntityType.PLAYER) {
                var playerAttacked = entityAttacked as Player

                if (playerAttacked.role == Role.GROUP_LEADER) {
                    for (player in playerAttacked.party) {
                        player.party.removeAll(playerAttacked.party)
                    }

                    playerAttacked.party.removeAll(playerAttacked.party)
                } else {
                    for (player in playerAttacked.party) {
                        player.party.remove(playerAttacked)
                    }
                }
            }
            gameState.entitiesInLevel.remove(entityAttacked.ID)
        } else {
            if (entityAttacked.ID == gameState.myPlayer.ID) {
                gameState.myPlayer = entityAttacked
            } else {
                gameState.entitiesInLevel[attackAction.attackedID] = entityAttacked
            }
        }
    }

    private fun handleJoinAction(action: EntityAction) {
        //var json =JSONObject(action.payload)
        // var gsonBuilder = GsonBuilder()
        // gsonBuilder.setLongSerializationPolicy(LongSerializationPolicy.STRING)
        // var gson = gsonBuilder.create()
        // var gson = Gson()
        // var joinAction: Join = gson.fromJson<Join>(action.payload, Join::class.java)
        var joinedPlayer: Player = if (gameState.myPlayer.ID == action.payload) {
            gameState.myPlayer
        } else {
            gameState.entitiesInLevel[action.payload] as Player
        }

        when {
            gameState.myPlayer.ID == action.performerID -> {
                gameState.myPlayer.role = Role.GROUP_LEADER

                joinedPlayer.party = gameState.myPlayer.party

                for (player in gameState.myPlayer.party) {
                    player.party.add(joinedPlayer)
                }

                gameState.myPlayer.party.add(joinedPlayer)
            }

            gameState.myPlayer.party.contains(gameState.entitiesInLevel[action.performerID] as Player) -> {
                joinedPlayer.party = gameState.myPlayer.party

                for (player in gameState.myPlayer.party) {
                    player.party.add(joinedPlayer)
                }

                gameState.myPlayer.party.add(joinedPlayer)
            }

            gameState.myPlayer.ID == action.payload -> {
                var leaderPlayer = gameState.entitiesInLevel[action.performerID] as Player

                for (player in leaderPlayer.party) {
                    player.party.add(gameState.myPlayer)
                }

                leaderPlayer.role = Role.GROUP_LEADER
                gameState.myPlayer.party = leaderPlayer.party
                gameState.myPlayer.party.add(leaderPlayer)
                if (!leaderPlayer.party.contains(gameState.myPlayer)) {
                    leaderPlayer.party.add(gameState.myPlayer)
                }
            }

            else -> {
                var leaderPlayer = gameState.entitiesInLevel[action.performerID] as Player
                for (players in leaderPlayer.party) {
                    players.party.add(joinedPlayer)
                }
                joinedPlayer.party = leaderPlayer.party
                leaderPlayer.party.add(joinedPlayer)
            }
        }
    }

    private fun handleKickAction(action: EntityAction) {
        //var json = JSONObject(action.payload)
        // var gson = Gson()
        // var kickAction = gson.fromJson<Kick>(action.payload, Kick::class.java)

        var kickingPlayer = if (action.performerID == gameState.myPlayer.ID) {
            gameState.myPlayer
        } else {
            gameState.entitiesInLevel[action.performerID] as Player
        }

        if (kickingPlayer.role == Role.GROUP_LEADER && gameState.myPlayer.ID == action.payload) {
            gameState.myPlayer.role = Role.PLAYER
            kickingPlayer.party.remove(gameState.myPlayer)

            for (player in kickingPlayer.party) {
                player.party.remove(gameState.myPlayer)
            }

            gameState.myPlayer.party.removeAll(gameState.myPlayer.party)
        } else if (kickingPlayer.role == Role.GROUP_LEADER && gameState.myPlayer.ID == kickingPlayer.ID) {
            val kickedPlayer = gameState.entitiesInLevel[action.payload] as Player

            gameState.myPlayer.party.remove(kickedPlayer)

            for (player in gameState.myPlayer.party) {
                player.party.remove(kickedPlayer)
            }

            kickedPlayer.party.removeAll(kickedPlayer.party)
        } else if (kickingPlayer.role == Role.GROUP_LEADER) {
            val kickedPlayer = gameState.entitiesInLevel[action.payload] as Player

            kickingPlayer.party.remove(kickedPlayer)

            for (player in kickingPlayer.party) {
                player.party.remove(kickedPlayer)
            }

            kickedPlayer.party.removeAll(kickingPlayer.party)
        } else {
            val kickedPlayer = gameState.entitiesInLevel[action.payload] as Player

            for (player in kickedPlayer.party) {
                player.party.remove(kickedPlayer)
            }

            gameState.entitiesInLevel.remove(action.payload)
        }
    }

    private fun handleRemoveAction(action: EntityAction) {
        if (gameState.myPlayer.ID == action.performerID) {
            gameState.eventQueueDisplay.add(DisconnectEvent())
        } else if (gameState.myPlayer.party.contains(gameState.entitiesInLevel[action.performerID] as Player)) {
            gameState.myPlayer.party.remove(gameState.entitiesInLevel[action.performerID] as Player)
        }

        gameState.entitiesInLevel.remove(action.performerID)
    }
}
