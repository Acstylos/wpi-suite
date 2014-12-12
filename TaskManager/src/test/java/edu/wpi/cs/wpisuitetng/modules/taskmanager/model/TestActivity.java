package edu.wpi.cs.wpisuitetng.modules.taskmanager.model;

import static org.junit.Assert.*;

import java.util.Date;

import org.junit.Test;

import edu.wpi.cs.wpisuitetng.modules.core.models.User;

public class TestActivity {

    @Test
    public void testCreateNonNullActivity() {
        assertNotNull(new ActivityModel());
        ActivityModel actMod = new ActivityModel(1, new User(null, null, null,
                1), "Activity", new Date(), true);
    }

    @Test
    public void testIsAutogen() {
        ActivityModel actMod = new ActivityModel();
        assertEquals(false, actMod.getIsAutogen());
        actMod.setIsAutogen(true);
        assertEquals(true, actMod.getIsAutogen());
    }

    @Test
    public void testDate() {
        ActivityModel actMod = new ActivityModel();
        assertEquals(new Date(), actMod.getDate());
        actMod.setDate(new Date(1, 1, 2014));
        assertEquals(new Date(1, 1, 2014), actMod.getDate());
    }

    @Test
    public void testID() {
        ActivityModel actMod = new ActivityModel();
        assertEquals(-1, actMod.getId());
        actMod.setId(1);
        assertEquals(1, actMod.getId());
    }

    @Test
    public void testUser() {
        ActivityModel actMod = new ActivityModel();
        assertEquals(-1, actMod.getUser());
        actMod.setUser(new User(null, null, null, 1));
        assertEquals(1, actMod.getUser());
    }

    @Test
    public void testActivity() {
        ActivityModel actMod = new ActivityModel();
        assertEquals("", actMod.getActivity());
        actMod.setActivity("Activity");
        assertEquals("Activity", actMod.getActivity());
    }

    @Test
    public void testCopyFrom() {
        ActivityModel actMod1 = new ActivityModel(1, new User(null, null, null,
                1), "Activity", new Date(), true);
        ActivityModel actMod2 = new ActivityModel();
        actMod2.copyFrom(actMod1);
        assertEquals(actMod2.getId(), actMod1.getId());
        assertEquals(actMod2.getActivity(), actMod1.getActivity());
        assertEquals(actMod2.getDate(), actMod1.getDate());
        assertEquals(actMod2.getIsAutogen(), actMod1.getIsAutogen());
        assertEquals(actMod2.getUser(), actMod1.getUser());
    }

    @Test
    public void testToJson() {
        ActivityModel actMod = new ActivityModel();
        actMod.setDate(new Date(12, 10, 2014));
        String json = "{\"id\":-1,\"userId\":-1,\"date\":\"May 7, 1918 12:00:00 AM\",\"activity\":\"\",\"isAutogen\":false,\"permissionMap\":{}}";
        assertEquals(json, actMod.toJson());
    }

    @Test
    public void testFromJson() {
        ActivityModel actMod1 = new ActivityModel();
        ActivityModel actMod2 = new ActivityModel();
        actMod1.setDate(new Date(12,10,2014));
        actMod2.setDate(new Date(12,10,2014));
        String json = "{\"id\":-1,\"userId\":-1,\"date\":\"May 7, 1918 12:00:00 AM\",\"activity\":\"\",\"isAutogen\":false,\"permissionMap\":{}}";
        assertTrue(actMod1.fromJson(json).isEqual(actMod2.fromJson(json)));
    }
}
