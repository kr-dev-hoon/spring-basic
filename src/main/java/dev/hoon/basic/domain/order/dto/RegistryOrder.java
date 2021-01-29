package dev.hoon.basic.domain.order.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RegistryOrder {

    @NotNull
    private Long accountId;

    @NotNull
    private Long productId;
}
