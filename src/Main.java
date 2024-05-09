import javax.swing.*;

public class Main extends JFrame{
    Panel panel;
    int width = 1200, height = 800;

    Main() {
        setResizable(false);
        setSize(width + 16, height + 39);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        Main frame = this;
        panel = new Panel(frame, width, height);

        setVisible(true);
    }

    public static void main(String[] args) {
        new Main();
    }

}