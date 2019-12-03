package com.product.demo.resource;


import com.codahale.metrics.annotation.Timed;
import com.google.inject.Inject;
import com.product.demo.data.models.product.Product;
import com.product.demo.service.IProductService;
import io.dropwizard.hibernate.UnitOfWork;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpStatus;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Slf4j
@Path("/api/")
@Api(value = "/products", description = "Products")
public class ProductResource {

    IProductService productService;

    @Inject
    public ProductResource(IProductService productService) {
        this.productService = productService;
    }

    @Path("/products")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @ApiOperation(value = "fetch products", notes = "no inputs", response = Product.class, responseContainer = "json")
    @ApiResponses(value = {
            @ApiResponse(code = HttpStatus.SC_OK, message = "Found Results"),
            @ApiResponse(code = HttpStatus.SC_BAD_REQUEST, message = "Bad Request"),
            @ApiResponse(code = HttpStatus.SC_INTERNAL_SERVER_ERROR, message = "Internal Server Error")

    })
    @UnitOfWork
    public Response getProducts() {
        return Response.status(HttpStatus.SC_OK).entity(productService.getProducts()).build();
    }

    @Path("/products/{productId}")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @ApiOperation(value = "Validate productId and fetch product", notes = "accepts valid productId", response = Product.class, responseContainer = "json")
    @ApiResponses(value = {
            @ApiResponse(code = HttpStatus.SC_OK, message = "Validation Successful"),
            @ApiResponse(code = HttpStatus.SC_BAD_REQUEST, message = "Bad Request"),
            @ApiResponse(code = HttpStatus.SC_INTERNAL_SERVER_ERROR, message = "Internal Server Error")

    })
    @UnitOfWork
    public Response getProductByProductId(@PathParam("productId") String productId) {
        return Response.status(HttpStatus.SC_OK).entity(productService.getProduct(productId)).build();
    }

    @Path("products/{productId}")
    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Timed
    @ApiOperation(value = "update product ", notes = "Accept product json", response = Product.class,
            responseContainer = "json")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK. Successful."),
            @ApiResponse(code = 400, message = "Bad Request")

    })
    @UnitOfWork
    public Response updateProduct(@PathParam("productId") String productId, Product product) {
        productService.updateProduct(product);
        return Response.status(HttpStatus.SC_OK).build();
    }

    @UnitOfWork
    @Path("products")
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Timed
    @ApiOperation(value = "create product with name and currentPrice", notes = "Accept product json", response = Product.class,
            responseContainer = "json")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK. Successful."),
            @ApiResponse(code = 400, message = "Bad Request")
    })

    public Response createProduct(Product product) {
        return Response.status(HttpStatus.SC_OK).entity(productService.createProduct(product)).build();
    }

}
