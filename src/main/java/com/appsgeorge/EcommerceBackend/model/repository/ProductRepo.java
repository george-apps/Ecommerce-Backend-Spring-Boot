package com.appsgeorge.EcommerceBackend.model.repository;

import com.appsgeorge.EcommerceBackend.model.Product;
import org.springframework.data.repository.ListCrudRepository;

public interface ProductRepo extends ListCrudRepository<Product,Long> {
}
