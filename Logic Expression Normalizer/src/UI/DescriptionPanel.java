package UI;

import java.awt.Component;
import javax.swing.JOptionPane;

public class DescriptionPanel {
    private String description = "This simple program normalize a logic expression to its canonical form using Quine-McCluskey method. \nThe program can handle up to 4 variables. The user can input the expression in the form of a truth table.\n The user can also view the steps taken by the program to normalize the expression.";
    private String title = "Description";

    public DescriptionPanel() {
        JOptionPane.showMessageDialog((Component)null, this.description, this.title, 1);
    }
}