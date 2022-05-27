package com.shoppingcartapp.service.offer;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;

import java.util.Collections;
import java.util.List;

import static org.hamcrest.collection.IsCollectionWithSize.*;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import com.shoppingcartapp.model.BillDiscount;
import com.shoppingcartapp.model.OfferItem;
import com.shoppingcartapp.model.ProductPriceList;
import com.shoppingcartapp.service.IShoppingCartService;

public class OfferHandlerTest {

	static class TestOfferHandler extends OfferHandler {
        
		@Override
		public void applyOffer(IShoppingCartService shoppingCart, List<BillDiscount> billDiscountResult) {
			billDiscountResult.add(new BillDiscount("name1", 50.0, 1.0));
            if (hasNext()) {
                next.applyNextOffer(shoppingCart, billDiscountResult);
            }
			
		}

		@Override
		public void applyNextOffer(IShoppingCartService shoppingCart, List<BillDiscount> billDiscountResult) {
			billDiscountResult.add(new BillDiscount("name2", 100.0, 0.0));
			if (hasNext()) {
                next.applyNextOffer(shoppingCart, billDiscountResult);
            }
		}
    }
	
	private OfferHandlerProcessor processor;

    @Before
    public void setUp() throws Exception {
        ProductPriceList priceList = Mockito.mock(ProductPriceList.class);
        List<OfferItem> offerItem = Collections.emptyList();
        this.processor = new OfferHandlerProcessor(offerItem,priceList);
    }

    @After
    public void tearDown() throws Exception {
        this.processor = null;
    }

    @Test
    public void testCreateProcessor() throws Exception {
        assertNotNull(processor);
    }

    @Test
    public void testAddHandlerAndApplyOffer() throws Exception {
        processor.addHandler(new TestOfferHandler());
        processor.addHandler(new TestOfferHandler());
        processor.addHandler(new TestOfferHandler());
        List<BillDiscount> result = processor.applyOffer(Mockito.mock(IShoppingCartService.class));
        assertThat(result, hasSize(3));
    }

    @Test
    public void testApplyOfferWithoutHandlers() throws Exception {
        List<BillDiscount> result = processor.applyOffer(Mockito.mock(IShoppingCartService.class));
        assertThat(result, hasSize(0));
    }
    
    @Test
    public void testApplyNextOfferWithoutHandlers() throws Exception {
        List<BillDiscount> result = processor.applyOffer(Mockito.mock(IShoppingCartService.class));
        assertThat(result, hasSize(0));
    }
}
