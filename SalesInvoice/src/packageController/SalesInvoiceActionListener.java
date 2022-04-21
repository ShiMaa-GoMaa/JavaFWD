
package packageController;

import packageModel.InvoiceHeader;
import packageModel.InvoiceHeaderTableModel;
import packageModel.InvoiceLine;
import packageModel.InvoiceLinesTableModel;
import packageView.InvoiceFrame;
import packageView.InvoiceHeaderDialog;
import packageView.InvoiceLineDialog;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

public class SalesInvoiceActionListener implements ActionListener, ListSelectionListener  {
    private InvoiceFrame frameObject;
    private DateFormat dateformateOdject = new SimpleDateFormat("dd-MM-yyyy");
    
    public SalesInvoiceActionListener(InvoiceFrame frameObject) {
        this.frameObject = frameObject;
    }
    
    
    @Override
    public void actionPerformed(ActionEvent e) {

        switch (e.getActionCommand()) {
            case "CreateNewInvoice":
                displayNewInvoiceDialog();
                break;
            case "DeleteInvoice":
                deleteInvoice();
                break;
            case "Save"://CreateNewLine=Save,SaveBtn
                displayNewLineDialog();
                break;
            case "DeleteLine"://cancelBtn
                deleteLine();
                break;
            case "LoadFile":
                loadFile();
                break;
            case "SaveFile":
                saveData();
                break;
            case "createInvoiceCancel":
                createInvoiceCancel();
                break;
            case "createInvoiceOK":
                createInvoiceSave();
                break;
            case "createLineCancel":
                createLineCancel();
                break;
            case "createLineSave":
                createLineSave();
                break;
        }
    }

    private void loadFile() {//done
        JOptionPane.showMessageDialog(frameObject, "Please, select Header file!", "Attension", JOptionPane.WARNING_MESSAGE);
        JFileChooser openFile = new JFileChooser();
        int ChooseFileresult = openFile.showOpenDialog(frameObject);
        if (ChooseFileresult == JFileChooser.APPROVE_OPTION) {
            File headerFile = openFile.getSelectedFile();
            try {
                FileReader headerFr = new FileReader(headerFile);
                BufferedReader headerBufferedObject = new BufferedReader(headerFr);
                String headerLine = null;

                while ((headerLine = headerBufferedObject.readLine()) != null) {
                    String[] headerParts = headerLine.split(",");
                    String InvoiceNumStr = headerParts[0];
                    String invDateStr = headerParts[1];
                    String custName = headerParts[2];

                    int invoiceNumber = Integer.parseInt(InvoiceNumStr);
                    Date invoiceDate = dateformateOdject.parse(invDateStr);

                    InvoiceHeader InvoiceHeaderObject = new InvoiceHeader(invoiceNumber, custName, invoiceDate);
                    frameObject.getInvoicesList().add(InvoiceHeaderObject);
                }

                JOptionPane.showMessageDialog(frameObject, "Please, select lines file!", "Attension", JOptionPane.WARNING_MESSAGE);
                ChooseFileresult = openFile.showOpenDialog(frameObject);
                if (ChooseFileresult == JFileChooser.APPROVE_OPTION) {
                    File Filelines = openFile.getSelectedFile();
                    BufferedReader linesBufferObject = new BufferedReader(new FileReader(Filelines));
                    String linesLine = null;
                    while ((linesLine = linesBufferObject.readLine()) != null) {
                        String[] lineSplit = linesLine.split(",");
                        String invoiceNumStr = lineSplit[0];
                        String itemNamestr = lineSplit[1];
                        String itemPriceStr = lineSplit[2];
                        String itemCountStr = lineSplit[3];

                        int invoiceNumber = Integer.parseInt(invoiceNumStr);
                        double itemPrice = Double.parseDouble(itemPriceStr);
                        int itemCount = Integer.parseInt(itemCountStr);
                        InvoiceHeader headerObject = findInvoiceByNum(invoiceNumber);
                        InvoiceLine InvoiceLineObject = new InvoiceLine(itemNamestr, itemPrice, itemCount, headerObject);
                        headerObject.getLines().add(InvoiceLineObject);
                    }
                    frameObject.setInvoiceHeaderTableModel(new InvoiceHeaderTableModel(frameObject.getInvoicesList()));
                    frameObject.getInvoicesTable().setModel(frameObject.getInvoiceHeaderTableModel());
                    frameObject.getInvoicesTable().validate();
                }
                System.out.println("Check");
            } catch (ParseException ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(frameObject, "Date Format Error\n" + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            } catch (NumberFormatException ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(frameObject, "Number Format Error\n" + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            } catch (FileNotFoundException ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(frameObject, "File Error\n" + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            } catch (IOException ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(frameObject, "Read Error\n" + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
        displayInvoices();
    }

    private void saveData() {
        String headers = "";
        String lines = "";
        for (InvoiceHeader headerObject : frameObject.getInvoicesList()) {
            headers += headerObject.getDataAsCSV();
            headers += "\n";
            for (InvoiceLine line : headerObject.getLines()) {
                lines += line.getDataAsCSV();
                lines += "\n";
            }
        }
        JOptionPane.showMessageDialog(frameObject, "Please, select file to save header data!", "Attension", JOptionPane.WARNING_MESSAGE);
        JFileChooser fileChooser = new JFileChooser();
        int chooseResult = fileChooser.showSaveDialog(frameObject);
        if (chooseResult == JFileChooser.APPROVE_OPTION) {
            File headerFile = fileChooser.getSelectedFile();
            try {
                FileWriter headerWriter = new FileWriter(headerFile);
                headerWriter.write(headers);
                headerWriter.flush();
                headerWriter.close();

                JOptionPane.showMessageDialog(frameObject, "Please, select file to save lines data!", "Attension", JOptionPane.WARNING_MESSAGE);
                chooseResult = fileChooser.showSaveDialog(frameObject);
                if (chooseResult == JFileChooser.APPROVE_OPTION) {
                    File linesFile = fileChooser.getSelectedFile();
                    FileWriter linesWriter = new FileWriter(linesFile);
                    linesWriter.write(lines);
                    linesWriter.flush();
                    linesWriter.close();
                }
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(frameObject, "Error: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
        JOptionPane.showMessageDialog(frameObject, "Data saved successfully", "Success", JOptionPane.INFORMATION_MESSAGE);

    }

    private InvoiceHeader findInvoiceByNum(int invoiceNumber) {
        InvoiceHeader headerObject = null;
        for (InvoiceHeader InvoiceCount : frameObject.getInvoicesList()) {
            if (invoiceNumber == InvoiceCount.getinvoiceNumber()) {
                headerObject = InvoiceCount;
                break;
            }
        }
        return headerObject;
    }

    @Override
    public void valueChanged(ListSelectionEvent e) {
        System.out.println("Invoice Selected!");
        invoicesTableRowSelected();
    }

    private void invoicesTableRowSelected() {
        int selectedRowIndex = frameObject.getInvoicesTable().getSelectedRow();
        if (selectedRowIndex >= 0) {
            InvoiceHeader InvoiceRow = frameObject.getInvoiceHeaderTableModel().getinvoicesListHeader().get(selectedRowIndex);
            frameObject.getCustNameTF().setText(InvoiceRow.getCustomerName());
            frameObject.getInvDateTF().setText(dateformateOdject.format(InvoiceRow.getInvoiceDate()));
            frameObject.getInvNumLbl().setText("" + InvoiceRow.getinvoiceNumber());
            frameObject.getInvTotalLbl().setText("" + InvoiceRow.getInvoiceTotal());
            ArrayList<InvoiceLine> lines = InvoiceRow.getLines();
            frameObject.setInvoiceLinesTableModel(new InvoiceLinesTableModel(lines));
            frameObject.getInvLinesTable().setModel(frameObject.getInvoiceLinesTableModel());
            frameObject.getInvoiceLinesTableModel().fireTableDataChanged();
        }
    }

    private void displayNewInvoiceDialog() {
        frameObject.setHeaderDialog(new InvoiceHeaderDialog(frameObject));
        frameObject.getHeaderDialog().setVisible(true);
    }

    private void displayNewLineDialog() {
        frameObject.setLineDialog(new InvoiceLineDialog(frameObject));
        frameObject.getLineDialog().setVisible(true);
    }

    private void createInvoiceCancel() {
        frameObject.getHeaderDialog().setVisible(false);
        frameObject.getHeaderDialog().dispose();
        frameObject.setHeaderDialog(null);
    }

    private void createInvoiceSave() {//Done
        String CustomerName = frameObject.getHeaderDialog().getCustomerNameField().getText();
        String invoiceDateStr = frameObject.getHeaderDialog().getinvoiceDateField().getText();
        frameObject.getHeaderDialog().setVisible(false);
        frameObject.getHeaderDialog().dispose();
        frameObject.setHeaderDialog(null);
        try {
            Date invoiceDateObject = dateformateOdject.parse(invoiceDateStr);
            int invNum = getNextInvoiceNum();
            InvoiceHeader invoiceHeader = new InvoiceHeader(invNum, CustomerName, invoiceDateObject);
            frameObject.getInvoicesList().add(invoiceHeader);
            frameObject.getInvoiceHeaderTableModel().fireTableDataChanged();
        } catch (ParseException ex) {
            JOptionPane.showMessageDialog(frameObject, "Wrong date format", "Error", JOptionPane.ERROR_MESSAGE);
            ex.printStackTrace();
        }
        displayInvoices();
    }

    private int getNextInvoiceNum() {
        int max = 0;
        for (InvoiceHeader header : frameObject.getInvoicesList()) {
            if (header.getinvoiceNumber() > max) {
                max = header.getinvoiceNumber();
            }
        }
        return max + 1;
    }

    private void createLineCancel() {
        frameObject.getLineDialog().setVisible(false);
        frameObject.getLineDialog().dispose();
        frameObject.setLineDialog(null);
    }

    private void createLineSave() {
        String itemName = frameObject.getLineDialog().getItemNameField().getText();
        String itemCountStr = frameObject.getLineDialog().getItemCountField().getText();
        String itemPriceStr = frameObject.getLineDialog().getItemPriceField().getText();
        frameObject.getLineDialog().setVisible(false);
        frameObject.getLineDialog().dispose();
        frameObject.setLineDialog(null);
        int itemCount = Integer.parseInt(itemCountStr);
        double itemPrice = Double.parseDouble(itemPriceStr);
        int headerIndex = frameObject.getInvoicesTable().getSelectedRow();
        InvoiceHeader invoice = frameObject.getInvoiceHeaderTableModel().getinvoicesListHeader().get(headerIndex);

        InvoiceLine invoiceLine = new InvoiceLine(itemName, itemPrice, itemCount, invoice);
        invoice.addInvoiceLine(invoiceLine);
        frameObject.getInvoiceLinesTableModel().fireTableDataChanged();
        frameObject.getInvoiceHeaderTableModel().fireTableDataChanged();
        frameObject.getInvTotalLbl().setText("" + invoice.getInvoiceTotal());
        displayInvoices();
    }

    private void deleteInvoice() {
        int invIndex = frameObject.getInvoicesTable().getSelectedRow();
        InvoiceHeader header = frameObject.getInvoiceHeaderTableModel().getinvoicesListHeader().get(invIndex);
        frameObject.getInvoiceHeaderTableModel().getinvoicesListHeader().remove(invIndex);
        frameObject.getInvoiceHeaderTableModel().fireTableDataChanged();
        frameObject.setInvoiceLinesTableModel(new InvoiceLinesTableModel(new ArrayList<InvoiceLine>()));
        frameObject.getInvLinesTable().setModel(frameObject.getInvoiceLinesTableModel());
        frameObject.getInvoiceLinesTableModel().fireTableDataChanged();
        frameObject.getCustNameTF().setText("");
        frameObject.getInvDateTF().setText("");
        frameObject.getInvNumLbl().setText("");
        frameObject.getInvTotalLbl().setText("");
        displayInvoices();
    }

    private void deleteLine() {
        int lineIndex = frameObject.getInvLinesTable().getSelectedRow();
        InvoiceLine line = frameObject.getInvoiceLinesTableModel().getInvoiceLines().get(lineIndex);
        frameObject.getInvoiceLinesTableModel().getInvoiceLines().remove(lineIndex);
        frameObject.getInvoiceLinesTableModel().fireTableDataChanged();
        frameObject.getInvoiceHeaderTableModel().fireTableDataChanged();
        frameObject.getInvTotalLbl().setText("" + line.getHeader().getInvoiceTotal());
        displayInvoices();
    }

    private void displayInvoices() {
        System.out.println("***************************");
        for (InvoiceHeader headerCount : frameObject.getInvoicesList()) {
            System.out.println(headerCount);
        }
        System.out.println("***************************");
    }
    
}
