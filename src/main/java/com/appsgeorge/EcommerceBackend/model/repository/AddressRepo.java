package com.appsgeorge.EcommerceBackend.model.repository;

import com.appsgeorge.EcommerceBackend.model.Address;
import org.springframework.data.repository.ListCrudRepository;

import java.util.List;

public interface AddressRepo extends ListCrudRepository<Address,Long> {

    List<Address> findByUser_Id(long id);
}
