import org.omg.Messaging.SYNC_WITH_TRANSPORT;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;


public class King extends GamePiece implements MouseListener {
    private int posX;
    private int posY;
    private GameBoard board;
    private outlinePanel[] outline = new outlinePanel[8];
    private int clickCounter = 0;
	private Component[] temp = new Component[8];
	private boolean[] occupied = new boolean[8];
	private int[] occupiedX = new int[8];
	private int[] occupiedY = new int [8];
	private boolean[] panelAdded = new boolean[8];

    public King(boolean isOpponent, int x, int y, GameBoard b)
    {
        super(isOpponent);
        posX = x;
        posY = y;

        board = b;

	    for (int i = 0; i < 8; ++i){
		    temp[i] = new BlankPanel();
		    occupied[i] = false;
		    occupiedX[i] = 0;
		    occupiedY[i] = 0;
		    panelAdded[i] = false;
	    }

        outline[0] = new outlinePanel(board, posX - 1, posY, this);
	    outline[1] = new outlinePanel(board, posX - 1, posY + 1, this);
	    outline[2] = new outlinePanel(board, posX - 1, posY - 1, this);
	    outline[3] = new outlinePanel(board, posX + 1, posY + 1, this);
	    outline[4] =  new outlinePanel(board, posX, posY + 1, this);
	    outline[5] = new outlinePanel(board, posX, posY - 1, this);
	    outline[6] = new outlinePanel(board, posX + 1, posY, this);
	    outline[7] = new outlinePanel(board, posX + 1, posY - 1, this);

	    if (isOpponent) {
	        setName("KingOpponent");
        }
        else{
        	setName("King");
        }
            super.addMouseListener(this);
    }

    public King(int x, int y, GameBoard b)
    {
        this(false,x, y, b);
    }

    public King(boolean isOpponent)
    {
        super(isOpponent);

        board = null;
        posX = 0;
        posY = 0;
        outline = null;
    }

    public void setPos(int x, int y)
    {
        //store old pos
        int oldPosX = posX;
        int oldPosY = posY;

        //set new pos
        posX = x;
        posY = y;

	    for (int i = 0; i < 8; ++i){
		    if (!temp[i].getName().equalsIgnoreCase("blankPanel") && posX == occupiedX[i] && posY == occupiedY[i]){
			    ReservoirPanel.addToRes(isOpponent, temp[i].getName());
			    temp[i] = new BlankPanel();
			    occupied[i] = false;
		    }
		    else if (occupied[i]){
			    //board.addPanel(occupiedX[i], occupiedY[i], temp[i]);
			    //temp[i] = new BlankPanel();

		    }
	    }

        //remove outline panels
	    try {
		    removeOutlinePanels(oldPosX, oldPosY);
	    }catch (Exception e){
		    e.printStackTrace();
	    }

        //reset outline panels
	    outline[0] = new outlinePanel(board, posX - 1, posY, this);
	    outline[1] = new outlinePanel(board, posX - 1, posY + 1, this);
	    outline[2] = new outlinePanel(board, posX - 1, posY - 1, this);
	    outline[3] = new outlinePanel(board, posX + 1, posY + 1, this);
	    outline[4] =  new outlinePanel(board, posX, posY + 1, this);
	    outline[5] = new outlinePanel(board, posX, posY - 1, this);
	    outline[6] = new outlinePanel(board, posX + 1, posY, this);
	    outline[7] = new outlinePanel(board, posX + 1, posY - 1, this);
        //System.out.println("PosX = " + posX + "\nPosY = " + posY);
    }

    @Override
    public void resetClickCounter() {
        clickCounter = 0;
    }

    @Override
    public void removeOutlinePanels(int x, int y) {
    	
	    if (x - 1 >= 0 && (panelAdded[0] || occupied[0])) {
		    board.removePanel(x - 1, y, outline[0]);
		    outline[0].removeCapture();
		    if (occupied[0]){
			    board.addPanel(occupiedX[0], occupiedY[0], temp[0]);
			    occupied[0] = false;
			    temp[0] = new BlankPanel();
		    }
		    panelAdded[0] = false;
	    }
	    if ((x - 1 >= 0) && (y + 1 < 9) && (panelAdded[1] || occupied[1])) {
		    board.removePanel(x - 1, y + 1, outline[1]);
		    outline[1].removeCapture();
		    if (occupied[1]){
			    board.addPanel(occupiedX[1], occupiedY[1], temp[1]);
			    occupied[1] = false;
			    temp[1] = new BlankPanel();
		    }
		    panelAdded[1] = false;
		    
	    }
	    if ((x - 1 >= 0) && (y - 1 >= 0) && (panelAdded[2] || occupied[2])) {
		    board.removePanel(x - 1, y - 1, outline[2]);
		    outline[2].removeCapture();
		    if (occupied[2]){
			    board.addPanel(occupiedX[2], occupiedY[2], temp[2]);
			    occupied[2] = false;
			    temp[2] = new BlankPanel();
		    }
		    panelAdded[2] = false;
	    }
	    if ((x + 1 < 9) && (y + 1 < 9) && (panelAdded[3] || occupied[3])) {
		    board.removePanel(x + 1, y + 1, outline[3]);
		    outline[3].removeCapture();
		    if (occupied[3]){
			    board.addPanel(occupiedX[3], occupiedY[3], temp[3]);
			    occupied[3] = false;
			    temp[3] = new BlankPanel();
		    }
		    panelAdded[3] = false;
	    }

	    if (y + 1 < 9 && (panelAdded[4] || occupied[4])) {
		    board.removePanel(x, y + 1, outline[4]);
		    outline[4].removeCapture();
		    if (occupied[4]){
			    board.addPanel(occupiedX[4], occupiedY[4], temp[4]);
			    occupied[4] = false;
			    temp[4] = new BlankPanel();
		    }
		    panelAdded[4] = false;
	    }

	    if (y - 1 >= 0 && (panelAdded[5] || occupied[5])) {
		    board.removePanel(x, y - 1, outline[5]);
		    outline[5].removeCapture();
		    if (occupied[5]){
			    board.addPanel(occupiedX[5], occupiedY[5], temp[5]);
			    occupied[5] = false;
			    temp[5] = new BlankPanel();
		    }
		    panelAdded[5] = false;
	    }

	    if (x + 1 < 9 && (panelAdded[6] || occupied[6])) {
		    board.removePanel(x + 1, y, outline[6]);
		    outline[6].removeCapture();
		    if (occupied[6]){
			    board.addPanel(occupiedX[6], occupiedY[6], temp[6]);
			    occupied[6] = false;
			    temp[6] = new BlankPanel();
		    }
		    panelAdded[6] = false;
	    }

	    if ((x + 1 < 9) && (y - 1 >= 0) && (panelAdded[7] || occupied[7])) {
		    board.removePanel(x + 1, y - 1, outline[7]);
		    outline[7].removeCapture();
		    if (occupied[7]){
			    board.addPanel(occupiedX[7], occupiedY[7], temp[7]);
			    occupied[7] = false;
			    temp[7] = new BlankPanel();
		    }
		    panelAdded[7] = false;
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
        g2d.drawString("K", 40, 50);

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

    public void move(){
    	if (isOpponent && !isPlayerTurn) {
		    if (clickCounter > 0) {
			    removeOutlinePanels(posX, posY);
			    clickCounter = 0;
		    } else {
		    	System.out.println("pos x = " + posX);
			    if (posX - 1 >= 0) {
				    if (board.isPanelOccupied(posX - 1, posY) &&
						    !board.getComponentInPanel(posX - 1, posY).getName().equalsIgnoreCase("outline") &&
						    !board.getComponentInPanel(posX - 1, posY).getName().contains("Opponent")) {
					    temp[0] = board.getComponentInPanel(posX - 1, posY);
					    board.removePanel(posX - 1, posY, temp[0]);
					    board.addPanel(posX - 1, posY, outline[0]);
					    outline[0].drawCapture();
					    occupied[0] = true;
					    occupiedX[0] = posX - 1;
					    occupiedY[0] = posY;
				    } else if(!board.isPanelOccupied(posX - 1, posY)){
				    	System.out.println("adding down panel");
					    board.addPanel(posX - 1, posY, outline[0]);
				        panelAdded[0] = true;
				    }
			    }

			    if ((posX - 1 >= 0) && (posY + 1 < 9)) {
				    if (board.isPanelOccupied(posX - 1, posY + 1) &&
						    !board.getComponentInPanel(posX - 1, posY + 1).getName().equalsIgnoreCase("outline") &&
						    !board.getComponentInPanel(posX - 1, posY + 1).getName().contains("Opponent")) {
					    temp[1] = board.getComponentInPanel(posX - 1, posY + 1);
					    board.removePanel(posX - 1, posY + 1, temp[1]);
					    board.addPanel(posX - 1, posY + 1, outline[1]);
					    outline[1].drawCapture();
					    occupied[1] = true;
					    occupiedX[1] = posX - 1;
					    occupiedY[1] = posY + 1;
				    } else if(!board.isPanelOccupied(posX - 1, posY + 1)){
					    board.addPanel(posX - 1, posY + 1, outline[1]);
					    panelAdded[1] = true;
				    }
			    }

			    if ((posX - 1 >= 0) && (posY - 1 >= 0)) {
				    if (board.isPanelOccupied(posX - 1, posY - 1) &&
						    !board.getComponentInPanel(posX - 1, posY - 1).getName().equalsIgnoreCase("outline") &&
						    !board.getComponentInPanel(posX - 1, posY - 1).getName().contains("Opponent")) {
					    temp[2] = board.getComponentInPanel(posX - 1, posY - 1);
					    board.removePanel(posX - 1, posY - 1, temp[2]);
					    board.addPanel(posX - 1, posY - 1, outline[2]);
					    outline[2].drawCapture();
					    occupied[2] = true;
					    occupiedX[2] = posX - 1;
					    occupiedY[2] = posY - 1;
				    } else if(!board.isPanelOccupied(posX - 1, posY - 1)){
					    board.addPanel(posX - 1, posY - 1, outline[2]);
					    panelAdded[2] = true;
				    }
			    }

			    if ((posX + 1 < 9) && (posY + 1 < 9)) {
				    if (board.isPanelOccupied(posX + 1, posY + 1) &&
						    !board.getComponentInPanel(posX + 1, posY + 1).getName().equalsIgnoreCase("outline") &&
						    !board.getComponentInPanel(posX + 1, posY + 1).getName().contains("Opponent")) {
					    temp[3] = board.getComponentInPanel(posX + 1, posY + 1);
					    board.removePanel(posX + 1, posY + 1, temp[3]);
					    board.addPanel(posX + 1, posY + 1, outline[3]);
					    outline[3].drawCapture();
					    occupied[3] = true;
					    occupiedX[3] = posX + 1;
					    occupiedY[3] = posY + 1;
				    } else if (!board.isPanelOccupied(posX + 1, posY + 1)) {
					    board.addPanel(posX + 1, posY + 1, outline[3]);
					    panelAdded[3] = true;
				    }
			    }

			    if (posY + 1 < 9) {
				    if (board.isPanelOccupied(posX, posY + 1) &&
						    !board.getComponentInPanel(posX, posY + 1).getName().equalsIgnoreCase("outline") &&
						    !board.getComponentInPanel(posX, posY + 1).getName().contains("Opponent")) {
					    temp[4] = board.getComponentInPanel(posX, posY + 1);
					    board.removePanel(posX, posY + 1, temp[4]);
					    board.addPanel(posX, posY + 1, outline[4]);
					    outline[4].drawCapture();
					    occupied[4] = true;
					    occupiedX[4] = posX ;
					    occupiedY[4] = posY + 1;
				    } else if (!board.isPanelOccupied(posX, posY + 1)) {
					    board.addPanel(posX, posY + 1, outline[4]);
					    panelAdded[4] = true;
				    }
			    }

			    if (posY - 1 >= 0) {
				    if (board.isPanelOccupied(posX, posY - 1) &&
						    !board.getComponentInPanel(posX, posY - 1).getName().equalsIgnoreCase("outline") &&
						    !board.getComponentInPanel(posX, posY - 1).getName().contains("Opponent")) {
					    temp[5] = board.getComponentInPanel(posX, posY - 1);
					    board.removePanel(posX, posY - 1, temp[5]);
					    board.addPanel(posX, posY - 1, outline[5]);
					    outline[5].drawCapture();
					    occupied[5] = true;
					    occupiedX[5] = posX ;
					    occupiedY[5] = posY - 1;
				    } else if (!board.isPanelOccupied(posX, posY - 1)) {
					    board.addPanel(posX, posY - 1, outline[5]);
					    panelAdded[5] = true;
				    }
			    }

			    if (posX + 1 < 9) {
				    if (board.isPanelOccupied(posX + 1, posY) &&
						    !board.getComponentInPanel(posX + 1, posY).getName().equalsIgnoreCase("outline") &&
						    !board.getComponentInPanel(posX + 1, posY).getName().contains("Opponent")) {
					    temp[6] = board.getComponentInPanel(posX + 1, posY);
					    board.removePanel(posX + 1, posY, temp[6]);
					    board.addPanel(posX + 1, posY, outline[6]);
					    outline[6].drawCapture();
					    occupied[6] = true;
					    occupiedX[6] = posX + 1;
					    occupiedY[6] = posY;
				    } else if (!board.isPanelOccupied(posX + 1, posY)) {
					    board.addPanel(posX + 1, posY, outline[6]);
					    panelAdded[6] = true;
				    }
			    }

			    if ((posX + 1 < 9) && (posY - 1 >= 0)) {
				    if (board.isPanelOccupied(posX + 1, posY - 1) &&
						    !board.getComponentInPanel(posX + 1, posY - 1).getName().equalsIgnoreCase("outline") &&
						    !board.getComponentInPanel(posX + 1, posY - 1).getName().contains("Opponent")) {
					    temp[7] = board.getComponentInPanel(posX + 1, posY - 1);
					    board.removePanel(posX + 1, posY - 1, temp[7]);
					    board.addPanel(posX + 1, posY - 1, outline[7]);
					    outline[7].drawCapture();
					    occupied[7] = true;
					    occupiedX[7] = posX + 1;
					    occupiedY[7] = posY - 1;
				    } else if (!board.isPanelOccupied(posX + 1, posY - 1)) {
					    board.addPanel(posX + 1, posY - 1, outline[7]);
					    panelAdded[7] = true;
				    }
			    }

			    ++clickCounter;
		    }
	    }else if(!isOpponent && isPlayerTurn){
		    if (clickCounter > 0) {
			    removeOutlinePanels(posX, posY);
			    clickCounter = 0;
		    } else {
			    System.out.println("pos x = " + posX);
			    if (posX - 1 >= 0) {
				    if (board.isPanelOccupied(posX - 1, posY) &&
						    !board.getComponentInPanel(posX - 1, posY).getName().equalsIgnoreCase("outline") &&
						    board.getComponentInPanel(posX - 1, posY).getName().contains("Opponent")) {
					    temp[0] = board.getComponentInPanel(posX - 1, posY);
					    board.removePanel(posX - 1, posY, temp[0]);
					    board.addPanel(posX - 1, posY, outline[0]);
					    outline[0].drawCapture();
					    occupied[0] = true;
					    occupiedX[0] = posX - 1;
					    occupiedY[0] = posY;
				    } else if(!board.isPanelOccupied(posX - 1, posY)){
					    System.out.println("adding down panel");
					    board.addPanel(posX - 1, posY, outline[0]);
					    panelAdded[0] = true;
				    }
			    }

			    if ((posX - 1 >= 0) && (posY + 1 < 9)) {
				    if (board.isPanelOccupied(posX - 1, posY + 1) &&
						    !board.getComponentInPanel(posX - 1, posY + 1).getName().equalsIgnoreCase("outline") &&
						    board.getComponentInPanel(posX - 1, posY + 1).getName().contains("Opponent")) {
					    temp[1] = board.getComponentInPanel(posX - 1, posY + 1);
					    board.removePanel(posX - 1, posY + 1, temp[1]);
					    board.addPanel(posX - 1, posY + 1, outline[1]);
					    outline[1].drawCapture();
					    occupied[1] = true;
					    occupiedX[1] = posX - 1;
					    occupiedY[1] = posY + 1;
				    } else if(!board.isPanelOccupied(posX - 1, posY + 1)){
					    board.addPanel(posX - 1, posY + 1, outline[1]);
					    panelAdded[1] = true;
				    }
			    }

			    if ((posX - 1 >= 0) && (posY - 1 >= 0)) {
				    if (board.isPanelOccupied(posX - 1, posY - 1) &&
						    !board.getComponentInPanel(posX - 1, posY - 1).getName().equalsIgnoreCase("outline") &&
						    board.getComponentInPanel(posX - 1, posY - 1).getName().contains("Opponent")) {
					    temp[2] = board.getComponentInPanel(posX - 1, posY - 1);
					    board.removePanel(posX - 1, posY - 1, temp[2]);
					    board.addPanel(posX - 1, posY - 1, outline[2]);
					    outline[2].drawCapture();
					    occupied[2] = true;
					    occupiedX[2] = posX - 1;
					    occupiedY[2] = posY - 1;
				    } else if(!board.isPanelOccupied(posX - 1, posY - 1)){
					    board.addPanel(posX - 1, posY - 1, outline[2]);
					    panelAdded[2] = true;
				    }
			    }

			    if ((posX + 1 < 9) && (posY + 1 < 9)) {
				    if (board.isPanelOccupied(posX + 1, posY + 1) &&
						    !board.getComponentInPanel(posX + 1, posY + 1).getName().equalsIgnoreCase("outline") &&
						    board.getComponentInPanel(posX + 1, posY + 1).getName().contains("Opponent")) {
					    temp[3] = board.getComponentInPanel(posX + 1, posY + 1);
					    board.removePanel(posX + 1, posY + 1, temp[3]);
					    board.addPanel(posX + 1, posY + 1, outline[3]);
					    outline[3].drawCapture();
					    occupied[3] = true;
					    occupiedX[3] = posX + 1;
					    occupiedY[3] = posY + 1;
				    } else if (!board.isPanelOccupied(posX + 1, posY + 1)) {
					    board.addPanel(posX + 1, posY + 1, outline[3]);
					    panelAdded[3] = true;
				    }
			    }

			    if (posY + 1 < 9) {
				    if (board.isPanelOccupied(posX, posY + 1) &&
						    !board.getComponentInPanel(posX, posY + 1).getName().equalsIgnoreCase("outline") &&
						    board.getComponentInPanel(posX, posY + 1).getName().contains("Opponent")) {
					    temp[4] = board.getComponentInPanel(posX, posY + 1);
					    board.removePanel(posX, posY + 1, temp[4]);
					    board.addPanel(posX, posY + 1, outline[4]);
					    outline[4].drawCapture();
					    occupied[4] = true;
					    occupiedX[4] = posX ;
					    occupiedY[4] = posY + 1;
				    } else if (!board.isPanelOccupied(posX, posY + 1)) {
					    board.addPanel(posX, posY + 1, outline[4]);
					    panelAdded[4] = true;
				    }
			    }

			    if (posY - 1 >= 0) {
				    if (board.isPanelOccupied(posX, posY - 1) &&
						    !board.getComponentInPanel(posX, posY - 1).getName().equalsIgnoreCase("outline") &&
						    board.getComponentInPanel(posX, posY - 1).getName().contains("Opponent")) {
					    temp[5] = board.getComponentInPanel(posX, posY - 1);
					    board.removePanel(posX, posY - 1, temp[5]);
					    board.addPanel(posX, posY - 1, outline[5]);
					    outline[5].drawCapture();
					    occupied[5] = true;
					    occupiedX[5] = posX ;
					    occupiedY[5] = posY - 1;
				    } else if (!board.isPanelOccupied(posX, posY - 1)) {
					    board.addPanel(posX, posY - 1, outline[5]);
					    panelAdded[5] = true;
				    }
			    }

			    if (posX + 1 < 9) {
				    if (board.isPanelOccupied(posX + 1, posY) &&
						    !board.getComponentInPanel(posX + 1, posY).getName().equalsIgnoreCase("outline") &&
						    board.getComponentInPanel(posX + 1, posY).getName().contains("Opponent")) {
					    temp[6] = board.getComponentInPanel(posX + 1, posY );
					    board.removePanel(posX + 1, posY, temp[6]);
					    board.addPanel(posX + 1, posY, outline[6]);
					    outline[6].drawCapture();
					    occupied[6] = true;
					    occupiedX[6] = posX + 1;
					    occupiedY[6] = posY;
				    } else if (!board.isPanelOccupied(posX + 1, posY)) {
					    board.addPanel(posX + 1, posY, outline[6]);
					    panelAdded[6] = true;
				    }
			    }

			    if ((posX + 1 < 9) && (posY - 1 >= 0)) {
				    if (board.isPanelOccupied(posX + 1, posY - 1) &&
						    !board.getComponentInPanel(posX + 1, posY - 1).getName().equalsIgnoreCase("outline") &&
						    board.getComponentInPanel(posX + 1, posY - 1).getName().contains("Opponent")) {
					    temp[7] = board.getComponentInPanel(posX + 1, posY - 1);
					    board.removePanel(posX + 1, posY - 1, temp[7]);
					    board.addPanel(posX + 1, posY - 1, outline[7]);
					    outline[7].drawCapture();
					    occupied[7] = true;
					    occupiedX[7] = posX + 1;
					    occupiedY[7] = posY - 1;
				    } else if (!board.isPanelOccupied(posX + 1, posY - 1)) {
					    board.addPanel(posX + 1, posY - 1, outline[7]);
					    panelAdded[7] = true;
				    }
			    }

			    ++clickCounter;
		    }
    	}

    }

}
