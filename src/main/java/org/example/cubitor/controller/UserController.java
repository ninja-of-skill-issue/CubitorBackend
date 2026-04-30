package org.example.cubitor.controller;


import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.example.cubitor.entity.User;
import org.example.cubitor.repository.UserRepository;
import org.example.cubitor.service.UserService;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @PostMapping("/update_user")
    public void editUser(@RequestBody User newUser,
                         @AuthenticationPrincipal UserDetails userDetails) {
        // 1. Получаем текущего пользователя из базы
        User user = userRepository.findByEmail(userDetails.getUsername())
                .orElseThrow(() -> new RuntimeException("User not found"));

        // 2. Обновляем обычные поля
        user.setAvatar(newUser.getAvatar());
        user.setElo(newUser.getElo());
        user.setDescription(newUser.getDescription());
        user.setUsername(newUser.getTheActualUsername());
        System.out.println(newUser.getDescription());

        // 3. Обновляем пароль ТОЛЬКО если он пришел в запросе
        if (newUser.getPassword() != null && !newUser.getPassword().isEmpty()) {
            // Обязательно хешируем перед сохранением!
            // passwordEncoder должен быть внедрен через конструктор
            String encodedPassword = passwordEncoder.encode(newUser.getPassword());
            user.setPassword(encodedPassword);
        }

        userRepository.save(user);
    }

}
