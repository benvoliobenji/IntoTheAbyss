package com.example.intotheabyss.dungeonassets

class Wall : Tile() {
    override val type = 2

    init {
        canHold = false
        isPassable = false
    }
}