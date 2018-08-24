import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;


public class Knight extends GamePiece implements MouseListener {
    private int posX;
    private int posY;
    private GameBoard board;
    private outlinePanel[] outline = new outlinePanel[2];
    private int clickCounter = 0;
    private Component[] temp = new Component[2];
    private boolean[] occupied = new boolean[2];
    private int[] occupiedX = new int[2];
    private int[] occupiedY = new int [2];
    private boolean leftPanelAdded = false;
    private boolean rightPanelAdded = false;

    public Knight(boolean isOpponent, int x, int y, GameBoard b)
    {
        super(isOpponent);
        posX = x;
        posY = y;

        board = b;

        for (int i = 0; i < 2; ++i){
            temp[i] = new BlankPanel();
            occupied[i] = false;
            occupiedX[i] = 0;
            occupiedY[i] = 0;
        }

        if(isOpponent) {
            outline[0] = new outlinePanel(board, posX + 2, posY + 1, this);
            outline[1] = new outlinePanel(board, posX + 2, posY - 1, this);
            setName("NightOpponent");
        } else {
            outline[0] = new outlinePanel(board, posX - 2, posY + 1, this);
            outline[1] = new outlinePanel(board, posX - 2, posY - 1, this);
            setName("Night");
        }

        super.addMouseListener(this);
    }

    public Knight(int x, int y, GameBoard b)
    {
        this(false, x, y, b);
    }

    public Knight(boolean isOpponent)
    {
        super(isOpponent);

        board = null;
        posX = 0;
        posY = 0;
        outline = null;
    }

    public void setPos(int x, int y)
    {
        int oldX = posX;
        int oldY = posY;
        posX = x;
        posY = y;

        for (int i = 0; i < 2; ++i){
            if (!temp[i].getName().equalsIgnoreCase("blankPanel") && posX == occupiedX[i] && posY == occupiedY[i]){
                ReservoirPanel.addToRes(isOpponent, temp[i].getName());
                temp[i] = new BlankPanel();
                occupied[i] = false;
            }
            else if (occupied[i]){
                board.addPanel(occupiedX[i], occupiedY[i], temp[i]);
                temp[i] = new BlankPanel();

            }
        }

        try {
            removeOutlinePanels(oldX, oldY);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }



        if(super.isOpponent)
        {
        	 outline[0] = new outlinePanel(board, posX + 2, posY + 1, this);
             outline[1] = new outlinePanel(board, posX + 2, posY - 1, this);
        } else{
            outline[0] = new outlinePanel(board, posX - 2, posY + 1, this);
            outline[1] = new outlinePanel(board, posX - 2, posY - 1, this);
        }
        //System.out.println("PosX = " + posX + "\nPosY = " + posY);
    }

    @Override
    public void resetClickCounter() {
        clickCounter = 0;
    }

    @Override
    public void removeOutlinePanels(int x, int y) {
    	
    	   if(super.isOpponent)
           {
    		   if((x + 2 < 9) && (y + 1 < 9) && (rightPanelAdded || occupied[0])) {
			       board.removePanel(x + 2, y + 1, outline[0]);
			       occupied[0] = false;
    		   }
    		    	if((x + 2 < 9) && (y - 1 >= 0) && (leftPanelAdded || occupied[1])) {
				        board.removePanel(x + 2, y - 1, outline[1]);
						occupied[1] = false;
			        }
           }
    	
    	   else{
    		   if((x - 2 >= 0) && (y + 1 < 9) && (rightPanelAdded || occupied[0])) {
			       board.removePanel(x - 2, y + 1, outline[0]);
			       occupied[0] = false;
		       }
    	
    		   if((x - 2 >= 0) && (y - 1 >= 0) && (leftPanelAdded || occupied[1])) {
			       board.removePanel(x - 2, y - 1, outline[1]);
			       occupied[1] = false;
		       }
    	   }

        leftPanelAdded = false;
        rightPanelAdded = false;

    }

    @Override
    public void paintComponent(Graphics g)
    {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        if (isRotated)
        {
            g2d.rotate(Math.PI, 98/2, 98/2);
        }

        g2d.setColor(Color.BLACK);
        g2d.setFont(new Font("TimesRoman", Font.PLAIN, 30));
        g2d.drawString("N", 40, 50);

    }

    @Override
    public void mouseClicked(MouseEvent e) {
    	
    	/*
    	if(super.isOpponent)
    	{
            if (clickCounter > 1) {
                if((posX + 2 < 9) && (posY + 1 < 9))
                    board.removePanel(posX + 2, posY + 1, outline[0]);

                if((posX + 2 < 9) && (posY - 1 >= 0))
                    board.removePanel(posX + 2, posY - 1, outline[1]);

                clickCounter = 0;

            } else {

                if ((posX + 2 < 9) && (posY + 1 < 9)) {
                    board.addPanel(posX + 2, posY + 1, outline[0]);
                }

                if ((posX + 2 < 9) && (posY - 1 >= 0)) {
                    board.addPanel(posX + 2, posY - 1, outline[1]);
                }

                ++clickCounter;
            }
    	}

    else{
            if (clickCounter > 1) {
                if((posX - 2 >= 0) && (posY + 1 < 9))
                    board.removePanel(posX - 2, posY + 1, outline[0]);

                if((posX - 2 >= 0) && (posY - 1 >= 0))
                    board.removePanel(posX - 2, posY - 1, outline[1]);

                clickCounter = 0;
            } else {

                if ((posX - 2 >= 0) && (posY + 1 < 9)) {
                    board.addPanel(posX - 2, posY + 1, outline[0]);
                }

                if ((posX - 2 >= 0) && (posY - 1 >= 0)) {
                    board.addPanel(posX - 2, posY - 1, outline[1]);
                }

                ++clickCounter;
            }
    	}
    	*/
    	move();
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

    private void move(){
        if(isOpponent && !isPlayerTurn)
        {
            if (clickCounter > 0) {
                if((posX + 2 < 9) && (posY + 1 < 9) && (rightPanelAdded || occupied[0])) {
                    board.removePanel(posX + 2, posY + 1, outline[0]);
                    outline[0].removeCapture();
                }
                if((posX + 2 < 9) && (posY - 1 >= 0) && (leftPanelAdded || occupied[1])) {
                    board.removePanel(posX + 2, posY - 1, outline[1]);
                    outline[1].removeCapture();
                }
                for(int i = 0; i < 2; ++ i){
                    if (occupied[i]){
                        board.addPanel(occupiedX[i], occupiedY[i], temp[i]);
                        occupied[i] = false;
                        temp[i] = new BlankPanel();
                    }
                }
                clickCounter = 0;

            } else {

                if ((posX + 2 < 9) && (posY + 1 < 9)) {
                    if (board.isPanelOccupied(posX + 2, posY + 1) &&
                            !board.getComponentInPanel(posX + 2, posY + 1).getName().equalsIgnoreCase("outline") &&
                            !board.getComponentInPanel(posX + 2, posY + 1).getName().contains("Opponent")){
                        temp[0] = board.getComponentInPanel(posX + 2, posY + 1);
                        board.removePanel(posX +2, posY + 1, temp[0]);
                        board.addPanel(posX + 2, posY + 1, outline[0]);
                        outline[0].drawCapture();
                        occupied[0] = true;
                        occupiedX[0] = posX + 2;
                        occupiedY[0] = posY + 1;
                    } else if (!board.isPanelOccupied(posX + 2, posY + 1)){
                        rightPanelAdded = true;
                        board.addPanel(posX + 2, posY + 1, outline[0]);
                    }
                }

                if ((posX + 2 < 9) && (posY - 1 >= 0)) {
                    if (board.isPanelOccupied(posX + 2, posY - 1) && !board.getComponentInPanel(posX + 2, posY - 1).getName().equalsIgnoreCase("outline") && !board.getComponentInPanel(posX + 2, posY - 1).getName().contains("Opponent")){
                        temp[1] = board.getComponentInPanel(posX + 2, posY - 1);
                        board.removePanel(posX +2, posY - 1, temp[1]);
                        board.addPanel(posX + 2, posY - 1, outline[1]);
                        outline[1].drawCapture();
                        occupied[1] = true;
                        occupiedX[1] = posX + 2;
                        occupiedY[1] = posY - 1;
                    } else if (!board.isPanelOccupied(posX + 2, posY - 1)){
                        leftPanelAdded = true;
                        board.addPanel(posX + 2, posY - 1, outline[1]);
                    }
                }

                ++clickCounter;
            }
        }

        else if (!isOpponent && isPlayerTurn){
            if (clickCounter > 0) {
                if((posX - 2 >= 0) && (posY + 1 < 9) && (rightPanelAdded || occupied[0]))
                    board.removePanel(posX - 2, posY + 1, outline[0]);

                if((posX - 2 >= 0) && (posY - 1 >= 0) && (leftPanelAdded || occupied[1]))
                    board.removePanel(posX - 2, posY - 1, outline[1]);

                for(int i = 0; i < 2; ++ i){
                    if (occupied[i]){
                        board.addPanel(occupiedX[i], occupiedY[i], temp[i]);
                        occupied[i] = false;
                        temp[i] = new BlankPanel();
                        leftPanelAdded = false;
                        rightPanelAdded = false;
                    }
                }

                clickCounter = 0;
            } else {

                if ((posX - 2 < 9) && (posY + 1 < 9)) {
                    if (board.isPanelOccupied(posX - 2, posY + 1) && !board.getComponentInPanel(posX - 2, posY + 1).getName().equalsIgnoreCase("outline") && board.getComponentInPanel(posX - 2, posY + 1).getName().contains("Opponent")){
                        temp[0] = board.getComponentInPanel(posX - 2, posY + 1);
                        board.removePanel(posX - 2, posY + 1, temp[0]);
                        board.addPanel(posX - 2, posY + 1, outline[0]);
                        outline[0].drawCapture();
                        occupied[0] = true;
                        occupiedX[0] = posX - 2;
                        occupiedY[0] = posY + 1;
                    } else if (!board.isPanelOccupied(posX - 2, posY + 1)){
                        rightPanelAdded = true;
                        board.addPanel(posX - 2, posY + 1, outline[0]);
                    }
                }

                if ((posX - 2 < 9) && (posY - 1 >= 0)) {
                    if (board.isPanelOccupied(posX - 2, posY - 1) && !board.getComponentInPanel(posX - 2, posY - 1).getName().equalsIgnoreCase("outline") && board.getComponentInPanel(posX - 2, posY - 1).getName().contains("Opponent")){
                        temp[1] = board.getComponentInPanel(posX - 2, posY - 1);
                        board.removePanel(posX - 2, posY - 1, temp[1]);
                        board.addPanel(posX - 2, posY - 1, outline[1]);
                        outline[1].drawCapture();
                        occupied[1] = true;
                        occupiedX[1] = posX - 2;
                        occupiedY[1] = posY - 1;
                    } else if (!board.isPanelOccupied(posX - 2, posY - 1)){
                        leftPanelAdded = true;
                        board.addPanel(posX - 2, posY - 1, outline[1]);
                    }
                }
                ++clickCounter;
            }


        }
    }
}
