package com.pluralsight.rxjava.dataaccess.server.dto;

public class OwnedProduct extends CustomerRelatedData {

    private long productId;
    private String name;

    public OwnedProduct() {
    }

    public OwnedProduct(long customerId, long productId, String name) {
        super(customerId);
        this.productId = productId;
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public long getProductId() {
        return productId;
    }
}
