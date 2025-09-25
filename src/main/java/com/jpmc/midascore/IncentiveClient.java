package com.jpmc.midascore;

import com.jpmc.midascore.foundation.Incentive;
import com.jpmc.midascore.foundation.Transaction;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;


@Component
public class IncentiveClient {

    private final RestTemplate restTemplate;

    public IncentiveClient(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public Incentive fetchIncentive(Transaction transaction) {
        return restTemplate.postForObject(
                "http://localhost:9090/incentive",
                transaction,
                Incentive.class
        );
    }
}
