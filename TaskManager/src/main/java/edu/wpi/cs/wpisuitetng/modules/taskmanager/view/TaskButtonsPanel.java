package edu.wpi.cs.wpisuitetng.modules.taskmanager.view;

import javax.swing.JPanel;

import net.miginfocom.swing.MigLayout;
import javax.swing.JButton;

public class TaskButtonsPanel extends JPanel {
    
    private static final long serialVersionUID = -3971494855765228847L;
    private final JButton okButton = new JButton();
    private final JButton cancelButton = new JButton();
    private final JButton clearButton = new JButton();
    private final JButton deleteButton = new JButton(); 

    /**
     * Create the panel.
     */
    public TaskButtonsPanel() {
        setLayout(new MigLayout("", "[][][]", "[]"));
        
        add(okButton, "cell 0 0");
        
        add(cancelButton, "cell 1 0");
        
        add(clearButton, "cell 2 0");
        
        add(deleteButton, "cell 3 0");
    }

}
