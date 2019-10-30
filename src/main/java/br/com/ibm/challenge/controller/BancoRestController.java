package br.com.ibm.challenge.controller;

import br.com.ibm.challenge.domain.*;
import br.com.ibm.challenge.service.CaixaAtmService;
import br.com.ibm.challenge.service.ContaService;
import br.com.ibm.challenge.service.MovimentoCaixaService;
import br.com.ibm.challenge.service.MovimentoContaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

/**
 * A classe BancoRestController e responsavel por disponibilizar os servicos de Caixa do banco.
 *
 * @author lucas
 */
@RestController
@RequestMapping(
        value = "/atm",
        produces = MediaType.APPLICATION_JSON_UTF8_VALUE,
        consumes = MediaType.APPLICATION_JSON_UTF8_VALUE
)
public class BancoRestController {

    @Autowired
    private CaixaAtmService caixaAtmService;

    /**
     * Metodo responsavel pelo cadastro do Caixa ATM
     * @param caixaAtm
     */
    @PostMapping(value = "cadastroAtm")
    @ResponseStatus(HttpStatus.CREATED)
    public void cadastrarCaixaAtm(@RequestBody CaixaAtm caixaAtm) {
        caixaAtm.setStatus(StatusCaixa.ABERTO);
        caixaAtm.setDataAtm(new Date());
        caixaAtm.setDataFechamento(null);

        caixaAtmService.save(caixaAtm);
    }

    /**
     * Metodo responsavel pela abertura do Caixa ATM
     * @param caixaAtm
     */
    @PostMapping(value = "aberturaCaixaAtm")
    @ResponseStatus(HttpStatus.CREATED)
    public void abrirCaixaAtm(@RequestBody CaixaAtm caixaAtm) throws Exception {
        caixaAtm.setStatus(StatusCaixa.ABERTO);
        caixaAtm.setDataAtm(new Date());
        caixaAtm.setDataFechamento(null);

        caixaAtmService.update(caixaAtm);
    }

    @Autowired
    private ContaService contaService;

    @Autowired
    private MovimentoCaixaService movimentoCaixaService;

    @Autowired
    private MovimentoContaService movimentoContaService;

    /**
     * Metodo responsavel pelo cadastro de conta do cliente
     * @param conta
     */
    @PostMapping(value = "cadastroConta")
    @ResponseStatus(HttpStatus.CREATED)
    public void cadastrarConta(@RequestBody Conta conta) {
        contaService.save(conta);
    }

    /**
     * Metodo responsavel pelo cadastro do movimento de caixa [DEPOSITO, SAQUE, TRANSFERENCIA]
     * @param movimentoCaixa
     */
    @PostMapping(value = "movimentoCaixa")
    @ResponseStatus(HttpStatus.CREATED)
    public void cadastrarMovimentoCaixa(@RequestBody MovimentoCaixa movimentoCaixa) throws Exception {
        movimentoCaixaService.save(movimentoCaixa);
    }

    /**
     * Metodo responsavel por emitir extrato da conta do cliente
     * @param conta
     * @throws Exception
     */
    @PostMapping(value = "emitirExtrato")
    @ResponseStatus(HttpStatus.OK)
    public List<MovimentoConta> emitirExtrato(@RequestBody Conta conta) throws Exception {
        return movimentoContaService.findAllByConta(conta);
    }

    /**
     * Metodo responsavel pelo relatorio de fechamento de caixa ATM
     * @param caixaAtm
     * @return lista MovimentoCaixa
     */
    @PostMapping(value = "fechamentoCaixa")
    @ResponseStatus(HttpStatus.OK)
    public List<MovimentoCaixa> fecharCaixa(@RequestBody CaixaAtm caixaAtm) throws Exception {
        return movimentoCaixaService.findAllByCaixaAtm(caixaAtm);
    }
}
