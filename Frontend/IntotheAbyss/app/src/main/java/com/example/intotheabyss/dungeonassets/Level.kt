//package level;
package com.example.intotheabyss.dungeonassets

import com.example.intotheabyss.player.Player
import kotlin.collections.ArrayList
import kotlin.random.Random


class Level {
    private val mapWidth: Int = 100
    private val mapHeight: Int = 50
    private val mapdepth: Int = 3



    private var seed: Int = Random.nextInt()
    private var players: ArrayList<Player> = arrayListOf<Player>()
    private var grid = arrayOf<Array<Tile>>()
    private var rooms = arrayOf<Array<Int>>()

    constructor(seed: Int) {
        this.seed = seed
    }

    fun getPlayers(): ArrayList<Player> {
        return this.players
    }

    fun getGrid(): Array<Array<Tile>> {
        return this.grid
    }

    //Function to set level grid to a big open box. Walls on edges, floor in middle
    // When is very similar to the switch statement in C with a few added bonuses
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
