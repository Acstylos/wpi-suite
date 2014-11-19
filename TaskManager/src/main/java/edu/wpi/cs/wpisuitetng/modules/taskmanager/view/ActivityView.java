package edu.wpi.cs.wpisuitetng.modules.taskmanager.view;
import edu.wpi.cs.wpisuitetng.modules.core.models.User;
import edu.wpi.cs.wpisuitetng.modules.taskmanager.view.TaskView;

import javax.swing.JPanel;

import net.miginfocom.swing.MigLayout;

import javax.swing.JTextField;
import javax.swing.JFormattedTextField;
import javax.swing.JLabel;
import javax.swing.JTextPane;
import javax.swing.JButton;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.util.Date;
 
public class ActivityView extends JPanel{
     
    private JButton submitButton;
    private JButton replyButton;
    private JLabel displayBox;
    private JTextPane activityBox;
     
    /**
     * @wbp.parser.entryPoint
     */
    public ActivityView() {
        this.setLayout(new MigLayout("", "[grow]", "[grow]"));
         
        JPanel panel = new JPanel();
        this.add(panel, "cell 0 0,grow");
        panel.setLayout(new MigLayout("", "[][][][][][][grow]", "[][][88.00][][][][][][30.00][9.00,grow]"));
         
        this.displayBox = new JLabel("test");
        panel.add(displayBox, "cell 0 0 7 4");
         
        JTextPane activityBox = new JTextPane();
        panel.add(activityBox, "cell 0 4 7 5,grow");
         
        JButton submitButton = new JButton("Submit");
        submitButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                System.out.println("nfdkashda");
                displayBox.setText(displayBox.getText() +"/n"+ activityBox.getText());
            }
        });
        panel.add(submitButton, "cell 4 9");
         
        JButton replyButton = new JButton("Reply");
        replyButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent arg0) {
            }
        });
        panel.add(replyButton, "cell 6 9");
    // TODO Auto-generated method stub
     
    }
     
    public void revalidate() {
     
    }
 
    public void repaint() {
    // TODO Auto-generated method stub
     
    }
 
    public void setActivity(String activity) {
    // TODO Auto-generated method stub
     
    }
 
    public void addOnSaveListener(ActionListener listener){
        this.replyButton.addActionListener(listener);
    }

    public User getUser() {
	// TODO Auto-generated method stub
	return null;
    }

    public Date getDate() {
	// TODO Auto-generated method stub
	return null;
    }

    public String getActivity() {
	// TODO Auto-generated method stub
	return null;
    }

    public void setUser(User user) {
	// TODO Auto-generated method stub
	
    }

    public void setDate(Date date) {
	// TODO Auto-generated method stub
	
    }
 
     
}