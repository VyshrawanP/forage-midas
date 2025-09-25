package com.jpmc.midascore.controller;

import com.jpmc.midascore.foundation.Balance;
import com.jpmc.midascore.model.User;
import com.jpmc.midascore.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;

@RestController
public class BalanceController {

    @Autowired
    private UserRepository userRepository;

    @GetMapping("/balance")
    public Balance getBalance(@RequestParam String userId) {
        User user = userRepository.findByUserId(userId).orElse(null);

        BigDecimal balance = (user != null) ? user.getBalance() : BigDecimal.ZERO;
        return new Balance(userId, balance);
    }
}
