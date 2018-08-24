import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class outlinePanel extends JPanel implements MouseListener {
    private GameBoard board;
    private int posX;
    private int posY;
    private GamePiece piece;
    private boolean canCapture = false;

    public outlinePanel(GameBoard b, int x, int y, GamePiece p)
    {
        board = b;
        posX = x;
        posY = y;
        piece = p;
        super.addMouseListener(this);

        setName("outline");
    }

    //set internal position
    public void setPos(int x, int y)
    {
        posX = x;
        posY = y;
    }

    public void paintComponent(Graphics g)
    {
        super.paintComponent(g);

        //draw outlin
        Graphics2D g2 = (Graphics2D) g;
        g2.setColor(Color.RED);
        g2.setStroke(new BasicStroke(5));
        g2.drawLine(0,0,getWidth(), 0);
        g2.drawLine(getWidth(),0,getWidth(), getHeight()-1);
        g2.drawLine(0,getHeight() - 1,getWidth(), getHeight()-1);
        g2.drawLine(0,0,0, getHeight()-1);

        //draw capture message
        if(canCapture)
        {
            g2.drawString("Capture", 10,50);
            g2.drawString("Piece", 20, 60);
        }

    }

    @Override
    public void mouseClicked(MouseEvent e) {
        board.removePanel(posX, posY, this);        //remove this panel
        board.addPanel(posX, posY, piece);      //add piece back
        int OP = JOptionPane.YES_NO_OPTION;
        int promotionOption;
        piece.setPos(posX, posY); //set piece position
        piece.resetClickCounter(); //reset piece click counter
        
       // JOptionPane.showConfirmDialog (null, "Promote Piece?");
	    //check for promotion
        if(posX == 0)
        {
        	//board.removePanel(posX, posY, piece);
        	switch(piece.getName())
        	{
        	case "Lance":
        		board.removePanel(posX, posY, piece);
        		 board.addPanel(posX, posY, new promotedLance(posX,posY,board));
        		 break;
        		 
        	case "Pawn" :
        		board.removePanel(posX, posY, piece);
        		 board.addPanel(posX, posY, new promotedPawn(posX,posY,board));
        		 break;
        		 
        	case "Night":
        		board.removePanel(posX, posY, piece);
        		board.addPanel(posX, posY, new promotedKnight(posX,posY,board));
        		break;
        		
        	case "Bishop": 
        		promotionOption = JOptionPane.showConfirmDialog (null, "Promote Piece?", null, JOptionPane.YES_NO_OPTION);
          		 if(promotionOption == 0)
          		 {
          			 board.removePanel(posX, posY, piece);
          			 board.addPanel(posX, posY, new promotedBishop(posX,posY,board)); 
          		 }
          		break;
           	
           	case "Rook": 
           		promotionOption = JOptionPane.showConfirmDialog (null, "Promote Piece?", null, JOptionPane.YES_NO_OPTION);
           	if(promotionOption == 0)
           	{
           		board.removePanel(posX, posY, piece);
           		board.addPanel(posX, posY, new promotedRook(posX,posY,board)); 
           	}
           	break;
           	
           	case "SilverGeneral": 
           		promotionOption = JOptionPane.showConfirmDialog (null, "Promote Piece?", null, JOptionPane.YES_NO_OPTION);
          		 if(promotionOption == 0)
          		 {
          			 board.removePanel(posX, posY, piece);
          			 board.addPanel(posX, posY, new promotedSilverGeneral(posX,posY,board)); 
          		 }
          		break;
        	}
        }
        
        if(posX == 1 || posX == 2)
        {
        	switch(piece.getName())
        	{
        	case "Lance": 
        		promotionOption = JOptionPane.showConfirmDialog (null, "Promote Piece?", null, JOptionPane.YES_NO_OPTION);
        		 if(promotionOption == 0)
        		 {
        			 board.removePanel(posX, posY, piece);
        			 board.addPanel(posX, posY, new promotedLance(posX,posY,board)); 
        		 }
        		break;
        		
        	case "Pawn": 
        		promotionOption = JOptionPane.showConfirmDialog (null, "Promote Piece?", null, JOptionPane.YES_NO_OPTION);
       		 if(promotionOption == 0)
       		 {
       			 board.removePanel(posX, posY, piece);
       			 board.addPanel(posX, posY, new promotedPawn(posX,posY,board)); 
       		 }
       		break;
       		
        	case "Night": 
        		promotionOption = JOptionPane.showConfirmDialog (null, "Promote Piece?", null, JOptionPane.YES_NO_OPTION);
       		 if(promotionOption == 0)
       		 {
       			 board.removePanel(posX, posY, piece);
       			 board.addPanel(posX, posY, new promotedKnight(posX,posY,board)); 
       		 }
       		break;
       		
        	case "Bishop": 
        		promotionOption = JOptionPane.showConfirmDialog (null, "Promote Piece?", null, JOptionPane.YES_NO_OPTION);
       		 if(promotionOption == 0)
       		 {
       			 board.removePanel(posX, posY, piece);
       			 board.addPanel(posX, posY, new promotedBishop(posX,posY,board)); 
       		 }
       		break;
        	
        	case "Rook": 
        		promotionOption = JOptionPane.showConfirmDialog (null, "Promote Piece?", null, JOptionPane.YES_NO_OPTION);
        	if(promotionOption == 0)
        	{
        		board.removePanel(posX, posY, piece);
        		board.addPanel(posX, posY, new promotedRook(posX,posY,board)); 
        	}
        	break;
        	
        	case "SilverGeneral": 
        		promotionOption = JOptionPane.showConfirmDialog (null, "Promote Piece?", null, JOptionPane.YES_NO_OPTION);
       		 if(promotionOption == 0)
       		 {
       			 board.removePanel(posX, posY, piece);
       			 board.addPanel(posX, posY, new promotedSilverGeneral(posX,posY,board)); 
       		 }
       		break;
        	}
        }
        
        if(posX == 8)
        {
        	//board.removePanel(posX, posY, piece);
        	switch(piece.getName())
        	{
        	case "LanceOpponent":
        		board.removePanel(posX, posY, piece);
        		 board.addPanel(posX, posY, new promotedLance(true,posX,posY,board));
        		 break;
        		 
        	case "PawnOpponent" :
        		board.removePanel(posX, posY, piece);
        		 board.addPanel(posX, posY, new promotedPawn(true,posX,posY,board));
        		 break;
        		 
        	case "NightOpponent":
        		board.removePanel(posX, posY, piece);
        		board.addPanel(posX, posY, new promotedKnight(true,posX,posY,board));
        		break;
        		
        	case "BishopOpponent": 
        		promotionOption = JOptionPane.showConfirmDialog (null, "Promote Piece?", null, JOptionPane.YES_NO_OPTION);
          		 if(promotionOption == 0)
          		 {
          			 board.removePanel(posX, posY, piece);
          			 board.addPanel(posX, posY, new promotedBishop(true,posX,posY,board)); 
          		 }
          		break;
           	
           	case "RookOpponent": 
           		promotionOption = JOptionPane.showConfirmDialog (null, "Promote Piece?", null, JOptionPane.YES_NO_OPTION);
           	if(promotionOption == 0)
           	{
           		board.removePanel(posX, posY, piece);
           		board.addPanel(posX, posY, new promotedRook(true,posX,posY,board)); 
           	}
           	break;
           	
           	case "SilverGeneralOpponent": 
           		promotionOption = JOptionPane.showConfirmDialog (null, "Promote Piece?", null, JOptionPane.YES_NO_OPTION);
          		 if(promotionOption == 0)
          		 {
          			 board.removePanel(posX, posY, piece);
          			 board.addPanel(posX, posY, new promotedSilverGeneral(true,posX,posY,board)); 
          		 }
          		break;
        	}
        }
        
        if(posX == 6 || posX == 7)
        {
         	switch(piece.getName())
        	{
        	case "LanceOpponent": 
        		promotionOption = JOptionPane.showConfirmDialog (null, "Promote Piece?", null, JOptionPane.YES_NO_OPTION);
        		 if(promotionOption == 0)
        		 {
        			 board.removePanel(posX, posY, piece);
        			 board.addPanel(posX, posY, new promotedLance(true,posX,posY,board)); 
        		 }
        		break;
        		
        	case "PawnOpponent": 
        		promotionOption = JOptionPane.showConfirmDialog (null, "Promote Piece?", null, JOptionPane.YES_NO_OPTION);
       		 if(promotionOption == 0)
       		 {
       			 board.removePanel(posX, posY, piece);
       			 board.addPanel(posX, posY, new promotedPawn(true,posX,posY,board)); 
       		 }
       		break;
       		
        	case "NightOpponent": 
        		promotionOption = JOptionPane.showConfirmDialog (null, "Promote Piece?", null, JOptionPane.YES_NO_OPTION);
       		 if(promotionOption == 0)
       		 {
       			 board.removePanel(posX, posY, piece);
       			 board.addPanel(posX, posY, new promotedKnight(true,posX,posY,board)); 
       		 }
       		break;
       		
        	case "BishopOpponent": 
        		promotionOption = JOptionPane.showConfirmDialog (null, "Promote Piece?", null, JOptionPane.YES_NO_OPTION);
         		 if(promotionOption == 0)
         		 {
         			 board.removePanel(posX, posY, piece);
         			 board.addPanel(posX, posY, new promotedBishop(true,posX,posY,board)); 
         		 }
         		break;
          	
          	case "RookOpponent": 
          		promotionOption = JOptionPane.showConfirmDialog (null, "Promote Piece?", null, JOptionPane.YES_NO_OPTION);
          	if(promotionOption == 0)
          	{
          		board.removePanel(posX, posY, piece);
          		board.addPanel(posX, posY, new promotedRook(true,posX,posY,board)); 
          	}
          	break;
          	
          	case "SilverGeneralOpponent": 
          		promotionOption = JOptionPane.showConfirmDialog (null, "Promote Piece?", null, JOptionPane.YES_NO_OPTION);
         		 if(promotionOption == 0)
         		 {
         			 board.removePanel(posX, posY, piece);
         			 board.addPanel(posX, posY, new promotedSilverGeneral(true,posX,posY,board)); 
         		 }
         		break;
        	}

        }
        board.changeTurn(); //change turns
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
    
    public void drawCapture() {
        canCapture = true;
        repaint();
    }
    
    public void removeCapture(){
    	canCapture = false;
        repaint();
    }
}
