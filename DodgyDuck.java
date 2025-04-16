import javax.swing.*;
import java.awt.*;

public class DodgyDuck extends JPanel {


    JPanel dodgyDuck = new JPanel();
    int windowheight = 100;
    int windowwidth = 100;

    DodgyDuck() {


        setPreferredSize(new Dimension(windowwidth,windowheight));
        dodgyDuck.setLayout(null);
        dodgyDuck.setLocation(0, 0);
        setBackground(Color.blue);


    }





}
