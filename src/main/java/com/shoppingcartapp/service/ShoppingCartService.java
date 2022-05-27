package com.shoppingcartapp.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * This class represents a shopping basket containing list of items Contains:
 * String(key) -> Item 
 * Long(value) -> Quantity
 * 
 * 
 * @author naimesh.shah
 *
 */
public class ShoppingCartService implements IShoppingCartService {

	private Map<String, Long> itemCount = new HashMap<>();

	@Override
	public void addItems(List<String> items) {
		items.forEach(i -> {
			Long count = itemCount.getOrDefault(i, 0L);
			itemCount.put(i, count + 1L);
		});
	}

	@Override
	public List<String> getItems() {
		return itemCount.keySet().stream().collect(Collectors.toList());
	}

	@Override
	public Long getQuantity(String item) {
		return itemCount.getOrDefault(item, 0L);
	}
	
}
