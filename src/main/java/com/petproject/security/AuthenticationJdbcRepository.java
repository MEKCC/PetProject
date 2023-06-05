package com.petproject.security;

import org.springframework.dao.support.DataAccessUtils;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.retry.annotation.Retryable;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import static java.util.Collections.emptyList;
import static java.util.stream.Collectors.toSet;

@Repository
public class AuthenticationJdbcRepository {

    private final NamedParameterJdbcOperations jdbcTemplate;

    public AuthenticationJdbcRepository(final NamedParameterJdbcOperations jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Retryable
    public AuthenticatedAccount fetchAuthenticatedAccount(final Number userId) {
        final String sql = "SELECT u.username, r.rolename role, u.is_active, u.firstname "
            + " FROM user u"
            + " LEFT JOIN roles r"
            + " ON u.id = r.id"
            + " WHERE u.id = :userId";
        final SqlParameterSource parameters = new MapSqlParameterSource("userId", userId);
        final List<AuthenticatedAccount> accounts = jdbcTemplate.query(sql, parameters, (rs, rowNum) -> {
            final List<GrantedAuthority> authorities = new ArrayList<>();
//            if (rs.getBoolean("allow_cpq")) {
//                authorities.add(new SimpleGrantedAuthority(cpqPermission));
//            }
//            if (rs.getBoolean("allow_icb")) {
//                authorities.add(new SimpleGrantedAuthority(icbPermission));
//            }
            final String role = rs.getString("role");
            if (role != null) {
                authorities.add(new SimpleGrantedAuthority(role));
            }
            return AuthenticatedAccount
                .builder()
                .accountNonLocked(rs.getBoolean("is_active"))
                .firstName(rs.getString("firstname"))
                .userName(rs.getString("username"))
                .authorities(authorities)
                .build();
        });
        return DataAccessUtils.requiredSingleResult(accounts);
    }

    private Collection<Integer> getInts(final String groups) {
        return groups == null ? emptyList()
            : Arrays.stream(groups.split(","))
            .map(Integer::parseInt)
            .collect(toSet());
    }

    public Boolean isUserExists(final String userName) {
        final String sql = "SELECT EXISTS("
            + "SELECT *"
            + " FROM user"
            + " WHERE username = :username) ";
        final MapSqlParameterSource parameters = new MapSqlParameterSource("username", userName);
        return jdbcTemplate.queryForObject(sql, parameters, Boolean.class);
    }
}
