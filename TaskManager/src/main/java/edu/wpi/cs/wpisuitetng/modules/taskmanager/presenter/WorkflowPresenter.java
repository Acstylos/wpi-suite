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
 * The presenter for the WorkflowView which sends the view all of the
 * BucketViews associated with the view. Creates 4 static buckets and passes it
 * to the view
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
        request.addObserver(new WorkflowObserver(this, method)); // add an
        request.setBody(model.toJson());
        request.send();
    }

    public void writeModelToView() {
        view = new WorkflowView(model.getTitle());
        List<BucketView> bucketViews = new ArrayList<BucketView>();
        for (int i : model.getBucketIDs()) {
            BucketPresenter bucketPresenter = new BucketPresenter(i);
            bucketViews.add(bucketPresenter.getView());
        }
        view.setBucketViews(bucketViews);
    }

    public void responseGet(WorkflowModel model) {
        if (model.getID() == 0)
            return;
        this.model = model;
        writeModelToView();
    }

    public void responsePost(WorkflowModel model) {

    }

    public void responsePut(WorkflowModel model) {
        this.model = new WorkflowModel();
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
