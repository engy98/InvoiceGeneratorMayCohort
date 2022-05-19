
package com.view;

import java.awt.GridLayout;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JTextField;


public class ItemDialog extends JDialog{
    private JTextField nameField;
    private JTextField countField;
    private JTextField priceField;
    private JLabel name;
    private JLabel count;
    private JLabel price;
    private JButton ok;
    private JButton cancel;
    
    public ItemDialog(InvoiceFrame frame) {
        nameField = new JTextField(15);
        name = new JLabel("Item Name");
        
        countField = new JTextField(15);
        count = new JLabel("Item Count");
        
        priceField = new JTextField(15);
        price = new JLabel("Item Price");
        
        ok = new JButton("OK");
        cancel = new JButton("Cancel");
        
        ok.setActionCommand("createItemOK");
        cancel.setActionCommand("createItemCancel");
        
        ok.addActionListener(frame.getController());
        cancel.addActionListener(frame.getController());
        setLayout(new GridLayout(5, 2));
        
        add(name);
        add(nameField);
        add(count);
        add(countField);
        add(price);
        add(priceField);
        add(ok);
        add(cancel);
        
        pack();
    }

    public JTextField getNameField() {
        return nameField;
    }

    public JTextField getCountField() {
        return countField;
    }

    public JTextField getPriceField() {
        return priceField;
    }
}
