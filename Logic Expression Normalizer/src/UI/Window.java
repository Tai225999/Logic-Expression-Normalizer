package UI;
import javax.swing.*;
import java.awt.*;
import Constants.WindowConstants;

public class Window extends JFrame{
    public Window(){
        this.setSize(WindowConstants.WINDOW_WIDTH, WindowConstants.WINDOW_HEIGHT);
        this.setTitle(WindowConstants.WINDOW_TITLE);
        this.setVisible(true);
        this.setLayout(null);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);




        //Add Main Menu Title
        JLabel mainMenuTitle = new JLabel("LOGIC EXPRESSION NORMALIZER");
        mainMenuTitle.setFont(WindowConstants.mainMenuTitleFont);
        mainMenuTitle.setBounds(WindowConstants.WINDOW_WIDTH/4, WindowConstants.WINDOW_HEIGHT/8, WindowConstants.WINDOW_WIDTH, WindowConstants.WINDOW_HEIGHT/10);
        this.add(mainMenuTitle);

        JLabel member1 = new JLabel("Lo Duc Tai");
        member1.setFont(WindowConstants.memberFont);
        member1.setBounds(WindowConstants.WINDOW_WIDTH/4, WindowConstants.WINDOW_HEIGHT/8+40, WindowConstants.WINDOW_WIDTH, WindowConstants.WINDOW_HEIGHT/10);
        this.add(member1);

        JLabel member2 = new JLabel("Pham Quang Anh");
        member2.setFont(WindowConstants.memberFont);
        member2.setBounds(WindowConstants.WINDOW_WIDTH/2-50, WindowConstants.WINDOW_HEIGHT/8+40, WindowConstants.WINDOW_WIDTH, WindowConstants.WINDOW_HEIGHT/10);
        this.add(member2);

        JLabel member3 = new JLabel("Bui Dang Quy");
        member3.setFont(WindowConstants.memberFont);
        member3.setBounds(WindowConstants.WINDOW_WIDTH/4*3-65, WindowConstants.WINDOW_HEIGHT/8+40, WindowConstants.WINDOW_WIDTH, WindowConstants.WINDOW_HEIGHT/10);
        this.add(member3);

        //Add Buttons
        JButton button1 = new Button("Two Variables");
        button1.setBounds(WindowConstants.WINDOW_WIDTH/2-150, WindowConstants.WINDOW_HEIGHT/8+360, WindowConstants.WINDOW_WIDTH/4, WindowConstants.WINDOW_HEIGHT/10);
        this.add(button1);

        JButton button2 = new Button("Three Variables");
        button2.setBounds(WindowConstants.WINDOW_WIDTH/2-150, WindowConstants.WINDOW_HEIGHT/8+480, WindowConstants.WINDOW_WIDTH/4, WindowConstants.WINDOW_HEIGHT/10);
        this.add(button2);
    }
}
