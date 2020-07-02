
package com.company.domain;


import com.company.customTypes.ItemID;
import com.company.customTypes.Money;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @author fatih
 */
public class ProductDiscountPricingStrategy implements ISalePricingStrategy{
    
    private Map<ItemID,Float> productDiscounts = new HashMap<>();

    public ProductDiscountPricingStrategy() {
        productDiscounts.put(new ItemID("11"), 0.5f);
    }
    
    @Override
    public Money getTotal(Sale s) {
        Money pdt = s.Total();
        List<SalesLineItem> SaleLineItems=s.getLineItems();

        for (SalesLineItem lineItem : SaleLineItems) {
            ItemID id = lineItem.getDescription().getITemID();
            if(productDiscounts.containsKey(id)){
                Money subTotal = lineItem.getSubTotal();
                float disc=productDiscounts.get(id);
                Money discMoney = subTotal.times(disc);
                pdt=pdt.minus(discMoney);
            }
                
        }
        return pdt;
    }
    
}
