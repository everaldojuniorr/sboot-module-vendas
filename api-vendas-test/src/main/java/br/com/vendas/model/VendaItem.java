package br.com.vendas.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@Entity
@Table(name = "venda_item")
@NoArgsConstructor
@AllArgsConstructor
public class VendaItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Integer quantidade;
    private BigDecimal valorUnitario;
    private BigDecimal desconto;
    private BigDecimal valorTotal;

    @ManyToOne
    @JoinColumn(name = "venda_id", nullable = false)
    @JsonBackReference
    private Venda venda;

    @ManyToOne
    @JoinColumn(name = "produto_id", nullable = false)
    private Produto produto;

    public VendaItem(BigDecimal valorUnitario) {
        this.valorUnitario = valorUnitario;
    }
}