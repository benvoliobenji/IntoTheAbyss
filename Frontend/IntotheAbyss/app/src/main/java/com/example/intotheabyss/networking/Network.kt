package com.example.intotheabyss.networking

import android.util.Log
import com.esotericsoftware.kryo.Kryo
import com.esotericsoftware.kryo.Serializer
import com.esotericsoftware.kryo.io.Input
import com.esotericsoftware.kryo.io.Output
import com.esotericsoftware.kryonet.Client
import com.esotericsoftware.kryonet.Connection
import com.esotericsoftware.kryonet.Listener
import java.io.IOException

import com.example.intotheabyss.networking.packets.ConnectionPackage

class Network() : Listener() {
    private var client: Client = Client()
    private val ip: String = "localhost"
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
        }

        //Add the class registration when we get to this part
        client.start()
        client.addListener(this)
        try{
            // Attempt to connect within a 5000 ms window before timing out
            client.connect(5000, ip, tcpPort, udpPort)
        } catch (e: IOException) {
            e.printStackTrace()
        }
    }

    override fun received(c: Connection, o: Any) {
        if(o is ConnectionPackage) {
            Log.i("Networking", o.text)
            val connectionResponse = ConnectionPackage("Client says hello!")
            this.client.sendTCP(connectionResponse)
        }
        // This will be where we verify the objects that have been sent over the connection
        // Will verify the instance of each object and then call functions based on the object type
    }
}