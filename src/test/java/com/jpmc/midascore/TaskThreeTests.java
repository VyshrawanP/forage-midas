package com.jpmc.midascore;

import com.jpmc.midascore.foundation.Transaction;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.kafka.core.KafkaTemplate;

@SpringBootTest
@DirtiesContext
public class TaskThreeTests {
    static final Logger logger = LoggerFactory.getLogger(TaskThreeTests.class);

    @MockBean
    private KafkaTemplate<String, Transaction> kafkaTemplate; // Mock Kafka

    @Autowired
    private KafkaProducer kafkaProducer;

    @Autowired
    private UserPopulator userPopulator;

    @Autowired
    private FileLoader fileLoader;

    @Test
    void task_three_verifier() throws InterruptedException {
        // Populate users from test file
        userPopulator.populate();

        // Load transactions and send via KafkaProducer (mocked)
        String[] transactionLines = fileLoader.loadStrings("/test_data/mnbvcxz.vbnm");
        for (String transactionLine : transactionLines) {
            kafkaProducer.send(transactionLine);
        }

        // Wait briefly for processing (simulated)
        Thread.sleep(2000);

        // Debug instructions
        logger.info("----------------------------------------------------------");
        logger.info("----------------------------------------------------------");
        logger.info("----------------------------------------------------------");
        logger.info("Use your debugger to check balances after all transactions are processed.");
        logger.info("This test will now exit automatically without hanging.");
    }
}
