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
import com.example.intotheabyss.networking.packets.*
import com.example.intotheabyss.game.player.Player
import java.io.IOException

import com.example.intotheabyss.utils.TileTypes

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
            register(MoveFloorPacket::class.java)

            // Player location registration
            register(PlayerLocationPacket::class.java)
        }


        //Add the class registration when we get to this part
        client.start()
        client.addListener(this)
        try{
            // Attempt to connect within a 5000 ms window before timing out
            client.connect(5000, ip, tcpPort, udpPort)
            Log.d("Networking","Sending Floor Request")
            client.sendTCP(ConnectionPackage(gameState.myPlayer.playerID))

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
        // This will be where we verify the objects that have been sent over the connection
        // Will verify the instance of each object and then call functions based on the object type
    }

    /**
     * Constructs a PlayerLocationPacket and sends it to the server via TCP.
     * @param playerID The ID of the player that has moved.
     * @param floor The floor that the player is currently on.
     * @param posX The current x-coordinate of the player.
     * @param posY The current y-coordinate of the player.
     */
    fun updatePosition(playerID: String, floor: Int, posX: Int, posY: Int) {
        val positionPacket = PlayerLocationPacket(playerID, floor, posX, posY)
        client.sendTCP(positionPacket)
    }

    /**
     * Constructs a MoveFloorPacket and sends it to the server via TCP.
     * This is done to notify the server that the player has moved to a new floor.
     * This is called after VolleyNetwork gets and builds a new floor.
     * @param playerID The ID of the player that has moved floors.
     * @param floor The floor that the player has moved to.
     */
    fun updateLevel(playerID: String, floor: Int) {
        val floorPacket = MoveFloorPacket(playerID, floor)
        client.sendTCP(floorPacket)
    }
}
