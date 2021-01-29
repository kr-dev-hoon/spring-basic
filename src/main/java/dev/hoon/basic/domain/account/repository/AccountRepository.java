package dev.hoon.basic.domain.account.repository;

import dev.hoon.basic.domain.account.model.Account;
import dev.hoon.basic.domain.account.model.AccountProjection;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AccountRepository extends JpaRepository<Account, Long> {

    Page<AccountProjection.Simple> findAllBy(Pageable pageable);

    <T> Page<T> findAllBy(Pageable pageable, Class<T> type);

    Optional<AccountProjection.Detail> findDetailById(long id);

    Optional<Account> findByNameIgnoreCase(String name);

    boolean existsByNameIgnoreCase(String name);

    boolean existsByEmailIgnoreCase(String name);

    <T> Optional<T> findById(long id, Class<T> type);

    @Query(" SELECT AC.name as name, AC.email as email, AC.id as id "
            + " FROM Account AC "
            + " WHERE UPPER(AC.name) LIKE CONCAT ('%', UPPER(:name), '%') ")
    <T> List<T> findAllByNameIgnoreCase(String name, Pageable pageable, Class<T> type);

    @Query(" SELECT AC.name as name, AC.email as email, AC.id as id "
            + " FROM Account AC "
            + " WHERE UPPER(AC.email) LIKE CONCAT ('%', UPPER(:email), '%') ")
    <T> List<T> findAllByEmailIgnoreCase(String email, Pageable pageable, Class<T> type);
}
