package com.bdi.course.app.products.controller;

import com.bdi.course.app.products.model.entity.Product;
import com.bdi.course.app.products.model.service.ProductService;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ProductController {

    //@Autowired
    //private Environment environment;

    @Value("${server.port}")
    private Integer port;

    @Autowired
    private ProductService productService;

    @GetMapping(value = "/products")
    public List<Product> getProductList() {
        List<Product> allProducts = productService.findAll().stream().map(product -> {
            product.setPort(port);
            return product;
        }).collect(Collectors.toList());
        return allProducts;
    }

    @GetMapping("/products/{id}")
    public Product getProduct(@PathVariable Long id) {
        Product product = productService.findById(id);
        //product.setPort(Integer.parseInt(environment.getProperty("local.server.port")));
        product.setPort(port);
        return product;
    }
}
