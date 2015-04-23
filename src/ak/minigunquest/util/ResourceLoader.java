package ak.minigunquest.util;

import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.Sound;
import org.newdawn.slick.util.BufferedImageUtil;
import sun.misc.JavaIOAccess;

import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;

/**
 * Created by Aleksander on 25/03/2015.
 */
public class ResourceLoader {

    //fucked your mother.
    //Written when I was feeling shit, so it's not a bug but a feature.

    public static Sound getSound (String file) throws SlickException {
        return new Sound(file);
    }

    public static Image getImage (String file) throws IOException {
        return new Image(BufferedImageUtil.getTexture("", ImageIO.read(new File(file))));
    }

}
