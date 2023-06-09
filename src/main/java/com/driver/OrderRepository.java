package com.driver;

import org.springframework.stereotype.Repository;

import java.util.*;

@Repository
public class OrderRepository {

    private HashMap<String,Order> oh = new HashMap<>();
    private HashMap<String,DeliveryPartner> dh = new HashMap<>();
    private HashMap<String, List<String>> od = new HashMap<>();
    private HashSet<String> orderSet = new HashSet<>();

    public void addOrder(Order order) {
        oh.put(order.getId(),order);
        orderSet.add(order.getId());

    }

    public void addPartner(String partnerId) {
        dh.put(partnerId,new DeliveryPartner(partnerId));

    }

    public void addOrderPartnerPair(String orderId, String partnerId) {
        List<String> al = od.get(partnerId);
        if(al == null)
            al = new ArrayList<>();
        al.add(orderId);

        DeliveryPartner partner = dh.get(partnerId);
        partner.setNumberOfOrders( partner.getNumberOfOrders()+1);

        od.put(partnerId,al);
        orderSet.remove(orderId);



    }

    public Order getOrderById(String orderId) {
        return oh.get(orderId);


    }

    public DeliveryPartner getPartnerById(String partnerId) {
        return dh.get(partnerId) ;
    }

    public Integer getOrderCountByPartnerId(String partnerId) {
        return od.get(partnerId).size();
    }

    public List<String> getOrdersByPartnerId(String partnerId) {
        return od.get(partnerId);
    }

    public List<String> getAllOrders() {
        return new ArrayList<>(oh.keySet());
    }

    public Integer getCountOfUnassignedOrders() {


        return orderSet.size();
    }

    public Integer getOrdersLeftAfterGivenTimeByPartnerId(int time, String partnerId) {

        int count = 0;

        for(String b : od.get(partnerId)) {
            Order or = oh.get(b);
            if (or.getDeliveryTime() > time)
                    count++;
            }

        return count;
    }

    public int getLastDeliveryTimeByPartnerId(String partnerId) {
        int max = Integer.MIN_VALUE;

        for(String b : od.get(partnerId)) {
            Order or = oh.get(b);
            if (or.getDeliveryTime() > max)
                max = or.getDeliveryTime();
        }



        return max;

    }


    public void deletePartnerById(String partnerId) {
        if(od.get(partnerId) != null)
            orderSet.addAll(od.get(partnerId));
        dh.remove(partnerId);
        od.remove(partnerId);
    }

    public void deleteOrderById(String orderId) {



        if(oh.containsKey(orderId)){
            if(orderSet.contains(orderId))
                orderSet.remove(orderId);
            else{
                for(String partner : od.keySet()){
                    List<String> orders = od.get(partner);
                    if(orders.contains(orderId))
                        orders.remove(orderId);
                }
            }
        }


    }


}
