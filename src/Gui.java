import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.HashMap;
import java.util.Objects;
import java.util.Random;

public class Gui extends JPanel {
    Panel panel;
    int x, y, w, h;
    JLabel logo;
    String[] buttonNames;
    int[][] buttonBounds;
    String[][] buttonSprites;
    HashMap<String, ImageIcon[]> buttonImages;
    HashMap<String, JLabel> buttons;
    ShortestPath sp;
    NodeMenu nm;
    RoadMenu rm;
    Random random;
    int[] logoBounds = {40, 40, 320, 280};
    int logoClicker = 0;
    boolean logoPress = false;

    Gui(Panel panel, Display display) {
        this.panel = panel;
        panel.add(this);
        setLayout(null);

        this.x = panel.display.w;
        this.y = 0;
        this.w = panel.width - panel.display.w;
        this.h = panel.height;
        setBounds(x, y, w, h);

        random = new Random();

        logo = new JLabel(new ImageIcon("images/necoarc.png"));
        logo.setBounds(logoBounds[0], logoBounds[1], logoBounds[2], logoBounds[3]);
        add(logo);
        logo.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                logoClicker++;
            }

            @Override
            public void mousePressed(MouseEvent e) {
                logoPress = true;
                logo.setIcon(new ImageIcon("images/necoarc.png"));
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                logoPress = false;
                logo.setIcon(new ImageIcon("images/necoarc2.png"));
            }

            @Override
            public void mouseEntered(MouseEvent e) {

            }

            @Override
            public void mouseExited(MouseEvent e) {

            }
        });

        sp = new ShortestPath(this, display);
        nm = new NodeMenu(this, display);
        rm = new RoadMenu(this, display);

        buttonsFun(display);
    }

    void buttonsFun(Display display) {
        // Instantiation
        {
            buttons = new HashMap<>();
            buttonNames = new String[]{
                    "NODE PEN",
                    "NODE ERASER",
                    "NODE EDITOR",
                    "ROAD PEN",
                    "ROAD ERASER",
                    "ROAD EDITOR",
                    "LOCATION MARKER",
                    "DESTINATION MARKER",
                    "CLEAR MARKERS",
                    "CHANGE BACKGROUND",
                    "CLEAR ALL",
                    "EXIT",
            };
            buttonBounds = new int[][]{
                    {40, 330},
                    {150, 330},
                    {260, 330},
                    {40, 440},
                    {150, 440},
                    {260, 440},
                    {40, 550},
                    {150, 550},
                    {260, 550},
                    {40, 660},
                    {150, 660},
                    {260, 660},
            };
            buttonSprites = new String[][]{
                    {"images/buttOff.png", "images/buttOn.png"},
                    {"images/buttOff.png", "images/buttOn.png"},
                    {"images/buttOff.png", "images/buttOn.png"},
                    {"images/buttonSprites/roadPenOn.png", "images/buttonSprites/roadPenOff.png"},
                    {"images/buttOff.png", "images/buttOn.png"},
                    {"images/buttOff.png", "images/buttOn.png"},
                    {"images/buttOff.png", "images/buttOn.png"},
                    {"images/buttOff.png", "images/buttOn.png"},
                    {"images/buttOff.png", "images/buttOn.png"},
                    {"images/buttOff.png", "images/buttOn.png"},
                    {"images/buttOff.png", "images/buttOn.png"},
                    {"images/buttOff.png", "images/buttOn.png"},
            };
            buttonImages = new HashMap<>();
            for (int i = 0; i < buttonNames.length; i++) {
                String s = buttonNames[i];
                buttonImages.put(s, new ImageIcon[]{
                        new ImageIcon(new ImageIcon(buttonSprites[i][0]).getImage().getScaledInstance(100, 100, Image.SCALE_SMOOTH)),
                        new ImageIcon(new ImageIcon(buttonSprites[i][1]).getImage().getScaledInstance(100, 100, Image.SCALE_SMOOTH))
                });
                buttons.put(s, new JLabel(buttonImages.get(s)[0]));
                buttons.get(s).setBounds(buttonBounds[i][0], buttonBounds[i][1], 100, 100);
                add(buttons.get(s));
            }
        }

        // Mouse Listener
        for (String s : buttonNames) {
            buttons.get(s).addMouseListener(new MouseListener() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    switch (s) {
                        case "NODE PEN" -> {
                            nm.nodePenToggle();
                            sp.allButtReset();
                            rm.allButtReset();
                        }
                        case "NODE ERASER" -> {
                            nm.nodeEraserToggle();
                            sp.allButtReset();
                            rm.allButtReset();
                        }
                        case "NODE EDITOR" -> {
                            nm.nodeEditorToggle();
                            sp.allButtReset();
                            rm.allButtReset();
                        }
                        case "ROAD PEN" -> {
                            rm.roadPenToggle();
                            sp.allButtReset();
                            nm.allButtReset();
                        }
                        case "ROAD ERASER" -> {
                            rm.roadEraserToggle();
                            sp.allButtReset();
                            nm.allButtReset();
                        }
                        case "ROAD EDITOR" -> {
                            rm.roadEditorToggle();
                        }
                        case "LOCATION MARKER" -> {
                            sp.locationMarkerToggle();
                            nm.allButtReset();
                            rm.allButtReset();
                        }
                        case "DESTINATION MARKER" -> {
                            sp.destinationMarkerToggle();
                            nm.allButtReset();
                            rm.allButtReset();
                        }
                        case "CLEAR MARKERS" -> {
                            sp.clearMarkers();
                            nm.allButtReset();
                            rm.allButtReset();
                        }
                        case "CHANGE BACKGROUND" -> {
                            display.backgroundIndex = (display.backgroundIndex + 1) % display.backgrounds.size();
                            nm.allButtReset();
                            sp.clearMarkers();
                            rm.allButtReset();
                        }
                        case "CLEAR ALL" -> {
                            display.roads.clear();
                            display.nodes.clear();
                            nm.allButtReset();
                            sp.clearMarkers();
                            rm.allButtReset();
                        }
                        case "EXIT" -> System.exit(0);
                    }
                }

                @Override
                public void mousePressed(MouseEvent e) {
                    if (!Objects.equals(s, "LOCATION MARKER") && !Objects.equals(s, "DESTINATION MARKER"))buttons.get(s).setIcon(buttonImages.get(s)[1]);
                }

                @Override
                public void mouseReleased(MouseEvent e) {
                    if (!Objects.equals(s, "LOCATION MARKER") && !Objects.equals(s, "DESTINATION MARKER")) buttons.get(s).setIcon(buttonImages.get(s)[0]);
                }

                @Override
                public void mouseEntered(MouseEvent e) {

                }

                @Override
                public void mouseExited(MouseEvent e) {

                }
            });
        }
    }
}