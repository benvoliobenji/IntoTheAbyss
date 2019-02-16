package com.example.intotheabyss.networking

class NetworkRunnable: Runnable {
    override fun run() {
        val network = Network()
        network.connect()
    }
}