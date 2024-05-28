package UI;

import javax.swing.*;
import static javax.swing.JOptionPane.showMessageDialog;

public class DescriptionPanel {
    private String description;
    private String title;

    public DescriptionPanel(){
        super();
        this.title = "Description";
        this.description = "This simple program normalize a logic expression to its canonical form using Quine-McCluskey" +
                " method. \nThe program can handle up to 4 variables. The user can input the expression in the form of a " +
                "truth table.\n The user can also view the steps taken by the program to normalize the expression.";

        showMessageDialog(null, this.description, this.title, JOptionPane.INFORMATION_MESSAGE);
    }
}
