package com.appsgeorge.EcommerceBackend.service;

import com.appsgeorge.EcommerceBackend.model.CustOrder;
import com.appsgeorge.EcommerceBackend.model.LocalUser;
import com.appsgeorge.EcommerceBackend.model.repository.OrderRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderService {

    @Autowired
    private OrderRepo orderRepo;

    public List<CustOrder> getOrders(LocalUser user){
        return orderRepo.findByUser(user);
    }

}
