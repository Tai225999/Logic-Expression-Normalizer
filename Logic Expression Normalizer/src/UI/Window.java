package UI;

import javax.swing.*;

public class Window extends JFrame {
    public Window(JPanel panel){
        this.setSize(1280,800);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setLayout(null);
        this.setResizable(false);
        this.add(panel);
        this.setVisible(true);
    }
}
