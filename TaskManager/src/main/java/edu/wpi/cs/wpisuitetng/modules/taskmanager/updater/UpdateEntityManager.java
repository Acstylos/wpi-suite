package edu.wpi.cs.wpisuitetng.modules.taskmanager.updater;

/* For listing changes. */
import java.util.List;
import java.util.ArrayList;

/* For mapping connection to change lists. */
import java.util.Map;
import java.util.HashMap;

/* Session, associated with list of active changes. */
import edu.wpi.cs.wpisuitetng.Session;

/* EntityManager, network interface. */
import edu.wpi.cs.wpisuitetng.modules.EntityManager;

/* Unused database, needed for interface. */
import edu.wpi.cs.wpisuitetng.database.Data;

/* Network exceptions. */
import edu.wpi.cs.wpisuitetng.exceptions.BadRequestException;
import edu.wpi.cs.wpisuitetng.exceptions.ConflictException;
import edu.wpi.cs.wpisuitetng.exceptions.NotFoundException;
import edu.wpi.cs.wpisuitetng.exceptions.WPISuiteException;

/**
 * Change entity manager that records changes on the server and sends them to
 * the clients. Keeps track of per-session changes and backlogs. Uses a CRUD
 * interface using the ChangeModel as a core datatype.
 */
public class UpdateEntityManager implements EntityManager<ChangeModel> {

    /**
     * For each session, keep a list of unchecked changes. Each server has one
     * map. You can access this anywhere.
     */
    private static Map<Session, List<ChangeModel>> changes = null;

    /**
     * Start a manager. May initialize the static changes variable.
     *
     * @param data
     *            Unused database.
     */
    public UpdateEntityManager(Data data) {

        if (changes == null)
            changes = new HashMap<Session, List<ChangeModel>>();
    }

    /**
     * Register a change. Adds it to all tracked sessions.
     *
     * @param change
     *            Change to add.
     */
    public static void registerChange(ChangeModel change, Session s) {

        if (changes == null) {
            changes = new HashMap<>();
        }
        for (Session i : changes.keySet()) {
            if (!changes.containsKey(i)) {
                changes.put(s, new ArrayList<ChangeModel>());
            }
            if (i != s)
                changes.get(i).add(change);
        }
    }

    /**
     * Initializes a change queue, if not already running.
     *
     * @param s
     *            Session to track.
     * @param content
     *            Unused.
     */
    @Override
    public ChangeModel makeEntity(Session s, String content)
            throws BadRequestException, ConflictException, WPISuiteException {

        if (changes.get(s) == null) {
            changes.put(s, new ArrayList<ChangeModel>());
        }
        return new ChangeModel();
    }

    /**
     * Get the latest list of changes. Clears the queue.
     *
     * @param s
     *            Session to check.
     * @param id
     *            Unused.
     */
    @Override
    public ChangeModel[] getEntity(Session s, String id)
            throws NotFoundException, WPISuiteException {

        List<ChangeModel> newChanges = changes.get(s);

        ChangeModel[] changeList = new ChangeModel[newChanges.size()];
        newChanges.toArray(changeList);

        if (newChanges != null) {
            changes.get(s).clear();
        }

        return changeList;
    }

    /**
     * Get the latest list of changes. Clears the queue.
     *
     * @param s
     *            Session to check.
     */
    @Override
    public ChangeModel[] getAll(Session s) throws WPISuiteException {

        List<ChangeModel> newChanges = changes.get(s);

        ChangeModel[] changeList = (ChangeModel[]) newChanges.toArray();

        if (newChanges != null) {
            changes.get(s).clear();
        }

        return changeList;
    }

    /**
     * Clears the set of current changes for a session. Used for complete
     * updates.
     *
     * @param s
     *            Which session to dump.
     * @param content
     *            unused.
     */
    @Override
    public ChangeModel update(Session s, String content)
            throws WPISuiteException {

        changes.get(s).clear();
        return new ChangeModel();
    }

    /**
     * Unused.
     *
     * @param s
     *            Unused.
     * @param model
     *            Unused.
     */
    @Override
    public void save(Session s, ChangeModel model) throws WPISuiteException {
    }

    /**
     * Deletes an update queue (for example, on disconnect.)
     *
     * @param s
     *            Session to clear.
     * @param id
     *            Unused.
     * @return Unconditional success.
     */
    @Override
    public boolean deleteEntity(Session s, String id) throws WPISuiteException {

        changes.remove(s);
        return true;
    }

    /**
     * Unused.
     *
     * @param s
     *            Unused.
     * @param args
     *            unused.
     */
    @Override
    public String advancedGet(Session s, String[] args)
            throws WPISuiteException {

        return null;
    }

    /**
     * Deletes an update queue (for example, on disconnect.)
     *
     * @param s
     *            Session to clear.
     */
    @Override
    public void deleteAll(Session s) throws WPISuiteException {

        changes.remove(s);
    }

    /**
     * Unused.
     */
    @Override
    public int Count() throws WPISuiteException {

        return 0;
    }

    /**
     * Unused.
     *
     * @param s
     *            Unused.
     * @param args
     *            Unused.
     * @param content
     *            Unused.
     */
    @Override
    public String advancedPut(Session s, String[] args, String content)
            throws WPISuiteException {

        return "";
    }

    /**
     * Unused.
     *
     * @param s
     *            Unused.
     * @param string
     *            Unused.
     * @param content
     *            Unused.
     */
    @Override
    public String advancedPost(Session s, String string, String content)
            throws WPISuiteException {

        return "";
    }

}
