package com.bdi.course.app.products.model.service;

import com.bdi.course.app.products.model.entity.Product;
import java.util.List;

public interface ProductService {
    List<Product> findAll();
    Product findById(Long id);

    Product save(Product product);

    void deleteById(Long id);
}
