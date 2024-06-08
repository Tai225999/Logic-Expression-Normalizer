package UI;

import algorithm.Minterm;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Line2D;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.List;

import UI.DrawingUtils.*;
import ui.DrawingUtils.ORShape;

public class LogicCircuitDiagram extends JFrame {
    private List<Minterm> mintermList;
    private int numberOfVariables;
    private boolean calculateStyle;

    public LogicCircuitDiagram(List<Minterm> mintermList, int numberOfVariables, boolean calculateStyle){
        this.mintermList = mintermList;
        this.numberOfVariables = numberOfVariables;
        this.calculateStyle = calculateStyle;

        this.setSize(500, 500);
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        this.setTitle("Logic Circuit Diagram");
        this.setLocationRelativeTo(null);

        // Create a new JPanel with overridden paintComponent method
        JPanel panel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                // Convert the Graphics object to a Graphics2D object
                Graphics2D g2d = (Graphics2D) g;
                // Draw the logic circuit diagram
                BufferedImage logicCircuitDiagram = draw_logic_circuit();
                g2d.drawImage(logicCircuitDiagram, 0, 0, this);
            }
        };

        // Add the JPanel to the JFrame
        this.add(panel);
        this.setVisible(true);

        JButton saveButton = new JButton("Save");
        saveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                BufferedImage logicCircuitDiagram = draw_logic_circuit();
                JFileChooser fileChooser = new JFileChooser();
                fileChooser.setDialogTitle("Specify a file to save");

                int userSelection = fileChooser.showSaveDialog(null);

                if (userSelection == JFileChooser.APPROVE_OPTION) {
                    File fileToSave = fileChooser.getSelectedFile();
                    String filePath = fileToSave.getAbsolutePath();
                    if(!filePath.toLowerCase().endsWith(".png")) {
                        filePath = filePath + ".png";
                        fileToSave = new File(filePath);
                    }
                    try {
                        ImageIO.write(logicCircuitDiagram, "png", fileToSave);
                        JOptionPane.showMessageDialog(null, "Image saved successfully!");
                    } catch (IOException ioException) {
                        JOptionPane.showMessageDialog(null, "Error while saving image.");
                        ioException.printStackTrace();
                    }
                }
            }
        });

        JButton returnButton = new JButton("Return");
        returnButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                LogicCircuitDiagram.this.dispose();
            }
        });

        JPanel buttonPanel = new JPanel();
        buttonPanel.add(saveButton);
        buttonPanel.add(returnButton);

        this.add(buttonPanel, BorderLayout.SOUTH);
        this.setVisible(true);
    }

    private BufferedImage draw_logic_circuit() {
        double DISTANCE_BETWEEN_COLUMN = 25;
        double DISTANCE_BETWEEN_ROW = 25, DISTANCE_BETWEEN_ROW_GROUP = 60, DOT_SIZE = 7, ARROWHEAD_SIZEX = 12, ARROWHEAD_SIZEY = 7;

        double imageHeight = 0, imageWidth = 0;

        // Calculate the image height and width
        imageHeight += 150;
        imageHeight += (mintermList.size()-1)*DISTANCE_BETWEEN_ROW_GROUP;
        for (Minterm minterm: mintermList) {
            System.out.println(minterm.getPositionOfBinaries() + " " + minterm.getBinaryRepresentation());
            imageHeight += (minterm.getPositionOfBinaries().size()-1)*DISTANCE_BETWEEN_ROW;
        }
        imageWidth += numberOfVariables*2*DISTANCE_BETWEEN_COLUMN;
        imageWidth += 300;

        // Create a new BufferedImage object with the calculated height and width
        BufferedImage ret = new BufferedImage((int) imageWidth, (int) imageHeight, BufferedImage.TYPE_3BYTE_BGR);

        Graphics2D g2d = ret.createGraphics();
        Rectangle2D.Double r = new Rectangle2D.Double(0,0,imageWidth, imageHeight);
        g2d.setColor(new Color(245, 245, 245));
        g2d.fill(r);

        g2d.setColor(Color.BLACK);
        g2d.setFont(new Font("TimesRoman", Font.BOLD, 20));

        // Draw the logic circuit diagram
        // Draw the input variables
        for(int i = 0; i < 2*numberOfVariables; i++) {
            g2d.draw(new Line2D.Double(DISTANCE_BETWEEN_COLUMN*(i+1), 50, DISTANCE_BETWEEN_COLUMN*(i+1), imageHeight-50));
            if(i%2==1) {
                g2d.drawString(String.valueOf((char)('A'+i/2))+"\u0305",(int) DISTANCE_BETWEEN_COLUMN*(i+1)-6, 45);
            }else {
                g2d.drawString(String.valueOf((char)('A'+i/2)),(int) DISTANCE_BETWEEN_COLUMN*(i+1)-6, 45);
            }
        }

        if(mintermList.size() > 1) {
            if(calculateStyle) {
                g2d.fill(new ORShape(imageWidth-125, (imageHeight-DISTANCE_BETWEEN_ROW*(mintermList.size()))/2, mintermList.size()));
            }else{
                g2d.fill(new ANDShape(imageWidth-125, (imageHeight-DISTANCE_BETWEEN_ROW*(mintermList.size()))/2, mintermList.size()));
            }
        }else {
            g2d.draw(new Line2D.Double(imageWidth-125, imageHeight/2, imageWidth-75, imageHeight/2));
        }

        g2d.draw(new Line2D.Double(imageWidth-75, imageHeight/2, imageWidth-25, imageHeight/2));
        g2d.fillPolygon(new int[]{(int) (imageWidth-25), (int) (imageWidth-25-ARROWHEAD_SIZEX), (int) (imageWidth-25-ARROWHEAD_SIZEX)},new int[]{(int) (imageHeight/2), (int) (imageHeight/2+ARROWHEAD_SIZEY), (int) (imageHeight/2-ARROWHEAD_SIZEY)},3);

        double currentImageHeight = 75;
        int group = 0;

        for(Minterm minterm: mintermList) {
            if(minterm.getPositionOfBinaries().size() > 1) {
                if(calculateStyle) {
                    System.out.println("AND");
                    g2d.fill(new ANDShape(imageWidth-250, currentImageHeight-DISTANCE_BETWEEN_ROW/2, minterm.getPositionOfBinaries().size()));
                }else {
                    System.out.println("OR");
                    g2d.fill(new ORShape(imageWidth-250, currentImageHeight-DISTANCE_BETWEEN_ROW/2, minterm.getPositionOfBinaries().size()));
                }
            }else {
                g2d.draw(new Line2D.Double(imageWidth-250, currentImageHeight, imageWidth-200, currentImageHeight));
            }
            double temp = Math.abs((2*group+1-mintermList.size())/2)*DISTANCE_BETWEEN_COLUMN/2;
            g2d.draw(new Line2D.Double(imageWidth-200, currentImageHeight+(minterm.getPositionOfBinaries().size()-1)*DISTANCE_BETWEEN_ROW/2, imageWidth-160+temp, currentImageHeight+(minterm.getPositionOfBinaries().size()-1)*DISTANCE_BETWEEN_ROW/2));
            g2d.draw(new Line2D.Double(imageWidth-160+temp, currentImageHeight+(minterm.getPositionOfBinaries().size()-1)*DISTANCE_BETWEEN_ROW/2, imageWidth-160+temp, (imageHeight-DISTANCE_BETWEEN_ROW*(mintermList.size()-2*group-1))/2));
            g2d.draw(new Line2D.Double(imageWidth-160+temp, (imageHeight-DISTANCE_BETWEEN_ROW*(mintermList.size()-2*group-1))/2, imageWidth-125, (imageHeight-DISTANCE_BETWEEN_ROW*(mintermList.size()-2*group-1))/2));
            group++;

            for(int j: minterm.getPositionOfBinaries()) {
                int pos = j*2;
                if (calculateStyle) {
                    if (minterm.getBinaryRepresentation().charAt(j) == '0') {
                        pos++;
                    }
                }
                else{
                    if (minterm.getBinaryRepresentation().charAt(j) == '1') {
                        pos++;
                    }
                }

                g2d.fill(new Ellipse2D.Double(DISTANCE_BETWEEN_COLUMN*(pos+1) - DOT_SIZE/2, currentImageHeight - DOT_SIZE/2, DOT_SIZE, DOT_SIZE));
                g2d.draw(new Line2D.Double(DISTANCE_BETWEEN_COLUMN*(pos+1), currentImageHeight, imageWidth-250, currentImageHeight));
                currentImageHeight += DISTANCE_BETWEEN_ROW;
            }
            currentImageHeight += DISTANCE_BETWEEN_ROW_GROUP-DISTANCE_BETWEEN_ROW;

        }

        this.setSize((int) imageWidth, (int) imageHeight * 11 / 10);


        return ret;
    }
}