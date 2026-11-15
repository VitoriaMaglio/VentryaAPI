package com.ventrya.api.messaging;

import com.ventrya.api.config.RabbitMqConfig;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;
import org.springframework.transaction.support.TransactionSynchronization;
import org.springframework.transaction.support.TransactionSynchronizationManager;

@Component
@RequiredArgsConstructor
public class DomainEventPublisher {
    private final RabbitTemplate rabbitTemplate;

    public void submissionCreated(Long id) {
        publishAfterCommit(RabbitMqConfig.SUBMISSION_KEY, id);
    }

    public void interviewCompleted(Long id) {
        publishAfterCommit(RabbitMqConfig.INTERVIEW_KEY, id);
    }

    private void publishAfterCommit(String routingKey, Long id) {
        if (!TransactionSynchronizationManager.isActualTransactionActive()
                || !TransactionSynchronizationManager.isSynchronizationActive()) {
            throw new IllegalStateException("Publicação requer transação ativa.");
        }
        TransactionSynchronizationManager.registerSynchronization(new TransactionSynchronization() {
            @Override public void afterCommit() {
                rabbitTemplate.convertAndSend(RabbitMqConfig.EXCHANGE, routingKey, id.toString());
            }
        });
    }
}
