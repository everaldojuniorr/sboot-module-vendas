package br.com.vendas.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.Date;
import java.util.List;

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

    @ManyToOne
    @JoinColumn(name = "cliente_id")
    private Cliente cliente;
    private String filial;

    @OneToMany
    @JoinColumn(name = "produto_id")
    private List<ProdutoVenda> produtos;

    private enum statusVenda {
        CANCELADA, APROVADA
    }


}
