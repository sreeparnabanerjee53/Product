package com.product.demo.service;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.product.demo.data.dao.ProductDao;
import com.product.demo.data.mapper.ProductMapper;
import com.product.demo.data.models.product.Product;
import com.product.demo.data.models.product.ProductDO;
import com.product.demo.data.models.product.exception.ProductCreationFailed;
import com.product.demo.data.models.product.exception.ProductNotExistException;

import javax.ws.rs.core.Response;
import java.time.LocalDateTime;
import java.util.List;


@Singleton
public class ProductService implements IProductService {

    private ProductDao productDao;
    private ProductMapper productMapper;

    @Inject
    public ProductService(ProductDao productDao,
                          ProductMapper productMapper) {
        this.productDao = productDao;
        this.productMapper = productMapper;
    }

    @Override
    public Product createProduct(Product product) {
        ProductDO productDO = productMapper.mapRequest(product);
        productDO.setCreatedAt(LocalDateTime.now());
        productDO.setLastUpdated(LocalDateTime.now());
        productDao.save(productDO);
        return productMapper.mapResponse(productDO).orElseThrow(() -> new ProductCreationFailed(Response.Status.BAD_REQUEST, product.getName()));
    }

    @Override
    public Product getProduct(String productId) {
        return productMapper.mapResponse(productDao.getProduct(productId)).orElseThrow(() -> new ProductNotExistException(Response.Status.BAD_REQUEST, productId));
    }

    @Override
    public void updateProduct(Product product) {
        ProductDO productDO = productDao.getProduct(product.getId());
        productDO = productMapper.mapRequest(product, productDO);
        productDao.update(productDO);
    }

    @Override
    public List<Product> getProducts() {
        return productMapper.mapResponseList(productDao.getProducts());
    }


}
