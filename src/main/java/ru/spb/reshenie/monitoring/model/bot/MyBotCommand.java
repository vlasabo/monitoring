package ru.spb.reshenie.monitoring.model.bot;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * Created by vladimirsabo on 13.08.2023
 */

@RequiredArgsConstructor
@Getter
public enum MyBotCommand {
    START("/start", "Запуск бота"),
    REG("/reg", "Регистрация"),
    TEST("/test", "Тест");
    private final String command;
    private final String description;
}
