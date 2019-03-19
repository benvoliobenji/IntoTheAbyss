package app.leaderboard;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class Leaderboard {

    @Id
    @Column(length = 50)
    private String playerID;

    private Integer floorsCleared, monstersKilled;

    public String getPlayerID() {
        return playerID;
    }

    public void setPlayerID(String playerID) {
        this.playerID = playerID;
    }

    public Integer getFloorsCleared() {
        return floorsCleared;
    }

    public void setFloorsCleared(Integer floorsCleared) {
        this.floorsCleared = floorsCleared;
    }

    public Integer getMonstersKilled() {
        return monstersKilled;
    }

    public void setMonstersKilled(Integer monstersKilled) {
        this.monstersKilled = monstersKilled;
    }
}

