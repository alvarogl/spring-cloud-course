package com.bdi.course.app.items.model.service;

import com.bdi.course.app.items.model.dto.Item;
import java.util.List;

public interface ItemService {

    List<Item> findAll();

    Item findById(Long id, Integer amount);
}
