package edu.wpi.cs.wpisuitetng.modules.taskmanager.view;

import javax.swing.JPanel;

import net.miginfocom.swing.MigLayout;

import javax.swing.JTextArea;

import java.awt.Color;

import javax.swing.JButton;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;
/*constructs message view for comments and history of tasks
 * message: message within comment box
 * 
 */
public class ActivityView extends JPanel {

    /**
     * serialVersionUID
     */
    private static final long serialVersionUID = 7743312885538872898L;
    /**
     * Create the panel.
     */
    private String message = "";
    private JPanel pastMessagePanel;

    /**
     * Constructor sets up panel and colors
     */
    public ActivityView() {
        setLayout(new MigLayout("", "[grow]", "[]"));

        pastMessagePanel = new JPanel();
        pastMessagePanel.setForeground(Color.WHITE);
        pastMessagePanel.setBackground(Color.LIGHT_GRAY);
        add(pastMessagePanel, "cell 0 0,grow");
        pastMessagePanel.setLayout(new MigLayout("", "[grow][]", "[]"));
        this.pastMessagePanel = pastMessagePanel;


    }

    /**
     * sets the message within a MessageView and sets it up
     * @param message: message within MessageView
     */
    public void setMessage(String message){
        this.message = message;
        JTextArea pastActivityLabel = new JTextArea();

        pastActivityLabel.setEditable(false);
        message = this.stringConverter(message, 20);
        pastActivityLabel.replaceSelection(message);
        this.pastMessagePanel.add(pastActivityLabel, "cell 0 0");
        pastActivityLabel.setForeground(Color.WHITE);
        pastActivityLabel.setBackground(Color.LIGHT_GRAY);
    }


    /**
     * Takes a string and converts it into a certain number of
     * characters per line
     * @param startString: String to be converted
     * @param maxPerLine: max number of characters per line
     * @return
     */
    public String stringConverter(String startString, int maxPerLine){
        List<Character> startInChar = new ArrayList<Character>();
        String finalString="";
        int lastFoundSpace=0;
        int j=0;
        
        for(char c: startString.toCharArray()){
            startInChar.add(c);
        }
        for(int i=0; i<startInChar.size(); i++){
            if (startInChar.get(i)==' '){
                lastFoundSpace=i;
            }
            if (startInChar.get(i)=='\n'){
                j=0;
                lastFoundSpace=i;
            }
            if (j==maxPerLine){
                if (lastFoundSpace!=0){
                    startInChar.set(lastFoundSpace, '\n');
                    i=lastFoundSpace;
                }
                else{
                    startInChar.add(i+1, startInChar.get(i));
                    startInChar.set(i, '\n');
                }
                j=0;
                lastFoundSpace=0;

            }
            else{
                j++;
            }
        }
        for (int k=0; k<startInChar.size(); k++){
            finalString= finalString + startInChar.get(k);
        }
        return finalString;
    }

    /**
     * Makes X button appear when mouse is over message (not yet working)
     * @param e: MouseEvent
     */
    public void mouseEntered(MouseEvent e) {
        JButton deleteActivityButton = new JButton("X");
        pastMessagePanel.add(deleteActivityButton, "cell 1 0");
        deleteActivityButton.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {

            }
        });

    }
}