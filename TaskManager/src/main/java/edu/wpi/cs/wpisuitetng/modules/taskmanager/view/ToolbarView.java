package edu.wpi.cs.wpisuitetng.modules.taskmanager.view;

import javax.swing.JButton;
import javax.swing.JPanel;

import net.miginfocom.swing.MigLayout;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.util.Date;

/**
 * Sets up upper toolbar of TaskManager tab
 */
public class ToolbarView extends JPanel
{
    private static final long serialVersionUID = 5489162021821230861L;

    /**
     * Creates and positions option buttons in upper toolbar
     * @param visible boolean
     */
    public ToolbarView() {
        setLayout(new MigLayout("fill"));
        
        JButton createNewTaskButton = new JButton("Create New Task");
        createNewTaskButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                System.out.println("Creating a new task!");
                MainView.getInstance().addTab("New Task", new TaskView("New Task", 700, null, null, ViewMode.CREATING));
                int tabCount = MainView.getInstance().getTabCount();
                MainView.getInstance().setSelectedIndex(tabCount-1);
            }
        });
        add(createNewTaskButton, "dock east");
    }
    
}
