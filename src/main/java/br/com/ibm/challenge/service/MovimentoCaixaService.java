package br.com.ibm.challenge.service;

import br.com.ibm.challenge.domain.CaixaAtm;
import br.com.ibm.challenge.domain.MovimentoCaixa;

import java.util.List;

/**
 * @author lucas
 */
public interface MovimentoCaixaService {

    void save(MovimentoCaixa movimentoCaixa) throws Exception;

    List<MovimentoCaixa> findAllByCaixaAtm(CaixaAtm caixaAtm) throws Exception;
}
