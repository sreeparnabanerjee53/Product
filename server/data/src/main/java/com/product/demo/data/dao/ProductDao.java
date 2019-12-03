package com.product.demo.data.dao;


import com.product.demo.data.models.product.ProductDO;
import io.dropwizard.hibernate.AbstractDAO;
import org.hibernate.SessionFactory;

import javax.inject.Inject;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;


public class ProductDao extends AbstractDAO<ProductDO> {

    @Inject
    public ProductDao(SessionFactory sessionFactory) {
        super(sessionFactory);
    }

    public void save(ProductDO productDO) {
        currentSession().save(productDO);
    }

    public ProductDO update(ProductDO entity) {
        return this.persist(entity);
    }

    public ProductDO getProduct(String productId) {
        CriteriaBuilder builder = currentSession().getCriteriaBuilder();
        CriteriaQuery<ProductDO> criteria = builder.createQuery(ProductDO.class);
        Root<ProductDO> callLegRoot = criteria.from(ProductDO.class);
        criteria.select(callLegRoot);
        criteria.where(builder.equal(callLegRoot.get("id"), productId));
        ProductDO productDO = currentSession().createQuery(criteria).getSingleResult();
        return productDO;
    }

    public List<ProductDO> getProducts() {
        CriteriaBuilder builder = currentSession().getCriteriaBuilder();
        CriteriaQuery<ProductDO> criteria = builder.createQuery(ProductDO.class);
        Root<ProductDO> callLegRoot = criteria.from(ProductDO.class);
        criteria.select(callLegRoot);
        criteria.orderBy(builder.desc(callLegRoot.get("lastUpdated")));
        List<ProductDO> resultList = currentSession().createQuery(criteria).getResultList();
        return resultList;
    }
}
