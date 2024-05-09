import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Random;

public class Display extends JPanel implements Runnable{
    Panel panel;
    int x = 0, y = 0, w = 800, h = 800;
    Thread thread;
    Random random;
    HashMap<Integer, Node> nodes;
    LinkedList<Road> roads;
    int curX, curY;
    int count;
    int temp = 0;
    boolean pressing = false;
    Node snapNode;
    LinkedList<String> backgrounds;
    int backgroundIndex = 1;

    Display(Panel panel) {
        this.panel = panel;
        panel.add(this);
        setBounds(x, y, w, h);

        thread = new Thread(this);
        thread.start();

        random = new Random();
        roads = new LinkedList<>();
        nodes = new HashMap<>();

        backgrounds = new LinkedList<>();
        for (int i = 0; i <= 7; i++) backgrounds.add("images/backgrounds/background (" + i + ").png");

        addMouseMotionListener(new MouseMotionListener() {
            @Override
            public void mouseDragged(MouseEvent e) {
                curX = e.getX();
                curY = e.getY();
            }

            @Override
            public void mouseMoved(MouseEvent e) {
                curX = e.getX();
                curY = e.getY();
            }
        });

        addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {

            }

            @Override
            public void mousePressed(MouseEvent e) {
                pressing = true;
                for (HashMap.Entry<Integer, Node> entry : nodes.entrySet()) {
                    Node n = entry.getValue();
                    if (dist(n.x, n.y,  curX,  curY) < (double) n.s / 2) {
                        snapNode = n;
                    }
                }
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                pressing = false;
                snapNode = null;
                if (nodes.get(gui.sp.location) != null && nodes.get(gui.sp.destination) != null) gui.sp.spa.shortestPath(nodes, gui.sp.location, gui.sp.destination, roads);
            }

            @Override
            public void mouseEntered(MouseEvent e) {

            }

            @Override
            public void mouseExited(MouseEvent e) {

            }
        });

        count = 1;
        nodes.put(count, new Node(count++, 200, 400, this));
        nodes.put(count, new Node(count++, 600, 400, this));
        roads.add(new Road(nodes, 1, 2, this));

        for (HashMap.Entry<Integer, Node> entry : nodes.entrySet()) System.out.println(entry.getValue().connections);
    }

    Gui gui;
    void addGui(Gui gui) {
        this.gui = gui;
    }

    public void run() {
        double drawInterval = (double) 1000000000 / 60;
        double nextDrawTime = System.nanoTime() + drawInterval;
        while (thread != null) {
            repaint();

            try {
                double remainingTime = (nextDrawTime - System.nanoTime()) / 1000000;
                if (remainingTime < 0) remainingTime = 0;
                Thread.sleep((long) remainingTime);
                nextDrawTime += drawInterval;
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D gg = (Graphics2D) g;

        gg.drawImage(new ImageIcon(backgrounds.get(backgroundIndex)).getImage(), 0, 0, getWidth(), getHeight(), null);

        for (Road r : roads) r.draw(gg);
        for (HashMap.Entry<Integer, Node> entry : nodes.entrySet()) entry.getValue().draw(gg);

        gui.sp.draw(gg);

        if (pressing) {
//            System.out.println("pressing " + temp++);
            if (snapNode != null) {
                snapNode.gotoXY(curX, curY);
//                System.out.println("snapping " + temp++);
            }
        }

        if (gui.rm.roadEditor) for (Road r : roads) r.drawDebug(gg);
        if (gui.logoPress) {
            gg.setFont(new Font("Timer New Roman", Font.BOLD, 22));
            gg.setColor(new Color(0x76000000, true));
            gg.fillRoundRect(0, 0, 800, 25, 10, 10);
            gg.setFont(new Font("Timer New Roman", Font.BOLD, 20));
            gg.setColor(new Color(0xEED152));
            gg.drawString(String.valueOf(gui.logoClicker), 10, 23);
        }

        gg.dispose();
    }

    double dist(double x1, double y1, double x2, double y2) {
        return Math.sqrt(Math.pow(x2 - x1, 2) + Math.pow(y2 - y1, 2));
    }
}
