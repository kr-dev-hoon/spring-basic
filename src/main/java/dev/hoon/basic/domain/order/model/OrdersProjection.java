package dev.hoon.basic.domain.order.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import dev.hoon.basic.domain.account.model.Account;
import org.springframework.data.web.ProjectedPayload;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@ProjectedPayload
public interface OrdersProjection {

    interface Detail {

        default long getOrdersBy() {
            return getAccount().getId();
        }

        default String getNameOrdersBy() {
            return getAccount().getName();
        }

        default long getId() {
            return getOrders().getId();
        }

        default long getProductId() {
            return getProduct().getId();
        }

        default String getOrderTicket() {

            return getOrders().getOrderTicket();
        }

        default LocalDateTime getCreatedAt() {

            return getOrders().getCreatedAt();
        }

        default String getProductName() {

            return getProduct().getProductName();
        }

        default BigDecimal getPrice() {

            return getProduct().getPrice();
        }

        @JsonIgnore
        Orders getOrders();

        @JsonIgnore
        Product getProduct();

        @JsonIgnore
        Account getAccount();

    }
}
