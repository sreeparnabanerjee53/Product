package com.product.demo.bootstrap;

import com.google.inject.AbstractModule;
import com.google.inject.Provides;
import com.google.inject.Singleton;
import com.product.demo.service.IProductService;
import com.product.demo.service.ProductService;
import io.dropwizard.hibernate.HibernateBundle;
import org.hibernate.SessionFactory;

public class ProductModule extends AbstractModule {

    private ProductConfiguration configuration;
    private final HibernateBundle<ProductConfiguration> bundle;

    public ProductModule(HibernateBundle<ProductConfiguration> bundle, ProductConfiguration config) {
        this.bundle = bundle;
        this.configuration = config;
    }

    @Override
    protected void configure() {
        bind(HibernateBundle.class).toInstance(bundle);
        bind(IProductService.class).to(ProductService.class);
        bind(ProductConfiguration.class).toInstance(configuration);
    }

    @Provides
    @Singleton
    public SessionFactory provideHibernateSessionFactory() {
        return bundle.getSessionFactory();
    }
}
