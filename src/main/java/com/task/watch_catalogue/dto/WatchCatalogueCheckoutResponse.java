package com.task.watch_catalogue.dto;

public class WatchCatalogueCheckoutResponse {

    private String price;

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public WatchCatalogueCheckoutResponse(String price) {
        this.price = price;
    }
}
