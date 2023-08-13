package ru.spb.reshenie.monitoring.model.security;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * Created by vladimirsabo on 13.08.2023
 */
@AllArgsConstructor
@Getter
public enum Permission {
    ADMIN_PERMISSION("ADMIN"),
    USER_PERMISSION("USER"),
    CLIENT_PERMISSION("CLIENT");
    private final String permission;
}
