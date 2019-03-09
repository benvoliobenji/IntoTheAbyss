package com.example.intotheabyss.utils

import android.util.Log
import com.example.intotheabyss.dungeonassets.Floor
import com.example.intotheabyss.dungeonassets.Stair
import com.example.intotheabyss.dungeonassets.Tile
import com.example.intotheabyss.dungeonassets.Wall
import org.json.JSONArray

fun gridParse(grid: JSONArray): Array<Array<Tile>> {
    // In order to dynamically resize the dungeon if needed, ArrayLists are created and then converted to Arrays for
    // display.
    val dungeonGrid: Array<Array<Tile>>
    val dungeonGridList = ArrayList<Array<Tile>>()

    for(i in 0 until grid.length()) {
        val gridRow = grid.getJSONArray(i)
        val rowList = ArrayList<Tile>()
        for(j in 0 until gridRow.length()) {
            val type = gridRow.getJSONObject(j)
            val tileType = type.getString("type")
            when (tileType) {
                "WALL" -> {
                    rowList.add(Wall())
                }
                "FLOOR" -> {
                    rowList.add(Floor())
                }
                "STAIR" -> {
                    rowList.add(Stair())
                }
            }
            rowList.trimToSize()
            Log.i("gridParse", rowList[j].toString())

            // Create a model Array to add to convert rowList to an Array<Tile> and then append to the dungeonGridList
            val rowArray = arrayOfNulls<Tile>(rowList.size)
            dungeonGridList.add(rowList.toArray(rowArray))
        }
    }
    // Trim dungeonGridList and convert it to Array<Array<Tile>>
    dungeonGridList.trimToSize()
    val rowArray = arrayOfNulls<Array<Tile>>(dungeonGridList.size)
    dungeonGrid = dungeonGridList.toArray(rowArray)

    return dungeonGrid
}