package com.shoppingcartapp.util;

import static org.hamcrest.CoreMatchers.hasItems;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;

import java.util.List;
import java.util.Map;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.shoppingcartapp.model.DiscountItem;
import com.shoppingcartapp.model.OfferItem;

public class ConfigParserUnitTest {
	
	private static String JSON_TEST_DATA = "{\n" +
            "  \"items\": [\n" +
            "    {\"name\": \"A\", \"price\": 0.65},\n" +
            "    {\"name\": \"B\", \"price\": 0.80},\n" +
            "    {\"name\": \"X\", \"price\": 1.30},\n" +
            "    {\"name\": \"Y\", \"price\": 1.00}\n" +
            "  ],\n" +
            "  \"offerItem\": [\n" +
            "    {\n" +
            "      \"name\": \"A\",\n" +
            "      \"quantity\": 1,\n" +
            "      \"discountItem\": {\n" +
            "        \"name\": \"B\",\n" +
            "        \"discountPercent\": 50\n" +
            "      }\n" +
            "    },\n" +
            "    {\n" +
            "      \"name\": \"X\",\n" +
            "      \"quantity\": 1,\n" +
            "      \"discountItem\": {\n" +
            "        \"name\": \"Y\",\n" +
            "        \"discountPercent\": 100\n" +
            "      }\n" +
            "    }\n" +
            "  ]\n" +
            "}";
	
	
	private ConfigParserUtil configParserUtil;

    @Before
    public void setUp() throws Exception {
        this.configParserUtil = new ConfigParserUtil(JSON_TEST_DATA);
    }

    @After
    public void tearDown() throws Exception {
        this.configParserUtil = null;
    }
    
    
    @Test
    public void testCreateConfigParseWithData() throws Exception {
        assertNotNull(configParserUtil);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testCreateConfigParseWithoutData() throws Exception {
        assertNotNull(new ConfigParserUtil(null));
    }

    @Test
    public void testCreateConfigParseWithInvalidData() throws Exception {
        assertNotNull(new ConfigParserUtil("{\"novalid\":\"novalid\"}"));
    }
    
    @Test
    public void testGetPricesWithInvalidData() throws Exception {
        configParserUtil = new ConfigParserUtil("{\"novalid\":\"novalid\"}");

        Map<String, Double> priceList = configParserUtil.getPrices();
        assertThat(priceList.size(), is(0));
    }
    
    @Test
    public void testGetPricesHasCorrectSize() throws Exception {
        Map<String, Double> priceList = configParserUtil.getPrices();
        assertThat(priceList.size(), is(4));
    }
    
    @Test
    public void testGetPriceListReturnPrices() throws Exception {
        Map<String, Double> priceList = configParserUtil.getPrices();

        assertThat(priceList.get("A"), is(0.65));
        assertThat(priceList.get("B"), is(0.80));
        assertThat(priceList.get("X"), is(1.30));
        assertThat(priceList.get("Y"), is(1.00));
    }
    
    @Test
    public void testGetOffersWithInvalidData() throws Exception {
        configParserUtil = new ConfigParserUtil("{\"novalid\":\"novalid\"}");

        Map<String, Double> priceList = configParserUtil.getPrices();
        assertThat(priceList.size(), is(0));
    }

    @Test
    public void testGetOffersReturnNotEmpty() throws Exception {
        List<OfferItem> offers = configParserUtil.getOffers();
        assertThat(offers, not(hasSize(0)));
    }

    @Test
    public void testGetOffersHasCorrectSize() throws Exception {
        List<OfferItem> offers = configParserUtil.getOffers();
        assertThat(offers, hasSize(2));
    }

    @Test
    public void testGetOffersReturnDiscount() throws Exception {
        List<OfferItem> offers = configParserUtil.getOffers();

        DiscountItem discountB = new DiscountItem("B", 50.00);
        OfferItem offerB = new OfferItem("A",1,discountB);

        DiscountItem discountA = new DiscountItem("Y", 100.00);
        OfferItem offerA = new OfferItem("X",1,discountA);

        assertThat(offers, hasItems(offerB,offerA));
    }
}
