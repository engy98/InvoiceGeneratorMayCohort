
package com.model;

import java.util.ArrayList;
import javax.swing.table.AbstractTableModel;


public class InvoicesTable extends AbstractTableModel{
    private ArrayList<Invoice> invoices;
    private String[] titles={"no.", "date", "customer", "total"};
    
    public InvoicesTable(ArrayList<Invoice> invoices) {
        this.invoices = invoices;
    }

    @Override
    public int getRowCount() {
        return invoices.size();
    }


    @Override
    public int getColumnCount() {
        return 4;
        
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
       Invoice inv=invoices.get(rowIndex);
       switch (columnIndex){
           case 0: return inv.getId();
           case 1: return inv.getDate();
           case 2: return inv.getCustomer();
           case 3: return inv.getTotal();
       }
       return "";
    }
    
    @Override
    public String getColumnName(int index){
        return titles[index];
    }
    
}
