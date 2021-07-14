package com.bdi.course.app.items.model.service;

import com.bdi.course.app.commons.models.entity.Product;
import com.bdi.course.app.items.model.dto.Item;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service("itemServiceRestTemplate")
public class ItemServiceImpl implements ItemService {

    @Autowired
    @Qualifier("restClient")
    private RestTemplate restTemplate;

    @Override
    public List<Item> findAll() {
        List<Product> products = Arrays
            .asList(Objects.requireNonNull(
                restTemplate.getForObject("http://product-service/products", Product[].class)));

        return products.stream().map(prod -> new Item(prod, 1)).collect(Collectors.toList());
    }

    @Override
    public Item findById(Long id, Integer amount) {
        Map<String, String> pathVarMap = Map.of("id", id.toString());
        Product product = restTemplate
            .getForObject("http://product-service/products/{id}", Product.class, pathVarMap);

        return new Item(product, amount);
    }

    @Override
    public Product save(Product product) {
        HttpEntity<Product> productHttpEntity = new HttpEntity<>(product);
        ResponseEntity<Product> response = restTemplate
            .exchange("http://product-service/products", HttpMethod.POST, productHttpEntity,
                Product.class);

        return response.getBody();
    }

    @Override
    public Product update(Product product, Long id) {
        HttpEntity<Product> productHttpEntity = new HttpEntity<>(product);
        ResponseEntity<Product> response = restTemplate
            .exchange("http://product-service/products/{id}", HttpMethod.PUT, productHttpEntity,
                Product.class, Map.of("id", id.toString()));
        return response.getBody();
    }

    @Override
    public void delete(Long id) {
        restTemplate.delete("http://product-service/products/{id}", Map.of("id", id.toString()));
    }
}
