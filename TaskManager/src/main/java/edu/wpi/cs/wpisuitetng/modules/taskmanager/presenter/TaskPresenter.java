/*******************************************************************************
 * Copyright (c) 2014 -- WPI Suite
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 ******************************************************************************/

package edu.wpi.cs.wpisuitetng.modules.taskmanager.presenter;

import java.awt.Color;
import java.awt.Dialog;
import java.awt.Point;
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
import java.util.Date;
import java.util.List;
import java.util.HashMap;
import java.util.Map;

import javax.swing.JTabbedPane;

import java.util.concurrent.TimeUnit;

import javax.swing.JComponent;
import javax.swing.SwingUtilities;
import javax.swing.Timer;
import javax.swing.TransferHandler;

import edu.wpi.cs.wpisuitetng.janeway.config.ConfigManager;
import edu.wpi.cs.wpisuitetng.modules.core.models.Notification;
import edu.wpi.cs.wpisuitetng.modules.core.models.User;
import edu.wpi.cs.wpisuitetng.modules.requirementmanager.models.Requirement;
import edu.wpi.cs.wpisuitetng.modules.requirementmanager.models.RequirementModel;
import edu.wpi.cs.wpisuitetng.modules.requirementmanager.view.ViewEventController;
import edu.wpi.cs.wpisuitetng.modules.taskmanager.model.TaskModel;
import edu.wpi.cs.wpisuitetng.modules.taskmanager.view.ColorRenderer;
import edu.wpi.cs.wpisuitetng.modules.taskmanager.view.GhostGlassPane;
import edu.wpi.cs.wpisuitetng.modules.taskmanager.view.Icons;
import edu.wpi.cs.wpisuitetng.modules.taskmanager.view.MainView;
import edu.wpi.cs.wpisuitetng.modules.taskmanager.view.MiniTaskView;
import edu.wpi.cs.wpisuitetng.modules.taskmanager.view.ReturnToOrigin;
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
    private Map<Integer, Requirement> reqMap = new HashMap<Integer, Requirement>();
    /** Dialog variables for use */
    private VerifyActionDialog cancelDialog = new VerifyActionDialog();
    private VerifyActionDialog undoDialog = new VerifyActionDialog();
    private VerifyActionDialog deleteDialog = new VerifyActionDialog();
    private boolean cancelDialogConfirmed = false;
    private boolean undoDialogConfirmed = false;
    private boolean deleteDialogConfirmed = true;
    private boolean allowCancelDialog = false;

    private BucketPresenter bucket;
    private List<ActivityPresenter> activityPresenters;

    public final static DataFlavor TASK_DATA_FLAVOR = new DataFlavor(
            TaskPresenter.class, "Task");

    /**
     * Constructor for testing methods without creating View, or
     * Buckets/Workflow Presenters PURELY TO TEST METHODS THAT ONLY OPERATE ON
     * TASKMODELS
     * 
     * @param model
     *            the model associated with this presenter
     */
    public TaskPresenter(TaskModel model) {
        this.model = new TaskModel();
        this.model.copyFrom(model);
    }

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
        assignedUserList = new ArrayList<Integer>(model.getAssignedTo());
        this.view = new TaskView(model, viewMode, this);
        this.miniView = new MiniTaskView(model);
        final Request userRequest = Network.getInstance().makeRequest(
                "core/user", HttpMethod.GET);
        userRequest.addObserver(new UsersObserver(this));
        userRequest.send();
        getRequirements();
        mapReqs();
        this.activityPresenters = new ArrayList<ActivityPresenter>();
        registerCallbacks();

    }

    /**
     * Register callbacks with the local view.
     */
    /**
     * 
     */
    private void registerCallbacks() {
        // onclick listener to expand minitaskview when clicked
        miniView.addOnClickOpenExpandedView(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                miniView.setModel(model);
                if (!miniView.isExpanded()) {
                    addUsersToMiniTaskView();
                    miniView.setExpandedView();
                } else {
                    miniView.setCollapsedView();
                }
                view.getCommentView().toggleTextField(ViewMode.EDITING);
                bucket.getView().revalidate();
                bucket.getView().repaint();
            }
        });

        // on click listener to edit tasks from the expanded task view
        miniView.addOnClickEditButton(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                mapReqs();
                updateView();
                MainView.getInstance().addTab(model.getShortTitle(),
                        Icons.TASKEDIT, view);// this line chooses tab title
                if (model.getIsArchived()) {
                    view.setViewMode(ViewMode.ARCHIVING);
                    view.getCommentView().toggleTextField(ViewMode.ARCHIVING);
                } else {
                    view.setViewMode(ViewMode.EDITING);
                }
                viewMode = view.getViewMode();
                int tabCount = MainView.getInstance().getTabCount();
                view.setIndex(tabCount - 1);
                MainView.getInstance().setSelectedIndex(tabCount - 1);
                MainView.getInstance().setToolTipTextAt(tabCount - 1,
                        model.getTitle());
                miniView.setModel(model);
                miniView.setCollapsedView();
            }
        });
        /* on click listener to restore or archive a task */
        miniView.addOnClickArchiveButton(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (miniView.getModel().getIsArchived()) {
                    miniView.getModel().setIsArchived(false);
                    miniView.getArchiveButton().setText("Archive");
                } else {
                    miniView.getModel().setIsArchived(true);
                    miniView.getArchiveButton().setText("Restore");
                }
                saveView();
                updateView();
                MainView.getInstance().resetAllBuckets();
            }
        });

        /* Set a handler to move the task when it's dragged and dropped */
        miniView.setTransferHandler(new TransferHandler() {
            /**
             * @return {@link TransferHandler#MOVE}. At least for now, tasks are
             *         moved, never copied or linked.
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
                return true;
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
                    if (taskPresenter.getModel().getId() == model.getId()) {
                        return false;
                    }
                    boolean flag = taskPresenter.getBucket().getModel()
                            .getTitle().equals(bucket.getModel().getTitle());
                    Point point = MainView.getInstance().getGlassPane()
                            .getPoint();
                    point = SwingUtilities.convertPoint(MainView.getInstance()
                            .getGlassPane(), point, bucket.getView());

                    bucket.insertTask(taskPresenter.getModel().getId(),
                            taskPresenter,
                            bucket.getView().getInsertionIndex(point, flag));

                    return true;
                } catch (UnsupportedFlavorException | IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }

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
            protected void exportDone(JComponent source, Transferable data,
                    int action) {
                GhostGlassPane glassPane = MainView.getInstance()
                        .getGlassPane();
                if (action != NONE) {
                    glassPane.setVisible(false);
                    miniView.setColorHighlighted(false);
                } else {
                    Point end = new Point(source.getLocationOnScreen());
                    SwingUtilities.convertPointFromScreen(end, glassPane);

                    end.x += glassPane.getStartDragPoint().x;
                    end.y += glassPane.getStartDragPoint().y;

                    Timer backTimer = new Timer(1000 / 60, new ReturnToOrigin(
                            glassPane, glassPane.getPoint(), end));
                    backTimer.start();
                    miniView.setColorHighlighted(false);
                }
            }
        });

        /**
         * Add listeners to the taskView okButton
         * 
         * @param ActionListener
         */
        view.addOkOnClickListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                updateBeforeModel();
                int index = MainView.getInstance().indexOfComponent(view);
                if (viewMode == ViewMode.CREATING) {
                    // CREATING MODE
                    updateModel();
                    createInDatabase(); // is calling "PUT" in task observer
                    view.setViewMode(ViewMode.EDITING);
                    addHistory("Create");
                }
                MainView.getInstance().remove(index);
                MainView.getInstance().setSelectedIndex(0);
                saveView();
                if (viewMode == ViewMode.EDITING) {
                    addHistory(beforeModel, model);
                }
                updateView();
                MainView.getInstance().resetAllBuckets();
                miniView.setModel(model);
                miniView.revalidate();
                miniView.repaint();
            }
        });
        view.addCancelOnClickListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cancelDialog
                        .setModalityType(Dialog.ModalityType.APPLICATION_MODAL);
                cancelDialog
                        .setCommentLabelText("Are you sure you want to close the tab?");
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
                if (cancelDialogConfirmed) {
                    int index = MainView.getInstance().indexOfComponent(view);
                    MainView.getInstance().remove(index);
                    MainView.getInstance().setSelectedIndex(0);
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
                undoDialog
                        .setModalityType(Dialog.ModalityType.APPLICATION_MODAL);
                undoDialog
                        .setCommentLabelText("Are you sure you want to undo your changes?");
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
                if (undoDialogConfirmed) {
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
                deleteDialog
                        .setModalityType(Dialog.ModalityType.APPLICATION_MODAL);
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
                if (viewMode == ViewMode.ARCHIVING) {
                    deleteDialog
                            .setCommentLabelText("Are you sure you want to delete this task?");
                    deleteDialog.setVisible(true);
                }
                if (deleteDialogConfirmed) {// delete has been confirmed
                    int index = MainView.getInstance().indexOfComponent(view);
                    MainView.getInstance().remove(index);
                    MainView.getInstance().setSelectedIndex(0);
                    if (viewMode == ViewMode.ARCHIVING) {// delete task

                        TaskPresenter taskPresenter = bucket.getTask(model
                                .getId());
                        bucket.removeTaskView(taskPresenter);

                    }

                }
            }
        });

        view.getCommentView().addOnPostListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent arg0) {
                addActivity();
            }

        });

        view.addRequirementButtonListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent arg0) {
                if (reqMap.get(view.getRequirementIndex() - 1) != null) {
                    JTabbedPane janeway = (JTabbedPane) MainView.getInstance()
                            .getParent().getParent();
                    int index = janeway.indexOfTab("Requirement Manager");
                    janeway.setSelectedIndex(index);

                    ViewEventController.getInstance().editRequirement(
                            reqMap.get(view.getRequirementIndex() - 1));
                }

            }

        });
    }

    /**
     * Sends a network request to query all of the available requirements
     */
    public void getRequirements() {
        ViewEventController.getInstance().getOverviewTable().initialize();
    }

    /**
     * Compares the task models before and after it is updated and makes a
     * string that contains all the user changes
     * 
     * @param before
     *            the task model before it was updated.
     * @param after
     *            the task model after it was updated.
     * @return summary the message which shows what has changed.
     */
    public String compareTasks(TaskModel before, TaskModel after) {
        boolean flag = false;// flag to tell if first print or not.
        DateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
        String summary = "";
        if (before.getTitle().compareTo(after.getTitle()) != 0) {
            summary = "Title was changed from " + before.getTitle() + " to "
                    + after.getTitle();
            flag = true;
        }
        if (before.getActualEffort() != after.getActualEffort()) {
            if (flag) {
                summary += "\n";
            }
            summary += "Actual Effort was changed from "
                    + before.getActualEffort() + " to "
                    + after.getActualEffort();
            flag = true;
        } else if (!flag) {
            flag = false;
        }
        if (before.getEstimatedEffort() != after.getEstimatedEffort()) {
            if (flag) {
                summary += "\n";
            }
            summary += "Estimated Effort was changed from "
                    + before.getEstimatedEffort() + " to "
                    + after.getEstimatedEffort();
            flag = true;
        } else if (!flag) {
            flag = false;
        }
        if (before.getDueDate().compareTo(after.getDueDate()) != 0) {
            if (flag) {
                summary += "\n";
            }
            summary += "Due Date was changed from "
                    + dateFormat.format(before.getDueDate()) + " to "
                    + dateFormat.format(after.getDueDate());
            flag = true;
        } else if (!flag) {
            flag = false;
        }
        if (before.getDescription().compareTo(after.getDescription()) != 0) {
            if (flag) {
                summary += "\n";
            }
            summary += "Description was changed.";
            flag = true;

        } else if (!flag) {
            flag = false;
        }
        if (before.getRequirement() != after.getRequirement()) {
            if (flag) {
                summary += "\n";
            }
            summary += "Associated requirement was changed from "
                    + (before.getRequirement() == 0 ? "none" : RequirementModel
                            .getInstance()
                            .getRequirement(before.getRequirement() - 1)
                            .getName())
                    + " to "
                    + (after.getRequirement() == 0 ? "none" : RequirementModel
                            .getInstance()
                            .getRequirement(after.getRequirement() - 1)
                            .getName());
        }
        if (!before.getAssignedTo().equals(after.getAssignedTo())) {
            ArrayList<Integer> beforeTemp = new ArrayList<Integer>(
                    before.getAssignedTo());
            ArrayList<Integer> afterTemp = new ArrayList<Integer>(
                    after.getAssignedTo());
            if (flag) {
                summary += "\n";
            }
            if (before.getAssignedTo().size() > after.getAssignedTo().size()) {
                beforeTemp.removeAll(afterTemp);
                // find difference between the before and after IDs
                for (int i = 0; i < beforeTemp.size(); i++) {
                    summary += idToUsername(beforeTemp.get(i))
                            + " was removed.";
                    if (i < beforeTemp.size() - 1) {
                        summary += "\n";
                    }
                }

            } else {
                afterTemp.removeAll(beforeTemp);
                for (int i = 0; i < afterTemp.size(); i++) {
                    summary += idToUsername(afterTemp.get(i)) + " was added.";
                    if (i < afterTemp.size() - 1) {
                        summary += "\n";
                    }
                }
            }
            flag = true;

        } else if (!flag) {
            flag = false;
        }
        if (before.getStatus() != after.getStatus()) {
            if (flag) {
                summary += "\n";
            }
            summary += "Task was moved from " + intToStatus(before.getStatus())
                    + " to " + intToStatus(after.getStatus());

            flag = true;
        }
        if (before.getLabelColor() != null) {
            if (!before.getLabelColor().equals(after.getLabelColor())) {
                if (flag) {
                    summary += "\n";
                }
                summary += "Label was changed from "
                        + ColorRenderer.evaluateColor(before.getLabelColor()
                                .toString())
                        + " to "
                        + ColorRenderer.evaluateColor(after.getLabelColor()
                                .toString());
            }
        }
        return summary;
    }

    /**
     * returns the bucket name with the given ID.
     * 
     * @param bucket
     *            the bucket's ID
     * @return String the name of the bucket
     */
    private String intToStatus(int bucket) {
        return this.bucket.getWorkflow().idToBucketName(bucket);
    }

    /**
     * Updates another model with the fields before it gets updated
     */
    public void updateBeforeModel() {
        beforeModel.setTitle(model.getTitle());
        beforeModel.setDescription(model.getDescription());
        beforeModel.setEstimatedEffort(model.getEstimatedEffort());
        beforeModel.setAssignedTo(model.getAssignedTo());
        beforeModel.setActualEffort(model.getActualEffort());
        beforeModel.setDueDate(model.getDueDate());
        beforeModel.setStatus(model.getStatus());
        beforeModel.setAssignedTo(new ArrayList<>(model.getAssignedTo()));
        beforeModel.setActivityIds(new ArrayList<>(model.getActivityIds()));
        beforeModel.setLabelColor(model.getLabelColor());
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
        String username = ConfigManager.getConfig().getUserName();
        String activity = username + " has updated tasks on "
                + dateFormat.format(cal.getTime()) + ":\n";

        activity += compareTasks(before, after);
        ActivityPresenter activityPresenter = new ActivityPresenter(this,
                activity, true);
        view.getCommentView().postHistory(activityPresenter.getView());
        activityPresenter.createInDatabase();
        activityPresenters.add(activityPresenter);

        /*
         * Send an email to any users assigned to this task (or who used to be
         * assigned to this task) saying what just changed.
         */
        List<String> recipients = new ArrayList<>();
        for (User user : this.allUserArray) {
            if ((before.getAssignedTo().contains(user.getIdNum()) || after
                    .getAssignedTo().contains(user.getIdNum()))
                    && user.getEmailAddress() != null
                    && !user.getEmailAddress().isEmpty()) {
                if (!recipients.contains(user.getEmailAddress())) {
                    recipients.add(user.getEmailAddress());
                }
            }
        }

        Notification notification = new Notification();
        notification.setRecipients(recipients);
        notification.setSubject(ConfigManager.getConfig().getUserName()
                + " updated " + this.model.getTitle() + " in "
                + ConfigManager.getConfig().getProjectName());
        notification.setContent(before.compareToHtml(after));
        notification.setProjectName(ConfigManager.getConfig().getProjectName());

        Request request = Network.getInstance().makeRequest("Notification",
                HttpMethod.POST);
        request.setBody(notification.toJson());
        request.send();
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
     * @param users
     *            User array of all users in the database
     */
    public void addUsersToAllUserList(User[] users) {
        this.allUserArray = users;
    }

    /**
     * Sets the assigned user list to a new list
     */
    public void setAssignedUserList() {
        this.assignedUserList = new ArrayList<Integer>();
    }

    /**
     * Takes the allUsers array, and checks users with assigned users list all
     * assigned users get added to the assigned view, and all others get added
     * to unassigned view
     */
    public void addUsersToView() {
        this.view.getUserListPanel().removeAllUsers();
        for (User user : allUserArray) {
            if (assignedUserList.contains(user.getIdNum())) {
                this.view.getUserListPanel().addUserToList(user, true);
            } else {
                this.view.getUserListPanel().addUserToList(user, false);
            }
        }
    }

    /**
     * put requirements into a hashmap, the requirement id is the key
     * 
     * @param reqs
     *            Requirement array of all requirement in the database
     */
    public void mapReqs() {
        List<Requirement> reqs = RequirementModel.getInstance()
                .getRequirements();
        for (Requirement r : reqs) {
            reqMap.put(r.getId(), r);
        }
        view.addRequirementsToComboBox(reqs);
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
        model.setRequirement(view.getRequirementIndex());
        model.setLabelColor(view.getLabelColor());
    }

    /**
     * Update the view with data from the model
     */
    public void updateView() {
        mapReqs();
        view.setRequirement(model.getRequirement());
        view.setModel(model);
        miniView.setModel(model);
        updateCommentView();
        assignedUserList = new ArrayList<Integer>(model.getAssignedTo());
        addUsersToView();
        this.setIconForMinitaskView();

        if (model.getIsArchived()) {
            miniView.setColorArchived(true);
        } else {
            miniView.setColorArchived(false);
        }
        view.revalidate();
        view.repaint();

        miniView.setModel(model);
        miniView.setToolTipText(model.getTitle());
        miniView.updateLabel();
    }

    /**
     * takes the current comment view, clears the posts, and puts each comment,
     * one by one back on to the current view.
     */
    public void updateCommentView() {
        view.getCommentView().clearPosts();
        for (ActivityPresenter p : activityPresenters) {
            if (p.getModel().getIsAutogen()) {
                view.getCommentView().postHistory(p.getView());
            } else {
                view.getCommentView().postActivity(p.getView());
            }
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
        return this.view;
    }

    /**
     * Get the miniView for this Task.
     * 
     * @return miniView for Task
     */
    public MiniTaskView getMiniView() {
        return this.miniView;
    }

    /**
     * Get the model for this class.
     * 
     * @return This provider's model.
     */
    public TaskModel getModel() {
        return this.model;
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
        if (this.model.getIsArchived()) {// if task is archived, will have to be
                                         // removed from view
            bucket.addMiniTaskstoView();
        }
        this.updateView();
    }

    /**
     * 
     * @return the bucket the task is contained within
     */
    public BucketPresenter getBucket() {
        return bucket;
    }

    /**
     * @param bucket
     *            the bucket that this task is in
     */
    public void setBucket(BucketPresenter bucket) {
        this.bucket = bucket;
        this.model.setStatus(bucket.getModel().getId());
    }

    /**
     * Removes a user from the assignedTo list
     * 
     * @param user
     *            User to remove from assignedTo
     */
    public void removeUserFromAssignedTo(User user) {
        this.assignedUserList.remove((Object) user.getIdNum());
    }

    /**
     * Add a user to the assignedTo list
     * 
     * @param user
     *            User to add to assignedTo
     */
    public void addUserToAssignedTo(User user) {
        this.assignedUserList.add(user.getIdNum());
        this.view.validateFields();
    }

    /**
     * @return A shallow copy of the temporary assigned users list, not the
     *         model's user list
     */
    public List<Integer> getAssignedUserList() {
        return this.assignedUserList;
    }

    /**
     * @param enable
     *            Whether or not to enable the cancel dialog
     */
    public void setAllowCancelDialogEnabled(boolean enable) {
        this.allowCancelDialog = enable;
        this.cancelDialogConfirmed = !enable; // if the dialog is enabled, the
                                              // confirmation of the dialog box
                                              // is opposite
    }

    /**
     * returns the Username with the given ID, otherwise blank.
     * 
     * @param id
     *            the user's ID
     * @return username the Username
     */
    public String idToUsername(int id) {
        for (User u : this.allUserArray) {
            if (u.getIdNum() == id) {
                return u.getUsername();
            }
        }
        return "";
    }

    /**
     * returns the Name with the given ID, otherwise blank.
     * 
     * @param id
     *            the user's ID
     * @return name the Name
     */
    public String idToName(int id) {
        for (User u : this.allUserArray) {
            if (u.getIdNum() == id) {
                return u.getName();
            }
        }
        return "";
    }

    /**
     * set icon for the task in update view
     */
    public void setIconForMinitaskView() {
        Calendar cal = Calendar.getInstance();
        Date nowDate = cal.getTime(); // Current Date
        Date dueDate = model.getDueDate();
        // Get time differences
        long leftTime = dueDate.getTime() - nowDate.getTime();
        long leftInHours = TimeUnit.MILLISECONDS.toHours(leftTime);
        // Set icons
        if (leftInHours == 0) { // On the date it's due
            this.miniView.setTaskNameLabelIcon(Icons.TASKDUE);
        } else {
            if (leftInHours < -24) { // Overdue
                this.miniView.setTaskNameLabelIcon(Icons.TASKDUE);
            } else if (leftInHours < 0) { // Nearly due
                this.miniView.setTaskNameLabelIcon(Icons.TASKNEARDUE);
            } else if (leftInHours < 48) { // In progress
                this.miniView.setTaskNameLabelIcon(Icons.TASKSTART);
            } else if (dueDate.getMonth() + 1 == 12 && dueDate.getDate() == 25) {
                this.miniView.setTaskNameLabelIcon(Icons.TASKCHRISTMAS);
            } else if (dueDate.getMonth() + 1 == 4 && dueDate.getDate() == 20) {
                this.miniView.setTaskNameLabelIcon(Icons.TASKSNOOPDOGG);
            } else if (dueDate.getMonth() + 1 == 10 && dueDate.getDate() == 31) {
                this.miniView.setTaskNameLabelIcon(Icons.TASKHALLOWEEN);
            } else { // New
                this.miniView.setTaskNameLabelIcon(Icons.TASKNEW);
            }
        }
    }

    /**
     * Wrapper function to add all assigned users to the miniTaskView
     */
    public void addUsersToMiniTaskView() {
        List<String> userNames = new ArrayList<String>();
        for (User user : allUserArray) {
            if (assignedUserList.contains(user.getIdNum())) {
                userNames.add(user.getName());
            }
        }
        miniView.addUsersToUserPanel(userNames);
    }

    /**
     * check if the label is null, then if it is not, then update the miniview
     * to the color of the task's colorLabel
     * 
     * @param model
     *            taskModel containing a colorLabel
     */
    public void validateUpdateLabel() {
        // white does no setBackground to panel.
        if (this.getModel().getLabelColor() != null) {
            if (!this.getModel().getLabelColor()
                    .equals(new Color(255, 255, 255))) {
                this.getMiniView().getColorLabel()
                        .setBackground(this.getModel().getLabelColor());
            }
        }
    }

    /**
     * 
     * @return beforeModel, TaskModel before updateBeforeModel() is called.
     */
    public TaskModel getBeforeModel() {
        return beforeModel;
    }

    /**
     * Set the model for this class. EXCEPT does not update any Views.
     * 
     * @param model
     *            This provider's model.
     */
    public void setModelNoView(TaskModel other) {
        this.model = other;
    }

    /**
     * Posts in the History Panel any task movement from Drag and Drop.
     * 
     * @param statusBefore
     *            the bucketID before drag and drop.
     * @param statusAfter
     *            the bucketID after drag and drop
     */
    public void dragDropHistory(int statusBefore, int statusAfter) {
        String activity = "";
        DateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy hh:mm:ss a");
        Calendar cal = Calendar.getInstance();
        String user = ConfigManager.getConfig().getUserName();
        activity = user + " has moved this task from "
                + this.bucket.getWorkflow().idToBucketName(statusBefore)
                + " to "
                + this.bucket.getWorkflow().idToBucketName(statusAfter)
                + " on " + dateFormat.format(cal.getTime());

        ActivityPresenter activityPresenter = new ActivityPresenter(this,
                activity, true);
        view.getCommentView().postHistory(activityPresenter.getView());
        activityPresenter.createInDatabase();
        activityPresenters.add(activityPresenter);
    }

}
