package ak.minigunquest;

import ak.minigunquest.entity.BaseEntity;
import org.newdawn.slick.AppGameContainer;

import java.util.ArrayList;

/**
 * Created by Aleksander on 22/03/2015.
 */
public class Camera {

    private int zoomLevel;
    private double x, y;
    private AppGameContainer cameraContainer;
    private BaseEntity targetEntity;
    private boolean isFollowingEntity;

    public Camera(BaseEntity entity, AppGameContainer container){
        if(entity != null){
            targetEntity = entity;
            this.x = x;
            this.y = y;
            this.cameraContainer = container;
            this.zoomLevel = 32;
        }else{
            Main.getLogger().log(Logger.LoggingLevel.SEVERE, "Base entity passed into Camera[" + this + "] was null!");
            throw new NullPointerException("BaseEntityWasNull");
        }
    }


    public boolean isFollowingEntity(){
        return isFollowingEntity;
    }

    public BaseEntity getTargetEntity(){
        return targetEntity;
    }
    public void setFollowingEntity(BaseEntity target){
        if(target != null){
            this.targetEntity = target;
            isFollowingEntity = true;
        }else{
            isFollowingEntity = false;
        }
    }
    /*
    public int getScreenWidthInTiles(){
        return screenWidth;
    }
    public int getScreenHeightInTiles(){
        return screenHeight;
    }
    */

    public double getX(){
        return x;
    }

    public double getY(){
        return y;
    }

    public void update(){
        x = targetEntity.getX() + 16 - Main.halfWidth;
        y = targetEntity.getY() + 16 - Main.halfHeight;
    }
}
