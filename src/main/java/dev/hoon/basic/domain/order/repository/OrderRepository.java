package dev.hoon.basic.domain.order.repository;

import dev.hoon.basic.domain.order.model.Orders;
import dev.hoon.basic.domain.order.model.OrdersProjection;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Orders, Long> {

    List<Orders> findFirstByAccountIdIn(@Param("accountIds") Long[] accountIds, Pageable pageable);

    @Query(" SELECT a as account, o as orders, o.product as product "
            + " FROM Orders o, Account a "
            + " WHERE o.accountId = a.id ")
    Page<OrdersProjection.Detail> findAllOrders(Pageable pageable);

    @Query(" SELECT a as account, o as orders, o.product as product "
            + " FROM Orders o, Account a "
            + " WHERE a.id = :accountId "
            + " AND o.accountId = a.id ")
    List<OrdersProjection.Detail> findDetailAccountId(long accountId);

}
