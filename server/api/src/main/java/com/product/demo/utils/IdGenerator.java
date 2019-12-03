package com.product.demo.utils;

import java.util.UUID;


/**
 * The type Id generator is generating unique Ids.
 */
public class IdGenerator {

    /**
     * Gets new product id.
     *
     * @return the new product id
     */
    public static String getNewProductId() {
        return new StringBuilder("P_").append(UUID.randomUUID()).toString();
    }

}
