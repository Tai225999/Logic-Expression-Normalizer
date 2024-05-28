package UI;

import Constants.WindowConstants;

import javax.swing.*;
import javax.swing.border.LineBorder;
import javax.swing.border.MatteBorder;
import java.awt.*;
import java.awt.event.*;
import java.util.Arrays;

public class InputScreen extends JPanel implements ActionListener{
    private JCheckBox[] valueBoxes;
    private final int numberOfVariables;
    private JButton submitButton;
    private JButton backButton;
    public InputScreen(int numberOfVariables){
        super();
        this.numberOfVariables = numberOfVariables;
        this.setBackground(Color.LIGHT_GRAY);
        this.setBounds(0,0, Constants.WindowConstants.WINDOW_WIDTH, Constants.WindowConstants.WINDOW_HEIGHT);
        this.setLayout(new BoxLayout(this, BoxLayout.X_AXIS));

        JPanel choosingPanel = new JPanel(new GridLayout((1<<numberOfVariables) + 1, numberOfVariables+1, 10, 10));
        choosingPanel.setBackground(Color.WHITE);
        for (int i = 0; i < numberOfVariables; i++) {
            char x = 'A';
            x += (char)i;
            JLabel charHere = new JLabel(x + "", SwingConstants.CENTER);
            charHere.setBorder(new LineBorder(Color.BLACK));
            choosingPanel.add(charHere);
        }
        choosingPanel.add(new JLabel("Y", SwingConstants.CENTER));
        valueBoxes = new JCheckBox[1 << numberOfVariables];
        // Add binary values to the panel
        for (int j = 0; j < 1 << numberOfVariables; j++) {
            for (int i = 0; i < numberOfVariables; i++) {
                int position = (j >> (numberOfVariables - i - 1)) & 1;
                JLabel positionValue = new JLabel(position + "", SwingConstants.CENTER);
                positionValue.setBorder(new LineBorder(Color.BLACK));
                choosingPanel.add(positionValue);
            }

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
        choosingPanel.setPreferredSize(new Dimension(Constants.WindowConstants.WINDOW_WIDTH*2/3, Constants.WindowConstants.WINDOW_HEIGHT*3/4));
        choosingPanel.setMinimumSize(new Dimension(Constants.WindowConstants.WINDOW_WIDTH*2/3, Constants.WindowConstants.WINDOW_HEIGHT*3/4));
        choosingPanel.setMaximumSize(new Dimension(Constants.WindowConstants.WINDOW_WIDTH*2/3, Constants.WindowConstants.WINDOW_HEIGHT*3/4));
        choosingPanel.setBorder(new LineBorder(Color.BLACK));
        choosingPanel.setVisible(true);
        this.add(choosingPanel);

        this.add(Box.createHorizontalStrut(Constants.WindowConstants.WINDOW_WIDTH/10));

        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.Y_AXIS));
        buttonPanel.setBackground(Color.LIGHT_GRAY);

        Dimension buttonSize = new Dimension(Constants.WindowConstants.WINDOW_WIDTH/5, Constants.WindowConstants.WINDOW_HEIGHT/10);

        submitButton = createButton("Submit", buttonSize);
        submitButton.setAlignmentY(Component.TOP_ALIGNMENT);
        buttonPanel.add(submitButton);

        buttonPanel.add(Box.createHorizontalStrut(Constants.WindowConstants.WINDOW_HEIGHT/16));

        backButton = createButton("Back", buttonSize);
        buttonPanel.add(backButton);

        buttonPanel.setPreferredSize(new Dimension(Constants.WindowConstants.WINDOW_WIDTH/5, Constants.WindowConstants.WINDOW_HEIGHT/4));
        buttonPanel.setMinimumSize(new Dimension(Constants.WindowConstants.WINDOW_WIDTH/5, Constants.WindowConstants.WINDOW_HEIGHT/4));
        buttonPanel.setMaximumSize(new Dimension(Constants.WindowConstants.WINDOW_WIDTH/5, WindowConstants.WINDOW_HEIGHT/4));

        buttonPanel.setVisible(true);
        this.add(buttonPanel);

        this.setVisible(true);

    }

    //Create button given text and size
    private JButton createButton(String text, Dimension size) {
        JButton button = new JButton(text);
        button.setAlignmentX(Component.CENTER_ALIGNMENT);
        button.setFont(new Font("Arial", Font.PLAIN, 30));
        button.setFocusable(false);
        button.setMinimumSize(size);
        button.setMaximumSize(size);
        button.setPreferredSize(size);
        button.setBackground(Color.BLACK);
        button.setForeground(Color.WHITE);
        button.setBorder(new MatteBorder(5, 5, 5, 5, Color.BLACK));
        button.addActionListener(this);
        return button;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource() == backButton){
            Transition.transitionToMainMenu(this);
        }
        else if(e.getSource() == submitButton){
            int numOfVals = 0;
            for(int i = 0; i < valueBoxes.length; i++){
                if(valueBoxes[i].isSelected()){
                    numOfVals++;
                }
            }
            int[] values = new int[numOfVals];
            int index = 0;
            for(int i = 0; i < valueBoxes.length; i++){
                if(valueBoxes[i].isSelected()){
                    values[index] = i;
                    index++;
                }
            }
            System.out.println(Arrays.toString(values));
            Transition.values = values;
            new CalculateStyle();
            Transition.transitionToOutputScreen(this, numberOfVariables);
        }
    }
}
