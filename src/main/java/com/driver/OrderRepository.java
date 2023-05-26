package com.driver;

import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

@Repository
public class OrderRepository {

    private HashMap<String,Order> oh = new HashMap<>();
    private HashMap<String,DeliveryPartner> dh = new HashMap<>();
    private HashMap<String, ArrayList<String>> od = new HashMap<>();

    public void addOrder(Order order) {
        oh.put(order.getId(),order);

    }

    public void addPartner(String partnerId) {
        dh.put(partnerId,new DeliveryPartner(partnerId));

    }

    public void addOrderPartnerPair(String orderId, String partnerId) {
        ArrayList<String> al = od.get(partnerId);
        if(al == null)
            al = new ArrayList<>();
        al.add(orderId);

        od.put(partnerId,al);
    }

    public Optional<Order> getOrderById(String orderId) {
        if(!oh.containsKey(orderId))
            return Optional.empty();

        return Optional.of(oh.get(orderId));


    }

    public DeliveryPartner getPartnerById(String partnerId) {
        return dh.get(partnerId) ;
    }

    public Integer getOrderCountByPartnerId(String partnerId) {
        return dh.get(partnerId).getNumberOfOrders();
    }

    public List<String> getOrdersByPartnerId(String partnerId) {
        return od.get(partnerId);
    }

    public List<String> getAllOrders() {
        return new ArrayList<>(oh.keySet());
    }

    public Integer getCountOfUnassignedOrders() {
        int orders = oh.size();
        int total = 0;

        for(String id : od.keySet())
        {
            total += getOrderCountByPartnerId(id);
        }

        return orders - total;
    }

    public Integer getOrdersLeftAfterGivenTimeByPartnerId(String time, String partnerId) {

        int count = 0;
        String[] a = time.split(":");
        int actualTime = Integer.parseInt(a[0]) * 60 + Integer.parseInt(a[1]);

        for(String b : od.keySet()) {
            ArrayList<String> al = od.get(b);
            for (int i = 0; i < al.size(); i++) {
                String orderId = al.get(i);
                if (oh.get(orderId).getDeliveryTime() > actualTime)
                    count++;
            }
        }
        return count;
    }

    public String getLastDeliveryTimeByPartnerId(String partnerId) {
        int max = Integer.MIN_VALUE;
        for(String b : od.keySet()) {
            ArrayList<String> al = od.get(b);
            for (int i = 0; i < al.size(); i++) {
                String orderId = al.get(i);
                if (oh.get(orderId).getDeliveryTime() > max)
                    max = oh.get(orderId).getDeliveryTime();
            }
        }
        int h = max / 60;
        int m = max % 60;


        return h+":"+m;

    }


    public void deletePartnerById(String partnerId) {
        dh.remove(partnerId);
        od.remove(partnerId);
    }

    public void deleteOrderById(String orderId) {
        oh.remove(orderId);

        for(String a : od.keySet()){
            ArrayList<String> al = od.get(a);
            for(int i = 0; i < al.size(); i++){
                if(al.get(i).equals(orderId))
                    al.remove(i);

            }
            od.put(a,al);


        }


    }


}
