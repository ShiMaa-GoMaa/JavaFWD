
package packageModel;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.List;
import javax.swing.table.AbstractTableModel;

public class InvoiceHeaderTableModel extends AbstractTableModel {

    private List<InvoiceHeader> invoicesListHeader;
    private DateFormat DateFormatObject = new SimpleDateFormat("dd-MM-yyyy");
    
    public InvoiceHeaderTableModel(List<InvoiceHeader> invoicesListHeader) {
        this.invoicesListHeader = invoicesListHeader;
    }

    public List<InvoiceHeader> getinvoicesListHeader() {
        return invoicesListHeader;
    }
    
    
    @Override
    public int getRowCount() {
        return invoicesListHeader.size();
    }

    @Override
    public int getColumnCount() {
        return 4;
    }

    @Override
    public String getColumnName(int columnIndex) {
        switch (columnIndex) {
            case 0:
                return "Invoice Number";
            case 1:
                return "Customer Name";
            case 2:
                return "Invoice Date";
            case 3:
                return "Invoice Total";
            default:
                return "";
        }
    }

    @Override
    public Class<?> getColumnClass(int columnIndex) {
        switch (columnIndex) {
            case 0:
                return Integer.class;
            case 1:
                return String.class;
            case 2:
                return String.class;
            case 3:
                return Double.class;
            default:
                return Object.class;
        }
    }

    @Override
    public boolean isCellEditable(int rowIndex, int columnIndex) {
        return false;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        InvoiceHeader InvoiceHeaderRow = invoicesListHeader.get(rowIndex);
        
        switch (columnIndex) {
            case 0:
                return InvoiceHeaderRow.getinvoiceNumber();
            case 1:
                return InvoiceHeaderRow.getCustomerName();
            case 2:
                return DateFormatObject.format(InvoiceHeaderRow.getInvoiceDate());
            case 3:
                return InvoiceHeaderRow.getInvoiceTotal();
            default:
                return "";
        }
        
    }
    
}
