package ak.minigunquest.entity;

import ak.minigunquest.Logger;
import ak.minigunquest.Main;
import ak.minigunquest.map.Map;
import ak.minigunquest.util.Direction;
import org.newdawn.slick.Image;
import org.newdawn.slick.util.BufferedImageUtil;

import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;

/**
 * Created by Aleksander on 21/03/2015.
 */
public class PlayerEntity extends LivingEntity{
    public HashMap<Direction, Image> textures = new HashMap<Direction, Image>(9);
    private static double speed;
    public boolean isLeftPressed, isRightPressed, isUpPressed, isDownPressed;
    public int tileX, tileY;
    private Map gameMap;

    public PlayerEntity(double x, double y, double speed, Map gameMap){
        super(x, y, EntityBehaviour.PLAYER);
        this.gameMap = gameMap;
        this.speed = speed;

        try {
            Main.getLogger().log(Logger.LoggingLevel.DEBUG, "Loading Textures");
            textures.put(Direction.NORTH, new Image(BufferedImageUtil.getTexture("", ImageIO.read(new File("res/textures/player/NORTH.png")))));
            textures.put(Direction.NORTH_EAST, new Image(BufferedImageUtil.getTexture("",ImageIO.read(new File("res/textures/player/NORTH_EAST.png")))));
            textures.put(Direction.EAST, new Image(BufferedImageUtil.getTexture("",ImageIO.read(new File("res/textures/player/EAST.png")))));
            textures.put(Direction.SOUTH_EAST, new Image(BufferedImageUtil.getTexture("",ImageIO.read(new File("res/textures/player/SOUTH_EAST.png")))));
            textures.put(Direction.SOUTH, new Image(BufferedImageUtil.getTexture("",ImageIO.read(new File("res/textures/player/SOUTH.png")))));
            textures.put(Direction.SOUTH_WEST, new Image(BufferedImageUtil.getTexture("",ImageIO.read(new File("res/textures/player/SOUTH_WEST.png")))));
            textures.put(Direction.WEST, new Image(BufferedImageUtil.getTexture("",ImageIO.read(new File("res/textures/player/WEST.png")))));
            textures.put(Direction.NORTH_WEST, new Image(BufferedImageUtil.getTexture("",ImageIO.read(new File("res/textures/player/NORTH_WEST.png")))));

            textures.put(Direction.CENTRE, new Image(BufferedImageUtil.getTexture("",ImageIO.read(new File("res/textures/player/CENTRE.png")))));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public double getSpeed(){
        return speed;
    }

    public void update(){
        this.setDirection(Direction.CENTRE);
        if(isUpPressed && !isDownPressed){
            this.setDirection(Direction.NORTH);
            if(isLeftPressed){
                this.setDirection(Direction.NORTH_WEST);
            }else if(isRightPressed){
                this.setDirection(Direction.NORTH_EAST);
            }
        }else if(isDownPressed && !isUpPressed){
            this.setDirection(Direction.SOUTH);
            if(isLeftPressed){
                this.setDirection(Direction.SOUTH_WEST);
            }else if(isRightPressed){
                this.setDirection(Direction.SOUTH_EAST);
            }
        }else if(isLeftPressed && !isRightPressed){
            this.setDirection(Direction.WEST);
        }else if(isRightPressed && !isLeftPressed){
            this.setDirection(Direction.EAST);
        }

        tileX = (int) Math.floor((16 + this.getX())/32);
        tileY = (int) Math.floor((16 + this.getY())/32);
    }

    public int getTileX(){
        return tileX;
    }

    public int getTileY(){
        return tileY;
    }
}
