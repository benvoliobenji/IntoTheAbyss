package com.example.intotheabyss.utils

import android.util.Log
import com.example.intotheabyss.dungeonassets.Floor
import com.example.intotheabyss.dungeonassets.Stair
import com.example.intotheabyss.dungeonassets.Tile
import com.example.intotheabyss.dungeonassets.Wall
import org.json.JSONArray

/**
 * This function is a helper function to retrieveNewDungeonLevel() in VolleyNetwork. This takes a JSONArray and
 * returns a 2D array of type Tile to then display to the user.
 * @param grid A JSONArray that contains the 2D dungeon array.
 * @return A 2D array of type Tile.
 * @author Benjamin Vogel
 */
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
            // Log.i("gridParse", rowList[j].toString())
        }
        rowList.trimToSize()
        // Create a model Array to add to convert rowList to an Array<Tile> and then append to the dungeonGridList
        val rowArray = arrayOfNulls<Tile>(rowList.size)
        dungeonGridList.add(rowList.toArray(rowArray))

    }
    // Trim dungeonGridList and convert it to Array<Array<Tile>>
    dungeonGridList.trimToSize()
    val rowArray = arrayOfNulls<Array<Tile>>(dungeonGridList.size)
    dungeonGrid = dungeonGridList.toArray(rowArray)

//    Log.i("RowLength", dungeonGrid.size.toString())
//    Log.i("ColumnLength", dungeonGrid[0].size.toString())
//
//    for (row in dungeonGrid) {
//        Log.i("Row", row.size.toString())
//        for (spot in row) {
//            Log.i("RowInternal", spot.toString())
//        }
//        Log.i("Row", "End of Row")
//    }

    return dungeonGrid
}