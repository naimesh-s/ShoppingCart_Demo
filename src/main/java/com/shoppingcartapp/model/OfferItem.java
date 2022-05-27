package com.shoppingcartapp.model;

/**
 * This class represents a rule of offer applied to the shopping cart
 * 
 * @author naimesh.shah
 *
 */
public class OfferItem {

	private String name;

	private int quantity;

	private DiscountItem discountItem;

	public OfferItem(String name, int quantity, DiscountItem discountItem) {
		this.name = name;
		this.quantity = quantity;
		this.discountItem = discountItem;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	public DiscountItem getDiscountItem() {
		return discountItem;
	}

	public void setDiscountItem(DiscountItem discountItem) {
		this.discountItem = discountItem;
	}

	@Override
	public String toString() {
		return "OfferItem [name=" + name + ", quantity=" + quantity + ", discountItem=" + discountItem + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((discountItem == null) ? 0 : discountItem.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + quantity;
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
		OfferItem other = (OfferItem) obj;
		if (discountItem == null) {
			if (other.discountItem != null)
				return false;
		} else if (!discountItem.equals(other.discountItem))
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (quantity != other.quantity)
			return false;
		return true;
	}

}
