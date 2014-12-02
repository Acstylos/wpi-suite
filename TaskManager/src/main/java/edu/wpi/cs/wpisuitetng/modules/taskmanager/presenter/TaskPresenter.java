/*******************************************************************************
 * Copyright (c) 2014 -- WPI Suite
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 ******************************************************************************/

package edu.wpi.cs.wpisuitetng.modules.taskmanager.presenter;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import edu.wpi.cs.wpisuitetng.modules.core.models.User;
import edu.wpi.cs.wpisuitetng.modules.taskmanager.model.TaskModel;
import edu.wpi.cs.wpisuitetng.modules.taskmanager.view.MainView;
import edu.wpi.cs.wpisuitetng.modules.taskmanager.view.MiniTaskView;
import edu.wpi.cs.wpisuitetng.modules.taskmanager.view.TaskView;
import edu.wpi.cs.wpisuitetng.modules.taskmanager.view.ViewMode;
import edu.wpi.cs.wpisuitetng.network.Network;
import edu.wpi.cs.wpisuitetng.network.Request;
import edu.wpi.cs.wpisuitetng.network.models.HttpMethod;

/**
 * This class creates a TaskView and updates the task with new information from
 * the database. This lets you modify the task, and will let the user modify it
 * as well.
 */
public class TaskPresenter {

    /** View for the task. */
    private TaskView view;
    private MiniTaskView miniView;
    /** Model for the task. */
    private TaskModel model;
    private ViewMode viewMode;
    private User[] allUserArray = {};
    private List<User> assignedUserList = new ArrayList<>();

    private BucketPresenter bucket;

    /**
     * Constructs a TaskPresenter for the given model. Constructs the view
     * offscreen, available if you call getView().
     * 
     * @param id
     *            ID of the bucket to create
     */
    public TaskPresenter(int id, BucketPresenter bucket, ViewMode viewMode) {
        this.bucket = bucket;
        this.viewMode = viewMode;
        this.model = new TaskModel();
        this.model.setId(id);
        this.model.setTitle("New Task");
        assignedUserList = new ArrayList<User>(model.getAssignedTo());
        this.view = new TaskView(model.getTitle(), model.getEstimatedEffort(),
                model.getDescription(), model.getDueDate(), viewMode, this);
        this.miniView = new MiniTaskView(model.getTitle(), model.getDueDate());
        final Request request = Network.getInstance().makeRequest("core/user",
                HttpMethod.GET);
        request.addObserver(new UsersObserver(this));
        request.send();
        registerCallbacks();

    }

    /**
     * Register callbacks with the local view.
     */
    private void registerCallbacks() {
        // onclick listener to open new tabs when minitaskview is clicked
        miniView.addOnClickOpenTabView(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                MainView.getInstance().addTab(model.getTitle(), view);
                view.setViewMode(ViewMode.EDITING);
                int tabCount = MainView.getInstance().getTabCount();
                view.setIndex(tabCount - 1);
                MainView.getInstance().setSelectedIndex(tabCount - 1);
            }
        });

        view.addOkOnClickListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                saveView();
                updateView(); // might be redundant
                MainView.getInstance().setTitleAt(view.getIndex(),
                        model.getTitle());
                if (viewMode == ViewMode.CREATING) {
                    createInDatabase();
                    bucket.addMiniTaskView(miniView);
                    view.setViewMode(ViewMode.EDITING);
                    int index = MainView.getInstance().indexOfTab(
                            model.getTitle());
                    MainView.getInstance().remove(index);
                    MainView.getInstance().setSelectedIndex(0);
                }
                view.revalidate();
                view.repaint();
                miniView.revalidate();
                miniView.repaint();
            }
        });

        view.addCancelOnClickListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int index = MainView.getInstance().indexOfTab(model.getTitle());
                MainView.getInstance().remove(index);
                updateView();
                view.revalidate();
                view.repaint();
                miniView.revalidate();
                miniView.repaint();
                MainView.getInstance().setSelectedIndex(0);
            }
        });

        view.addClearOnClickListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                updateView();
                view.revalidate();
                view.repaint();
                miniView.revalidate();
                miniView.repaint();
                MainView.getInstance().setTitleAt(view.getIndex(),
                        model.getTitle());
            }
        });

        view.addDeleteOnClickListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int index = MainView.getInstance().indexOfTab(model.getTitle());
                MainView.getInstance().remove(index);
                MainView.getInstance().getWorkflowPresenter()
                        .archiveTask(model.getId(), bucket.getModel().getId());
                MainView.getInstance().getArchive().getArchiveBucket()
                        .addTaskToView(miniView);
                bucket.getView().getComponentAt(view.getLocation())
                        .setVisible(false);

            }
        });

        view.addDocumentListenerOnTaskName(new DocumentListener() {
            @Override
            public void removeUpdate(DocumentEvent e) {
                view.validateTaskNameField();
            }

            @Override
            public void insertUpdate(DocumentEvent e) {
                view.validateTaskNameField();
            }

            @Override
            public void changedUpdate(DocumentEvent arg0) {
                view.validateTaskNameField();
            }
        });
    }

    /**
     * Create a new task in the database. Initializes an async network request
     * with an observer.
     */
    public void createInDatabase() {
        Request request = Network.getInstance().makeRequest("taskmanager/task",
                HttpMethod.PUT);
        request.setBody(this.model.toJson());
        request.addObserver(new TaskObserver(this));
        request.send();
    }

    /**
     * Save the fields entered in the view.
     */
    private void saveView() {
        updateModel();

        Request request = Network.getInstance().makeRequest("taskmanager/task",
                HttpMethod.POST);
        request.setBody(this.model.toJson());
        request.addObserver(new TaskObserver(this));
        request.send();
    }

    /**
     * @param users User array of all users in the database
     */
    public void addUsersToAllUserList(User[] users) {
        this.allUserArray = users;
    }
    
    /**
     * Takes the allUsers array, and checks users with assigned users list
     * all assigned users get added to the assigned view, and all others
     * get added to unassigned view
     */
    public void addUsersToView() {
        this.view.getUserListPanel().removeAllUsers();
        for(User user: allUserArray) {
            if(assignedUserList.contains(user)) {
                this.view.getUserListPanel().addUserToList(user, true);
            } else {
                this.view.getUserListPanel().addUserToList(user, false);
            }
        }
    }

    /**
     * Have the presenter reload the view from the model.
     */
    public void updateFromDatabase() {
        Request request = Network.getInstance().makeRequest(
                "taskmanager/task/" + this.model.getId(), HttpMethod.GET);
        request.addObserver(new TaskObserver(this));
        request.send();

        System.out.println("Sending GET request: " + request);
    }

    /**
     * Update the model with data from the view
     */
    public void updateModel() {
        model.setTitle(view.getTaskNameField());
        model.setEstimatedEffort(view.getEstimatedEffort());
        model.setActualEffort(view.getActualEffort());
        model.setDescription(view.getDescriptionText());
        model.setDueDate(view.getDueDate());
        model.setAssignedTo(assignedUserList);
    }

    /**
     * Update the view with data from the model
     */
    public void updateView() {
        view.setTaskNameField(model.getTitle());
        view.setEstimatedEffort(model.getEstimatedEffort());
        view.setActualEffort(model.getActualEffort());
        view.setDescriptionText(model.getDescription());
        view.setDueDate(model.getDueDate());
        miniView.setTaskName(model.getTitle());
        miniView.setDueDate(model.getDueDate());
        assignedUserList = new ArrayList<User>(model.getAssignedTo());
        addUsersToView();
    }

    public void setTheViewViewMode(ViewMode viewMode) {
        view.setViewMode(viewMode);
    }

    /**
     * Get the view for this Task.
     */
    public TaskView getView() {
        return view;
    }

    /**
     * Get the miniView for this Task.
     */
    public MiniTaskView getMiniView() {
        return miniView;
    }

    /**
     * Get the model for this class.
     * 
     * @return This provider's model.
     */
    public TaskModel getModel() {
        return model;
    }

    /**
     * Set the model for this class.
     * 
     * @param model
     *            This provider's model.
     */
    public void setModel(TaskModel model) {
        this.model = model;
    }

    /**
     * @return the bucket presenter that holds this task
     */
    public BucketPresenter getBucket() {
        return bucket;
    }

    /**
     * Removes a user from the assignedTo list
     * @param user User to remove from assignedTo
     */
    public void removeAssignedTo(User user) {
        this.assignedUserList.remove(user);
    }

    /**
     * Add a user from the assignedTo list
     * @param user User to add to assignedTo 
     */
    public void addUserToAssignedTo(User user) {
        this.assignedUserList.add(user);
    }
}
