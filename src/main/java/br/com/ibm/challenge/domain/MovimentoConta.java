package br.com.ibm.challenge.domain;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * A classe MovimentoConta representa o modelo de dados do Movimento da Conta no banco.
 *
 * @author lucas
 */
@Entity
@Table(name = "conta_movimento")
public class MovimentoConta implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "conta", referencedColumnName = "id")
    private Conta conta;

    @NotNull
    @Enumerated(EnumType.STRING)
    private TipoMovimento tipoMovimento;

    @NotNull
    @Temporal(TemporalType.TIMESTAMP)
    private Date dataMov;

    @NotNull
    @Min(0)
    private BigDecimal valor;

    @Size(max = 200)
    private String descricao;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public Date getDataMov() {
        return dataMov;
    }

    public void setDataMov(Date dataMov) {
        this.dataMov = dataMov;
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

    public MovimentoConta() {

    }

    public MovimentoConta(Long id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "MovimentoConta{" +
                "id=" + id +
                '}';
    }
}
