/*******************************************************************************
 * Copyright (c) 2014 -- WPI Suite
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 ******************************************************************************/

package edu.wpi.cs.wpisuitetng.modules.taskmanager.presenter;

import java.awt.datatransfer.UnsupportedFlavorException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.TransferHandler;
import javax.swing.TransferHandler.TransferSupport;

import edu.wpi.cs.wpisuitetng.modules.taskmanager.model.BucketModel;
import edu.wpi.cs.wpisuitetng.modules.taskmanager.model.TaskModel;
import edu.wpi.cs.wpisuitetng.modules.taskmanager.view.BucketView;
//import edu.wpi.cs.wpisuitetng.modules.taskmanager.view.Entity;
import edu.wpi.cs.wpisuitetng.modules.taskmanager.view.Icons;
import edu.wpi.cs.wpisuitetng.modules.taskmanager.view.MainView;
import edu.wpi.cs.wpisuitetng.modules.taskmanager.view.MiniTaskView;
import edu.wpi.cs.wpisuitetng.modules.taskmanager.presenter.TaskPresenter;
import edu.wpi.cs.wpisuitetng.modules.taskmanager.view.TaskView;
import edu.wpi.cs.wpisuitetng.modules.taskmanager.view.ViewMode;
import edu.wpi.cs.wpisuitetng.network.Network;
import edu.wpi.cs.wpisuitetng.network.Request;
import edu.wpi.cs.wpisuitetng.network.models.HttpMethod;

/**
 * 
 * 
 * @author TheFloorIsJava
 * 
 */

public class BucketPresenter {

    private BucketView view;
    private BucketModel model;
    private Map<Integer, TaskPresenter> taskMap;
    private WorkflowPresenter workflow;

    /**
     * Constructs a BucketPresenter for the given model.
     * 
     * @param model
     * @param workflow
     */
    public BucketPresenter(BucketModel model, WorkflowPresenter workflow) {
        this.model = model;
        this.workflow = workflow;
    }

    /**
     * Constructor for a bucket presenter
     * 
     * @param bucketId
     * @param workflow
     */
    public BucketPresenter(int bucketId, WorkflowPresenter workflow) {
        this.workflow = workflow;
        this.model = new BucketModel();
        this.taskMap = new HashMap<Integer, TaskPresenter>();
        this.model.setId(bucketId);
        this.view = new BucketView(this.model);
        registerCallbacks();
        load();
    }

    /**
     * Requests the server for a new bucket or the bucket corresponding to the
     * current ID
     */
    public void load() {
        HttpMethod method;
        String id = "/" + model.getId();
        if (model.getId() == 0) { // Put = create a new model
            method = HttpMethod.PUT;
            id = "";
        } else {// Retrieve a model
            method = HttpMethod.GET;
        }

        // Sends a request for the TaskViews associated with the BucketView
        final Request request = Network.getInstance().makeRequest(
                "taskmanager/bucket" + id, method);
        if (method == HttpMethod.PUT) {
            request.setBody(model.toJson());
        }
        request.addObserver(new BucketObserver(this, method)); // add an
        // observer to
        // the response
        request.send();
    }

    /**
     * Sets the view of the model
     */
    public void writeModelToView() {
        String name = "";
        switch (model.getId()) {
        case 1:
            name = "New";
            break;
        case 2:
            name = "Selected";
            break;
        case 3:
            name = "In Progress";
            break;
        case 4:
            name = "Completed";
            break;
        case 5:
            name = "Archive";
            break;
        }
        if (name.length() > 1) {
            model.setTitle(name);
        }

        this.view.setModel(this.model);
        List<Integer> taskIds = model.getTaskIds();
        for (int i : taskIds) {
            if (!taskMap.containsKey(i)) {
                taskMap.put(i, new TaskPresenter(i, this, ViewMode.EDITING));
            }
            taskMap.get(i).updateFromDatabase();

            MiniTaskView miniTaskView = taskMap.get(i).getMiniView();
            miniTaskView.setModel(taskMap.get(i).getModel());
            view.addTaskToView(miniTaskView);
        }
        addMiniTaskstoView();
        view.revalidate();
        view.repaint();

    }

    /**
     * Register callbacks with the local view.
     */
    private void registerCallbacks() {
        /* Add a handler to let the user drag tasks into this bucket */
        this.view.setTransferHandler(new TransferHandler() {
            /**
             * @return true if it's a task being transfered
             */
            @Override
            public boolean canImport(TransferHandler.TransferSupport support) {
                try {
                    TaskPresenter taskPresenter =
                            (TaskPresenter) support.getTransferable().getTransferData(TaskPresenter.TASK_DATA_FLAVOR);
                    
                    /* The task can be imported into this bucket if it's not
                     * already in it.
                     */
                    return taskPresenter.getBucket() != BucketPresenter.this;
                } catch (UnsupportedFlavorException | IOException e) {
                    return false;
                }                
            }
            
            /**
             * Add the task to this bucket
             */
            @Override
            public boolean importData(TransferSupport support) {
                try {
                    TaskPresenter taskPresenter =
                            (TaskPresenter) support.getTransferable().getTransferData(TaskPresenter.TASK_DATA_FLAVOR);
                    
                    BucketPresenter.this.addTask(taskPresenter.getModel().getId(), taskPresenter);
                    
                    return true;
                } catch (UnsupportedFlavorException | IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
                
                return false;
            }
        });
    }

    /**
     * Adds a new task to the bucket view, in the form of a miniTaskView
     */
    public void addNewMiniTaskToView() {

        TaskPresenter taskPresenter = new TaskPresenter(0, this,
                ViewMode.CREATING);
        // taskPresenter.createInDatabase();
        TaskModel taskModel = taskPresenter.getModel();
        TaskView taskView = taskPresenter.getView();
        MainView.getInstance().addTab(taskModel.getShortTitle(), Icons.TASK,
                taskView);
        int tabCount = MainView.getInstance().getTabCount();
        taskView.setIndex(tabCount - 1);
        MainView.getInstance().setSelectedIndex(tabCount - 1);
    }

    /**
     * Remove a task ID from the list of taskIDs in the model, update the
     * view to not have that task, and update the database to remove the task
     * from this bucket
     * 
     * @param rmid
     *            ID of the existing task to be removed
     */
    public void removeTask(int rmid) {
        model.removeTaskId(rmid);
        
        view.setModel(model);
        view.revalidate();
        view.repaint();
        
        taskMap.remove(rmid);
        updateInDatabase();
    }

    /**
     * Adds a task ID to the list of taskIDs in the bucket model. Sends an async
     * update to the database.
     * 
     * @param id
     *            ID of the existing task.
     * @param taskPresenter
     *            taskPresenter associated with the task
     */
    public void addTask(int id, TaskPresenter taskPresenter) {
        model.addTaskID(id);
        if (!taskMap.containsKey(id)) {
            taskMap.put(id, taskPresenter);
        }
        
        if (taskPresenter.getBucket() != this) {
            taskPresenter.getBucket().removeTask(id);
            taskPresenter.setBucket(this);
        }
        
        taskPresenter.getModel().setStatus(this.getModel().getId());
        taskPresenter.updateView();
        
        /* Immediately add the view for instant feedback to the user */
        if (taskPresenter.getMiniView() != null) {
            this.view.addTaskToView(taskPresenter.getMiniView());
        }
        
        view.setModel(model);
        view.revalidate();
        view.repaint();
        
        updateInDatabase();
    }

    /**
     * Write the model to the network/database. Must be created already.
     */
    private void updateInDatabase() {
        Request request = Network.getInstance().makeRequest(
                "taskmanager/bucket", HttpMethod.POST); // Update.
        request.setBody(model.toJson());
        request.addObserver(new BucketObserver(this, HttpMethod.POST));
        request.send();
    }

    /**
     * Handles the result of a GET request
     * 
     * @param models
     *            The models sent from the network
     */
    public void responseGet(BucketModel[] models) {
        if (models[0].getId() == 0)
            return;
        this.model = models[0];
        writeModelToView();
    }

    /**
     * Handles the result of a POST request
     * 
     * @param model
     *            The model sent from the network
     */
    public void responsePost(BucketModel model) {
        writeModelToView();
    }

    /**
     * Handles the result of a PUT request
     * 
     * @param model
     *            The model sent from the network
     */
    public void responsePut(BucketModel model) {
        this.model = model;
        writeModelToView();
    }

    /**
     * Handles the result of a DELETE request
     * 
     * @param model
     *            The model sent from the network
     */
    public void responseDelete(BucketModel model) {

    }

    /**
     * @return The view corresponding to the model
     */
    public BucketView getView() {
        return view;
    }

    /**
     * @return The model corresponding to the presenter
     */
    public BucketModel getModel() {
        return model;
    }

    /**
     * @param model
     *            THe model of the presenter to be set
     */
    public void setModel(BucketModel model) {
        this.model = model;
        writeModelToView();
    }

    /**
     * Add the miniTaskView to view
     * 
     * @param miniView
     *            The miniView associated with the task being added
     */
    public void addMiniTaskView(MiniTaskView miniView) {
        view.addTaskToView(miniView);
    }

    /**
     * 
     * @param id
     *            Id of the TaskPresenter mapped in his bucketPresenter's task
     *            HashMap
     * @return The TaskPresenter associated with the id given
     */
    public TaskPresenter getTask(int id) {
        return taskMap.get(id);
    }

    /**
     * removes all tasks from view and only adds back based on archive options
     */
    public void addMiniTaskstoView() {
        List<Integer> taskIds = model.getTaskIds();
        this.view.resetTaskList();
        for (int i : taskIds) {
            MiniTaskView miniTaskView = taskMap.get(i).getMiniView();
            if (MainView.getInstance().getShowArchived()) {
                view.addTaskToView(miniTaskView);
            } else {
                if (!taskMap.get(i).getModel().getIsArchived()) {
                    view.addTaskToView(miniTaskView);
                }
            }
        }
    }

    /*
     * removes task from bucketView, presenter, and model
     * 
     * @param task presenter of task to be moved
     */

    public void removeTaskView(TaskPresenter taskPresenter) {
        taskMap.remove(taskPresenter.getModel().getId());
        model.removeTaskId(taskPresenter.getModel().getId());
        view.removeTaskView(taskPresenter.getMiniView());
        Request request = Network.getInstance().makeRequest(
                "taskmanager/task/" + this.model.getId(), HttpMethod.DELETE);
        request.addObserver(new TaskObserver(taskPresenter));
        request.send();
        updateInDatabase();

    }
}
