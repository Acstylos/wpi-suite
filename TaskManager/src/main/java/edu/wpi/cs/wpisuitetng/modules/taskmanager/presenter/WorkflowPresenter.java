/*
 * 
 * 
 * 
 */


package edu.wpi.cs.wpisuitetng.modules.taskmanager.presenter;

import java.util.ArrayList;
import java.util.List;

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

    /**
     * Constructor does nothing for now
     *
     * @param view
     *            The view associated with this presenter
     */
    public WorkflowPresenter(int workflowId) {
        this.model = new WorkflowModel();
        model.setId(workflowId);
        this.view = new WorkflowView("Loading...");

        load();
    }
    
    /**
     * Request the server for a new bucker or the bucket corresponding to the current ID
     */
    public void load() {
        HttpMethod method;
        String id = "/" + model.getId();
        if (model.getId() == 0) {// Put = create a new model
            method = HttpMethod.PUT;
            id = "";
        } else {// Retrieve a model
            method = HttpMethod.GET;
        }

        // Sends a request for the TaskViews associated with the BucketView
        final Request request = Network.getInstance().makeRequest(
                "taskmanager/workflow" + id, method);
        if(method == HttpMethod.PUT){
        	request.setBody(model.toJson());
        }
        request.addObserver(new WorkflowObserver(this, method));

        request.send();
    }
    
    /**
     * Set the view of the model
     */
    public void writeModelToView() {
    	while(model.getBucketIds().size() < 4){
    		ArrayList<Integer> buckets = model.getBucketIds();
    		buckets.add(0);
    		model.setBucketIds(buckets);
    	}
    	
    	int hardCodedId = 1;
    	
    	ArrayList<Integer> newBuckets = new ArrayList<Integer>();
        view.setTitle(model.getTitle());
        List<BucketView> bucketViews = new ArrayList<BucketView>();
        for (int i : model.getBucketIds()) {
            BucketPresenter bucketPresenter = new BucketPresenter(i);
            bucketPresenter = new BucketPresenter(hardCodedId);
            hardCodedId++;
            
            newBuckets.add(bucketPresenter.getModel().getId());
            System.out.println(bucketPresenter.getModel().getId());
            bucketViews.add(bucketPresenter.getView());
        }

        
        view.setBucketViews(bucketViews);
        model.setBucketIds(newBuckets);
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
     * @param models The models sent from the network
     */
    public void responseGet(WorkflowModel[] models) {
    	if(models[0].getId() == 0)
    		return;
        this.model = models[0];
        writeModelToView();
    }

    /**
     *  Handles the result of a POST request
     * @param model The model sent from the network
     */
    public void responsePost(WorkflowModel model) {

    }

    /**
     * Handles the result of a PUT request
     * @param model The model sent from the network
     */
    public void responsePut(WorkflowModel model) {
    	this.model = model;
    	writeModelToView();
    }

    /**
     * Handles the result of a DELETE request
     * @param model The model sent from the network
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
     * @return The model corresponding to the presenter
     */
    public WorkflowModel getModel() {
        return model;
    }
}