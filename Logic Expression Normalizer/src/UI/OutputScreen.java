package UI;

import javax.swing.*;
import javax.swing.border.MatteBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class OutputScreen extends JPanel implements ActionListener {

    public OutputScreen() {
        super();
        this.setSize(Constants.WindowConstants.WINDOW_WIDTH, Constants.WindowConstants.WINDOW_HEIGHT);
        this.setBounds(0,0, Constants.WindowConstants.WINDOW_WIDTH, Constants.WindowConstants.WINDOW_HEIGHT);
        this.setLayout(null);

        //Setting up the up-left subpanel, which has a flowlayout, the width of 2/3 of the screen and the height of 3/4 of the screen
        JPanel upLeftPanel = new JPanel();
        upLeftPanel.setLayout(new FlowLayout());
        upLeftPanel.setBackground(Color.WHITE);
        upLeftPanel.setBorder(new MatteBorder(0, 0, 0, 1, Color.BLACK));


        upLeftPanel.setVisible(true);
        this.add(upLeftPanel);

        this.setVisible(true);

    }
    @Override
    public void actionPerformed(ActionEvent e) {

    }
}
