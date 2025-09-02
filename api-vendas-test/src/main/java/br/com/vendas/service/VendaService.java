package br.com.vendas.service;

import br.com.vendas.model.Produto;
import br.com.vendas.model.Venda;
import br.com.vendas.model.VendaItem;
import br.com.vendas.repository.ClienteRepository;
import br.com.vendas.repository.FilialRepository;
import br.com.vendas.repository.ProdutoRepository;
import br.com.vendas.repository.VendaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Service
@RequiredArgsConstructor
public class VendaService {

    private final VendaRepository vendaRepository;
    private final ClienteRepository clienteRepository;
    private final FilialRepository filialRepository;
    private final ProdutoRepository produtoRepository;
    private final RabbitTemplate rabbitTemplate;

    private static final Logger log = LoggerFactory.getLogger(VendaService.class);

    public List<Venda> listarTodas() {
        return vendaRepository.findAll();
    }

    public Venda buscarPorId(Long id) {
        log.info("Evento: buscarPorId -> vendaId={}", id);
        return vendaRepository.findById(Math.toIntExact(id)).orElse(null);
    }

    @Transactional
    public Venda criarVenda(Venda venda) {
        log.info("Evento: criarVenda -> nomeCliente={}", venda.getCliente().getNome());
        validarItensVenda(venda);

        venda.setCliente(clienteRepository.findById(Math.toIntExact(venda.getCliente().getId()))
                .orElseThrow(() -> new IllegalArgumentException("Cliente não encontrado")));
        venda.setFilial(filialRepository.findById(Math.toIntExact(venda.getFilial().getId()))
                .orElseThrow(() -> new IllegalArgumentException("Filial não encontrada")));

        BigDecimal valorTotalVenda = BigDecimal.ZERO;

        for (VendaItem item : venda.getItens()) {
            Produto produto = produtoRepository.findById(Math.toIntExact(item.getProduto().getId()))
                    .orElseThrow(() -> new IllegalArgumentException("Produto não encontrado: " + item.getProduto().getId()));

            item.setProduto(produto);
            item.setVenda(venda);

            BigDecimal valorUnitario = produto.getValorUnitario();
            int quantidade = item.getQuantidade();

            BigDecimal descontoPercentual = calcularDescontoPercentual(quantidade);
            BigDecimal desconto = produto.getValorUnitario().multiply(BigDecimal.valueOf(quantidade))
                    .multiply(descontoPercentual);
            BigDecimal valorTotalItem = valorUnitario.multiply(BigDecimal.valueOf(quantidade)).subtract(desconto);

            item.setValorUnitario(BigDecimal.valueOf(valorUnitario.doubleValue()));
            item.setDesconto(BigDecimal.valueOf(desconto.doubleValue()));
            item.setValorTotal(BigDecimal.valueOf(valorTotalItem.doubleValue()));

            valorTotalVenda = valorTotalVenda.add(valorTotalItem);
        }

        venda.setValorTotal(BigDecimal.valueOf(valorTotalVenda.doubleValue()));
        try{
            Venda criada = vendaRepository.save(venda);
            log.info("Evento: CompraEfetuada -> vendaId={}", criada.getId());
        } catch (Exception e) {
            e.printStackTrace();
        }

        return venda;
    }

    @Transactional
    public Venda atualizarVenda(Long id, Venda vendaAtualizada) {
        Venda vendaExistente = vendaRepository.findById(Math.toIntExact(id))
                .orElseThrow(() -> new IllegalArgumentException("Venda não encontrada"));

        vendaExistente.setDataVenda(vendaAtualizada.getDataVenda());
        vendaExistente.setStatus(vendaAtualizada.getStatus());
        vendaExistente.setCliente(clienteRepository.findById(Math.toIntExact(vendaAtualizada.getCliente().getId()))
                .orElseThrow(() -> new IllegalArgumentException("Cliente não encontrado")));
        vendaExistente.setFilial(filialRepository.findById(Math.toIntExact(vendaAtualizada.getFilial().getId()))
                .orElseThrow(() -> new IllegalArgumentException("Filial não encontrada")));

        vendaExistente.getItens().clear();
        vendaExistente.getItens().addAll(vendaAtualizada.getItens());

        validarItensVenda(vendaExistente);
        log.info("Evento: AtualizandoVenda -> vendaId={}", vendaExistente.getId());
        return criarVenda(vendaExistente);
    }

    @Transactional
    public Venda atualizarParcial(Long id, Venda vendaParcial) {
        Venda vendaExistente = vendaRepository.findById(Math.toIntExact(id))
                .orElseThrow(() -> new IllegalArgumentException("Venda não encontrada"));

        if (vendaParcial.getDataVenda() != null) {
            vendaExistente.setDataVenda(vendaParcial.getDataVenda());
        }
        if (vendaParcial.getStatus() != null) {
            vendaExistente.setStatus(vendaParcial.getStatus());
        }
        if (vendaParcial.getCliente() != null) {
            vendaExistente.setCliente(clienteRepository.findById(Math.toIntExact(vendaParcial.getCliente().getId()))
                    .orElseThrow(() -> new IllegalArgumentException("Cliente não encontrado")));
        }
        if (vendaParcial.getFilial() != null) {
            vendaExistente.setFilial(filialRepository.findById(Math.toIntExact(vendaParcial.getFilial().getId()))
                    .orElseThrow(() -> new IllegalArgumentException("Filial não encontrada")));
        }
        if (vendaParcial.getItens() != null && !vendaParcial.getItens().isEmpty()) {
            vendaExistente.getItens().clear();
            vendaExistente.getItens().addAll(vendaParcial.getItens());
        }

        validarItensVenda(vendaExistente);
        log.info("Evento: AtualizandoVenda -> vendaId={}", vendaExistente.getId());
        return criarVenda(vendaExistente);
    }

    @Transactional
    public boolean deletarVenda(Long id) {
        Optional<Venda> venda = vendaRepository.findById(Math.toIntExact(id));
        if (venda.isPresent()) {
            log.info("Evento: DeletandoVenda -> vendaId={}", id);
            vendaRepository.delete(venda.get());
            return true;
        }
        return false;
    }

    private void validarItensVenda(Venda venda) {
        for (VendaItem item : venda.getItens()) {
            if (item.getQuantidade() > 20) {
                throw new IllegalArgumentException("Quantidade de itens não pode ser maior que 20. Produto: " + item.getProduto().getNome());
            }
        }
    }

    private BigDecimal calcularDescontoPercentual(int quantidade) {
        if (quantidade > 9 && quantidade <= 20) {
            return BigDecimal.valueOf(0.20); // 20%
        }
        if (quantidade >= 4 && quantidade <= 9) {
            return BigDecimal.valueOf(0.10); // 10%
        }
        return BigDecimal.ZERO;
    }

    public Optional<Venda> getVendaById(int id) {
        return null;
    }
}
