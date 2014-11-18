package edu.wpi.cs.wpisuitetng.modules.taskmanager.view;

import javax.swing.JPanel;

import net.miginfocom.swing.MigLayout;

import javax.swing.JTabbedPane;
import javax.swing.JScrollPane;
import javax.swing.JButton;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

/*
 * Part of Task editor containing comments and history
 */
public class CommentView extends JPanel {

    private static final long serialVersionUID = 6161339014039149740L;

    /**
     * Constructor sets up TabbedPanes Comments and History
     */
    public CommentView() {
        setLayout(new MigLayout("", "[grow]", "[grow]"));

        JTabbedPane commentPane = new JTabbedPane(JTabbedPane.TOP);
        add(commentPane, "cell 0 0,grow");

        JPanel commentPanel = new JPanel();
        commentPane.addTab("Comments", null, commentPanel, null);
        commentPanel.setLayout(new MigLayout("", "[grow][grow][23px]", "[grow][41px][23px]"));

        //scroll bar for box containing all comments
        JScrollPane commentScroll = new JScrollPane();
        
        commentPanel.add(commentScroll, "cell 0 0 3 1,grow");
        
        JPanel postedCommentPanel = new JPanel();
        
        postedCommentPanel.setLayout(new MigLayout("", "[grow]", "[grow]"));
        ActivityView testActivity = new ActivityView();
       // postedCommentPanel.add(testActivity, "cell 0 0,alignx left,aligny top");
        testActivity.setMessage("Hi John!");
        commentScroll.setViewportView(postedCommentPanel);
        
        //scroll bar for box containing comment currently being edited
        JScrollPane editCommentScroll = new JScrollPane();
        commentPanel.add(editCommentScroll, "cell 0 1 3 1,grow");

        //Write-in box for comments
        PresetTextArea commentText = new PresetTextArea();
        commentText.setStartText("Comment here");
        editCommentScroll.setViewportView(commentText);

        //Post button
        JButton btnPostComment = new JButton("Post");
        btnPostComment.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String str=commentText.getText();
                if (!str.equals("")){
                    ActivityView newComment = new ActivityView();
                    newComment.setMessage(str);
                    postedCommentPanel.add(newComment, "dock north");
                    commentText.setText("");
                    postedCommentPanel.revalidate();
                    postedCommentPanel.repaint();
                    
                }
            }
        });
        commentPanel.add(btnPostComment, "flowx,cell 0 2,alignx left,aligny center");

        // when clicked, the screen will clear if the original text is inside
        commentText.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                commentText.clicked();
            }
        });
        
                //Clear button (resets comment)
                JButton btnClearComment = new JButton("Clear");
                btnClearComment.addActionListener((ActionEvent e) -> {
                    commentText.resetText();
                });
                
                        commentPanel.add(btnClearComment, "cell 0 2,alignx left,aligny top");
        
        
        //history panel
        JPanel historyPanel = new JPanel();
        commentPane.addTab("History", null, historyPanel, null);
        historyPanel.setLayout(new MigLayout("", "[grow]", "[grow]"));

        //scroll bar for history
        JScrollPane historyScroll = new JScrollPane();
        historyPanel.add(historyScroll, "cell 0 0,grow");
        
        JPanel postedHistoryPanel = new JPanel();
        postedHistoryPanel.setLayout(new MigLayout("", "[grow]", "[grow]"));
        ActivityView testActivity2 = new ActivityView();
        testActivity2.setMessage("Hello Alex!");
        postedHistoryPanel.add(testActivity2, "cell 0 0,alignx left,aligny top");
        
        historyScroll.setViewportView(postedHistoryPanel);
    }

}
