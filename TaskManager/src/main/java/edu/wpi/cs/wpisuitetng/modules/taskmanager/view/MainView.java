package edu.wpi.cs.wpisuitetng.modules.taskmanager.view;


import java.beans.PropertyChangeEvent;

import javafx.scene.control.ProgressBar;

import javax.swing.BoxLayout;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTabbedPane;

import edu.wpi.cs.wpisuitetng.modules.taskmanager.presenter.WorkflowPresenter;

/**
 * MainView is a scrollable window with a viewport that can
 * view only WorkflowViews.
 */
public class MainView extends JPanel 
{
    private static final long serialVersionUID = -346061317795260862L;
    
    private WorkflowPresenter workflowPresenter;
    private JScrollPane scrollPane;
    
    /**
     * Constructor for the scrollable main view.  
     */
    public MainView(WorkflowPresenter workflowPresenter) {
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        
        scrollPane = new JScrollPane();
        add(scrollPane);
        
        setWorkflowPresenter(workflowPresenter);
        
        /* As soon as this is added to a container, load the workflow */
        addPropertyChangeListener("ancestor", (PropertyChangeEvent evt) -> {
           this.workflowPresenter.load();
        });
    }

    /**
     * @return the WorkflowPresenter being displayed
     */
    public WorkflowPresenter getWorkflowPresenter() {
        return this.workflowPresenter;
    }

    /**
     * @param workflowView The WorkflowView to be displayed
     */
    public void setWorkflowPresenter(WorkflowPresenter workflowPresenter) {
        this.workflowPresenter = workflowPresenter;
        this.scrollPane.setViewportView(workflowPresenter.getView());
    }
}