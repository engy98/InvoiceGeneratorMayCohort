
package com.controller;


import com.model.Invoice;
import com.model.InvoicesTable;
import com.model.Item;
import com.model.ItemsTable;
import com.view.InvoiceDialog;
import com.view.InvoiceFrame;
import com.view.ItemDialog;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.swing.JFileChooser;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;


public class Controller implements ActionListener, ListSelectionListener {
    private InvoiceFrame Frame;
    private InvoiceDialog invoiceDialog;
    private ItemDialog itemDialog;
    public Controller(InvoiceFrame myFrame){
        this.Frame=myFrame;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String actionCommand=e.getActionCommand();
        switch (actionCommand){
            case "Load File":
                loadFile();
                break;
            case "Save File":
                saveFile();
                break;
            case "Create New Invoice":
                createNewInvoice();
                break;
            case "Delete Invoice":
                deleteInvoice();
                break;
            case "Create":
                create();
                break;
            case "Delete":
                delete();
                break;
            case "createInvOK":
                invoiceOk();
                break;
            case "createInvCancel":
                invoiceCancel();
                break;
            case "createItemOK":
                itemOk();
                break;
            case "createItemCancel":
                itemCancel();
                break;
                
    }
    
    
}

    private void loadFile() {
        JFileChooser fileChooser= new JFileChooser();
        try{
        int res=fileChooser.showOpenDialog(Frame);
        if(res==JFileChooser.APPROVE_OPTION){
            File header=fileChooser.getSelectedFile();
            Path headerPath=Paths.get(header.getAbsolutePath());
            List<String> headerData= Files.readAllLines(headerPath);
            ArrayList<Invoice> invoicesArr=new ArrayList<>();
            for(int i=0; i<headerData.size();i++){
                String[] headerSplited=headerData.get(i).split(",");
                int invoiceId=Integer.parseInt(headerSplited[0]);
                String invoiceDate=headerSplited[1];
                char[] buffer=invoiceDate.toCharArray();
                for(int t=0;t<invoiceDate.length();t++){
                    if(buffer[t]=='-'){
                        buffer[t]='/';
                    }
                }
                String dateModified=new String(buffer);
                String customerName=headerSplited[2];
                Invoice invoice=new Invoice(invoiceId, dateModified, customerName);
                invoicesArr.add(invoice);
            }
            res=fileChooser.showOpenDialog(Frame);
            if(res ==JFileChooser.APPROVE_OPTION){
                File line=fileChooser.getSelectedFile();
                Path linePath=Paths.get(line.getAbsolutePath());
                List<String> lineData= Files.readAllLines(linePath);
                for(int i=0;i< lineData.size();i++){
                    String [] lineSplited=lineData.get(i).split(",");
                    int invoiceId=Integer.parseInt(lineSplited[0]);
                    String item=lineSplited[1];
                    double price=Double.parseDouble(lineSplited[2]);
                    int count=Integer.parseInt(lineSplited[3]);
                    Invoice invoice=null;
                    for(int j=0;j<invoicesArr.size();j++){
                        if (invoicesArr.get(j).getId()==invoiceId){
                            invoice=invoicesArr.get(j);
                            break;
                        }
                    }
                    Item lineObj=new Item(invoiceId, item, price, count, invoice);
                    invoice.getLinesArray().add(lineObj);
                }
                
            }
            Frame.setInvoices(invoicesArr);
            InvoicesTable table=new InvoicesTable(invoicesArr);
            Frame.setInvTable(table);
            Frame.getInvoicesTable().setModel(table);
            Frame.getInvTable().fireTableDataChanged();
            for(int q=0;q<invoicesArr.size();q++){
                System.out.println("Invoice"+invoicesArr.get(q).getId());
                System.out.println("{");
                System.out.println("("+invoicesArr.get(q).getDate()+"),"+invoicesArr.get(q).getCustomer());
                for(int y=0;y<invoicesArr.get(q).getLinesArray().size();y++){
                    System.out.println(invoicesArr.get(q).getLinesArray().get(y).getName()+","+invoicesArr.get(q).getLinesArray().get(y).getPrice()+","+invoicesArr.get(q).getLinesArray().get(y).getCount());
                }
                System.out.println("}");
            }
          }
                
        }catch(IOException x){
            x.printStackTrace();
        }
    }

    private void saveFile() {
        ArrayList<Invoice> invoices=Frame.getInvoices();
        String invHeaders="";
        String items="";
        for(int i=0;i<invoices.size();i++){
            String invCsv=invoices.get(i).convertToCsv();
            invHeaders+=invCsv+"\n";
        
            for (int j=0;j<invoices.get(i).getLinesArray().size();j++){
                String itemCsv=invoices.get(i).getLinesArray().get(j).convertToCsv();
                items+=itemCsv+"\n";
            }
        }
        try{
        JFileChooser fileCh=new JFileChooser();
        int flag=fileCh.showSaveDialog(Frame);
        if(flag== JFileChooser.APPROVE_OPTION){
            File header=fileCh.getSelectedFile();
            FileWriter headerWriter=new FileWriter(header);
            headerWriter.write(invHeaders);
            headerWriter.flush();
            headerWriter.close();
            flag=fileCh.showSaveDialog(Frame);
            if(flag== JFileChooser.APPROVE_OPTION){
                File item=fileCh.getSelectedFile();
                FileWriter itemWriter=new FileWriter(item);
                 itemWriter.write(items);
                 itemWriter.flush();
                 itemWriter.close();
            }
        }
        }catch(Exception e){
            
        }
    }

    private void createNewInvoice() {
        invoiceDialog= new InvoiceDialog(Frame);
        invoiceDialog.setVisible(true);
        
         
    }

    private void deleteInvoice() {
        int index=Frame.getInvoicesTable().getSelectedRow();
        if(index>=0){
            Frame.getInvoices().remove(index);
            Frame.getInvTable().fireTableDataChanged();
            
        }
         
    }

    private void create() {
        itemDialog=new ItemDialog(Frame);
        itemDialog.setVisible(true);
         
    }

    private void delete() {
         int invIndex=Frame.getInvoicesTable().getSelectedRow();
         int index=Frame.getInvoiceItems().getSelectedRow();
        if(invIndex>=0 && index>=0){
            Invoice inv=Frame.getInvoices().get(invIndex);
            inv.getLinesArray().remove(index);
            ItemsTable itemTable=new ItemsTable(inv.getLinesArray());
            Frame.getInvoiceItems().setModel(itemTable);
            itemTable.fireTableDataChanged();
            Frame.getInvTable().fireTableDataChanged();
                        
            
        }
    }

    @Override
    public void valueChanged(ListSelectionEvent e) {
        
        int index=Frame.getInvoicesTable().getSelectedRow();
        if(index>=0){
        Invoice invoice=Frame.getInvoices().get(index);
          double invoiceTotal=invoice.getTotal();
        Frame.getInvoiceNum().setText(""+invoice.getId());
        Frame.getInvoiceDate().setText(invoice.getDate());
        Frame.getCustomerName().setText(invoice.getCustomer());
        Frame.getInvoiceTotal().setText(""+invoiceTotal);
        ItemsTable table=new ItemsTable(invoice.getLinesArray());
        Frame.getInvoiceItems().setModel(table);
        table.fireTableDataChanged();
        
        }
    }

    private void invoiceOk() {
        String date=invoiceDialog.getDateField().getText();
        char[] buffer=date.toCharArray();
        for(int x=0;x<buffer.length;x++){
            if(buffer[x]=='-'){
                buffer[x]='/';
            }
        }
        String dateModified=new String(buffer);
        String name=invoiceDialog.getNameField().getText();
        int id= Frame.getNextInvId();
        Invoice invoice=new Invoice(id, dateModified, name);
        Frame.getInvoices().add(invoice);
        Frame.getInvTable().fireTableDataChanged();
        invoiceCancel();
 
    }

    private void invoiceCancel() {
        invoiceDialog.setVisible(false);
        invoiceDialog.dispose();
        invoiceDialog=null;

    }

    private void itemOk() {
        String item=itemDialog.getNameField().getText();
        String count=itemDialog.getCountField().getText();
        String price=itemDialog.getPriceField().getText();
        int intCount=Integer.parseInt(count);
        double doublePrice=Double.parseDouble(price);
        int index=Frame.getInvoicesTable().getSelectedRow();
        if(index>=0){
            Invoice invoice=Frame.getInvoices().get(index);
            Item line=new Item(invoice.getId(),item, doublePrice, intCount, invoice);
            invoice.getLinesArray().add(line);
            ItemsTable itemTable=(ItemsTable) Frame.getInvoiceItems().getModel();
            itemTable.fireTableDataChanged();
            Frame.getInvTable().fireTableDataChanged();
        }
        itemCancel();

    }

    private void itemCancel() {
        itemDialog.setVisible(false);
        itemDialog.dispose();
        itemDialog=null;
        

    }
}
