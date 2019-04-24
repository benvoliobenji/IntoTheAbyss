package com.example.intotheabyss.networking.packets

import com.example.intotheabyss.dungeonassets.Tile

/**
 * DEPRECIATED
 * The Map Packet that sends the 2D array representing the dungeon to the client.
 *  However this has since been depreciated as VolleyNetwork now gets the Map.
 *  @constructor Provided a 2D array of type Tile, set that array to the payload of the packet.
 *  @author Benjamin Vogel
 */
class MapPacket(var levelGrid: Array<Array<Tile>> = arrayOf())