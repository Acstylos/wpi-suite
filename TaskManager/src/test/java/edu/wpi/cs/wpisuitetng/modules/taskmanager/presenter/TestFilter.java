package edu.wpi.cs.wpisuitetng.modules.taskmanager.presenter;

import static org.junit.Assert.*;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.junit.Test;

import edu.wpi.cs.wpisuitetng.modules.core.models.User;
import edu.wpi.cs.wpisuitetng.modules.taskmanager.model.TaskModel;

public class TestFilter {

    @Test
    public final void testMatchesUser() {
        User bob = new User("bob", "BOB", "123", 1);
        User rob = new User("rob", "ROB", "1234", 2);
        User[] allUsers = { bob, rob };

        List<Integer> assignedUsers = new ArrayList<Integer>();
        assignedUsers.add(bob.getIdNum());
        assignedUsers.add(rob.getIdNum());

        Filter test = new Filter();
        TaskModel task1 = new TaskModel(1, "sample", "short description", 50,
                new Date(2014, 12, 18), 1);
        task1.setAssignedTo(assignedUsers);

        TaskModel task2 = new TaskModel(1, "brush teeth",
                "dont forget to floss", 1, new Date(2014, 12, 01), 1);
        task2.setAssignedTo(new ArrayList<Integer>());

        TaskPresenter pres1 = new TaskPresenter(task1);
        pres1.addUsersToAllUserList(allUsers);
        pres1.setAssignedUserList();
        try {
            pres1.addUserToAssignedTo(rob);
            pres1.addUserToAssignedTo(bob);
        } catch (NullPointerException e) {
            // this is because there is no View in Task Presenter.
            // We do not need to call validate Fields in Task View.
            TaskPresenter pres2 = new TaskPresenter(task2);
            pres2.addUsersToAllUserList(allUsers);
            pres2.setAssignedUserList();
            test.setFilterUser("bob");
            assertFalse(test.matches(pres1));
            assertFalse(test.matches(pres2));
            test.setFilterUser("BOB");
            assertFalse(test.matches(pres1));
            assertFalse(test.matches(pres2));
        }
    }

    @Test
    public final void testMatchesText() {
        Filter test = new Filter();
        TaskModel task1 = new TaskModel(1, "sample", "short description", 50,
                new Date(2014, 12, 18), 1);
        TaskModel task2 = new TaskModel(1, "brush teeth",
                "dont forget to floss", 1, new Date(2014, 12, 01), 1);
        test.setFilterText("forget");
        assertFalse(test.matches(new TaskPresenter(task1)));
        assertTrue(test.matches(new TaskPresenter(task2)));
        test.setFilterText("teeth");
        assertFalse(test.matches(new TaskPresenter(task1)));
        assertTrue(test.matches(new TaskPresenter(task2)));
        test.setFilterText("sample");
        assertTrue(test.matches(new TaskPresenter(task1)));
        assertFalse(test.matches(new TaskPresenter(task2)));

    }

    @Test
    public final void testMatchesColor() {
        Filter test = new Filter();
        TaskModel task1 = new TaskModel(1, "sample", "short description", 50,
                new Date(2014, 12, 18), 1);
        task1.setLabelColor(new Color(0, 0, 0));
        TaskModel task2 = new TaskModel(1, "brush teeth",
                "dont forget to floss", 1, new Date(2014, 12, 01), 1);
        task2.setLabelColor(new Color(223, 220, 0));
        test.setFilterColor(new Color(0, 0, 0));
        assertTrue(test.matches(new TaskPresenter(task1)));
        assertFalse(test.matches(new TaskPresenter(task2)));
    }

    @Test
    public final void testMatchesStartDate() {
        Filter test = new Filter();
        TaskModel task1 = new TaskModel(1, "sample", "short description", 50,
                new Date(2014, 12, 18), 1);
        TaskModel task2 = new TaskModel(1, "brush teeth",
                "dont forget to floss", 1, new Date(2014, 12, 01), 1);
        test.setStartDate(new Date(2014, 12, 11));
        assertTrue(test.matches(new TaskPresenter(task1)));
        assertFalse(test.matches(new TaskPresenter(task2)));
    }

    @Test
    public final void testMatchesEndDate() {
        Filter test = new Filter();
        TaskModel task1 = new TaskModel(1, "sample", "short description", 50,
                new Date(2014, 12, 18), 1);
        TaskModel task2 = new TaskModel(1, "brush teeth",
                "dont forget to floss", 1, new Date(2014, 12, 01), 1);
        test.setEndDate(new Date(2014, 12, 11));
        assertFalse(test.matches(new TaskPresenter(task1)));
        assertTrue(test.matches(new TaskPresenter(task2)));
    }

}
