package com.appsgeorge.EcommerceBackend.api.model.order;

import com.appsgeorge.EcommerceBackend.model.CustOrder;
import com.appsgeorge.EcommerceBackend.model.LocalUser;
import com.appsgeorge.EcommerceBackend.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Currency;
import java.util.List;

@RestController
@RequestMapping("/order")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @GetMapping
    public List<CustOrder> getOrders(@AuthenticationPrincipal LocalUser user){
        return orderService.getOrders(user);
    }

}
