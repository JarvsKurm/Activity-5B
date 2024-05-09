import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;
import java.util.HashMap;
import java.util.LinkedList;

public class RoadMenu extends JPanel {
    Gui gui;
    Display display;
    boolean roadPen = false, roadEraser = false, roadEditor = false;
    LinkedList<File> imageSprites;
    Node selectedNode;
    int prevNodeIndex = -1;
    Road selectedRoad;

    RoadMenu(Gui gui, Display display) {
        this.gui = gui;
        this.display = display;

        imageSprites = new LinkedList<>();

        for (int i = 1; i <= 3; i++) {
            imageSprites.add(new File("images/roadSprites/road (" + i + ").png"));
        }

        display.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (roadPen) {
                    boolean emptySpace = true;
                    int clickedNodeIndex = -1;
                    for (HashMap.Entry<Integer, Node> entry : display.nodes.entrySet()) {
                        Node n = entry.getValue();
                        if (display.dist(n.x, n.y,  display.curX,  display.curY) < n.s) {
                            clickedNodeIndex = n.index;
                            break;
                        }
                    }
                    if (prevNodeIndex != clickedNodeIndex && prevNodeIndex != -1 && clickedNodeIndex != -1 &&
                            !display.nodes.get(prevNodeIndex).connections.contains(clickedNodeIndex)) {
                        display.roads.add(new Road(display.nodes, prevNodeIndex, clickedNodeIndex, display));
                        gui.sp.spa.shortestPath(display.nodes, gui.sp.location, gui.sp.destination, display.roads);
                    }
                    prevNodeIndex = clickedNodeIndex;
                }
                if (roadEraser) {
                    selectedRoad = getRoadAt(e.getX(), e.getY());
                    if (selectedRoad != null) {
                        selectedRoad.remove();
                        repaint();
                        gui.sp.spa.shortestPath(display.nodes, gui.sp.location, gui.sp.destination, display.roads);
                    }
                    prevNodeIndex = -1;
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
        roadPen = false;
        roadEraser = false;
        updateSprites();
    }

    void roadPenToggle() {
        selectedRoad = null;
        roadEraser = false;
        roadPen = !roadPen;
        prevNodeIndex = -1;
        updateSprites();
    }

    void roadEraserToggle() {
        roadPen = false;
        roadEraser = !roadEraser;
        updateSprites();
    }

    void roadEditorToggle() {
        roadEditor = !roadEditor;
        updateSprites();
    }

    void updateSprites() {
        gui.buttons.get("ROAD PEN").setIcon(gui.buttonImages.get("ROAD PEN")[roadPen ? 1 : 0]);
        gui.buttons.get("ROAD ERASER").setIcon(gui.buttonImages.get("ROAD ERASER")[roadEraser ? 1 : 0]);
        gui.buttons.get("ROAD EDITOR").setIcon(gui.buttonImages.get("ROAD EDITOR")[roadEditor ? 1 : 0]);
    }

    private Road getRoadAt(int x, int y) {
        for (Road road : display.roads) {
            if (road.contains(x,y)) {
                return road;
            }
        }
        return null;
    }
}
