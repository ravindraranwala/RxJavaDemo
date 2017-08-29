package com.pluralsight.rxjava.dataaccess.server.dto;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Customer extends CustomerRelatedData {
 
    private String username;

    private final List<Address> addresses = new ArrayList<>();
    private final List<OwnedProduct> ownedProducts = new ArrayList<>();
            
    public Customer() {
    }

    public Customer(long id, String username) {
        super(id);
        this.username = username;
    }

    public String getUsername() {
        return username;
    }
    
    public void addAddress(Address a) {
        addresses.add(a);
    }
    
    public List<Address> getAddressList() {
        return Collections.unmodifiableList(addresses);
    }
    
    public void addOwnedProduct(OwnedProduct p) {
        ownedProducts.add(p);
    }
    
    public List<OwnedProduct> getOwnedProductList() {
        return Collections.unmodifiableList(ownedProducts);
    }
}
