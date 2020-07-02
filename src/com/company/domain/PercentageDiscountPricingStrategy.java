
package com.company.domain;

import com.company.customTypes.Money;

/**
 *
 * @author fatih
 */
public class PercentageDiscountPricingStrategy implements ISalePricingStrategy{
    
    private float percentage = (float) 0;
	
    public PercentageDiscountPricingStrategy(float p) {
	this.percentage = p;
    }
	
    public void setPercentage( float p ) {
	this.percentage = p;
    }
	
    public float getPercentage() {
	return this.percentage;
    }
        
    @Override
    public Money getTotal(Sale sale) {
        return sale.Total().times (1.0 - percentage);
    }
    
}
