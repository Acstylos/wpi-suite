/*
 * 
 * 
 * 
 */

package edu.wpi.cs.wpisuitetng.modules.taskmanager.presenter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import edu.wpi.cs.wpisuitetng.modules.taskmanager.model.BucketModel;
import edu.wpi.cs.wpisuitetng.modules.taskmanager.view.BucketView;
import edu.wpi.cs.wpisuitetng.modules.taskmanager.view.MiniTaskView;
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
        this.view = new BucketView("Loading...");
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

        view.setTitle(model.getTitle());
        List<Integer> taskIds = model.getTaskIds();
        view.setTaskViews(new ArrayList<>());
        view.setTaskViews(new ArrayList<MiniTaskView>());
        System.out.println(model.getTitle() + ": " + taskIds.toString());
        for (int i : taskIds) {
            if(!taskMap.containsKey(i)){
                taskMap.put(i, new TaskPresenter(i, this, ViewMode.EDITING));
            }
            taskMap.get(i).updateFromDatabase();
            MiniTaskView miniTaskView = taskMap.get(i).getMiniView();
            view.addTaskToView(miniTaskView);
        }
        view.revalidate();
        view.repaint();
    }

    /**
     * Register callbacks with the local view.
     */
    private void registerCallbacks() {
    }

    /**
     * remove a task ID from the list of taskIDs in the model
     * Sends an async update to the database
     * @param id  ID of the existing task
     */
    public void removeTask(int id) {
        model.removeTaskId(id);
        updateInDatabase();
        writeModelToView();
    }
    
    /**
     * Adds a task ID to the list of taskIDs in the model.
     * Sends an async update to the database.
     * @param id ID of the existing task.
     */
    public void addTask(int id){
        model.addTaskID(id);
        updateInDatabase();
        writeModelToView();
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
     * @param miniView simply add the miniTaskView to view
     */
    public void addMiniTaskView(MiniTaskView miniView) {
        view.addTaskToView(miniView);
    }
}
