package UI;

import javax.swing.*;
import Constants.WindowConstants;

public class Button extends JButton {
    public Button(String title){
        super(title);
        this.setFont(WindowConstants.buttonFont);
    }
}
