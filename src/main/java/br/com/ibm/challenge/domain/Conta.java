package br.com.ibm.challenge.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * A classe Conta representa o modelo de dados da Conta no banco.
 *
 * @author lucas
 */
@Entity
@Table(name = "conta")
public class Conta implements Serializable {

    @JsonIgnore
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Size(max = 4)
    @Column(name = "cod_agencia")
    private String codigoAgencia;

    @NotNull
    @Size(max = 8)
    @Column(name = "num_conta")
    private String numeroConta;

    @Size(max = 100)
    @Column(name = "nome")
    private String nome;

    @NotNull
    @Min(0)
    @Column(name = "saldo")
    private BigDecimal saldo;

    @JsonIgnore
    @OneToMany(mappedBy = "conta", cascade = {CascadeType.ALL})
    private List<MovimentoConta> listaMovimentoConta;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCodigoAgencia() {
        return codigoAgencia;
    }

    public void setCodigoAgencia(String codigoAgencia) {
        this.codigoAgencia = codigoAgencia;
    }

    public String getNumeroConta() {
        return numeroConta;
    }

    public void setNumeroConta(String numeroConta) {
        this.numeroConta = numeroConta;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public BigDecimal getSaldo() {
        return saldo;
    }

    public void setSaldo(BigDecimal saldo) {
        this.saldo = saldo;
    }

    public List<MovimentoConta> getListaMovimentos() {
        return listaMovimentoConta;
    }

    public void setListaMovimentos(List<MovimentoConta> listaMovimentos) {
        this.listaMovimentoConta = listaMovimentos;
    }

    public Conta() {
    }

    public Conta(Long id) {
        this.id = id;
    }

    public Conta(Long id, @NotNull @Size(max = 4) String codigoAgencia, @NotNull @Size(max = 8) String numeroConta) {
        this.id = id;
        this.codigoAgencia = codigoAgencia;
        this.numeroConta = numeroConta;
    }

    @Override
    public String toString() {
        return "Conta{" +
                "id=" + id +
                ", codigoAgencia='" + codigoAgencia + '\'' +
                ", numeroConta='" + numeroConta + '\'' +
                ", nome='" + nome + '\'' +
                ", saldo=" + saldo +
                '}';
    }
}
