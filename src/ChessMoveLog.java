import javax.swing.*;
import java.awt.*;

public class ChessMoveLog extends JScrollPane {
    private JTextArea moveLog;
    private int moveNumber;

    public ChessMoveLog() {
        moveLog = new JTextArea();
        moveLog.setEditable(false);
        setViewportView(moveLog);
        setPreferredSize(new Dimension(200, getHeight()));
        moveNumber = 1;
    }

    // adds each move to the log as the game goes on
    public void appendMove(String move, String color, String pieceType) {
        moveLog.append(moveNumber + ". " + color + ": " + pieceType + "" + move + "\n");
        moveLog.setCaretPosition(moveLog.getDocument().getLength());
        moveNumber++;
    }
}