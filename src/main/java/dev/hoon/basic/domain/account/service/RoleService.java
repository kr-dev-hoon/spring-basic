package dev.hoon.basic.domain.account.service;

import dev.hoon.basic.domain.account.meta.Role;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * USER는 현재 별도의 ROLE이 없으므로, DEFAULT 설정 제공.
 */
@Service
public class RoleService {

    public List<Role> findByAccountName(String name) {
        return List.of(Role.USER);
    }

}
