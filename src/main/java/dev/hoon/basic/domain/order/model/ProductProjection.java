package dev.hoon.basic.domain.order.model;

import java.math.BigDecimal;

public interface ProductProjection {

    interface Simple {

        long getId();

        long getAccountId();

        long getProductId();

        String getProductName();

        BigDecimal getPrice();

    }


}
