package app.tiles;

import app.utils.TileTypes;

public class Stair extends Tile {

    public Stair(){
        canHold = true;
        isPassable = true;
        type = TileTypes.STAIR;
    }

    @Override
    public TileTypes getType() {
        return type;
    }


}
