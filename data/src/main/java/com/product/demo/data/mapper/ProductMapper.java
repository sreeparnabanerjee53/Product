package com.product.demo.data.mapper;

import com.product.demo.data.models.product.Product;
import com.product.demo.data.models.product.ProductDO;

import javax.annotation.Nonnull;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ProductMapper implements ObjectMapper<Product, ProductDO> {

    @Override
    public ProductDO mapRequest(Product product) {
        if (product == null) {
            return null;
        }
        final ProductDO productDO = new ProductDO();
        productDO.setId(product.getId());
        productDO.setLastUpdated(LocalDateTime.now());
        return mapRequest(product, productDO);
    }

    @Override
    public ProductDO mapRequest(final Product product, @Nonnull final ProductDO productDO) {
        if (product == null) {
            return productDO;
        }
        productDO.setName(product.getName());
        productDO.setCurrentPrice(product.getCurrentPrice());
        return productDO;
    }

    @Override
    public Optional<Product> mapResponse(ProductDO productDO) {
        if (productDO == null)
            return Optional.empty();
        Product product = new Product();
        product.setId(productDO.getId());
        product.setName(productDO.getName());
        product.setCurrentPrice(productDO.getCurrentPrice());
        product.setLastUpdated(productDO.getLastUpdated());

        return Optional.of(product);
    }

    @Override
    public List<ProductDO> mapRequestList(List<Product> products) {
        if (products == null || products.isEmpty())
            return null;
        List<ProductDO> productDOS = new ArrayList<>();
        for (Product product : products) {
            ProductDO productDO = mapRequest(product);
            productDOS.add(productDO);
        }
        return productDOS;
    }

    @Override
    public List<Product> mapResponseList(List<ProductDO> productDOS) {
        if (productDOS == null || productDOS.isEmpty())
            return null;
        List<Product> products = new ArrayList<>();
        for (ProductDO productDO : productDOS) {
            Optional<Product> accountDetail = mapResponse(productDO);
            if (accountDetail.isPresent())
                products.add(accountDetail.get());
        }
        return products;
    }
}