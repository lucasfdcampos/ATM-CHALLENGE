package br.com.ibm.challenge.service;

import br.com.ibm.challenge.dao.ContaDao;
import br.com.ibm.challenge.domain.Conta;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author lucas
 */
@Service
@Transactional
public class ContaServiceImpl implements ContaService {

    @Autowired
    private ContaDao contaDao;

    @Override
    public void save(Conta conta) {
        contaDao.save(conta);
    }

    @Override
    public void update(Conta conta) {
        contaDao.update(conta);
    }

    @Override
    @Transactional(readOnly = true)
    public Conta findById(Long id) {
        return contaDao.findById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public Conta findByCodAgenciaNumConta(String codigoAgencia, String numeroConta) {
        return contaDao.findByCodAgenciaNumConta(codigoAgencia, numeroConta);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Conta> findAll() {
        return contaDao.findAll();
    }
}
