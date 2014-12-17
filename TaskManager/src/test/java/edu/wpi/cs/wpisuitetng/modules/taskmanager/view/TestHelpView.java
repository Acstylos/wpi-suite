package edu.wpi.cs.wpisuitetng.modules.taskmanager.view;

import static org.junit.Assert.*;

import org.junit.Test;

public class TestHelpView {

    @Test
    public void testCreateNonNullHelpView() {
        assertNotNull(new HelpView());
    }

}
