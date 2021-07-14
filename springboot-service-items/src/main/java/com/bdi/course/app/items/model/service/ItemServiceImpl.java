package com.bdi.course.app.items.model.service;

import com.bdi.course.app.items.model.dto.Item;
import com.bdi.course.app.items.model.dto.Product;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
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
}
