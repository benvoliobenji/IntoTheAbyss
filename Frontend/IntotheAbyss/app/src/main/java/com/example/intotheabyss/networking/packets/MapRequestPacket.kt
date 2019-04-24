package com.example.intotheabyss.networking.packets

/**
 * DEPRECIATED
 * This is designed to be used in conjunction of MapPacket and would act as a call/response. The client would send
 * the MapRequestPacket and the server would respond with a MapPacket.
 *
 * This has since been depreciated due to VolleyNetwork handling the map logic.
 * @constructor Provided a floor number of type Int, set the payload to that number.
 * @constructor Benjamin Vogel
 */
class MapRequestPacket(floorNumber: Int) {
    var floorNum: Int = floorNumber
}