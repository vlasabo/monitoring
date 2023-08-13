package ru.spb.reshenie.monitoring.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.spb.reshenie.monitoring.service.TelegramBot;

/**
 * Created by vladimirsabo on 13.08.2023
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/")
public class IncomingRequestController {
    private final TelegramBot telegramBot;

    @GetMapping("test") //not @PreAuthorize
    public String test(@RequestParam Boolean isOk,
                       @RequestParam Integer errorsCount,
                       @RequestParam String clientName) {
        return "good";
    }
}
