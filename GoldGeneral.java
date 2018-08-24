import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;


public class GoldGeneral extends GamePiece implements MouseListener {
    private int posX;
    private int posY;
    private GameBoard board;
    private outlinePanel[] outline = new outlinePanel[6];
    private int clickCounter = 0;
    private Component[] temp = new Component[6];
    private boolean[] occupied = new boolean[6];
    private int[] occupiedX = new int[6];
    private int[] occupiedY = new int [6];
    private boolean[] panelAdded = new boolean[6];


    public GoldGeneral(boolean isOpponent, int x, int y, GameBoard b)
    {
        super(isOpponent);
        posX = x;
        posY = y;
        board = b;

        //init arrays
        for (int i = 0; i < 6; ++i){
            temp[i] = new BlankPanel();
            occupied[i] = false;
            occupiedX[i] = 0;
            occupiedY[i] = 0;
            panelAdded[i] = false;
        }

        //init outline panels and set name
        if(isOpponent) {
            outline[0] = new outlinePanel(board, posX + 1, posY, this);
            outline[1] = new outlinePanel(board, posX + 1, posY + 1, this);
            outline[2] = new outlinePanel(board, posX + 1, posY - 1, this);
            outline[3] = new outlinePanel(board, posX, posY + 1, this);
            outline[4] = new outlinePanel(board, posX, posY - 1, this);
            outline[5] = new outlinePanel(board, posX - 1, posY, this);
            setName("GoldGeneralOpponent");
        }else{
            outline[0] = new outlinePanel(board, posX - 1, posY, this);
            outline[1] = new outlinePanel(board, posX - 1, posY + 1, this);
            outline[2] = new outlinePanel(board, posX - 1, posY - 1, this);
            outline[3] = new outlinePanel(board, posX, posY + 1, this);
            outline[4] = new outlinePanel(board, posX, posY - 1, this);
            outline[5] = new outlinePanel(board, posX + 1, posY, this);
            setName("GoldGeneral");

        }

            super.addMouseListener(this);
    }

    public GoldGeneral(int x, int y, GameBoard b)
    {
        this(false, x, y, b);
    }

    public GoldGeneral(boolean isOpponent)
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


	    for (int i = 0; i < 6; ++i){
	    	//adds to reservoir panel if captured
		    if (!temp[i].getName().equalsIgnoreCase("blankPanel") && posX == occupiedX[i] && posY == occupiedY[i]){
			    ReservoirPanel.addToRes(isOpponent, temp[i].getName());
			    temp[i] = new BlankPanel();
			    occupied[i] = false;
		    }
		    //add uncaptured panels back
		    else if (occupied[i]){
			    board.addPanel(occupiedX[i], occupiedY[i], temp[i]);
			    temp[i] = new BlankPanel();

		    }
	    }

	    //remove outline panels
        try{
            removeOutlinePanels(oldX, oldY);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

        //reset outline panels
        if(super.isOpponent)
        {
            outline[0] = new outlinePanel(board, posX + 1, posY, this);
            outline[1] = new outlinePanel(board, posX + 1, posY + 1, this);
            outline[2] = new outlinePanel(board, posX + 1, posY - 1, this);
            outline[3] = new outlinePanel(board, posX, posY + 1, this);
            outline[4] = new outlinePanel(board, posX, posY - 1, this);
            outline[5] = new outlinePanel(board, posX - 1, posY, this);
        }

       else{
        outline[0] = new outlinePanel(board, posX - 1, posY, this);
        outline[1] = new outlinePanel(board, posX - 1, posY + 1, this);
        outline[2] = new outlinePanel(board, posX - 1, posY - 1, this);
        outline[3] = new outlinePanel(board, posX, posY + 1, this);
        outline[4] = new outlinePanel(board, posX, posY - 1, this);
        outline[5] = new outlinePanel(board, posX + 1, posY, this);
    }
        
    }

    @Override
    public void resetClickCounter() {
        clickCounter = 0;
    }

    //removes outline panels
    @Override
    public void removeOutlinePanels(int x, int y) {

        if(super.isOpponent)
        {
	        if(x + 1 < 9 && (panelAdded[0] || occupied[0])) {
		        board.removePanel(x + 1, y, outline[0]);
		        occupied[0] = false;
		        panelAdded[0] = false;
	        }
	        if((x + 1 < 9) && (y + 1 < 9) && (panelAdded[1] || occupied[1])) {
		        board.removePanel(x + 1, y + 1, outline[1]);
		        occupied[1] = false;
		        panelAdded[1] = false;
	        }
	        if((x + 1 < 9) && (y - 1 >= 0) && (panelAdded[2] || occupied[2])) {
		        board.removePanel(x + 1, y - 1, outline[2]);
		        occupied[2] = false;
		        panelAdded[2] = false;
	        }
	        if(y + 1 < 9 && (panelAdded[3] || occupied[3])) {
		        board.removePanel(x, y + 1, outline[3]);
		        occupied[3] = false;
		        panelAdded[3] = false;
	        }
	        if(y - 1 >= 0 && (panelAdded[4] || occupied[4])) {
		        board.removePanel(x, y - 1, outline[4]);
		        occupied[4] = false;
		        panelAdded[4] = false;
	        }
	        if(x - 1 >= 0 && (panelAdded[5] || occupied[5])) {
		        board.removePanel(x - 1, y, outline[5]);
		        occupied[5] = false;
		        panelAdded[5] = false;
	        }
        }

        else {
            if ((x - 1) >= 0 && (panelAdded[0] || occupied[0])) {
	            board.removePanel(x - 1, y, outline[0]);
	            occupied[0] = false;
	            panelAdded[0] = false;
            }
            if ((x - 1 >= 0) && (y + 1 < 9) && (panelAdded[1] || occupied[1])) {
	            board.removePanel(x - 1, y + 1, outline[1]);
	            occupied[1] = false;
	            panelAdded[1] = false;
            }
            if ((x - 1 >= 0) && (y - 1 >= 0) && (panelAdded[2] || occupied[2])) {
	            board.removePanel(x - 1, y - 1, outline[2]);
	            occupied[2] = false;
	            panelAdded[2] = false;
            }

            if (y + 1 < 9 && (panelAdded[3] || occupied[3])) {
            	board.removePanel(x, y + 1, outline[3]);
            	occupied[3] = false;
            	panelAdded[3] = false;
            }
            if (y - 1 >= 0 && (panelAdded[4] || occupied[4])) {
            	board.removePanel(x, y - 1, outline[4]);
            	occupied[4] = false;
            	panelAdded[4] = false;
            }
            if (x + 1 < 9 && (panelAdded[5] || occupied[5])) {
            	board.removePanel(x + 1, y, outline[5]);
            	occupied[5] = false;
            	panelAdded[5] = false;
            }
        }
        
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
        g2d.drawString("G", 40, 50);

    }

    @Override
    public void mouseClicked(MouseEvent e) {
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

    //moves piece
    private void move(){
        if(isOpponent && !isPlayerTurn){
            if (clickCounter > 0) {

                if(posX + 1 < 9 && (panelAdded[0] || occupied[0])) {
	                board.removePanel(posX + 1, posY, outline[0]);
	                outline[0].removeCapture();
                }
                if((posX + 1 < 9) && (posY + 1 < 9) && (panelAdded[1] || occupied[1])) {
	                board.removePanel(posX + 1, posY + 1, outline[1]);
	                outline[1].removeCapture();
                }
                if((posX + 1 < 9) && (posY - 1 >= 0) && (panelAdded[2] || occupied[2])) {
	                board.removePanel(posX + 1, posY - 1, outline[2]);
	                outline[2].removeCapture();
                }
                if(posY + 1 < 9 && (panelAdded[3] || occupied[3])) {
	                board.removePanel(posX, posY + 1, outline[3]);
	                outline[3].removeCapture();
                }
                if(posY - 1 >= 0 && (panelAdded[4] || occupied[4])) {
	                board.removePanel(posX, posY - 1, outline[4]);
	                outline[4].removeCapture();
                }
                if(posX - 1 >= 0 && (panelAdded[5] || occupied[5])) {
                	board.removePanel(posX - 1, posY, outline[5]);
	                outline[5].removeCapture();
                }
	            for(int i = 0; i < 6; ++ i){
		            if (occupied[i]){
			            board.addPanel(occupiedX[i], occupiedY[i], temp[i]);
			            occupied[i] = false;
			            temp[i] = new BlankPanel();
		            }
		            panelAdded[i] = false;
	            }

                clickCounter = 0;
            } else {
                if(posX + 1 < 9) {
                    if (board.isPanelOccupied(posX + 1, posY) &&
                            !board.getComponentInPanel(posX + 1, posY).getName().equalsIgnoreCase("outline") &&
                            !board.getComponentInPanel(posX + 1, posY).getName().contains("Opponent")) {
                        temp[0] = board.getComponentInPanel(posX + 1, posY);
                        board.removePanel(posX + 1, posY, temp[0]);
                        board.addPanel(posX + 1, posY, outline[0]);
                        outline[0].drawCapture();
                        occupied[0] = true;
                        occupiedX[0] = posX + 1;
                        occupiedY[0] = posY;
                    } else if (!board.isPanelOccupied(posX + 1, posY)) {
                        board.addPanel(posX + 1, posY, outline[0]);
                        panelAdded[0] = true;
                    }
                }
                if((posX + 1 < 9) && (posY + 1 < 9)) {
                    if (board.isPanelOccupied(posX + 1, posY + 1) &&
                            !board.getComponentInPanel(posX + 1, posY + 1).getName().equalsIgnoreCase("outline") &&
                            !board.getComponentInPanel(posX + 1, posY + 1).getName().contains("Opponent")) {
                        temp[1] = board.getComponentInPanel(posX + 1, posY + 1);
                        board.removePanel(posX + 1, posY + 1, temp[1]);
                        board.addPanel(posX + 1, posY + 1, outline[1]);
                        outline[1].drawCapture();
                        occupied[1] = true;
                        occupiedX[1] = posX + 1;
                        occupiedY[1] = posY + 1;
                    } else if (!board.isPanelOccupied(posX + 1, posY + 1)) {
                        board.addPanel(posX + 1, posY + 1, outline[1]);
                        panelAdded[1] = true;
                    }
                   // board.addPanel(posX + 1, posY + 1, outline[1]);
                }

                if((posX + 1 < 9) && (posY - 1 >= 0)) {
                    if (board.isPanelOccupied(posX + 1, posY - 1) &&
                            !board.getComponentInPanel(posX + 1, posY - 1).getName().equalsIgnoreCase("outline") &&
                            !board.getComponentInPanel(posX + 1, posY - 1).getName().contains("Opponent")) {
                        temp[2] = board.getComponentInPanel(posX + 1, posY - 1);
                        board.removePanel(posX + 1, posY - 1, temp[2]);
                        board.addPanel(posX + 1, posY - 1, outline[2]);
                        outline[2].drawCapture();
                        occupied[2] = true;
                        occupiedX[2] = posX + 1;
                        occupiedY[2] = posY - 1;
                    } else if (!board.isPanelOccupied(posX + 1, posY - 1)) {
                        board.addPanel(posX + 1, posY - 1, outline[2]);
                        panelAdded[2] = true;
                    }
                    //board.addPanel(posX + 1, posY - 1, outline[2]);
                }

                if(posY + 1 < 9){
                    if (board.isPanelOccupied(posX, posY + 1) &&
                            !board.getComponentInPanel(posX, posY + 1).getName().equalsIgnoreCase("outline") &&
                            !board.getComponentInPanel(posX, posY + 1).getName().contains("Opponent")) {
                        temp[3] = board.getComponentInPanel(posX, posY + 1);
                        board.removePanel(posX, posY + 1, temp[3]);
                        board.addPanel(posX, posY + 1, outline[3]);
                        outline[3].drawCapture();
                        occupied[3] = true;
                        occupiedX[3] = posX ;
                        occupiedY[3] = posY + 1;
                    } else if (!board.isPanelOccupied(posX, posY + 1)) {
                        board.addPanel(posX, posY + 1, outline[3]);
                        panelAdded[3] = true;
                    }
                    //board.addPanel(posX, posY + 1, outline[3]);

                }

                if(posY - 1 >= 0) {
                    if (board.isPanelOccupied(posX, posY - 1) &&
                            !board.getComponentInPanel(posX, posY - 1).getName().equalsIgnoreCase("outline") &&
                            !board.getComponentInPanel(posX, posY - 1).getName().contains("Opponent")) {
                        temp[4] = board.getComponentInPanel(posX, posY - 1);
                        board.removePanel(posX, posY - 1, temp[4]);
                        board.addPanel(posX, posY - 1, outline[4]);
                        outline[4].drawCapture();
                        occupied[4] = true;
                        occupiedX[4] = posX ;
                        occupiedY[4] = posY - 1;
                    } else if (!board.isPanelOccupied(posX, posY - 1)) {
                        board.addPanel(posX, posY - 1, outline[4]);
                        panelAdded[4] = true;
                    }
                    //board.addPanel(posX, posY - 1, outline[4]);
                }

                if(posX - 1 >= 0) {
                    if (board.isPanelOccupied(posX - 1, posY) &&
                            !board.getComponentInPanel(posX - 1, posY).getName().equalsIgnoreCase("outline") &&
                            !board.getComponentInPanel(posX - 1, posY).getName().contains("Opponent")) {
                        temp[5] = board.getComponentInPanel(posX - 1, posY);
                        board.removePanel(posX - 1, posY, temp[5]);
                        board.addPanel(posX - 1, posY, outline[5]);
                        outline[5].drawCapture();
                        occupied[5] = true;
                        occupiedX[5] = posX - 1;
                        occupiedY[5] = posY;
                    } else if (!board.isPanelOccupied(posX - 1, posY)) {
                        board.addPanel(posX - 1, posY, outline[5]);
                        panelAdded[5] = true;
                    }
                    //board.addPanel(posX - 1, posY, outline[5]);
                }

                ++clickCounter;
            }

        } else if (!isOpponent && isPlayerTurn){
            if (clickCounter > 0) {

                if(posX - 1 >= 0 && (panelAdded[0] || occupied[0])) {
	                board.removePanel(posX - 1, posY, outline[0]);
	                outline[0].removeCapture();
                }
                if((posX - 1 >= 0) && (posY + 1 < 9) && (panelAdded[1] || occupied[1])) {
	                board.removePanel(posX - 1, posY + 1, outline[1]);
	                outline[1].removeCapture();
                }
                if((posX - 1 >= 0) && (posY - 1 >= 0) && (panelAdded[2] || occupied[2])) {
	                board.removePanel(posX - 1, posY - 1, outline[2]);
	                outline[2].removeCapture();
                }
                if(posY + 1 < 9 && (panelAdded[3] || occupied[3])) {
	                board.removePanel(posX, posY + 1, outline[3]);
	                outline[3].removeCapture();
                }
                if(posY - 1 >= 0 && (panelAdded[4] || occupied[4])) {
	                board.removePanel(posX, posY - 1, outline[4]);
	                outline[4].removeCapture();
                }
                if(posX + 1 < 9 && (panelAdded[5] || occupied[5])) {
	                board.removePanel(posX + 1, posY, outline[5]);
	                outline[5].removeCapture();
                }
	            for(int i = 0; i < 6; ++ i){
		            if (occupied[i]){
			            board.addPanel(occupiedX[i], occupiedY[i], temp[i]);
			            occupied[i] = false;
			            temp[i] = new BlankPanel();
		            }
		            panelAdded[i] = false;
	            }

                clickCounter = 0;
            } else {
                if(posX - 1 >= 0) {
                    if (board.isPanelOccupied(posX - 1, posY) &&
                            !board.getComponentInPanel(posX - 1, posY).getName().equalsIgnoreCase("outline") &&
                            board.getComponentInPanel(posX - 1, posY).getName().contains("Opponent")) {
	                    System.out.println("panel down");
                    	temp[0] = board.getComponentInPanel(posX - 1, posY);
                        board.removePanel(posX - 1, posY, temp[0]);
                        board.addPanel(posX - 1, posY, outline[0]);
                        outline[0].drawCapture();
                        occupied[0] = true;
                        occupiedX[0] = posX - 1;
                        occupiedY[0] = posY;
                    } else if (!board.isPanelOccupied(posX - 1, posY)) {
                        board.addPanel(posX - 1, posY, outline[0]);
                        panelAdded[0] = true;
                    }
                }
                if((posX - 1 >= 0) && (posY + 1 < 9)) {
                    if (board.isPanelOccupied(posX - 1, posY + 1) &&
                            !board.getComponentInPanel(posX - 1, posY + 1).getName().equalsIgnoreCase("outline") &&
                            board.getComponentInPanel(posX - 1, posY + 1).getName().contains("Opponent")) {
	                    System.out.println("panel up right");
                        temp[1] = board.getComponentInPanel(posX - 1, posY + 1);
                        board.removePanel(posX - 1, posY + 1, temp[1]);
                        board.addPanel(posX - 1, posY + 1, outline[1]);
                        outline[1].drawCapture();
                        occupied[1] = true;
                        occupiedX[1] = posX - 1;
                        occupiedY[1] = posY + 1;
                    } else if (!board.isPanelOccupied(posX - 1, posY + 1)) {
                        board.addPanel(posX - 1, posY + 1, outline[1]);
                        panelAdded[1] = true;
                    }
                    // board.addPanel(posX + 1, posY + 1, outline[1]);
                }

                if((posX - 1 >= 0) && (posY - 1 >= 0)) {
                    if (board.isPanelOccupied(posX - 1, posY - 1) &&
                            !board.getComponentInPanel(posX - 1, posY - 1).getName().equalsIgnoreCase("outline") &&
                            board.getComponentInPanel(posX - 1, posY - 1).getName().contains("Opponent")) {
	                    System.out.println("panel up left");
                    	temp[2] = board.getComponentInPanel(posX - 1, posY - 1);
                        board.removePanel(posX - 1, posY - 1, temp[2]);
                        board.addPanel(posX - 1, posY - 1, outline[2]);
                        outline[2].drawCapture();
                        occupied[2] = true;
                        occupiedX[2] = posX - 1;
                        occupiedY[2] = posY - 1;
                    } else if (!board.isPanelOccupied(posX - 1, posY - 1)) {
                        board.addPanel(posX - 1, posY - 1, outline[2]);
                        panelAdded[2] = true;
                    }
                    //board.addPanel(posX + 1, posY - 1, outline[2]);
                }

                if(posY + 1 < 9){
                    if (board.isPanelOccupied(posX, posY + 1) &&
                            !board.getComponentInPanel(posX, posY + 1).getName().equalsIgnoreCase("outline") &&
                            board.getComponentInPanel(posX, posY + 1).getName().contains("Opponent")) {
	                    System.out.println("panel right");
                    	temp[3] = board.getComponentInPanel(posX, posY + 1);
                        board.removePanel(posX, posY + 1, temp[3]);
                        board.addPanel(posX, posY + 1, outline[3]);
                        outline[3].drawCapture();
                        occupied[3] = true;
                        occupiedX[3] = posX ;
                        occupiedY[3] = posY + 1;
                    } else if (!board.isPanelOccupied(posX, posY + 1)) {
                        board.addPanel(posX, posY + 1, outline[3]);
                        panelAdded[3] = true;
                    }
                    //board.addPanel(posX, posY + 1, outline[3]);

                }

                if(posY - 1 >= 0) {
                    if (board.isPanelOccupied(posX, posY - 1) &&
                            !board.getComponentInPanel(posX, posY - 1).getName().equalsIgnoreCase("outline") &&
                            board.getComponentInPanel(posX, posY - 1).getName().contains("Opponent")) {
                        System.out.println("panel left");
                    	temp[4] = board.getComponentInPanel(posX, posY - 1);
                    	System.out.println("temp 4 = " + temp[4].getName());
                        board.removePanel(posX, posY - 1, temp[4]);
                        board.addPanel(posX, posY - 1, outline[4]);
                        outline[4].drawCapture();
                        occupied[4] = true;
                        occupiedX[4] = posX ;
                        occupiedY[4] = posY - 1;
                    } else if (!board.isPanelOccupied(posX, posY - 1)) {
                        board.addPanel(posX, posY - 1, outline[4]);
                        panelAdded[4] = true;
                    }
                    //board.addPanel(posX, posY - 1, outline[4]);
                }

                if(posX + 1 < 9) {
                    if (board.isPanelOccupied(posX + 1, posY) &&
                            !board.getComponentInPanel(posX + 1, posY).getName().equalsIgnoreCase("outline") &&
                            board.getComponentInPanel(posX + 1, posY).getName().contains("Opponent")) {
                        temp[5] = board.getComponentInPanel(posX + 1, posY);
                        board.removePanel(posX + 1, posY, temp[5]);
                        board.addPanel(posX + 1, posY, outline[5]);
                        outline[5].drawCapture();
                        occupied[5] = true;
                        occupiedX[5] = posX + 1;
                        occupiedY[5] = posY;
                    } else if (!board.isPanelOccupied(posX + 1, posY)) {
                        board.addPanel(posX + 1, posY, outline[5]);
                        panelAdded[5] = true;
                    }
                    //board.addPanel(posX - 1, posY, outline[5]);
                }

                ++clickCounter;
            }
        }
    }
}
