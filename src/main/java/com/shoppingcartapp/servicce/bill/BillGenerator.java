package com.shoppingcartapp.servicce.bill;

import java.util.List;
import java.util.Optional;

import com.shoppingcartapp.model.Bill;
import com.shoppingcartapp.model.BillDiscount;
import com.shoppingcartapp.model.ProductPriceList;
import com.shoppingcartapp.service.IShoppingCartService;
import com.shoppingcartapp.service.offer.OfferHandlerProcessor;

public class BillGenerator {

	private ProductPriceList priceList;

    private OfferHandlerProcessor processor;
    
    public BillGenerator(ProductPriceList priceList, OfferHandlerProcessor processor) {
		this.priceList = priceList;
		this.processor = processor;
	}

	public Bill generateBill(IShoppingCartService shoppingCart) {
        Double subTotal = calculateSubTotal(shoppingCart);
        List<BillDiscount> billDiscounts = processor.applyOffer(shoppingCart);
        Optional<Double> discount = totalDiscount(billDiscounts);
        Double total = subTotal - discount.orElse(0.0);
        return new Bill(subTotal, total, billDiscounts);
    }
    
    public Double calculateSubTotal(IShoppingCartService shoppingCart) {
        return shoppingCart.getItems().stream()
                .mapToDouble(item -> priceList.getItemPrice(item) *  shoppingCart.getQuantity(item))
                .sum();
    }
    
    public Optional<Double> totalDiscount(List<BillDiscount> discounts) {
        return discounts.stream()
                .map(BillDiscount::getDiscountValue)
                .reduce((d1, d2) -> d1 + d2);
    }
}
