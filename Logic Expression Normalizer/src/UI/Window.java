package UI;

import javax.swing.*;
import Constants.WindowConstants;

public class Window extends JFrame {
    public Window(JPanel panel){
        this.setSize(WindowConstants.WINDOW_WIDTH,WindowConstants.WINDOW_HEIGHT);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setLocationRelativeTo(null);
        this.setLayout(null);
        this.setResizable(false);
        this.setIconImage(new ImageIcon("src/UI/image.png").getImage());
        this.add(panel);
        this.setVisible(true);
    }
}
