package com.driver;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Order {

    private String id;
    private int deliveryTime;

    public Order(String id, String deliveryTime) {

        // The deliveryTime has to converted from string to int and then stored in the attribute
        //deliveryTime  = HH*60 + MM
        this.id = id;

        String[] a = deliveryTime.split(":");
        this.deliveryTime = Integer.parseInt(a[0]) * 60 + Integer.parseInt(a[1]);
    }


}
