package dev.hoon.basic.domain.order.dto;

/**
 * https://www.baeldung.com/spring-data-jpa-projections
 * Account Domain쪽의 Entity를 사용하지 않고, 해당 DTO로 전달받아야한다.
 */
public interface Account {

    long getId();

    String getName();

    String getEmail();

}
