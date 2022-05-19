
package com.model;
import java.util.ArrayList;


public class Invoice {
    private int id;
    private String date;
    private String customer;
    private ArrayList<Item> linesArray;
    

    public Invoice() {
    }

    public Invoice(int id, String date, String customer) {
        this.id = id;
        this.date = date;
        this.customer = customer;
    }

    public double getTotal() {
        double total=0.0;
        if(linesArray!=null){
             for (int i=0;i<linesArray.size();i++){
                total+=linesArray.get(i).getTotal();
        }
        return total;
                   
        }
        return total; 
    }


    

    public ArrayList<Item> getLinesArray() {
        if(linesArray==null){
            linesArray=new ArrayList<>();
        }
        return linesArray;
    }

    public String getCustomer() {
        return customer;
    }

    public void setCustomer(String customer) {
        this.customer = customer;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
    public String convertToCsv(){
        String result=id+","+date+","+customer;
        return result;
    }
    
    
}
