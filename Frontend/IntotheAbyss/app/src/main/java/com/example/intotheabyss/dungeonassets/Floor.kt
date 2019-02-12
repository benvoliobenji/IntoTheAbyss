package com.example.intotheabyss.dungeonassets

class Floor : Tile() {
    override val type = 1

    init {
        canHold = true
        isPassable = true
    }
}
