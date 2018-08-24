import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;


public class Bishop extends GamePiece implements MouseListener {
    private int posX;
    private int posY;
    private GameBoard board;
    private outlinePanel[] outline = new outlinePanel[32];
    private int clickCounter = 0;
    private boolean[] occupied = new boolean[4];
    private boolean[] occupiedByFriend = new boolean[4];
    private Component[] temp = new Component[4];
    private int occupiedX[] = new int[4];
    private int occupiedY[] = new int[4];
    private int panelsAdded[] = new int[4];


    public Bishop(boolean isOpponent, int x, int y, GameBoard b) {
        super(isOpponent);
        posX = x;
        posY = y;

        board = b;

        //initialize arrays
        for(int i = 0; i < 4; ++i){
            temp[i] = new BlankPanel();
            occupied[i] = false;
            occupiedByFriend[i] = false;
            occupiedX[i] = posX;
            occupiedY[i] = posY;
            panelsAdded[i] = 7;
        }


        //initialize outline panels
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

        //set names
        if(isOpponent)
            setName("BishopOpponent");
        else
            setName("Bishop");

            super.addMouseListener(this);
    }

    public Bishop(int x, int y, GameBoard b) {
        this(false, x, y, b);
    }

    //use if you don't want mouse listener
    public Bishop(boolean isOpponent) {
        super(isOpponent);

        setName("Bishop");

        board = null;
        posX = 0;
        posY = 0;
        outline = null;
    }

    //sets new position of piece and removes outline panels
    public void setPos(int x, int y) {
        int oldX = posX;
        int oldY = posY;
        posX = x;
        posY = y;

        for (int i = 0; i < 4; ++i)
        {
            //add captured piece to reservoir
            if(!temp[i].getName().equalsIgnoreCase("blankPanel") && posX == occupiedX[i] && posY == occupiedY[i]) {
                ReservoirPanel.addToRes(super.isOpponent, temp[i].getName());
                temp[i] = new BlankPanel();
            }
            //add back uncaptured pieces
            else if(occupied[i]) {
                board.addPanel(occupiedX[i], occupiedY[i], temp[i]);
                temp[i] = new BlankPanel();
            }
        }

        //remove outline panels
        try {
            removeOutlinePanels(oldX, oldY);
        } catch (Exception e) {
            e.printStackTrace();
        }

        //reset occupied booleans
        for (int i = 0; i < 4; ++i)
        {
            occupiedByFriend[i] = false;
            occupied[i] = false;
        }


        //reinitialize outline panels to new position
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

    @Override
    public void resetClickCounter() {
        clickCounter = 0;
    }

    //removes outline panels
    @Override
    public void removeOutlinePanels(int x, int y) {

        for (int i = 0; i < 8; i++) {
            if (((x - i - 1) >= 0) && ((y - i - 1) >= 0) && i < panelsAdded[0] + 1) {
                board.removePanel(x - i - 1, y - i - 1, outline[i]);
            }
            if (((x + i + 1) < 9) && ((y + i + 1) < 9) && i < panelsAdded[1] + 1) {
                board.removePanel(x + i + 1, y + i + 1, outline[i + 8]);
            }
            if (((x - i - 1) >= 0) && ((y + i + 1) < 9) && i < panelsAdded[2] + 1) {
                board.removePanel(x - i - 1, y + i + 1, outline[i + 16]);
            }
            if (((x + i + 1) < 9) && ((y - i - 1) >= 0) && i < panelsAdded[3] + 1) {
                board.removePanel(x + i + 1, y - i - 1, outline[i + 24]);
            }
        }

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
        g2d.drawString("B", 40, 50);

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

    //moves the piece
    private void move(){
        if (isOpponent) {
        	//remove panels when clicked again
            if (clickCounter > 0) {
                for (int i = 0; i < 8; i++) {
                    if (((posX - i - 1) >= 0) && ((posY - i - 1) >= 0) && i < panelsAdded[0] + 1) {
                        System.out.println("panels added = " + panelsAdded[0] + 1);
                        board.removePanel(posX - i - 1, posY - i - 1, outline[i]);
                        outline[i].removeCapture();
                    }
                    if (((posX + i + 1) < 9) && ((posY + i + 1) < 9) && i < panelsAdded[1] + 1) {
                        System.out.println("panels added = " + panelsAdded[1] + 1);
                        board.removePanel(posX + i + 1, posY + i + 1, outline[i + 8]);
                        outline[i + 8].removeCapture();
                    }
                    if (((posX - i - 1) >= 0) && ((posY + i + 1) < 9) && i < panelsAdded[2] + 1) {
                        System.out.println("panels added = " + panelsAdded[2] + 1);
                        board.removePanel(posX - i - 1, posY + i + 1, outline[i + 16]);
                        outline[i + 16].removeCapture();
                    }
                    if (((posX + i + 1) < 9) && ((posY - i - 1) >= 0) && i < panelsAdded[3] + 1) {
                        System.out.println("panels added = " + panelsAdded[3] + 1);
                        board.removePanel(posX + i + 1, posY - i - 1, outline[i + 24]);
                        outline[i + 24].removeCapture();
                    }
                }
                //reset booleans and add back covered pieces
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
            	//add outline panels
            for (int i = 0; i < 8; i++) {
            	//check if in bounds and if occupied by opponents pieces
                if (((posX - i - 1) >= 0) && ((posY - i - 1) >= 0) && !occupied[0] && !occupiedByFriend[0]) {
                    if(board.isPanelOccupied(posX - i - 1, posY - i - 1) &&
                            !board.getPanelName(posX - i - 1, posY - i - 1).equalsIgnoreCase("outline") &&
                            !board.getPanelName(posX - i - 1, posY - i - 1).contains("Opponent")) {
                        System.out.println("panel occupied[0]");
                        temp[0] = board.getComponentInPanel(posX - i - 1, posY - i - 1); //store occupied piece
                        board.removePanel(posX - i - 1, posY - i - 1, temp[0]); //remove piece
                        board.addPanel(posX - i - 1, posY - i - 1, outline[i]); //add outline
                        outline[i].drawCapture(); //draw capture message
                        occupied[0] = true;
                        //set occupied spots position
                        occupiedX[0] = posX - i - 1;
                        occupiedY[0] = posY - i - 1;
                        //add number of panels added to board
                        panelsAdded[0] = i;
                    }
                    //if board is occupied by piece from same side stop
                    else if (board.isPanelOccupied(posX - i - 1, posY - i - 1) &&
                            !board.getPanelName(posX - i - 1, posY - i - 1).equalsIgnoreCase("outline") &&
                            board.getPanelName(posX - i - 1, posY - i - 1).contains("Opponent")){
                        occupiedByFriend[0] = true;
                    }else //add panels
                    {
                        board.addPanel(posX - i - 1, posY - i - 1, outline[i]);
                        panelsAdded[0] = i;
                    }
                }

                if (((posX + i + 1) < 9) && ((posY + i + 1) < 9) && !occupied[1] && !occupiedByFriend[1]) {
                    if(board.isPanelOccupied(posX + i + 1, posY + i + 1) &&
                            !board.getPanelName(posX + i + 1, posY + i + 1).equalsIgnoreCase("outline") &&
                            !board.getPanelName(posX + i + 1, posY + i + 1).contains("Opponent")) {
                        System.out.println("panel occupied[1]");
                        temp[1] = board.getComponentInPanel(posX + i + 1, posY + i + 1);
                        board.removePanel(posX + i + 1, posY + i + 1, temp[1]);
                        board.addPanel(posX + i + 1, posY + i + 1, outline[i + 8]);
                        outline[i + 8].drawCapture();
                        occupied[1] = true;
                        occupiedX[1] = posX + i + 1;
                        occupiedY[1] = posY + i + 1;
                        panelsAdded[1] = i;
                    } else if(board.isPanelOccupied(posX + i + 1, posY + i + 1) &&
                            !board.getPanelName(posX + i + 1, posY + i + 1).equalsIgnoreCase("outline") &&
                            board.getPanelName(posX + i + 1, posY + i + 1).contains("Opponent")){
                        occupiedByFriend[1] = true;
                    } else{
                        board.addPanel(posX + i + 1, posY + i + 1, outline[i + 8]);
                        panelsAdded[1] = i;

                    }
                }

                if (((posX - i - 1) >= 0) && ((posY + i + 1) < 9) && !occupied[2] && !occupiedByFriend[2]) {
                    if(board.isPanelOccupied(posX - i - 1, posY + i + 1) &&
                            !board.getPanelName(posX - i - 1, posY + i + 1).equalsIgnoreCase("outline") &&
                            !board.getPanelName(posX - i - 1, posY + i + 1).contains("Opponent")) {
                        System.out.println("panel occupied[2]");
                        temp[2] = board.getComponentInPanel(posX - i - 1, posY + i + 1);
                        board.removePanel(posX - i - 1, posY + i + 1, temp[2]);
                        board.addPanel(posX - i - 1, posY + i + 1, outline[i + 16]);
                        outline[i + 16].drawCapture();
                        occupied[2] = true;
                        occupiedX[2] = posX - i - 1;
                        occupiedY[2] = posY + i + 1;
                        panelsAdded[2] = i;
                    } else if(board.isPanelOccupied(posX - i - 1, posY + i + 1) &&
                            !board.getPanelName(posX - i - 1, posY + i + 1).equalsIgnoreCase("outline") &&
                            board.getPanelName(posX - i - 1, posY + i + 1).contains("Opponent")){
                        occupiedByFriend[2] = true;
                    } else {
                        board.addPanel(posX - i - 1, posY + i + 1, outline[i + 16]);
                        panelsAdded[2] = i;
                    }
                }

                if (((posX + i + 1) < 9) && ((posY - i - 1) >= 0) && !occupied[3] && !occupiedByFriend[3]) {
                    if(board.isPanelOccupied(posX + i + 1, posY - i - 1) &&
                            !board.getPanelName(posX + i + 1, posY - i - 1).equalsIgnoreCase("outline") &&
                            !board.getPanelName(posX + i + 1, posY - i - 1).contains("Opponent")) {
                        System.out.println("panel occupied[3]");
                        temp[3] = board.getComponentInPanel(posX + i + 1, posY - i - 1);
                        board.removePanel(posX + i + 1, posY - i - 1, temp[3]);
                        board.addPanel(posX + i + 1, posY - i - 1, outline[i + 24]);
                        outline[i + 24].drawCapture();
                        occupied[3] = true;
                        occupiedX[3] = posX + i + 1;
                        occupiedY[3] = posY - i - 1;
                        panelsAdded[3] = i;
                    } else if(board.isPanelOccupied(posX + i + 1, posY - i - 1) &&
                            !board.getPanelName(posX + i + 1, posY - i - 1).equalsIgnoreCase("outline") &&
                            board.getPanelName(posX + i + 1, posY - i - 1).contains("Opponent")){
                        occupiedByFriend[3] = true;
                    } else {
                        board.addPanel(posX + i + 1, posY - i - 1, outline[i + 24]);
                        panelsAdded[3] = i;
                    }
                }

            }
            ++clickCounter;

            }
        } else if(!isOpponent){
            if (clickCounter > 0) {
                for (int i = 0; i < 8; i++) {
                    if (((posX - i - 1) >= 0) && ((posY - i - 1) >= 0) && i < panelsAdded[0] + 1) {
                        board.removePanel(posX - i - 1, posY - i - 1, outline[i]);
                        outline[i].removeCapture();
                    }
                    if (((posX + i + 1) < 9) && ((posY + i + 1) < 9) && i < panelsAdded[1] + 1) {
                        board.removePanel(posX + i + 1, posY + i + 1, outline[i + 8]);
                        outline[i + 8].removeCapture();
                    }
                    if (((posX - i - 1) >= 0) && ((posY + i + 1) < 9) && i < panelsAdded[2] + 1) {
                        board.removePanel(posX - i - 1, posY + i + 1, outline[i + 16]);
                        outline[i + 16].removeCapture();
                    }
                    if (((posX + i + 1) < 9) && ((posY - i - 1) >= 0) && i < panelsAdded[3] + 1) {
                        board.removePanel(posX + i + 1, posY - i - 1, outline[i + 24]);
                        outline[i + 24].removeCapture();
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
                    if (((posX - i - 1) >= 0) && ((posY - i - 1) >= 0) && !occupied[0] && !occupiedByFriend[0]) {
                        if(board.isPanelOccupied(posX - i - 1, posY - i - 1) &&
                                !board.getPanelName(posX - i - 1, posY - i - 1).equalsIgnoreCase("outline") &&
                                board.getPanelName(posX - i - 1, posY - i - 1).contains("Opponent")) {
                            System.out.println("panel occupied[0]");
                            temp[0] = board.getComponentInPanel(posX - i - 1, posY - i - 1);
                            board.removePanel(posX - i - 1, posY - i - 1, temp[0]);
                            board.addPanel(posX - i - 1, posY - i - 1, outline[i]);
                            outline[i].drawCapture();
                            occupied[0] = true;
                            occupiedX[0] = posX - i - 1;
                            occupiedY[0] = posY - i - 1;
                            panelsAdded[0] = i;
                        } else if (board.isPanelOccupied(posX - i - 1, posY - i - 1) &&
                                !board.getPanelName(posX - i - 1, posY - i - 1).equalsIgnoreCase("outline") &&
                                !board.getPanelName(posX - i - 1, posY - i - 1).contains("Opponent")){
                            occupiedByFriend[0] = true;
                        }else {
                            board.addPanel(posX - i - 1, posY - i - 1, outline[i]);
                            panelsAdded[0] = i;
                        }
                    }
                    if (((posX + i + 1) < 9) && ((posY + i + 1) < 9) && !occupied[1] && !occupiedByFriend[1]) {
                        if(board.isPanelOccupied(posX + i + 1, posY + i + 1) &&
                                !board.getPanelName(posX + i + 1, posY + i + 1).equalsIgnoreCase("outline") &&
                                board.getPanelName(posX + i + 1, posY + i + 1).contains("Opponent")) {
                            System.out.println("panel occupied[1]");
                            temp[1] = board.getComponentInPanel(posX + i + 1, posY + i + 1);
                            board.removePanel(posX + i + 1, posY + i + 1, temp[1]);
                            board.addPanel(posX + i + 1, posY + i + 1, outline[i + 8]);
                            outline[i + 8].drawCapture();
                            occupied[1] = true;
                            occupiedX[1] = posX + i + 1;
                            occupiedY[1] = posY + i + 1;
                            panelsAdded[1] = i;
                        } else if(board.isPanelOccupied(posX + i + 1, posY + i + 1) &&
                                !board.getPanelName(posX + i + 1, posY + i + 1).equalsIgnoreCase("outline") &&
                                !board.getPanelName(posX + i + 1, posY + i + 1).contains("Opponent")){
                            occupiedByFriend[1] = true;
                        } else {
                            board.addPanel(posX + i + 1, posY + i + 1, outline[i + 8]);
                            panelsAdded[1] = i;
                        }
                    }
                    if (((posX - i - 1) >= 0) && ((posY + i + 1) < 9) && !occupied[2] && !occupiedByFriend[2]) {
                        if(board.isPanelOccupied(posX - i - 1, posY + i + 1) &&
                                !board.getPanelName(posX - i - 1, posY + i + 1).equalsIgnoreCase("outline") &&
                                board.getPanelName(posX - i - 1, posY + i + 1).contains("Opponent")) {
                            System.out.println("panel occupied[2]");
                            temp[2] = board.getComponentInPanel(posX - i - 1, posY + i + 1);
                            board.removePanel(posX - i - 1, posY + i + 1, temp[2]);
                            outline[i + 16].drawCapture();
                            board.addPanel(posX - i - 1, posY + i + 1, outline[i + 16]);
                            occupied[2] = true;
                            occupiedX[2] = posX - i - 1;
                            occupiedY[2] = posY + i + 1;
                            panelsAdded[2] = i;
                        } else if(board.isPanelOccupied(posX - i - 1, posY + i + 1) &&
                                !board.getPanelName(posX - i - 1, posY + i + 1).equalsIgnoreCase("outline") &&
                                !board.getPanelName(posX - i - 1, posY + i + 1).contains("Opponent")){
                            occupiedByFriend[2] = true;
                        } else {
                            board.addPanel(posX - i - 1, posY + i + 1, outline[i + 16]);
                            panelsAdded[2] = i;
                        }
                    }
                    if (((posX + i + 1) < 9) && ((posY - i - 1) >= 0) && !occupied[3] && !occupiedByFriend[3]) {
                        if(board.isPanelOccupied(posX + i + 1, posY - i - 1) &&
                                !board.getPanelName(posX + i + 1, posY - i - 1).equalsIgnoreCase("outline") &&
                                board.getPanelName(posX + i + 1, posY - i - 1).contains("Opponent")) {
                            System.out.println("panel occupied[3]");
                            temp[3] = board.getComponentInPanel(posX + i + 1, posY - i - 1);
                            board.removePanel(posX + i + 1, posY - i - 1, temp[3]);
                            board.addPanel(posX + i + 1, posY - i - 1, outline[i + 24]);
                            outline[i + 24].drawCapture();
                            occupied[3] = true;
                            occupiedX[3] = posX + i + 1;
                            occupiedY[3] = posY - i - 1;
                            panelsAdded[3] = i;
                        } else if (board.isPanelOccupied(posX + i + 1, posY - i - 1) &&
                                !board.getPanelName(posX + i + 1, posY - i - 1).equalsIgnoreCase("outline") &&
                                !board.getPanelName(posX + i + 1, posY - i - 1).contains("Opponent")){
                            occupiedByFriend[3] = true;
                        } else {
                            board.addPanel(posX + i + 1, posY - i - 1, outline[i + 24]);
                            panelsAdded[3] = i;
                        }
                    }
                }
                ++clickCounter;

            }

        }
    }
}
