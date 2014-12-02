/*******************************************************************************
 * Copyright (c) 2014 -- WPI Suite
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 ******************************************************************************/

package edu.wpi.cs.wpisuitetng.modules.taskmanager.view;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import org.jdesktop.swingx.JXTextArea;

import edu.wpi.cs.wpisuitetng.modules.taskmanager.presenter.ActivityPresenter;
import net.miginfocom.swing.MigLayout;

/*
 * Part of Task editor containing comments and history
 */
public class CommentView extends JTabbedPane {

    private static final long serialVersionUID = 6161339014039149740L;

    private JPanel commentPanel = new JPanel();
    private JPanel historyPanel = new JPanel();
    private JPanel postedHistoryPanel = new JPanel();
    private JScrollPane historyScroll = new JScrollPane();
    private JScrollPane commentScroll = new JScrollPane();
    private JPanel postedCommentPanel = new JPanel();
    private ActivityView testActivity = new ActivityView();
    private ActivityView testActivity2 = new ActivityView();
    private JScrollPane editCommentScroll = new JScrollPane();
    private JXTextArea commentText = new JXTextArea("Write a comment...", Color.GRAY);
    private JButton postCommentButton = new JButton("Post");
    private JButton clearCommentButton = new JButton("Clear");
    
    private List<ActivityPresenter> activityPresenters = new ArrayList<ActivityPresenter>(); 

    /**
     * Constructor sets up Comments and History
     */
    public CommentView() {
        this.setTabLayoutPolicy(JTabbedPane.SCROLL_TAB_LAYOUT);
        
        this.addTab("Comments", Icons.COMMENTS, commentPanel, null);
        this.addTab("History", Icons.HISTORY, historyPanel, null);

        // Set layouts
        this.commentPanel.setLayout(new MigLayout("", "[grow]", "[grow][50px:n][min]"));
        this.historyPanel.setLayout(new MigLayout("", "[grow]", "[grow]"));
        this.postedCommentPanel.setLayout(new MigLayout("fill"));
        this.postedHistoryPanel.setLayout(new MigLayout("fill"));

        // Add components to commentPanel
        this.commentPanel.add(commentScroll, "cell 0 0,grow");
        this.commentPanel.add(editCommentScroll, "cell 0 1,grow");
        this.commentPanel.add(postCommentButton, "cell 0 2,alignx left,growy");
        this.commentPanel.add(clearCommentButton, "cell 0 2,alignx left,growy");
        this.commentText.setStartText("Comment here");
        this.commentText.setWrapStyleWord(true);
        this.commentText.setLineWrap(true);
        
        this.historyPanel.add(historyScroll, "cell 0 0,grow");        
        
        // Set scrollpane viewports
        this.commentScroll.setViewportView(postedCommentPanel);
        this.editCommentScroll.setViewportView(commentText);
        this.historyScroll.setViewportView(postedHistoryPanel);
        this.postCommentButton.setEnabled(false);
        this.postCommentButton.setIcon(Icons.COMMENT);
        this.clearCommentButton.setEnabled(false);
        this.clearCommentButton.setIcon(Icons.CLEAR);
        this.setupListeners();
    }
    
    /**
     * Enable or disable the post and reset buttons depending of if there's
     * something entered in the comment box.
     */
    private void validateFields() {
        if (commentText.getText().trim().isEmpty()) {
            this.postCommentButton.setEnabled(false);
            this.clearCommentButton.setEnabled(false);
        } else {
            this.postCommentButton.setEnabled(true);
            this.clearCommentButton.setEnabled(true);
        }
    }
    
    /**
     * Posts the new activity to the view
     * 
     * @param newComment
     *            the activity to be posted, either auto generated or manually
     *            added
     */
    public void postActivity(ActivityView newComment) {
                postedCommentPanel.add(newComment, "dock south");
        commentText.setText("");
                JScrollBar vertical = commentScroll.getVerticalScrollBar();
                JScrollBar horizontal = commentScroll.getHorizontalScrollBar();
                postedCommentPanel.revalidate();
                postedCommentPanel.repaint();
                vertical.setValue(vertical.getMinimum());
                horizontal.setValue(horizontal.getMinimum());
        validateFields();
            }
        });

    /**
     * Sets up the button listeners so that buttons can do things.
     */
    private void setupListeners() {
        // when clicked, the screen will clear if the original text is inside
        this.clearCommentButton.addActionListener((ActionEvent) -> {
            commentText.setText("");
            validateFields();
        });
        
        this.commentText.getDocument().addDocumentListener(new DocumentListener() {

            @Override
            public void removeUpdate(DocumentEvent e) {
                validateFields();
            }

            @Override
            public void insertUpdate(DocumentEvent e) {
                validateFields();
            }

            @Override
            public void changedUpdate(DocumentEvent arg0) {
                validateFields();
            }
        });
    }
    
    /**
     * Adds ActionListener to post comment button
     * @param listener triggers post
     */
    public void addOnPostListener(ActionListener listener) {
        this.postCommentButton.addActionListener(listener);
    }

    /**
     * @return the commentText
     */
    public JXTextArea getCommentText() {
        return this.commentText;
    }

    /**
     * Clears the posts in the view
     */
    public void clearPosts() {
        postedCommentPanel.removeAll();
    }

}
