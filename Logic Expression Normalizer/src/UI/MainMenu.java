package UI;

import Constants.WindowConstants;

import javax.swing.*;
import javax.swing.border.MatteBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MainMenu extends JPanel implements ActionListener {
    private JButton threeVariableExpressionButton;
    private JButton fourVariableExpressionButton;
    private JButton descriptionButton;
    private JButton exitButton;

    public MainMenu(){
        super();
        this.setSize(Constants.WindowConstants.WINDOW_WIDTH, Constants.WindowConstants.WINDOW_HEIGHT);
        this.setBounds(0,0, Constants.WindowConstants.WINDOW_WIDTH, Constants.WindowConstants.WINDOW_HEIGHT);
        this.setBackground(Color.LIGHT_GRAY);
        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        this.add(Box.createVerticalStrut(Constants.WindowConstants.WINDOW_HEIGHT/8));
        // Create a Box and add title to it
        Box titleBox = Box.createHorizontalBox();
        JLabel title = new JLabel("LOGIC EXPRESSION NORMALIZER");
        title.setFont(new Font("Arial", Font.BOLD, 70));
        titleBox.add(Box.createHorizontalGlue());
        titleBox.add(title);
        titleBox.add(Box.createHorizontalGlue());
        this.add(titleBox);

        this.add(Box.createVerticalStrut(Constants.WindowConstants.WINDOW_HEIGHT/8));

        JPanel menuPanel = new JPanel();
        menuPanel.setLayout(new BoxLayout(menuPanel, BoxLayout.Y_AXIS));
        menuPanel.setBackground(Color.WHITE);
        menuPanel.setPreferredSize(new Dimension(Constants.WindowConstants.WINDOW_WIDTH*2/7, Constants.WindowConstants.WINDOW_HEIGHT*3/4));

        // Add main menu label and align it to the center
        JLabel menuLabel = new JLabel("Main Menu");
        menuLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        menuLabel.setFont(new Font("Arial", Font.BOLD, 40));
        menuPanel.add(menuLabel);

        menuPanel.add(Box.createVerticalStrut(Constants.WindowConstants.WINDOW_HEIGHT/30));

        // Define a common size for all buttons
        Dimension buttonSize = new Dimension(Constants.WindowConstants.WINDOW_WIDTH/3, Constants.WindowConstants.WINDOW_HEIGHT/10);

        // Add a "Three Variable Expression" button
        threeVariableExpressionButton = createButton("Three Variable", buttonSize);
        menuPanel.add(threeVariableExpressionButton);
        menuPanel.add(Box.createVerticalStrut(10));

        // Add a "Four Variable Expression" button
        fourVariableExpressionButton = createButton("Four Variable", buttonSize);
        menuPanel.add(fourVariableExpressionButton);
        menuPanel.add(Box.createVerticalStrut(10));

        // Add a "Description" button
        descriptionButton = createButton("Description", buttonSize);
        menuPanel.add(descriptionButton);
        menuPanel.add(Box.createVerticalStrut(10));

        // Add an "Exit" button
        exitButton = createButton("Exit", buttonSize);
        menuPanel.add(exitButton);

        menuPanel.add(Box.createVerticalStrut(WindowConstants.WINDOW_HEIGHT/30));

        menuPanel.setBorder(new MatteBorder(5, 5, 5, 5, Color.BLACK));

        // Create a Box and add menuPanel to it
        Box box = Box.createHorizontalBox();
        box.add(Box.createHorizontalGlue());
        box.add(menuPanel);
        box.add(Box.createHorizontalGlue());

        // Add the Box to UI.MainMenu
        this.add(box);

        this.setVisible(true);
    }

    private JButton createButton(String text, Dimension size) {
        JButton button = new JButton(text);
        button.setAlignmentX(Component.CENTER_ALIGNMENT);
        button.setFont(new Font("Arial", Font.PLAIN, 30));
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

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource() == exitButton) {
            System.exit(0);
        }
        else if(e.getSource() == descriptionButton){
            new DescriptionPanel();
        }
        else if(e.getSource() == threeVariableExpressionButton){
            Transition.transitionToInputScreen(3, this);
        }
        else if(e.getSource() == fourVariableExpressionButton){
            Transition.transitionToInputScreen(4, this);
        }
    }

}