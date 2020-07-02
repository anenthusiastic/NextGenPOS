
package com.company.domain;

import com.company.customTypes.Money;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author fatih
 */
public class TaxLineItem {
    
    private Map<String,Float> productTaxes = new HashMap<>();//taxes by category
    private Money amount;
    private ProductDescription pd;
    
    public TaxLineItem(Money amount,ProductDescription pd) {
        this.amount = amount;
        this.pd = pd;
        productTaxes.put("Drink", 0.1f);
        productTaxes.put("Alcohol", 0.2f);
        productTaxes.put("Food", 0.05f);
    }
    
    public Money getSubTax(){
        for (Map.Entry<String, Float> entry : productTaxes.entrySet()) {
            String key = entry.getKey();
            Float value = entry.getValue();
            if(pd.getCategory().equals(key)){
                return amount.times(value);
            }
        }
        return new Money();
    }
    
    @Override
    public String toString(){
        return "(Subtotal: "  + getSubTax()+")" + "\n";
    }
}
