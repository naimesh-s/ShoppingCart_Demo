package com.shoppingcartapp.service.bill;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import com.shoppingcartapp.model.Bill;
import com.shoppingcartapp.model.BillDiscount;
import com.shoppingcartapp.model.ProductPriceList;
import com.shoppingcartapp.servicce.bill.BillGenerator;
import com.shoppingcartapp.service.IShoppingCartService;
import com.shoppingcartapp.service.ShoppingCartService;
import com.shoppingcartapp.service.offer.OfferHandlerProcessor;

import java.util.*;

import static junit.framework.Assert.assertNotNull;
import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;
import static org.mockito.ArgumentMatchers.any;

/**
 * 
 * @author naimesh.shah
 *
 */
public class BillGeneratorTest {

	private IShoppingCartService shoppingCart;

    private ProductPriceList priceList;

    private OfferHandlerProcessor offerHandlerProcessor;

    private BillGenerator billGenerator;
    
    @Before
    public void setUp() throws Exception {
        this.priceList = Mockito.mock(ProductPriceList.class);
        this.offerHandlerProcessor = Mockito.mock(OfferHandlerProcessor.class);
        this.shoppingCart = Mockito.spy(new ShoppingCartService());
        this.billGenerator = Mockito.spy(new BillGenerator(priceList, offerHandlerProcessor));
    }
    
    @After
    public void tearDown() throws Exception {
        this.priceList = null;
        this.offerHandlerProcessor = null;
        this.shoppingCart = null;
        this.billGenerator = null;
    }
    
    @Test
    public void testCreateGenerateBill() throws Exception {
        assertNotNull(billGenerator);
    }
    
    @Test
    public void testGenerateZeroBillWithEmptySCart() throws Exception {
        Bill bill = billGenerator.generateBill(Mockito.mock(IShoppingCartService.class));
        assertThat(bill, is(new Bill(0.0,0.0, Collections.emptyList())));
    }
    
    @Test
    public void testGenerateBillWithOutDiscounts() throws Exception {
        this.shoppingCart.addItems(Arrays.asList("item1", "item2"));
        Mockito.when(priceList.getItemPrice(any())).thenReturn(10.0);

        Bill bill = billGenerator.generateBill(shoppingCart);
        assertThat(bill, is(new Bill(20.0,20.0, Collections.emptyList())));
    }
    
    @Test
    public void testGenerateBillWithOffers() throws Exception {
    	this.shoppingCart.addItems(Arrays.asList("item1"));
        Mockito.when(priceList.getItemPrice(any())).thenReturn(10.0);
        Mockito.when(offerHandlerProcessor.applyOffer(any()))
                .thenReturn(Arrays.asList(
                        new BillDiscount("item1", 10.0, 1.0)
                ));
        Bill bill = billGenerator.generateBill(shoppingCart);
        assertThat(bill.getSubtotal(), is(10.0));
        assertThat(bill.getTotal(), is(9.0));
        assertThat(bill.getBillDiscounts(), hasSize(1));
    }
    
    
    @Test
    public void testGenerateBillWith3ItemsAndOffers() throws Exception {
        this.shoppingCart.addItems(Arrays.asList("item1", "item1", "item1"));
        Mockito.when(priceList.getItemPrice(any())).thenReturn(10.0);
        Mockito.when(offerHandlerProcessor.applyOffer(any()))
                .thenReturn(Arrays.asList(
                        new BillDiscount("item1", 10.0, 6.0)
                ));
        Bill bill = billGenerator.generateBill(shoppingCart);
        assertThat(bill.getSubtotal(), is(30.0));
        assertThat(bill.getTotal(), is(24.0));
        assertThat(bill.getBillDiscounts(), hasSize(1));
    }
    
    @Test
    public void testGenerateBillWithMultpipleItemsAndOffers() throws Exception {
        this.shoppingCart.addItems(Arrays.asList("item1", "item1", "item2"));
        Mockito.when(priceList.getItemPrice(any())).thenReturn(10.0);
        Mockito.when(offerHandlerProcessor.applyOffer(any()))
                .thenReturn(Arrays.asList(
                        new BillDiscount("item1", 10.0, 4.0),
                        new BillDiscount("item2", 10.0, 1.0)
                ));
        Bill bill = billGenerator.generateBill(shoppingCart);
        assertThat(bill.getSubtotal(), is(30.0));
        assertThat(bill.getTotal(), is(25.0));
        assertThat(bill.getBillDiscounts(), hasSize(2));
    }
    
    @Test
    public void testCalculateSubTotalWithEmptyCart() throws Exception {
        Double d = billGenerator.calculateSubTotal(shoppingCart);
        assertThat(d, is(0.0));
    }
    
    @Test
    public void testCalculateSubTotalUniqueItems() throws Exception {
        this.shoppingCart.addItems(Arrays.asList("item1", "item2", "item3"));
        
        Mockito.when(priceList.getItemPrice("item1")).thenReturn(10.0);
        Mockito.when(priceList.getItemPrice("item2")).thenReturn(5.0);
        Mockito.when(priceList.getItemPrice("item3")).thenReturn(1.0);
        Double d = billGenerator.calculateSubTotal(shoppingCart);
        assertThat(d, is(16.0));
    }
    
    @Test
    public void testCalculateSubTotalWithMoreThanOneItems() throws Exception {
        this.shoppingCart.addItems(Arrays.asList("item1", "item1", "item2"));
        Mockito.when(priceList.getItemPrice("item1")).thenReturn(10.0);
        Mockito.when(priceList.getItemPrice("item2")).thenReturn(5.0);
        Double d = billGenerator.calculateSubTotal(shoppingCart);
        assertThat(d, is(25.0));
    }
    
    @Test
    public void totalDiscountWithOutDiscount() throws Exception {
        List<BillDiscount> discounts = Collections.EMPTY_LIST;
        Optional<Double> d= billGenerator.totalDiscount(discounts);
        assertThat(d.isPresent(), is(false));
    }
    
    @Test
    public void totalDiscountWithDiscount() throws Exception {
        List<BillDiscount> discounts = Arrays.asList(
                new BillDiscount("item1", 10.0, 4.0),
                new BillDiscount("item2", 10.0, 1.0)
        );
        Optional<Double> d= billGenerator.totalDiscount(discounts);
        assertThat(d.get(), is(5.0));
    }
}
