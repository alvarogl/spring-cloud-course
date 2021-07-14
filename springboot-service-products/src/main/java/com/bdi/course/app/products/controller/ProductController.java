package com.bdi.course.app.products.controller;

import com.bdi.course.app.commons.models.entity.Product;
import com.bdi.course.app.products.exception.ProductNotFoundException;
import com.bdi.course.app.products.model.service.ProductService;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ProductController {

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
        if (product == null) {
            throw new ProductNotFoundException(id);
        }
        product.setPort(port);
        return product;
    }

    @PostMapping("/products")
    @ResponseStatus(HttpStatus.CREATED)
    public Product createProduct(@RequestBody Product product) {
        return productService.save(product);
    }

    @PutMapping("/products/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Product updateProduct(@RequestBody Product product, @PathVariable Long id) {
        Product prodDb = productService.findById(id);
        if (prodDb == null) {
            throw new ProductNotFoundException(id);
        }

        prodDb.setName(product.getName());
        prodDb.setPrice(product.getPrice());
        return productService.save(prodDb);
    }

    @DeleteMapping("/products/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteById(@PathVariable Long id) {
        productService.deleteById(id);
    }
}
