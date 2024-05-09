import javax.swing.*;

public class Panel extends JPanel {
    Main frame;
    int width, height;
    Display display;
    Gui gui;

    Panel(Main frame, int width, int height) {
        frame.add(this);
        this.frame = frame;
        this.width = width;
        this.height = height;
        setBounds(0, 0, width, height);
        setLayout(null);

        display = new Display(this);
        gui = new Gui(this, display);
        display.addGui(gui);
    }
}
