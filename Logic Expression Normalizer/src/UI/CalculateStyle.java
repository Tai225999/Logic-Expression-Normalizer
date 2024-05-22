package UI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class CalculateStyle extends JDialog implements ActionListener {

    public CalculateStyle(){
        this.setSize(450, 160);
        this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        this.setLayout(new FlowLayout(FlowLayout.CENTER, 10, 10));
        this.setResizable(false);
        this.setModal(true);

        JButton sopButton = new JButton("SOP");
        sopButton.addActionListener(this);
        sopButton.setPreferredSize(new Dimension(200, 100));
        sopButton.setMinimumSize(new Dimension(200, 100));
        sopButton.setMaximumSize(new Dimension(200, 100));
        sopButton.setFont(new Font("Arial", Font.BOLD, 30));
        this.add(sopButton);

        JButton posButton = new JButton("POS");
        posButton.addActionListener(this);
        posButton.setPreferredSize(new Dimension(200, 100));
        posButton.setMinimumSize(new Dimension(200, 100));
        posButton.setMaximumSize(new Dimension(200, 100));
        posButton.setFont(new Font("Arial", Font.BOLD, 30));
        this.add(posButton);
        this.setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getActionCommand().equals("POS")) {
            System.out.println("POS");
            Transition.calculateStyle = false;
            this.dispose();

        } else if (e.getActionCommand().equals("SOP")) {
            System.out.println("SOP");
            Transition.calculateStyle = true;
            this.dispose();
        }
    }
}
