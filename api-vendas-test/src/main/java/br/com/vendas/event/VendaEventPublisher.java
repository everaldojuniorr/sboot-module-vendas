package br.com.vendas.event;

import org.springframework.amqp.rabbit.core.RabbitTemplate;

public class VendaEventPublisher {

    private final RabbitTemplate rabbitTemplate;

    public VendaEventPublisher(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    public void publicarCompraEfetuada(Long vendaId) {
        rabbitTemplate.convertAndSend("venda.events", "CompraEfetuada:" + vendaId);
    }

}
