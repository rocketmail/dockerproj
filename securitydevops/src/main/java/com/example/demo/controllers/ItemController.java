package com.example.demo.controllers;

import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.model.persistence.Item;
import com.example.demo.model.persistence.repositories.ItemRepository;

@RestController
@RequestMapping("/api/item")
public class ItemController {

    @Autowired
    private ItemRepository itemRepository;

    private static final Logger log = LoggerFactory.getLogger(ItemController.class);

    @GetMapping
    public ResponseEntity<List<Item>> getItems() {
        List<Item> items = itemRepository.findAll();
        if (items == null || items.isEmpty()) {
            log.error("getItems() error : Cannot get list of Items");
        }
        return ResponseEntity.ok(itemRepository.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Item> getItemById(@PathVariable Long id) {
        Optional<Item> item = itemRepository.findById(id);
        if (!item.isPresent()) {
            log.error("getItems() error : Cannot get Item");
        }
        return ResponseEntity.of(itemRepository.findById(id));
    }

    @GetMapping("/name/{name}")
    public ResponseEntity<List<Item>> getItemsByName(@PathVariable String name) {
        List<Item> items = itemRepository.findByName(name);
        if (items == null || items.isEmpty()) {
            log.error("getItemsByName() error : Cannot get list of Items by [" + name + "]");
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(items);

    }

}
