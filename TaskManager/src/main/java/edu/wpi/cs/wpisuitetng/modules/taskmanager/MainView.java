package edu.wpi.cs.wpisuitetng.modules.taskmanager;

import javax.swing.JScrollPane;

import edu.wpi.cs.wpisuitetng.modules.taskmanager.view.WorkflowView;

/**
 * MainView is a scrollable window with a viewport that can
 * view only WorkflowViews. Eventually it will support viewing
 * of multiple types of JPanels.
 * 
 * @author Thefloorisjava
 */
public class MainView extends JScrollPane 
{
    private static final long serialVersionUID = -346061317795260862L;

    /**
     * Constructor for the scrollable main view.  
     */
    public MainView(WorkflowView view){
	setViewportView(view);
    }
}
