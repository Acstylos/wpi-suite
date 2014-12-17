/*******************************************************************************
 * Copyright (c) 2014 -- WPI Suite
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 ******************************************************************************/

package edu.wpi.cs.wpisuitetng.modules.taskmanager.view;

import static org.junit.Assert.*;

import org.junit.Test;

public class TestCommentView {

    @Test
    public void testCreateNotNullCommentView() {
        assertNotNull(new CommentView(ViewMode.EDITING));
    }

    @Test
    public void testPostActivity() {
        CommentView cView = new CommentView(ViewMode.EDITING);
        ActivityView aView = new ActivityView("Activity");
        cView.postActivity(aView);
        assertEquals(cView.getPostedCommentPanel().getComponent(0), aView);
    }

    @Test
    public void testPostHistory() {
        CommentView cView = new CommentView(ViewMode.EDITING);
        ActivityView aView = new ActivityView("Activity");
        cView.postHistory(aView);
        assertEquals(cView.getPostedHistoryPanel().getComponent(0), aView);
    }
    
    @Test
    public void testClearButton() {
        CommentView cView = new CommentView(ViewMode.EDITING);
        cView.setCommentText("test");
        assertEquals("test", cView.getCommentText().getText());
        cView.getClearButton().doClick();
        assertEquals("", cView.getCommentText().getText());
    }
    
    @Test
    public void testToggleTextField() {
        CommentView cView = new CommentView(ViewMode.EDITING);
        cView.toggleTextField(ViewMode.EDITING);
        cView.setCommentText("test");
        assertEquals("test", cView.getCommentText().getText());
        cView.getClearButton().doClick();
        assertEquals("", cView.getCommentText().getText());
        
        cView.setCommentText("test");
        cView.toggleTextField(ViewMode.ARCHIVING);
        cView.getClearButton().doClick();
        assertEquals("test", cView.getCommentText().getText());
        
        cView.toggleTextField(ViewMode.CREATING);
        assertEquals("test", cView.getCommentText().getText());
        
        cView.toggleTextField(null);
        assertEquals("test", cView.getCommentText().getText());
    }
    
    @Test
    public void testClearPosts() {
        CommentView cView = new CommentView(ViewMode.EDITING);
        ActivityView aView2 = new ActivityView("Comment");
        ActivityView aView = new ActivityView("History");
        cView.postHistory(aView);
        cView.postActivity(aView2);
        assertEquals(cView.getPostedHistoryPanel().getComponent(0), aView);
        assertEquals(cView.getPostedCommentPanel().getComponent(0), aView2);
        
        cView.clearPosts();
        cView.postHistory(aView2);
        cView.postActivity(aView);
        assertEquals(cView.getPostedHistoryPanel().getComponent(0), aView2);
        assertEquals(cView.getPostedCommentPanel().getComponent(0), aView);
    }
}
