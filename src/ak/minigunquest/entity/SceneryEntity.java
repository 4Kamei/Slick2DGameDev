package ak.minigunquest.entity;

import ak.minigunquest.map.Tile;
/**
 * Created by Aleksander on 24/03/2015.
 */
public class SceneryEntity extends BaseEntity{

    private SceneryEntityType sceneryType;
    public SceneryEntity(double x, double y, SceneryEntityType sceneryType) {
        super(x, y);
        this.sceneryType = sceneryType;
    }

    public SceneryEntityType getSceneryType(){
        return sceneryType;
    }

}
