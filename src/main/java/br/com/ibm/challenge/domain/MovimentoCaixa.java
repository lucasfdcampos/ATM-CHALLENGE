package br.com.ibm.challenge.domain;


import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * A classe MovimentoCaixa representa o modelo de dados do Movimento do Caixa ATM no banco.
 *
 * @author lucas
 */
@Entity
@Table(name = "atm_movimento")
public class MovimentoCaixa implements Serializable {

    @JsonIgnore
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "atm", referencedColumnName = "id")
    private CaixaAtm caixaAtm;

    @JsonIgnore
    @NotNull
    @Temporal(TemporalType.TIMESTAMP)
    private Date dataMov;

    @NotNull
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "conta", referencedColumnName = "id")
    private Conta conta;

    @Enumerated(EnumType.STRING)
    private TipoMovimento tipoMovimento;

    @NotNull
    @Min(0)
    private BigDecimal valor;

    @JsonIgnore
    @Size(max = 200)
    private String descricao;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "contaDestino", referencedColumnName = "id")
    private Conta contaDestino;

    public MovimentoCaixa() {

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public CaixaAtm getCaixaAtm() {
        return caixaAtm;
    }

    public void setCaixaAtm(CaixaAtm caixaAtm) {
        this.caixaAtm = caixaAtm;
    }

    public Date getDataMov() {
        return dataMov;
    }

    public void setDataMov(Date dataMov) {
        this.dataMov = dataMov;
    }

    public Conta getConta() {
        return conta;
    }

    public void setConta(Conta conta) {
        this.conta = conta;
    }

    public TipoMovimento getTipoMovimento() {
        return tipoMovimento;
    }

    public void setTipoMovimento(TipoMovimento tipoMovimento) {
        this.tipoMovimento = tipoMovimento;
    }

    public BigDecimal getValor() {
        return valor;
    }

    public void setValor(BigDecimal valor) {
        this.valor = valor;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public Conta getContaDestino() {
        return contaDestino;
    }

    public void setContaDestino(Conta contaDestino) {
        this.contaDestino = contaDestino;
    }

    public MovimentoCaixa(Long id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "MovimentoCaixa{" +
                "id=" + id +
                '}';
    }
}
