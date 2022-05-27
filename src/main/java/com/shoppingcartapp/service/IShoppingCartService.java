package com.shoppingcartapp.service;

import java.util.List;

public interface IShoppingCartService {
	
	void addItems(List<String> items);
	List<String> getItems();
	Long getQuantity(String item);
}
