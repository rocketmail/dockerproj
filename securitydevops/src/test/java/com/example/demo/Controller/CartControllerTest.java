package com.example.demo.Controller;

import com.example.demo.TestUtils;
import com.example.demo.controllers.CartController;
import com.example.demo.controllers.UserController;
import com.example.demo.model.persistence.Cart;
import com.example.demo.model.persistence.Item;
import com.example.demo.model.persistence.User;
import com.example.demo.model.persistence.repositories.CartRepository;
import com.example.demo.model.persistence.repositories.ItemRepository;
import com.example.demo.model.persistence.repositories.UserRepository;
import com.example.demo.model.requests.ModifyCartRequest;
import org.junit.Before;
import org.junit.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class CartControllerTest {
    private CartController cartController;
    private UserRepository userRepo = mock(UserRepository.class);
    private CartRepository cartRepo = mock(CartRepository.class);
    private ItemRepository itemRepo = mock(ItemRepository.class);


    @Before
    public void setUp() {
        cartController = new CartController();
        TestUtils.injectObjects(cartController, "userRepository", userRepo);
        TestUtils.injectObjects(cartController, "cartRepository", cartRepo);
        TestUtils.injectObjects(cartController, "itemRepository", itemRepo);
    }

    @Test
    public void testAddTocart(){
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

        when(userRepo.findByUsername(any())).thenReturn(user);
        when(itemRepo.findById(any())).thenReturn(Optional.of(item));

        ModifyCartRequest request = new ModifyCartRequest();
        request.setItemId(0L);
        request.setQuantity(10);
        request.setUsername("testAddTocart");

        ResponseEntity<Cart> response = cartController.addTocart(request);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        Cart reponseCart = response.getBody();
        assertNotNull(reponseCart);
    }

    @Test
    public void testRemoveFromcart(){
        User user = new User();
        user.setId(0L);
        user.setUsername("testRemoveFromcart");
        user.setPassword("testPasswordRemoveFromcart");
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

        when(userRepo.findByUsername(any())).thenReturn(user);
        when(itemRepo.findById(any())).thenReturn(Optional.of(item));

        ModifyCartRequest request = new ModifyCartRequest();
        request.setItemId(0L);
        request.setQuantity(10);
        request.setUsername("testRemoveFromcart");

        ResponseEntity<Cart> response = cartController.removeFromcart(request);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        Cart reponseCart = response.getBody();
        assertNotNull(reponseCart);
    }

}
