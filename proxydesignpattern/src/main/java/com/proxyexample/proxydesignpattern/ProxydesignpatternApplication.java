package com.proxyexample.proxydesignpattern;


import com.proxyexample.proxydesignpattern.component.FilpkartService;
import com.proxyexample.proxydesignpattern.factory.FlipkartFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ProxydesignpatternApplication {

    public static void main(String[] args) {

        SpringApplication.run(ProxydesignpatternApplication.class, args);

        FilpkartService filpkartService = FlipkartFactory.getInstance("0");

       Float price =  filpkartService.calculatePrice( new Float []{ 10f,20f,50f,70f,});
        System.err.println(price);
        System.err.println(filpkartService.getClass());
    }

}
