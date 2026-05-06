package org.example.cubitor.service;

import lombok.RequiredArgsConstructor;
import org.example.cubitor.dto.SettingsDTO;
import org.example.cubitor.entity.Settings;
import org.example.cubitor.entity.User;
import org.example.cubitor.repository.SettingsRepository;
import org.example.cubitor.repository.UserRepository;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SettingsService {
    private final SettingsRepository settingsRepository;
    private final UserRepository userRepository;
    private final DTOService dtoService;


    public void updateSettings(String email, SettingsDTO newSettings) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));


        Settings settings = settingsRepository.findByUser(user)
                .orElse(dtoService.toEntity(newSettings));

        settingsRepository.save(settings);
    }

    public Settings findByUserEmail(String email) {
        User user = userRepository.findByEmail(email).orElse(null);

        return settingsRepository.findByUser(user).orElse(null);
    }
}
