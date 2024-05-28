package UI;

import Algorithm.column.ColumnTable;
import Constants.WindowConstants;

import javax.swing.*;
import javax.swing.border.MatteBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import Algorithm.column.Column;
import Algorithm.Minterm;

import java.util.List;

public class OutputScreen extends JPanel implements ActionListener {

    public OutputScreen(ColumnTable columns) {
        this.setLayout(null);
        this.setBackground(Color.GRAY);
        this.setBounds(0, 0, WindowConstants.WINDOW_WIDTH, WindowConstants.WINDOW_HEIGHT);

        //Add the title label to the output panel. which should be in the center of the horizontal line
        JLabel titleLabel = new JLabel("RESULTS", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 50));
        titleLabel.setBackground(Color.WHITE);
        titleLabel.setOpaque(true);
        titleLabel.setBounds(WindowConstants.WINDOW_WIDTH*3/100, WindowConstants.WINDOW_HEIGHT*3/100, WindowConstants.WINDOW_WIDTH*94/100, WindowConstants.WINDOW_HEIGHT*10/100);
        titleLabel.setBorder(new MatteBorder(5, 5, 5, 5, Color.BLACK));
        this.add(titleLabel);

        //Add the first sub-panel of the output panel, which is the column table.
        JPanel columnTablePanel = new JPanel();
        columnTablePanel.setLayout(new FlowLayout());
        columnTablePanel.setBackground(Color.WHITE);
        columnTablePanel.setPreferredSize(new Dimension(WindowConstants.WINDOW_WIDTH*7/12, WindowConstants.WINDOW_HEIGHT*7/12));
        columnTablePanel.setBounds(WindowConstants.WINDOW_WIDTH*3/100, WindowConstants.WINDOW_HEIGHT*15/100, WindowConstants.WINDOW_WIDTH*7/12, WindowConstants.WINDOW_HEIGHT*7/12);
        columnTablePanel.setBorder(new MatteBorder(5, 5, 5, 5, Color.BLACK));

        if(columns.getPrimeImplicants().isEmpty()){
            JLabel emptyLabel = new JLabel("No prime implicants found");
            emptyLabel.setFont(new Font("Arial", Font.ITALIC, 30));
            columnTablePanel.add(emptyLabel);
            this.add(columnTablePanel);
        }
        else {
            int numberOfColumns = columns.getColumnList().size();
            JPanel[] columnPanels = new JPanel[numberOfColumns];
            for (int i = 0; i < numberOfColumns; i++) {
                columnPanels[i] = new JPanel();
                columnPanels[i].setLayout(new BoxLayout(columnPanels[i], BoxLayout.Y_AXIS));
                columnPanels[i].setBackground(Color.WHITE);
                columnPanels[i].setPreferredSize(new Dimension(columnTablePanel.getWidth() / 4, columnTablePanel.getHeight() * 9 / 10));
                columnPanels[i].setMinimumSize(new Dimension(columnTablePanel.getWidth() / 4, columnTablePanel.getHeight() * 9 / 10));
                columnPanels[i].setMaximumSize(new Dimension(columnTablePanel.getWidth() / 4, columnTablePanel.getHeight() * 9 / 10));

                JLabel columnTitleLabel = new JLabel("Column " + (i + 1), SwingConstants.CENTER);
                columnTitleLabel.setFont(new Font("Arial", Font.BOLD, 30));

                // Create a new JPanel with FlowLayout and add the columnTitleLabel to it
                JPanel titlePanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 100, 0));
                titlePanel.setBackground(Color.WHITE);
                titlePanel.add(columnTitleLabel);
                // Set the size of the titlePanel to be fixed
                titlePanel.setPreferredSize(new Dimension(columnTablePanel.getWidth() / 4, 50));
                titlePanel.setMinimumSize(new Dimension(columnTablePanel.getWidth() / 4, 50));
                titlePanel.setMaximumSize(new Dimension(columnTablePanel.getWidth() / 4, 50));

                // Add the titlePanel to columnPanels[i]
                columnPanels[i].add(titlePanel);

                Column column = columns.getColumnList().get(i);
                List<List<Minterm>> currColumn = column.getTable();
                for (int j = 0; j < currColumn.size(); j++) {
                    JPanel rowPanel = new JPanel();
                    rowPanel.setLayout(new BoxLayout(rowPanel, BoxLayout.Y_AXIS));
                    rowPanel.setBackground(Color.WHITE);

                    // Set the size of the rowPanel to match the width of the columnPanels
                    int rowHeight;
                    if (currColumn.get(j).isEmpty()) {
                        rowHeight = columnPanels[i].getPreferredSize().height / 10;
                        JLabel emptyLabel = new JLabel("Empty");
                        emptyLabel.setFont(new Font("Arial", Font.ITALIC, 20));

                        // Create a new JPanel with FlowLayout and add the emptyLabel to it
                        JPanel emptyPanel = new JPanel(new FlowLayout());
                        emptyPanel.setBackground(Color.WHITE);
                        emptyPanel.add(emptyLabel);

                        // Add the emptyPanel to rowPanel
                        rowPanel.add(emptyPanel);
                    } else {
                        rowHeight = columnPanels[i].getPreferredSize().height / currColumn.size();
                    }
                    rowPanel.setPreferredSize(new Dimension(columnPanels[i].getPreferredSize().width, rowHeight));
                    rowPanel.setMinimumSize(new Dimension(columnPanels[i].getMinimumSize().width, rowHeight));
                    rowPanel.setMaximumSize(new Dimension(columnPanels[i].getMaximumSize().width, rowHeight));
                    for (int k = 0; k < currColumn.get(j).size(); k++) {
                        JPanel innerPanel = new JPanel(new BorderLayout());
                        innerPanel.setBackground(Color.WHITE);

                        JLabel mintermLabel = new JLabel(currColumn.get(j).get(k).getValues().toString());
                        mintermLabel.setFont(new Font("Arial", Font.PLAIN, 18));
                        innerPanel.add(mintermLabel, BorderLayout.WEST);

                        JLabel binaryLabel = new JLabel(currColumn.get(j).get(k).getBinaryRepresentation());
                        binaryLabel.setFont(new Font("Arial", Font.PLAIN, 18));
                        innerPanel.add(binaryLabel, BorderLayout.EAST);

                        rowPanel.add(innerPanel);
                    }

                    rowPanel.setBorder(new MatteBorder(5, 0, 0, 0, Color.BLACK));
                    rowPanel.setVisible(true);
                    columnPanels[i].add(rowPanel);
                }
                columnPanels[i].setVisible(true);
                columnTablePanel.add(columnPanels[i]);
            }
        }
        columnTablePanel.setVisible(true);
        this.add(columnTablePanel);

        //Add the second sub-panel of the output panel, which is the prime Implicants table.
        //This panel should be directly to the right of the first panel
        //The table should have a title, and a list of prime implicants, each row containing the prime implicant's integer set, its binary representation and its boolean representation
        JPanel primeImplicantsPanel = new JPanel();
        primeImplicantsPanel.setLayout(new BorderLayout());
        primeImplicantsPanel.setBackground(Color.WHITE);
        primeImplicantsPanel.setBounds(WindowConstants.WINDOW_WIDTH*65/100, WindowConstants.WINDOW_HEIGHT*15/100, WindowConstants.WINDOW_WIDTH*32/100, WindowConstants.WINDOW_HEIGHT*7/12);
        primeImplicantsPanel.setBorder(new MatteBorder(5, 5, 5, 5, Color.BLACK));

        primeImplicantsPanel.setPreferredSize(new Dimension(WindowConstants.WINDOW_WIDTH*32/100, WindowConstants.WINDOW_HEIGHT*7/12));
        primeImplicantsPanel.setMinimumSize(new Dimension(WindowConstants.WINDOW_WIDTH*32/100, WindowConstants.WINDOW_HEIGHT*7/12));
        primeImplicantsPanel.setMaximumSize(new Dimension(WindowConstants.WINDOW_WIDTH*32/100, WindowConstants.WINDOW_HEIGHT*7/12));

        JLabel primeImplicantsLabel = new JLabel("Prime Implicants Table", SwingConstants.CENTER);
        primeImplicantsLabel.setFont(new Font("Arial", Font.BOLD, 30));
        primeImplicantsPanel.add(primeImplicantsLabel, BorderLayout.NORTH);

        String[] columnNames = {"Integer Set", "Binary Representation", "Boolean Representation"};
        List<Minterm> primeImplicants = columns.getPrimeImplicants();

        Object[][] data = new Object[primeImplicants.size()][3];
        for (int i = 0; i < primeImplicants.size(); i++) {
            Minterm pi = primeImplicants.get(i);
            data[i][0] = pi.getValues().toString();
            data[i][1] = pi.getBinaryRepresentation();
            data[i][2] = pi.getBooleanRepresentation();
        }

        JTable table = new JTable(data, columnNames);
        table.setFont(new Font("Arial", Font.PLAIN, 20));
        table.setRowHeight(30);

        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setPreferredSize(new Dimension(WindowConstants.WINDOW_WIDTH*32/100, WindowConstants.WINDOW_HEIGHT*7/12));
        scrollPane.setMinimumSize(new Dimension(WindowConstants.WINDOW_WIDTH*32/100, WindowConstants.WINDOW_HEIGHT*7/12));
        scrollPane.setMaximumSize(new Dimension(WindowConstants.WINDOW_WIDTH*32/100, WindowConstants.WINDOW_HEIGHT*7/12));

        primeImplicantsPanel.add(scrollPane, BorderLayout.CENTER);

        this.add(primeImplicantsPanel);

        //Add the final Expression panel, which is directly below the first panel
        JPanel finalExpressionPanel = new JPanel();
        finalExpressionPanel.setLayout(new BorderLayout());
        finalExpressionPanel.setBackground(Color.WHITE);
        finalExpressionPanel.setBounds(WindowConstants.WINDOW_WIDTH*3/100, WindowConstants.WINDOW_HEIGHT*77/100, WindowConstants.WINDOW_WIDTH*7/12, WindowConstants.WINDOW_HEIGHT*15/100);
        finalExpressionPanel.setBorder(new MatteBorder(5, 5, 5, 5, Color.BLACK));

        String finalExpression = "Y = ";
        if(columns.getPrimeImplicants().isEmpty()){
            finalExpression += "0";
        }
        for (Minterm pi : columns.getPrimeImplicants()) {
            finalExpression += pi.getBooleanRepresentation() + " + ";
        }

        JLabel leftLabel = new JLabel("Final Expression: ");
        leftLabel.setFont(new Font("Arial", Font.BOLD, 30));
        finalExpressionPanel.add(leftLabel, BorderLayout.WEST);

        JLabel finalExpressionLabel = new JLabel(finalExpression.substring(0, finalExpression.length() - 3));
        finalExpressionLabel.setFont(new Font("Arial", Font.BOLD, 30));
        finalExpressionPanel.add(finalExpressionLabel, BorderLayout.CENTER);
        this.add(finalExpressionPanel);

        //Add the return button, which is directly below the second panel
        JButton returnButton = new JButton("Return");
        returnButton.setFont(new Font("Arial", Font.BOLD, 40));
        returnButton.setBackground(Color.LIGHT_GRAY);
        returnButton.setBounds(WindowConstants.WINDOW_WIDTH*65/100, WindowConstants.WINDOW_HEIGHT*77/100, WindowConstants.WINDOW_WIDTH*32/100, WindowConstants.WINDOW_HEIGHT*15/100);
        returnButton.setBorder(new MatteBorder(5, 5, 5, 5, Color.BLACK));
        returnButton.addActionListener(this);
        this.add(returnButton);

        this.setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getActionCommand().equals("Return")) {
            this.setVisible(false);
            Transition.transitionToMainMenu(this);
        }
    }
}
