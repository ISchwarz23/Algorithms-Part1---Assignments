import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdOut;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

/**
 * Created by Ingo on 12.09.2015.
 */
public class PercolationPlayer {

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new FileReader(new File(args[0])));
        int N = Integer.parseInt(br.readLine());
        StdOut.println(N);

        StdDraw.show(0);
        Percolation perc = new Percolation(N);
        PercolationVisualizer.draw(perc, N);
        StdDraw.show(0);

        new ControllFrame(N, perc, br).setVisible(true);
    }

    private static class ControllFrame extends JFrame {

        public ControllFrame(final int N, final Percolation perc, final BufferedReader br) {
            super("Controlls");

            final JTextField textField = new JTextField("1");
            textField.setPreferredSize(new Dimension(50, 25));

            JButton playButton = new JButton("Play");
            playButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    int stepsToGo = Integer.parseInt(textField.getText());
                    String currentLine;
                    for(int step=0; step<stepsToGo; step++) {
                        try {
                            currentLine = br.readLine();
                            StdOut.println(currentLine);
                            String[] values = currentLine.trim().split("  ");
                            int i = Integer.parseInt(values[0].trim());
                            int j = Integer.parseInt(values[1].trim());
                            perc.open(i, j);

                            // draw N-by-N percolation system
                            StdDraw.show(0);
                            PercolationVisualizer.draw(perc, N);
                        } catch (IOException e1) {
                            e1.printStackTrace();
                        }

                    }
                }
            });

            setLayout(new FlowLayout());
            add(textField);
            add(playButton);
            setSize(300, 150);
        }
    }

}
