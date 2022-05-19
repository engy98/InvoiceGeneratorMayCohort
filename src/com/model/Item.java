/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.model;

/**
 *
 * @author Engy Heshmat
 */
public class Item {
    private int id;
    private String name;
    private double price;
    private int count;
    private Invoice invoice;
    private double total;

    public Item() {
    }

    public Item(int id, String name, double price, int count, Invoice invoice) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.count = count;
        this.invoice=invoice;
    }

    public double getTotal() {
        this.total=this.price*this.count;
        return total;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }
    public String convertToCsv(){
        String res=invoice.getId()+","+name+","+price+","+count;
        return res;
    }
    
}
