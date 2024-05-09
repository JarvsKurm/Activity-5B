import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;

public class Road {
    Display display;
    int nodeA, nodeB;
    HashMap<Integer, Node> nodes;
    int roadSpriteIndex;
    File imageHold, image, image2, image3;
    int mx, my;
    int s = 20;
    Node node1, node2;
    boolean shine = false;
    LinkedList<File> imageSprites;
    int distance = -1;

    Road(HashMap<Integer, Node> nodes, int nodeA, int nodeB, Display display) {
        this.display = display;
        this.nodeA = nodeA;
        this.nodeB = nodeB;
        this.nodes = nodes;
        this.node1 = nodes.get(nodeA);
        node1.connections.add(nodeB);
        Collections.sort(node1.connections);
        this.node2 = nodes.get(nodeB);
        node2.connections.add(nodeA);
        Collections.sort(node2.connections);
        roadSpriteIndex = display.random.nextInt(1, 3);

        imageSprites = new LinkedList<>();
        for (int i = 1; i <= 3; i++) {
            imageSprites.add(new File("images/roadSprites/road (" + i + ").png"));
        }

        imageHold = new File("images/roadSprites/road (1).png");
        image = new File("images/roadSprites/road (1).png");
        image2 = new File("images/roadSprites/road (2).png");
        image3 = new File("images/roadSprites/road (3).png");
    }

    void draw(Graphics2D gg) {
        if (nodes.get(this.nodeB) == null || nodes.get(this.nodeA) == null) return;

        BufferedImage imageB;
        Node nodeB = nodes.get(this.nodeB);
        Node nodeA = nodes.get(this.nodeA);
        double angle = Math.atan2(nodeB.y - nodeA.y, nodeB.x - nodeA.x) + Math.PI/2;

        try {
            imageB = ImageIO.read(image);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        AffineTransform tx = new AffineTransform();
        tx.scale((double) s / imageB.getWidth(null), (double) s / imageB.getWidth(null));
        tx.rotate(angle, (double) imageB.getWidth() / 2, (double) imageB.getHeight() / 2);

        AffineTransformOp op = new AffineTransformOp(tx, AffineTransformOp.TYPE_BILINEAR);

        double distance = Math.sqrt(Math.pow(nodeB.x - nodeA.x, 2) + Math.pow(nodeB.y - nodeA.y, 2));
        double dx = (double) (nodeB.x - nodeA.x) / distance;
        double dy = (double) (nodeB.y - nodeA.y) / distance;

        this.distance = (int) distance;

        for (int i = 0; i < distance; i += s) {
            int xx = (int) (nodeA.x + i * dx);
            int yy = (int) (nodeA.y + i * dy);
            gg.drawImage(imageB, op, xx - s/2, yy - s/2);
        }

        mx = (nodeA.x + nodeB.x) / 2;
        my = (nodeA.y + nodeB.y) / 2;
    }

    void drawDebug(Graphics2D gg) {
        gg.setFont(new Font("Timer New Roman", Font.BOLD, 22));
        gg.setColor(new Color(0x76000000, true));
        gg.fillRoundRect(mx - 5, my - 20, 45, 25, 10, 10);
        gg.setFont(new Font("Timer New Roman", Font.BOLD, 20));
        gg.setColor(new Color(0xEED152));
        gg.drawString(String.valueOf((int) distance), mx, my);
    }

    void shineOn() {
        shine = true;
        image = new File("images/necoarc.png");
    }

    void shineOff() {
        shine = false;
        if (image != imageHold) image = imageHold;
    }

    boolean contains(int x, int y) {
        Node nodeA = nodes.get(this.nodeA);
        Node nodeB = nodes.get(this.nodeB);

        double distanceAB = Math.sqrt(Math.pow(nodeB.x - nodeA.x, 2) + Math.pow(nodeB.y - nodeA.y, 2));
        double distanceAX = Math.sqrt(Math.pow(x - nodeA.x, 2) + Math.pow(y - nodeA.y, 2));
        double distanceBX = Math.sqrt(Math.pow(x - nodeB.x, 2) + Math.pow(y - nodeB.y, 2));

        return Math.abs(distanceAB - (distanceAX + distanceBX)) < 1;
    }

    void changeImage() {
        roadSpriteIndex = (roadSpriteIndex + 1) % 3 + 1;
    }

    void remove() {
        for (int i = 0; i < nodes.get(nodeA).connections.size(); i++) {
            if (nodes.get(nodeA).connections.get(i) == nodeB) {
                nodes.get(nodeA).connections.remove(i);
                break;
            }
        }
        for (int i = 0; i < nodes.get(nodeB).connections.size(); i++) {
            if (nodes.get(nodeB).connections.get(i) == nodeA) {
                nodes.get(nodeB).connections.remove(i);
                break;
            }
        }
        display.roads.remove(this);
    }
}
