package edu.wpi.cs.wpisuitetng.modules.taskmanager.presenter;

import edu.wpi.cs.wpisuitetng.modules.requirementmanager.models.Requirement;
import edu.wpi.cs.wpisuitetng.network.RequestObserver;
import edu.wpi.cs.wpisuitetng.network.models.IRequest;

public class RequirementsObserver implements RequestObserver{
    TaskPresenter presenter;

    /**
     * Construct the observer
     * 
     * @param presenter
     *            The presenter that make the request
     */
    public RequirementsObserver(TaskPresenter presenter) {
        this.presenter = presenter;
    }

    @Override
    public void responseSuccess(IRequest iReq) {
        System.out.println(Requirement.fromJsonArray(iReq.getResponse().getBody()));
        Requirement[] coreRequirements = Requirement.fromJsonArray(iReq.getResponse().getBody());
        presenter.addRequirementsToAllReqArray(coreRequirements);
        presenter.addRequirementsToView();

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
        System.err.println("User request failed");
        exception.printStackTrace();
    }

}
