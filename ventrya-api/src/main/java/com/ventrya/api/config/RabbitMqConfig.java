package com.ventrya.api.config;

import org.springframework.amqp.core.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMqConfig {
    public static final String EXCHANGE = "ventrya.events";
    public static final String DEAD_EXCHANGE = "ventrya.events.dlx";
    public static final String SUBMISSION_QUEUE = "ventrya.submission.created";
    public static final String INTERVIEW_QUEUE = "ventrya.interview.completed";
    public static final String SUBMISSION_KEY = "submission.created";
    public static final String INTERVIEW_KEY = "interview.completed";

    @Bean DirectExchange ventryaExchange() { return new DirectExchange(EXCHANGE, true, false); }
    @Bean DirectExchange ventryaDeadExchange() { return new DirectExchange(DEAD_EXCHANGE, true, false); }
    @Bean Queue submissionQueue() { return QueueBuilder.durable(SUBMISSION_QUEUE)
            .deadLetterExchange(DEAD_EXCHANGE).deadLetterRoutingKey(SUBMISSION_QUEUE + ".dead").build(); }
    @Bean Queue interviewQueue() { return QueueBuilder.durable(INTERVIEW_QUEUE)
            .deadLetterExchange(DEAD_EXCHANGE).deadLetterRoutingKey(INTERVIEW_QUEUE + ".dead").build(); }
    @Bean Queue submissionDeadQueue() { return QueueBuilder.durable(SUBMISSION_QUEUE + ".dead").build(); }
    @Bean Queue interviewDeadQueue() { return QueueBuilder.durable(INTERVIEW_QUEUE + ".dead").build(); }
    @Bean Binding submissionBinding(Queue submissionQueue, DirectExchange ventryaExchange) {
        return BindingBuilder.bind(submissionQueue).to(ventryaExchange).with(SUBMISSION_KEY);
    }
    @Bean Binding interviewBinding(Queue interviewQueue, DirectExchange ventryaExchange) {
        return BindingBuilder.bind(interviewQueue).to(ventryaExchange).with(INTERVIEW_KEY);
    }
    @Bean Binding submissionDeadBinding(Queue submissionDeadQueue, DirectExchange ventryaDeadExchange) {
        return BindingBuilder.bind(submissionDeadQueue).to(ventryaDeadExchange).with(SUBMISSION_QUEUE + ".dead");
    }
    @Bean Binding interviewDeadBinding(Queue interviewDeadQueue, DirectExchange ventryaDeadExchange) {
        return BindingBuilder.bind(interviewDeadQueue).to(ventryaDeadExchange).with(INTERVIEW_QUEUE + ".dead");
    }
}
