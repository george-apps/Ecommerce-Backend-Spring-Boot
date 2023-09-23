package com.appsgeorge.EcommerceBackend.service;

import com.appsgeorge.EcommerceBackend.model.Product;
import com.appsgeorge.EcommerceBackend.model.repository.ProductRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductService {

    @Autowired
    private ProductRepo productRepo;


    public List<Product> getProducts(){
        return productRepo.findAll();
    }
}
