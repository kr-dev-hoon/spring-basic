package dev.hoon.basic.global.util;

import dev.hoon.basic.global.model.PageSort;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static java.util.stream.Collectors.toList;

public class PageableFactory {

    public static Pageable from(PageSort pageSort) {

        return PageRequest.of(pageSort.getOffset(),
                pageSort.getLimit(),
                Sort.by(parseOrders(pageSort.getSort())));

    }

    private static List<Sort.Order> parseOrders(List<String> sort) {

        return Optional.ofNullable(sort)
                .map(str -> sort.stream()
                        .map(it -> it.split(":"))
                        .map(sortOrder -> new Sort.Order(Sort.Direction.fromString(sortOrder[1]), sortOrder[0]))
                        .collect(toList()))
                .orElseGet(Collections::emptyList);

    }
}
