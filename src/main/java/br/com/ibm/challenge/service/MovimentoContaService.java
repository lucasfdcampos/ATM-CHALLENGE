package br.com.ibm.challenge.service;

import br.com.ibm.challenge.domain.Conta;
import br.com.ibm.challenge.domain.MovimentoConta;

import java.util.List;

/**
 * @author lucas
 */
public interface MovimentoContaService {

    void save(MovimentoConta movimentoConta);

    List<MovimentoConta> findAllByConta(Conta conta) throws Exception;
}
