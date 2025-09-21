package com.jpmc.midascore;   // use your own package

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;
import com.jpmc.midascore.foundation.Transaction;


@Component
public class TransactionListener {

    // Spring will call this method every time a message arrives on the topic
    @KafkaListener(topics = "${midas.kafka.transactions-topic}",groupId = "midas-core-group")
    public void receive(Transaction transaction) {
        
        System.out.println("Amount: " + transaction.getAmount());
    }
}
