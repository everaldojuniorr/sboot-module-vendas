package br.com.vendas.service;

import br.com.vendas.model.Venda;
import br.com.vendas.repository.VendasRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class VendaService {

    @Autowired
    VendasRepository vendasRepository;

    public Optional<Venda> getVendaById(int id) {
        return vendasRepository.findById(id);

    }
}
