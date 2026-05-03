package org.example.cubitor.service;

import lombok.RequiredArgsConstructor;
import org.example.cubitor.entity.SetSet;
import org.example.cubitor.entity.User;
import org.example.cubitor.repository.SetSetRepository;
import org.example.cubitor.repository.UserRepository;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SetSetService {
    private final SetSetRepository setSetRepository;
    private final UserRepository userRepository;


    public void updateSettings(String email, SetSet newSettings) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));


        SetSet settings = setSetRepository.findByUser(user)
                .orElse(new SetSet());


        settings.setBio(newSettings.getBio());
        settings.setTheme(newSettings.getTheme());
        settings.setFavoriteEvent(newSettings.getFavoriteEvent());
        settings.setFont(newSettings.getFont());
        settings.setCelebrationTime(newSettings.getCelebrationTime());
        settings.setConfirmSolveDeletion(newSettings.getConfirmSolveDeletion());
        settings.setCubingGoal(newSettings.getCubingGoal());
        settings.setUseInspection(newSettings.getUseInspection());
        settings.setSaveMinigameSolves(newSettings.getSaveMinigameSolves());
        settings.setWidgetCount(newSettings.getWidgetCount());
        settings.setUser(user);

        setSetRepository.save(settings);
    }

    public SetSet findByUserEmail(String email) {
        User user = userRepository.findByEmail(email).orElse(null);
        if (user == null) return null;


        return setSetRepository.findByUser(user).orElse(null);
    }
}
