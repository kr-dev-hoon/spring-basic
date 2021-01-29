package dev.hoon.basic.domain.account.service;

import dev.hoon.basic.domain.account.dto.AccountSearch;
import dev.hoon.basic.domain.account.dto.RegistryAccount;
import dev.hoon.basic.domain.account.dto.SignIn;
import dev.hoon.basic.domain.account.meta.SearchType;
import dev.hoon.basic.domain.account.model.Account;
import dev.hoon.basic.domain.account.model.AccountProjection;
import dev.hoon.basic.domain.account.repository.AccountRepository;
import dev.hoon.basic.global.util.PageableFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.function.Function;

@Service
public class AccountService {

    private final AccountRepository accountRepository;
    private final PasswordEncoder   passwordEncoder;

    private final Logger logger = LoggerFactory.getLogger(getClass());

    public AccountService(AccountRepository accountRepository,
            PasswordEncoder passwordEncoder) {

        this.accountRepository = accountRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Transactional
    public Account create(RegistryAccount dto) throws Exception {

        if (accountRepository.existsByNameIgnoreCase(dto.getName())) {
            throw new Exception(""); // EXCEPTION HANDLING
        }

        if (accountRepository.existsByEmailIgnoreCase(dto.getEmail())) {
            throw new Exception(""); // EXCEPTION HANDLING
        }

        logger.debug("create >> name : {}, nickname : {}, countryCode : {}, phone : {}, email : {}", dto.getName(),
                dto.getNickName(), dto.getCountryCode(), dto.getPhone(), dto.getEmail());

        return accountRepository.save(makeAccount(dto));

    }

    @Transactional(readOnly = true)
    public boolean isValidSignIn(SignIn signIn) {

        return Optional.ofNullable(signIn.getName())
                .flatMap(accountRepository::findByNameIgnoreCase)
                .filter(actual -> passwordEncoder.matches(signIn.getPassword(), actual.getPassword()))
                .isPresent();

    }

    @Transactional(readOnly = true)
    public List<AccountProjection.Simple> findAll(AccountSearch accountSearch) {

        Pageable pageable = PageableFactory.from(accountSearch);

        return Optional.ofNullable(accountSearch.getSearch())
                .filter(it -> !it.trim().isEmpty())
                .map(findBySearch(accountSearch.getSearchType(), pageable))
                .orElseGet(() -> findAllBy(pageable));

    }

    @Transactional(readOnly = true)
    protected List<AccountProjection.Simple> findAllBy(Pageable pageable) {

        return accountRepository.findAllBy(pageable).toList();
    }

    @Transactional(readOnly = true)
    public <T> List<T> findAllBy(Pageable pageable, Class<T> type) {

        return accountRepository.findAllBy(pageable, type).toList();
    }

    @Transactional(readOnly = true)
    public Optional<Account> findByName(String name) {

        return accountRepository.findByNameIgnoreCase(name);
    }

    @Transactional(readOnly = true)
    protected Function<String, List<AccountProjection.Simple>> findBySearch(SearchType searchType, Pageable pageable) {

        return findBySearch(searchType, AccountProjection.Simple.class, pageable);
    }

    @Transactional(readOnly = true)
    public <T> Function<String, List<T>> findBySearch(SearchType searchType, Class<T> type, Pageable pageable) {

        return search -> (searchType == SearchType.NAME) ?
                accountRepository.findAllByNameIgnoreCase(search, pageable, type) :
                accountRepository.findAllByEmailIgnoreCase(search, pageable, type);
    }

    @Transactional(readOnly = true)
    public Optional<AccountProjection.Detail> findDetailById(long id) {

        return accountRepository.findDetailById(id);
    }

    @Transactional(readOnly = true)
    public <T> Optional<T> findById(long id, Class<T> type) {

        return accountRepository.findById(id, type);
    }

    private Account makeAccount(RegistryAccount dto) {

        return Account.builder()
                .name(dto.getName())
                .nickName(dto.getNickName())
                .email(dto.getEmail())
                .countryCode(dto.getCountryCode())
                .phone(dto.getPhone())
                .sex(dto.getSex())
                .password(passwordEncoder.encode(dto.getPassword()))
                .build();
    }
}
