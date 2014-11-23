package edu.wpi.cs.wpisuitetng.modules.taskmanager.presenter;

import edu.wpi.cs.wpisuitetng.modules.core.models.User;
import edu.wpi.cs.wpisuitetng.network.RequestObserver;
import edu.wpi.cs.wpisuitetng.network.models.IRequest;

public class UsersObserver implements RequestObserver{
    TaskPresenter presenter;

    /**
     * Construct the observer
     * 
     * @param presenter
     *            The presenter that make the request
     */
    public UsersObserver(TaskPresenter presenter) {
        this.presenter = presenter;
    }

    @Override
    public void responseSuccess(IRequest iReq) {
        System.out.println(User.fromJsonToArray(iReq.getResponse().getBody()));
        User[] coreUsers = User.fromJsonToArray(iReq.getResponse().getBody());
        presenter.addUsersToAllUserList(coreUsers);

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
