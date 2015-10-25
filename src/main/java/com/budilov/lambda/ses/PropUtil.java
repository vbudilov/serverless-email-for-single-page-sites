package com.budilov.lambda.ses;


import java.util.Properties;

/**
 * Created by Vladimir Budilov on 3/16/15.
 */

public class PropUtil {

    private static Properties properties = new Properties();

    static {
        try {
            properties.load(PropUtil.class.getResourceAsStream("/app.properties"));
        } catch (Exception exc) {
            System.out.println("Couldn't load the properties file: " + exc.getMessage());
        }
    }

    /**
     * First checks if the property is cached and returns it if it is, if not it loads the propperty
     * from the config file.
     *
     * @param key
     * @return
     */
    public static String get(String key) {
        return properties.getProperty(key);
    }

}
