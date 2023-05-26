package com.driver;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class OrderService {
    @Autowired OrderRepository orderRepository;

    public void addOrder(Order order) {
        orderRepository.addOrder(order);

    }

    public void addPartner(String partnerId) {
        orderRepository.addPartner(partnerId);
    }

    public void addOrderPartnerPair(String orderId, String partnerId) {
        orderRepository.addOrderPartnerPair(orderId,partnerId);
    }

    public Order getOrderById(String orderId) {
        Optional<Order> val = orderRepository.getOrderById(orderId);
        if(val.isEmpty())
            throw new RuntimeException("Order not Found");
        return val.get();
    }

    public DeliveryPartner getPartnerById(String partnerId) {
        return orderRepository.getPartnerById(partnerId);
    }

    public Integer getOrderCountByPartnerId(String partnerId) {
        return orderRepository.getOrderCountByPartnerId(partnerId);
    }

    public List<String> getOrdersByPartnerId(String partnerId) {
        return orderRepository.getOrdersByPartnerId(partnerId);
    }

    public List<String> getAllOrders() {
        return orderRepository.getAllOrders();
    }

    public Integer getCountOfUnassignedOrders() {
        return orderRepository.getCountOfUnassignedOrders();
    }

    public Integer getOrdersLeftAfterGivenTimeByPartnerId(String time, String partnerId) {
        String[] a = time.split(":");
        int actualTime = Integer.parseInt(a[0]) * 60 + Integer.parseInt(a[1]);
        return orderRepository.getOrdersLeftAfterGivenTimeByPartnerId(actualTime,partnerId);
    }

    public String getLastDeliveryTimeByPartnerId(String partnerId) {
        int time = orderRepository.getLastDeliveryTimeByPartnerId(partnerId);
        int h = time / 60;
        int m = time % 60;

        String hh = String.valueOf(h);
        String mm = String.valueOf(m);

        if(hh.length() == 1)
            hh = '0' + hh;
        if(mm.length() == 1)
            mm = '0' + mm;

        return hh+":"+mm;
    }

    public void deletePartnerById(String partnerId) {
        orderRepository.deletePartnerById(partnerId);
    }

    public void deleteOrderById(String orderId) {
        orderRepository.deleteOrderById(orderId);
    }



}
