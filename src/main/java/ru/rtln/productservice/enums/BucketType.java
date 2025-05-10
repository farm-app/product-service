package ru.rtln.productservice.enums;

import lombok.Getter;

@Getter
public enum BucketType {

    PRODUCT_PICTURE("product-pictures-bucket");

    private final String name;

    BucketType(String name) {
        this.name = name;
    }
}
