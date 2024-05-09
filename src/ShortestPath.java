import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.HashMap;

public class ShortestPath {
    boolean locationMarker = false;
    boolean destinationMarker = false;
    int location = -1, destination = -1;
    Display display;
    Gui gui;
    HashMap<Integer, Node> nodes;
    ShortestPathAlgorithm spa;

    ShortestPath(Gui gui, Display display) {
        this.display = display;
        this.nodes = display.nodes;
        this.gui = gui;
        this.spa = new ShortestPathAlgorithm();

        display.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
//                System.out.println("CLICK " + (count++) + " " + display.curX + " " +  display.curY);
                for (HashMap.Entry<Integer, Node> entry : nodes.entrySet()) {
                    Node n = entry.getValue();
                    if (dist(n.x, n.y,  display.curX,  display.curY) < n.s) {
                        if (locationMarker) location = n.index;
                        if (destinationMarker) destination = n.index;
                        System.out.println(n + " " + n.x + " " + n.y);
                    }
                    if (nodes.get(location) != null && nodes.get(destination) != null) {
                        System.out.println("\n\n\nshortest path");
                        spa.shortestPath(nodes, location, destination, display.roads);
//                        System.out.println(spa.minPath);
                    }
                    else {
                        for (HashMap.Entry<Integer, Node> entry2 : nodes.entrySet()) entry2.getValue().shineOff();
                        for (Road r : display.roads) r.shineOff();
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

    void draw(Graphics2D gg) {
        if (nodes.get(location) != null) gg.drawString("LOCATION", nodes.get(location).x, nodes.get(location).y+25);
        if (nodes.get(destination) != null) gg.drawString("DESTINATION", nodes.get(destination).x, nodes.get(destination).y+25);
    }

    double dist(double x1, double y1, double x2, double y2) {
        return Math.sqrt(Math.pow(x2 - x1, 2) + Math.pow(y2 - y1, 2));
    }

    void locationMarkerToggle() {
        destinationMarker = false;
        locationMarker = !locationMarker;
        updateSprites();
    }

    void destinationMarkerToggle() {
        locationMarker = false;
        destinationMarker = !destinationMarker;
        updateSprites();
    }

    void clearMarkers() {
        locationMarker = false;
        destinationMarker = false;
        updateSprites();
        location = -1;
        destination = -1;
        for (HashMap.Entry<Integer, Node> entry2 : nodes.entrySet()) entry2.getValue().shineOff();
        for (Road r : display.roads) r.shineOff();
    }

    void allButtReset() {
        locationMarker = false;
        destinationMarker = false;
        updateSprites();
    }

    void updateSprites() {
        gui.buttons.get("LOCATION MARKER").setIcon(gui.buttonImages.get("LOCATION MARKER")[locationMarker ? 1 : 0]);
        gui.buttons.get("DESTINATION MARKER").setIcon(gui.buttonImages.get("DESTINATION MARKER")[destinationMarker ? 1 : 0]);
    }
}
