package com.company.customTypes;

public class CurrencyException extends Exception {
    public CurrencyException(){
        super("Product currencies must be same in order to be in the same sale");
    }
}
