package com.paulomlr.ecommerceSystem.domain.enums;

import lombok.Getter;

@Getter
public enum SaleStatus {
    PENDING(1),
    COMPLETED(2),
    CANCELED(3);

    private final Integer number;

    SaleStatus(Integer number) {this.number = number;}
}
