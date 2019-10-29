package br.com.ibm.challenge.service;

import br.com.ibm.challenge.domain.Conta;

import java.util.List;

/**
 * @author lucas
 */
public interface ContaService {

    void save(Conta conta);

    void update(Conta conta);

    Conta findById(Long id);

    Conta findByCodAgenciaNumConta(String codigoAgencia, String numeroConta);

    List<Conta> findAll();
}
