package dev.hoon.basic.domain.order.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
@AllArgsConstructor
public class AccountOrders {

    private long accountId;

    private String accountName;

    private List<OrderProduct> orders;
}
