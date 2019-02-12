package com.example.intotheabyss.player

class Player {
    //So in kotlin, we don't need getters and setters?
    var floorNumber: Int = 0
    private val x: Int = 0
    private val y: Int = 0

    //Getters and setters are automatically generated if non-private variables.
    //This is just testing to confirm.
    fun getX(): Int {
        return x
    }

    fun getY(): Int {
        return y
    }
}