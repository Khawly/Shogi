import javax.swing.*;
import java.awt.*;
import java.awt.geom.GeneralPath;

public abstract class GamePiece extends JPanel {
    protected boolean isRotated;
    protected static boolean isPlayerTurn;
    protected boolean isOpponent;

    public GamePiece(boolean rotated)
    {
        isOpponent = rotated;
        isRotated = rotated;
        isPlayerTurn = true;
    }

    //abstract functions
    public abstract void setPos(int x, int y);
    public abstract void resetClickCounter();
    public abstract void removeOutlinePanels(int x, int y);

    //sets player turn
    public static void setPlayerTurn(boolean x)
    {
        isPlayerTurn = x;

    }
    //returns player turn
    public static boolean getPlayerTurn(){
        return isPlayerTurn;
    }

    public void paintComponent(Graphics g)
    {
        super.paintComponent(g);

        //draws piece
        Graphics2D g2 = (Graphics2D) g;
        GeneralPath piece = new GeneralPath();
        piece.moveTo(10, getHeight() - 10);
        piece.lineTo(10, getHeight()/4);
        piece.lineTo(getWidth()/2 , 10);
        piece.lineTo(getWidth() - 10, getHeight()/4);
        piece.lineTo(getWidth()-10, getHeight() -10);
        piece.closePath();

        if (isRotated)
        {
            g2.rotate(Math.PI, 98/2, 98/2);
        }
        g2.setColor(Color.ORANGE);
        g2.setStroke(new BasicStroke(3));
        g2.fill(piece);
    }
}
