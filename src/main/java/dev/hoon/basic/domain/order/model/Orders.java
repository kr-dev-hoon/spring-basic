package dev.hoon.basic.domain.order.model;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import java.io.Serializable;
import java.time.LocalDateTime;

@Entity
@Table(name = "Orders")
@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor
@Getter
public class Orders implements Serializable {

    private static final long serialVersionUID = 2510938647522890831L;

    @Id
    @Column(name = "ID", unique = true, nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "ACCOUNT_ID", nullable = false)
    private long accountId;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "PRODUCT_ID", updatable = false)
    private Product product;

    @Column(name = "ORDER_TICKET", nullable = false, length = 12, unique = true)
    private String orderTicket;

    @Column(name = "CREATED_AT", nullable = false)
    private LocalDateTime createdAt;
}
