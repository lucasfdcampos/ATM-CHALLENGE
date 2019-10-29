package br.com.ibm.challenge.dao;

import br.com.ibm.challenge.domain.CaixaAtm;
import br.com.ibm.challenge.domain.MovimentoCaixa;

import java.util.List;

/**
 * @author lucas
 */
public interface MovimentoCaixaDao {

    void save(MovimentoCaixa movimentoCaixa);

    List<MovimentoCaixa> findAllByCaixaAtm(CaixaAtm caixaAtm);
}
