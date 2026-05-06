package org.example.cubitor.controller;

import lombok.RequiredArgsConstructor;
import org.example.cubitor.dto.SettingsDTO;
import org.example.cubitor.entity.Settings;
import org.example.cubitor.entity.User;
import org.example.cubitor.service.SettingsService;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class SettingsController {
    private final SettingsService settingsService;

    @PostMapping("/get_setset")
    public Settings getSetSet(@AuthenticationPrincipal User user) {
        return settingsService.findByUserEmail(user.getEmail());
    }

    @PostMapping("/set_setset")
    public void setSetSet(@AuthenticationPrincipal User user, @RequestBody SettingsDTO settings) {
        settingsService.updateSettings(user.getEmail(), settings);
    }
}
