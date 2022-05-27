package com.shoppingcartapp.service.offer;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.anyLong;
import org.hamcrest.collection.IsCollectionWithSize;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import com.shoppingcartapp.model.BillDiscount;
import com.shoppingcartapp.model.DiscountItem;
import com.shoppingcartapp.model.OfferItem;
import com.shoppingcartapp.model.ProductPriceList;
import com.shoppingcartapp.service.IShoppingCartService;
import com.shoppingcartapp.service.ShoppingCartService;

public class SimpleDiscountOfferHandlerTest {

	private SimpleDiscountOfferHandler handler;
	private ProductPriceList priceList;
	private OfferItem offerItem;
	
	@Before
    public void setUp() throws Exception {
        this.priceList = Mockito.mock(ProductPriceList.class);
        this.offerItem = Mockito.mock(OfferItem.class);
        this.handler = new SimpleDiscountOfferHandler(offerItem,priceList);
    }
	
	@After
    public void tearDown() throws Exception {
        this.handler = null;
    }
	
	@Test
    public void testCreateSimpleDiscountOfferHandler() throws Exception {
        assertNotNull(handler);
    }
	
	@Test
    public void testHasNextReturnFalse() throws Exception {
        assertFalse(handler.hasNext());
    }
	
	@Test
    public void testHasNextReturnTrue() throws Exception {
        SimpleDiscountOfferHandler handlerNext= new SimpleDiscountOfferHandler(offerItem,priceList);
        handler.setNextHandler(handlerNext);
        assertTrue(handler.hasNext());
    }
	
	@Test
    public void testApplyOfferAndGenerateOneDiscount() throws Exception {
        IShoppingCartService shoppingCart = new ShoppingCartService();
        shoppingCart.addItems(Arrays.asList("item"));
        List<BillDiscount> billDiscountsResult = new ArrayList<>();
        OfferItem offerItem = new OfferItem("item", 1, new DiscountItem("item", 50.0));

        handler = Mockito.spy(new SimpleDiscountOfferHandler(offerItem,new ProductPriceList(Collections.EMPTY_MAP)));
        Mockito.when(handler.proccessDiscount(anyLong())).thenReturn(new BillDiscount("item", 1.0, 10.0));
        handler.applyOffer(shoppingCart, billDiscountsResult);

        Mockito.verify(handler).hasNext();
        assertThat(billDiscountsResult, IsCollectionWithSize.hasSize(1));
    }
	
	@Test
    public void testApplyNextOfferAndGenerateTwoDiscountWhere() throws Exception {
        IShoppingCartService shoppingCart = new ShoppingCartService();
        shoppingCart.addItems(Arrays.asList("item1","item1","item2","item2","item2"));
        List<BillDiscount> billDiscountsResult = new ArrayList<>();
        OfferItem offerItem1 = new OfferItem("item1", 1, new DiscountItem("item1", 50.0));
        OfferItem offerItem2 = new OfferItem("item2", 1, new DiscountItem("item2", 100.0));
        
        handler = Mockito.spy(new SimpleDiscountOfferHandler(offerItem1,new ProductPriceList(Collections.EMPTY_MAP)));
        handler.next = Mockito.spy(new SimpleDiscountOfferHandler(offerItem2,new ProductPriceList(Collections.EMPTY_MAP)));
        Mockito.when(handler.proccessDiscount(anyLong())).thenReturn(new BillDiscount("item1", 1.0, 4.5));
        handler.applyOffer(shoppingCart, billDiscountsResult);

        Mockito.verify(handler).hasNext();
        assertThat(billDiscountsResult, IsCollectionWithSize.hasSize(2));
    }
}
