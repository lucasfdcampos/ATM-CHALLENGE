package br.com.ibm.challenge.dao;

import br.com.ibm.challenge.domain.CaixaAtm;

import java.util.List;

/**
 * @author lucas
 */
public interface CaixaAtmDao {

    void save(CaixaAtm caixa);

    void update(CaixaAtm caixa);

    CaixaAtm findById(Long id);

    CaixaAtm findByNumeroCaixa(String numero);

    List<CaixaAtm> findAll();
}
