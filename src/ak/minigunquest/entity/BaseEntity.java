package ak.minigunquest.entity;

import ak.minigunquest.util.Direction;

/**
 * Created by Aleksander on 21/03/2015.
 */
public abstract class BaseEntity {

    private double x;
    private double y;

    public BaseEntity(double x, double y) {
        this.x = x;
        this.y = y;
    }

    public void setX(double x) {
        this.x = x;
    }

    public void setY(double y) {
        this.y = y;
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

}