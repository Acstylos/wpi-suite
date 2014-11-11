package edu.wpi.cs.wpisuitetng.modules.taskmanager.presenter;

import edu.wpi.cs.wpisuitetng.modules.taskmanager.model.BucketModel;
import edu.wpi.cs.wpisuitetng.network.RequestObserver;
import edu.wpi.cs.wpisuitetng.network.models.HttpMethod;
import edu.wpi.cs.wpisuitetng.network.models.IRequest;
import edu.wpi.cs.wpisuitetng.network.models.ResponseModel;

/**
 * 
 * 
 * @author TheFloorIsJava
 *
 */
public class BucketObserver implements RequestObserver{

	private BucketPresenter presenter;
	private HttpMethod method;
	
	public BucketObserver(BucketPresenter presenter, HttpMethod method){
		this.presenter = presenter;
		this.method = method;
	}
	
	/**
	 * Parse the TaskViews from the response received by the network
	 * 
	 */
	@Override
	public void responseSuccess(IRequest iReq) {
		// Store the response
		final ResponseModel response = iReq.getResponse();
		
		// Parse the message
		final BucketModel model = new BucketModel();
		// model = BucketModel.fromJSON(response.getBody()); fromJSON not implemented yet
		
		switch(method){
		case GET:
			presenter.responseGet(model);
			break;
		case POST:
			presenter.responsePost(model);
			break;
		case PUT:
			presenter.responsePut(model);
			break;
		case DELETE:
			presenter.responseDelete(model);
			break;
		}
	}
	
	/**
	 * @see edu.wpi.cs.wpisuitetng.network.RequestObserver#responseError(edu.wpi.cs.wpisuitetng.network.models.IRequest)
	 */
	@Override
	public void responseError(IRequest iReq) {
        System.err.println("The request to " + httpMethodToString(method) + " a message failed.");
	}

	/**
	 * 
	 */
	@Override
	public void fail(IRequest iReq, Exception exception) {
        System.err.println("The request to " + httpMethodToString(method) + " a message failed.");
	}

	private static String httpMethodToString(HttpMethod method_) {
        String methodString = "";
        switch (method_) {
        case GET: methodString = "fetch"; break;
        case POST: methodString = "update"; break;
        case PUT: methodString = "create"; break;
        case DELETE: methodString = "delete"; break;
        }
        return methodString;
    }
	
	/**
     * @return The presenter
     */
    public BucketPresenter getPresenter() {
        return this.presenter;
    }
}
