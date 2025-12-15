import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.ImageIcon;
import javax.swing.Icon;
import java.awt.GridLayout;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class Chess {
    // adds the ChessPanel (and in the future will add the ChessMoveLogPanel) to the JFrame
    public static void main(String[] args) {
        JFrame f = new JFrame("Chess");
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        ChessPanel chess = new ChessPanel();
        f.add(chess);
        f.setSize(800, 800);
        f.setVisible(true);
    }

    // main part of the chess game
    private static class ChessPanel extends JPanel {
        private JButton[][] jb;
        private boolean isPieceSelected = false;
        private ImageIcon pieceSelectedIcon;
        private JButton pieceSelected;

        public ChessPanel() {
            setLayout(new GridLayout(8, 8));

            // creates an 8x8 grid of buttons
            jb = new JButton[8][8];
            for (int i = 0; i < 8; i++) {
                for (int j = 0; j < 8; j++) {
                    jb[i][j] = new JButton();
                    add(jb[i][j]);
                }
            }

            // sets the icons of all the buttons to the appropriate starting chess pieces
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
            for (int i = 0; i < 8; i++) {
                jb[1][i].setIcon(new ImageIcon("BlackPawn.png"));
                jb[6][i].setIcon(new ImageIcon("WhitePawn.png"));
            }

            // adds a PieceHandler to all the buttons
            PieceHandler ph = new PieceHandler();
            for (int i = 0; i < 8; i++) {
                for (int j = 0; j < 8; j++) {
                    jb[i][j].addActionListener(ph);
                }
            }
        }

        private class PieceHandler implements ActionListener {
            public void actionPerformed(ActionEvent e) {
                // stores the chess piece that has been clicked if it was the first click
                // or moves the chess piece to the spot that was the second click
                if (isPieceSelected == false) {
                    isPieceSelected = true;
                    pieceSelectedIcon = (ImageIcon) ((JButton) e.getSource()).getIcon();
                    pieceSelected = (JButton) e.getSource();
                } else {
                    isPieceSelected = false;
                    if (isValidMove(pieceSelectedIcon, getRow(pieceSelected), getCol(pieceSelected),
                            getRow((JButton) e.getSource()), getCol((JButton) e.getSource()))) {
                        ((JButton) e.getSource()).setIcon(pieceSelectedIcon);
                        pieceSelected.setIcon(null);
                    }
                }
            }

            private int getRow(JButton button) {
                for (int i = 0; i < 8; i++) {
                    for (int j = 0; j < 8; j++) {
                        if (jb[i][j] == button) {
                            return i;
                        }
                    }
                }
                return -1;
            }

            private int getCol(JButton button) {
                for (int i = 0; i < 8; i++) {
                    for (int j = 0; j < 8; j++) {
                        if (jb[i][j] == button) {
                            return j;
                        }
                    }
                }
                return -1;
            }
        }

        // 
        private boolean isValidMove(ImageIcon icon, int fromRow, int fromCol, int toRow, int toCol) {
            if (icon.getDescription().contains("BlackPawn")) {
                return isValidPawnMove(true, fromRow, fromCol, toRow, toCol);
            } else if (icon.getDescription().contains("WhitePawn")) {
                return isValidPawnMove(false, fromRow, fromCol, toRow, toCol);
            } else if (icon.getDescription().contains("Rook")) {
                return isValidRookMove(fromRow, fromCol, toRow, toCol);
            } else if (icon.getDescription().contains("Knight")) {
                return isValidKnightMove(fromRow, fromCol, toRow, toCol);
            } else if (icon.getDescription().contains("Bishop")) {
                return isValidBishopMove(fromRow, fromCol, toRow, toCol);
            } else if (icon.getDescription().contains("Queen")) {
                return isValidQueenMove(fromRow, fromCol, toRow, toCol);
            } else if (icon.getDescription().contains("King")) {
                return isValidKingMove(fromRow, fromCol, toRow, toCol);
            }
            return true;
        }

        private boolean isValidPawnMove(boolean isBlack, int fromRow, int fromCol, int toRow, int toCol) {
            int direction = isBlack ? 1 : -1;

            // Basic pawn move (one square forward)
            if (fromCol == toCol && toRow == fromRow + direction) {
                return jb[toRow][toCol].getIcon() == null;
            }

            // Initial double move for pawns
            if (fromCol == toCol && fromRow == (isBlack ? 1 : 6) && toRow == fromRow + 2 * direction) {
                return jb[fromRow + direction][toCol].getIcon() == null && jb[toRow][toCol].getIcon() == null;
            }

            // Pawn capture diagonally
            return Math.abs(toCol - fromCol) == 1 && toRow == fromRow + direction
                    && isOpponentPiece(isBlack, jb[toRow][toCol].getIcon());
        }

        private boolean isValidRookMove(int fromRow, int fromCol, int toRow, int toCol) {
            // Rook can move horizontally or vertically
            return fromRow == toRow || fromCol == toCol;
        }

        private boolean isValidKnightMove(int fromRow, int fromCol, int toRow, int toCol) {
            // Knight moves in an L-shape (two squares in one direction and one square perpendicular)
            int rowDiff = Math.abs(toRow - fromRow);
            int colDiff = Math.abs(toCol - fromCol);
            return (rowDiff == 2 && colDiff == 1) || (rowDiff == 1 && colDiff == 2);
        }

        private boolean isValidBishopMove(int fromRow, int fromCol, int toRow, int toCol) {
            // Bishop moves diagonally
            return Math.abs(toRow - fromRow) == Math.abs(toCol - fromCol);
        }

        private boolean isValidQueenMove(int fromRow, int fromCol, int toRow, int toCol) {
            // Queen moves horizontally, vertically, or diagonally
            return isValidRookMove(fromRow, fromCol, toRow, toCol) || isValidBishopMove(fromRow, fromCol, toRow, toCol);
        }

        private boolean isValidKingMove(int fromRow, int fromCol, int toRow, int toCol) {
            // King moves one square in any direction
            int rowDiff = Math.abs(toRow - fromRow);
            int colDiff = Math.abs(toCol - fromCol);
            return rowDiff <= 1 && colDiff <= 1;
        }
	
	// Mainly used to distinguish if capturable or in check, whenever thats done
        private boolean isOpponentPiece(boolean isBlack, Icon icon) {
                return (isBlack && icon.toString().contains("White")) ||
                       (!isBlack && icon.toString().contains("Black"));
        }
    }
}

