package edu.wpi.cs.wpisuitetng.modules.taskmanager.view;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.io.IOException;

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

import com.lowagie.text.Font;

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
    private JXTextArea commentText = new JXTextArea(" Add a comment…", Color.GRAY);
    private JButton postCommentButton = new JButton("Post");
    private JButton clearCommentButton = new JButton("Clear");

    /**
     * Constructor sets up Comments and History
     */
    public CommentView() {
        this.setTabLayoutPolicy(JTabbedPane.SCROLL_TAB_LAYOUT);

        this.addTab("Comments", Icons.COMMENTS, commentPanel, null);
        this.addTab("History", Icons.HISTORY, historyPanel, null);

        // Set layouts
        this.commentPanel.setLayout(new MigLayout("", "[grow]",
                "[grow][50px:n][min]"));
        this.historyPanel.setLayout(new MigLayout("", "[grow]", "[grow]"));
        this.postedCommentPanel.setLayout(new MigLayout("fill"));
        this.postedHistoryPanel.setLayout(new MigLayout("fill"));

        // Add components to commentPanel
        this.commentPanel.add(commentScroll, "cell 0 0,grow");
        this.commentPanel.add(editCommentScroll, "cell 0 1,grow");
        this.commentPanel.add(postCommentButton, "cell 0 2,alignx left,growy");
        this.commentPanel.add(clearCommentButton, "cell 0 2,alignx left,growy");
        this.commentText.setWrapStyleWord(true);
        this.commentText.setLineWrap(true);
        this.commentText.setPromptFontStyle(Font.ITALIC);

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
     * Enable or disable the options to clear and post a comment based on
     * weather or not there is a comment entered.
     */
    private void validateFields() {
        /*
         * To submit a comment, the user must enter at least one non-whitespace
         * character into the text area.
         */
        boolean isCommentEntered = !this.commentText.getText().trim().isEmpty();

        this.postCommentButton.setEnabled(isCommentEntered);
        this.clearCommentButton.setEnabled(isCommentEntered);
    }

    /**
     * Sets up the button listeners so that buttons can do things.
     */
    private void setupListeners() {
        // Post button
        this.postCommentButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                ActivityView newComment = new ActivityView(commentText.getText());
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

        // Clear button (resets comment)
        this.clearCommentButton.addActionListener((ActionEvent e) -> {
            commentText.setText("");
            validateFields();
        });
        
        /* Re-validate the input every time it gets edited */
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
            public void changedUpdate(DocumentEvent e) {
                validateFields();
            }
        });
    }
}
