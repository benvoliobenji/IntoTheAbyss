package app.tiles;

import app.utils.TileTypes;

public class Stair extends Tile {

    public Stair(){
        canHold = false;
        isPassable = true;
        type = TileTypes.STAIR;
    }

    @Override
    public TileTypes getType() {
        return type;
    }


}
