package br.com.vendas.controller;

import br.com.vendas.model.Venda;
import br.com.vendas.repository.VendaRepository;
import br.com.vendas.service.VendaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
public class VendasApi {

    @Autowired
    VendaRepository vendaRepository;

    @Autowired
    VendaService vendaService;

   @GetMapping(path = { "/findAllVendas"})
    public List<Venda> getVendas() {
       return vendaRepository.findAll();
   }

   @GetMapping(path = { "/venda/{id}"})
    public Optional<Venda> getVenda(@PathVariable int id) {
       return vendaService.getVendaById(id);
   }

   @PutMapping(path = { "/venda/save"})
    public void addVenda(@RequestBody Venda venda) {
       vendaService.criarVenda(venda);
   }
}
