package com.bdi.course.app.items.controller;

import com.bdi.course.app.commons.models.entity.Product;
import com.bdi.course.app.items.model.dto.Item;
import com.bdi.course.app.items.model.service.ItemService;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.juli.logging.Log;
import org.apache.juli.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.client.circuitbreaker.CircuitBreakerFactory;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RefreshScope
@RestController
public class ItemController {

    private static final Log LOGGER = LogFactory.getLog(ItemController.class);
    @Autowired
    private Environment env;

    @Autowired
    @Qualifier("itemServiceRestTemplate")
    private ItemService itemService;
    private CircuitBreakerFactory factory;

    @Value("${config.text.env}")
    private String envName;

    public ItemController(CircuitBreakerFactory factory) {
        this.factory = factory;
    }

    @GetMapping("/items")
    public List<Item> listAllItems() {
        return itemService.findAll();
    }

    @GetMapping("/items/{id}/amount/{amount}")
    public Item getItem(@PathVariable Long id, @PathVariable Integer amount) {
        return factory.create("getItem").run(() -> itemService.findById(id, amount), throwable -> {
            LOGGER.error(throwable.getMessage(), throwable);
            Item item = new Item();
            Product product = new Product();
            product.setName("Fallback");
            product.setPrice(0.0);
            product.setId(id);
            item.setAmount(amount);
            item.setProduct(product);
            return item;
        });
    }

    @GetMapping("/getConfig")
    public ResponseEntity<?> getConfig(@Value("${server.port}") String port) {
        Map<String, String> configMap = new HashMap<>();
        configMap.put("text", envName);
        configMap.put("port", port);
        if (env.getActiveProfiles().length > 0 && env.getActiveProfiles()[0].equals("DEV")) {
            configMap.put("author.name", env.getProperty("config.author.name"));
            configMap.put("author.email", env.getProperty("config.author.email"));
        }
        return new ResponseEntity<>(configMap, HttpStatus.OK);
    }
}
