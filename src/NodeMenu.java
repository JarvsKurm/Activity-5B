import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedList;

public class NodeMenu extends JPanel {
    Gui gui;
    Display display;
    boolean nodePen = false, nodeEraser = false, nodeEditor = false;
    LinkedList<Image> imageSprites;

    NodeMenu(Gui gui, Display display) {
        this.gui = gui;
        this.display = display;

        imageSprites = new LinkedList<>();

        for (int i = 1; i <= 53; i++) {
            Image image = new ImageIcon("images/nodeSprites/node (" + i + ").png").getImage().getScaledInstance(75, 75, Image.SCALE_SMOOTH);
            imageSprites.add(image);
        }

        display.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (nodePen) {
                    boolean emptySpace = true;
                    for (HashMap.Entry<Integer, Node> entry : display.nodes.entrySet()) {
                        Node n = entry.getValue();
                        if (display.dist(n.x, n.y,  display.curX,  display.curY) < n.s) {
                            emptySpace = false;
                            break;
                        }
                    }
                    if (emptySpace) display.nodes.put(display.count, new Node(display.count++, display.curX, display.curY, display));
                }
                if (nodeEraser) {
                    for (HashMap.Entry<Integer, Node> entry : display.nodes.entrySet()) {
                        Node n = entry.getValue();
                        if (display.dist(n.x, n.y,  display.curX,  display.curY) < n.s) {
                            display.nodes.remove(n.index);
                            gui.sp.spa.shortestPath(display.nodes, gui.sp.location, gui.sp.destination, display.roads);
                            break;
                        }
                    }
                }
                if (nodeEditor) {
                    for (HashMap.Entry<Integer, Node> entry : display.nodes.entrySet()) {
                        Node n = entry.getValue();
                        if (display.dist(n.x, n.y,  display.curX,  display.curY) < n.s) {
                            n.changeImage(imageSprites.get(display.random.nextInt(1, imageSprites.size())));
                            break;
                        }
                    }
                }
            }

            @Override
            public void mousePressed(MouseEvent e) {

            }

            @Override
            public void mouseReleased(MouseEvent e) {

            }

            @Override
            public void mouseEntered(MouseEvent e) {

            }

            @Override
            public void mouseExited(MouseEvent e) {

            }
        });
    }

    void allButtReset() {
        nodePen = false;
        nodeEraser = false;
        nodeEditor = false;
        updateSprites();
    }

    void nodePenToggle() {
        nodeEraser = false;
        nodeEditor = false;
        nodePen = !nodePen;
        updateSprites();
    }

    void nodeEraserToggle() {
        nodePen = false;
        nodeEditor = false;
        nodeEraser = !nodeEraser;
        updateSprites();
    }

    void nodeEditorToggle() {
        nodePen = false;
        nodeEraser = false;
        nodeEditor = !nodeEditor;
        updateSprites();
    }

    void updateSprites() {
        gui.buttons.get("NODE PEN").setIcon(gui.buttonImages.get("NODE PEN")[nodePen ? 1 : 0]);
        gui.buttons.get("NODE ERASER").setIcon(gui.buttonImages.get("NODE ERASER")[nodeEraser ? 1 : 0]);
        gui.buttons.get("NODE EDITOR").setIcon(gui.buttonImages.get("NODE EDITOR")[nodeEditor ? 1 : 0]);
    }
}
