import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Chess {
    private JFrame frame;
    private JPanel mainMenuPanel;
    private ChessPanel chessPanel;
    private ColorSettingsPanel colorSettingsPanel;
    private CardLayout cardLayout;
    public Color color1, color2, color3, color4;

    public Chess() {
        // use CardLayout to switch between panels
        frame = new JFrame("Chess");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        cardLayout = new CardLayout();
        frame.setLayout(cardLayout);

        // create the main menu panel
        mainMenuPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                color1 = colorSettingsPanel.getColor1();
                color2 = colorSettingsPanel.getColor2();
                Graphics2D g2d = (Graphics2D) g;
                int backColor = 1;
                boolean isEvenRow = false;
                for (int i = 0; i < 8; i++) {
                    for (int j = 0; j < 8; j++) {
                        if (backColor % 2 == 0 && isEvenRow == false || backColor % 2 != 0 && isEvenRow == true)
                            g2d.setColor(color1);
                        else
                            g2d.setColor(color2);
                        g2d.fillRect(getWidth() / 8 * i, getHeight() / 8 * j, getWidth() / 8 + 10,
                                getHeight() / 8 + 10);
                        if (backColor % 8 == 0)
                            isEvenRow = !isEvenRow;
                        backColor++;
                    }
                }

            }
        };
        mainMenuPanel.setLayout(new BoxLayout(mainMenuPanel, BoxLayout.Y_AXIS));
        JButton startButton = new JButton("Start Chess");
        startButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // switch to the ChessPanel when the button is clicked
                showChessPanel();
            }
        });
        JButton colorSettingsButton = new JButton("Color Settings");
        colorSettingsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // switch to the ChessPanel when the button is clicked
                showColorSettingsPanel();
            }
        });
        startButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        colorSettingsButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        mainMenuPanel.add(Box.createVerticalGlue());
        mainMenuPanel.add(startButton);
        mainMenuPanel.add(Box.createVerticalStrut(10));
        mainMenuPanel.add(colorSettingsButton);
        mainMenuPanel.add(Box.createVerticalGlue());

        // create the ColorSettingsPanel
        colorSettingsPanel = new ColorSettingsPanel(frame);

        // add the first 2 panels to the frame
        frame.add(mainMenuPanel, "MainMenu");
        frame.add(colorSettingsPanel, "ColorSettingsPanel");

        // show the main menu initially
        cardLayout.show(frame.getContentPane(), "MainMenu");

        frame.setSize(800, 800);
        frame.setVisible(true);
    }

    // only creates the chess panel after the color settings have been decided and
    // the user clicks play
    private void showChessPanel() {
        color1 = colorSettingsPanel.getColor1();
        color2 = colorSettingsPanel.getColor2();
        color3 = colorSettingsPanel.getColor3();
        color4 = colorSettingsPanel.getColor4();
        chessPanel = new ChessPanel(color1, color2, color3, color4);
        frame.add(chessPanel, "ChessPanel");
        cardLayout.show(frame.getContentPane(), "ChessPanel");
    }

    // self-explanatory
    private void showColorSettingsPanel() {
        cardLayout.show(frame.getContentPane(), "ColorSettingsPanel");
    }

    // starting the application
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new Chess();
            }
        });
    }
}