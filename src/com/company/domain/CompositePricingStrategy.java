
package com.company.domain;

import com.company.customTypes.Money;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @author fatih
 */
public abstract class CompositePricingStrategy implements ISalePricingStrategy{
    
    protected List strategies = new ArrayList();
    public Map <String,Money> discounts = new HashMap<>();
    
    protected void add( ISalePricingStrategy s ) {
	strategies.add(s);
    }
    
    protected void addDiscount(String discountType,Money discount){
        discounts.put(discountType, discount);
    }
    
    @Override
    public abstract Money getTotal(Sale s);
    
}
