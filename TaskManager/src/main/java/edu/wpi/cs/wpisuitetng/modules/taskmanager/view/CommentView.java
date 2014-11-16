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

    public CommentView() {
        setLayout(new MigLayout("", "[438px]", "[276px]"));

        JTabbedPane commentPane = new JTabbedPane(JTabbedPane.TOP);
        add(commentPane, "cell 0 0,grow");

        JPanel commentPanel = new JPanel();
        commentPane.addTab("Comments", null, commentPanel, null);
        commentPanel.setLayout(new MigLayout("", "[grow]",
                "[60%,grow][grow][5%]"));

        //scroll bar for box containing all comments
        JScrollPane commentScroll = new JScrollPane();
        commentPanel.add(commentScroll, "cell 0 0,grow");

        //scroll bar for box containing comment currently being edited
        JScrollPane editCommentScroll = new JScrollPane();
        commentPanel.add(editCommentScroll, "cell 0 1,grow");

        //Write-in box for comments
        JWriteinText commentText = new JWriteinText();
        commentText.setStartText("Comment here");
        editCommentScroll.setViewportView(commentText);

        //Post button
        JButton btnPostComment = new JButton("Post");
        btnPostComment.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            }
        });
        commentPanel.add(btnPostComment,
                "flowx,cell 0 2,alignx left,aligny center");

        //Clear button (resets comment)
        JButton btnClearComment = new JButton("Clear");
        btnClearComment.addActionListener((ActionEvent e) -> {
            commentText.resetText();
        });

        // when clicked, the screen will clear if the original text is inside
        commentText.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                commentText.clicked();
            }
        });

        commentPanel.add(btnClearComment, "cell 0 2");
        //history panel
        JPanel historyPanel = new JPanel();
        commentPane.addTab("History", null, historyPanel, null);
        historyPanel.setLayout(new MigLayout("", "[grow]", "[grow]"));

        //scroll bar for history
        JScrollPane historyScroll = new JScrollPane();
        historyPanel.add(historyScroll, "cell 0 0,grow");

    }

}
