import javax.swing.*;
import java.awt.*;
import java.util.Random;


//creates an empty panel
public class BlankPanel extends JPanel {
    public BlankPanel()
    {
        //setSize(100, 100);
        //System.out.println("X = " + this.getWidth() + "\nY = " + this.getHeight());
        setBackground(Color.LIGHT_GRAY);
        setLayout(new GridLayout(1,1));
        setName("blankPanel"); //sets name of panel
    }


    public void paintComponent(Graphics g)
    {
        super.paintComponent(g);

        setSize(100,100);



        validate();

    }

}
