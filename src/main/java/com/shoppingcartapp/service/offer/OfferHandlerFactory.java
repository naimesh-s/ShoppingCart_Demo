package com.shoppingcartapp.service.offer;

import com.shoppingcartapp.model.OfferItem;
import com.shoppingcartapp.model.ProductPriceList;

/**
 * Factory which returns a offer handler.
 * 
 * @author naimesh.shah
 *
 */
public final class OfferHandlerFactory {

	private OfferHandlerFactory() {}
	
	public static OfferHandler createOfferHandler(OfferItem offerItem, ProductPriceList priceList) {
		OfferHandler handler = new SimpleDiscountOfferHandler(offerItem, priceList);
        return handler;
    }
}
