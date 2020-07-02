package com.company.domain;

import com.company.fileOperations.SaleFileOperator;

import java.util.ArrayList;
import java.util.List;

public class Store {
    private ProductCatalog catalog = new ProductCatalog();
    private Register register = new Register(catalog, this);
    private List<Sale> completedSales = new ArrayList<>();
    private List<Customer> customers = new ArrayList<>();
    
    SaleFileOperator sfo = new SaleFileOperator("..\\NextGenPOS\\Sales.txt");

    public Store() {
        customers.add(new Customer(687,""));
        customers.add(new Customer(647,"VIP"));
        customers.add(new Customer(171,"Employee"));
        customers.add(new Customer(680,""));
        customers.add(new Customer(1,"VIP"));
        customers.add(new Customer(3,"Employee"));
    }
    
    public Register getRegister(){
        return register;
    }

    public void addSale(Sale s){
        completedSales.add(s);
        saveSale(s);
    }

    public void saveSale(Sale s){
        sfo.saveSale(s);
    }

    Customer getCustomer(int id) {
        for (Customer c: customers) {
            if(c.getId()==id){
                return c;
            }
        }
        return null;
    }
}
