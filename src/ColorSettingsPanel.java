import javax.swing.*;
import javax.swing.event.ChangeListener;
import javax.swing.plaf.basic.BasicSliderUI;
import javax.swing.event.ChangeEvent;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;

public class ColorSettingsPanel extends JPanel {
    private Graphics2D g2d;
    private Color color1, color2, color3, color4;
    private JPanel oval1, oval2, oval3, oval4, labelPanel1, labelPanel2, labelPanel3, labelPanel4;
    private JSlider r1, r2, r3, r4, g1, g2, g3, g4, b1, b2, b3, b4;
    private JButton defaultButton, backButton;
    private JLabel blankSpace, label1, label2, label3, label4;

    public ColorSettingsPanel(JFrame frame) {
        // initializing all of the components
        color1 = new Color(71, 56, 44);
        color2 = new Color(173, 130, 95);
        color3 = new Color(125, 240, 144);
        color4 = new Color(255, 92, 92);
        label1 = new JLabel("ChessBoard Color 1");
        label2 = new JLabel("ChessBoard Color 2");
        label3 = new JLabel("Possibles Moves Color");
        label4 = new JLabel("Selected Piece Color");
        labelPanel1 = new JPanel(new FlowLayout(FlowLayout.CENTER));
        labelPanel1.add(label1);
        labelPanel2 = new JPanel(new FlowLayout(FlowLayout.CENTER));
        labelPanel2.add(label2);
        labelPanel3 = new JPanel(new FlowLayout(FlowLayout.CENTER));
        labelPanel3.add(label3);
        labelPanel4 = new JPanel(new FlowLayout(FlowLayout.CENTER));
        labelPanel4.add(label4);
        oval1 = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                g2d = (Graphics2D) g;
                g2d.setColor(color1);
                g2d.fillOval(0, 0, getWidth(), getHeight());
            }
        };
        oval2 = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                g2d = (Graphics2D) g;
                g2d.setColor(color2);
                g2d.fillOval(0, 0, getWidth(), getHeight());
            }
        };
        oval3 = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                g2d = (Graphics2D) g;
                g2d.setColor(color3);
                g2d.fillOval(0, 0, getWidth(), getHeight());
            }
        };
        oval4 = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                g2d = (Graphics2D) g;
                g2d.setColor(color4);
                g2d.fillOval(0, 0, getWidth(), getHeight());
            }
        };
        initializeSliders();
        blankSpace = new JLabel();
        defaultButton = new JButton("Restore Default Settings");
        backButton = new JButton("Back to Main Menu");

        // adding all of the components to an organized grid
        setLayout(new GridLayout(6, 4));
        add(labelPanel1);
        add(labelPanel2);
        add(labelPanel3);
        add(labelPanel4);
        add(oval1);
        add(oval2);
        add(oval3);
        add(oval4);
        add(r1);
        add(r2);
        add(r3);
        add(r4);
        add(g1);
        add(g2);
        add(g3);
        add(g4);
        add(b1);
        add(b2);
        add(b3);
        add(b4);
        add(blankSpace);
        add(defaultButton);
        add(backButton);

        // checks if the back button or default settings button has been clicked
        ActionListener buttonListener = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // switches panels to the main menu
                if (e.getSource() == backButton)
                    ((CardLayout) frame.getContentPane().getLayout()).show(frame.getContentPane(), "MainMenu");
                // resets all of the chess colors to the default
                else if (e.getSource() == defaultButton) {
                    color1 = new Color(71, 56, 44);
                    color2 = new Color(173, 130, 95);
                    color3 = new Color(125, 240, 144);
                    color4 = new Color(255, 92, 92);

                    r1.setValue(71);
                    r2.setValue(173);
                    r3.setValue(125);
                    r4.setValue(255);
                    g1.setValue(56);
                    g2.setValue(130);
                    g3.setValue(240);
                    g4.setValue(92);
                    b1.setValue(44);
                    b2.setValue(95);
                    b3.setValue(144);
                    b4.setValue(92);
                }
            }
        };
        backButton.addActionListener(buttonListener);
        defaultButton.addActionListener(buttonListener);

        // update the size of the inner color panels when the outer panel is resized
        addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                oval1.setSize(new Dimension(getWidth() / 2, getHeight() / 2));
                oval1.revalidate();
                oval1.repaint();

                oval2.setSize(new Dimension(getWidth() / 2, getHeight() / 2));
                oval2.revalidate();
                oval2.repaint();

                oval3.setSize(new Dimension(getWidth() / 2, getHeight() / 2));
                oval3.revalidate();
                oval3.repaint();

                oval4.setSize(new Dimension(getWidth() / 2, getHeight() / 2));
                oval4.revalidate();
                oval4.repaint();
            }
        });
    }

    // self-explanatory
    public Color getColor1() {
        return color1;
    }

    public Color getColor2() {
        return color2;
    }

    public Color getColor3() {
        return color3;
    }

    public Color getColor4() {
        return color4;
    }

    // probably could have created an inner class for these sliders but at first
    // there was only two buttons
    // and this code looked a lot smaller
    private void initializeSliders() {
        // changes the color of the ovals whenever a color slider is changed to
        // represent the rgb values
        // of the sliders
        ChangeListener sliderListener = new ChangeListener() {
            public void stateChanged(ChangeEvent e) {
                if (e.getSource() == r1) {
                    color1 = new Color(r1.getValue(), color1.getGreen(), color1.getBlue());
                    oval1.repaint();
                } else if (e.getSource() == r2) {
                    color2 = new Color(r2.getValue(), color2.getGreen(), color2.getBlue());
                    oval2.repaint();
                } else if (e.getSource() == r3) {
                    color3 = new Color(r3.getValue(), color3.getGreen(), color3.getBlue());
                    oval3.repaint();
                } else if (e.getSource() == r4) {
                    color4 = new Color(r4.getValue(), color4.getGreen(), color4.getBlue());
                    oval4.repaint();
                } else if (e.getSource() == g1) {
                    color1 = new Color(color1.getRed(), g1.getValue(), color1.getBlue());
                    oval1.repaint();
                } else if (e.getSource() == g2) {
                    color2 = new Color(color2.getRed(), g2.getValue(), color2.getBlue());
                    oval2.repaint();
                } else if (e.getSource() == g3) {
                    color3 = new Color(color3.getRed(), g3.getValue(), color3.getBlue());
                    oval3.repaint();
                } else if (e.getSource() == g4) {
                    color4 = new Color(color4.getRed(), g4.getValue(), color4.getBlue());
                    oval4.repaint();
                } else if (e.getSource() == b1) {
                    color1 = new Color(color1.getRed(), color1.getGreen(), b1.getValue());
                    oval1.repaint();
                } else if (e.getSource() == b2) {
                    color2 = new Color(color2.getRed(), color2.getGreen(), b2.getValue());
                    oval2.repaint();
                } else if (e.getSource() == b3) {
                    color3 = new Color(color3.getRed(), color3.getGreen(), b3.getValue());
                    oval3.repaint();
                } else if (e.getSource() == b4) {
                    color4 = new Color(color4.getRed(), color4.getGreen(), b4.getValue());
                    oval4.repaint();
                }
            }
        };

        // initializes all of the sliders with default color values
        r1 = new JSlider(JSlider.HORIZONTAL, 0, 255, 71);
        r1.setPaintTicks(true);
        r1.setPaintLabels(true);
        r1.setMajorTickSpacing(5);
        r1.setLabelTable(r1.createStandardLabels(255));
        r1.setUI(new ColorSliderUI(Color.RED));
        r1.addChangeListener(sliderListener);

        r2 = new JSlider(JSlider.HORIZONTAL, 0, 255, 173);
        r2.setPaintTicks(true);
        r2.setPaintLabels(true);
        r2.setMajorTickSpacing(5);
        r2.setLabelTable(r2.createStandardLabels(255));
        r2.setUI(new ColorSliderUI(Color.RED));
        r2.addChangeListener(sliderListener);

        r3 = new JSlider(JSlider.HORIZONTAL, 0, 255, 125);
        r3.setPaintTicks(true);
        r3.setPaintLabels(true);
        r3.setMajorTickSpacing(5);
        r3.setLabelTable(r3.createStandardLabels(255));
        r3.setUI(new ColorSliderUI(Color.RED));
        r3.addChangeListener(sliderListener);

        r4 = new JSlider(JSlider.HORIZONTAL, 0, 255, 255);
        r4.setPaintTicks(true);
        r4.setPaintLabels(true);
        r4.setMajorTickSpacing(5);
        r4.setLabelTable(r4.createStandardLabels(255));
        r4.setUI(new ColorSliderUI(Color.RED));
        r4.addChangeListener(sliderListener);

        g1 = new JSlider(JSlider.HORIZONTAL, 0, 255, 56);
        g1.setPaintTicks(true);
        g1.setPaintLabels(true);
        g1.setMajorTickSpacing(5);
        g1.setLabelTable(g1.createStandardLabels(255));
        g1.setUI(new ColorSliderUI(Color.GREEN));
        g1.addChangeListener(sliderListener);

        g2 = new JSlider(JSlider.HORIZONTAL, 0, 255, 130);
        g2.setPaintTicks(true);
        g2.setPaintLabels(true);
        g2.setMajorTickSpacing(5);
        g2.setLabelTable(g2.createStandardLabels(255));
        g2.setUI(new ColorSliderUI(Color.GREEN));
        g2.addChangeListener(sliderListener);

        g3 = new JSlider(JSlider.HORIZONTAL, 0, 255, 240);
        g3.setPaintTicks(true);
        g3.setPaintLabels(true);
        g3.setMajorTickSpacing(5);
        g3.setLabelTable(g3.createStandardLabels(255));
        g3.setUI(new ColorSliderUI(Color.GREEN));
        g3.addChangeListener(sliderListener);

        g4 = new JSlider(JSlider.HORIZONTAL, 0, 255, 92);
        g4.setPaintTicks(true);
        g4.setPaintLabels(true);
        g4.setMajorTickSpacing(5);
        g4.setLabelTable(g4.createStandardLabels(255));
        g4.setUI(new ColorSliderUI(Color.GREEN));
        g4.addChangeListener(sliderListener);

        b1 = new JSlider(JSlider.HORIZONTAL, 0, 255, 44);
        b1.setPaintTicks(true);
        b1.setPaintLabels(true);
        b1.setMajorTickSpacing(5);
        b1.setLabelTable(b1.createStandardLabels(255));
        b1.setUI(new ColorSliderUI(Color.BLUE));
        b1.addChangeListener(sliderListener);

        b2 = new JSlider(JSlider.HORIZONTAL, 0, 255, 95);
        b2.setPaintTicks(true);
        b2.setPaintLabels(true);
        b2.setMajorTickSpacing(5);
        b2.setLabelTable(b2.createStandardLabels(255));
        b2.setUI(new ColorSliderUI(Color.BLUE));
        b2.addChangeListener(sliderListener);

        b3 = new JSlider(JSlider.HORIZONTAL, 0, 255, 144);
        b3.setPaintTicks(true);
        b3.setPaintLabels(true);
        b3.setMajorTickSpacing(5);
        b3.setLabelTable(b3.createStandardLabels(255));
        b3.setUI(new ColorSliderUI(Color.BLUE));
        b3.addChangeListener(sliderListener);

        b4 = new JSlider(JSlider.HORIZONTAL, 0, 255, 92);
        b4.setPaintTicks(true);
        b4.setPaintLabels(true);
        b4.setMajorTickSpacing(5);
        b4.setLabelTable(b4.createStandardLabels(255));
        b4.setUI(new ColorSliderUI(Color.BLUE));
        b4.addChangeListener(sliderListener);
    }

    // allows the sliders to be the same color as the rgb value that they control
    public class ColorSliderUI extends BasicSliderUI {

        private Color trackColor;

        public ColorSliderUI(Color c) {
            super();
            this.trackColor = c;
        }

        @Override
        protected Color getShadowColor() {
            return trackColor;
        }

        @Override
        protected Color getHighlightColor() {
            return trackColor;
        }
    }
}