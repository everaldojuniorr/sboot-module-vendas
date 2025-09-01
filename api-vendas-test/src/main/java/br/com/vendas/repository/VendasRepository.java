package br.com.vendas.repository;

import br.com.vendas.model.Venda;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface VendasRepository extends JpaRepository<Venda, Integer> {

    @Override
    void deleteById(Integer integer);

}
