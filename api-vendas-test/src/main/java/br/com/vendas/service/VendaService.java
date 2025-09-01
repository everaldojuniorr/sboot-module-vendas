package br.com.vendas.service;

import br.com.vendas.model.Produto;
import br.com.vendas.model.Venda;
import br.com.vendas.repository.VendasRepository;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Optional;

public class VendaService {

    @Autowired
    VendasRepository vendasRepository;

    public Optional<Venda> getVendaById(int id) {
        return vendasRepository.findById(id);

    }
}
