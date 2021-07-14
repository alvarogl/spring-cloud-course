package com.bdi.course.app.items.client;

import com.bdi.course.app.items.model.dto.Product;
import java.util.List;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "product-service")
public interface ProductRestClient {

    @GetMapping("/products")
    List<Product> listProducts();

    @GetMapping("/products/{id}")
    Product getProduct(@PathVariable Long id);
}
