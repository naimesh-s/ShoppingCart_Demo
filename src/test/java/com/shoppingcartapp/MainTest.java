package com.shoppingcartapp;

import static org.hamcrest.CoreMatchers.hasItems;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import com.shoppingcartapp.model.Bill;
import com.shoppingcartapp.servicce.bill.BillGenerator;
import com.shoppingcartapp.service.IShoppingCartService;
import com.shoppingcartapp.util.ConfigUtil;

/**
 * 
 * @author naimesh.shah
 *
 */
public class MainTest {
	
	public static final String TEST_DATA_FILE = "testdata.json";
	private static final List<String> ITEM_LIST_IN_CART = Arrays.asList("A", "A", "B", "B", "B", "X", "Y", "Y", "R");
	
	@Before
	public void setup() {
		
	}
	
	@Test
	public void testShoppingCart() throws Exception {
		
		IShoppingCartService shoppingCart = Main.getShoppingCart(ITEM_LIST_IN_CART);
		assertThat(shoppingCart.getQuantity("A"), is(2L));
        assertThat(shoppingCart.getQuantity("B"), is(3L));
        assertThat(shoppingCart.getQuantity("X"), is(1L));
        assertThat(shoppingCart.getQuantity("Y"), is(2L));
        assertThat(shoppingCart.getQuantity("R"), is(1L));
	}
	
	@Test
	public void testGetConfigReturnsConfigUtil() throws Exception {
		ConfigUtil configUtil = Main.getConfig(TEST_DATA_FILE);
        assertNotNull(configUtil);
	}
	
	@Test
    public void testGetPriceFromConfigReturnsNotEmpty() throws Exception {
        ConfigUtil configUtil = Main.getConfig(TEST_DATA_FILE);
        assertThat(configUtil.getPrices().size(), not(is(0)));
    }
	
	@Test
    public void testGetDiscountsFromConfigReturnsNotEmpty() throws Exception {
        ConfigUtil configUtil = Main.getConfig(TEST_DATA_FILE);
        assertThat(configUtil.getOffers(), hasItems());
    }
	
	@Test
    public void testGetBillGeneratorReturnBillGenerator() throws Exception {
        ConfigUtil configUtil = mock(ConfigUtil.class);
        when(configUtil.getPrices()).thenReturn(Collections.EMPTY_MAP);
        when(configUtil.getOffers()).thenReturn(Collections.EMPTY_LIST);
        BillGenerator billGenerator = Main.getBillGenerator(configUtil);
        assertNotNull(billGenerator);
    }
	
	@Test
    public void testShopping9itemsAnd2Discounts() throws Exception {
        IShoppingCartService shoppingCart = Main.getShoppingCart(ITEM_LIST_IN_CART);
        ConfigUtil configUtil = Main.getConfig(TEST_DATA_FILE);
        BillGenerator billGenerator = Main.getBillGenerator(configUtil);
        Bill bill = billGenerator.generateBill(shoppingCart);

        assertThat(bill.getSubtotal(), is(69.0));
        assertThat(bill.getTotal(), is(55.0));
        assertThat(bill.getBillDiscounts(), hasSize(2));
    }
}
