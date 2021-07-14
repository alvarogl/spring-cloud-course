package com.bdi.course.app.items.model.service;

import com.bdi.course.app.commons.models.entity.Product;
import com.bdi.course.app.items.model.dto.Item;
import java.util.List;

public interface ItemService {

    List<Item> findAll();

    Item findById(Long id, Integer amount);

    Product save(Product product);

    Product update(Product product, Long id);

    void delete(Long id);
}
