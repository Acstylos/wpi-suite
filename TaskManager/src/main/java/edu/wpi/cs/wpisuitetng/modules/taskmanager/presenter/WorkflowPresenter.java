/*
 * 
 * 
 * 
 */

package edu.wpi.cs.wpisuitetng.modules.taskmanager.presenter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import edu.wpi.cs.wpisuitetng.modules.taskmanager.model.WorkflowModel;
import edu.wpi.cs.wpisuitetng.modules.taskmanager.view.BucketView;
import edu.wpi.cs.wpisuitetng.modules.taskmanager.view.WorkflowView;
import edu.wpi.cs.wpisuitetng.network.Network;
import edu.wpi.cs.wpisuitetng.network.Request;
import edu.wpi.cs.wpisuitetng.network.models.HttpMethod;

/**
 * Creates 4 static buckets for the workflow and passes it to the view
 * 
 * @author TheFloorIsJava
 * 
 */

public class WorkflowPresenter {

    private WorkflowView view;
    private WorkflowModel model;
    private Map<Integer, BucketPresenter> bucketPresenters = new HashMap<>();

    /**
     * Constructor does nothing for now
     *
     * @param workflowId
     *            the id of the workflow 
     */
    public WorkflowPresenter(int workflowId) {
        this.model = new WorkflowModel();
        model.setId(workflowId);
        this.view = new WorkflowView("Loading...");
    }

    /**
     * Request the server for a new workflow or the workflow corresponding to the
     * current ID
     */
    public void load() {
        // Sends a request for the TaskViews associated with the BucketView
        final Request request = Network.getInstance().makeRequest(
                "taskmanager/workflow", HttpMethod.GET);
        request.addObserver(new WorkflowObserver(this, HttpMethod.GET));
        request.send();
    }
    
    /**
     * Initializes the workflow and stores it to the DB.
     * @param presenter Presenter to associate the workflow with.
     */
    public void initWorkflow(WorkflowPresenter presenter){
        saveBaseWorkflow(presenter);
        BucketPresenter.saveBaseBuckets();
        writeModelToView();
    }
    
    /**
     * Adds in a default workflow with id=1 to the DB
     * @param presenter Presenter to associate the workflow with.
     */
    private void saveBaseWorkflow(WorkflowPresenter presenter){
        WorkflowModel workflow = new WorkflowModel(1, "Workflow");
        ArrayList<Integer> baseBucketList = new ArrayList<Integer>();
        baseBucketList.add(1);
        baseBucketList.add(2);
        baseBucketList.add(3);
        baseBucketList.add(4);
        workflow.setBucketIds(baseBucketList);
        presenter.setModel(workflow);
        final Request request = Network.getInstance().makeRequest("taskmanager/workflow", HttpMethod.PUT);
        System.out.println("Workflow JSON: " + workflow.toJson().toString());
        request.setBody(workflow.toJson());
        request.addObserver(new WorkflowObserver(presenter, HttpMethod.PUT));
        request.send();
    }

    /**
     * Set the view of the model
     */
    public void writeModelToView() {
        ArrayList<Integer> buckets = model.getBucketIds();
        System.out.println("Buckets in the Workflow: " + buckets.toString());
        ArrayList<BucketView> bucketViews = new ArrayList<BucketView>();
        for(int bucketId: buckets){
            BucketPresenter bucketPresenter = new BucketPresenter(bucketId);
            bucketPresenters.put(bucketId, bucketPresenter);
            bucketPresenter.load();
            bucketViews.add(bucketPresenter.getView());
        }
        view.setBucketViews(bucketViews);
        view.revalidate();
        view.repaint();
        saveModel();
    }

    /**
     * Write the model to the network/database. Must be created already.
     */
    private void saveModel() {
        Request request = Network.getInstance().makeRequest(
                "taskmanager/workflow", HttpMethod.POST); // Update.
        request.setBody(model.toJson());
        request.addObserver(new WorkflowObserver(this, HttpMethod.POST));
        request.send();
    }

    /**
     * Handles the result of a GET request
     * 
     * @param models
     *            The models sent from the network
     */
    public void responseGet(WorkflowModel model) {
        this.model = model;
        writeModelToView();
    }

    /**
     * Handles the result of a POST request
     * 
     * @param model
     *            The model sent from the network
     */
    public void responsePost(WorkflowModel model) {

    }

    /**
     * Handles the result of a PUT request
     * 
     * @param model
     *            The model sent from the network
     */
    public void responsePut(WorkflowModel model) {
    }

    /**
     * Handles the result of a DELETE request
     * 
     * @param model
     *            The model sent from the network
     */
    public void responseDelete(WorkflowModel model) {

    }

    /**
     * @return The view corresponding to the model
     */
    public WorkflowView getView() {
        return view;
    }
    
    /**
     * @param model The model this workflow presenter represents.
     */
    public void setModel(WorkflowModel model){
        this.model = model;
    }

    /**
     * @return The model corresponding to the presenter
     */
    public WorkflowModel getModel() {
        return model;
    }
    
    /**
     * @return The list of BucketPresenters inside of the workflow.
     */
    public Map<Integer, BucketPresenter> getBucketPresenters() {
        return bucketPresenters;
    }
    
    /**
     * @param id ID of bucket to return
     * @return a bucket with the corresponding ID
     */
    public BucketPresenter getBucketPresenterById(int id){
        return bucketPresenters.get(id);
    }

    /**
     * move tasks from one bucket to another
     * 
     * @param taskId
     *            The id of tasks needed to be moved
     * @param toId
     *            The id of the bucket task moved to
     * @param fromId
     *            The id of the bucket task moved from
     */
    public void moveTask(int taskId, int toId, int fromId) {
        bucketPresenters.get(toId).addTask(taskId, bucketPresenters.get(fromId).getTask(taskId));
        bucketPresenters.get(fromId).removeTask(taskId);
    }

    /**
     * get the bucket id from the hashmap
     * @param id the id of the bucket to be retrieved
     * @return the bucket model from the hashmap
     */
	public BucketPresenter getBucket(int id) {
		return bucketPresenters.get(id);
	}
}
