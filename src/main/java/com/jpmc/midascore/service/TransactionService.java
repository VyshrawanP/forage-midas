package com.jpmc.midascore.service;

import com.jpmc.midascore.Transaction;
import com.jpmc.midascore.model.User;
import com.jpmc.midascore.model.TransactionRecord;
import com.jpmc.midascore.repository.UserRepository;


import com.jpmc.midascore.repository.TransactionRecordRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.Instant;

@Service
public class TransactionService {
    private final UserRepository userRepository;
    private final TransactionRecordRepository recordRepo;

    public TransactionService(UserRepository userRepository, TransactionRecordRepository recordRepo) {
        this.userRepository = userRepository;
        this.recordRepo = recordRepo;
    }

    @Transactional
    public void handleTransaction(Transaction tx) {
        User sender = userRepository.findByUserId(tx.getSenderId())
                .orElseThrow(() -> new IllegalArgumentException("Invalid sender"));
        User recipient = userRepository.findByUserId(tx.getRecipientId())
                .orElseThrow(() -> new IllegalArgumentException("Invalid recipient"));

        if (sender.getBalance().compareTo(tx.getAmount()) >= 0) {
            sender.setBalance(sender.getBalance().subtract(tx.getAmount()));
            recipient.setBalance(recipient.getBalance().add(tx.getAmount()));

            TransactionRecord record = new TransactionRecord();
            record.setSender(sender);
            record.setRecipient(recipient);
            record.setAmount(tx.getAmount());
            record.setTimestamp(Instant.now());

            recordRepo.save(record);
            userRepository.save(sender);
            userRepository.save(recipient);
        }
    }
}
