package br.com.vendas.event;

import br.com.vendas.service.VendaService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

public class VendaEventListener {

    private static final Logger log = LoggerFactory.getLogger(VendaEventListener.class);

    @RabbitListener(queues = "venda.events")
    public void receberEvento(String mensagem) {
        log.info("Evento recebido: " + mensagem);
    }

}
