/*
 * 
 * 
 * 
 */


package edu.wpi.cs.wpisuitetng.modules.taskmanager.presenter;

import java.awt.event.ActionEvent;

import edu.wpi.cs.wpisuitetng.modules.taskmanager.model.BucketModel;
import edu.wpi.cs.wpisuitetng.modules.taskmanager.view.BucketView;
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

    /**
     * Constructor for a bucket presenter
     * 
     * @param view
     *            The view associated with this presenter
     */
    public BucketPresenter(int bucketId) {
        this.model = new BucketModel();
        this.model.setId(bucketId);
        this.view = new BucketView("Loading...");

        load();
    }

    /**
     * Requests the server for a new bucket or the bucket corresponding to the current ID
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
        if(method == HttpMethod.PUT){
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
    	switch(model.getId()){
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
    	}
    	if(name.length() > 1){
    		model.setTitle(name);
    	}
    	
        view.setTitle(model.getTitle());
        // Add taskviews to the BucketView
        saveModel();
    }
    
    /**
     * Register callbacks with the local view.
     */
    private void registerCallbacks() {
        view.addOnAddTaskListener((ActionEvent event) -> {
            BucketPresenter.this.addNewTaskToView();
        });
    }
    
    public void addNewTaskToView(){
    }

    /**
     * Write the model to the network/database. Must be created already.
     */
    private void saveModel() {
        Request request = Network.getInstance().makeRequest(
                "taskmanager/bucket", HttpMethod.POST); // Update.
        request.setBody(model.toJson());
        request.addObserver(new BucketObserver(this, HttpMethod.POST));
        request.send();
    }
    
    /**
     * Handles the result of a GET request
     * @param models The models sent from the network
     */
    public void responseGet(BucketModel[] models) {
    	if(models[0].getId() == 0)
    		return;
        this.model = models[0];
        writeModelToView();
    }

    /**
     *  Handles the result of a POST request
     * @param model The model sent from the network
     */
    public void responsePost(BucketModel model) {
    	
    }

    /**
     * Handles the result of a PUT request
     * @param model The model sent from the network
     */
    public void responsePut(BucketModel model) {
        this.model = model;
        writeModelToView();
    }
    
    /**
     * Handles the result of a DELETE request
     * @param model The model sent from the network
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
     * @param model THe model of the presenter to be set
     */
    public void setModel(BucketModel model) {
        this.model = model;
        writeModelToView();
    }
}
