import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

// main part of the chess game
public class ChessPanel extends JPanel {
    private JButton[][] jb;
    private JButton[] promotionGrid;
    private boolean isPieceSelected = false;
    private ImageIcon pieceSelectedIcon;
    private JButton pieceSelected;
    private ChessMoveLog moveLog;
    private boolean isBlackMove = false;
    private boolean wonGame = false;
    private boolean pickingPromotion = false;
    private JPanel promotionPanel;
    private Color color1, color2, color3, color4;

    public ChessPanel(Color firstColor, Color secondColor, Color thirdColor, Color fourthColor) {
        color1 = firstColor;
        color2 = secondColor;
        color3 = thirdColor;
        color4 = fourthColor;
        setLayout(new BorderLayout());

        // creating the main chess grid and filling in the colors
        JPanel chessBoardPanel = new JPanel(new GridLayout(8, 8));
        jb = new JButton[8][8];
        int backColor = 1;
        boolean isEvenRow = false;
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                jb[i][j] = new JButton();
                if (backColor % 2 == 0 && isEvenRow == false || backColor % 2 != 0 && isEvenRow == true)
                    jb[i][j].setBackground(color1);
                else
                    jb[i][j].setBackground(color2);
                if (backColor % 8 == 0)
                    isEvenRow = !isEvenRow;
                backColor++;
                chessBoardPanel.add(jb[i][j]);
            }
        }

        // sets the icons of all the buttons to the appropriate starting chess pieces
        jb[0][0].setIcon(new ImageIcon(getClass().getResource("/resources/BlackRook.png")));
        jb[0][1].setIcon(new ImageIcon(getClass().getResource("/resources/BlackKnight.png")));
        jb[0][2].setIcon(new ImageIcon(getClass().getResource("/resources/BlackBishop.png")));
        jb[0][3].setIcon(new ImageIcon(getClass().getResource("/resources/BlackQueen.png")));
        jb[0][4].setIcon(new ImageIcon(getClass().getResource("/resources/BlackKing.png")));
        jb[0][5].setIcon(new ImageIcon(getClass().getResource("/resources/BlackBishop.png")));
        jb[0][6].setIcon(new ImageIcon(getClass().getResource("/resources/BlackKnight.png")));
        jb[0][7].setIcon(new ImageIcon(getClass().getResource("/resources/BlackRook.png")));

        jb[7][0].setIcon(new ImageIcon(getClass().getResource("/resources/WhiteRook.png")));
        jb[7][1].setIcon(new ImageIcon(getClass().getResource("/resources/WhiteKnight.png")));
        jb[7][2].setIcon(new ImageIcon(getClass().getResource("/resources/WhiteBishop.png")));
        jb[7][3].setIcon(new ImageIcon(getClass().getResource("/resources/WhiteQueen.png")));
        jb[7][4].setIcon(new ImageIcon(getClass().getResource("/resources/WhiteKing.png")));
        jb[7][5].setIcon(new ImageIcon(getClass().getResource("/resources/WhiteBishop.png")));
        jb[7][6].setIcon(new ImageIcon(getClass().getResource("/resources/WhiteKnight.png")));
        jb[7][7].setIcon(new ImageIcon(getClass().getResource("/resources/WhiteRook.png")));

        for (int i = 0; i < 8; i++) {
            jb[1][i].setIcon(new ImageIcon(getClass().getResource("/resources/BlackPawn.png")));
            jb[6][i].setIcon(new ImageIcon(getClass().getResource("/resources/WhitePawn.png")));
        }

        // adds a PieceHandler to all the buttons
        PieceHandler ph = new PieceHandler();
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                jb[i][j].addActionListener(ph);
            }
        }

        // initializing the promotion grid so that buttons with the correct piece color
        // can be added later
        promotionPanel = new JPanel(new GridLayout(4, 1));
        promotionGrid = new JButton[4];
        promotionPanel.setVisible(false);

        // combining all of the panels together
        JPanel rightPanel = new JPanel(new BorderLayout());
        moveLog = new ChessMoveLog();
        rightPanel.add(moveLog, BorderLayout.EAST);
        add(chessBoardPanel, BorderLayout.CENTER);
        add(rightPanel, BorderLayout.EAST);
        add(promotionPanel, BorderLayout.WEST);
    }

    private class PieceHandler implements ActionListener {
        // couldn't get en passant to work in time but heres the unfinished code
        /*
         * private JButton enPassantPawn;
         * 
         * private boolean isValidEnPassantMove(ImageIcon icon, int fromRow, int
         * fromCol, int toRow, int toCol) {
         * if (isValidPawnMove(!isBlackMove, fromRow, fromCol, toRow, toCol)) {
         * JButton leftPawn = jb[toRow][toCol - 1];
         * JButton rightPawn = jb[toRow][toCol + 1];
         * 
         * // Check if the adjacent pawns of the opposite color have moved two squares
         * // forward
         * if (leftPawn != null && leftPawn.getIcon() != null &&
         * isOpponentPiece(!isBlackMove, leftPawn.getIcon())
         * && leftPawn.getClientProperty("twoStepMove") != null
         * && (boolean) leftPawn.getClientProperty("twoStepMove")) {
         * enPassantPawn = leftPawn;
         * return true;
         * }
         * 
         * if (rightPawn != null && rightPawn.getIcon() != null
         * && isOpponentPiece(!isBlackMove, rightPawn.getIcon())
         * && rightPawn.getClientProperty("twoStepMove") != null
         * && (boolean) rightPawn.getClientProperty("twoStepMove")) {
         * enPassantPawn = rightPawn;
         * return true;
         * }
         * }
         * return false;
         * }
         */

        public void actionPerformed(ActionEvent e) {
            boolean moveMade = false;
            // saves the first selected piece and updates the background colors to match
            // possible moves
            if (isPieceSelected == false && wonGame == false && pickingPromotion == false) {
                if (((JButton) e.getSource()).getIcon() != null
                        && isOpponentPiece(!isBlackMove, ((JButton) e.getSource()).getIcon())) {
                    isPieceSelected = true;
                    pieceSelectedIcon = (ImageIcon) ((JButton) e.getSource()).getIcon();
                    pieceSelected = (JButton) e.getSource();

                    for (int i = 0; i < 8; i++) {
                        for (int j = 0; j < 8; j++) {
                            if (isValidMove(pieceSelectedIcon, getRow(pieceSelected), getCol(pieceSelected), i, j) ) {
                                jb[i][j].setBackground(color3);
                            }
                        }
                    }
                    jb[getRow(pieceSelected)][getCol(pieceSelected)].setBackground(color4);
                }
            } else if (wonGame == false) {
                isPieceSelected = false;
                // more unfinished en passant code
                /*
                 * if (isValidEnPassantMove(pieceSelectedIcon, getRow(pieceSelected),
                 * getCol(pieceSelected),
                 * getRow((JButton) e.getSource()), getCol((JButton) e.getSource()))) {
                 * 
                 * // Capture the en passant pawn
                 * enPassantPawn.setIcon(null);
                 * 
                 * // Move the current pawn to the en passant square
                 * ((JButton) e.getSource()).setIcon(pieceSelectedIcon);
                 * pieceSelected.setIcon(null);
                 * 
                 * moveMade = true;
                 * String move = getMoveNotation(pieceSelected, (JButton) e.getSource()) +
                 * " (en passant)";
                 * String color = isBlackMove ? "Black" : "White";
                 * moveLog.appendMove(move, color, "");
                 * isBlackMove = !isBlackMove; // Toggle the player's turn
                 * }
                 */

                // Check for castling
                if (pieceSelectedIcon.getDescription().contains("King")) {
                    if (isValidCastlingMove(pieceSelected, (JButton) e.getSource())) {
                        performCastling(pieceSelected, (JButton) e.getSource());
                        String castlingNotation = (getCol((JButton) e.getSource()) == 6) ? "O-O" : "O-O-O";
                        String color = isBlackMove ? "White" : "Black";
                        moveLog.appendMove(castlingNotation, color, "");
                        moveMade = true;
                    }
                }

                // determines if the move will put the king in check
                if (isValidMove(pieceSelectedIcon, getRow(pieceSelected), getCol(pieceSelected),
                        getRow((JButton) e.getSource()), getCol((JButton) e.getSource()))) {
                    ImageIcon tempPieceSelectedIcon = pieceSelectedIcon;
                    ImageIcon tempDestIcon = (ImageIcon) ((JButton) e.getSource()).getIcon();

                    ((JButton) e.getSource()).setIcon(pieceSelectedIcon);
                    pieceSelected.setIcon(null);

                    // if the move doesnt put the king in check, updates the move log
                    // otherwise, undoes the move
                    if (isKingInCheck(isBlackMove)) {
                        ((JButton) e.getSource()).setIcon(tempDestIcon);
                        pieceSelected.setIcon(tempPieceSelectedIcon);
                        JOptionPane.showMessageDialog((Component) e.getSource(),
                                "This move will put your king in check!");
                    } else {
                        moveMade = true;
                        String move = getMoveNotation(pieceSelected, (JButton) e.getSource());
                        String color = isBlackMove ? "Black" : "White";
                        String pieceType = getPieceType(pieceSelectedIcon);
                        moveLog.appendMove(move, color, pieceType);
                        isBlackMove = !isBlackMove; // Toggle the player's turn
                    }
                }

                // recoloring the background to get rid of the possible move highlights
                int backColor = 1;
                boolean isEvenRow = false;
                for (int i = 0; i < 8; i++) {
                    for (int j = 0; j < 8; j++) {
                        if (backColor % 2 == 0 && isEvenRow == false || backColor % 2 != 0 && isEvenRow == true)
                            jb[i][j].setBackground(color1);
                        else
                            jb[i][j].setBackground(color2);
                        if (backColor % 8 == 0)
                            isEvenRow = !isEvenRow;
                        backColor++;
                    }
                }

                // checking if there is a single possible move that wont put the player in check
                if (moveMade) {
                    String s = isBlackMove ? "Black" : "White";
                    ImageIcon checkingIcon;
                    boolean checkmate = true;
                    ImageIcon tempPieceSelectedIcon;
                    ImageIcon tempDestIcon;

                    quadloop: for (int i = 0; i < 8; i++) {
                        for (int j = 0; j < 8; j++) {
                            checkingIcon = (ImageIcon) jb[i][j].getIcon();
                            if (checkingIcon != null && checkingIcon.toString().contains(s)) {
                                for (int k = 0; k < 8; k++) {
                                    for (int l = 0; l < 8; l++) {
                                        if (isValidMove(checkingIcon, i, j, k, l)) {
                                            tempPieceSelectedIcon = checkingIcon;
                                            tempDestIcon = (ImageIcon) jb[k][l].getIcon();
                                            jb[k][l].setIcon(checkingIcon);
                                            jb[i][j].setIcon(null);

                                            if (!isKingInCheck(isBlackMove)) {
                                                checkmate = false;
                                            }

                                            jb[k][l].setIcon(tempDestIcon);
                                            jb[i][j].setIcon(tempPieceSelectedIcon);

                                            if (!checkmate)
                                                break quadloop;
                                        }
                                    }
                                }
                            }
                        }
                    }

                    s = isBlackMove ? "White" : "Black";
                    if (checkmate) {
                        JOptionPane.showMessageDialog((Component) ((JButton) e.getSource()).getParent(),
                                "Checkmate! " + s + " has won the game!");
                        wonGame = true;
                    }

                    // pawn promotion
                    if (!wonGame) {
                        // black pawn promotion
                        if (getRow((JButton) e.getSource()) == 7
                                && pieceSelectedIcon.getDescription().contains("BlackPawn")) {
                            for (int i = 0; i < 4; i++) {
                                promotionGrid[i] = new JButton();
                                promotionGrid[i].setBackground(Color.WHITE);
                            }
                            promotionGrid[0].setIcon(new ImageIcon(getClass().getResource("/resources/BlackQueen.png")));
                            promotionGrid[1].setIcon(new ImageIcon(getClass().getResource("/resources/BlackRook.png")));
                            promotionGrid[2].setIcon(new ImageIcon(getClass().getResource("/resources/BlackBishop.png")));
                            promotionGrid[3].setIcon(new ImageIcon(getClass().getResource("/resources/BlackKnight.png")));

                            pickingPromotion = true;

                            // white pawn promotion
                        } else if (getRow((JButton) e.getSource()) == 0
                                && pieceSelectedIcon.getDescription().contains("WhitePawn")) {
                            for (int i = 0; i < 4; i++) {
                                promotionGrid[i] = new JButton();
                                promotionGrid[i].setBackground(Color.BLACK);
                            }
                            promotionGrid[0].setIcon(new ImageIcon(getClass().getResource("/resources/WhiteQueen.png")));
                            promotionGrid[1].setIcon(new ImageIcon(getClass().getResource("/resources/WhiteRook.png")));
                            promotionGrid[2].setIcon(new ImageIcon(getClass().getResource("/resources/WhiteBishop.png")));
                            promotionGrid[3].setIcon(new ImageIcon(getClass().getResource("/resources/WhiteKnight.png")));
                            pickingPromotion = true;
                        }

                        // shows the promotion panel and then checks if the promotion would put the
                        // other player in checkmate
                        if (pickingPromotion) {

                            for (int i = 0; i < 4; i++)
                                promotionPanel.add(promotionGrid[i]);
                            promotionPanel.setVisible(true);
                            for (int i = 0; i < 4; i++) {
                                promotionGrid[i].addActionListener(new ActionListener() {
                                    @Override
                                    public void actionPerformed(ActionEvent e1) {
                                        ((JButton) e.getSource()).setIcon(((JButton) e1.getSource()).getIcon());
                                        pickingPromotion = false;
                                        promotionPanel.setVisible(false);

                                        String s = isBlackMove ? "Black" : "White";
                                        ImageIcon checkingIcon;
                                        boolean checkmate = true;
                                        ImageIcon tempPieceSelectedIcon;
                                        ImageIcon tempDestIcon;

                                        quadloop: for (int i = 0; i < 8; i++) {
                                            for (int j = 0; j < 8; j++) {
                                                checkingIcon = (ImageIcon) jb[i][j].getIcon();
                                                if (checkingIcon != null && checkingIcon.toString().contains(s)) {
                                                    for (int k = 0; k < 8; k++) {
                                                        for (int l = 0; l < 8; l++) {
                                                            if (isValidMove(checkingIcon, i, j, k, l)) {
                                                                tempPieceSelectedIcon = checkingIcon;
                                                                tempDestIcon = (ImageIcon) jb[k][l].getIcon();
                                                                jb[k][l].setIcon(checkingIcon);
                                                                jb[i][j].setIcon(null);

                                                                if (!isKingInCheck(isBlackMove)) {
                                                                    checkmate = false;
                                                                }

                                                                jb[k][l].setIcon(tempDestIcon);
                                                                jb[i][j].setIcon(tempPieceSelectedIcon);

                                                                if (!checkmate)
                                                                    break quadloop;
                                                            }
                                                        }
                                                    }
                                                }
                                            }
                                        }

                                        s = isBlackMove ? "White" : "Black";
                                        if (checkmate) {
                                            JOptionPane.showMessageDialog(
                                                    (Component) ((JButton) e.getSource()).getParent(),
                                                    "Checkmate! " + s + " has won the game!");
                                            wonGame = true;
                                        }
                                    }
                                });
                            }
                            JOptionPane.showMessageDialog((Component) ((JButton) e.getSource()).getParent(),
                                    "Pick a Promotion!");
                        }
                    }
                }
            }
        }

        // self-explanatory
        private int getRow(JButton button) {
            for (int i = 0; i < 8; i++) {
                for (int j = 0; j < 8; j++) {
                    if (jb[i][j] == button) {
                        return i;
                    }
                }
            }
            return 1;
        }

        private int getCol(JButton button) {
            for (int i = 0; i < 8; i++) {
                for (int j = 0; j < 8; j++) {
                    if (jb[i][j] == button) {
                        return j;
                    }
                }
            }
            return 1;
        }

        // converts the array notation to standard chess notation
        private String getMoveNotation(JButton fromButton, JButton toButton) {
            char fromCol = (char) ('a' + getCol(fromButton));
            int fromRow = 8 - getRow(fromButton);
            char toCol = (char) ('a' + getCol(toButton));
            int toRow = 8 - getRow(toButton);
            return String.format("%s%d to %s%d", fromCol, fromRow, toCol, toRow);
        }

        // used to display what piece was moved on the move log
        private String getPieceType(ImageIcon icon) {
            if (icon != null) {
                String description = icon.getDescription();
                if (description != null && description.contains("Black")) {
                    if (description.contains("Pawn"))
                        return "";
                    else if (description.contains("Rook"))
                        return "R";
                    else if (description.contains("Knight"))
                        return "N";
                    else if (description.contains("Bishop"))
                        return "B";
                    else if (description.contains("Queen"))
                        return "Q";
                    else if (description.contains("King"))
                        return "K";
                } else if (description != null && description.contains("White")) {
                    if (description.contains("Pawn"))
                        return "";
                    else if (description.contains("Rook"))
                        return "R";
                    else if (description.contains("Knight"))
                        return "N";
                    else if (description.contains("Bishop"))
                        return "B";
                    else if (description.contains("Queen"))
                        return "Q";
                    else if (description.contains("King"))
                        return "K";
                }
            }
            return "Unknown";
        }

        // checks if a player can castle
        private boolean isValidCastlingMove(JButton kingButton, JButton destinationButton) {
            int fromCol = getCol(kingButton);
            int fromRow = getRow(kingButton);
            int toCol = getCol(destinationButton);
            int toRow = getRow(destinationButton);

            // must move exactly two squares horizontally
            if (fromRow != toRow || Math.abs(fromCol - toCol) != 2) {
                return false;
            }

            // destination must be empty
            if (jb[toRow][toCol].getIcon() != null) {
                return false;
            }

            // king-side castling
            if (toCol == 6) {
                return isValidKingSideCastling();
            }

            // queen-side castling
            if (toCol == 2) {
                return isValidQueenSideCastling();
            }

            return false;
        }


        // check if squares between the king and rook are empty
        private boolean isValidKingSideCastling() {
            return jb[getRow(pieceSelected)][5].getIcon() == null &&
                    jb[getRow(pieceSelected)][6].getIcon() == null;
        }

        // check if squares between the king and rook are empty
        private boolean isValidQueenSideCastling() {
            return jb[getRow(pieceSelected)][3].getIcon() == null &&
                    jb[getRow(pieceSelected)][2].getIcon() == null &&
                    jb[getRow(pieceSelected)][1].getIcon() == null;
        }

        // make the pieces move for castling
        private void performCastling(JButton kingButton, JButton destinationButton) {
            int toCol = getCol(destinationButton);
            int toRow = getRow(destinationButton);

            // move the king
            destinationButton.setIcon(kingButton.getIcon());
            kingButton.setIcon(null);

            // move the rook
            if (toCol == 6) {
                // king-side castling
                jb[toRow][5].setIcon(jb[toRow][7].getIcon());
                jb[toRow][7].setIcon(null);
            } else if (toCol == 2) {
                // queen-side castling
                jb[toRow][3].setIcon(jb[toRow][0].getIcon());
                jb[toRow][0].setIcon(null);
            }

            // toggle player's turn
            isBlackMove = !isBlackMove;
        }
    }

    // combines all of the valid move functions together
    private boolean isValidMove(ImageIcon icon, int fromRow, int fromCol, int toRow, int toCol) {
        if (isBlackMove == false && icon.getDescription().contains("White")
                || isBlackMove == true && icon.getDescription().contains("Black")) {
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
            } else
                return isValidKingMove(fromRow, fromCol, toRow, toCol);
        } else
            return false;
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

        // unfinished en passant capture code
        /*
         * if (fromRow == (isBlack ? 3 : 4) && toRow == fromRow + direction &&
         * Math.abs(toCol - fromCol) == 1) {
         * JButton adjacentPawn = jb[fromRow][toCol];
         * 
         * // Check if the adjacent pawn of the opposite color has moved two squares
         * // forward
         * if (adjacentPawn != null && isOpponentPiece(!isBlack, adjacentPawn.getIcon())
         * && adjacentPawn.getClientProperty("twoStepMove") != null
         * && (boolean) adjacentPawn.getClientProperty("twoStepMove")) {
         * enPassantPawn = adjacentPawn;
         * return true;
         * }
         * }
         */

        // pawn capture diagonally
        return Math.abs(toCol - fromCol) == 1 && toRow == fromRow + direction
                && isOpponentPiece(isBlack, jb[toRow][toCol].getIcon());
    }

    private boolean isValidRookMove(int fromRow, int fromCol, int toRow, int toCol) {
        boolean continueSearch = true, isBlack = isOpponentPiece(false, jb[fromRow][fromCol].getIcon());
        int maxDistance = 0;
        if (fromRow == toRow && fromCol == toCol)
            return false;

        if (fromCol == toCol) {
            // going north
            if (fromRow - toRow > 0) {
                while (maxDistance != fromRow && continueSearch == true) {
                    if ((jb[fromRow - maxDistance - 1][fromCol]).getIcon() == null)
                        maxDistance++;
                    else if (isOpponentPiece(isBlack, (jb[fromRow - maxDistance - 1][fromCol]).getIcon())) {
                        maxDistance++;
                        continueSearch = false;
                    } else
                        continueSearch = false;
                }
                return maxDistance >= fromRow - toRow;
            }

            // going south
            while (maxDistance != 7 - fromRow && continueSearch == true) {
                if ((jb[fromRow + maxDistance + 1][fromCol]).getIcon() == null)
                    maxDistance++;
                else if (isOpponentPiece(isBlack, (jb[fromRow + maxDistance + 1][fromCol]).getIcon())) {
                    maxDistance++;
                    continueSearch = false;
                } else
                    continueSearch = false;
            }
            return maxDistance >= toRow - fromRow;
        }

        if (fromRow == toRow) {
            // going west
            if (fromCol - toCol > 0) {
                while (maxDistance != fromCol && continueSearch == true) {
                    if ((jb[fromRow][fromCol - maxDistance - 1]).getIcon() == null)
                        maxDistance++;
                    else if (isOpponentPiece(isBlack, (jb[fromRow][fromCol - maxDistance - 1]).getIcon())) {
                        maxDistance++;
                        continueSearch = false;
                    } else
                        continueSearch = false;
                }
                return maxDistance >= fromCol - toCol;
            }

            // going east
            while (maxDistance != 7 - fromCol && continueSearch == true) {
                if ((jb[fromRow][fromCol + maxDistance + 1]).getIcon() == null)
                    maxDistance++;
                else if (isOpponentPiece(isBlack, (jb[fromRow][fromCol + maxDistance + 1]).getIcon())) {
                    maxDistance++;
                    continueSearch = false;
                } else
                    continueSearch = false;
            }
            return maxDistance >= toCol - fromCol;
        }

        return false;
    }

    private boolean isValidKnightMove(int fromRow, int fromCol, int toRow, int toCol) {
        // knight moves in an L-shape (two squares in one direction and one square
        // perpendicular)
        int rowDiff = Math.abs(toRow - fromRow);
        int colDiff = Math.abs(toCol - fromCol);
        boolean isBlack = isOpponentPiece(false, jb[fromRow][fromCol].getIcon());
        if (isOpponentPiece(isBlack, jb[toRow][toCol].getIcon()) || jb[toRow][toCol].getIcon() == null)
            return (rowDiff == 2 && colDiff == 1) || (rowDiff == 1 && colDiff == 2);
        return false;
    }

    private boolean isValidBishopMove(int fromRow, int fromCol, int toRow, int toCol) {
        // bishop moves diagonally
        boolean continueSearch = true, isBlack = isOpponentPiece(false, jb[fromRow][fromCol].getIcon());
        int maxDistance = 0;

        if (fromRow == toRow && fromCol == toCol || Math.abs(toRow - fromRow) != Math.abs(toCol - fromCol))
            return false;

        if (fromRow - toRow > 0) {
            // going northwest
            if (fromCol - toCol > 0) {
                while (maxDistance != fromRow && maxDistance != fromCol && continueSearch == true) {
                    if ((jb[fromRow - maxDistance - 1][fromCol - maxDistance - 1]).getIcon() == null)
                        maxDistance++;
                    else if (isOpponentPiece(isBlack,
                            (jb[fromRow - maxDistance - 1][fromCol - maxDistance - 1]).getIcon())) {
                        maxDistance++;
                        continueSearch = false;
                    } else
                        continueSearch = false;
                }
                return maxDistance >= fromRow - toRow && maxDistance >= fromCol - toCol;
            }

            // going northeast
            while (maxDistance != fromRow && maxDistance != 7 - fromCol && continueSearch == true) {
                if ((jb[fromRow - maxDistance - 1][fromCol + maxDistance + 1]).getIcon() == null)
                    maxDistance++;
                else if (isOpponentPiece(isBlack,
                        (jb[fromRow - maxDistance - 1][fromCol + maxDistance + 1]).getIcon())) {
                    maxDistance++;
                    continueSearch = false;
                } else
                    continueSearch = false;
            }
            return maxDistance >= fromRow - toRow && maxDistance >= toCol - fromCol;
        }

        // going southwest
        if (fromCol - toCol > 0) {
            while (maxDistance != 7 - fromRow && maxDistance != fromCol && continueSearch == true) {
                if ((jb[fromRow + maxDistance + 1][fromCol - maxDistance - 1]).getIcon() == null)
                    maxDistance++;
                else if (isOpponentPiece(isBlack,
                        (jb[fromRow + maxDistance + 1][fromCol - maxDistance - 1]).getIcon())) {
                    maxDistance++;
                    continueSearch = false;
                } else
                    continueSearch = false;
            }
            return maxDistance >= toRow - fromRow && maxDistance >= fromCol - toCol;
        }

        // going southeast
        while (maxDistance != 7 - fromRow && maxDistance != 7 - fromCol && continueSearch == true) {
            if ((jb[fromRow + maxDistance + 1][fromCol + maxDistance + 1]).getIcon() == null)
                maxDistance++;
            else if (isOpponentPiece(isBlack, (jb[fromRow + maxDistance + 1][fromCol + maxDistance + 1]).getIcon())) {
                maxDistance++;
                continueSearch = false;
            } else
                continueSearch = false;
        }
        return maxDistance >= toRow - fromRow && maxDistance >= toCol - fromCol;
    }

    private boolean isValidQueenMove(int fromRow, int fromCol, int toRow, int toCol) {
        // queen moves horizontally, vertically, or diagonally
        return isValidRookMove(fromRow, fromCol, toRow, toCol) || isValidBishopMove(fromRow, fromCol, toRow, toCol);
    }

    private boolean isValidKingMove(int fromRow, int fromCol, int toRow, int toCol) {
        // king moves one square in any direction
        int rowDiff = Math.abs(toRow - fromRow);
        int colDiff = Math.abs(toCol - fromCol);
        boolean isBlack = isOpponentPiece(false, jb[fromRow][fromCol].getIcon());

        if (rowDiff == 0 && colDiff == 0)
            return false;

        if (isOpponentPiece(isBlack, jb[toRow][toCol].getIcon()) || jb[toRow][toCol].getIcon() == null)
            return rowDiff <= 1 && colDiff <= 1;
        return false;
    }

    // used to distinguish if capturable or in check
    private boolean isOpponentPiece(boolean isBlack, Icon icon) {
        if (icon != null)
            return (isBlack && icon.toString().contains("White")) ||
                    (!isBlack && icon.toString().contains("Black"));
        return false;
    }

    private boolean isKingInCheck(boolean isBlack) {
        // find the king's position
        int kingRow = -1;
        int kingCol = -1;
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                ImageIcon icon = (ImageIcon) jb[i][j].getIcon();
                if (icon != null && ((isBlack && icon.toString().contains("BlackKing"))
                        || (!isBlack && icon.toString().contains("WhiteKing")))) {
                    kingRow = i;
                    kingCol = j;
                    break;
                }
            }
        }

        // check if any opponent's piece can attack the king
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                ImageIcon icon = (ImageIcon) jb[i][j].getIcon();
                if (icon != null && isOpponentPiece(isBlack, icon)) {
                    isBlackMove = !isBlackMove;
                    if (isValidMove(icon, i, j, kingRow, kingCol)) {
                        isBlackMove = !isBlackMove;
                        return true;
                    }
                    isBlackMove = !isBlackMove;
                }
            }
        }

        return false;
    }
}
