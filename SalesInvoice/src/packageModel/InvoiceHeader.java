
package packageModel;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class InvoiceHeader {
    private int invoiceNumber;//invNum=invoiceNumber
    private String customerName;
    private Date invoiceDate ;//invDate=invoiceDate
    private ArrayList<InvoiceLine> linesList;  

    public InvoiceHeader(int invoiceNumber, String customerName, Date invoiceDate) {
        this.invoiceNumber = invoiceNumber;
        this.customerName = customerName;
        this.invoiceDate = invoiceDate;
        //this.lines = new ArrayList<>();   // eager creation
    }

    public Date getInvoiceDate() //getinvoiceDate=getInvDate
    {
        return invoiceDate;
    }

    public void setInvoiceDate(Date invoiceDate)//setInvoiceDate=setInvDate
    {
        this.invoiceDate = invoiceDate;
    }

    public int getinvoiceNumber()//getInvNum()=getinvoiceNumber
    {
        return invoiceNumber;
    }

    public void setinvoiceNumber(int invoiceNumber)//setInvNum()/setinvoiceNumber
    {
        this.invoiceNumber = invoiceNumber;
    }

    public String getCustomerName() {
        
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

   
    public ArrayList<InvoiceLine> getLines() {
        if (linesList == null)
            linesList = new ArrayList<>();  // lazy creation
        return linesList;
    }

    public void setLines(ArrayList<InvoiceLine> linesList) {
        this.linesList = linesList;
    }

    public double getInvoiceTotal()//getInvTotal()=getInvoiceTotal()
    {
        double total = 0.0;
        for (InvoiceLine line : getLines()) {
            total += line.getLineTotal();
        }
        return total;
    }
    
    public void addInvoiceLine(InvoiceLine line)//addInvLine=addInvoiceLine
    {
        getLines().add(line);
    }
    
    public String getDataAsCSV() {
        DateFormat dateformatObject = new SimpleDateFormat("dd-MM-yyyy");
        return "" + getinvoiceNumber() + "," + dateformatObject.format(getInvoiceDate()) + "," + getCustomerName();
    }

   /* public String getInvoiceTotal() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }*/
    
}
