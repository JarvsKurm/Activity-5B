import java.awt.*;
import java.awt.event.*;

public class Key {
    double curX, curY;
    boolean w, a, s, d, space, left, right, up, down, esc, comma, period, lmb, rmb, mmb;
    boolean wL, aL, sL, dL, spaceL, leftL, rightL, upL, downL, escL, commaL, periodL, lmbL, rmbL, mmbL;
    int frameX, frameY;

    Key(Container c) {
        c.addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {
            }

            @Override
            public void keyPressed(KeyEvent e) {
                switch (e.getKeyCode()) {
                    case KeyEvent.VK_W -> w = true;
                    case KeyEvent.VK_A -> a = true;
                    case KeyEvent.VK_S -> s = true;
                    case KeyEvent.VK_D -> d = true;
                    case KeyEvent.VK_SPACE -> space = true;
                    case KeyEvent.VK_LEFT-> left = true;
                    case KeyEvent.VK_RIGHT -> right = true;
                    case KeyEvent.VK_UP -> up = true;
                    case KeyEvent.VK_DOWN-> down = true;
                    case KeyEvent.VK_ESCAPE -> esc = true;
                    case KeyEvent.VK_COMMA -> comma = true;
                    case KeyEvent.VK_PERIOD -> period = true;
                }
            }

            @Override
            public void keyReleased(KeyEvent e) {
                switch (e.getKeyCode()) {
                    case KeyEvent.VK_W -> w = false;
                    case KeyEvent.VK_A -> a = false;
                    case KeyEvent.VK_S -> s = false;
                    case KeyEvent.VK_D -> d = false;
                    case KeyEvent.VK_SPACE -> space = false;
                    case KeyEvent.VK_LEFT-> left = false;
                    case KeyEvent.VK_RIGHT -> right = false;
                    case KeyEvent.VK_UP -> up = false;
                    case KeyEvent.VK_DOWN-> down = false;
                    case KeyEvent.VK_ESCAPE -> esc = false;
                    case KeyEvent.VK_COMMA -> comma = false;
                    case KeyEvent.VK_PERIOD -> period = false;
                }
            }
        });

        c.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {

            }

            @Override
            public void mousePressed(MouseEvent e) {
                if (e.getButton() == MouseEvent.BUTTON1) {
                    lmb = true;
                }
                if (e.getButton() == MouseEvent.BUTTON2) {
                    mmb = true;
                }
                if (e.getButton() == MouseEvent.BUTTON3) {
                    rmb = true;
                }
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                if (e.getButton() == MouseEvent.BUTTON1) {
                    lmb = false;
                    lmbL = false;
                }
                if (e.getButton() == MouseEvent.BUTTON2) {
                    mmb = false;
                    mmbL = false;
                }
                if (e.getButton() == MouseEvent.BUTTON3) {
                    rmb = false;
                    rmbL = false;
                }
            }

            @Override
            public void mouseEntered(MouseEvent e) {

            }

            @Override
            public void mouseExited(MouseEvent e) {

            }
        });
    }

    double getCurXY(boolean x) {
        Point cursor = MouseInfo.getPointerInfo().getLocation();
        curX = cursor.getX();
        curY = cursor.getY();
        return (x ? curX : curY);
    }

    double getCurXY(boolean x, Container c) {
        frameX = c.getX();
        frameY = c.getY();

        Point cursor = MouseInfo.getPointerInfo().getLocation();
        curX = cursor.getX() - frameX - 8;
        curY = cursor.getY() - frameY - 31;
        return (x ? curX : curY);
    }

    void curToXY(double x, double y) {
        try {
            Robot r = new Robot();
            r.mouseMove((int) x, (int) y);
        } catch (AWTException e) {
            throw new RuntimeException(e);
        }
    }
}
