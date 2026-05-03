package org.example.cubitor.controller;

import lombok.RequiredArgsConstructor;
import org.example.cubitor.entity.SetSet;
import org.example.cubitor.entity.User;
import org.example.cubitor.repository.SetSetRepository;
import org.example.cubitor.service.SetSetService;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class SetSetController {

    private final SetSetService setSetService;
    private final SetSetRepository setSetRepository;

    @PostMapping("/get_setset")
    public SetSet getSetSet(@AuthenticationPrincipal User user) {
        return setSetService.findByUserEmail(user.getEmail());
    }

    @PostMapping("/set_setset")
    public void setSetSet(@AuthenticationPrincipal User user, @RequestBody SetSet setSet) {
        setSetService.updateSettings(user.getEmail(), setSet);
    }
}
