package org.example.cubitor.service;

import lombok.RequiredArgsConstructor;
import org.example.cubitor.entity.SetSet;
import org.example.cubitor.entity.User;
import org.example.cubitor.repository.SetSetRepository;
import org.example.cubitor.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SetSetService {
    private final SetSetRepository setSetRepository;
    private final UserRepository userRepository;


    public SetSet save(SetSet setSet) {
        return setSetRepository.save(setSet);
    }

    public void delete(SetSet setSet) {
        setSetRepository.delete(setSet);
    }

    public List<SetSet> findAll() {
        return setSetRepository.findAll();
    }

    public SetSet findByUserEmail(String email) {
        return setSetRepository.findAllByUser(userRepository.findByEmail(email).orElse(null))
                .stream().findFirst().orElse(null);
    }
}
