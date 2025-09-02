package br.com.vendas.repository;

import br.com.vendas.model.Venda;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VendaRepository extends JpaRepository<Venda, Integer> {

    @Override
    void deleteById(Integer integer);

}
