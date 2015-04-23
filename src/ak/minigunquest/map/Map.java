package ak.minigunquest.map;

import ak.minigunquest.Camera;
import ak.minigunquest.Logger;
import ak.minigunquest.Main;
import ak.minigunquest.entity.PlayerEntity;
import ak.minigunquest.entity.SceneryEntity;
import ak.minigunquest.entity.SceneryEntityType;

import java.util.ArrayList;

/**
 * Created by Aleksander on 20/03/2015.
 */
public class Map {

    private int width, height;
    private Tile[][] map;
    private Main main;
    public Map(int width, int height, Main main){
        this.main = main;
        this.width = width;
        this.height = height;
        map = new Tile[width][height];
        for(int i = 0; i < width;i++){
            for(int j = 0; j < height;j++){
                double Rand = Math.random();
                if(Rand > 0.33f) {
                    map[i][j] = new Tile(TileMaterial.GRASS_1, i, j);
                }else if(Rand > 0.66f){
                    map[i][j] = new Tile(TileMaterial.GRASS_2, i, j);
                }else{
                    map[i][j] = new Tile(TileMaterial.GRASS_3, i, j);
                }
                if(Math.random() > 0.8) {
                    if (Math.random() > 0.5) {
                        map[i][j].addScenery(new SceneryEntity((Math.random() + i) * 32, (Math.random() + j) * 32, SceneryEntityType.PLANT_1));
                    } else if (Math.random() > 0.5) {
                        map[i][j].addScenery(new SceneryEntity((Math.random() + i) * 32, (Math.random() + j) * 32, SceneryEntityType.PLANT_2));
                    } else if (Math.random() > 0.5) {
                        map[i][j].addScenery(new SceneryEntity((Math.random() + i) * 32, (Math.random() + j) * 32, SceneryEntityType.PLANT_3));
                    } else if (Math.random() > 0.5) {
                        map[i][j].addScenery(new SceneryEntity((Math.random() + i) * 32, (Math.random() + j) * 32, SceneryEntityType.PLANT_4));
                    } else if (Math.random() > 0.5) {
                        map[i][j].addScenery(new SceneryEntity((Math.random() + i) * 32, (Math.random() + j) * 32, SceneryEntityType.PLANT_5));
                    }
                }
            }
        }
    }

    public ArrayList<Tile> asTileArrayList(){
        ArrayList<Tile> list = new ArrayList<Tile>();
        for(int i = 0;i < width;i++ ){
            for(int j = 0;j < height;j++ ){
                list.add(map[i][j]);
            }
        }
        return list;
    }
    public ArrayList<Tile> getVisibleTiles(Camera camera){
        PlayerEntity p = (PlayerEntity) camera.getTargetEntity();
        int lowerX = p.getTileX() - 2 - main.getScreenWidthInTiles()/2;
        int lowerY = p.getTileY() - 2 - main.getScreenHeightInTiles()/2;
        int higherX = p.getTileX() + 2 + main.getScreenWidthInTiles()/2;
        int higherY = p.getTileY() + 2 + main.getScreenHeightInTiles()/2;

        ArrayList<Tile> list = new ArrayList<Tile>((higherX-lowerX) * (higherY-lowerY));
        for(int i = lowerX; i <= higherX;i++){
            for(int j = lowerY; j <= higherY;j++) {
                if((i >= 0) && (j >= 0) && (i < width) && (j < height))
                    list.add(map[i][j]);
            }
        }
        return list;
    }

    public int getWidthInTiles() {
        return width;
    }

    public int getHeightInTiles() {
        return height;
    }

    public Tile getTile(int x, int y){
        try{
            return map[x][y];
        }catch (Exception e){
            Main.getLogger().log(Logger.LoggingLevel.ERROR, "The tile at x " + x + ", y " + y + " does not exist!");
            return null;
        }
    }

}
