package edu.wpi.cs.wpisuitetng.modules.taskmanager.view;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JPanel;

import edu.wpi.cs.wpisuitetng.modules.taskmanager.presenter.BucketPresenter;
import edu.wpi.cs.wpisuitetng.modules.taskmanager.presenter.TaskPresenter;
import net.miginfocom.swing.MigLayout;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.io.IOException;
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
        setLayout(new MigLayout("", "[fill]", "[grow]"));
        
        JButton createNewTaskButton = new JButton("<html>Create<br/>Task</html>");
        
        try {
            createNewTaskButton.setIcon(new ImageIcon(ImageIO.read(TaskView.class.getResourceAsStream("create-task-large.png"))));
        } catch (IOException e1) {
            e1.printStackTrace();
        }
        
        createNewTaskButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // get instance of the New-Bucket Presenter to add new tasks into
                BucketPresenter newBucketPresenter = MainView.getInstance().getWorkflowPresenter().getBucketPresenterById(1);
                newBucketPresenter.addNewTaskToView();
            }
        });
        add(createNewTaskButton, "cell 0 0");
    }
    
}
