package br.com.ibm.challenge.service;

import br.com.ibm.challenge.domain.CaixaAtm;

import java.util.List;

/**
 * @author lucas
 */
public interface CaixaAtmService {

    void save(CaixaAtm caixa);

    void update(CaixaAtm caixa) throws Exception;

    CaixaAtm findById(Long id);

    CaixaAtm findByNumeroCaixa(String numero);

    List<CaixaAtm> findAll();
}