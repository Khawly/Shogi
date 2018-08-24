import javax.swing.*;
import java.awt.*;

public class ReservoirPanel extends JPanel {
    private static PlayerReservoirPanel opponent;
    private static PlayerReservoirPanel player;
    public ReservoirPanel()
    {
        setLayout(new GridLayout(3,1));
        opponent = new PlayerReservoirPanel(true);
        player = new PlayerReservoirPanel(false);
        add(opponent);
        add(new JLabel("Reservoir"));
        add(player);
        validate();
        setSize(600,900);
        validate();


    }

    //add piece to res
    public static void addToRes(boolean isOpponent, String panelName)
    {
        if(isOpponent)
            opponent.addToRes(panelName);
        else
            player.addToRes(panelName);

    }

    public void paintComponent(Graphics g)
    {
        super.paintComponent(g);




        setSize(600,900);
        validate();

    }
}

