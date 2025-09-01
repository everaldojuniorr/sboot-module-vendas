package br.com.vendas.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.Date;
import java.util.List;

@Builder
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "Venda")
public class Venda {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(nullable = false)
    private int id;

    private Date dataVenda;
    private Cliente cliente;
    private String filial;
    private List<Produto> produtos;

    private enum statusVenda {
        CANCELADA, APROVADA
    }


}
