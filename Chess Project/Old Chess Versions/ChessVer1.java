import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.ImageIcon;
import java.awt.GridLayout;
import java.awt.Dimension;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class Chess
{
    //adds the ChessPanel (and in the future will add the ChessMoveLogPanel) to the JFrame
    public static void main(String[] args)
    {
        JFrame f = new JFrame("Chess");
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        ChessPanel chess = new ChessPanel();
        f.add(chess);
        f.setSize(800,800);
        f.setVisible(true);
    }

    //main part of the chess game
    private static class ChessPanel extends JPanel
    {
        private JButton[][] jb;
        private boolean isPieceSelected = false;
        private ImageIcon pieceSelectedIcon;
        private JButton pieceSelected;

        public ChessPanel()
        {
            setLayout(new GridLayout(8,8));
            
            //creates an 8x8 grid of buttons
            jb = new JButton[8][8];
            for (int i = 0; i < 8; i++)
            {
                for (int j = 0; j < 8; j++)
                {
                    jb[i][j] = new JButton();
                    add(jb[i][j]);
                }
            }

            //sets the icons of all of the buttons to the appropriate starting chess pieces
            jb[0][0].setIcon(new ImageIcon("BlackRook.png"));
            jb[0][1].setIcon(new ImageIcon("BlackKnight.png"));
            jb[0][2].setIcon(new ImageIcon("BlackBishop.png"));
            jb[0][3].setIcon(new ImageIcon("BlackQueen.png"));
            jb[0][4].setIcon(new ImageIcon("BlackKing.png"));
            jb[0][5].setIcon(new ImageIcon("BlackBishop.png"));
            jb[0][6].setIcon(new ImageIcon("BlackKnight.png"));
            jb[0][7].setIcon(new ImageIcon("BlackRook.png"));
            jb[7][0].setIcon(new ImageIcon("WhiteRook.png"));
            jb[7][1].setIcon(new ImageIcon("WhiteKnight.png"));
            jb[7][2].setIcon(new ImageIcon("WhiteBishop.png"));
            jb[7][3].setIcon(new ImageIcon("WhiteQueen.png"));
            jb[7][4].setIcon(new ImageIcon("WhiteKing.png"));
            jb[7][5].setIcon(new ImageIcon("WhiteBishop.png"));
            jb[7][6].setIcon(new ImageIcon("WhiteKnight.png"));
            jb[7][7].setIcon(new ImageIcon("WhiteRook.png"));
            for (int i = 0; i < 8; i++)
            {
                jb[1][i].setIcon(new ImageIcon("BlackPawn.png"));
                jb[6][i].setIcon(new ImageIcon("WhitePawn.png"));
            }

            //adds a PieceHandler to all of the buttons
            PieceHandler ph = new PieceHandler();
            for (int i = 0; i < 8; i++)
            {
                for (int j = 0; j < 8; j++)
                {
                    jb[i][j].addActionListener(ph);
                }
            }
        }

        private class PieceHandler implements ActionListener
        {
            public void actionPerformed(ActionEvent e)
            {
                //stores the chess piece that has been clicked if it was the first click
                //or moves the chess piece to the spot that was the second click
                if (isPieceSelected == false)
                {
                    isPieceSelected = true;
                    pieceSelectedIcon = (ImageIcon)((JButton)e.getSource()).getIcon();
                    pieceSelected = (JButton)e.getSource();
                }
                else
                {
                    isPieceSelected = false;
                    ((JButton)e.getSource()).setIcon(pieceSelectedIcon);
                    pieceSelected.setIcon(null);
                }
            }
        }
    }
}