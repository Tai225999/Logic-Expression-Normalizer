package UI;

import Constants.WindowConstants;
import java.awt.Component;
import java.awt.LayoutManager;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class Window extends JFrame {
    public Window(JPanel panel) {
        this.setSize(WindowConstants.WINDOW_WIDTH, WindowConstants.WINDOW_HEIGHT);
        this.setDefaultCloseOperation(3);
        this.setLocationRelativeTo((Component)null);
        this.setLayout((LayoutManager)null);
        this.setResizable(false);
        this.setIconImage((new ImageIcon("src/UI/images.png")).getImage());
        this.add(panel);
        this.setVisible(true);
    }
}