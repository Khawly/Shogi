import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;


public class Pawn extends GamePiece implements MouseListener {
    private int posX;
    private int posY;
    private GameBoard board;
    private outlinePanel outline;
    private int clickCounter = 0;
    private Component temp = new BlankPanel();
    private boolean occupied;

    public Pawn(boolean isOpponent, int x, int y, GameBoard b) {
        super(isOpponent);
        posX = x;
        posY = y;

        board = b;

        occupied = false;

        super.addMouseListener(this);

        //set name and initialize outline panels
        if (isOpponent) {
            setName("PawnOpponent");
            outline = new outlinePanel(board, posX + 1, posY, this);
        } else {
            setName("Pawn");
            outline = new outlinePanel(board, posX - 1, posY, this);
        }


    }

    public Pawn(int x, int y, GameBoard b) {
        this(false, x, y, b);
    }

    public Pawn(boolean isOpponent) {
        super(isOpponent);

        board = null;
        posX = 0;
        posY = 0;
        outline = null;
    }

    public void setPos(int x, int y) {
        System.out.println("setPos invoked" + "temp name is " + temp.getName());
        posX = x;
        posY = y;
        occupied = false;
        if (!temp.getName().equalsIgnoreCase("blankPanel")) {
            ReservoirPanel.addToRes(super.isOpponent, temp.getName());
            temp = new BlankPanel();
        }
        if (!super.isOpponent)
            outline = new outlinePanel(board, posX - 1, posY, this);
        else if (super.isOpponent)
            outline = new outlinePanel(board, posX + 1, posY, this);
    }

    @Override
    public void resetClickCounter() {
        clickCounter = 0;
    }

    @Override
    public void removeOutlinePanels(int x, int y) {

    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        if (isRotated) {
            g2d.rotate(Math.PI, 98 / 2, 98 / 2);
        }

        g2d.setColor(Color.BLACK);
        g2d.setFont(new Font("TimesRoman", Font.PLAIN, 30));
        g2d.drawString("P", 40, 50);

    }

    @Override
    public void mouseClicked(MouseEvent e) {

        if (super.isOpponent && !isPlayerTurn) {
            //remove outline panels when clicked again
	        if (clickCounter > 0) {
		        System.out.println("running click counter");
		        if (occupied) {
			        board.addPanel(posX + 1, posY, temp);
			        occupied = false;
		        }
		        board.removePanel(posX + 1, posY, outline);
		        outline.removeCapture();
		        clickCounter = 0;
	        } else {
	        	//place outline panels
		        if (board.isPanelOccupied(posX + 1, posY) && !board.getPanelName(posX + 1, posY).equalsIgnoreCase("outline") && !board.getPanelName(posX + 1, posY).contains("Opponent")) {
			        System.out.println("panel occupied");
			        temp = board.getComponentInPanel(posX + 1, posY);
			        board.removePanel(posX + 1, posY, temp);
			        outline.setBackground(Color.ORANGE);
			        outline.drawCapture();
			        board.addPanel(posX + 1, posY, outline);
			        occupied = true;
		        } else if (!board.isPanelOccupied(posX + 1, posY)) {
			        System.out.println("panel not occupied");
			        board.addPanel(posX + 1, posY, outline);
		        }
		        ++clickCounter;
	        }
        } else if (isPlayerTurn && !isOpponent) {
            if (board.isPanelOccupied(posX - 1, posY) && !board.getPanelName(posX - 1, posY).equalsIgnoreCase("outline") && board.getPanelName(posX - 1, posY).contains("Opponent")) {
                System.out.println("panel occupied");
                temp = board.getComponentInPanel(posX - 1, posY);
                board.removePanel(posX - 1, posY, temp);
                outline.setBackground(Color.ORANGE);
                outline.drawCapture();
                board.addPanel(posX - 1, posY, outline);
                occupied = true;
            } else if (!board.isPanelOccupied(posX - 1, posY)) {
                System.out.println("panel not occupied");
                board.addPanel(posX - 1, posY, outline);
            }
            ++clickCounter;
            if (clickCounter > 1) {
                System.out.println("running click counter");
                if (occupied) {
                    board.addPanel(posX - 1, posY, temp);
                    occupied = false;
                }
                board.removePanel(posX - 1, posY, outline);
                outline.removeCapture();
                clickCounter = 0;
            }
        }

    }



    @Override
    public void mousePressed(MouseEvent e) {

    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mouseEntered(MouseEvent e) {


    }

    @Override
    public void mouseExited(MouseEvent e) {


    }

}
