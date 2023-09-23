package com.appsgeorge.EcommerceBackend.api.model.product;

import com.appsgeorge.EcommerceBackend.model.Product;
import com.appsgeorge.EcommerceBackend.model.repository.ProductRepo;
import com.appsgeorge.EcommerceBackend.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/product")
public class ProductController {


    @Autowired
    private ProductService productService;


    @GetMapping
    public List<Product> getProducts(){
        return productService.getProducts();
    }
}
