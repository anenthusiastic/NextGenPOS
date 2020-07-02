package com.company.domain;

import com.company.customTypes.ItemID;
import com.company.customTypes.Money;


public class ProductDescription {
    private ItemID id;
    private Money price;
    private String description;
    private String category;
    
    public ProductDescription(ItemID id, Money price, String description,String category){
        this.id=id;
        this.price =price;
        this.description =description;
        this.category =category;
    }

    public ItemID getITemID(){
        return id;
    }

    public Money getPrice(){
        return price;
    }

    public String getDescription(){
        return  description;
    }
    
    public String getCategory() {
        return category;
    }

    @Override
    public String toString(){
        return id.toString()+ " - "+ price.toString()+" - " + description+" - " +category;
    }



}
