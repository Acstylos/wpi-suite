/*******************************************************************************
 * Copyright (c) 2014 -- WPI Suite
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 ******************************************************************************/
package edu.wpi.cs.wpisuitetng.modules.taskmanager.view;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import net.miginfocom.swing.MigLayout;

/**
 * General, all-purpose dialog that can be used. The buttons can be configured
 * to do what ever the caller wants. The text in the label can be set however
 * the caller wants.
 */
public class VerifyActionDialog extends JDialog {

    private static final long serialVersionUID = 2701751430157769197L;
    private final JPanel contentPanel = new JPanel();
    private JPanel buttonPane = new JPanel();
    private JLabel commentLabel = new JLabel(
            "Are you sure you want to do this?");
    private JButton okButton = new JButton("Yes");
    private JButton cancelButton = new JButton("Cancel");

    /**
     * Create the dialog.
     */
    public VerifyActionDialog() {
        setBounds(100, 100, 450, 150);
        setResizable(false);
        getContentPane().setLayout(new BorderLayout());
        contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
        getContentPane().add(contentPanel, BorderLayout.CENTER);
        contentPanel.setLayout(new MigLayout("", "[grow]", "[grow]"));

        commentLabel.setIcon(Icons.ERROR_LARGE);
        contentPanel.add(commentLabel, "cell 0 0,alignx center,aligny center");

        buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
        getContentPane().add(buttonPane, BorderLayout.SOUTH);

        okButton.setIcon(Icons.OK);
        okButton.setActionCommand("Yes");
        buttonPane.add(okButton);
        getRootPane().setDefaultButton(okButton);

        cancelButton.setIcon(Icons.CANCEL);
        cancelButton.setActionCommand("Cancel");
        buttonPane.add(cancelButton);

    }

    /**
     * @param listener
     *            The listener to add for this button. Set by whatever calls
     *            this dialog.
     */
    public void addConfirmButtonListener(ActionListener listener) {
        okButton.addActionListener(listener);
    }

    /**
     * @param listener
     *            The listener to add for this button. Set by whatever calls
     *            this dialog.
     */
    public void addCancelButtonListener(ActionListener listener) {
        cancelButton.addActionListener(listener);
    }

    /**
     * @param text
     *            The text to set the commentLabel to have.
     */
    public void setCommentLabelText(String text) {
        commentLabel.setText(text);
    }

}
