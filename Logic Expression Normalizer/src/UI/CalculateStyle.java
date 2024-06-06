package UI;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JDialog;

public class CalculateStyle extends JDialog implements ActionListener {
    public CalculateStyle() {
        this.setSize(450, 160);
        this.setDefaultCloseOperation(2);
        this.setLayout(new FlowLayout(1, 10, 10));
        this.setResizable(false);
        this.setModal(true);
        this.setLocationRelativeTo((Component)null);
        JButton sopButton = new JButton("SOP");
        sopButton.addActionListener(this);
        sopButton.setBackground(Color.gray);
        sopButton.setForeground(Color.white);
        sopButton.setPreferredSize(new Dimension(200, 100));
        sopButton.setMinimumSize(new Dimension(200, 100));
        sopButton.setMaximumSize(new Dimension(200, 100));
        sopButton.setFont(new Font("Arial", 1, 30));
        this.add(sopButton);
        JButton posButton = new JButton("POS");
        posButton.addActionListener(this);
        posButton.setBackground(Color.gray);
        posButton.setForeground(Color.white);
        posButton.setPreferredSize(new Dimension(200, 100));
        posButton.setMinimumSize(new Dimension(200, 100));
        posButton.setMaximumSize(new Dimension(200, 100));
        posButton.setFont(new Font("Arial", 1, 30));
        this.add(posButton);
        this.setVisible(true);
    }

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