//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.awt.image.ImageObserver;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

public class PositionedImage {
    BufferedImage image;
    int posX;
    int posY;

    public PositionedImage(String filename, int posX, int posY) {
        this.posX = posX;
        this.posY = posY;

        try {
            this.image = ImageIO.read(new File(filename));
        } catch (IOException var5) {
            var5.printStackTrace();
        }

    }

    public void draw(Graphics graphics) {
        if (this.image != null) {
            graphics.drawImage(this.image, this.posX, this.posY, (ImageObserver)null);
        }

    }
}
