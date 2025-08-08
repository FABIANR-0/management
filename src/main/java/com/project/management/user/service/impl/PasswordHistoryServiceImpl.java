package com.project.management.user.service.impl;

import com.project.management.user.entity.PasswordHistory;
import com.project.management.user.entity.User;
import com.project.management.user.repository.PasswordHistoryRepository;
import com.project.management.user.service.PasswordHistoryService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;


@Service
public class PasswordHistoryServiceImpl implements PasswordHistoryService {

    private final PasswordEncoder passwordEncoder;

    private final PasswordHistoryRepository passwordHistoryRepository;

    public PasswordHistoryServiceImpl(PasswordEncoder passwordEncoder, PasswordHistoryRepository passwordHistoryRepository) {
        this.passwordEncoder = passwordEncoder;
        this.passwordHistoryRepository = passwordHistoryRepository;
    }

    @Override
    public Boolean validateLastPassword(User user, String requestPassword) {
        List<PasswordHistory> passwordHistories = user.getPasswordHistories();
        passwordHistories = passwordHistories
                .stream()
                .filter(passwordHistory -> passwordEncoder.matches(requestPassword, passwordHistory.getPasswordHistoryValue()))
                .toList();
        return !passwordHistories.isEmpty();
    }

    @Override
    public void addPasswordHistory(String passwordHistory, User user) {
        List<PasswordHistory> passwordHistories = user.getPasswordHistories() != null ? user.getPasswordHistories().stream().sorted(Comparator.comparing(PasswordHistory::getCreatedAt)).collect(Collectors.toList()) : new ArrayList<>();
        if (passwordHistories.size() > 4) {
            passwordHistoryRepository.delete(passwordHistories.removeFirst());
        }
        passwordHistoryRepository.save(PasswordHistory.create(passwordEncoder.encode(passwordHistory), user));
    }
}
