package br.com.vendas.model;

import jakarta.persistence.*;

@Entity
@Table
public class Produto {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(nullable = false)
    private int id;
    private String nomeProduto;
    private long valorUnitarioProduto;
}
