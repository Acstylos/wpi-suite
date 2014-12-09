/*******************************************************************************
 * Copyright (c) 2014 -- WPI Suite
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 ******************************************************************************/

package edu.wpi.cs.wpisuitetng.modules.taskmanager.presenter;

import java.awt.Container;
import java.awt.Dialog;
import java.awt.Dimension;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import javax.swing.JComponent;
import javax.swing.SwingUtilities;
import javax.swing.TransferHandler;
import javax.swing.TransferHandler.TransferSupport;

import edu.wpi.cs.wpisuitetng.janeway.config.ConfigManager;
import edu.wpi.cs.wpisuitetng.modules.core.models.User;
import edu.wpi.cs.wpisuitetng.modules.taskmanager.model.TaskModel;
import edu.wpi.cs.wpisuitetng.modules.taskmanager.view.GhostGlassPane;
import edu.wpi.cs.wpisuitetng.modules.taskmanager.view.Icons;
import edu.wpi.cs.wpisuitetng.modules.taskmanager.view.MainView;
import edu.wpi.cs.wpisuitetng.modules.taskmanager.view.MiniTaskView;
import edu.wpi.cs.wpisuitetng.modules.taskmanager.view.TaskView;
import edu.wpi.cs.wpisuitetng.modules.taskmanager.view.VerifyActionDialog;
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
    private TaskModel beforeModel = new TaskModel();
    private ViewMode viewMode;
    private User[] allUserArray = {};
    private List<Integer> assignedUserList;
    /** Dialog variables for use */
    private VerifyActionDialog cancelDialog = new VerifyActionDialog();
    private VerifyActionDialog undoDialog = new VerifyActionDialog();
    private VerifyActionDialog deleteDialog = new VerifyActionDialog();
    private boolean cancelDialogConfirmed = false;
    private boolean undoDialogConfirmed = false;
    private boolean deleteDialogConfirmed = false;
    private boolean allowCancelDialog = false;

    private BucketPresenter bucket;
    private List<ActivityPresenter> activityPresenters;
    
    public final static DataFlavor TASK_DATA_FLAVOR = new DataFlavor(TaskPresenter.class, "Task");

    /**
     * Constructs a TaskPresenter for the given model. Constructs the view
     * offscreen, available if you call getView().
     * 
     * @param id
     *            ID of the bucket to create
     * @param bucket
     *            The BucketPresenter this TaskPresenter is a part of.
     * @param viewMode
     *            The ViewMode of the task, that will be displayed.
     */
    public TaskPresenter(int id, BucketPresenter bucket, ViewMode viewMode) {
        this.bucket = bucket;
        this.viewMode = viewMode;
        this.model = new TaskModel();
        this.model.setId(id);
        this.model.setTitle("New Task");
        assignedUserList = new ArrayList<Integer>(model.getAssignedTo());
        this.view = new TaskView(model, viewMode, this);
        this.miniView = new MiniTaskView(model);
        final Request request = Network.getInstance().makeRequest("core/user",
                HttpMethod.GET);
        request.addObserver(new UsersObserver(this));
        request.send();
        Dimension maxView = new Dimension(bucket.getView().getWidth()-32, bucket.getView().getHeight());
        this.miniView.setMaximumSize(maxView);//prevent horizontal scroll
        this.miniView.getTaskNameLabel().setMaximumSize(maxView);
        this.activityPresenters = new ArrayList<ActivityPresenter>();
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
                MainView.getInstance().addTab(model.getShortTitle(),
                        Icons.TASK, view);// this line chooses tab title
                view.setViewMode(ViewMode.EDITING);
                viewMode = view.getViewMode();
                int tabCount = MainView.getInstance().getTabCount();
                view.setIndex(tabCount - 1);
                MainView.getInstance().setSelectedIndex(tabCount - 1);
                MainView.getInstance().setToolTipTextAt(tabCount - 1,
                        model.getTitle());

            }
        });
        
        /* Set a handler to move the task when it's dragged and dropped */ 
        miniView.setTransferHandler(new TransferHandler() {
            /**
             * @return {@link TransferHandler#MOVE}. At least for now, tasks
             * are moved, never copied or linked.
             */
            @Override
            public int getSourceActions(JComponent c) {
                return MOVE;
            }
            
            /**
             * @return false always, since things can't be dragged onto tasks
             */
            @Override
            public boolean canImport(TransferSupport support) {
                return false;
            }
            
            /**
             * @return A transferable for the task presenter
             */
            @Override
            protected Transferable createTransferable(JComponent c) {
                return new Transferable() {
                    @Override
                    public DataFlavor[] getTransferDataFlavors() {
                        return new DataFlavor[] { TASK_DATA_FLAVOR };
                    }

                    @Override
                    public boolean isDataFlavorSupported(DataFlavor flavor) {
                        return flavor == TASK_DATA_FLAVOR;
                    }

                    @Override
                    public Object getTransferData(DataFlavor flavor)
                            throws UnsupportedFlavorException, IOException {
                        return TaskPresenter.this;
                    }
                };
            }
            
            /**
             * Hide the ghosted image after the drag and drop is done
             */
            protected void exportDone(JComponent source, Transferable data, int action) {
                GhostGlassPane glassPane = MainView.getInstance().getGlassPane();
                glassPane.setVisible(false);
            }
        });

        /**
         * Open the task tab when a task is clicked
         * 
         * @param ActionListener
         */
        view.addOkOnClickListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int index = MainView.getInstance().indexOfComponent(view);
                if (viewMode == ViewMode.CREATING) {
                    // CREATING MODE
                    updateModel();
                    createInDatabase(); // is calling "PUT" in task observer
                    view.setViewMode(ViewMode.EDITING);
                    MainView.getInstance().remove(index);
                    MainView.getInstance().setSelectedIndex(0);
                }

                else {
                    updateBeforeModel();
                    if (view.getStatus() != bucket.getModel().getId()) { // if
                                                                         // we
                                                                         // are
                                                                         // switching
                                                                         // buckets
                        MainView.getInstance()
                                .getWorkflowPresenter()
                                .moveTask(model.getId(), view.getStatus(),
                                        bucket.getModel().getId());
                        bucket.writeModelToView();
                        saveView();
                        updateView();
                        MainView.getInstance().setTitleAt(index,
                                model.getShortTitle());
                        MainView.getInstance().setToolTipTextAt(index, model.getTitle());
                        addHistory(beforeModel, model);
                        refreshCommentView();
                    } else { // not switching buckets
                        saveView();
                        updateView();
                        MainView.getInstance().setTitleAt(index,
                                model.getShortTitle());
                        MainView.getInstance().setToolTipTextAt(index, model.getTitle());
                        addHistory(beforeModel, model);
                    }
                }

            }
        });

        view.addCancelOnClickListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cancelDialog.setModalityType(Dialog.ModalityType.APPLICATION_MODAL);
                cancelDialog.setCommentLabelText("Are you sure you want to close the tab?");
                cancelDialog.addConfirmButtonListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        cancelDialogConfirmed = true;
                        cancelDialog.setVisible(false);
                    }
                });
                cancelDialog.addCancelButtonListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        cancelDialogConfirmed = false;
                        cancelDialog.setVisible(false);
                    }
                });
                if (allowCancelDialog) {
                    cancelDialog.setVisible(true);
                }
                if(cancelDialogConfirmed) {
                    int index = MainView.getInstance().indexOfComponent(view);
                    MainView.getInstance().remove(index);
                    updateView();
                    view.revalidate();
                    view.repaint();
                    miniView.revalidate();
                    miniView.repaint();
                    MainView.getInstance().setSelectedIndex(0);
                }
            }
        });

        view.addClearOnClickListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                undoDialog.setModalityType(Dialog.ModalityType.APPLICATION_MODAL);
                undoDialog.setCommentLabelText("Are you sure you want to undo your changes?");
                undoDialog.addConfirmButtonListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        undoDialogConfirmed = true;
                        undoDialog.setVisible(false);
                    }
                });
                undoDialog.addCancelButtonListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        undoDialogConfirmed = false;
                        undoDialog.setVisible(false);
                    }
                });
                undoDialog.setVisible(true);
                if(undoDialogConfirmed) {
                    updateView();
                    view.revalidate();
                    view.repaint();
                    miniView.revalidate();
                    miniView.repaint();
                    MainView.getInstance().setTitleAt(view.getIndex(),
                            model.getShortTitle());
                }
            }
        });

        view.addDeleteOnClickListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                deleteDialog.setModalityType(Dialog.ModalityType.APPLICATION_MODAL);
                deleteDialog.setCommentLabelText("Are you sure you want to delete this task?");
                deleteDialog.addConfirmButtonListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        deleteDialogConfirmed = true;
                        deleteDialog.setVisible(false);
                    }
                });
                deleteDialog.addCancelButtonListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        deleteDialogConfirmed = false;
                        deleteDialog.setVisible(false);
                    }
                });
                deleteDialog.setVisible(true);
                if(deleteDialogConfirmed) {
                    int index = MainView.getInstance().indexOfComponent(view);
                    MainView.getInstance().remove(index);
                    MainView.getInstance().getWorkflowPresenter().archiveTask(model.getId(), bucket.getModel().getId());
                }
            }
        });

        view.getCommentView().addOnPostListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent arg0) {
                addActivity();
            }

        });
    }

    /**
     * Updates another model with the fields before it gets updated
     */
    public void updateBeforeModel() {
        beforeModel.setTitle(model.getTitle());
        beforeModel.setDescription(model.getDescription());
        beforeModel.setEstimatedEffort(model.getEstimatedEffort());
        beforeModel.setActualEffort(model.getActualEffort());
        beforeModel.setDueDate(model.getDueDate());
        beforeModel.setStatus(model.getStatus());
    }

    /**
     * Creates a new activity in the Database by using the text provided in the
     * comment box
     */
    public void addActivity() {
        DateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy hh:mm a");
        Calendar cal = Calendar.getInstance();
        String userInformation = ConfigManager.getConfig().getUserName() + " ["
                + dateFormat.format(cal.getTime()) + "]: ";
        ActivityPresenter activityPresenter = new ActivityPresenter(this,
                userInformation
                        + view.getCommentView().getCommentText().getText(),
                false);

        view.getCommentView().postActivity(activityPresenter.getView());
        activityPresenter.createInDatabase();
        activityPresenters.add(activityPresenter);
    }

    /**
     * Automatically generates a comment based on what the user has changed in
     * the task. The "Update" button being clicked.
     * 
     * @param before
     *            The task model fields before it was updated
     * @param after
     *            The task model fields after it is updated
     */
    public void addHistory(TaskModel before, TaskModel after) {
        DateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy hh:mm:ss a");
        Calendar cal = Calendar.getInstance();
        String user = ConfigManager.getConfig().getUserName();
        String activity = user + " has updated tasks on "
                + dateFormat.format(cal.getTime()) + ":\n";

        activity += before.compareTo(after);
        ActivityPresenter activityPresenter = new ActivityPresenter(this,
                activity, true);
        view.getCommentView().postHistory(activityPresenter.getView());
        activityPresenter.createInDatabase();
        activityPresenters.add(activityPresenter);
    }

    /**
     * Automatically generates a comment based on what the user has done.
     * 
     * @param type
     *            the type of action that generated the activity
     */
    public void addHistory(String type) {
        String activity = "";
        DateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy hh:mm:ss a");
        Calendar cal = Calendar.getInstance();
        String user = ConfigManager.getConfig().getUserName();
        switch (type) {
        case "Create":
            activity = user + " has created a task on "
                    + dateFormat.format(cal.getTime());
            break;
        case "Move":
            activity = user + " has moved a task from x to y on "
                    + dateFormat.format(cal.getTime());
            break;
        case "Archive":
            activity = user + " has archived a task on "
                    + dateFormat.format(cal.getTime());
            break;
        default:
            break;
        }

        ActivityPresenter activityPresenter = new ActivityPresenter(this,
                activity, true);
        view.getCommentView().postHistory(activityPresenter.getView());
        activityPresenter.createInDatabase();
        activityPresenters.add(activityPresenter);
    }

    /**
     * saves an activity in the model with the given ID from the DataBase
     * 
     * @param id
     *            database given ID
     */
    public void saveActivityId(int id) {
        model.addActivityID(id);
        saveView();
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
            if(assignedUserList.contains(user.getIdNum())) {
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
        model.setStatus(view.getStatus());
        this.bucket = MainView.getInstance().getWorkflowPresenter()
                .getBucket(view.getStatus());
    }

    /**
     * Update the view with data from the model
     */
    public void updateView() {
        view.setStatus(model.getStatus());
        view.setModel(model);
        miniView.setModel(model);
        updateCommentView();
        assignedUserList = new ArrayList<Integer>(model.getAssignedTo());
        addUsersToView();
    }

    /**
     * takes the current comment view, clears the posts, and puts each comment,
     * one by one back on to the current view.
     */
    public void updateCommentView() {
        view.getCommentView().clearPosts();
        for (ActivityPresenter p : activityPresenters) {
            if (p.getModel().getIsAutogen())
                view.getCommentView().postHistory(p.getView());
            else
                view.getCommentView().postActivity(p.getView());
        }

    }

    /**
     * refresh comment view by using revalidate and repaint
     */
    public void refreshCommentView() {
        view.getCommentView().revalidate();
        view.getCommentView().repaint();
    }

    /**
     * Change the view
     * 
     * @param viewMode
     *            the viewMode to be switched to
     */
    public void setTheViewViewMode(ViewMode viewMode) {
        view.setViewMode(viewMode);
    }

    /**
     * Get the view for this Task.
     * 
     * @return the TaskView for the current TaskPresenter
     */
    public TaskView getView() {
        return view;
    }

    /**
     * Get the miniView for this Task.
     * 
     * @return miniView for Task
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
        activityPresenters = new ArrayList<ActivityPresenter>();
        for (int i : model.getActivityIds()) {
            ActivityPresenter p = new ActivityPresenter(i, this);
            p.load();
            activityPresenters.add(p);
        }
    }

    /**
     * 
     * @return the bucket the task is contained within
     */
    public BucketPresenter getBucket() {
        return bucket;
    }

    /**
     * @param bucket the bucket that this task is in
     */
    public void setBucket(BucketPresenter bucket) {
        this.bucket = bucket;
        this.model.setStatus(bucket.getModel().getId());
    }

    /**
     * Removes a user from the assignedTo list
     * @param user User to remove from assignedTo
     */
    public void removeUserFromAssignedTo(User user) {
        this.assignedUserList.remove((Object)user.getIdNum());
    }

    /**
     * Add a user from the assignedTo list
     * @param user User to add to assignedTo 
     */
    public void addUserToAssignedTo(User user) {
        this.assignedUserList.add(user.getIdNum());
        this.view.validateFields();
    }
    
    /**
     * @return A shallow copy of the temporary assigned users list, not the model's user list
     */
    public List<Integer> getAssignedUserList() {
        return this.assignedUserList;
    }
    
    /**
     * @param enable Whether or not to enable the cancel dialog
     */
    public void setAllowCancelDialogEnabled(boolean enable) {
        this.allowCancelDialog = enable;
        this.cancelDialogConfirmed = !enable; // if the dialog is enabled, the confirmation of the dialog box is opposite
    }
}
