package ak.minigunquest;

import ak.minigunquest.entity.PlayerEntity;
import ak.minigunquest.entity.SceneryEntity;
import ak.minigunquest.entity.SceneryEntityType;
import ak.minigunquest.map.Map;
import ak.minigunquest.map.Tile;
import ak.minigunquest.util.Direction;
import ak.minigunquest.util.References;
import ak.minigunquest.map.TileMaterial;
import ak.minigunquest.util.ResourceLoader;
import com.sun.javafx.runtime.SystemProperties;
import net.java.games.input.Controller;
import org.lwjgl.input.Keyboard;
import org.newdawn.slick.*;
import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.util.BufferedImageUtil;
import sun.plugin2.util.SystemUtil;

import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by Aleksander on 20/03/2015.
 */
public class Main extends BasicGame {

    enum GameState {
        GENERATING,
        MAIN_MENU,
        PLAYING,
        PAUSED
    }

    public static double halfWidth, halfHeight;
    public static GameState gameState;
    public static AppGameContainer app;
    public static Map gameMap;
    public int mouseX, mouseY;



    private HashMap<TileMaterial, Image> tileTexures = new HashMap<TileMaterial, Image>(3);
    private HashMap<SceneryEntityType, Image> scenetyTextures = new HashMap<SceneryEntityType, Image>(5);
    private HashMap<TileMaterial, Sound> playerStep = new HashMap<TileMaterial, Sound>();

    private PlayerEntity player;
    private float rotateAngle;
    private boolean drawDebug;
    private Camera camera;
    private Image menuRotator;
    private Image menuScreen;
    private static Animation loadingTorch;
    private static Logger logger;
    private Sound sound;
    private Music music;
    public Main(String str){
        super(str);
    }

    public static void main(String[] args) {

        try {
            app = new AppGameContainer(new Main(References.gameName));
            app.setDisplayMode(1024, 720, false);
            app.setTargetFrameRate(120);
            app.setShowFPS(false);
            app.setSoundOn(true);
            app.setSoundVolume(0.5f);
            app.setAlwaysRender(true);
            app.start();

        } catch (SlickException e) {
            e.printStackTrace();
        }

    }

    public static Logger getLogger() {
        return logger;
    }

    @Override
    public void init(final GameContainer gameContainer) throws SlickException {
        loadingTorch = new Animation();
        drawDebug = false;
        music = new Music("res/sounds/music/music1.wav");
        //music.loop();
        sound = new Sound("res/sounds/player/playerstep.wav");


        try {
            tileTexures.put(TileMaterial.STONE, ResourceLoader.getImage("res/textures/blocks/STONE.png"));
            tileTexures.put(TileMaterial.PATH, ResourceLoader.getImage("res/textures/blocks/PATH.png"));
            tileTexures.put(TileMaterial.GRASS_1, ResourceLoader.getImage("res/textures/blocks/GRASS_1.png"));
            tileTexures.put(TileMaterial.GRASS_2, ResourceLoader.getImage("res/textures/blocks/GRASS_2.png"));
            tileTexures.put(TileMaterial.GRASS_3, ResourceLoader.getImage("res/textures/blocks/GRASS_3.png"));
            scenetyTextures.put(SceneryEntityType.PLANT_1, ResourceLoader.getImage("res/textures/scenery/PLANT_1.png"));
            scenetyTextures.put(SceneryEntityType.PLANT_2, ResourceLoader.getImage("res/textures/scenery/PLANT_2.png"));
            scenetyTextures.put(SceneryEntityType.PLANT_3, ResourceLoader.getImage("res/textures/scenery/PLANT_3.png"));
            scenetyTextures.put(SceneryEntityType.PLANT_4, ResourceLoader.getImage("res/textures/scenery/PLANT_4.png"));
            scenetyTextures.put(SceneryEntityType.PLANT_5, ResourceLoader.getImage("res/textures/scenery/PLANT_5.png"));


            loadingTorch.addFrame(ResourceLoader.getImage("res/textures/menu/torchAnimation/frame_000.png"), 1);
            loadingTorch.addFrame(ResourceLoader.getImage("res/textures/menu/torchAnimation/frame_001.png"), 1);
            loadingTorch.addFrame(ResourceLoader.getImage("res/textures/menu/torchAnimation/frame_002.png"), 1);
            loadingTorch.addFrame(ResourceLoader.getImage("res/textures/menu/torchAnimation/frame_003.png"), 1);
            loadingTorch.addFrame(ResourceLoader.getImage("res/textures/menu/torchAnimation/frame_004.png"), 1);
            loadingTorch.addFrame(ResourceLoader.getImage("res/textures/menu/torchAnimation/frame_005.png"), 1);

        } catch (IOException e) {
            e.printStackTrace();
        }
        halfWidth = gameContainer.getWidth()/2;
        halfHeight = gameContainer.getHeight()/2;

        try {
            menuScreen = new Image(BufferedImageUtil.getTexture("",ImageIO.read(new File("res/textures/menu/mainmenu/TITLE_SCREEN.png"))));
            menuRotator = new Image(BufferedImageUtil.getTexture("",ImageIO.read(new File("res/textures/menu/mainmenu/ROTATOR1.png"))));
        } catch (IOException e) {
            e.printStackTrace();
        }

        logger = new Logger(System.out);
        player = new PlayerEntity(1000, 1000, 0.15d, gameMap);
        camera = new Camera(player, app);

        gameState = GameState.MAIN_MENU;
        final Main main = this;
        gameContainer.getInput().addMouseListener(new MouseListener() {
            @Override
            public void mouseWheelMoved(int i) {

            }

            @Override
            public void mouseClicked(int i, int i1, int i2, int i3) {
                mouseX = i1;
                mouseY = i2;
            }

            @Override
            public void mousePressed(int i, int i1, int i2) {

            }

            @Override
            public void mouseReleased(int i, int i1, int i2) {

            }

            @Override
            public void mouseMoved(int i, int i1, int i2, int i3) {

            }

            @Override
            public void mouseDragged(int i, int i1, int i2, int i3) {

            }

            @Override
            public void setInput(Input input) {

            }

            @Override
            public boolean isAcceptingInput() {
                return true;
            }

            @Override
            public void inputEnded() {

            }

            @Override
            public void inputStarted() {

            }
        });
        gameContainer.getInput().addKeyListener(new KeyListener() {
            @Override
            public void keyPressed(int keyCode, char pressedChar) {

                if((keyCode == Keyboard.KEY_RETURN) && (gameState == GameState.MAIN_MENU) && (gameMap == null)){
                    Thread t = new Thread(new Runnable() {
                        @Override
                        public void run() {
                            getLogger().log(Logger.LoggingLevel.DEBUG, "Generation of level started");
                            long startTime = System.currentTimeMillis();
                            gameMap = new Map(1000, 1000, main);
                            getLogger().log(Logger.LoggingLevel.DEBUG, "Generation of level finished in " + (System.currentTimeMillis() - startTime) + " milliseconds!");
                            Main.gameState = GameState.PLAYING;

                        }
                    });
                    gameState = GameState.GENERATING;
                    t.start();
                }

                if(gameState == GameState.PLAYING){
                    if(keyCode == Keyboard.KEY_F3){
                        drawDebug = !drawDebug;
                    }
                    if(keyCode == Keyboard.KEY_LEFT){
                        player.isLeftPressed = true;
                    }
                    if(keyCode == Keyboard.KEY_RIGHT){
                        player.isRightPressed = true;
                    }
                    if(keyCode == Keyboard.KEY_UP){
                        player.isUpPressed = true;
                    }
                    if(keyCode == Keyboard.KEY_DOWN){
                        player.isDownPressed = true;
                    }
                }
            }

            @Override
            public void keyReleased(int keyCode, char CharPressed) {
                if(gameState == GameState.PLAYING){
                    if(keyCode == Keyboard.KEY_LEFT){
                        player.isLeftPressed = false;
                    }
                    if(keyCode == Keyboard.KEY_RIGHT){
                        player.isRightPressed = false;
                    }
                    if(keyCode == Keyboard.KEY_UP){
                        player.isUpPressed = false;
                    }
                    if(keyCode == Keyboard.KEY_DOWN){
                        player.isDownPressed = false;
                    }
                }
            }

            @Override
            public void setInput(Input input) {

            }

            @Override
            public boolean isAcceptingInput() {
                return true;
            }

            @Override
            public void inputEnded() {
            }

            @Override
            public void inputStarted() {
            }

        });
    }

    @Override
    public void update(GameContainer gameContainer, int delta) throws SlickException {
        if(gameState == GameState.PLAYING){
            //System.out.println("Delta = " + delta);
            player.update();


            double root2 = Math.sqrt(2);
            double deltaSpeed = player.getSpeed() * delta;
            switch (player.getDirection()){
                case NORTH:
                    player.setY(player.getY() - deltaSpeed);
                    break;
                case NORTH_EAST:
                    player.setX(player.getX() + deltaSpeed / root2);
                    player.setY(player.getY() - deltaSpeed / root2);
                    break;
                case EAST:
                    player.setX(player.getX() + deltaSpeed);
                    break;
                case SOUTH_EAST:
                    player.setX(player.getX() + deltaSpeed / root2);
                    player.setY(player.getY() + deltaSpeed / root2);
                    break;
                case SOUTH:
                    player.setY(player.getY() + deltaSpeed);
                    break;
                case SOUTH_WEST:
                    player.setY(player.getY() + deltaSpeed / root2);
                    player.setX(player.getX() - deltaSpeed / root2);
                    break;
                case WEST:
                    player.setX(player.getX() - deltaSpeed);
                    break;
                case NORTH_WEST:
                    player.setX(player.getX() - deltaSpeed / root2);
                    player.setY(player.getY() - deltaSpeed / root2);
                    break;
            }
            if(player.getDirection() != Direction.CENTRE && !sound.playing()){
                sound.play((float) Math.random()/10 + 1.1f, app.getSoundVolume());
            }

            camera.update();
        }else if(gameState == GameState.MAIN_MENU){
            rotateAngle = 0.1f * delta;
        }
    }

    @Override
    public void render(GameContainer gameContainer, Graphics graphics) throws SlickException {
        switch (gameState){
            case GENERATING:
                graphics.setBackground(Color.gray);
                loadingTorch.draw((gameContainer.getWidth() - loadingTorch.getWidth()) /2, (gameContainer.getHeight() - loadingTorch.getHeight()) /2);
                break;
            case PAUSED:
                graphics.setBackground(Color.black);
                break;
            case PLAYING:
                ArrayList<SceneryEntity> plant = new ArrayList<SceneryEntity>();
                for(Tile tile : gameMap.getVisibleTiles(camera)){
                    tileTexures.get(tile.getMaterial()).draw((float) (tile.getX() * 32 - camera.getX()), (float) (tile.getY() * 32 - camera.getY()));
                    for(SceneryEntity entity : tile.getScenery()){
                            plant.add(entity);
                    }
                }
                for(SceneryEntity entity : plant){
                    //Think of the children! getLogger().log(Logger.LoggingLevel.DEBUG, "Entity is of type : " + entity.getSceneryType());
                    scenetyTextures.get(entity.getSceneryType()).draw((float) (entity.getX() - camera.getX()),  (float) (entity.getY() - camera.getY()));
                }


                if(drawDebug) {
                    graphics.drawString(
                                     "Drawing rect from " + (player.getTileX() - getScreenWidthInTiles() / 2) + " to " + (player.getTileX() + getScreenWidthInTiles() / 2)
                            + "\n" + "Drawing rect from " + (player.getTileY() - getScreenHeightInTiles() / 2) + " to " + (player.getTileY() + getScreenHeightInTiles() / 2)
                            + "\n" + "Player is facing " + player.getDirection()
                            + "\n" + "Player is standing on tile " + player.getTileX() + ", " + player.getTileY()
                            + "\n" + "Player coordinates : " + Math.floor(player.getX()) + ", " + Math.floor(player.getY())
                            + "\n" + "Game is running " + app.getFPS() + " Frames Per Second, and using Java from " + SystemProperties.getCodebase()
                            , 1, 1);
                }
                try {
                    player.textures.get(player.getDirection()).draw((float) (player.getX() - camera.getX()), (float) (player.getY() - camera.getY()));
                }catch (NullPointerException e){
                    getLogger().log(Logger.LoggingLevel.DEBUG, "Crash on drawing player, try... running the game at a LOWER fps. ");
                }
                break;
            case MAIN_MENU:
                graphics.setBackground(Color.darkGray);

                menuScreen.draw((gameContainer.getWidth() - menuScreen.getWidth()) /2 , (gameContainer.getHeight() - menuScreen.getHeight())/5);
                menuRotator.draw((gameContainer.getWidth() - menuRotator.getWidth()) /4 , (gameContainer.getHeight() - menuRotator.getHeight())/5 * 3);
                menuRotator.rotate(rotateAngle);
        }
    }

    @Override
    public boolean closeRequested() {
        app.destroy();
        return true;
    }

    @Override
    public String getTitle() {
        return References.gameName;
    }

    public int getScreenWidthInTiles(){
        return this.app.getWidth() / 32;
    }

    public int getScreenHeightInTiles(){
        return this.app.getHeight() / 32;
    }
}
