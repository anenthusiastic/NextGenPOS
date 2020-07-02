
package com.company.domain;

import com.company.customTypes.Money;
import java.math.BigDecimal;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author fatih
 */
public class PricingStrategyFactory {
    
    static PricingStrategyFactory instance = null;
    private ISalePricingStrategy strategy = null;
    private Map<Integer,Float> discountDays = new HashMap<>();//VIP müşterilere hangi gün yüzde kaç indirim olduğunu tutuyor
    private Map<String,Float> discountByCustomerType = new HashMap<>();//müşteri tipine göre yüzde kaç indirim olduğunu tutuyor
    private Money lastMonthsProfit = new Money(1257000);
    Date theDate = new Date();
    private PricingStrategyFactory(){
        discountDays.put(1, 0.05f);//5% indirim pazartesi
        discountDays.put(4, 0.03f);//3% indirim perşembe
        
        discountByCustomerType.put("Employee",0.1f);//Çalışanlara 10% sabit (yani haftanın her günü ) bir indirim var
    }
    public static PricingStrategyFactory getInstance() {
	if(instance == null)
            instance = new PricingStrategyFactory();
	return instance;
    }
    private void determinePricingStrategy(){
        Calendar cal = Calendar.getInstance();
        cal.setTime(theDate);
        if((cal.get(Calendar.DATE)==23 & (cal.get(Calendar.MONTH)==3)) || //23 nisan
                (cal.get(Calendar.DATE)==19 & (cal.get(Calendar.MONTH)==4)) || //19 mayıs
                (cal.get(Calendar.DATE)==23 & (cal.get(Calendar.MONTH)==3))|| //30 ağustos
                (cal.get(Calendar.DATE)==29 & (cal.get(Calendar.MONTH)==9))|| //29 ekim
                (cal.get(Calendar.DATE)==31 & (cal.get(Calendar.MONTH)==11))){ //31 aralık yılbaşı indrimi
            setSalePricingStrategy(1); //AllDiscounts strategy uygullanır
        }
        else if((lastMonthsProfit.getAmount().compareTo(BigDecimal.valueOf(1000000))==1) ||
                (lastMonthsProfit.getAmount().compareTo(BigDecimal.valueOf(1000000))==0)){//Geçen ayki kar 1milyon euro veya daha fazlaysa
            setSalePricingStrategy(0); //Best for customer strategy uygulanır
        }
        else if(lastMonthsProfit.getAmount().compareTo(BigDecimal.valueOf(1000000))==-1){//Geçen ayki kar 1milyon eurodan azsa
            setSalePricingStrategy(2); //Best for store strategy uygulanır
        }
    }
    public ISalePricingStrategy setSalePricingStrategy(int choice) {//aslında private olmalıydı. strategyleri test etebilmek için public yaptık
        switch(choice) {
            case 1:
                strategy = getAllDiscountsPricingStrategy();
                break;
            case 2:
                strategy = getBestForStorePricingStrategy();
                break;
            default:
                strategy = getBestForCustomerPricingStrategy();
        }
        return strategy;
    }
    
    public ISalePricingStrategy getSalePricingStrategy(){
        determinePricingStrategy();//Eğer önceki salede strategy değiştirildiyse,yeni sale başlatıldığında factory olması gereken strategy'yi tekrar belirler
        return strategy;
    }
    
    private ISalePricingStrategy SeniorPricingStrategy(float p) {
        return new PercentageDiscountPricingStrategy(p);
    }
    
    private ISalePricingStrategy AbsolutePricingStrategy(Money dis, Money th){
        return new AbsoluteDiscountOverThresholdPricingStrategy(dis, th);
    }
    
    private ISalePricingStrategy ProductPricingStrategy(){
        return new ProductDiscountPricingStrategy();
    }
   
    private ISalePricingStrategy getBestForCustomerPricingStrategy() {
        CompositeBestForCustomerPricingStrategy cust = new CompositeBestForCustomerPricingStrategy();
        cust.add(this.AbsolutePricingStrategy(new Money(100), new Money(1000)));
        cust.add(this.ProductPricingStrategy());
        return cust;
    }

    private ISalePricingStrategy getBestForStorePricingStrategy() {
        CompositeBestForStorePricingStrategy store = new CompositeBestForStorePricingStrategy();
        store.add(this.AbsolutePricingStrategy(new Money(100), new Money(1000)));
        store.add(this.ProductPricingStrategy());
        return store;
    }
    
    private ISalePricingStrategy getAllDiscountsPricingStrategy(){
        CompositeAllDiscountsPricingStrategy cadps = new CompositeAllDiscountsPricingStrategy();
        cadps.add(this.ProductPricingStrategy());
        cadps.add(this.AbsolutePricingStrategy(new Money(100), new Money(1000)));
        return cadps;
    }

    void addCustomerPricingStrategy(Sale s) {
        Customer c = s.getCstmr();
        CompositePricingStrategy ps = (CompositePricingStrategy)s.getPricingStrategy();
        if(c.getType().equals("VIP")){
            Calendar cal = Calendar.getInstance();
            cal.setTime(theDate);
            int day=cal.get(Calendar.DAY_OF_WEEK)-1;
            if(day==0)
                day=7;
            boolean isDiscountDay = discountDays.containsKey(day);

            if(isDiscountDay){
                ps.add(this.SeniorPricingStrategy(discountDays.get(day)));
            }
        }
        else{
            float pct = getCustomerPercentage(c);
            ps.add(this.SeniorPricingStrategy(pct));
        }
    } 
    
    private float getCustomerPercentage(Customer c) {
        for (Map.Entry<String, Float> entry : discountByCustomerType.entrySet()) {
            String key = entry.getKey();
            Float percent = entry.getValue();
            if(key.equals(c.getType()))
                return percent;
        }
        return 0;
    }
}
