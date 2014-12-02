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
 * General, all-purpose dialog that can be used.
 * The buttons can be configured to do what ever the caller wants.
 * The text in the label can be set however the caller wants.
 */
public class VerifyActionDialog extends JDialog {

    private static final long serialVersionUID = 2701751430157769197L;
    private final JPanel contentPanel = new JPanel();
    private JPanel buttonPane = new JPanel();
    private JLabel commentLabel = new JLabel("Are you sure you want to do this?");
    private JButton okButton = new JButton("Yes");
    private JButton cancelButton = new JButton("Cancel");

    /**
     * Launch the application.
     */
    public static void main(String[] args) {
        try {
            VerifyActionDialog dialog = new VerifyActionDialog();
            dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
            dialog.setVisible(true);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Create the dialog.
     */
    public VerifyActionDialog() {
        setBounds(100, 100, 450, 300);
        getContentPane().setLayout(new BorderLayout());
        contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
        getContentPane().add(contentPanel, BorderLayout.CENTER);
        contentPanel.setLayout(new MigLayout("", "[grow]", "[grow]"));
        {
            contentPanel.add(commentLabel, "cell 0 0,alignx center,aligny center");
        }
        {
            buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
            getContentPane().add(buttonPane, BorderLayout.SOUTH);
            {
                okButton.setActionCommand("Yes");
                buttonPane.add(okButton);
                getRootPane().setDefaultButton(okButton);
            }
            {
                cancelButton.setActionCommand("Cancel");
                buttonPane.add(cancelButton);
            }
        }
    }
    
    /**
     * @param listener The listener to add for this button. Set by whatever calls this dialog.
     */
    public void addConfirmButtonListener(ActionListener listener) {
        okButton.addActionListener(listener);
    }
    
    /**
     * @param listener The listener to add for this button. Set by whatever calls this dialog.
     */
    public void addCancelButtonListener(ActionListener listener) {
        cancelButton.addActionListener(listener);
    }
    
    /**
     * @param text The text to set the commentLabel to have.
     */
    public void setCommentLabelText(String text) {
        commentLabel.setText(text);
    }

}
