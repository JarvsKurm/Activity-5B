import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.LinkedList;

public class Node {
    Display display;
    int x, y, s = 75;
    double a;
    Image imageHold, image;
    int index;
    LinkedList<Integer> connections;

    Node(int index, int x, int y, Display display) {
        this.display = display;
        this.index = index;
        this.x = x;
        this.y = y;

        imageHold = new ImageIcon("images/nodeSprites/node (" + display.random.nextInt(1, 53) + ").png").getImage();
        image = imageHold;

        connections = new LinkedList<>();
    }

    void draw(Graphics2D gg) {
        gg.drawImage(image, x - s /2, y - s /2, s, s, null);
//        gg.drawString(String.valueOf(index) + " " + connections, x, y);
    }

    void gotoXY(double x, double y) {
        this.x = Math.min(Math.max((int) x, 0), display.w);
        this.y = Math.min(Math.max((int) y, 0), display.h);
    }

    double dist(double x1, double y1, double x2, double y2) {
        return Math.sqrt(Math.pow(x2 - x1, 2) + Math.pow(y2 - y1, 2));
    }

    void shineOn() {
        image = new ImageIcon("images/necoarc.png").getImage();
    }

    void shineOff() {
        image = imageHold;
    }

    void changeImage(Image newImage) {
        imageHold = newImage;
        image = imageHold;
    }
}
