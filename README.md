# Product

How to start the Product application
---

1. Run `mvn clean install` to build your application
2. cd api
3. Build application with `mvn docker:build`
4. Start application with `mvn docker:start`
5. Stop application with `mvn docker:stop`
6. To check that your application is running enter url `http://localhost:9001`

Health Check
---
To see your applications health enter url `http://localhost:9001/healthcheck`

Swagger
---
    http://localhost:9000/swagger

UI link
---
    http://localhost:9000/products/index.html

APIs
---
        GET     /api/products (com.product.demo.resource.ProductResource)
        POST    /api/products (com.product.demo.resource.ProductResource)
        GET     /api/products/{productId} (com.product.demo.resource.ProductResource)
        PUT     /api/products/{productId} (com.product.demo.resource.ProductResource)
        GET     /swagger (io.federecio.dropwizard.swagger.SwaggerResource)
        GET     /swagger.{type:json|yaml} (io.swagger.jaxrs.listing.ApiListingResource)