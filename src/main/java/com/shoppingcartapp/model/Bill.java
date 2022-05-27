package com.shoppingcartapp.model;

import java.util.List;

/**
 * This class represents total bill object.
 * 
 * @author naimesh.shah
 *
 */
public class Bill {

	private Double subtotal;

	private Double total;

	private List<BillDiscount> billDiscounts;

	public Double getSubtotal() {
		return subtotal;
	}

	public Double getTotal() {
		return total;
	}

	public List<BillDiscount> getBillDiscounts() {
		return billDiscounts;
	}

	public Bill(Double subtotal, Double total, List<BillDiscount> billDiscounts) {
		super();
		this.subtotal = subtotal;
		this.total = total;
		this.billDiscounts = billDiscounts;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((billDiscounts == null) ? 0 : billDiscounts.hashCode());
		result = prime * result + ((subtotal == null) ? 0 : subtotal.hashCode());
		result = prime * result + ((total == null) ? 0 : total.hashCode());
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
		Bill other = (Bill) obj;
		if (billDiscounts == null) {
			if (other.billDiscounts != null)
				return false;
		} else if (!billDiscounts.equals(other.billDiscounts))
			return false;
		if (subtotal == null) {
			if (other.subtotal != null)
				return false;
		} else if (!subtotal.equals(other.subtotal))
			return false;
		if (total == null) {
			if (other.total != null)
				return false;
		} else if (!total.equals(other.total))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Bill [subtotal=" + subtotal + ", total=" + total + ", billDiscounts=" + billDiscounts + "]";
	}

}
