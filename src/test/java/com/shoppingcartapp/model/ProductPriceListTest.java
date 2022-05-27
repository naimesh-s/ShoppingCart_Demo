package com.shoppingcartapp.model;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;
import static org.hamcrest.core.Is.is;

import java.util.HashMap;
import java.util.Map;

import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class ProductPriceListTest {
	
	private static final Map<String, Double> itemPrice = new HashMap<>();
    private ProductPriceList priceList;
    
    
    @BeforeClass
    public static void setUpBeforeClass() throws Exception {
        itemPrice.put("A", 0.65);
        itemPrice.put("B", 0.80);
        itemPrice.put("X", 1.30);
        itemPrice.put("Y", 1.00);
    }
    
    @Before
    public void setUp() throws Exception {
        this.priceList = new ProductPriceList(itemPrice);
    }
    
    @After
    public void tearDown() throws Exception {
        this.priceList = null;
    }
    
    @Test
    public void testCreatePriceList() {
        assertNotNull(priceList);
    }
    
    @Test
    public void testGetPriceReturnPrice() throws Exception {
        assertThat(priceList.getItemPrice("A"), is(0.65));
        assertThat(priceList.getItemPrice("B"), is(0.80));
        assertThat(priceList.getItemPrice("X"), is(1.30));
        assertThat(priceList.getItemPrice("Y"), is(1.00));
    }
    
    @Test
    public void testIfItemNotFoundReturnsZero() throws Exception {
        assertThat(priceList.getItemPrice("noExist"), is(0.00));
    }
    
    @Test
    public void testHashCodeNotNullForItemPrice() throws Exception {
    	assertNotNull(priceList.hashCode());
    }
}
