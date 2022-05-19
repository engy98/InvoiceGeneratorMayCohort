
package com.view;

import java.awt.GridLayout;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JTextField;


public class InvoiceDialog extends JDialog {
    private JTextField nameField;
    private JTextField dateField;
    private JLabel name;
    private JLabel date;
    private JButton ok;
    private JButton cancel;

    public InvoiceDialog(InvoiceFrame frame) {
        name = new JLabel("Customer Name");
        nameField = new JTextField(15);
        date = new JLabel("Invoice Date");
        dateField = new JTextField(15);
        ok = new JButton("OK");
        cancel = new JButton("Cancel");
        
        ok.setActionCommand("createInvOK");
        cancel.setActionCommand("createInvCancel");
        
        ok.addActionListener(frame.getController());
        cancel.addActionListener(frame.getController());
        setLayout(new GridLayout(4, 3));
        
        add(date);
        add(dateField);
        add(name);
        add(nameField);
        add(ok);
        add(cancel);
        
        pack();
        
    }

    public JTextField getNameField() {
        return nameField;
    }

    public JTextField getDateField() {
        return dateField;
    }
    
}
