package edu.wpi.cs.wpisuitetng.modules.taskmanager.presenter;

import edu.wpi.cs.wpisuitetng.modules.taskmanager.model.TaskModel;
import edu.wpi.cs.wpisuitetng.network.RequestObserver;
import edu.wpi.cs.wpisuitetng.network.models.IRequest;

/**
 * Observes the result of network requests for tasks
 * @author TheFloorIsJava
 */
public class TaskObserver implements RequestObserver {

    TaskPresenter presenter;

    /**
     * Construct the observer
     * 
     * @param presenter
     *            The presenter that make the request
     */
    public TaskObserver(TaskPresenter presenter) {
        this.presenter = presenter;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void responseSuccess(IRequest iReq) {
        System.out.println("Received response: " + iReq.getResponse().getBody());
        
        /*
         * Take the appropriate action based on what the method of the request
         * was.
         */
        switch (iReq.getHttpMethod()) {
        case GET:
        case PUT:
            String json = iReq.getResponse().getBody();
            TaskModel model = TaskModel.fromJsonArray(json)[0];
            
            /* Set the new model and update the view to reflect the new
             * data.  GET and PUT requests both respond with a modified
             * task - GET returns the task stored in the database and PUT
             * returns the same task but with a new ID assigned. */
            this.presenter.getModel().copyFrom(model);
            this.presenter.updateView();
            
            break;

        case POST:
            break;

        case DELETE:
            break;
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void responseError(IRequest iReq) {
        System.err.println(iReq.getResponse());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void fail(IRequest iReq, Exception exception) {
        System.err.println("Task request failed");
        exception.printStackTrace();
    }
}
