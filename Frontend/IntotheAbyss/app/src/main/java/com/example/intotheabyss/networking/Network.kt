package com.example.intotheabyss.networking

import android.util.Log
import com.esotericsoftware.kryo.Kryo
import com.esotericsoftware.kryo.Serializer
import com.esotericsoftware.kryo.io.Input
import com.esotericsoftware.kryo.io.Output
import com.esotericsoftware.kryonet.Client
import com.esotericsoftware.kryonet.Connection
import com.esotericsoftware.kryonet.Listener
import com.example.intotheabyss.Game.GameState
import com.example.intotheabyss.dungeonassets.Tile
import java.io.IOException

import com.example.intotheabyss.networking.packets.ConnectionPackage
import com.example.intotheabyss.networking.packets.MapPacket
import com.example.intotheabyss.networking.packets.MapRequestPacket

class Network(private var gameState: GameState): Listener() {
    private var client: Client = Client()
    private val ip: String = "10.26.14.167"
    private val tcpPort: Int = 44444
    private val udpPort: Int = 44445

    fun connect() {
        client = Client()

        // Because the packets on our end are Kotlin and the server is Java, there needs to be some translation
        client.kryo.apply {
            register(ConnectionPackage::class.java, object: Serializer<ConnectionPackage>() {
                override fun write(kryo: Kryo, output: Output, component: ConnectionPackage) {
                    kryo.writeObject(output, component.text)
                }

                override fun read(kryo: Kryo, input: Input, type: Class<ConnectionPackage>): ConnectionPackage {
                    return ConnectionPackage(
                        kryo.readObject(input, String::class.java)
                    )
                }
            })

            register(MapPacket::class.java, object: Serializer<MapPacket>() {
                override fun write(kryo: Kryo, output: Output, component: MapPacket) {
                    kryo.writeObject(output, component.levelGrid)
                }

                override fun read(kryo: Kryo, input: Input, type: Class<MapPacket>): MapPacket {
                    return MapPacket(
                        kryo.readObject(input, Array<Array<Tile>>::class.java)
                    )
                }
            })

            register(MapRequestPacket::class.java, object: Serializer<MapRequestPacket>() {
                override fun write(kryo: Kryo, output: Output, component: MapRequestPacket) {
                    kryo.writeObject(output, component.floorNum)
                }

                override fun read(kryo: Kryo, input: Input, type: Class<MapRequestPacket>): MapRequestPacket {
                    return MapRequestPacket(
                    kryo.readObject(input, Int::class.java)
                    )
                }
            })
        }

        //Add the class registration when we get to this part
        client.start()
        client.addListener(this)
        try{
            // Attempt to connect within a 5000 ms window before timing out
            client.connect(5000, ip, tcpPort, udpPort)
            Log.d("Networking","Sending Floor Request")
            // client.sendTCP(MapRequestPacket(gameState.myPlayer.floorNumber))
            client.sendTCP(MapRequestPacket(1))
        } catch (e: IOException) {
            e.printStackTrace()
        }
    }

    override fun received(c: Connection, o: Any) {
        if (o is ConnectionPackage) {
            Log.i("Networking", o.text)
            val connectionResponse = ConnectionPackage("Client says hello!")
            this.client.sendTCP(connectionResponse)
        }
        if (o is MapPacket) {
            Log.d("Receiving", o.toString())
            // Display new map to user
        }
        // This will be where we verify the objects that have been sent over the connection
        // Will verify the instance of each object and then call functions based on the object type
    }
}
