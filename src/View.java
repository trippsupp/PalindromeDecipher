
/**
 *
 * View.java - Sets up the GUI
 *
 * @author Vance Field
 * @date 8-Oct-2015
 */
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.*;
import java.io.IOException;
import javax.swing.*;
import javax.swing.border.Border;

public class View extends JPanel {

    Srv srvObj = new Srv();
    private JButton browse;
    private JButton decrypt;
    private JTextArea output;
    private JFileChooser fileChooser;
    public final int FRAMEHEIGHT = 250;
    public final int FRAMEWIDTH = 400;
    private String fileName = "";
    private int returnVal;

    /**
     * Constructs each object
     */
    public View() {
        setLayout(null);
        setSize(FRAMEWIDTH, FRAMEHEIGHT);
        setBackground(Color.lightGray);

        fileChooser = new JFileChooser("G:\\ITEC 220\\Project2\\");     // sets default browsing directory
        add(fileChooser);

        browse = new JButton("Browse");
        browse.setSize(90, 20);
        browse.setLocation(75, 15);
        browse.addActionListener(new ButtonClickHandler());
        add(browse);

        decrypt = new JButton("Decrypt");
        decrypt.setSize(90, 20);
        decrypt.setLocation(235, 15);
        decrypt.addActionListener(new ButtonClickHandler());
        add(decrypt);

        output = new JTextArea("Click BROWSE to select encrypted file.");
        output.setSize(350, 150);
        output.setLocation(25, 50);
        output.setEditable(false);
        output.setBackground(Color.BLACK);
        output.setForeground(Color.GRAY);
        output.setLineWrap(true);
        output.setWrapStyleWord(true);
        Border border = BorderFactory.createLineBorder(Color.BLACK);
        output.setBorder(BorderFactory.createCompoundBorder(border,
                BorderFactory.createEmptyBorder(10, 10, 10, 10)));
        add(output);
    }

    /**
     * Displays the GUI
     */
    public void display() {
        JFrame frame = new JFrame("Vance Decipher");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(false);
        frame.setContentPane(this);
        frame.setPreferredSize(new Dimension(FRAMEWIDTH, FRAMEHEIGHT));
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    /**
     * ButtonClickHandler provides the action listener for the buttons
     */
    private class ButtonClickHandler implements ActionListener {

        /**
         * This method handles the tasks of each button
         *
         * @param e the action event handled by this method
         */
        public void actionPerformed(ActionEvent e) {
            if (e.getSource() == browse) {                                    // If you click openButton **** DO THIS ****
                
                returnVal = fileChooser.showOpenDialog(null);                 // Open file chooser
                if (returnVal == JFileChooser.APPROVE_OPTION) {
                    try {
                        fileName = fileChooser.getSelectedFile().getName();   // Sets fileName to the name of the selected file
                        output.setText("File <" + fileChooser.getSelectedFile().toString()
                                + "> sucessfully loaded."
                                + "\n\nPress DECRYPT to decrypt the message.");
                    } catch (Exception y) {
                        System.out.println("File not found");
                    }
                }
            }

            if (e.getSource() == decrypt) {                                   // if you click decrypt
                try {
                    srvObj.parseLine(fileName);                               // starts the process 
                    output.setForeground(Color.YELLOW);
                    output.setText("Decrypted Message:\n\n" + srvObj.getDecipheredText());               // displays the output
                } catch (IOException ex) {
                    output.setText("Something very bad went wrong");
                }
            }
        }
    }
}
