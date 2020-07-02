
package com.company.domain;

import com.company.customTypes.Money;

/**
 *
 * @author fatih
 */
public interface ISalePricingStrategy {
    public Money getTotal(Sale s);
    
}
