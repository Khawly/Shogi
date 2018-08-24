import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

public class GameBoard extends JPanel {
    private BufferedImage bg;
    private GridLayout layout = new GridLayout(9,9,2,2);
    private JPanel[][] panelArray;


    GameBoard(){
        setLayout(layout);
        panelArray = new JPanel[9][9];

        for (int i = 0; i < 9; ++i)
        {
            for (int j = 0; j < 9; ++j)
            {
                panelArray[i][j] = new BlankPanel();
                add(panelArray[i][j]);
            }
        }

        //create opponent pieces
        Pawn[] rp = new Pawn[9];
        Lance[] rl = new Lance[2];
        Bishop rb = new Bishop(true);
        Rook rr = new Rook(true);
        Knight[] rn = new Knight[2];
        SilverGeneral[] rs = new SilverGeneral[2];
        GoldGeneral[] rg = new GoldGeneral[2];
        King rk = new King(true);

        //initialize arrays
        for (int i = 0; i < 2; ++i)
        {
           // rl[i] = new Lance(true);
            rn[i] = new Knight(true);
            rs[i] = new SilverGeneral(true);
            rg[i] = new GoldGeneral(true);
        }

        //add opponents pieces
        //add pawns
        for (int i = 0; i < 9; ++i) {
            rp[i] =  new Pawn(true, 2, i, this); //initialize array
            addPanel(2, i, rp[i]); //add to panel
        }

        rr = new Rook(true,1,1,this);
        addPanel(1,1, rr); //add rook
        rb = new Bishop(true,1,7,this);
        addPanel(1,7, rb); //add bishop
        //add lance
        rl[0] = new Lance(true, 0, 0, this);
        rl[1] = new Lance(true, 0, 8, this);
        addPanel(0, 0, rl[0]);
        addPanel(0, 8, rl[1]);
        //add knight
        rn[0] = new Knight(true, 0, 1, this);
        rn[1] = new Knight(true, 0, 7, this);
        addPanel(0, 1, rn[0]);
        addPanel(0, 7, rn[1]);
        //add silver general
        rs[0] = new SilverGeneral(true, 0, 2, this);
        rs[1] = new SilverGeneral(true, 0, 6, this);
        addPanel(0, 2, rs[0]);
        addPanel(0, 6, rs[1]);
        //add gold general
        rg[0] = new GoldGeneral(true,0,3,this);
        rg[1] = new GoldGeneral(true,0,5,this);
        addPanel(0, 3, rg[0]);
        addPanel(0, 5, rg[1]);
        //add king
        rk = new King(true,0,4,this);
        addPanel(0, 4, rk);


        //player pieces
        Pawn[] p = new Pawn[9];
        Lance[] l = new Lance[2];
        Bishop b = new Bishop(7,1,this);
        Rook r = new Rook(7,7, this);
        Knight[] n = new Knight[2];
        SilverGeneral[] s = new SilverGeneral[2];
        GoldGeneral[] g = new GoldGeneral[2];
        King k = new King(8,4,this);

        //initialize arrays
        for (int i = 0; i < 2; ++i)
        {
            l[i] = new Lance(0,0,this);
            n[i] = new Knight(0,0,this);
            s[i] = new SilverGeneral(0,0,this);
            g[i] = new GoldGeneral(0,0,this);
        }

        //add opponents pieces
        //add pawns
        for (int i = 0; i < 9; ++i) {
            p[i] =  new Pawn(6, i, this); //initialize array
            addPanel(6, i, p[i]); //add to panel
        }

        //add rook
        addPanel(7,7, r);
        //add bishop
        addPanel(7,1, b);
        //add lance
        l[0].setPos(8,0);
        l[1].setPos(8,8);
        addPanel(8, 0, l[0]);
        addPanel(8, 8, l[1]);
        //add knight
        n[0].setPos(8,1);
        n[1].setPos(8,7);
        addPanel(8, 1, n[0]);
        addPanel(8, 7, n[1]);
        //add silver general
        s[0].setPos(8,2);
        s[1].setPos(8,6);
        addPanel(8, 2, s[0]);
        addPanel(8, 6, s[1]);
        //add gold general
        g[0].setPos(8,3);
        g[1].setPos(8,5);
        addPanel(8, 3, g[0]);
        addPanel(8, 5, g[1]);
        //add king
        addPanel(8, 4, k);

        validate();
    }

    //adds panel to board
    public void addPanel(int x, int y, Component c)
    {
        panelArray[x][y].add(c);
        repaint();
    }

    //remove panel from board
    public void removePanel(int x, int y, Component c)
    {
        panelArray[x][y].remove(c);
        repaint();
    }
    
    //returns true if panel has a game piece in it
    public boolean isPanelOccupied(int x, int y)
    {
        int panelCount;
        String panelName;
        panelCount = panelArray[x][y].getComponentCount();

        if(panelCount > 0)
            return true;
        else
            return false;
    }

    //returns name of panel in panel array
    public String getPanelName(int x, int y)
    {

        Component[] temp = panelArray[x][y].getComponents();
        if (temp.length > 0)
            return temp[0].getName();
        else
            return panelArray[x][y].getName();

    }

    //returns piece ast position x, y in game board
    public Component getComponentInPanel(int x, int y)
    {
        try {
            Component[] temp = panelArray[x][y].getComponents();
            return temp[0];
        } catch (Exception e)
        {
            e.printStackTrace();
            return new BlankPanel();
        }
    }

    public void paintComponent(Graphics g)
    {
        super.paintComponent(g);

        Graphics2D g2d = (Graphics2D) g;
        setLayout(layout);


        //draws grid
        for (int i = 0; i < 10; ++i)
        {

            g2d.drawLine(0,i *100,900 , i * 100);
            g2d.drawLine(i* 100,0, i * 100, 900 );
        }
        g2d.drawLine(899,0, 899, 899);
        g2d.drawLine(0,899, 899, 899);





        validate();
        setSize(900,900);
    }

    //changes turn
    public void changeTurn(){
    	if (GamePiece.getPlayerTurn())
    		GamePiece.setPlayerTurn(false);
    	else
    		GamePiece.setPlayerTurn(true);
    }
}
