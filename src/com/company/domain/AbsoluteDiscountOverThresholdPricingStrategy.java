
package com.company.domain;

import com.company.customTypes.Money;

/**
 *
 * @author fatih
 */
public class AbsoluteDiscountOverThresholdPricingStrategy implements ISalePricingStrategy{
    
    private Money discount;
    private Money threshold;
	
    public AbsoluteDiscountOverThresholdPricingStrategy(Money discount, Money threshold) {
	this.discount = discount;
	this.threshold = threshold;
    }
	
    public Money getDiscount() {
        return this.discount;
    }
	
    public Money getThreshold() {
        return this.threshold;
    }
	
    @Override
    public Money getTotal(Sale sale) {
	Money total = sale.Total();
        
	if((total.getAmount()).compareTo(threshold.getAmount())== -1){
            return total;
	}	
        return total.minus(this.discount);
    }
    
}
