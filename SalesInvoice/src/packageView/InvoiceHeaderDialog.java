
package packageView;

import java.awt.GridLayout;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JTextField;


public class InvoiceHeaderDialog extends JDialog {
    private JTextField CustomerNameField;
    private JTextField invoiceDateField;
    private JLabel custNameLbl;
    private JLabel invDateLbl;
    private JButton saveButton;//saveButto=okBtn
    private JButton cancelButton;

    public InvoiceHeaderDialog(InvoiceFrame frameObject) {
        custNameLbl = new JLabel("Customer Name:");
        CustomerNameField = new JTextField(20);
        invDateLbl = new JLabel("Invoice Date:");
        invoiceDateField = new JTextField(20);
        saveButton = new JButton("save");
        cancelButton = new JButton("Cancel");
        
        saveButton.setActionCommand("createInvoiceSave");
        cancelButton.setActionCommand("createInvoiceCancel");
        
        saveButton.addActionListener(frameObject.getListener());
        cancelButton.addActionListener(frameObject.getListener());
        setLayout(new GridLayout(3, 2));
        
        add(invDateLbl);
        add(invoiceDateField);
        add(custNameLbl);
        add(CustomerNameField);
        add(saveButton);
        add(cancelButton);
        
        pack();
        
    }

    public JTextField getCustomerNameField()//getCustNameField()=getCustomerNameField
    {
        return CustomerNameField;
    }

    public JTextField getinvoiceDateField(){//getInvDateField()=getinvoiceDateField {
        return invoiceDateField;
    }
    
}
