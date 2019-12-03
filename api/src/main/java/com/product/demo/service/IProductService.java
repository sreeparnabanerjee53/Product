package com.product.demo.service;

import com.product.demo.data.models.product.Product;

import java.util.List;


/**
 * The interface Product service.
 */
public interface IProductService {

    /**
     * Create product given a userId.
     *
     * @param product the product
     * @return product
     */
    Product createProduct(Product product);

    /**
     * Gets product info given an productId.
     *
     * @param productId the product id
     * @return the product
     */
    Product getProduct(String productId);

    /**
     * Update Product.
     *
     * @param product the product
     */
    void updateProduct(Product product);

    /**
     * get all Products
     *
     * @return the product
     */
    List<Product> getProducts();
}
