//package level;
package com.example.intotheabyss.dungeonassets

import com.example.intotheabyss.game.player.Player
import kotlin.collections.ArrayList
import kotlin.random.Random

/**
 * DEPRECIATED
 * Level is not used by the client-side, but was here for the beginning before networking was set up.
 * This allowed the client-side development to work independently of the server-side until the server was running
 *
 * @constructor Generates a level based on the seed provided
 */
class Level {
    private val mapWidth: Int = 100
    private val mapHeight: Int = 50



    private var seed: Int = Random.nextInt()
    private var players: ArrayList<Player> = arrayListOf<Player>()
    private var grid = arrayOf<Array<Tile>>()
    private var rooms = arrayOf<Array<Int>>()

    constructor(seed: Int) {
        this.seed = seed
    }

    /**
     * DEPRECIATED
     * Returns the ArrayList of Players.
     * @return An ArrayList of Players
     */
    fun getPlayers(): ArrayList<Player> {
        return this.players
    }

    /**
     * DEPRECIATED
     * Returns the 2D dungeon layout
     * @return A 2D array of type Tile
     */
    fun getGrid(): Array<Array<Tile>> {
        return this.grid
    }

    //Function to set level grid to a big open box. Walls on edges, floor in middle
    // When is very similar to the switch statement in C with a few added bonuses
    /**
     * DEPRECIATED
     * Generates a default dungeon level (a big open box).
     */
    fun genDefaultLev() {
        for(i in 1..this.mapHeight) {
            for(j in 1..this.mapWidth) {
                when {
                    i == 1 or this.mapHeight -> {
                        grid[i][j] = Wall()
                    }
                    j == 1 or this.mapWidth -> {
                        grid[i][j] = Wall()
                    }
                    else -> {
                        grid[i][j] = Floor()
                    }
                }
            }
        }
    }
}
