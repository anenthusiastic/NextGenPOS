package com.company.domain;

import com.company.customTypes.Money;


public class SalesLineItem  {
    private int quantity;
    private ProductDescription description;

    public SalesLineItem(ProductDescription desc, int quantity){
        this.description = desc;
        this.quantity = quantity;
    }

    public ProductDescription getDescription() {
        return description;
    }

    public Money getSubTotal(){
        return description.getPrice().times(quantity);
    }

    @Override
    public String toString(){
        return description.toString() + " (x" +quantity +") " + "(Subtotal: "  + getSubTotal()+")" + "\n";
    }


}
