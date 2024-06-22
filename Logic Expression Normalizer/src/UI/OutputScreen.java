package UI;

import algorithm.Minterm;
import algorithm.QuinneMcCluskey;
import algorithm.column.Column;
import algorithm.column.ColumnTable;
import Constants.WindowConstants;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.LayoutManager;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import javax.swing.*;
import javax.swing.border.MatteBorder;

public class OutputScreen extends JPanel implements ActionListener {
    public OutputScreen(QuinneMcCluskey session, ColumnTable columns, boolean calculateStyle) {
        this.setLayout((LayoutManager)null);
        this.setBackground(Color.GRAY);
        this.setBounds(0, 0, WindowConstants.WINDOW_WIDTH, WindowConstants.WINDOW_HEIGHT);

        createTitlePanel();
        createIntermediateColumns(session, columns);
        createPITable(columns, session);
        createMakeEquationPanel(session);
        createEquationPanel(session, calculateStyle);
        createReturnButton();
    }

    private void createReturnButton() {
        JButton returnButton = new JButton("Return");
        returnButton.setFont(new Font("Arial", 1, 40));
        returnButton.setBackground(Color.LIGHT_GRAY);
        returnButton.setBounds(WindowConstants.WINDOW_WIDTH * 65 / 100, WindowConstants.WINDOW_HEIGHT * 77 / 100, WindowConstants.WINDOW_WIDTH * 32 / 100, WindowConstants.WINDOW_HEIGHT * 15 / 100);
        returnButton.setBorder(new MatteBorder(5, 5, 5, 5, Color.BLACK));
        returnButton.addActionListener(this);
        this.add(returnButton);
        this.setVisible(true);
    }

    private void createEquationPanel(QuinneMcCluskey session, boolean calculateStyle) {
        JPanel rowPanel;
        rowPanel = new JPanel();
        rowPanel.setLayout(new BorderLayout());
        rowPanel.setBackground(Color.WHITE);
        String finalExpression = "Y = ";

        finalExpression += calculateStyle ? QuinneMcCluskey.toExpression(session.getFinalPIs()) : QuinneMcCluskey.convertToComplementForm(QuinneMcCluskey.toExpression(session.getFinalPIs()));

        JLabel leftLabel = new JLabel("Final Expression: ");
        leftLabel.setFont(new Font("Arial", 1, 25));
        rowPanel.add(leftLabel, "West");
        JLabel finalExpressionLabel = new JLabel(finalExpression);
        if(finalExpression.length() <= 22)
        finalExpressionLabel.setPreferredSize(new Dimension(WindowConstants.WINDOW_WIDTH * 5 / 20, WindowConstants.WINDOW_HEIGHT * 10 / 100));
        else finalExpressionLabel.setPreferredSize(new Dimension(WindowConstants.WINDOW_WIDTH * finalExpression.length()/70, WindowConstants.WINDOW_HEIGHT * 10 / 100));
        finalExpressionLabel.setFont(new Font("Arial", 1, 25));
        rowPanel.add(finalExpressionLabel, "Center");

        JButton logicCircuitButton = new JButton("Circuit");
        logicCircuitButton.setFont(new Font("Arial", 1, 25));
        logicCircuitButton.setBackground(Color.LIGHT_GRAY);
        rowPanel.add(logicCircuitButton, "East");
        logicCircuitButton.addActionListener(e -> {
            if (e.getActionCommand().equals("Circuit")) {
                Transition.openDiagram(session);
            }

        });
        if(finalExpression.equals("Y = 0") || finalExpression.equals("Y = 1") || finalExpression.equals("Y = (1)")) logicCircuitButton.setEnabled(false);

        JScrollPane finalExpressionScrollPane = new JScrollPane(rowPanel);
        finalExpressionScrollPane.setPreferredSize(new Dimension(WindowConstants.WINDOW_WIDTH * 7 / 12, WindowConstants.WINDOW_HEIGHT * 15 / 100));
        finalExpressionScrollPane.setMinimumSize(new Dimension(WindowConstants.WINDOW_WIDTH * 7 / 12, WindowConstants.WINDOW_HEIGHT * 15 / 100));
        finalExpressionScrollPane.setMaximumSize(new Dimension(WindowConstants.WINDOW_WIDTH * 7 / 12, WindowConstants.WINDOW_HEIGHT * 15 / 100));
        finalExpressionScrollPane.setBorder(new MatteBorder(5, 5, 5, 5, Color.BLACK));
        finalExpressionScrollPane.setBounds(WindowConstants.WINDOW_WIDTH * 3 / 100, WindowConstants.WINDOW_HEIGHT * 77 / 100, WindowConstants.WINDOW_WIDTH * 7 / 12, WindowConstants.WINDOW_HEIGHT * 15 / 100);
        this.add(finalExpressionScrollPane);
    }

    private void createMakeEquationPanel(QuinneMcCluskey session) {
        JPanel makeEquationPanel = new JPanel();
        makeEquationPanel.setLayout(new BorderLayout());
        makeEquationPanel.setBackground(Color.WHITE);
        makeEquationPanel.setBounds(WindowConstants.WINDOW_WIDTH * 65 / 100, WindowConstants.WINDOW_HEIGHT * 45 / 100, WindowConstants.WINDOW_WIDTH * 32 / 100, WindowConstants.WINDOW_HEIGHT * 7 / 25);
        makeEquationPanel.setBorder(new MatteBorder(5, 5, 5, 5, Color.BLACK));
        makeEquationPanel.setPreferredSize(new Dimension(WindowConstants.WINDOW_WIDTH * 32 / 100, WindowConstants.WINDOW_HEIGHT * 7 / 24));
        makeEquationPanel.setMinimumSize(new Dimension(WindowConstants.WINDOW_WIDTH * 32 / 100, WindowConstants.WINDOW_HEIGHT * 7 / 24));
        makeEquationPanel.setMaximumSize(new Dimension(WindowConstants.WINDOW_WIDTH * 32 / 100, WindowConstants.WINDOW_HEIGHT * 7 / 24));
        JLabel primeImplicantsLabel = new JLabel("Make Equation", 0);
        primeImplicantsLabel.setFont(new Font("Arial", 1, 15));
        makeEquationPanel.add(primeImplicantsLabel, "North");
        String[] columnNames = new String[]{"Integer Set", "Binary Representation", "Boolean Representation"};
        List<Minterm> primeImplicants = session.getFinalPIs();
        Object[][] data = new Object[primeImplicants.size()][3];

        for(int i = 0; i < primeImplicants.size(); ++i) {
            Minterm pi = (Minterm)primeImplicants.get(i);
            data[i][0] = pi.getValues().toString();
            data[i][1] = pi.getBinaryRepresentation();
            data[i][2] = pi.getBooleanRepresentation();
        }

        JTable table = new JTable(data, columnNames){
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        table.setFont(new Font("Arial", 0, 13));
        table.setRowHeight(30);
        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setPreferredSize(new Dimension(WindowConstants.WINDOW_WIDTH * 32 / 100, WindowConstants.WINDOW_HEIGHT * 7 / 24));
        scrollPane.setMinimumSize(new Dimension(WindowConstants.WINDOW_WIDTH * 32 / 100, WindowConstants.WINDOW_HEIGHT * 7 / 24));
        scrollPane.setMaximumSize(new Dimension(WindowConstants.WINDOW_WIDTH * 32 / 100, WindowConstants.WINDOW_HEIGHT * 7 / 24));
        makeEquationPanel.add(scrollPane, "Center");
        this.add(makeEquationPanel);
    }

    private void createPITable(ColumnTable columns, QuinneMcCluskey session) {
        //Add PI Panel, which contains the prime implicants table
        JPanel pIPanel = new JPanel();
        pIPanel.setLayout(new BorderLayout());
        pIPanel.setBackground(Color.WHITE);
        pIPanel.setBounds(WindowConstants.WINDOW_WIDTH * 65 / 100, WindowConstants.WINDOW_HEIGHT * 15 / 100, WindowConstants.WINDOW_WIDTH * 32 / 100, WindowConstants.WINDOW_HEIGHT * 7 / 24);
        pIPanel.setBorder(new MatteBorder(5, 5, 5, 5, Color.BLACK));

        List<Minterm> pIList = columns.getPrimeImplicantsFromEachColumn();
        System.out.println("This is pilist " + pIList);

        JLabel primeImplicantsLabel = new JLabel("Prime Implicants Table", 0);
        primeImplicantsLabel.setFont(new Font("Arial", 1, 15));
        pIPanel.add(primeImplicantsLabel, "North");

        int numOfRows = pIList.size();
        int numOfColumns = (int)Math.pow(2, Transition.numberOfVariables) + 1;
        JTable table = new JTable(numOfRows, numOfColumns){
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        table.setFont(new Font("Arial", 0, 13));
        table.setRowHeight(20);
        table.getColumnModel().getColumn(0).setPreferredWidth(Integer.MAX_VALUE);

        table.getColumnModel().getColumn(0).setHeaderValue("PI");
        for(int i = 1; i < numOfColumns; ++i) {
            table.getColumnModel().getColumn(i).setHeaderValue(String.valueOf(i - 1));
        }

        int maxPILength = 0;
        for(int i = 0; i < numOfRows; i++) {
            Minterm pi = pIList.get(i);
            String firstColumn = pi.getValues().toString();
            if (firstColumn.length() > maxPILength) maxPILength = firstColumn.length();
            if(session.getFinalPIs().contains(pi)) firstColumn += "*";
            table.setValueAt(firstColumn, i, 0);
            for(int j = 1; j < numOfColumns; ++j) {
                if(pi.getValues().contains(j-1)) {
                    table.setValueAt("x", i, j);
                }
            }
        }

        table.setFocusable(false);

        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setPreferredSize(new Dimension(WindowConstants.WINDOW_WIDTH * 32 / 100, WindowConstants.WINDOW_HEIGHT * 7 / 24));
        scrollPane.setMinimumSize(new Dimension(WindowConstants.WINDOW_WIDTH * 32 / 100, WindowConstants.WINDOW_HEIGHT * 7 / 24));
        scrollPane.setMaximumSize(new Dimension(WindowConstants.WINDOW_WIDTH * 32 / 100, WindowConstants.WINDOW_HEIGHT * 7 / 24));
        pIPanel.add(scrollPane, "Center");

        this.add(pIPanel);
    }

    private void createIntermediateColumns(QuinneMcCluskey session, ColumnTable columns) {
        JPanel columnTablePanel = new JPanel();
        FlowLayout layout = new FlowLayout( 1, 30, 0);
        layout.setAlignOnBaseline(true);
        columnTablePanel.setLayout(layout);
        columnTablePanel.setBackground(Color.WHITE);
        int columnTablePanelWidth = (columns.getNumOfColumns() >=4 ) ? WindowConstants.WINDOW_WIDTH/2*(columns.getNumOfColumns()-2) : WindowConstants.WINDOW_WIDTH * 13 / 24;
        int columnTablePanelHeight = Math.max(WindowConstants.WINDOW_HEIGHT*13/24, WindowConstants.WINDOW_HEIGHT * 13 * columns.getMaxLines() / 480);
        columnTablePanel.setPreferredSize(new Dimension(columnTablePanelWidth, columnTablePanelHeight));
        columnTablePanel.setBounds(WindowConstants.WINDOW_WIDTH * 3 / 100, WindowConstants.WINDOW_HEIGHT * 15 / 100, WindowConstants.WINDOW_WIDTH * 10 / 12, WindowConstants.WINDOW_HEIGHT * 10 / 12);
        JPanel rowPanel;
        JLabel leftLabel;
        if (session.getFinalPIs().isEmpty()) {
            JLabel emptyLabel = new JLabel("No prime implicants found");
            emptyLabel.setFont(new Font("Arial", 2, 30));
            columnTablePanel.add(emptyLabel);
            this.add(columnTablePanel);
        } else {
            int numberOfColumns = columns.getColumnList().size();
            JPanel[] columnPanels = new JPanel[numberOfColumns];
            int columnPanelWidth = columnTablePanelWidth / (numberOfColumns + 1);
            int columnPanelHeight = columnTablePanelHeight;
            for(int i = 0; i < numberOfColumns; ++i) {
                columnPanels[i] = new JPanel();
                columnPanels[i].setLayout(new BoxLayout(columnPanels[i], 1));
                columnPanels[i].setBackground(Color.WHITE);
                columnPanels[i].setPreferredSize(new Dimension(columnPanelWidth, columnPanelHeight));
                columnPanels[i].setMinimumSize(new Dimension(columnPanelWidth, columnPanelHeight));
                columnPanels[i].setMaximumSize(new Dimension(columnPanelWidth, columnPanelHeight));

                JLabel columnTitleLabel = new JLabel("Column " + (i + 1), 0);
                columnTitleLabel.setFont(new Font("Arial", 1, 15));
                JPanel columnTitlePanel = new JPanel(new FlowLayout(1));
                columnTitlePanel.setBackground(Color.WHITE);
                columnTitlePanel.add(columnTitleLabel);
                columnTitlePanel.setPreferredSize(new Dimension(columnPanelWidth, 20));
                columnTitlePanel.setMinimumSize(new Dimension(columnPanelWidth, 20));
                columnTitlePanel.setMaximumSize(new Dimension(columnPanelWidth, 20));
                columnTitlePanel.setVisible(true);
                columnPanels[i].add(columnTitlePanel);
                Column column = (Column) columns.getColumnList().get(i);
                List<List<Minterm>> currColumn = column.getTable();
                System.out.println(currColumn);

                for(int j = 0; j < currColumn.size(); ++j) {
                    rowPanel = new JPanel();
                    rowPanel.setLayout(new BoxLayout(rowPanel, 1));
                    rowPanel.setBackground(Color.WHITE);
                    int rowHeight;
                    JPanel innerPanel;
                    if (((List)currColumn.get(j)).isEmpty()) {
                        rowHeight = 20;
                        leftLabel = new JLabel("Empty");
                        leftLabel.setFont(new Font("Arial", 2, 10));
                        innerPanel = new JPanel(new FlowLayout());
                        innerPanel.setBackground(Color.WHITE);
                        innerPanel.add(leftLabel);
                        rowPanel.add(innerPanel);
                    } else {
                        rowHeight = 20 * currColumn.get(j).size();
                    }

                    rowPanel.setPreferredSize(new Dimension(columnPanelWidth, rowHeight));
                    rowPanel.setMinimumSize(new Dimension(columnPanelWidth, rowHeight));
                    rowPanel.setMaximumSize(new Dimension(columnPanelWidth, rowHeight));

                    for(int k = 0; k < ((List)currColumn.get(j)).size(); ++k) {
                        Minterm curr = currColumn.get(j).get(k);
                        innerPanel = new JPanel(new BorderLayout());
                        innerPanel.setBackground(Color.WHITE);
                        JLabel mintermLabel = new JLabel(curr.getValues().toString());
                        mintermLabel.setFont(new Font("Arial", 0, 11));
                        if(!columns.getPrimeImplicantsFromEachColumn().contains(curr)) mintermLabel.setForeground(Color.RED);
                        innerPanel.add(mintermLabel, "West");
                        JLabel binaryLabel = new JLabel(curr.getBinaryRepresentation());
                        if(!columns.getPrimeImplicantsFromEachColumn().contains(curr)) binaryLabel.setForeground(Color.RED);
                        binaryLabel.setFont(new Font("Arial", 0, 11));
                        innerPanel.add(binaryLabel, "East");
                        innerPanel.setPreferredSize(new Dimension(columnPanelWidth, 20));
                        rowPanel.add(innerPanel);
                    }

                    rowPanel.setBorder(new MatteBorder(2, 0, 0, 0, Color.BLACK));
                    rowPanel.setVisible(true);
                    columnPanels[i].add(rowPanel);
                }

                columnPanels[i].setVisible(true);
                columnTablePanel.add(columnPanels[i]);
            }
        }

        System.out.println(session.getFinalPIs());

        columnTablePanel.setVisible(true);
        JScrollPane columnScrollPane = new JScrollPane(columnTablePanel);
        columnScrollPane.setBounds(WindowConstants.WINDOW_WIDTH * 3 / 100, WindowConstants.WINDOW_HEIGHT * 15 / 100, WindowConstants.WINDOW_WIDTH * 7 / 12, WindowConstants.WINDOW_HEIGHT * 7 / 12);
        columnScrollPane.setPreferredSize(new Dimension(WindowConstants.WINDOW_WIDTH * 7 / 12, WindowConstants.WINDOW_HEIGHT * 7 / 12));
        columnScrollPane.setMinimumSize(new Dimension(WindowConstants.WINDOW_WIDTH * 7 / 12, WindowConstants.WINDOW_HEIGHT * 7 / 12));
        columnScrollPane.setMaximumSize(new Dimension(WindowConstants.WINDOW_WIDTH * 7 / 12, WindowConstants.WINDOW_HEIGHT * 7 / 12));
        columnScrollPane.setBorder(new MatteBorder(5, 5, 5, 5, Color.BLACK));
        columnScrollPane.setVisible(true);
        this.add(columnScrollPane);
    }

    private void createTitlePanel() {
        JLabel titleLabel = new JLabel("RESULTS", 0);
        titleLabel.setFont(new Font("Arial", 1, 50));
        titleLabel.setBackground(Color.WHITE);
        titleLabel.setOpaque(true);
        titleLabel.setBounds(WindowConstants.WINDOW_WIDTH * 3 / 100, WindowConstants.WINDOW_HEIGHT * 3 / 100, WindowConstants.WINDOW_WIDTH * 94 / 100, WindowConstants.WINDOW_HEIGHT * 10 / 100);
        titleLabel.setBorder(new MatteBorder(5, 5, 5, 5, Color.BLACK));
        this.add(titleLabel);
    }

    public void actionPerformed(ActionEvent e) {
        if (e.getActionCommand().equals("Return")) {
            this.setVisible(false);
            Transition.transitionToMainMenu(this);
        }

    }
}