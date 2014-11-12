/*
 * 
 * 
 * 
 */


package edu.wpi.cs.wpisuitetng.modules.taskmanager.presenter;

import java.util.ArrayList;
import java.util.List;

import edu.wpi.cs.wpisuitetng.modules.taskmanager.model.BucketModel;
import edu.wpi.cs.wpisuitetng.modules.taskmanager.view.BucketView;
import edu.wpi.cs.wpisuitetng.network.Network;
import edu.wpi.cs.wpisuitetng.network.Request;
import edu.wpi.cs.wpisuitetng.network.models.HttpMethod;

import javax.swing.JPanel;

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
     * Constructor does nothing for now
     * 
     * @param view
     *            The view associated with this presenter
     */
    public BucketPresenter(int bucketID) {
        this.model = new BucketModel();
        model.setID(bucketID);
        this.view = new BucketView("Loading...");

        load();
    }

    /**
     * 
     */
    public void load() {
        HttpMethod method;
        String id = "/" + model.getID();
        if (model.getID() == 0) { // Put = create a new model
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
     * 
     */
    public void writeModelToView() {
    	String name = "";
    	switch(model.getID()){
    	case 2:
    		name = "New";
    		break;
    	case 3:
    		name = "Selected";
    		break;
    	case 4:
    		name = "In Progress";
    		break;
    	case 5:
    		name = "Completed";
    		break;
    	}
    	model.setTitle(name);
    	
        view.setTitle(model.getTitle());
        // Add taskviews to the BucketView
        saveModel();
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

    public void responseGet(BucketModel[] models) {
    	if(models[0].getID() == 0)
    		return;
        this.model = models[0];
        writeModelToView();
    }

    public void responsePost(BucketModel model) {
    	
    }

    public void responsePut(BucketModel model) {
        this.model = model;
        writeModelToView();
    }

    public void responseDelete(BucketModel model) {

    }

    public BucketView getView() {
        return view;
    }

    public BucketModel getModel() {
        return model;
    }
}
