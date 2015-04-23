package ak.minigunquest.entity;

import ak.minigunquest.util.Direction;

/**
 * Created by Aleksander on 21/03/2015.
 */
public class LivingEntity extends BaseEntity {

    private final EntityBehaviour behaviour;
    private Direction facing;

    public LivingEntity(double x, double y, EntityBehaviour behaviour) {
        super(x, y);
        this.behaviour = behaviour;
    }

    public Direction getDirection() {
        return facing;
    }

    public void setDirection(Direction direction){
        this.facing = direction;
    }

}
