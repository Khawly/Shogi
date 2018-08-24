import javax.swing.*;
import java.awt.*;
import java.security.PublicKey;

public class Shogi {
    private static GameBoard b;
    private static ReservoirPanel res;
    private static JFrame f;

    public static void main(String[] args)
    {
        f = new JFrame("SHOGI"); //create frame
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        f.setSize(1900, 1000);
        f.setLayout(new BoxLayout(f.getContentPane(), BoxLayout.X_AXIS));
        b = new GameBoard();
        f.add(b);
        res = new ReservoirPanel();
        f.add(res);
        f.validate();
        f.setVisible(true);
    }

    public static ReservoirPanel getResPanel()
    {
        return res;
    }

    public static GameBoard getGameBoard()
    {
        return b;
    }

    public static void NewGame(){
	    f.remove(b);
	    f.remove(res);
	    b = new GameBoard();
	    res = new ReservoirPanel();
	    f.add(b);
	    f.add(res);
	    f.validate();
        f.repaint();
    }

    public static void Exit(){
        f.dispose();
    }
}
