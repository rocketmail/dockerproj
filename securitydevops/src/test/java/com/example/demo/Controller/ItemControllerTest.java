package com.example.demo.Controller;

import com.example.demo.TestUtils;
import com.example.demo.controllers.CartController;
import com.example.demo.controllers.ItemController;
import com.example.demo.model.persistence.Cart;
import com.example.demo.model.persistence.Item;
import com.example.demo.model.persistence.repositories.CartRepository;
import com.example.demo.model.persistence.repositories.ItemRepository;
import com.example.demo.model.persistence.repositories.UserRepository;
import org.junit.Before;
import org.junit.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class ItemControllerTest {
    private ItemController itemController;
    private ItemRepository itemRepo = mock(ItemRepository.class);

    //index=main error : Cannot get Item | bucket _time span=5m | stats count by _time | where count > 2

    @Before
    public void setUp() {
        itemController = new ItemController();
        TestUtils.injectObjects(itemController, "itemRepository", itemRepo);
    }

    @Test
    public void testGetItems() {
        Item item1 = new Item();
        item1.setId(0L);
        item1.setName("newItem1");
        item1.setPrice(BigDecimal.valueOf(23.99));
        Item item2 = new Item();
        item2.setId(1L);
        item2.setName("newItem2");
        item2.setPrice(BigDecimal.valueOf(23.99));

        List<Item> lstItem = new ArrayList<Item>();
        lstItem.add(item1);
        lstItem.add(item2);

        when(itemRepo.findAll()).thenReturn(lstItem);

        ResponseEntity<List<Item>> response = itemController.getItems();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        List<Item> responseItems = response.getBody();
        assertNotNull(responseItems);
        assertEquals(lstItem.size(), responseItems.size());
    }

    @Test
    public void testGetItemById() {
        Item item1 = new Item();
        item1.setId(0L);
        item1.setName("newItem1");
        item1.setPrice(BigDecimal.valueOf(23.99));
        Item item2 = new Item();
        item2.setId(1L);
        item2.setName("newItem2");
        item2.setPrice(BigDecimal.valueOf(23.99));

        List<Item> lstItem = new ArrayList<Item>();
        lstItem.add(item1);
        lstItem.add(item2);

        when(itemRepo.findById(any())).thenReturn(Optional.of(item1));

        ResponseEntity<Item> response = itemController.getItemById(0L);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        Item responseItem = response.getBody();
        assertNotNull(responseItem);
    }

    @Test
    public void testGetItemByIdIsNull() {
        Item item1 = new Item();
        item1.setId(0L);
        item1.setName("newItem1");
        item1.setPrice(BigDecimal.valueOf(23.99));
        Item item2 = new Item();
        item2.setId(1L);
        item2.setName("newItem2");
        item2.setPrice(BigDecimal.valueOf(23.99));

        List<Item> lstItem = new ArrayList<Item>();
        lstItem.add(item1);
        lstItem.add(item2);

        when(itemRepo.findById(any())).thenReturn(Optional.empty());

        ResponseEntity<Item> response = itemController.getItemById(0L);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        Item responseItem = response.getBody();
        assertNull(responseItem);
    }

    @Test
    public void testGetItemsByName() {
        Item item1 = new Item();
        item1.setId(0L);
        item1.setName("newItem1");
        item1.setPrice(BigDecimal.valueOf(23.99));
        Item item2 = new Item();
        item2.setId(1L);
        item2.setName("newItem2");
        item2.setPrice(BigDecimal.valueOf(23.99));

        List<Item> lstItem = new ArrayList<Item>();
        lstItem.add(item1);
        lstItem.add(item2);

        when(itemRepo.findByName("newItem1")).thenReturn(lstItem);

        ResponseEntity<List<Item>> response = itemController.getItemsByName("newItem1");

        assertEquals(HttpStatus.OK, response.getStatusCode());
        List<Item> responseItems = response.getBody();
        assertNotNull(responseItems);
        assertEquals(item1, responseItems.get(0));
    }
}
