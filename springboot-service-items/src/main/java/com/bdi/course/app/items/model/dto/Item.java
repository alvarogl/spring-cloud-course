package com.bdi.course.app.items.model.dto;

import com.bdi.course.app.commons.models.entity.Product;

public class Item {

    private Product product;
    private Integer amount;

    public Item() {
    }

    public Item(Product product, Integer amount) {
        this.product = product;
        this.amount = amount;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public Integer getAmount() {
        return amount;
    }

    public void setAmount(Integer amount) {
        this.amount = amount;
    }

    public Double getTotalPrice() {
        return product.getPrice() * amount.doubleValue();
    }
}
