/*******************************************************************************
 * Copyright (c) 2014 -- WPI Suite
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 ******************************************************************************/

package edu.wpi.cs.wpisuitetng.modules.taskmanager.presenter;

import java.awt.Point;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.SwingUtilities;
import javax.swing.JComponent;
import javax.swing.TransferHandler;

import edu.wpi.cs.wpisuitetng.modules.taskmanager.model.BucketModel;
import edu.wpi.cs.wpisuitetng.modules.taskmanager.model.TaskModel;
import edu.wpi.cs.wpisuitetng.modules.taskmanager.view.BucketView;
import edu.wpi.cs.wpisuitetng.modules.taskmanager.view.Icons;
import edu.wpi.cs.wpisuitetng.modules.taskmanager.view.MainView;
import edu.wpi.cs.wpisuitetng.modules.taskmanager.view.MiniTaskView;
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
    private WorkflowPresenter workflow;
    private static Filter taskFilter = new Filter();
    private Map<Integer, TaskPresenter> taskMap = new HashMap<Integer, TaskPresenter>();

    /**
     * Constructor for a bucket presenter
     * 
     * @param bucketId
     *            Id of the bucket associated with this presenter
     * @param workflow
     *            Workflow that contains this bucket.
     */
    public BucketPresenter(int bucketId, WorkflowPresenter workflow) {
        this.workflow = workflow;
        model = new BucketModel();
        model.setId(bucketId);
        view = new BucketView(model);
        registerCallbacks();
    }

    /**
     * Requests the server for a new bucket or the bucket corresponding to the
     * current ID
     */
    public void load() {
        // Sends a request for the TaskViews associated with the BucketView
        final Request request = Network.getInstance().makeRequest(
                "taskmanager/bucket", HttpMethod.GET);
        request.addObserver(new BucketObserver(this, HttpMethod.GET));
        request.send();
    }

    /**
     * Sets the view of the model
     */
    public void writeModelToView() {

        view.setModel(model);
        for (int i : model.getTaskIds()) {
            if (!taskMap.containsKey(i)) {
                taskMap.put(i, new TaskPresenter(i, this, ViewMode.EDITING));
            }
            taskMap.get(i).updateFromDatabase();

            MiniTaskView miniTaskView = taskMap.get(i).getMiniView();
            miniTaskView.setModel(taskMap.get(i).getModel());

            taskMap.get(i).validateUpdateLabel();
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
        view.setTransferHandler(new TransferHandler() {
            /**
             * @return {@link TransferHandler#MOVE}.
             */
            @Override
            public int getSourceActions(JComponent c) {
                return MOVE;
            }

            /**
             * @return A transferable for the bucket presenter. Buckets can be
             *         converted into HTML tables, allowing them to be dropped
             *         into spreadsheets.
             */
            @Override
            protected Transferable createTransferable(JComponent c) {
                return new Transferable() {

                    /** {@inheritDoc} */
                    @Override
                    public DataFlavor[] getTransferDataFlavors() {
                        return new DataFlavor[] { DataFlavor.fragmentHtmlFlavor };
                    }

                    /** {@inheritDoc} */
                    @Override
                    public boolean isDataFlavorSupported(DataFlavor flavor) {
                        return flavor == DataFlavor.fragmentHtmlFlavor;
                    }

                    /** {@inheritDoc} */
                    @Override
                    public Object getTransferData(DataFlavor flavor)
                            throws UnsupportedFlavorException, IOException {
                        if (!isDataFlavorSupported(flavor)) {
                            throw new UnsupportedFlavorException(flavor);
                        } else {
                            return BucketPresenter.this.toHtml();
                        }
                    }
                };
            }

            /**
             * @return true if it's a task being transfered
             */
            @Override
            public boolean canImport(TransferHandler.TransferSupport support) {
                try {
                    TaskPresenter taskPresenter = (TaskPresenter) support
                            .getTransferable().getTransferData(
                                    TaskPresenter.TASK_DATA_FLAVOR);

                    /*
                     * The task can be imported into this bucket if it's not
                     * already in it.
                     */
                    return true;
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
                    TaskPresenter taskPresenter = (TaskPresenter) support
                            .getTransferable().getTransferData(
                                    TaskPresenter.TASK_DATA_FLAVOR);
                    boolean flag = taskPresenter.getBucket().getModel()
                            .getTitle().equals(model.getTitle());
                    Point point = MainView.getInstance().getGlassPane()
                            .getPoint();
                    point = SwingUtilities.convertPoint(MainView.getInstance()
                            .getGlassPane(), point, BucketPresenter.this
                            .getView());

                    BucketPresenter.this.insertTask(taskPresenter.getModel()
                            .getId(), taskPresenter, BucketPresenter.this
                            .getView().getInsertionIndex(point, flag));

                    return true;
                } catch (UnsupportedFlavorException | IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }

                return false;
            }
        });

        /*
         * Allows clicking of the bucket's title to open the change view. Which
         * will allow users to change the title of the bucket.
         */
        view.addChangeBucketNameListener(new MouseAdapter() {

            @Override
            public void mouseClicked(MouseEvent e) {
                view.setChangeTitlePanel();
                view.revalidate();
                view.repaint();
            }
        });

        /*
         * Allows the OK button to revert the Title Panel of the bucket. Also
         * saves the new label to the model for load.
         */
        view.addOkButtonListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                String title = view.getChangeTextField().getText();
                if (title.trim().equals("")) {
                    view.getBucketNameLabel().setText(
                            view.getChangeTextField().getPrompt());
                } else {
                    view.getBucketNameLabel().setText(title.trim());
                    model.setTitle(title.trim());
                }
                view.setStaticTitlePanel();
                workflow.updateManageWorkflowView();
                updateInDatabase();
                view.revalidate();
                view.repaint();
            }
        });

        /*
         * Allows the Cancel button to revert the Title Panel of the bucket.
         */
        view.addCancelButtonListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                view.setStaticTitlePanel();
                view.revalidate();
                view.repaint();
            }
        });
    }

    /**
     * @return A representation of the key information in this bucket as an HTML
     *         table
     */
    protected String toHtml() {
        String str = "<table>";

        str += "<tr><td><b>" + model.getTitle() + "</b></tr>";
        str += "<tr><td><b>Task<td><b>Due Date<td><b>Actual Effort<td><b>Estimated Effort<td><b>Category</tr>";

        for (Integer taskId : model.getTaskIds()) {
            TaskPresenter taskPresenter = taskMap.get(taskId);
            TaskModel taskModel = taskPresenter.getModel();

            str += "<tr>";
            str += "<td>" + taskModel.getTitle();
            str += "<td>" + taskModel.getDueDate();
            str += "<td>" + taskModel.getActualEffort();
            str += "<td>" + taskModel.getEstimatedEffort();
            if (taskModel.getLabelColor() == null) {
                str += "<td><i>None";
            } else {
                str += "<td bgcolor=\"#"
                        + Integer.toHexString(
                                taskModel.getLabelColor().getRGB())
                                .substring(2) + "\">&nbsp";
            }
            str += "</tr>";
        }

        return str;
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
        MainView.getInstance().addTab(taskModel.getShortTitle(),
                Icons.TASKEDIT, taskView);
        int tabCount = MainView.getInstance().getTabCount();
        taskView.setIndex(tabCount - 1);
        MainView.getInstance().setSelectedIndex(tabCount - 1);
    }

    /**
     * Remove a task ID from the list of taskIDs in the model, update the view
     * to not have that task, and update the database to remove the task from
     * this bucket
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
        taskPresenter.getBucket().removeTask(id);
        taskPresenter.setBucket(this);
        model.addTaskID(id);
        if (!taskMap.containsKey(id)) {
            taskMap.put(id, taskPresenter);
        }

        taskPresenter.getModel().setStatus(this.getModel().getId());
        taskPresenter.updateView();

        /* Immediately add the view for instant feedback to the user */
        if (taskPresenter.getMiniView() != null) {
            view.addTaskToView(taskPresenter.getMiniView());
        }

        view.setModel(model);
        view.revalidate();
        view.repaint();

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
     * @param index
     *            the index to add the given task in the list
     */
    public void insertTask(int id, TaskPresenter taskPresenter, int index) {
        int before = taskPresenter.getModel().getStatus();
        int after = this.getModel().getId();
        taskPresenter.getBucket().removeTask(id);
        taskPresenter.setBucket(this);
        model.addTaskID(index, id);
        if (!taskMap.containsKey(id)) {
            taskMap.put(id, taskPresenter);
        }

        taskPresenter.getModel().setStatus(this.getModel().getId());
        taskPresenter.updateView();

        /* Immediately add the view for instant feedback to the user */
        if (taskPresenter.getMiniView() != null) {
            view.addTaskToView(taskPresenter.getMiniView());
        }

        view.setModel(model);
        view.revalidate();
        view.repaint();

        updateInDatabase();
        taskPresenter.dragDropHistory(before, after);
        taskPresenter.getView().getCommentView().revalidateHistoryPanel();

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
    public void responseGet(BucketModel models) {
        model = models;
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
        this.model.setId(model.getId());
        this.model.setTaskIds(model.getTaskIds());
        this.model.setTitle(model.getTitle());
        this.model = model;
        view.setModel(model);
        this.addMiniTaskstoView();
        view.revalidate();
        view.repaint();
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
     * @return The workflow that contains this bucket
     */
    public WorkflowPresenter getWorkflow() {
        return workflow;
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
     * removes all tasks from view and only adds back based on filter options
     */
    public void addMiniTaskstoView() {
        List<Integer> taskIds = model.getTaskIds();
        view.resetTaskList();

        for (int i : taskIds) {
            MiniTaskView miniTaskView = taskMap.get(i).getMiniView();
            if (taskFilter.matches(taskMap.get(i))) {

                view.addTaskToView(miniTaskView);

            }
        }
        view.revalidate();
        view.repaint();
    }

    /**
     * removes task from bucketView, presenter, and model
     * 
     * @param task
     *            presenter of task to be moved
     */

    public void removeTaskView(TaskPresenter taskPresenter) {
        taskMap.remove(taskPresenter.getModel().getId());
        model.removeTaskId(taskPresenter.getModel().getId());
        view.removeTaskView(taskPresenter.getMiniView());
        Request request = Network.getInstance().makeRequest(
                "taskmanager/task/" + model.getId(), HttpMethod.DELETE);
        request.addObserver(new TaskObserver(taskPresenter));
        request.send();
        updateInDatabase();
    }

    /*
     * The ideas for the following functions comes from reading code and using
     * implementations found at
     * https://github.com/dcpounds/wpi-suite/tree/dev-gradle
     * /TaskManager/src/main
     * /java/edu/wpi/cs/wpisuitetng/modules/taskmanager/controller/stage Team 4
     * of year B2014's implementation
     */

    /**
     * Saves the 4 preset stages. Should only happen if db is empty.
     */
    public static final void saveBaseBuckets() {
        BucketModel newBucket = new BucketModel(1, "New");
        BucketModel scheduledBucket = new BucketModel(2, "Scheduled");
        BucketModel inProgressBucket = new BucketModel(3, "In Progress");
        BucketModel completedBucket = new BucketModel(4, "Completed");

        try {
            sendBaseBucketRequest(new BucketPresenter(1, MainView.getInstance()
                    .getWorkflowPresenter()), newBucket);
            Thread.sleep(1000);
            sendBaseBucketRequest(new BucketPresenter(2, MainView.getInstance()
                    .getWorkflowPresenter()), scheduledBucket);
            Thread.sleep(1000);
            sendBaseBucketRequest(new BucketPresenter(3, MainView.getInstance()
                    .getWorkflowPresenter()), inProgressBucket);
            Thread.sleep(1000);
            sendBaseBucketRequest(new BucketPresenter(4, MainView.getInstance()
                    .getWorkflowPresenter()), completedBucket);
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            System.err.println("Sleep Exception: "
                    + e.getStackTrace().toString());
        }

    }

    /**
     * this function sets the filter to be used by the bucket presenter.
     * 
     * @param taskFilter
     *            the new Filter to be used by the bucket presenter
     */
    public static void setTaskFilter(Filter taskFilterIn) {
        taskFilter = taskFilterIn;
    }

    /**
     * This function gets the current filter being used by the bucket presenter.
     * 
     * @return the current taskFileter being used by the bucket presenter.
     */
    public static Filter getTaskFilter() {
        return taskFilter;
    }

    /**
     * Adds a bucket model to the DB.
     * 
     * @param bucket
     *            Bucket to add to the DB
     */
    private static final void sendBaseBucketRequest(BucketPresenter presenter,
            BucketModel bucket) {
        final Request request = Network.getInstance().makeRequest(
                "taskmanager/bucket", HttpMethod.PUT);
        request.setBody(bucket.toJson());
        request.addObserver(new BucketObserver(presenter, HttpMethod.PUT));
        request.send();
    }

    /**
     * Gets the csv entries for all tasks in this bucket.
     *
     * @return CSV entries, ends in newline.
     */
    public String getCsv() {
        String t = new String();
        for (TaskPresenter i : taskMap.values()) {
            if (taskFilter == null) {
                t = t + i.getModel().getCsv();
            } else if (taskFilter.matches(i)) {
                t = t + i.getModel().getCsv();
            }
        }
        return t;
    }

    /**
     * Create a new bucket in the database. Initializes an async network request
     * with an observer.
     */
    public void createInDatabase() {
        Request request = Network.getInstance().makeRequest(
                "taskmanager/bucket", HttpMethod.PUT);
        request.setBody(model.toJson());
        request.addObserver(new BucketObserver(this, HttpMethod.PUT));
        request.send();
    }
}
