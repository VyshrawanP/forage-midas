package com.jpmc.midascore.listener;

import com.jpmc.midascore.IncentiveClient;
import com.jpmc.midascore.foundation.Incentive;
import com.jpmc.midascore.foundation.Transaction;
import com.jpmc.midascore.model.User;
import com.jpmc.midascore.repository.UserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

@Component
public class TransactionListener {

    private final IncentiveClient incentiveClient;
    private final UserRepository userRepository;

    @Autowired
    public TransactionListener(IncentiveClient incentiveClient, UserRepository userRepository) {
        this.incentiveClient = incentiveClient;
        this.userRepository = userRepository;
    }

    @KafkaListener(topics = "${midas.kafka.transactions-topic}", groupId = "midas-core-group")
    public void receive(Transaction transaction) {
        processTransaction(transaction);
    }

   @Transactional
public void processTransaction(Transaction transaction) {
    // 1. Call incentive API
    Incentive incentive = incentiveClient.fetchIncentive(transaction);
    transaction.setIncentive(incentive.getAmount());

    // 2. Fetch sender + recipient
    User sender = userRepository.findById(transaction.getSenderId()).orElseThrow();
    User recipient = userRepository.findById(transaction.getRecipientId()).orElseThrow();

    // 3. Update balances
    sender.setBalance(sender.getBalance().subtract(BigDecimal.valueOf(transaction.getAmount())));
    recipient.setBalance(
        recipient.getBalance()
                 .add(BigDecimal.valueOf(transaction.getAmount()))
                 .add(BigDecimal.valueOf(transaction.getIncentive()))
    );

    // 4. Print Wilbur's balance only
    if (recipient.getUserId().equalsIgnoreCase("wilbur") || sender.getUserId().equalsIgnoreCase("wilbur")) {
        System.out.println("Wilbur's current balance: " + 
                           (recipient.getUserId().equalsIgnoreCase("wilbur") 
                             ? recipient.getBalance() 
                             : sender.getBalance()));
    }

    // 5. Save back to DB
    userRepository.save(sender);
    userRepository.save(recipient);
}


}
