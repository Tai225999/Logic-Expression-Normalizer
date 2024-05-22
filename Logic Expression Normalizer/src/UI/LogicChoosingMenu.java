package UI;

import Constants.WindowConstants;
import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Arrays;

public class LogicChoosingMenu extends JPanel implements ActionListener{
    private JPanel choosingPanel;
    private JPanel buttonPanel;
    private final int variable_num;
    private JButton submitButton;
    private JButton returnButton;
    private JCheckBox[] valueBoxes;

    public LogicChoosingMenu(int variable_num){
        super();
        this.variable_num = variable_num;
        this.setBackground(Color.GRAY);

        choosingPanel = new JPanel(new GridLayout((1<<variable_num) + 1, variable_num+1, 10, 10));
        choosingPanel.setBackground(Color.WHITE);
        for (int i = 0; i < variable_num; i++) {
            char x = 'A';
            x += (char)i;
            JLabel charHere = new JLabel(x + "", SwingConstants.CENTER);
            charHere.setBorder(new LineBorder(Color.BLACK));
            choosingPanel.add(charHere);
        }
        choosingPanel.add(new JLabel("Y", SwingConstants.CENTER));
        valueBoxes = new JCheckBox[1 << variable_num];
        // Add binary values to the panel
        for (int j = 0; j < 1 << variable_num; j++) {
            for (int i = 0; i < variable_num; i++) {
                int position = (j >> (variable_num - i - 1)) & 1;
                JLabel positionValue = new JLabel(position + "", SwingConstants.CENTER);
                positionValue.setBorder(new LineBorder(Color.BLACK));
                choosingPanel.add(positionValue);
            }
            // Add the binary representation as a string
            valueBoxes[j] = new JCheckBox();
            JPanel checkboxPanel = new JPanel(new GridBagLayout());
            GridBagConstraints gbc = new GridBagConstraints();
            gbc.gridx = 0;
            gbc.gridy = 0;
            gbc.weightx = 1;
            gbc.weighty = 1;
            gbc.anchor = GridBagConstraints.CENTER;
            checkboxPanel.add(valueBoxes[j], gbc);
            choosingPanel.add(checkboxPanel);
        }
        choosingPanel.setBounds(WindowConstants.WINDOW_WIDTH/10,WindowConstants.WINDOW_HEIGHT/10, WindowConstants.CHOOSING_PANEL_WIDTH, WindowConstants.CHOOSING_PANEL_HEIGHT);
        this.add(choosingPanel);

        buttonPanel = new JPanel(new GridLayout(0, 1, 20, 30));
        // Add buttons to the button panel
        submitButton = new JButton("Submit");
        submitButton.setFocusable(false);
        submitButton.setFont(WindowConstants.buttonFont);
        submitButton.setBackground(Color.LIGHT_GRAY);
        submitButton.setBorder(new LineBorder(Color.BLACK));
        submitButton.addActionListener(this);
        buttonPanel.add(submitButton);

        // Place holders
        for (int i = 1; i <= 4; i++) {
            JButton button2 = new JButton("Button " + i);
            button2.setFocusable(false);
            button2.setFont(WindowConstants.buttonFont);
            button2.setBackground(Color.LIGHT_GRAY);
            button2.setBorder(new LineBorder(Color.BLACK));
            buttonPanel.add(button2);
        }

        returnButton = new JButton("Return");
        returnButton.setFocusable(false);
        returnButton.setFont(WindowConstants.buttonFont);
        returnButton.setBackground(Color.LIGHT_GRAY);
        returnButton.setBorder(new LineBorder(Color.BLACK));
        returnButton.addActionListener(this);
        buttonPanel.add(returnButton);

        this.add(buttonPanel);
        buttonPanel.setBackground(Color.WHITE);
        buttonPanel.setBounds(WindowConstants.WINDOW_WIDTH*7/10, WindowConstants.WINDOW_HEIGHT/10, WindowConstants.BUTTON_PANEL_WIDTH, WindowConstants.BUTTON_PANEL_HEIGHT);
        this.add(buttonPanel);

        choosingPanel.setVisible(true);
        buttonPanel.setVisible(true);
        this.setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource() == submitButton){
            revalidate();
            repaint();
            int[] values = new int[1 << variable_num];
            for(int i = 0; i < (1<<variable_num); i++){
                if(valueBoxes[i].isSelected()) values[i] = 1;
                else values[i] = 0;
            }

            System.out.println(Arrays.toString(values));
        }
        else if(e.getSource() == returnButton){

        }
    }
}
