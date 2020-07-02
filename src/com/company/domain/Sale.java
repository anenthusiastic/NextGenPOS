package com.company.domain;

import com.company.customTypes.CurrencyException;
import com.company.customTypes.Money;
import java.math.BigDecimal;

import java.util.*;

public class Sale {
    private List<SalesLineItem> lineItems = new ArrayList<>();
    private Date date = new Date();
    private boolean isComplete = false;
    private Payment payment;
    private ISalePricingStrategy pricingStrategy ;

    public Sale() {
        this.pricingStrategy = PricingStrategyFactory.getInstance().getSalePricingStrategy();
    }
    private Customer cstmr;
    private Money Total ;
    
    
    public Money getBalance() {
        return payment.getAmount().minus(getReducedTotal());
    }

    public void becomeComplete() {
        isComplete = true;
    }

    public boolean isComplete() {
        return isComplete;
    }

    public String makeLineItem(ProductDescription desc, int quantity) throws CurrencyException {
        SalesLineItem sl = new SalesLineItem(desc, quantity);
        String slString=sl.toString();
        if (lineItems.size() > 0) { //check if there is any product in the list
            Currency firstItemCurrency =  lineItems.get(0).getSubTotal().getCurrency();
            if (firstItemCurrency == desc.getPrice().getCurrency()) {//check to see if currencies are the same
                lineItems.add(sl);
            } else {
                throw new CurrencyException();
            }
        } else {
            lineItems.add(sl);
        }
        return slString;
    }

    public Money getTotalWithoutTax() {
        Money totalwt = new Money(BigDecimal.ZERO,lineItems.get(0).getDescription().getPrice().getCurrency());
        Money subTotal = null;
        Money subTax = null;
        for (SalesLineItem lineItem : lineItems) {
            subTotal = lineItem.getSubTotal();
            totalwt = totalwt.add(subTotal);
        }
        return totalwt;
    }
    
    public Money getTotalTax(){
        Money subTotal = null;
        Money subTax = null;
        Money totalTax = new Money(BigDecimal.ZERO,lineItems.get(0).getDescription().getPrice().getCurrency());
        for (SalesLineItem lineItem : lineItems) {
            subTotal = lineItem.getSubTotal();
            subTax = (new TaxLineItem(subTotal, lineItem.getDescription())).getSubTax();
            totalTax = totalTax.add(subTax);
        }
        
        return totalTax;
    }
    
    public Money getTotal(){
        Total = getTotalWithoutTax().add(getTotalTax());
        return Total ;
    }
    
    public Money setTotal(Money m){
        Total=m;
        return Total;
    }
    
    public Money Total(){
        return Total;
    }
    
    public void makePayment(Money cashTendered) {
        payment = new Payment(cashTendered);
    }


    @Override
    public String toString() {
        String s = "Date: " + date.toString() + "\n";
        
        s += "\n";
        s += "Total (without tax): " + getTotalWithoutTax()+ "\n";
        s += "Total: " + getTotal()+ "\n";
        s += "(Tax Amount:" + getTotalTax()+ ")\n";

        if(payment!=null){
            s += "Payment: " + payment.toString() + "\n";
            s += "Balance: " + getBalance() + "\n";
            String dsc=discounts();
            if(!dsc.equals("")){
                s += "(Discount Amount:" + getDiscount() + "\n";
                s += "Discounts respectively :\n";
            }
                
            s += dsc+")\n";
        }
        s += "--------------------------------------\n";
        return s;
    }
    
    public String discounts(){
        String s="";
        Map<String,Money> map = ((CompositePricingStrategy)pricingStrategy).discounts;
        for (Map.Entry<String, Money> entry : map.entrySet()) {
            String key = entry.getKey();
            Money value = entry.getValue();
            s+=key + ": " + value + "\n";
        }
        return s;
    }
    
    public Money getDiscount(){
       return getTotal().minus(getReducedTotal());
    }

    public List<SalesLineItem> getLineItems() {
        return lineItems;
    }
	
    public Money getReducedTotal() {
	return pricingStrategy.getTotal(this);
    }

    void enterCustomerForDiscount(Customer c) {
        cstmr=c;
        PricingStrategyFactory.getInstance().addCustomerPricingStrategy(this);
    }

    public Customer getCstmr() {
        return cstmr;
    }

    public ISalePricingStrategy getPricingStrategy() {
        return pricingStrategy;
    }

    void setPricingStrategy(int i) {
        pricingStrategy = PricingStrategyFactory.getInstance().setSalePricingStrategy(i);
    }

}
