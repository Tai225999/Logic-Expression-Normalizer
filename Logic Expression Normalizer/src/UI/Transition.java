package UI;

import Algorithm.QuinneMcCluskey;
import Algorithm.column.ColumnTable;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

public class Transition {
    public static int[] values;
    public static boolean calculateStyle;

    public Transition() {
    }

    public static void transitionToInputScreen(int numberOfVariables, JPanel panel) {
        panel.setVisible(false);
        Window window = (Window)SwingUtilities.getWindowAncestor(panel);
        window.getContentPane().remove(panel);
        window.add(new InputScreen(numberOfVariables));
    }

    public static void transitionToMainMenu(JPanel panel) {
        panel.setVisible(false);
        Window window = (Window)SwingUtilities.getWindowAncestor(panel);
        window.getContentPane().remove(panel);
        window.add(new MainMenu());
    }

    public static void transitionToOutputScreen(JPanel panel, int numOfBits) {
        QuinneMcCluskey session = new QuinneMcCluskey(numOfBits, values, calculateStyle);
        ColumnTable columns = session.performingAlgorithm();
        panel.setVisible(false);
        Window window = (Window)SwingUtilities.getWindowAncestor(panel);
        window.getContentPane().remove(panel);
        window.add(new OutputScreen(session, columns, calculateStyle));
    }
}
