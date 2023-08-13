package ru.spb.reshenie.monitoring.model.security;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Created by vladimirsabo on 13.08.2023
 */
@AllArgsConstructor
@Getter
public enum Role {
    USER("Доступ 1", Set.of( //todo описание
            Permission.USER_PERMISSION,
            Permission.CLIENT_PERMISSION
    )
    ),

    CLIENT("Доступ 2", Set.of(
            Permission.CLIENT_PERMISSION
    )
    ),

    ADMIN("Администратор",
            Arrays.stream(Permission.values()).collect(Collectors.toSet())
    );

    private final String roleName;
    private final Set<Permission> permissions;

    public Set<SimpleGrantedAuthority> getAuthorities() {
        return getPermissions().stream()
                .map(permission -> new SimpleGrantedAuthority(permission.getPermission()))
                .collect(Collectors.toSet());
    }

}
