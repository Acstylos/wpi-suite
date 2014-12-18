package edu.wpi.cs.wpisuitetng.modules.taskmanager.updater;

/* Timers for updates. */
import java.util.Arrays;
import java.util.Timer;
import java.util.TimerTask;

/* List of objects. */
import java.util.Map;
import java.util.HashMap;

/* List of ChangeModels. */
import java.util.List;



/* Presenters to update. */
import edu.wpi.cs.wpisuitetng.modules.taskmanager.presenter.WorkflowPresenter;
import edu.wpi.cs.wpisuitetng.modules.taskmanager.presenter.BucketPresenter;
import edu.wpi.cs.wpisuitetng.modules.taskmanager.presenter.TaskPresenter;
import edu.wpi.cs.wpisuitetng.modules.taskmanager.presenter.ActivityPresenter;

/* Get buckets. */
import edu.wpi.cs.wpisuitetng.modules.taskmanager.view.MainView;

import edu.wpi.cs.wpisuitetng.modules.taskmanager.view.ViewMode;
/* Networking. */
import edu.wpi.cs.wpisuitetng.network.Network;
import edu.wpi.cs.wpisuitetng.network.Request;

/* Networking with janeway. */
import edu.wpi.cs.wpisuitetng.network.RequestObserver;
import edu.wpi.cs.wpisuitetng.network.models.IRequest;

/* Http method encoded CRUD enum. */
import edu.wpi.cs.wpisuitetng.network.models.HttpMethod;

/**
 * Polls the server and propagates updates to various objects. Initialized on
 * TaskManager initialization. Also a singleton, register all updatable objects
 * here.
 */
public class Updater extends TimerTask implements RequestObserver {

    /**
     * Timer used to schedule the updates.
     */
    Timer timer;

    /**
     * Map of Workflows, Buckets, Tasks, Comments, Users, ..., to update. Map
     * from ID to presenter.
     */
    Map<Integer, WorkflowPresenter> workflows;
    Map<Integer, BucketPresenter> buckets;
    Map<Integer, TaskPresenter> tasks;
    Map<Integer, ActivityPresenter> activities;

    /**
     * Instance of the updater singleton. Used in the singleton pattern.
     */
    private static Updater instance = null;

    /**
     * Private constructor. Only called internally, used in singleton pattern.
     * Initializes the maps and timer. Does not start updating.
     */
    private Updater() {
        timer = new Timer();
        workflows = new HashMap<>();
        buckets = new HashMap<>();
        tasks = new HashMap<>();
        activities = new HashMap<>();
        startPolling();
    }

    /**
     * Get instance of the updater for the client, or initialize one.
     * @return Updater instance.
     */
    public static Updater getInstance() {
        if (instance == null)
            instance = new Updater();
        return instance;
    }

    /**
     * Start polling events. Uses java.util.Task to run regularly.
     */
    public void startPolling() {
        Request request = Network.getInstance().makeRequest(
                "taskmanager/updates/0", HttpMethod.PUT);
        request.send();

        timer.scheduleAtFixedRate(this, 3000, 5000);
    }

    /**
     * Stop polling events. Cancels the Task.
     */
    public void stopPolling() {
        Request request = Network.getInstance().makeRequest(
                "taskmanager/updates/0", HttpMethod.DELETE);
        request.send();
        timer.cancel();
    }

    /**
     * Function to run every few seconds.
     */
    @Override
    public void run() {
        this.pollUpdates();
    }

    /**
     * Send a network request to the server. Asks for the latest network
     * changes.
     */
    public void pollUpdates() {
        Request request = Network.getInstance().makeRequest(
                "taskmanager/updates/0", HttpMethod.GET);
        request.addObserver(this);
        request.send();
    }

    /**
     * Handle a response.
     *
     * @param iReq
     *            Server's response to request.
     */
    @Override
    public void responseSuccess(IRequest iReq) {

        if (iReq.getHttpMethod() != HttpMethod.GET)
            return;
        String json = iReq.getResponse().getBody();
        ChangeModel[] changeList = ChangeModel.fromJsonArray(json);
        List<ChangeModel> changes = Arrays.asList(changeList);

        for (ChangeModel change : changes) {
            HttpMethod operation = change.getRequest();
            int id = change.getId();

            switch (change.getObjectType()) {
            case WORKFLOW:
                this.changeWorkflow(operation, id);
                break;
            case BUCKET:
                this.changeBucket(operation, id);
                break;
            case TASK:
                this.changeTask(operation, id);
                break;
            case ACTION:
                this.changeActivity(operation, id);
                break;
            }
        }
    }

    /**
     * Handle a server failure.
     *
     * @param iReq
     *            Failed request.
     */
    @Override
    public void responseError(IRequest iReq) {
        System.out.println("Server failure, could not service request.");
    }

    /**
     * Handle a networking exception.
     *
     * @param iReq
     *            Failed request.
     * @param exception
     *            Exception to handle.
     */
    @Override
    public void fail(IRequest iReq, Exception exception) {
        System.out
        .println("Network exception, could not send request. Reason: "
                + exception.toString());
    }

    /**
     * Register for updates from the server.
     *
     * @param workflow
     *            Workflow to check periodically.
     */
    public void registerWorkflow(WorkflowPresenter workflow) {
        this.workflows.put(workflow.getModel().getId(), workflow);
    }

    /**
     * Register for updates from the server.
     *
     * @param bucket
     *            Bucket to check periodically.
     */
    public void registerBucket(BucketPresenter bucket) {
        this.buckets.put(bucket.getModel().getId(), bucket);
    }

    /**
     * Register for updates from the server.
     *
     * @param task
     *            Task to check periodically.
     */
    public void registerTask(TaskPresenter task) {
        this.tasks.put(task.getModel().getId(), task);
    }

    /**
     * Register for updates from the server.
     *
     * @param activity
     *            Activity to check periodically.
     */
    public void registerActivity(ActivityPresenter activity) {
        this.activities.put(activity.getModel().getId(), activity);
    }

    /**
     * Applies a change to a workflow. We don't currently
     * change workflows, so this does nothing.
     *
     * @param operation
     *            CRUD operation to apply.
     * @param id
     *            ID of the workflow to change.
     */
    public void changeWorkflow(HttpMethod operation, int id) {
        /* When we need to manipulate workflows, add code here. */
        return;
    }

    /**
     * Applies a change to a bucket. We don't currently
     * change buckets, so this does nothing.
     *
     * @param operation
     *            CRUD operation to apply.
     * @param id
     *            ID of the bucket to change.
     */
    public void changeBucket(HttpMethod operation, int id) {
        switch (operation) {
        case GET:
            break; /* We don't care about reads. */
        case POST:
            System.out.println("POST'd bucket. Updating. Bucket ID: " + id);
            this.buckets.get(id).load();
            break;
        case PUT:
            break; /* We don't create yet. */
        case DELETE:
            break; /* We don't delete yet. */
        }
        return;
    }

    /**
     * Applies a change to a task.
     *
     * @param operation
     *            CRUD operation to apply.
     * @param id
     *            ID of the task to change.
     */
    public void changeTask(HttpMethod operation, int id) {
        switch (operation) {
        case PUT:
            System.out.println("PUT'd task. Creating. Bucket ID: " + id);
            this.buckets.get(id).load();
            break;
        case GET:
            break; /* We don't care about reads, only writes. */
        case POST:
            System.out.println("POST'd task. Updating.");
            if (this.tasks.get(id) != null)
                this.tasks.get(id).updateFromDatabase();
            else
                System.out.println("Attempted to update nonexisting task. ID: " + id);
            break;
        case DELETE:
            System.out.println("DELETE'd task. Updating.");
            TaskPresenter p = this.tasks.get(id);
            p.getBucket().removeTaskLocal(id);
            break;
        }
        return;
    }

    /**
     * Applies a change to a Activity.
     *
     * @param operation
     *            CRUD operation to apply.
     * @param id
     *            ID of the activity to change.
     */
    public void changeActivity(HttpMethod operation, int id) {
        switch (operation) {
        case PUT:
            System.out.println("PUT'd activity. Creating.");
            TaskPresenter task = tasks.get(id);
            if (task != null)
                task.updateFromDatabase();
            break;
        case DELETE: /* We don't delete activities. */
        case GET:
            break; /* We don't care about reading, only editing. */
        case POST:
            break; /* You cannot update comments. */
        }
    }

}
