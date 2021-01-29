package dev.hoon.basic.domain.order.dto;

import dev.hoon.basic.domain.account.meta.SearchType;
import dev.hoon.basic.global.model.PageSort;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
public class OrderSearch extends PageSort {

    @Builder(builderMethodName = "orderSearchBuilder")
    public OrderSearch(int offset, int limit, List<String> sort, String search, SearchType searchType) {

        super(offset, limit, sort);
        this.searchType = searchType;
        this.search = search;
    }

    private SearchType searchType = SearchType.NAME;
    private String     search;

}
