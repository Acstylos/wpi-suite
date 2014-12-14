package edu.wpi.cs.wpisuitetng.modules.taskmanager.model;

import static org.junit.Assert.*;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.junit.Test;

import edu.wpi.cs.wpisuitetng.modules.requirementmanager.view.requirements.ViewMode;
import edu.wpi.cs.wpisuitetng.modules.taskmanager.presenter.TaskPresenter;

public class TestTaskPresenter {

    @Test
    public final void testTaskPresenterIntBucketPresenterViewMode() {
        fail("Not yet implemented"); // TODO
    }

    @Test
    public final void testCompareTasks() {
        TaskPresenter test = new TaskPresenter(new TaskModel());
        DateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");

        TaskModel before = new TaskModel (1, "before", "bDes", 0 , new Date(2014,03,20), 1);
        TaskModel after = new TaskModel (1, "after", "aDes", 2 , new Date(2014,03,22), 2);
        assertEquals(test.compareTasks(before, after),
                "Title was changed from before to after"
                + "\n" +"Estimated Effort was changed from 0 to 2"
                        +"\n"+ "Due Date was changed from "
                +dateFormat.format(before.getDueDate()) + " to " +
                dateFormat.format(after.getDueDate()) +"\n"
                        + "Description was changed." + "\n" +
                        "Task was moved from New to Selected");
    }

    @Test
    public final void testUpdateBeforeModel() {
        TaskPresenter test = new TaskPresenter(new TaskModel());
        test.setModelNoView(new TaskModel(2, "beforeModel", "Descrip", 23, new Date(2014, 12, 10), 1));
        test.updateBeforeModel();
        assertTrue(test.getModel().equals(test.getBeforeModel()));
    }

    @Test
    public final void testAddActivity() {
        fail("Not yet implemented"); // TODO
    }

    @Test
    public final void testAddHistoryTaskModelTaskModel() {
        fail("Not yet implemented"); // TODO
    }

    @Test
    public final void testAddHistoryString() {
        fail("Not yet implemented"); // TODO
    }

    @Test
    public final void testSaveActivityId() {
        fail("Not yet implemented"); // TODO
    }

    @Test
    public final void testCreateInDatabase() {
        fail("Not yet implemented"); // TODO
    }

    @Test
    public final void testAddUsersToAllUserList() {
        fail("Not yet implemented"); // TODO
    }

    @Test
    public final void testAddUsersToView() {
        fail("Not yet implemented"); // TODO
    }

    @Test
    public final void testUpdateFromDatabase() {
        fail("Not yet implemented"); // TODO
    }

    @Test
    public final void testUpdateModel() {
        fail("Not yet implemented"); // TODO
    }

    @Test
    public final void testUpdateView() {
        fail("Not yet implemented"); // TODO
    }

    @Test
    public final void testUpdateCommentView() {
        fail("Not yet implemented"); // TODO
    }

    @Test
    public final void testRefreshCommentView() {
        fail("Not yet implemented"); // TODO
    }

    @Test
    public final void testSetTheViewViewMode() {
        fail("Not yet implemented"); // TODO
    }

    @Test
    public final void testGetView() {
        fail("Not yet implemented"); // TODO
    }

    @Test
    public final void testGetMiniView() {
        fail("Not yet implemented"); // TODO
    }

    @Test
    public final void testGetModel() {
        fail("Not yet implemented"); // TODO
    }

    @Test
    public final void testSetModel() {
        fail("Not yet implemented"); // TODO
    }

    @Test
    public final void testGetBucket() {
        fail("Not yet implemented"); // TODO
    }

    @Test
    public final void testSetBucket() {
        fail("Not yet implemented"); // TODO
    }

    @Test
    public final void testRemoveUserFromAssignedTo() {
        fail("Not yet implemented"); // TODO
    }

    @Test
    public final void testAddUserToAssignedTo() {
        fail("Not yet implemented"); // TODO
    }

    @Test
    public final void testGetAssignedUserList() {
        fail("Not yet implemented"); // TODO
    }

    @Test
    public final void testSetAllowCancelDialogEnabled() {
        fail("Not yet implemented"); // TODO
    }

    @Test
    public final void testIdToUsername() {
        fail("Not yet implemented"); // TODO
    }

    @Test
    public final void testSetIconForMinitaskView() {
        fail("Not yet implemented"); // TODO
    }

    @Test
    public final void testAddUsersToMiniTaskView() {
        fail("Not yet implemented"); // TODO
    }

    @Test
    public final void testValidateUpdateLabel() {
        fail("Not yet implemented"); // TODO
    }

}
