package com.shoppingcartapp.service.offer;

import java.util.List;

import com.shoppingcartapp.model.BillDiscount;
import com.shoppingcartapp.model.DiscountItem;
import com.shoppingcartapp.model.OfferItem;
import com.shoppingcartapp.model.ProductPriceList;
import com.shoppingcartapp.service.IShoppingCartService;

import lombok.AccessLevel;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

/**
 * Chain which applies Offer discount.
 * 
 * @author naimesh.shah
 *
 */

@Data
@Getter(AccessLevel.NONE)
@Setter(AccessLevel.NONE)
public class SimpleDiscountOfferHandler extends OfferHandler{

	private OfferItem offerItem;

    private ProductPriceList priceList;
    	
    public SimpleDiscountOfferHandler(OfferItem offerItem, ProductPriceList priceList) {
    	this.offerItem = offerItem;
    	this.priceList = priceList;
    }
    
    /**
     * This method is used to handle the Offer 1 - Buy A and get B for half price. Half price is applied to B items based on the number of A items.
     */
	@Override
	public void applyOffer(IShoppingCartService shoppingCart, List<BillDiscount> billDiscountResult) {
		long itemQuantity = shoppingCart.getQuantity(offerItem.getName());

		Long targetQuantity = shoppingCart.getQuantity(offerItem.getDiscountItem().getName());

		if (itemQuantity > 0 && targetQuantity > 0 && targetQuantity >= itemQuantity) {
			
			BillDiscount billDiscount = proccessDiscount((targetQuantity + itemQuantity) - targetQuantity);
			billDiscountResult.add(billDiscount);
		}

		if (hasNext()) {
			next.applyNextOffer(shoppingCart, billDiscountResult);
		}
	}
	
	/**
	 * This method is used to handle the offer 2 -Buy any 3 items from a set of products {X, Y, Z, P, Q} and get the cheapest one for free.
	 */
	@Override
	public void applyNextOffer(IShoppingCartService shoppingCart, List<BillDiscount> billDiscountResult) {
		
		Long itemQuantity = shoppingCart.getQuantity(offerItem.getName()); //X=1 --> 10$

		Long targetQuantity = shoppingCart.getQuantity(offerItem.getDiscountItem().getName()); // Y=2 --> 9$
		
		Long totalQuantity = itemQuantity + targetQuantity;
		if (totalQuantity >= 3) {
			if(priceList.getItemPrice(offerItem.getName()) > priceList.getItemPrice(offerItem.getDiscountItem().getName())){
				targetQuantity = targetQuantity - 1;
				Double discountedValue = (targetQuantity * priceList.getItemPrice(offerItem.getDiscountItem().getName()) * (offerItem.getDiscountItem().getDiscountPercent()))/100;
				billDiscountResult.add(new BillDiscount(offerItem.getDiscountItem().getName(), offerItem.getDiscountItem().getDiscountPercent(), discountedValue));
			}else {
				itemQuantity = itemQuantity - 1;
				Double discountedValue = (itemQuantity * priceList.getItemPrice(offerItem.getName()) * (offerItem.getDiscountItem().getDiscountPercent()))/100;
				billDiscountResult.add(new BillDiscount(offerItem.getDiscountItem().getName(), offerItem.getDiscountItem().getDiscountPercent(), discountedValue));
			}
		}
		
		if(hasNext()) {
			next.applyNextOffer(shoppingCart, billDiscountResult);
		}
	}
	
	public BillDiscount proccessDiscount(Long itemQuantity) {
        DiscountItem discountItem = offerItem.getDiscountItem();
        Double discountedItemPrice = priceList.getItemPrice(discountItem.getName());

        Double discountValue = calculateDiscount(itemQuantity, discountedItemPrice, discountItem.getDiscountPercent());
        return new BillDiscount(discountItem.getName(), discountItem.getDiscountPercent(), discountValue);
    }

    public static Double calculateDiscount(long quantity, Double itemPrice, Double discountPercent) {
        return (quantity * itemPrice * discountPercent) / 100;
    }

	

}
