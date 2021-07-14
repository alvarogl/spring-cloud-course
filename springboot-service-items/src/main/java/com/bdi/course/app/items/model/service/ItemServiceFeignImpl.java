package com.bdi.course.app.items.model.service;

import com.bdi.course.app.commons.models.entity.Product;
import com.bdi.course.app.items.client.ProductRestClient;
import com.bdi.course.app.items.model.dto.Item;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("itemServiceFeign")
public class ItemServiceFeignImpl implements ItemService {

    @Autowired
    private ProductRestClient productFeignClient;

    @Override
    public List<Item> findAll() {
        return productFeignClient.listProducts().stream().map(prod -> new Item(prod, 1)).collect(
            Collectors.toList());
    }

    @Override
    public Item findById(Long id, Integer amount) {
        Product product = productFeignClient.getProduct(id);

        return new Item(product, amount);
    }

    @Override
    public Product save(Product product) {
        return null;
    }

    @Override
    public Product update(Product product, Long id) {
        return null;
    }

    @Override
    public void delete(Long id) {

    }
}
