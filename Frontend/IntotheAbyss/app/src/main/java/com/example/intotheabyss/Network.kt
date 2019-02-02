package com.example.intotheabyss

import com.esotericsoftware.kryonet.Client
import com.esotericsoftware.kryonet.Connection
import com.esotericsoftware.kryonet.Listener
import java.io.IOException

class Network : Listener() {
    private var client: Client = Client()
    private val ip: String = "localhost"
    private val  port: Int = 27960

    fun connect() {
        client = Client()

        //Add the class registration when we get to this part

        client.addListener(this)
        client.start()
        try{
            // Attempt to connect within a 5000 ms window before timing out
            client.connect(5000, ip, port, port)
        } catch (e: IOException) {
            e.printStackTrace()
        }
    }

    // Needs to be explicitly overridden due to Kotlin clashing with JVM
    override fun received(c: Connection?, o: Any?) {
        // This will be where we verify the objects that have been sent over the connection
        // Will verify the instance of each object and then call functions based on the object type
    }


}
