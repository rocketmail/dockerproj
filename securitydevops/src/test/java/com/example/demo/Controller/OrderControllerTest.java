package com.example.demo.Controller;

import com.example.demo.TestUtils;
import com.example.demo.controllers.CartController;
import com.example.demo.controllers.OrderController;
import com.example.demo.model.persistence.Cart;
import com.example.demo.model.persistence.Item;
import com.example.demo.model.persistence.User;
import com.example.demo.model.persistence.UserOrder;
import com.example.demo.model.persistence.repositories.CartRepository;
import com.example.demo.model.persistence.repositories.ItemRepository;
import com.example.demo.model.persistence.repositories.OrderRepository;
import com.example.demo.model.persistence.repositories.UserRepository;
import org.junit.Before;
import org.junit.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class OrderControllerTest {
    private OrderController orderController;
    private UserRepository userRepo = mock(UserRepository.class);
    private OrderRepository orderRepo = mock(OrderRepository.class);


    @Before
    public void setUp() {
        orderController = new OrderController();
        TestUtils.injectObjects(orderController, "userRepository", userRepo);
        TestUtils.injectObjects(orderController, "orderRepository", orderRepo);

    }

    @Test
    public void testSubmit() {
        User user = new User();
        user.setId(0L);
        user.setUsername("testAddTocart");
        user.setPassword("testPasswordAddTocart");
        //user.setCart(getTestCart(user));

        Item item = new Item();
        item.setId(0L);
        item.setName("newItem");
        item.setPrice(BigDecimal.valueOf(23.99));

        List<Item> lstItem = new ArrayList<Item>();
        lstItem.add(item);

        Cart cart = new Cart();
        cart.setId(0L);
        cart.setItems(lstItem);
        cart.setTotal(BigDecimal.valueOf(23.99));
        cart.setUser(user);

        user.setCart(cart);

        when(userRepo.findByUsername("testAddTocart")).thenReturn(user);
        ResponseEntity<UserOrder> response = orderController.submit("testAddTocart");

        assertEquals(HttpStatus.OK, response.getStatusCode());
        UserOrder reponseUserOrder = response.getBody();
        assertNotNull(reponseUserOrder);
    }

    @Test
    public void testGetOrdersForUser() {
        User user = new User();
        user.setId(0L);
        user.setUsername("testAddTocart");
        user.setPassword("testPasswordAddTocart");
        //user.setCart(getTestCart(user));

        Item item = new Item();
        item.setId(0L);
        item.setName("newItem");
        item.setPrice(BigDecimal.valueOf(23.99));

        List<Item> lstItem = new ArrayList<Item>();
        lstItem.add(item);

        Cart cart = new Cart();
        cart.setId(0L);
        cart.setItems(lstItem);
        cart.setTotal(BigDecimal.valueOf(23.99));
        cart.setUser(user);

        user.setCart(cart);

        when(userRepo.findByUsername("testAddTocart")).thenReturn(user);
        orderController.submit("testAddTocart");

        ResponseEntity<List<UserOrder>> responseEntity = orderController.getOrdersForUser("testAddTocart");

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        List<UserOrder> reponseUserOrder = responseEntity.getBody();
        assertNotNull(reponseUserOrder);
    }
}
