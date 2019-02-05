package com.example.intotheabyss

class Floor : Tile() {
    override val type = 1

    init {
        canHold = true
        isPassable = true
    }
}
