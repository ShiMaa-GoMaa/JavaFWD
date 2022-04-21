
package packageModel;

public class InvoiceLine {
    private String itemName;
    private double itemPrice;
    private int itemCount;
    private InvoiceHeader headerObject;

    public InvoiceLine(String itemName, double itemPrice, int itemCount, InvoiceHeader headerObject) {
        this.itemName = itemName;
        this.itemPrice = itemPrice;
        this.itemCount = itemCount;
        this.headerObject = headerObject;
    }
    
    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public double getItemPrice() {
        return itemPrice;
    }

    public void setItemPrice(double itemPrice) {
        this.itemPrice = itemPrice;
    }

    public int getItemCount() {
        return itemCount;
    }

    public void setItemCount(int itemCount) {
        this.itemCount = itemCount;
    }

    public InvoiceHeader getHeader() {
        return headerObject;
    }

    public void setHeader(InvoiceHeader headerObject) {
        this.headerObject = headerObject;
    }

    
    public double getLineTotal() {
        return itemCount * itemPrice;
    }
    
    public String getDataAsCSV() {
        return "" + getHeader().getinvoiceNumber() 
                + "," + getItemName() + "," + getItemPrice() + "," + getItemCount();
    }
}
