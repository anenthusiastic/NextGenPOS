
package com.company.domain;

import com.company.customTypes.Money;
import java.math.BigDecimal;
import java.util.Iterator;

/**
 *
 * @author fatih
 */
public class CompositeAllDiscountsPricingStrategy extends CompositePricingStrategy{

    @Override
    public Money getTotal(Sale sale) {
        Money reduced = sale.getTotal();
        for (Iterator i=strategies.iterator() ; i.hasNext();) {
            ISalePricingStrategy strategy =(ISalePricingStrategy) i.next();
            Money old = sale.Total();
            reduced=sale.setTotal(strategy.getTotal(sale));
            Money discount = old.minus(reduced);
            //strategy ve indirim miktarını belirleyip mape ekleme işlemleri
            if(AbsoluteDiscountOverThresholdPricingStrategy.class.isInstance(strategy) &
                    (discount.getAmount().compareTo(BigDecimal.ZERO))!=0){
                addDiscount("Absolute discount", discount);
            }
            else if(ProductDiscountPricingStrategy.class.isInstance(strategy) &
                    (discount.getAmount().compareTo(BigDecimal.ZERO))!=0){
                addDiscount("Product discount", discount);
            }
            else if(discount.getAmount().compareTo(BigDecimal.ZERO)!=0){
                addDiscount("Customer discount", discount);
            }
        }
        return reduced;
    }
    
}
