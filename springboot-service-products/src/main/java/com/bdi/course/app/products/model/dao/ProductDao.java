package com.bdi.course.app.products.model.dao;

import com.bdi.course.app.commons.models.entity.Product;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductDao extends CrudRepository<Product, Long> {

}
