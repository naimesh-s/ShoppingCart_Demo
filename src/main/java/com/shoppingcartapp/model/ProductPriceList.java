package com.shoppingcartapp.model;

import java.util.Map;

/**
 * This class represents the map of item to its price i.e. Product price list.
 * 
 * @author naimesh.shah
 *
 */

public class ProductPriceList {

	private Map<String, Double> itemPrice;

	public ProductPriceList(Map<String, Double> itemPrice) {
		this.itemPrice = itemPrice;
	}
	
	public Double getItemPrice(String name) {
		return itemPrice.getOrDefault(name, 0.0);
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((itemPrice == null) ? 0 : itemPrice.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ProductPriceList other = (ProductPriceList) obj;
		if (itemPrice == null) {
			if (other.itemPrice != null)
				return false;
		} else if (!itemPrice.equals(other.itemPrice))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "ProductPriceList [itemPrice=" + itemPrice + "]";
	}
	
}
