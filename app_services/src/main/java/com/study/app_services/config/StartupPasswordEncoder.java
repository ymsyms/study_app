package com.study.app_services.config;

import com.study.app_services.entity.User;
import com.study.app_services.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class StartupPasswordEncoder implements CommandLineRunner {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public StartupPasswordEncoder(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void run(String... args) {
        List<User> users = userRepository.findAll();
        for (User u : users) {
            String p = u.getPassword();
            if (p != null && p.startsWith("{noop}")) {
                String raw = p.replace("{noop}", "");
                u.setPassword(passwordEncoder.encode(raw));
                userRepository.save(u);
            }
        }
    }
}
