package dungeon;
import java.util.Random;

public class Floor {

    public char[][] level;
    public int seed;
    private Random rand;


    public static Floor generateFloor() {
        Random r = new Random();
        return generateFloor(r.nextInt());
    }

    public static Floor generateFloor(int seed){
        //return a floor generated with the given RNG seed.
        return new Floor(seed);
    }

    private Floor(int seed) {
        this.seed = seed;
        rand = new Random(seed);
        level = new char[50][100];
        fillLevel();
    }

    private void fillLevel() {
        //temp
        for (int i = 0; i < 50; i ++)
            for (int j = 0; j < 100; j ++)
                level[i][j] = '+';
    }

    public char[][] getLevel(){
        return level;
    }
}
