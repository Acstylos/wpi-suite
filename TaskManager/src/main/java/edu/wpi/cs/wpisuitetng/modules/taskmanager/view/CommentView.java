package edu.wpi.cs.wpisuitetng.modules.taskmanager.view;

import javax.swing.JPanel;

import net.miginfocom.swing.MigLayout;

import javax.swing.JTabbedPane;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JButton;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.Font;
import java.awt.FlowLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JFrame;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;

import java.awt.Color;

public class CommentView extends JPanel {

	/**
	 * Create the panel.
	 */
	boolean commentTyped = false;
	public CommentView() {
		setLayout(new MigLayout("", "[438px]", "[276px]"));
		
		JTabbedPane commentPane = new JTabbedPane(JTabbedPane.TOP);
		add(commentPane, "cell 0 0,grow");
		
		JPanel commentPanel = new JPanel();
		commentPane.addTab("Comments", null, commentPanel, null);
		commentPanel.setLayout(new MigLayout("", "[grow]", "[60%,grow][grow][5%]"));
		
		JScrollPane scrollPane = new JScrollPane();
		commentPanel.add(scrollPane, "cell 0 0,grow");
		
		JScrollPane scrollPane_1 = new JScrollPane();
		commentPanel.add(scrollPane_1, "cell 0 1,grow");
		
		JScrollPane scrollPane_2 = new JScrollPane();
		scrollPane_1.setViewportView(scrollPane_2);
		
		JWriteinText txtrCommentHere = new JWriteinText();
		txtrCommentHere.SetStartText("Comment here");
		scrollPane_2.setViewportView(txtrCommentHere);
		
		//txtrCommentHere.setInputPrompt("Yolo");
		
		
		JButton btnPostComment = new JButton("Post");
		btnPostComment.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		commentPanel.add(btnPostComment, "flowx,cell 0 2,alignx left,aligny center");
		
		JButton btnClearComment = new JButton("Clear");
		btnClearComment.addActionListener((ActionEvent e) -> {
			txtrCommentHere.ResetText();
		});
		txtrCommentHere.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				txtrCommentHere.Clicked();
			}
		});

		commentPanel.add(btnClearComment, "cell 0 2");
		
		JPanel historyPanel = new JPanel();
		commentPane.addTab("History", null, historyPanel, null);
		historyPanel.setLayout(new MigLayout("", "[grow]", "[grow]"));
		
		JScrollPane scrollPane_3 = new JScrollPane();
		historyPanel.add(scrollPane_3, "cell 0 0,grow");

	}

}
