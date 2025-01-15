package com.proxyexample.proxydesignpattern.factory;
import com.proxyexample.proxydesignpattern.component.FilpkartService;
import com.proxyexample.proxydesignpattern.component.FlipkartServiceImpl;
import com.proxyexample.proxydesignpattern.proxy.FlipkartServiceProxy;

public class FlipkartFactory {


    public static FilpkartService getInstance(String coupen){

        if(coupen.equals("") || coupen.equals("0")){
            return new FlipkartServiceImpl();
        }
        else if(coupen.equals("10")) {
            return new FlipkartServiceProxy(10f);
        }
        else if (coupen.equals("20")){
            return new FlipkartServiceProxy(20f);
        }
        else {
            return new FlipkartServiceProxy(50f);
        }
    }
}
