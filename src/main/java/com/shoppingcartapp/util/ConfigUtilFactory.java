package com.shoppingcartapp.util;


/**
 *
 * Factory which returns a configUtil
 *
 * @author naimesh.shah
 */
public final class ConfigUtilFactory {

    private ConfigUtilFactory() {}

    public static ConfigUtil createConfigUtil(String data) {
        return new ConfigParserUtil(data);
    }

}
