import javax.swing.*;
import java.awt.*;

public class PlayerReservoirPanel extends JPanel {
    private JPanel panelArray2[][];
    private int pawnCount = 0;
    private int lanceCount = 0;
    private int bishopCount = 0;
    private int rookCount = 0;
    private int goldCount = 0;
    private int silverCount = 0;
    private int knightCount = 0;
    private customLabel[] labelArray = new customLabel[7];


    public PlayerReservoirPanel(boolean isOpponent)
    {
        panelArray2 = new JPanel[3][6];
        setLayout(new GridLayout(3,6));
        //initialize panel array
        for (int i = 0; i < 3; ++i)
        {
            for (int j = 0; j < 6; ++j) {
                panelArray2[i][j] = new BlankPanel();
                add(panelArray2[i][j]);
            }
        }

        for (int i = 0; i < labelArray.length; ++i)
        {
            labelArray[i] = new customLabel( 0);
        }

        //add pieces to panel
        panelArray2[0][0].add(new Rook(isOpponent));
        panelArray2[0][1].add(labelArray[0]);

        panelArray2[0][2].add(new Pawn(isOpponent));
        panelArray2[0][3].add(labelArray[1]);

        panelArray2[0][4].add(new GoldGeneral(isOpponent));
        panelArray2[0][5].add(labelArray[2]);

        panelArray2[1][0].add(new SilverGeneral(isOpponent));
        panelArray2[1][1].add(labelArray[3]);

        panelArray2[1][2].add(new Knight(isOpponent));
        panelArray2[1][3].add(labelArray[4]);

        panelArray2[1][4].add(new Bishop(isOpponent));
        panelArray2[1][5].add(labelArray[5]);

        panelArray2[2][2].add(new Lance(isOpponent));
        panelArray2[2][3].add(labelArray[6]);

    }

    public void addToRes(String type)
    {
        //check wich piece was added and update count
        switch (type.charAt(0))
        {
            case 'R':
            case 'r':
                labelArray[0].setText(++rookCount);
                break;
            case 'P':
            case 'p':
                labelArray[1].setText(++pawnCount);
                break;
            case 'G':
            case 'g':
                labelArray[2].setText(++goldCount);
                break;
            case 'S':
            case 's':
                labelArray[3].setText(++silverCount);
                break;
            case 'N':
            case 'n':
                labelArray[4].setText(++knightCount);
                break;
            case 'B':
            case 'b':
                labelArray[5].setText(++bishopCount);
                break;
            case 'L':
            case 'l':
                labelArray[6].setText(++lanceCount);
                break;
            case 'K':
            case 'k':
	            String winner;
            	if (type.equalsIgnoreCase("opponent")){
            		winner = "1";
	            }else
	            	winner = "2";

                //JOptionPane.showMessageDialog(null, "CHECKMATE", null, JOptionPane.INFORMATION_MESSAGE);
                Object[] options = {"New Game", "Exit"};
                int result = JOptionPane.showOptionDialog(null,
                        "Player " + winner + " Won!",
                        "Checkmate",
                        JOptionPane.YES_NO_OPTION,
                        JOptionPane.QUESTION_MESSAGE,
                        null,     //do not use a custom Icon
                        options,  //the titles of buttons
                        options[0]); //default button title
	            if (result == JOptionPane.YES_OPTION)
	            	Shogi.NewGame();
	            else
	            	Shogi.Exit();

                break;
                default:
                    break;



        }
    }

    public void paintComponent (Graphics g)
    {

        super.paintComponent(g);

    }

}
