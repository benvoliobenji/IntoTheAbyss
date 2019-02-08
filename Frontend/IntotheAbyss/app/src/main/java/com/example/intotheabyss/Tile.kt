package com.example.intotheabyss

abstract class Tile {
    internal var canHold: Boolean = false
    internal var isPassable: Boolean = false

    abstract val type: Int


}

