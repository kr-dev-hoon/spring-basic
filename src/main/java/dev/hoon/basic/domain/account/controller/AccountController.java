package dev.hoon.basic.domain.account.controller;

import dev.hoon.basic.domain.account.dto.AccountSearch;
import dev.hoon.basic.domain.account.model.AccountProjection;
import dev.hoon.basic.domain.account.service.AccountService;
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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;

@Tag(name = "account", description = "Account 조회를 위한 API Methods / 단건, 전체 조회")
@RestController
@RequestMapping("/account")
public class AccountController {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    private final AccountService accountService;

    public AccountController(AccountService accountService) {

        this.accountService = accountService;
    }

    @GetMapping
    @Operation(summary = "FIND ACCOUNTS", description = "Search(EMAIL.NAME) 기반으로 검색 / Paging", responses = {
            @ApiResponse(description = "Successful Operation", responseCode = "200", content = @Content(mediaType = "application/json", schema = @Schema(implementation = AccountProjection.Simple.class))),
            @ApiResponse(responseCode = "404", description = "Not Found", content = @Content(mediaType = "application/json")),
            @ApiResponse(responseCode = "401", description = "Authentication Failure", content = @Content(schema = @Schema(hidden = true))) })
    private ResponseEntity<List<AccountProjection.Simple>> findAll(
            @Valid AccountSearch accountSearch) {

        return ResponseEntity.ok(accountService.findAll(accountSearch));
    }

    @GetMapping("/{id}")
    @Operation(summary = "FIND BY ACCOUNT ID", description = "Account ID로 Account 상세 정보 조회", responses = {
            @ApiResponse(description = "Successful Operation", responseCode = "200", content = @Content(mediaType = "application/json", schema = @Schema(implementation = AccountProjection.Detail.class))),
            @ApiResponse(responseCode = "404", description = "Not Found", content = @Content(mediaType = "application/json")),
            @ApiResponse(responseCode = "401", description = "Authentication Failure", content = @Content(schema = @Schema(hidden = true))) })
    private ResponseEntity<AccountProjection.Detail> findById(
            @Parameter(name = "id", description = "조회 대상 ID") @PathVariable("id") long id) {

        return ResponseEntity.of(accountService.findDetailById(id));

    }

}
