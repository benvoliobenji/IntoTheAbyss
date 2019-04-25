package com.example.intotheabyss.networking.packets

/**
 * The Connection Packet that gets sent when Kryonet establishes a connection.
 * @constructor Provided a string, set that string as the payload.
 * @author Benjamin Vogel
 */
class ConnectionPackage(var playerID: String) {
}