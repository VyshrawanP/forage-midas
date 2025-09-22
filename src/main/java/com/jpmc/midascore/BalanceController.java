package com.jpmc.midascore;


import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.Map;

@RestController
public class BalanceController {

    @GetMapping("/balance")
    public Map<String, Object> balance() {
        return Map.of("balance", 1000);
    }
}

