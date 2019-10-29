package br.com.ibm.challenge.dao;

import br.com.ibm.challenge.domain.Conta;
import br.com.ibm.challenge.domain.MovimentoConta;

import java.util.List;

/**
 * @author lucas
 */
public interface MovimentoContaDao {

    void save(MovimentoConta movimentoConta);

    List<MovimentoConta> findAllByConta(Conta conta);
}
