package app.leaderboard;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

// TODO: Auto-generated Javadoc
/**
 * The Class Leaderboard.
 */
@Entity
public class Leaderboard {

    /** The player ID. */
    @Id
    @Column(length = 50)
    private String playerID;

    /** The monsters killed. */
    private Integer floorsCleared, monstersKilled;

    /**
     * Gets the player ID.
     *
     * @return the player ID
     */
    public String getPlayerID() {
        return playerID;
    }

    /**
     * Sets the player ID.
     *
     * @param playerID the new player ID
     */
    public void setPlayerID(String playerID) {
        this.playerID = playerID;
    }

    /**
     * Gets the floors cleared.
     *
     * @return the floors cleared
     */
    public Integer getFloorsCleared() {
        return floorsCleared;
    }

    /**
     * Sets the floors cleared.
     *
     * @param floorsCleared the new floors cleared
     */
    public void setFloorsCleared(Integer floorsCleared) {
        this.floorsCleared = floorsCleared;
    }

    /**
     * Gets the monsters killed.
     *
     * @return the monsters killed
     */
    public Integer getMonstersKilled() {
        return monstersKilled;
    }

    /**
     * Sets the monsters killed.
     *
     * @param monstersKilled the new monsters killed
     */
    public void setMonstersKilled(Integer monstersKilled) {
        this.monstersKilled = monstersKilled;
    }
}

