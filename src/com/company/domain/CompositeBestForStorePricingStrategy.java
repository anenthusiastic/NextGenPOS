
package com.company.domain;

import com.company.customTypes.Money;
import java.math.BigDecimal;
import java.util.Iterator;

/**
 *
 * @author fatih
 */
public class CompositeBestForStorePricingStrategy extends CompositePricingStrategy{

    @Override
    public Money getTotal(Sale sale) {
	Money highestTotal = new Money(Integer.MIN_VALUE);
        ISalePricingStrategy highestStrategy=null;
	for (Iterator i=strategies.iterator() ; i.hasNext();) {
            ISalePricingStrategy strategy =(ISalePricingStrategy) i.next();
            Money total = strategy.getTotal(sale);
            if(total.getAmount().compareTo(highestTotal.getAmount())==1){
                highestTotal = total;
                highestStrategy = strategy;
            }
        }   
	 //strategy ve indirim miktarını belirleyip mape ekleme işlemleri
        Money discount = sale.Total().minus(highestTotal);
	if(AbsoluteDiscountOverThresholdPricingStrategy.class.isInstance(highestStrategy) &
                    (discount.getAmount().compareTo(BigDecimal.ZERO))!=0){
                addDiscount("Absolute discount", discount);
            }
            else if(ProductDiscountPricingStrategy.class.isInstance(highestStrategy) &
                    (discount.getAmount().compareTo(BigDecimal.ZERO))!=0){
                addDiscount("Product discount", discount);
            }
            else if(discount.getAmount().compareTo(BigDecimal.ZERO)!=0){
                addDiscount("Customer discount", discount);
            }	
		
        return highestTotal;
    }
    
}
