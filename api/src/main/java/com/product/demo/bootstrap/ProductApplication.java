package com.product.demo.bootstrap;

import com.codahale.metrics.jmx.JmxReporter;
import com.fasterxml.jackson.jaxrs.json.JacksonJaxbJsonProvider;
import com.google.common.collect.ImmutableList;
import com.google.inject.Guice;
import com.google.inject.Injector;
import com.product.demo.utils.ObjectMapperUtil;
import io.dropwizard.Application;
import io.dropwizard.assets.AssetsBundle;
import io.dropwizard.configuration.EnvironmentVariableSubstitutor;
import io.dropwizard.configuration.SubstitutingSourceProvider;
import io.dropwizard.db.DataSourceFactory;
import io.dropwizard.hibernate.HibernateBundle;
import io.dropwizard.hibernate.SessionFactoryFactory;
import io.dropwizard.jersey.jackson.JsonProcessingExceptionMapper;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;
import io.federecio.dropwizard.swagger.SwaggerBundle;
import io.federecio.dropwizard.swagger.SwaggerBundleConfiguration;
import org.eclipse.jetty.servlets.CrossOriginFilter;
import org.reflections.Reflections;

import javax.persistence.Entity;
import javax.servlet.DispatcherType;
import javax.servlet.FilterRegistration;
import javax.ws.rs.Path;
import javax.ws.rs.ext.ExceptionMapper;
import java.util.EnumSet;
import java.util.Set;

public class ProductApplication extends Application<ProductConfiguration> {

    private static final Reflections reflections = new Reflections("com.product.demo");
    private static final String DATETIME_FORMAT = "yyyy-MM-dd HH:mm:ss";


    public static void main(String[] args) throws Exception {
        new ProductApplication().run(args);
    }

    private final HibernateBundle<ProductConfiguration> hibernate = new HibernateBundle<ProductConfiguration>(
            (ImmutableList) ImmutableList.builder().addAll(reflections.getTypesAnnotatedWith(Entity.class)).build(),
            new SessionFactoryFactory()) {
        @Override
        public DataSourceFactory getDataSourceFactory(ProductConfiguration productConfiguration) {
            return productConfiguration.getDatabase();
        }
    };

    @Override
    public void initialize(Bootstrap<ProductConfiguration> bootstrap) {
        bootstrap.addBundle(new SwaggerBundle<ProductConfiguration>() {
            @Override
            protected SwaggerBundleConfiguration getSwaggerBundleConfiguration(ProductConfiguration configuration) {
                return configuration.swaggerBundleConfiguration;
            }
        });
        bootstrap.addBundle(new AssetsBundle("/assets/ajax", "/products", null, "ajax"));
        bootstrap.addBundle(hibernate);
        bootstrap.setConfigurationSourceProvider(
                new SubstitutingSourceProvider(
                        bootstrap.getConfigurationSourceProvider(),
                        new EnvironmentVariableSubstitutor(false))
        );
        super.initialize(bootstrap);
    }

    private void configureCors(Environment environment) {
        final FilterRegistration.Dynamic filter = environment.servlets().addFilter("CORS",
                CrossOriginFilter.class);
        filter.addMappingForUrlPatterns(EnumSet.allOf(DispatcherType.class), true, "/*");
        filter.setInitParameter(CrossOriginFilter.ALLOWED_METHODS_PARAM, "GET,PUT,POST,DELETE,OPTIONS");
        filter.setInitParameter(CrossOriginFilter.ALLOWED_ORIGINS_PARAM, "*");
        filter.setInitParameter(CrossOriginFilter.ACCESS_CONTROL_ALLOW_ORIGIN_HEADER, "*");
        filter.setInitParameter("allowedHeaders",
                "Content-Type,Authorization,X-Requested-With,Content-Length,Accept,Origin");
        filter.setInitParameter("allowCredentials", "true");
    }

    @Override
    public void run(ProductConfiguration config, Environment environment) throws Exception {
        JacksonJaxbJsonProvider provider = new JacksonJaxbJsonProvider();
        provider.setMapper(ObjectMapperUtil.getObjectMapper());
        environment.jersey().register(provider);
        environment.jersey().register(new JsonProcessingExceptionMapper(true));
        Injector injector = Guice.createInjector(new ProductModule(hibernate, config));
        addAppResources(environment, injector);
        JmxReporter.forRegistry(environment.metrics()).build().start();
        addExceptionMappers(environment, injector);
        configureCors(environment);
    }

    private void addAppResources(Environment environment, Injector injector) {
        Set<Class<?>> resourceClasses = reflections.getTypesAnnotatedWith(Path.class);
        for (Class<?> aClass : resourceClasses) {
            environment.jersey().register(injector.getInstance(aClass));
        }
    }

    private void addExceptionMappers(Environment environment, Injector injector) {
        Set<Class<? extends ExceptionMapper>> exceptionMapperClasses = reflections.getSubTypesOf(ExceptionMapper.class);
        for (Class<? extends ExceptionMapper> aClass : exceptionMapperClasses) {
            environment.jersey().getResourceConfig().register(injector.getInstance(aClass));
        }
    }
}
