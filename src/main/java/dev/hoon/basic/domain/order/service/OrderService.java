package dev.hoon.basic.domain.order.service;

import dev.hoon.basic.domain.account.service.AccountService;
import dev.hoon.basic.domain.order.dto.Account;
import dev.hoon.basic.domain.order.dto.AccountOrders;
import dev.hoon.basic.domain.order.dto.OrderProduct;
import dev.hoon.basic.domain.order.dto.OrderSearch;
import dev.hoon.basic.domain.order.dto.RegistryOrder;
import dev.hoon.basic.domain.order.model.Orders;
import dev.hoon.basic.domain.order.model.OrdersProjection;
import dev.hoon.basic.domain.order.model.Product;
import dev.hoon.basic.domain.order.repository.OrderRepository;
import dev.hoon.basic.global.model.PageSort;
import dev.hoon.basic.global.util.PageableFactory;
import dev.hoon.basic.global.util.TicketUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.groupingBy;

@Service
public class OrderService {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    private final AccountService  accountService;
    private final OrderRepository orderRepository;
    private final ProductService  productService;

    public OrderService(AccountService accountService,
            OrderRepository orderRepository, ProductService productService) {

        this.accountService = accountService;
        this.orderRepository = orderRepository;
        this.productService = productService;
    }

    @Transactional
    public Orders registry(RegistryOrder dto) {

        logger.debug("create >> accountId : {}, productId : {}", dto.getAccountId(), dto.getProductId());

        return orderRepository.save(makeOrders(dto));

    }

    @Transactional
    public Orders makeOrders(RegistryOrder dto) {

        // PACKAGE 종속성 제거.
        Account account = accountService.findById(dto.getAccountId(), Account.class)
                .orElseThrow(() -> new IllegalArgumentException(
                        String.format("makeOrders : Not Found Account Id: %s", dto.getProductId())));

        Product product = productService.findById(dto.getProductId())
                .orElseThrow(() -> new IllegalArgumentException(
                        String.format("makeOrders : Not Found Product Id: %s", dto.getProductId())));

        return Orders.builder()
                .accountId(account.getId())
                .product(product)
                .createdAt(LocalDateTime.now())
                .orderTicket(
                        TicketUtil.getTicket(TicketUtil.TicketLabel.ORDER).getTicketId())
                .build();
    }

    @Transactional(readOnly = true)
    public List<OrdersProjection.Detail> findAllByAccounts(PageSort pageSort) {

        Pageable pageable = PageableFactory.from(pageSort);

        return orderRepository.findAllOrders(pageable).toList();
    }

    @Transactional(readOnly = true)
    public List<OrdersProjection.Detail> findAllByAccount(long accountId) {

        return orderRepository.findDetailAccountId(accountId);
    }

    @Transactional(readOnly = true)
    public List<AccountOrders> findAllByAccountEmailOrName(OrderSearch orderSearch) {

        Pageable accountPageable = PageableFactory.from(orderSearch);
        Pageable orderPageable = PageableFactory.from(new PageSort(0, 1, List.of("createdAt:desc")));

        return Optional.ofNullable(orderSearch.getSearch())
                .filter(it -> !it.isBlank())
                .map(accountService.findBySearch(orderSearch.getSearchType(), Account.class, accountPageable))
                .or(() -> Optional.of(accountService.findAllBy(accountPageable, Account.class)))
                .map(it -> {

                    Long[] ids = it.stream().map(Account::getId).toArray(Long[]::new);

                    List<Orders> orders = orderRepository.findFirstByAccountIdIn(ids, orderPageable);

                    return mergeAccountOrders(it, orders);

                })
                .orElseGet(Collections::emptyList);

    }

    public List<AccountOrders> mergeAccountOrders(List<Account> accountㄴ, List<Orders> orders) {

        Map<Long, List<Orders>> groupedMap = orders.stream()
                .collect(groupingBy(Orders::getAccountId));

        return accountㄴ.stream()
                .map(it -> {

                    List<OrderProduct> orderProducts = groupedMap.getOrDefault(it.getId(), Collections.emptyList())
                            .stream().map(this::convertOrderDto)
                            .collect(Collectors.toList());

                    return AccountOrders.builder()
                            .accountId(it.getId())
                            .accountName(it.getName())
                            .orders(orderProducts)
                            .build();

                }).collect(Collectors.toList());
    }

    private OrderProduct convertOrderDto(Orders order) {

        return OrderProduct.builder()
                .orderId(order.getId())
                .createdAt(order.getCreatedAt())
                .orderTicket(order.getOrderTicket())
                .productId(order.getProduct().getId())
                .productName(order.getProduct().getProductName())
                .price(order.getProduct().getPrice())
                .build();
    }
}
