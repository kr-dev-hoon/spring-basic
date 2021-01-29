package dev.hoon.basic.domain.account.dto;

import dev.hoon.basic.domain.account.meta.SearchType;
import dev.hoon.basic.global.model.PageSort;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
public class AccountSearch extends PageSort {

    @Builder(builderMethodName = "accountSearchBuilder")
    public AccountSearch(int offset, int limit, List<String> sort,
            SearchType searchType,
            @Parameter(name = "searchType", description = "name / email", in = ParameterIn.QUERY) String search) {

        super(offset, limit, sort);
        this.searchType = searchType;
        this.search = search;
    }

    private SearchType searchType = SearchType.NAME;
    private String     search;

}
