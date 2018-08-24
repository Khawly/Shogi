import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;


public class promotedRook extends GamePiece implements MouseListener {
    private int posX;
    private int posY;
    private GameBoard board;
    private outlinePanel[] outline = new outlinePanel[36];
    private int clickCounter = 0;
    private boolean[] occupied = new boolean[4];
    private boolean[] occupiedByFriend = new boolean[4];
    private Component[] temp = new Component[4];
    private int occupiedX[] = new int[4];
    private int occupiedY[] = new int[4];
    private int panelsAdded[] = new int[4];

    public promotedRook(boolean isOpponent, int x, int y, GameBoard b)
    {
        super(isOpponent);
        posX = x;
        posY = y;

        board = b;
      //  initOutlinePanels();
        

        for(int i = 0; i < 4; ++i){
            temp[i] = new BlankPanel();
            occupied[i] = false;
            occupiedByFriend[i] = false;
            occupiedX[i] = posX;
            occupiedY[i] = posY;
            panelsAdded[i] = 7;
        }
        
        for(int i = 0; i < 8; i++) {
            if(((posX - i - 1) >= 0))
            {
                outline[i] = new outlinePanel(board, posX - i - 1, posY, this);
            }
            if(((posX + i + 1) < 9)) {
                outline[i + 8] = new outlinePanel(board, posX + i + 1, posY, this);
            }
            if(((posY + i + 1) < 9)) {
                outline[i + 16] = new outlinePanel(board, posX, posY + i + 1, this);
            }
            if(((posY - i - 1) >= 0)) {
                outline[i + 24] = new outlinePanel(board, posX, posY - i - 1, this);
            }
        }
       if(isOpponent)
        setName("rookOpponent");
       else
    	   setName("rook");
        super.addMouseListener(this);
    }

    public promotedRook(int x, int y, GameBoard b)
    {
    	 this(false, x, y, b);
    }

    public promotedRook(boolean isOpponent)
    {
        super(isOpponent);

        board = null;
        posX = 0;
        posY = 0;
        outline = null;
    }
    
    //creates outline panels
    private void initOutlinePanels()
    {
    	  for(int i = 0; i < 4; ++i){
              temp[i] = new BlankPanel();
              occupied[i] = false;
              occupiedByFriend[i] = false;
              occupiedX[i] = posX;
              occupiedY[i] = posY;
              panelsAdded[i] = 7;
          }


          for (int i = 0; i < 8; i++) {
              if (((posX - i - 1) >= 0) && ((posY - i - 1) >= 0)) {
                  outline[i] = new outlinePanel(board, posX - i - 1, posY - i - 1, this);
              }
              if (((posX + i + 1) < 9) && ((posY + i + 1) < 9)) {
                  outline[i + 8] = new outlinePanel(board, posX + i + 1, posY + i + 1, this);
              }
              if (((posX - i - 1) >= 0) && ((posY + i + 1) < 9)) {
                  outline[i + 16] = new outlinePanel(board, posX - i - 1, posY + i + 1, this);
              }
              if (((posX + i + 1) < 9) && ((posY - i - 1) >= 0)) {
                  outline[i + 24] = new outlinePanel(board, posX + i + 1, posY - i - 1, this);
              }
          }


    }


    public void setPos(int x, int y)
    {
        int oldX = posX;
        int oldY = posY;
        posX = x;
        posY = y;


        for (int i = 0; i < 4; ++i)
        {
            if(!temp[i].getName().equalsIgnoreCase("blankPanel") && posX == occupiedX[i] && posY == occupiedY[i]) {
                ReservoirPanel.addToRes(super.isOpponent, temp[i].getName());
                temp[i] = new BlankPanel();
            }
            else if(occupied[i]) {
                board.addPanel(occupiedX[i], occupiedY[i], temp[i]);
                temp[i] = new BlankPanel();
            }
        }

        try{
            removeOutlinePanels(oldX, oldY);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        

        for (int i = 0; i < 4; ++i)
        {
            occupiedByFriend[i] = false;
            occupied[i] = false;
        }

        for(int i = 0; i < 8; i++) {
            if(((posX - i - 1) >= 0))
            {
                outline[i] = new outlinePanel(board, posX - i - 1, posY, this);
            }
            if(((posX + i + 1) < 9)) {
                outline[i + 8] = new outlinePanel(board, posX + i + 1, posY, this);
            }
            if(((posY + i + 1) < 9)) {
            	outline[i + 16] = new outlinePanel(board, posX, posY + i + 1, this);
            }
            if(((posY - i - 1) >= 0)) {
            	 outline[i + 24] = new outlinePanel(board, posX, posY - i - 1, this);
            }
        }
    }
    @Override
    public void resetClickCounter() {
        clickCounter = 0;
    }

    @Override
    public void removeOutlinePanels(int x, int y) {

    	 for(int i = 0; i < 8; i++) {
             if(((x - i - 1) >= 0) && i < panelsAdded[0] + 1) {
                 board.removePanel(x - i - 1, y, outline[i]);
                 outline[i].removeCapture();
             }
             if(((x + i + 1) < 9) && i < panelsAdded[1] + 1) {
                 board.removePanel(x + i + 1, y, outline[i + 8] );
                 outline[i+8].removeCapture();
             }
             if(((y + i + 1) < 9) && i < panelsAdded[2] + 1) {
                 board.removePanel(x, y + i + 1, outline[i + 16]);
                 outline[i+16].removeCapture();
             }     
                 if(((y - i - 1) >= 0) && i < panelsAdded[3] + 1) {
                     board.removePanel(x, y - i - 1, outline[i + 24]);
                     outline[i+24].removeCapture();
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

        g2d.setColor(Color.RED);
        g2d.setFont(new Font("TimesRoman", Font.PLAIN, 30));
        g2d.drawString("R", 40, 50);

    }

    @Override
    public void mouseClicked(MouseEvent e) {


        if (isOpponent && !isPlayerTurn) {
            if (clickCounter > 0) {
                for (int i = 0; i < 8; i++) {
                    if (((posX - i - 1) >= 0) && i < panelsAdded[0] + 1) {
                        board.removePanel(posX - i - 1, posY, outline[i]);
                        outline[i].removeCapture();
                    }
                    if (((posX + i + 1) < 9) && i < panelsAdded[1] + 1) {
                        board.removePanel(posX + i + 1, posY, outline[i + 8]);
                        outline[i+8].removeCapture();
                    }
                    if (((posY + i + 1) < 9) && i < panelsAdded[2] + 1) {
                        board.removePanel(posX, posY + i + 1, outline[i + 16]);
                        outline[i+16].removeCapture();
                    }
                    if (((posY - i - 1) >= 0) && i < panelsAdded[3] + 1) {
                        board.removePanel(posX, posY - i - 1, outline[i + 24]);
                        outline[i+24].removeCapture();
                    }
                   
                }
                for (int i = 0; i < 4; ++i){
                    if (occupied[i]){
                        board.addPanel(occupiedX[i], occupiedY[i], temp[i]);
                        occupied[i] = false;
                        temp[i] = new BlankPanel();
                        occupiedByFriend[i] = false;
                    }
                    occupiedByFriend[i] = false;
                }
                clickCounter = 0;
            } else{
            for (int i = 0; i < 8; i++) {
                if (((posX - i - 1) >= 0) && !occupied[0] && !occupiedByFriend[0]) {
                    if(board.isPanelOccupied(posX - i - 1, posY) &&
                            !board.getPanelName(posX - i - 1, posY).equalsIgnoreCase("outline") &&
                            !board.getPanelName(posX - i - 1, posY).contains("Opponent")) {
                        System.out.println("panel occupied[0]");
                        temp[0] = board.getComponentInPanel(posX - i - 1, posY);
                        board.removePanel(posX - i - 1, posY, temp[0]);
                        board.addPanel(posX - i - 1, posY, outline[i]);
                        outline[i].drawCapture();
                        occupied[0] = true;
                        occupiedX[0] = posX - i - 1;
                        occupiedY[0] = posY;
                        panelsAdded[0] = i;
                    }
                    else if (board.isPanelOccupied(posX - i - 1, posY) &&
                            !board.getPanelName(posX - i - 1, posY).equalsIgnoreCase("outline") &&
                            board.getPanelName(posX - i - 1, posY).contains("Opponent")){
                        occupiedByFriend[0] = true;
                    }else
                    {
                        board.addPanel(posX - i - 1, posY, outline[i]);
                        panelsAdded[0] = i;
                    }
                }

                if (((posX + i + 1) < 9) && !occupied[1] && !occupiedByFriend[1]) {
                    if(board.isPanelOccupied(posX + i + 1, posY) &&
                            !board.getPanelName(posX + i + 1, posY).equalsIgnoreCase("outline") &&
                            !board.getPanelName(posX + i + 1, posY).contains("Opponent")) {
                        System.out.println("panel occupied[1]");
                        temp[1] = board.getComponentInPanel(posX + i + 1, posY);
                        board.removePanel(posX + i + 1, posY, temp[1]);
                        board.addPanel(posX + i + 1, posY, outline[i + 8]);
                        outline[i + 8].drawCapture();
                        occupied[1] = true;
                        occupiedX[1] = posX + i + 1;
                        occupiedY[1] = posY;
                        panelsAdded[1] = i;
                    } else if(board.isPanelOccupied(posX + i + 1, posY) &&
                            !board.getPanelName(posX + i + 1, posY).equalsIgnoreCase("outline") &&
                            board.getPanelName(posX + i + 1, posY).contains("Opponent")){
                        occupiedByFriend[1] = true;
                    } else
                    {
                        board.addPanel(posX + i + 1, posY, outline[i + 8]);
                        panelsAdded[1] = i;
                    }
                }

                if (((posY + i + 1) < 9) && !occupied[2] && !occupiedByFriend[2]) {
                    if(board.isPanelOccupied(posX, posY + i + 1) &&
                            !board.getPanelName(posX, posY + i + 1).equalsIgnoreCase("outline") &&
                            !board.getPanelName(posX, posY + i + 1).contains("Opponent")) {
                        System.out.println("panel occupied[2]");
                        temp[2] = board.getComponentInPanel(posX, posY + i + 1);
                        board.removePanel(posX , posY + i + 1, temp[2]);
                        board.addPanel(posX, posY + i + 1, outline[i + 16]);
                        outline[i + 16].drawCapture();
                        occupied[2] = true;
                        occupiedX[2] = posX;
                        occupiedY[2] = posY + i + 1;
                        panelsAdded[2] = i;
                    } else if(board.isPanelOccupied(posX, posY + i + 1) &&
                            !board.getPanelName(posX, posY + i + 1).equalsIgnoreCase("outline") &&
                            board.getPanelName(posX, posY + i + 1).contains("Opponent")){
                        occupiedByFriend[2] = true;
                    } else
                    {
                        board.addPanel(posX, posY + i + 1, outline[i + 16]);
                        panelsAdded[2] = i;
                    }
                }

                if (((posY - i - 1) >= 0) && !occupied[3] && !occupiedByFriend[3]) {
                    if(board.isPanelOccupied(posX, posY - i - 1) &&
                            !board.getPanelName(posX, posY - i - 1).equalsIgnoreCase("outline") &&
                            !board.getPanelName(posX, posY - i - 1).contains("Opponent")) {
                        System.out.println("panel occupied[3]");
                        temp[3] = board.getComponentInPanel(posX, posY - i - 1);
                        board.removePanel(posX, posY - i - 1, temp[3]);
                        board.addPanel(posX, posY - i - 1, outline[i + 24]);
                        outline[i + 24].drawCapture();
                        occupied[3] = true;
                        occupiedX[3] = posX;
                        occupiedY[3] = posY - i - 1;
                        panelsAdded[3] = i;
                    } else if(board.isPanelOccupied(posX, posY - i - 1) &&
                            !board.getPanelName(posX, posY - i - 1).equalsIgnoreCase("outline") &&
                            board.getPanelName(posX, posY - i - 1).contains("Opponent")){
                        occupiedByFriend[3] = true;
                    } else
                    {
                        board.addPanel(posX, posY - i - 1, outline[i + 24]);
                        panelsAdded[3] = i;
                    }
                }
            }
            ++clickCounter;

             }
         } else if(!isOpponent && isPlayerTurn){
         	if (clickCounter > 0) {
                 for (int i = 0; i < 8; i++) {
                     if (((posX - i - 1) >= 0) && i < panelsAdded[0] + 1) {
                         board.removePanel(posX - i - 1, posY, outline[i]);
                        outline[i].removeCapture();
                     }
                     if (((posX + i + 1) < 9) && i < panelsAdded[1] + 1) {
                         board.removePanel(posX + i + 1, posY, outline[i + 8]);
                         outline[i+8].removeCapture();
                     }
                     if (((posY + i + 1) < 9) && i < panelsAdded[2] + 1) {
                         board.removePanel(posX, posY + i + 1, outline[i + 16]);
                         outline[i+16].removeCapture();
                     }
                     if (((posY - i - 1) >= 0) && i < panelsAdded[3] + 1) {
                         board.removePanel(posX, posY - i - 1, outline[i + 24]);
                         outline[i+24].removeCapture();
                     }
                 }
                 
                 for (int i = 0; i < 4; ++i){
                     if (occupied[i]){
                         board.addPanel(occupiedX[i], occupiedY[i], temp[i]);
                         occupied[i] = false;
                         temp[i] = new BlankPanel();
                       
                     }
                     occupiedByFriend[i] = false;
                 }
                 clickCounter = 0;
                 
                 } else{
                 for (int i = 0; i < 8; i++) {
                     if (((posX - i - 1) >= 0) && !occupied[0] && !occupiedByFriend[0]) {
                         if(board.isPanelOccupied(posX - i - 1, posY) &&
                                 !board.getPanelName(posX - i - 1, posY).equalsIgnoreCase("outline") &&
                                 board.getPanelName(posX - i - 1, posY).contains("Opponent")) {
                             System.out.println("panel occupied[0]");
                             temp[0] = board.getComponentInPanel(posX - i - 1, posY);
                             board.removePanel(posX - i - 1, posY, temp[0]);
                             board.addPanel(posX - i - 1, posY, outline[i]);
                             outline[i].drawCapture();
                             occupied[0] = true;
                             occupiedX[0] = posX - i - 1;
                             occupiedY[0] = posY;
                             panelsAdded[0] = i;
                         } else if (board.isPanelOccupied(posX - i - 1, posY) &&
                                 !board.getPanelName(posX - i - 1, posY).equalsIgnoreCase("outline") &&
                                 !board.getPanelName(posX - i - 1, posY).contains("Opponent")){
                             occupiedByFriend[0] = true;
                         }else{
                             board.addPanel(posX - i - 1, posY, outline[i]);
                             panelsAdded[0] = i;
                         }
                     }
                     if (((posX + i + 1) < 9) && !occupied[1] && !occupiedByFriend[1]) {
                         if(board.isPanelOccupied(posX + i + 1, posY) &&
                                 !board.getPanelName(posX + i + 1, posY).equalsIgnoreCase("outline") &&
                                 board.getPanelName(posX + i + 1, posY).contains("Opponent")) {
                             System.out.println("panel occupied[1]");
                             temp[1] = board.getComponentInPanel(posX + i + 1, posY);
                             board.removePanel(posX + i + 1, posY, temp[1]);
                             board.addPanel(posX + i + 1, posY, outline[i + 8]);
                             outline[i + 8].drawCapture();
                             occupied[1] = true;
                             occupiedX[1] = posX + i + 1;
                             occupiedY[1] = posY;
                             panelsAdded[1] = i;
                         } else if(board.isPanelOccupied(posX + i + 1, posY ) &&
                                 !board.getPanelName(posX + i + 1, posY).equalsIgnoreCase("outline") &&
                                 !board.getPanelName(posX + i + 1, posY).contains("Opponent")){
                             occupiedByFriend[1] = true;
                         } else{
                             board.addPanel(posX + i + 1, posY, outline[i + 8]);
                             panelsAdded[1] = i;
                         }

                     }
                     if (((posY + i + 1) < 9) && !occupied[2] && !occupiedByFriend[2]) {
                         if(board.isPanelOccupied(posX, posY + i + 1) &&
                                 !board.getPanelName(posX, posY + i + 1).equalsIgnoreCase("outline") &&
                                 board.getPanelName(posX, posY + i + 1).contains("Opponent")) {
                             System.out.println("panel occupied[2]");
                             temp[2] = board.getComponentInPanel(posX, posY + i + 1);
                             board.removePanel(posX, posY + i + 1, temp[2]);
                             outline[i + 16].drawCapture();
                             board.addPanel(posX, posY + i + 1, outline[i + 16]);
                             occupied[2] = true;
                             occupiedX[2] = posX;
                             occupiedY[2] = posY + i + 1;
                             panelsAdded[2] = i;
                         } else if(board.isPanelOccupied(posX, posY + i + 1) &&
                                 !board.getPanelName(posX, posY + i + 1).equalsIgnoreCase("outline") &&
                                 !board.getPanelName(posX, posY + i + 1).contains("Opponent")){
                             occupiedByFriend[2] = true;
                         } else
                         {
                             board.addPanel(posX, posY + i + 1, outline[i + 16]);
                             panelsAdded[2] = i;
                         }
                     }
                     if (((posY - i - 1) >= 0) && !occupied[3] && !occupiedByFriend[3]) {
                         if(board.isPanelOccupied(posX, posY - i - 1) &&
                                 !board.getPanelName(posX, posY - i - 1).equalsIgnoreCase("outline") &&
                                 board.getPanelName(posX, posY - i - 1).contains("Opponent")) {
                             System.out.println("panel occupied[3]");
                             temp[3] = board.getComponentInPanel(posX, posY - i - 1);
                             board.removePanel(posX, posY - i - 1, temp[3]);
                             board.addPanel(posX, posY - i - 1, outline[i + 24]);
                             outline[i + 24].drawCapture();
                             occupied[3] = true;
                             occupiedX[3] = posX;
                             occupiedY[3] = posY - i - 1;
                             panelsAdded[3] = i;
                         } else if (board.isPanelOccupied(posX, posY - i - 1) &&
                                 !board.getPanelName(posX, posY - i - 1).equalsIgnoreCase("outline") &&
                                 !board.getPanelName(posX, posY - i - 1).contains("Opponent")){
                             occupiedByFriend[3] = true;
                         } else{
                             board.addPanel(posX, posY - i - 1, outline[i + 24]);
                             panelsAdded[3] = i;
                         }
                     }
                 }
                 ++clickCounter;
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
