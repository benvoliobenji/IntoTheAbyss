package app.tiles;

import app.utils.TileTypes;

// TODO: Auto-generated Javadoc
/**
 * The Class Stair.
 */
public class Stair extends Tile {

    /**
     * Instantiates a new stair.
     */
    public Stair(){
        canHold = false;
        isPassable = true;
        type = TileTypes.STAIR;
    }

    /* (non-Javadoc)
     * @see app.tiles.Tile#getType()
     */
    @Override
    public TileTypes getType() {
        return type;
    }
    
    /* (non-Javadoc)
     * @see app.tiles.Tile#isPassable()
     */
    public boolean isPassable() {
		return isPassable;
	}
	
	/* (non-Javadoc)
	 * @see app.tiles.Tile#canHold()
	 */
	public boolean canHold() {
		return canHold;
	}

}
