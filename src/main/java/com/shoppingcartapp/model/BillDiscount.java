package com.shoppingcartapp.model;

/**
 * Represent a discount in the bill
 * 
 * @author naimesh.shah
 *
 */
public class BillDiscount {

	private String name;

	private Double discountPercent;

	private Double discountValue;

	public String getName() {
		return name;
	}

	public Double getDiscountPercent() {
		return discountPercent;
	}

	public Double getDiscountValue() {
		return discountValue;
	}

	public BillDiscount(String name, Double discountPercent, Double discountValue) {
		super();
		this.name = name;
		this.discountPercent = discountPercent;
		this.discountValue = discountValue;
	}

	@Override
	public String toString() {
		return "BillDiscount [name=" + name + ", discountPercent=" + discountPercent + ", discountValue="
				+ discountValue + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((discountPercent == null) ? 0 : discountPercent.hashCode());
		result = prime * result + ((discountValue == null) ? 0 : discountValue.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
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
		BillDiscount other = (BillDiscount) obj;
		if (discountPercent == null) {
			if (other.discountPercent != null)
				return false;
		} else if (!discountPercent.equals(other.discountPercent))
			return false;
		if (discountValue == null) {
			if (other.discountValue != null)
				return false;
		} else if (!discountValue.equals(other.discountValue))
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		return true;
	}

}
