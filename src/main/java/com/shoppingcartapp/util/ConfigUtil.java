package com.shoppingcartapp.util;


import java.util.List;
import java.util.Map;

import com.shoppingcartapp.model.OfferItem;

/**
 *
 * Interface for configurations
 *
 * @author naimesh.shah
 */
public interface ConfigUtil {

    Map<String, Double> getPrices();

    List<OfferItem> getOffers();

}
