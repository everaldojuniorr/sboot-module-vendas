package br.com.vendas.repository;

import br.com.vendas.model.VendaItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VendaItemRepository extends JpaRepository<VendaItem, Integer> {

}
