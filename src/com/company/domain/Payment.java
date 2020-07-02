package com.company.domain;

import com.company.customTypes.Money;



public class Payment {
    private Money amount;

    public Payment(Money cashTendered){
        amount=cashTendered;
    }

    public Money getAmount(){
        return amount;
    }

    public String toString(){
        return amount.toString();
    }
}
