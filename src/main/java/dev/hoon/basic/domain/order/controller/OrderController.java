package dev.hoon.basic.domain.order.controller;

import dev.hoon.basic.domain.account.model.AccountProjection;
import dev.hoon.basic.domain.order.dto.AccountOrders;
import dev.hoon.basic.domain.order.dto.OrderSearch;
import dev.hoon.basic.domain.order.dto.RegistryOrder;
import dev.hoon.basic.domain.order.model.Orders;
import dev.hoon.basic.domain.order.model.OrdersProjection;
import dev.hoon.basic.domain.order.service.OrderService;
import dev.hoon.basic.global.model.PageSort;
import dev.hoon.basic.global.model.Response;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;

@Tag(name = "order", description = "Account의 주문을 생성, 조회하기 위한 API Methods")
@RestController
@RequestMapping("/order")
public class OrderController {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    private final OrderService orderService;

    public OrderController(OrderService orderService) {

        this.orderService = orderService;
    }

    @PostMapping
    @Operation(summary = "Find All Orders", description = "Account Paging / Account별 마지막 주문 정보 제공", responses = {
            @ApiResponse(description = "Successful Operation", responseCode = "200", content = @Content(mediaType = "application/json", schema = @Schema(implementation = Long.class))),
            @ApiResponse(responseCode = "404", description = "Not Found", content = @Content(mediaType = "application/json")),
            @ApiResponse(responseCode = "500", description = "Not Found Product Id Or Account Id") })
    private ResponseEntity<Long> create(@Valid @RequestBody RegistryOrder registryOrder) {

        Orders orders = orderService.registry(registryOrder);

        logger.debug("create >> id : {}", orders.getId());

        return ResponseEntity.ok(orders.getId());
    }

    @GetMapping
    @Operation(summary = "Find All Orders", description = "Account Paging / Account별 마지막 주문 정보 제공", responses = {
            @ApiResponse(description = "Successful Operation", responseCode = "200", content = @Content(mediaType = "application/json", schema = @Schema(implementation = AccountProjection.Simple.class))),
            @ApiResponse(responseCode = "404", description = "Not Found", content = @Content(mediaType = "application/json")),
            @ApiResponse(responseCode = "401", description = "Authentication Failure", content = @Content(schema = @Schema(hidden = true))) })
    private ResponseEntity<List<OrdersProjection.Detail>> findAll(@Parameter @Valid PageSort pageSort) {

        List<OrdersProjection.Detail> orderList = orderService.findAllByAccounts(pageSort);

        return Response.of(orderList);
    }

    @GetMapping("/account")
    @Operation(summary = "Find All Orders By Account's Email or Name", description = "Account 기반으로 Search, Paging / Account별 마지막 주문 정보 제공", responses = {
            @ApiResponse(description = "Successful Operation", responseCode = "200", content = @Content(mediaType = "application/json", schema = @Schema(implementation = AccountOrders.class))),
            @ApiResponse(responseCode = "404", description = "Not Found", content = @Content(mediaType = "application/json")),
            @ApiResponse(responseCode = "401", description = "Authentication Failure", content = @Content(schema = @Schema(hidden = true))) })
    private ResponseEntity<List<AccountOrders>> findAllByAccountEmailOrName(OrderSearch orderSearch) {

        return Response.of(orderService.findAllByAccountEmailOrName(orderSearch));
    }

    @GetMapping("/account/{id}")
    @Operation(summary = "Find All Orders By Account Id", description = "단일 Account의 주문 내역 제공", responses = {
            @ApiResponse(description = "Successful Operation", responseCode = "200", content = @Content(mediaType = "application/json", schema = @Schema(implementation = OrdersProjection.Detail.class))),
            @ApiResponse(responseCode = "404", description = "Not Found", content = @Content(mediaType = "application/json")),
            @ApiResponse(responseCode = "401", description = "Authentication Failure", content = @Content(schema = @Schema(hidden = true))) })
    private ResponseEntity<List<OrdersProjection.Detail>> findAllByAccount(@PathVariable("id") long accountId) {

        return Response.of(orderService.findAllByAccount(accountId));
    }

}
