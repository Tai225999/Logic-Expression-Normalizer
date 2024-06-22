package UI;

import Constants.WindowConstants;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Arrays;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.LineBorder;
import javax.swing.border.MatteBorder;

public class InputScreen extends JPanel implements ActionListener {
    private JCheckBox[] valueBoxes;
    private final int numberOfVariables;
    private JButton submitButton;
    private JButton backButton;

    public InputScreen(int numberOfVariables) {
        this.numberOfVariables = numberOfVariables;
        this.setBackground(Color.LIGHT_GRAY);
        this.setBounds(0, 0, WindowConstants.WINDOW_WIDTH, WindowConstants.WINDOW_HEIGHT);
        this.setLayout(new BoxLayout(this, 0));

        JPanel choosingPanel = createChoosingPanel();
        this.add(choosingPanel);
        this.add(Box.createHorizontalStrut(WindowConstants.WINDOW_WIDTH / 10));

        JPanel buttonPanel = createButtonPanel();
        this.add(buttonPanel);

        this.setVisible(true);
    }

    private JPanel createChoosingPanel() {
        JPanel choosingPanel = new JPanel(new GridLayout((1 << numberOfVariables) + 1, numberOfVariables + 1, 10, 10));
        choosingPanel.setBackground(Color.WHITE);

        addLabelsToChoosingPanel(choosingPanel);
        addCheckBoxesToChoosingPanel(choosingPanel);

        choosingPanel.setPreferredSize(new Dimension(WindowConstants.WINDOW_WIDTH * 2 / 3, WindowConstants.WINDOW_HEIGHT * 3 / 4));
        choosingPanel.setMinimumSize(new Dimension(WindowConstants.WINDOW_WIDTH * 2 / 3, WindowConstants.WINDOW_HEIGHT * 3 / 4));
        choosingPanel.setMaximumSize(new Dimension(WindowConstants.WINDOW_WIDTH * 2 / 3, WindowConstants.WINDOW_HEIGHT * 3 / 4));
        choosingPanel.setBorder(new LineBorder(Color.BLACK));
        choosingPanel.setVisible(true);

        return choosingPanel;
    }

    private void addLabelsToChoosingPanel(JPanel choosingPanel) {
        for(int j = 0; j < numberOfVariables; ++j) {
            char i = (char) (65 + j);
            JLabel charHere = new JLabel("" + i, 0);
            charHere.setBorder(new LineBorder(Color.BLACK));
            choosingPanel.add(charHere);
        }

        choosingPanel.add(new JLabel("Y", 0));
    }

    private void addCheckBoxesToChoosingPanel(JPanel choosingPanel) {
        this.valueBoxes = new JCheckBox[1 << numberOfVariables];

        for(int j = 0; j < 1 << numberOfVariables; ++j) {
            addPositionLabelsToChoosingPanel(choosingPanel, j);
            addCheckBoxToChoosingPanel(choosingPanel, j);
        }
    }

    private void addPositionLabelsToChoosingPanel(JPanel choosingPanel, int j) {
        for(int i = 0; i < numberOfVariables; ++i) {
            int position = j >> numberOfVariables - i - 1 & 1;
            JLabel positionValue = new JLabel("" + position, 0);
            positionValue.setBorder(new LineBorder(Color.BLACK));
            choosingPanel.add(positionValue);
        }
    }

    private void addCheckBoxToChoosingPanel(JPanel choosingPanel, int j) {
        this.valueBoxes[j] = new JCheckBox();
        JPanel checkboxPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 1.0;
        gbc.weighty = 1.0;
        gbc.anchor = 10;
        checkboxPanel.add(this.valueBoxes[j], gbc);
        choosingPanel.add(checkboxPanel);
    }

    private JPanel createButtonPanel() {
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new BoxLayout(buttonPanel, 1));
        buttonPanel.setBackground(Color.LIGHT_GRAY);
        Dimension buttonSize = new Dimension(WindowConstants.WINDOW_WIDTH / 5, WindowConstants.WINDOW_HEIGHT / 10);

        this.submitButton = this.createButton("Submit", buttonSize);
        this.submitButton.setAlignmentY(0.0F);
        buttonPanel.add(this.submitButton);
        buttonPanel.add(Box.createHorizontalStrut(WindowConstants.WINDOW_HEIGHT / 16));

        this.backButton = this.createButton("Back", buttonSize);
        buttonPanel.add(this.backButton);

        buttonPanel.setPreferredSize(new Dimension(WindowConstants.WINDOW_WIDTH / 5, WindowConstants.WINDOW_HEIGHT / 4));
        buttonPanel.setMinimumSize(new Dimension(WindowConstants.WINDOW_WIDTH / 5, WindowConstants.WINDOW_HEIGHT / 4));
        buttonPanel.setMaximumSize(new Dimension(WindowConstants.WINDOW_WIDTH / 5, WindowConstants.WINDOW_HEIGHT / 4));
        buttonPanel.setVisible(true);

        return buttonPanel;
    }

    private JButton createButton(String text, Dimension size) {
        JButton button = new JButton(text);
        button.setAlignmentX(0.5F);
        button.setFont(new Font("Arial", 0, 30));
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

    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == this.backButton) {
            Transition.transitionToMainMenu(this);
        } else if (e.getSource() == this.submitButton) {
            int numOfVals = 0;

            for(int i = 0; i < this.valueBoxes.length; ++i) {
                if (this.valueBoxes[i].isSelected()) {
                    ++numOfVals;
                }
            }

            int[] values = new int[numOfVals];
            int index = 0;

            for(int i = 0; i < this.valueBoxes.length; ++i) {
                if (this.valueBoxes[i].isSelected()) {
                    values[index] = i;
                    ++index;
                }
            }

            System.out.println(Arrays.toString(values));
            Transition.values = values;
            new CalculateStyle();
            Transition.transitionToOutputScreen(this, this.numberOfVariables);
        }

    }
}