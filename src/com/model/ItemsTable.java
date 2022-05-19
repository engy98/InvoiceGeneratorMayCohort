
package com.model;

import java.util.ArrayList;
import javax.swing.table.AbstractTableModel;


public class ItemsTable extends AbstractTableModel{
    private ArrayList<Item> items;
    private String[] titles={"no.", "item name", "item price", "count", "item total"};

    public ItemsTable(ArrayList<Item> items) {
        this.items = items;
    }

    public ArrayList<Item> getItems() {
        return items;
    }
    

    @Override
    public int getRowCount() {
        return items.size();
   }

    @Override
    public int getColumnCount() {
        return 5;

    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        Item line=items.get(rowIndex);
        switch(columnIndex){
            case 0: return line.getId();
            case 1: return line.getName();
            case 2: return line.getPrice();
            case 3: return line.getCount();
            case 4: return line.getTotal();
        }
        return "";
    }
    @Override
    public String getColumnName(int index){
        return titles[index];
    }
    
}
