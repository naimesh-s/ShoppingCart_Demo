package com.shoppingcartapp.service.offer;

import java.util.List;

import com.shoppingcartapp.model.BillDiscount;
import com.shoppingcartapp.service.IShoppingCartService;

/**
 * Applying Chain of Responsibility Pattern
 * 
 * Abstract class for an offer handler chain.
 * 
 * @author naimesh.shah
 *
 */
public abstract class OfferHandler {
	
	protected OfferHandler next;

    public void setNextHandler(OfferHandler next) {
        this.next = next;
    }

    public boolean hasNext() {
        return this.next != null;
    }

    public abstract void applyOffer(IShoppingCartService cart, List<BillDiscount> billDiscountResult);

	public abstract void applyNextOffer(IShoppingCartService shoppingCart, List<BillDiscount> billDiscountResult);

}
