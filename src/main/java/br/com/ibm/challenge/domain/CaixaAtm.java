package br.com.ibm.challenge.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * A classe CaixaAtm representa o modelo de dados do Caixa ATM no banco.
 *
 * @author lucas
 */
@Entity
@Table(name = "atm")
public class CaixaAtm implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Size(max = 5)
    @Column(name = "numero_atm", nullable = false)
    private String numeroAtm;

    @NotNull
    @Size(max = 30)
    @Column(name = "nome_atm", nullable = false)
    private String nomeAtm;

    @NotNull
    @Size(max = 45)
    @Column(name = "serial_atm", nullable = false)
    private String serialAtm;

    @JsonIgnore
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "data_atm")
    private Date dataAtm;

    @JsonIgnore
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "data_fechamento")
    private Date dataFechamento;

    @JsonIgnore
    @Enumerated(EnumType.STRING)
    private StatusCaixa status;

    @JsonIgnore
    @OneToMany(mappedBy = "atm", cascade = {CascadeType.ALL})
    private List<MovimentoCaixa> listaMovimentoCaixa;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNumeroAtm() {
        return numeroAtm;
    }

    public void setNumeroAtm(String numeroAtm) {
        this.numeroAtm = numeroAtm;
    }

    public String getNomeAtm() {
        return nomeAtm;
    }

    public void setNomeAtm(String nomeAtm) {
        this.nomeAtm = nomeAtm;
    }

    public String getSerialAtm() {
        return serialAtm;
    }

    public void setSerialAtm(String serialAtm) {
        this.serialAtm = serialAtm;
    }

    public Date getDataAtm() {
        return dataAtm;
    }

    public void setDataAtm(Date dataAtm) {
        this.dataAtm = dataAtm;
    }

    public Date getDataFechamento() {
        return dataFechamento;
    }

    public void setDataFechamento(Date dataFechamento) {
        this.dataFechamento = dataFechamento;
    }

    public StatusCaixa getStatus() {
        return status;
    }

    public void setStatus(StatusCaixa status) {
        this.status = status;
    }

    public List<MovimentoCaixa> getListMovimentoCaixa() {
        return listaMovimentoCaixa;
    }

    public void setListMovimentoCaixa(List<MovimentoCaixa> listMovimentoCaixa) {
        this.listaMovimentoCaixa = listMovimentoCaixa;
    }

    public CaixaAtm() {

    }

    public CaixaAtm(Long id, String numeroAtm) {
        this.id = id;
        this.numeroAtm = numeroAtm;
    }

    @Override
    public String toString() {
        return "CaixaAtm{" +
                "id=" + id +
                ", numeroAtm='" + numeroAtm + '\'' +
                '}';
    }
}
