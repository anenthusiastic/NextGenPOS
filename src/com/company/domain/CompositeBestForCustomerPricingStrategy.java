
package com.company.domain;

import com.company.customTypes.Money;
import java.math.BigDecimal;
import java.util.Iterator;

/**
 *
 * @author fatih
 */
public class CompositeBestForCustomerPricingStrategy extends CompositePricingStrategy{

    @Override
    public Money getTotal(Sale sale) {
	Money lowestTotal = new Money(Integer.MAX_VALUE);
        ISalePricingStrategy lowestStrategy=null;
	for (Iterator i=strategies.iterator() ; i.hasNext();) {
            ISalePricingStrategy strategy =(ISalePricingStrategy) i.next();
            Money total = strategy.getTotal(sale);
            if(total.getAmount().compareTo(lowestTotal.getAmount())==-1){
                lowestTotal = total;
                lowestStrategy = strategy;
            }
        }
        //strategy ve indirim miktarını belirleyip mape ekleme işlemleri
        Money discount = sale.Total().minus(lowestTotal);
	if(AbsoluteDiscountOverThresholdPricingStrategy.class.isInstance(lowestStrategy) &
                    (discount.getAmount().compareTo(BigDecimal.ZERO))!=0){
                addDiscount("Absolute discount", discount);
            }
            else if(ProductDiscountPricingStrategy.class.isInstance(lowestStrategy) &
                    (discount.getAmount().compareTo(BigDecimal.ZERO))!=0){
                addDiscount("Product discount", discount);
            }
            else if(discount.getAmount().compareTo(BigDecimal.ZERO)!=0){
                addDiscount("Customer discount", discount);
            }	
	return lowestTotal;
    }
    
}
