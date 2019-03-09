package com.example.intotheabyss.utils

import com.example.intotheabyss.dungeonassets.Floor
import com.example.intotheabyss.dungeonassets.Stair
import com.example.intotheabyss.dungeonassets.Tile
import com.example.intotheabyss.dungeonassets.Wall
import org.json.JSONArray

fun GridParse(grid: JSONArray): Array<Array<Tile>> {
    val dungeonGrid: Array<Array<Tile>> = arrayOf()

    for(i in 0 until grid.length()) {
        val gridRow = grid.getJSONArray(i)
        for(j in 0 until gridRow.length()) {
            val type = gridRow.getJSONObject(j)
            val tileType = type.getString("type")
            when (tileType) {
                "WALL" -> {
                    dungeonGrid[i][j] = Wall()
                }
                "FLOOR" -> {
                    dungeonGrid[i][j] = Floor()
                }
                "STAIR" -> {
                    dungeonGrid[i][j] = Stair()
                }
            }
        }
    }

    return dungeonGrid
}