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

   @GetMapping(path = { "/venda/findAllVendas"})
    public ResponseEntity<List<Venda>> getVendas() {
       return ResponseEntity.ok(vendaService.listarTodas());
   }

   @PutMapping(path = { "/venda/salvarVenda"})
    public void addVenda(@RequestBody Venda venda) {
       vendaService.criarVenda(venda);
   }

    @GetMapping(path = { "/venda/buscarPorId/{id}"})
    public ResponseEntity<Venda> buscarPorId(@PathVariable Long id) {
        Venda venda = vendaService.buscarPorId(id);
        return (venda != null) ? ResponseEntity.ok(venda) : ResponseEntity.notFound().build();
    }

    @PutMapping(path = { "/venda/atualizarVenda/{id}"})
    public ResponseEntity<Venda> atualizarVenda(@PathVariable Long id, @RequestBody Venda vendaAtualizada) {
        Venda atualizada = vendaService.atualizarVenda(id, vendaAtualizada);
        return ResponseEntity.ok(atualizada);
    }

    @DeleteMapping(path = { "/venda/deleterVenda/{id}"})
    public ResponseEntity<Void> deletarVenda(@PathVariable Long id) {
        boolean deletada = vendaService.deletarVenda(id);
        return deletada ? ResponseEntity.noContent().build() : ResponseEntity.notFound().build();
    }
}
