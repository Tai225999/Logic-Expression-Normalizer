package UI;

import Algorithm.column.ColumnTable;

import javax.swing.*;

public class Transition {
    public static int[] values;
    public static boolean calculateStyle;

    public static void transitionToInputScreen(int numberOfVariables, JPanel panel) {
        panel.setVisible(false);
        Window window = (Window) SwingUtilities.getWindowAncestor(panel);
        window.getContentPane().remove(panel);
        window.add(new InputScreen(numberOfVariables));
    }

    public static void transitionToMainMenu(JPanel panel) {
        panel.setVisible(false);
        Window window = (Window) SwingUtilities.getWindowAncestor(panel);
        window.getContentPane().remove(panel);
        window.add(new MainMenu());
    }

    public static void transitionToOutputScreen(JPanel panel, int numOfBits) {
        ColumnTable table = new ColumnTable(numOfBits, values);
        table.printPrimeImplicants();
        panel.setVisible(false);
        Window window = (Window) SwingUtilities.getWindowAncestor(panel);
        window.getContentPane().remove(panel);
        window.add(new OutputScreen(table));
    }
}
