package com.example.intotheabyss.networking

import android.util.Log
import com.esotericsoftware.kryonet.Client
import com.esotericsoftware.kryonet.Connection
import com.esotericsoftware.kryonet.Listener
import com.example.intotheabyss.dungeonassets.Floor
import com.esotericsoftware.minlog.Log as kryolog
import com.example.intotheabyss.game.GameState
import com.example.intotheabyss.dungeonassets.Tile
import com.example.intotheabyss.dungeonassets.Wall
import java.io.IOException

import com.example.intotheabyss.networking.packets.ConnectionPackage
import com.example.intotheabyss.networking.packets.MapPacket
import com.example.intotheabyss.networking.packets.MapRequestPacket
import com.example.intotheabyss.networking.packets.PlayerLocationPacket
import com.example.intotheabyss.utils.TileTypes

class Network(private var gameState: GameState): Listener() {
    private var client: Client = Client()
    private val ip: String = "http://cs309-ad-4.misc.iastate.edu:8080"
    private val tcpPort: Int = 44444
    private val udpPort: Int = 44445

    fun connect() {
        // For logging if need be
        kryolog.TRACE()
        client = Client(16384, 65536)


        client.kryo.apply {
//            register(ConnectionPackage::class.java, object: Serializer<ConnectionPackage>() {
//                override fun write(kryo: Kryo, output: Output, component: ConnectionPackage) {
//                    kryo.writeObject(output, component.text)
//                }
//
//                override fun read(kryo: Kryo, input: Input, type: Class<ConnectionPackage>): ConnectionPackage {
//                    return ConnectionPackage(
//                        kryo.readObject(input, String::class.java)
//                    )
//                }
//            })
//
//            register(MapRequestPacket::class.java, object: Serializer<MapRequestPacket>() {
//                override fun write(kryo: Kryo, output: Output, component: MapRequestPacket) {
//                    kryo.writeObject(output, component.floorNum)
//                }
//
//                override fun read(kryo: Kryo, input: Input, type: Class<MapRequestPacket>): MapRequestPacket {
//                    return MapRequestPacket(
//                        kryo.readObject(input, Int::class.java)
//                    )
//                }
//            })
//
//            register(MapPacket::class.java, object: Serializer<MapPacket>() {
//                override fun write(kryo: Kryo, output: Output, component: MapPacket) {
//                    kryo.writeObject(output, component.levelGrid)
//                }
//
//                override fun read(kryo: Kryo, input: Input, type: Class<MapPacket>): MapPacket {
//                    return MapPacket(
//                        kryo.readObject(input, Array<Array<Tile>>::class.java)
//                    )
//                }
//            })

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
            client.sendTCP(ConnectionPackage("Client says hello!"))

        } catch (e: IOException) {
            e.printStackTrace()
        }
    }

    override fun received(c: Connection, o: Any) {
        if (o is ConnectionPackage) {
            Log.i("Networking", o.text)
            val connectionResponse = ConnectionPackage("Client says hello!")
            client.sendTCP(connectionResponse)
        }
        // This will be where we verify the objects that have been sent over the connection
        // Will verify the instance of each object and then call functions based on the object type
    }

    fun updatePosition(playerID: String, floor: Int, posX: Int, posY: Int) {
        val positionPacket = PlayerLocationPacket(playerID, floor, posX, posY)
        client.sendTCP(positionPacket)
    }
}
