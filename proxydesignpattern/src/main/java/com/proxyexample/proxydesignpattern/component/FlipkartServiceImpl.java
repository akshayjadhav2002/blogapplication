package com.proxyexample.proxydesignpattern.component;

public class FlipkartServiceImpl implements FilpkartService{

    @Override
    public Float calculatePrice(Float[] prices) {
        Float totalAmount = 0f;
        for (Float price : prices){
            totalAmount +=price;
        }
        return totalAmount;
    }
}
