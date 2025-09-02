package br.com.vendas.config;

import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitConfig {

    public static final String VENDA_QUEUE = "venda.events";

    @Bean
    public Queue vendaQueue() {
        return new Queue(VENDA_QUEUE, true);
    }

}