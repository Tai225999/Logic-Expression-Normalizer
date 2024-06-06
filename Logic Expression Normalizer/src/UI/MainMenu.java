package UI;

import Constants.WindowConstants;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.MatteBorder;

public class MainMenu extends JPanel implements ActionListener {
    private JButton threeVariableExpressionButton;
    private JButton fourVariableExpressionButton;
    private JButton descriptionButton;
    private JButton exitButton;

    public MainMenu() {
        this.setSize(WindowConstants.WINDOW_WIDTH, WindowConstants.WINDOW_HEIGHT);
        this.setBounds(0, 0, WindowConstants.WINDOW_WIDTH, WindowConstants.WINDOW_HEIGHT);
        this.setBackground(Color.LIGHT_GRAY);
        this.setLayout(new BoxLayout(this, 1));
        this.add(Box.createVerticalStrut(WindowConstants.WINDOW_HEIGHT / 8));
        Box titleBox = Box.createHorizontalBox();
        JLabel title = new JLabel("LOGIC EXPRESSION NORMALIZER");
        title.setFont(new Font("Arial", 1, 50));
        titleBox.add(Box.createHorizontalGlue());
        titleBox.add(title);
        titleBox.add(Box.createHorizontalGlue());
        this.add(titleBox);
        this.add(Box.createVerticalStrut(WindowConstants.WINDOW_HEIGHT / 8));
        JPanel menuPanel = new JPanel();
        menuPanel.setLayout(new BoxLayout(menuPanel, 1));
        menuPanel.setBackground(Color.WHITE);
        menuPanel.setPreferredSize(new Dimension(WindowConstants.WINDOW_WIDTH * 2 / 7, WindowConstants.WINDOW_HEIGHT * 3 / 4));
        JLabel menuLabel = new JLabel("Main Menu");
        menuLabel.setAlignmentX(0.5F);
        menuLabel.setFont(new Font("Arial", 1, 40));
        menuPanel.add(menuLabel);
        menuPanel.add(Box.createVerticalStrut(WindowConstants.WINDOW_HEIGHT / 30));
        Dimension buttonSize = new Dimension(WindowConstants.WINDOW_WIDTH / 3, WindowConstants.WINDOW_HEIGHT / 10);
        this.threeVariableExpressionButton = this.createButton("Three Variable", buttonSize);
        menuPanel.add(this.threeVariableExpressionButton);
        menuPanel.add(Box.createVerticalStrut(10));
        this.fourVariableExpressionButton = this.createButton("Four Variable", buttonSize);
        menuPanel.add(this.fourVariableExpressionButton);
        menuPanel.add(Box.createVerticalStrut(10));
        this.descriptionButton = this.createButton("Description", buttonSize);
        menuPanel.add(this.descriptionButton);
        menuPanel.add(Box.createVerticalStrut(10));
        this.exitButton = this.createButton("Exit", buttonSize);
        menuPanel.add(this.exitButton);
        menuPanel.add(Box.createVerticalStrut(WindowConstants.WINDOW_HEIGHT / 30));
        menuPanel.setBorder(new MatteBorder(5, 5, 5, 5, Color.BLACK));
        Box box = Box.createHorizontalBox();
        box.add(Box.createHorizontalGlue());
        box.add(menuPanel);
        box.add(Box.createHorizontalGlue());
        this.add(box);
        this.setVisible(true);
    }

    private JButton createButton(String text, Dimension size) {
        JButton button = new JButton(text);
        button.setAlignmentX(0.5F);
        button.setFont(new Font("Arial", 0, 30));
        button.setFocusable(false);
        button.setMinimumSize(size);
        button.setMaximumSize(size);
        button.setPreferredSize(size);
        button.setBackground(Color.GRAY);
        button.setForeground(Color.WHITE);
        button.setBorder(new MatteBorder(2, 2, 2, 2, Color.BLACK));
        button.addActionListener(this);
        return button;
    }

    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == this.exitButton) {
            System.exit(0);
        } else if (e.getSource() == this.descriptionButton) {
            new DescriptionPanel();
        } else if (e.getSource() == this.threeVariableExpressionButton) {
            Transition.transitionToInputScreen(3, this);
        } else if (e.getSource() == this.fourVariableExpressionButton) {
            Transition.transitionToInputScreen(4, this);
        }

    }
}
