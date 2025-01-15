package com.proxyexample.proxydesignpattern.proxy;


import com.proxyexample.proxydesignpattern.component.FilpkartService;
import com.proxyexample.proxydesignpattern.component.FlipkartServiceImpl;

public class FlipkartServiceProxy implements FilpkartService {


    FlipkartServiceImpl flipkartService;
    Float totalDiscount;

    public FlipkartServiceProxy (Float totalDiscount){
        flipkartService = new FlipkartServiceImpl();
        this.totalDiscount = totalDiscount;
    }

    @Override
    public Float calculatePrice(Float[] prices) {

        Float finalAmount = 0f;
        if (totalDiscount==null || totalDiscount==0f){
           return flipkartService.calculatePrice(prices);
        }
        else {


            for( Float price:prices){
            finalAmount+=price;
            }

            finalAmount = finalAmount - (totalDiscount/100);
        }


        return finalAmount;
    }
}
