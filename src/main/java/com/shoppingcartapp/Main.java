package com.shoppingcartapp;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import org.apache.commons.io.IOUtils;

import com.shoppingcartapp.model.Bill;
import com.shoppingcartapp.model.ProductPriceList;
import com.shoppingcartapp.servicce.bill.BillGenerator;
import com.shoppingcartapp.service.IShoppingCartService;
import com.shoppingcartapp.service.ShoppingCartService;
import com.shoppingcartapp.service.offer.OfferHandlerProcessor;
import com.shoppingcartapp.util.ConfigUtil;
import com.shoppingcartapp.util.ConfigUtilFactory;

public class Main {

	public static final String CONFIG_FILE_NAME = "data.json";
	
	public static final String ENCODING = "UTF-8";
	
	public static void main(String[] args) {
		
		//Chosen item list to the cart
		IShoppingCartService shoppingCart = getShoppingCart(Arrays.asList("A", "A", "B", "B", "B", "X", "Y", "Y", "R"));
		
		ConfigUtil configUtil = null;
		
		try {
            configUtil = getConfig(CONFIG_FILE_NAME);
        } catch (Exception e) {
            System.err.println("ERROR IN READING THE CONFIG FILE");
            System.exit(-1);
        }
		
		BillGenerator billGenerator = getBillGenerator(configUtil);
		Bill finalBill = billGenerator.generateBill(shoppingCart);
		System.out.println("Final Bill Amount to be Paid After Discount = "+finalBill.getTotal()+"$");
	}
	
	public static IShoppingCartService getShoppingCart(List<String> choosenItems) {
		IShoppingCartService shoppingCartService = new ShoppingCartService();
		shoppingCartService.addItems(choosenItems);
		return shoppingCartService;
		
	}
	
	public static BillGenerator getBillGenerator(ConfigUtil configUtil) {
        ProductPriceList priceList = new ProductPriceList(configUtil.getPrices());
        OfferHandlerProcessor offerHandlerProcessor = new OfferHandlerProcessor(configUtil.getOffers(), priceList);
        return new BillGenerator(priceList, offerHandlerProcessor);
    }
	
	public static ConfigUtil getConfig(String resource) throws IOException {
        //Get prices and discounts from resources folder
        String data = IOUtils.toString(Main.class.getClassLoader().getResource(resource), ENCODING);
        return ConfigUtilFactory.createConfigUtil(data);
    }

}
