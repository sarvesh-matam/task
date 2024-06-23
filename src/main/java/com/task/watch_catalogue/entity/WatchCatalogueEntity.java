package com.task.watch_catalogue.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "watch_catalogue")
public class WatchCatalogueEntity implements Comparable<WatchCatalogueEntity>{

    @Id
    private String id;
    private String name;
    private float unitPrice;
    private String discount;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public float getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(float unitPrice) {
        this.unitPrice = unitPrice;
    }

    public String getDiscount() {
        return discount;
    }

    public void setDiscount(String discount) {
        this.discount = discount;
    }


    @Override
    public int compareTo(WatchCatalogueEntity o) {
        return this.id.compareTo(o.id);
    }

    public WatchCatalogueEntity(String id, String name, float unitPrice, String discount) {
        this.id = id;
        this.name = name;
        this.unitPrice = unitPrice;
        this.discount = discount;
    }

    public WatchCatalogueEntity() {
    }
}
