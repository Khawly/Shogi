import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;


public class Lance extends GamePiece implements MouseListener {
    private int posX;
    private int posY;
    private GameBoard board;
    private outlinePanel[] outline = new outlinePanel[8];
    private int clickCounter = 0;
    private Component temp = new BlankPanel();
    private boolean occupied;
    private int occupiedX;
    private int panelsAdded;


    public Lance(boolean isOpponent, int x, int y, GameBoard b)
    {
        super(isOpponent);
        posX = x;
        posY = y;

        board = b;
        panelsAdded = 7;

        if(isOpponent) {
            for (int i = 0; i < 8; i++){
                outline[i] = new outlinePanel(board, posX + i + 1, posY, this);
            }

            setName("LanceOpponent");
        }
        else {
            for(int i = 0; i < 8; ++i)
            {
                outline[i] = new outlinePanel(board, posX - i - 1, posY, this);
            }
            setName("Lance");
        }
        super.addMouseListener(this);
    }

    public Lance(int x, int y, GameBoard b)
    {
        this(false, x, y, b);

    }

    public Lance(boolean isOpponent)
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

        if(!temp.getName().equalsIgnoreCase("blankPanel") && posX == occupiedX) {
            ReservoirPanel.addToRes(super.isOpponent, temp.getName());
            temp = new BlankPanel();
            occupied = false;
        }
        else if(occupied) {
            board.addPanel(occupiedX, posY, temp);
            temp = new BlankPanel();
            occupied = false;
        }

        try{
            removeOutlinePanels(oldX, oldY);
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        if(super.isOpponent)
        {
        	for(int i = 0; i < 8; i++)
        		outline[i] = new outlinePanel(board, posX + i + 1, posY, this);
        }
        	
        else if(!super.isOpponent){
            for(int i = 0; i < 8; i++)
                outline[i] = new outlinePanel(board, posX - i - 1, posY, this);
        }
    }

    @Override
    public void resetClickCounter() {
        clickCounter = 0;
    }

    @Override
    public void removeOutlinePanels(int x, int y) {
    	  
        if(super.isOpponent)
        {
        	for(int i = 0; i < panelsAdded + 1; i++)
        	{
        		if((x + i + 1) < 9)
        		board.removePanel(x + i + 1, y, outline[i]);
        	}
        }
    	
    	
        else if(!super.isOpponent){
            for(int i = 0; i < panelsAdded + 1; i++)
            {
                if((x - i - 1) >= 0)
                    board.removePanel(x - i - 1, y, outline[i]);
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
        g2d.drawString("L", 40, 50);

    }

    @Override
    public void mouseClicked(MouseEvent e) {
        /*
	    if(super.isOpponent){
    		
    		for(int i = 0; i < 8; i++)
    		{
    			if((posX + i + 1) < 9)
        		board.addPanel(posX + i + 1, posY, outline[i]);
    		}
    		   ++clickCounter;
    		   
    		   if (clickCounter > 1) {
    			   for(int i = 0; i < 8; i++)
    			   {
    				   if((posX + i + 1) < 9)
    			   board.removePanel(posX + i + 1, posY, outline[i]);
    			   }
    			   
    			   clickCounter = 0;
    		   }
    		   
    	}
    	
	    else if(!super.isOpponent){
        	if(posX - 1 >= 0)
            board.addPanel(posX - 1, posY, outline[0]);

        	if(posX - 2 >= 0)
            board.addPanel(posX - 2, posY, outline[1]);

        	if(posX - 3 >= 0)
            board.addPanel(posX - 3, posY, outline[2]);

        	if(posX - 4 >= 0)
            board.addPanel(posX - 4, posY, outline[3]);

        	if(posX - 5 >= 0)
            board.addPanel(posX - 5, posY, outline[4]);

        	if(posX - 6 >= 0)
            board.addPanel(posX - 6, posY, outline[5]);

        	if(posX - 7 >= 0)
            board.addPanel(posX - 7, posY, outline[6]);

        	if(posX - 8 >= 0)
            board.addPanel(posX - 8, posY, outline[7]);
        	
            ++clickCounter;
            
            if (clickCounter > 1) {
            	if(posX - 1 >= 0)
                board.removePanel(posX - 1, posY, outline[0]);

                if(posX - 2 >= 0)
                board.removePanel(posX - 2, posY, outline[1]);

                if(posX - 3 >= 0)
                board.removePanel(posX - 3, posY, outline[2]);

                if(posX - 4 >= 0)
                board.removePanel(posX - 4, posY, outline[3]);

                if(posX - 5 >= 0)
                board.removePanel(posX - 5, posY, outline[4]);

                if(posX - 6 >= 0)
                board.removePanel(posX - 6, posY, outline[5]);

                if(posX - 7 >= 0)
                board.removePanel(posX - 7, posY, outline[6]);

                if(posX - 8 >= 0)
                board.removePanel(posX - 8, posY, outline[7]);

                clickCounter = 0;
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

    private void move() {
        if(isOpponent && !isPlayerTurn){

            if (clickCounter > 0) {
                System.out.println("running click counter");
                for(int i = 0; i < panelsAdded + 1; i++) {
                    if((posX + i + 1) < 9) {
	                    board.removePanel(posX + i + 1, posY, outline[i]);
                        outline[i].removeCapture();
                    }
                }
                if(occupied)
                {
                    board.addPanel(occupiedX, posY, temp);
                    occupied = false;
                }
                clickCounter = 0;
            }
            else {
                for (int i = 0; i < 8; i++) {
                    if ((posX + i + 1) < 9) {
                        if (board.isPanelOccupied(posX + i + 1, posY) && !board.getPanelName(posX + i + 1, posY).equalsIgnoreCase("outline") && !board.getPanelName(posX + i + 1, posY).contains("Opponent")) {
                            System.out.println("panel occupied");
                            temp = board.getComponentInPanel(posX + i + 1, posY);
                            board.removePanel(posX + i + 1, posY, temp);
                            outline[i].drawCapture();
                            board.addPanel(posX + i + 1, posY, outline[i]);
                            occupied = true;
                            occupiedX = posX + i + 1;
                            panelsAdded = i;
                            i = 8;
                        } else if (board.isPanelOccupied(posX + i + 1, posY) && !board.getPanelName(posX + i + 1, posY).equalsIgnoreCase("outline") && board.getPanelName(posX + i + 1, posY).contains("Opponent")){
                            i = 8;

                        } else {
                            panelsAdded = i;
                            System.out.println("panel not occupied\nPanels added = " + panelsAdded);
                            board.addPanel(posX + i + 1, posY, outline[i]);
                        }
                    }
                }

                    ++clickCounter;
            }

        }

        else if (!isOpponent && isPlayerTurn){
            if (clickCounter > 0) {
                System.out.println("running click counter");
                for(int i = 0; i < panelsAdded + 1; i++)
                {
                    if((posX - i - 1) >= 0) {
	                    board.removePanel(posX - i - 1, posY, outline[i]);
	                    outline[i].removeCapture();
                    }
                }
                if(occupied)
                {
                    board.addPanel(occupiedX, posY, temp);
                    occupied = false;
                }

                clickCounter = 0;
            }
            else {
                for (int i = 0; i < 8; i++) {
                    if ((posX - i - 1) >= 0) {
                        if (board.isPanelOccupied(posX - i - 1, posY) && !board.getPanelName(posX - i - 1, posY).equalsIgnoreCase("outline") && board.getPanelName(posX - i - 1, posY).contains("Opponent")) {
                            System.out.println("panel occupied");
                            temp = board.getComponentInPanel(posX - i - 1, posY);
                            board.removePanel(posX - i - 1, posY, temp);
                            outline[i].drawCapture();
                            board.addPanel(posX - i - 1, posY, outline[i]);
                            occupied = true;
                            occupiedX = posX - i - 1;
                            panelsAdded = i;
                            i = 8;
                        } else if (board.isPanelOccupied(posX - i - 1, posY) && !board.getPanelName(posX - i - 1, posY).equalsIgnoreCase("outline") && !board.getPanelName(posX - i - 1, posY).contains("Opponent")){
                            i = 8;

                        }
                        else {
                            panelsAdded = i;
                            System.out.println("panel not occupied\nPanels added = " + panelsAdded);
                            board.addPanel(posX - i - 1, posY, outline[i]);
                        }
                    }
                }

                ++clickCounter;
            }

        }

    }
}
