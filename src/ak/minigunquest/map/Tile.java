package ak.minigunquest.map;

import ak.minigunquest.Logger;
import ak.minigunquest.Main;
import ak.minigunquest.entity.SceneryEntity;
import ak.minigunquest.entity.SceneryEntityType;
import ak.minigunquest.map.TileMaterial;

import java.util.ArrayList;

/**
 * Created by Aleksander on 21/03/2015.
 */
public class Tile {

    private int x;
    private int y;
    private TileMaterial material;
    private ArrayList<SceneryEntity> scenery = new ArrayList<SceneryEntity>();


    public Tile(TileMaterial mat, int x, int y){
        this.x = x;
        this.y = y;
        this.material = mat;
    }

    public void addScenery(SceneryEntity sceneryEntity1) {
        int x32 = x * 32;
        int y32 = y * 32;

        if ((sceneryEntity1.getX() >= x32) && (sceneryEntity1.getX() <= x32+32) && (sceneryEntity1.getY() >= y32+0) && (sceneryEntity1.getY() <= y32+32)) {
            scenery.add(sceneryEntity1);
        } else {
            Main.getLogger().log(Logger.LoggingLevel.ERROR, sceneryEntity1 + " added to tile " + this.getX() + ", " + this.getY() + " was out of bounds");
        }

    }

    public ArrayList<SceneryEntity> getScenery(){
        return scenery;
    }

    public TileMaterial getMaterial(){
        return material;
    }

    public int getX(){
        return x;
    }

    public int getY(){
        return y;
    }
}
