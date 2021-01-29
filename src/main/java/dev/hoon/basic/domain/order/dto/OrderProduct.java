package dev.hoon.basic.domain.order.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Builder
@AllArgsConstructor
public class OrderProduct {

    private long          orderId;
    private String        orderTicket;
    private long          productId;
    private BigDecimal    price;
    private String        productName;
    private LocalDateTime createdAt;
}
