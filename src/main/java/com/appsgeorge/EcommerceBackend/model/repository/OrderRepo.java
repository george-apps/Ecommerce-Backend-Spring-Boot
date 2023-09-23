package com.appsgeorge.EcommerceBackend.model.repository;

import com.appsgeorge.EcommerceBackend.model.CustOrder;
import com.appsgeorge.EcommerceBackend.model.LocalUser;
import org.springframework.data.repository.ListCrudRepository;

import java.util.List;

public interface OrderRepo extends ListCrudRepository<CustOrder,Long> {

    List<CustOrder> findByUser(LocalUser user);
}
