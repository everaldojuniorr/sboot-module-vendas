package br.com.vendas.controller;

import br.com.vendas.model.Venda;
import br.com.vendas.repository.VendasRepository;
import br.com.vendas.service.VendaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
public class VendasApi {

    @Autowired
    VendasRepository vendasRepository;

    @Autowired
    VendaService vendaService;

   @GetMapping(path = { "/findAllVendas"})
    public List<Venda> getVendas() {
       return vendasRepository.findAll();
   }

   @GetMapping(path = { "/venda/{id}"})
    public Optional<Venda> getVenda(@PathVariable int id) {
       return vendaService.getVendaById(id);
   }

   @PutMapping(path = { "/venda/save"})
    public void addVenda(@RequestBody Venda venda) {
       vendasRepository.save(venda);
   }

}
