package edu.wpi.cs.wpisuitetng.modules.taskmanager.view;

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
    private PresetTextArea commentText = new PresetTextArea("Comment here");
    private JButton postCommentButton = new JButton("Post");
    private JButton clearCommentButton = new JButton("Clear");

    /**
     * Constructor sets up Comments and History
     */
    public CommentView() {
        this.setTabLayoutPolicy(JTabbedPane.SCROLL_TAB_LAYOUT);
        
        Icon commentsIcon = null, historyIcon = null, commentIcon = null, clearIcon = null;
        
        try {
            commentsIcon = new ImageIcon(ImageIO.read(getClass().getResourceAsStream("comments.png")));
            historyIcon = new ImageIcon(ImageIO.read(getClass().getResourceAsStream("history.png")));
            commentIcon = new ImageIcon(ImageIO.read(getClass().getResourceAsStream("comment.png")));
            clearIcon = new ImageIcon(ImageIO.read(getClass().getResourceAsStream("clear.png")));
        } catch (IOException e) {
            e.printStackTrace();
        }
        
        this.addTab("Comments", commentsIcon, commentPanel, null);
        this.addTab("History", historyIcon, historyPanel, null);

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
        this.postCommentButton.setIcon(commentIcon);
        this.clearCommentButton.setEnabled(false);
        this.clearCommentButton.setIcon(clearIcon);
        this.setupListeners();
    }
    
    private void validateButtons(boolean commentTyped){
        if(!commentTyped){
            this.postCommentButton.setEnabled(false);
            this.clearCommentButton.setEnabled(false);
        } else if(this.commentText.getText().equals("")){
            this.postCommentButton.setEnabled(false);
            this.clearCommentButton.setEnabled(false);
        } else {
            this.postCommentButton.setEnabled(true);
            this.clearCommentButton.setEnabled(true);
        }
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
                commentText.resetText();
                JScrollBar vertical = commentScroll.getVerticalScrollBar();
                JScrollBar horizontal = commentScroll.getHorizontalScrollBar();
                postedCommentPanel.revalidate();
                postedCommentPanel.repaint();
                vertical.setValue(vertical.getMinimum());
                horizontal.setValue(horizontal.getMinimum());
                validateButtons(commentText.isCommentTyped());
            }
        });

        // when clicked, the screen will clear if the original text is inside
        this.commentText.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                commentText.clicked();
            }
            
        });

        // Clear button (resets comment)
        this.clearCommentButton.addActionListener((ActionEvent e) -> {
            commentText.resetText();
            validateButtons(commentText.isCommentTyped());
        });
        
        this.commentText.getDocument().addDocumentListener(new DocumentListener() {

            @Override
            public void removeUpdate(DocumentEvent e) {
                validateButtons(commentText.isCommentTyped());
            }

            @Override
            public void insertUpdate(DocumentEvent e) {
                validateButtons(commentText.isCommentTyped());
            }

            @Override
            public void changedUpdate(DocumentEvent arg0) {
                validateButtons(commentText.isCommentTyped());
            }
        });
    }

}
