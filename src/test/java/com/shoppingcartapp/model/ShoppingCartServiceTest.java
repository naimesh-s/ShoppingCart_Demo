package com.shoppingcartapp.model;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;

import java.util.Arrays;
import java.util.List;

import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.hamcrest.collection.IsEmptyCollection.empty;
import static org.hamcrest.core.Is.is;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.shoppingcartapp.service.IShoppingCartService;
import com.shoppingcartapp.service.ShoppingCartService;

public class ShoppingCartServiceTest {

	private IShoppingCartService shoppingCart;

	@Before
	public void setUp() throws Exception {
		this.shoppingCart = new ShoppingCartService();
	}
	
	@After
    public void tearDown() throws Exception {
        this.shoppingCart = null;
    }
	
	@Test
    public void testCreateShoppingCart() {
        assertNotNull(shoppingCart);
    }
	
	@Test
    public void testEmptyReturnsNoItems() throws Exception {
        assertThat(shoppingCart.getItems(), empty());
    }
	
	@Test
    public void testAddUniqueItemListReturnsItemsInBasket() throws Exception {
        shoppingCart.addItems(Arrays.asList("A", "B"));
        assertThat(shoppingCart.getItems(), hasSize(2));
    }
	
	@Test
    public void testAdd_3A_ReturnsOneItem() throws Exception {
        shoppingCart.addItems(Arrays.asList("A", "A", "A"));
        assertThat(shoppingCart.getItems(), hasSize(1));
    }
	
	@Test
    public void testAdd_3A_ReturnsQuantity3() throws Exception {
        List<String> items = Arrays.asList("B", "B", "B");
        shoppingCart.addItems(items);
        assertThat(shoppingCart.getQuantity("B"), is(3L));
    }
	
	@Test
    public void testIfItemNotFoundReturnsZero() throws Exception {
        shoppingCart.addItems(Arrays.asList("X", "X", "X"));
        assertThat(shoppingCart.getQuantity("Y"), is(0L));
    }
}
