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
    public WorkflowPresenter(int workflowID) {
        this.model = new WorkflowModel();
        model.setID(workflowID);
        this.view = new WorkflowView("Loading...");

        load();
    }
    
    /**
     * 
     */
    public void load() {
        HttpMethod method;
        String id = "/" + model.getID();
        if (model.getID() == 0) {// Put = create a new model
            method = HttpMethod.PUT;
            id = "";
        } else {// Retrieve a model
            method = HttpMethod.GET;
        }

        // Sends a request for the TaskViews associated with the BucketView
        final Request request = Network.getInstance().makeRequest(
                "taskmanager/workflow" + id, method);
        request.addObserver(new WorkflowObserver(this, method));
        if(method == HttpMethod.PUT){
        	request.setBody(model.toJson());
        }
        request.send();
    }
    
    /**
     * 
     */
    public void writeModelToView() {
    	if(model.getBucketIDs().size() < 4){
    		ArrayList<Integer> buckets = new ArrayList<Integer>();
    		buckets.add(0);
    		buckets.add(0);
    		buckets.add(0);
    		buckets.add(0);
    		model.setBucketIDs(buckets);
    	}
    	
    	int hardCodedId = 2;
    	
    	ArrayList<Integer> newBuckets = new ArrayList<Integer>();
        view.setTitle(model.getTitle());
        List<BucketView> bucketViews = new ArrayList<BucketView>();
        for (int i : model.getBucketIDs()) {
            BucketPresenter bucketPresenter = new BucketPresenter(hardCodedId);
            hardCodedId++;
            bucketViews.add(bucketPresenter.getView());
            if(i == 0)
            	i = bucketPresenter.getModel().getID();
            newBuckets.add(bucketPresenter.getModel().getID());
        }
        view.setBucketViews(bucketViews);
        model.setBucketIDs(newBuckets);
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

    public void responseGet(WorkflowModel[] models) {
    	if(models[0].getID() == 0)
    		return;
        this.model = models[0];
        writeModelToView();
    }

    public void responsePost(WorkflowModel model) {

    }

    public void responsePut(WorkflowModel model) {
    	this.model = model;
    	writeModelToView();
    }

    public void responseDelete(WorkflowModel model) {

    }

    public WorkflowView getView() {
        return view;
    }

    public WorkflowModel getModel() {
        return model;
    }
}
