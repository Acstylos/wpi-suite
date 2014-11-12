/*
 * 
 * 
 * 
 */

package edu.wpi.cs.wpisuitetng.modules.taskmanager.presenter;

import edu.wpi.cs.wpisuitetng.modules.taskmanager.model.BucketModel;
import edu.wpi.cs.wpisuitetng.modules.taskmanager.view.workflow.BucketView;
import edu.wpi.cs.wpisuitetng.network.Network;
import edu.wpi.cs.wpisuitetng.network.Request;
import edu.wpi.cs.wpisuitetng.network.models.HttpMethod;

/**
 * The presenter for the BucketView which sends the view all of the TaskViews
 * associated with the view.
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
        request.setBody(model.toJson());
        request.addObserver(new BucketObserver(this, method)); // add an
                                                               // observer to
                                                               // the response
        request.send();
    }

    public void writeModelToView() {
        view = new BucketView(model.getTitle());
        // Add taskviews to the BucketView
    }

    public void responseGet(BucketModel model) {
        if (model.getID() == 0)
            return;
        this.model = model;
        writeModelToView();
    }

    public void responsePost(BucketModel model) {

    }

    public void responsePut(BucketModel model) {
        this.model = new BucketModel();
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
