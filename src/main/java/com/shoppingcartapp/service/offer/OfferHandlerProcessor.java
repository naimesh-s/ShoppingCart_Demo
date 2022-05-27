package com.shoppingcartapp.service.offer;

import java.util.ArrayList;
import java.util.List;

import com.shoppingcartapp.model.BillDiscount;
import com.shoppingcartapp.model.OfferItem;
import com.shoppingcartapp.model.ProductPriceList;
import com.shoppingcartapp.service.IShoppingCartService;

public class OfferHandlerProcessor {

    private OfferHandler firstHandler;

    private OfferHandler lastHandler;

    public OfferHandlerProcessor(List<OfferItem> offerItems, ProductPriceList priceList) {
        OfferHandler offerHandler;
        for (OfferItem offerItem : offerItems) {
            offerHandler = OfferHandlerFactory.createOfferHandler(offerItem, priceList);
            addHandler(offerHandler);
        }
    }

    public void addHandler(OfferHandler offerHandler) {
        if (firstHandler == null) {
            firstHandler = offerHandler;
        } else {
            lastHandler.setNextHandler(offerHandler);
        }
        lastHandler = offerHandler;
    }

    public List<BillDiscount> applyOffer(IShoppingCartService shoppingCart) {
        List<BillDiscount> discountsResults = new ArrayList<>();
        if (firstHandler != null) {
            firstHandler.applyOffer(shoppingCart, discountsResults);
        }
        return discountsResults;
    }

}
