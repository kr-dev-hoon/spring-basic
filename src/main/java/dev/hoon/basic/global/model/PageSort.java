package dev.hoon.basic.global.model;

import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
@AllArgsConstructor
public class PageSort {

    @Parameter(name = "offset", description = "start page-number of lookup targets", in = ParameterIn.QUERY)
    int offset;

    @Parameter(name = "limit", description = "size per page", in = ParameterIn.QUERY)
    int limit;

    @Parameter(name = "sort", description = "The sorting rules client want to see",
            example = "column:{asc|desc},column:{asc|desc}, ...",
            examples = {
                    @ExampleObject(name = "empty", description = "empty sort", value = ""),
                    @ExampleObject(name = "single", description = "single column sort", value = "createdAt,desc"),
                    @ExampleObject(name = "multiple", description = "multiple column sort", value = "createdAt,desc:count,asc") }, in = ParameterIn.QUERY, ref = "")
    List<String> sort;
}
