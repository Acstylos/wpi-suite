package edu.wpi.cs.wpisuitetng.modules.thefloorisjava.modules;

import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.List;

import javax.swing.*;

import edu.wpi.cs.wpisuitetng.janeway.modules.*;
import edu.wpi.cs.wpisuitetng.network.Network;
import edu.wpi.cs.wpisuitetng.network.models.HttpMethod;

public class MyModule implements IJanewayModule {

    private ArrayList<JanewayTabModel> tabs;
    private int counter;
    private JLabel resultLabel;
    
    private static String RESULT_LABEL_PREFIX = "Result = ";

    public MyModule() {
    	counter = 0;
    	resultLabel = new JLabel(RESULT_LABEL_PREFIX + counter);
    	
        JPanel mainPanel = new JPanel();
        mainPanel.add(resultLabel);
        
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout());
        
        /* Add a button to increment the counter */
        JButton increment = new JButton("+");
        increment.addActionListener((ActionEvent e) -> {
			counter++;
			resultLabel.setText(RESULT_LABEL_PREFIX + counter);
        });
        buttonPanel.add(increment);
        
        /* Add a button to decrement the counter */
        JButton decrement = new JButton("-");
        decrement.addActionListener((ActionEvent e) -> {
			counter--;
			resultLabel.setText(RESULT_LABEL_PREFIX + counter);
        });
        buttonPanel.add(decrement);

        tabs = new ArrayList<JanewayTabModel>();
        JanewayTabModel calculatorTab = new JanewayTabModel("Counter", new ImageIcon(), buttonPanel, mainPanel);
        tabs.add(calculatorTab);
        
        Network net = Network.getInstance();
        net.makeRequest("?", HttpMethod.GET);
    }

    /**
     * @see edu.wpi.cs.wpisuitetng.janeway.modules.IJanewayModule#getName()
     */
    @Override
    public String getName() {
        return "My Module";
    }

    /**
     * @see edu.wpi.cs.wpisuitetng.janeway.modules.IJanewayModule#getTabs()
     */
    @Override
    public List<JanewayTabModel> getTabs() {
        return tabs;
    }
}
