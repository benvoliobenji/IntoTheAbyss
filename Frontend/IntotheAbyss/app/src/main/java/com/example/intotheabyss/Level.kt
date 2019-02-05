//package level;
package com.example.intotheabyss

import java.util.ArrayList
import java.util.Random
//import player.Player

class Level {
    // private static int mapdepth = 3;

    // private Random rand;
    val players: ArrayList<Player>? = null
    var grid: ArrayList<ArrayList<Tile>>? = null

    //Errors on arrayofNulls. Wants type, confused to where though
    constructor() {
//        grid = Array(mapWidth) { arrayOfNulls(mapHeight):}
    }

    constructor(random: Random) {
//        grid = Array(mapWidth) { arrayOfNulls(mapHeight) }
        // rand = random;

    }

    constructor(seed: Int) {
//        grid = Array(mapWidth) { arrayOfNulls(mapHeight) }
    }

    companion object {
        private val mapWidth = 100
        private val mapHeight = 50
    }

    //Function to set level grid to a big open box. Walls on edges, floor in middle
    //I think this should be correct, but I had a hell of a time doing this in Kotlin.
    //Everything is a bit different
    fun genDefaultLev() {
        for(i in 1..mapHeight)  {
            grid.add(ArrayList<Tile>())
            for(j in 1..mapWidth)   {
                if (i == 1 or mapHeight)    {
                    val newTile = Wall()
                    grid.get(i).add(newTile)
                }   else if(j == 1 or mapWidth) {
                    val newTile = Wall()
                    grid.get(i).add(newTile)
                }   else    {
                    //If not top/bottom or side edge, new tile should be floor
                    val newTile = Floor()
                    grid.get(i).add(newTile)
                }
            }
        }
    }

}
