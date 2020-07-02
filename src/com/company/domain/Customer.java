
package com.company.domain;

/**
 *
 * @author fatih
 */
public class Customer {
    private int id;
    private String type;

 
    public Customer(int id, String type) {
        this.id = id;
        this.type = type;
    }

    public int getId() {
        return id;
    }
    
     public String getType() {
        return type;
    }


    @Override
    public String toString() {
        return "Customer id: "+id; //To change body of generated methods, choose Tools | Templates.
    }
    
    
 }


