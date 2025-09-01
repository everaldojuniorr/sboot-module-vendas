package br.com.vendas.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
@Inheritance(strategy = InheritanceType.JOINED)
@Entity
@Table(name = "Produto")
public class ProdutoVenda {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(nullable = true)
    private int produto_id;
    private String nomeProduto;
    private long valorUnitarioProduto;
    private int quantidade;

    private enum statusProduto {
        CANCELADO, APROVADO
    }
}
