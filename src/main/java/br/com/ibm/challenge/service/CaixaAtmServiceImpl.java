package br.com.ibm.challenge.service;

import br.com.ibm.challenge.dao.CaixaAtmDao;
import br.com.ibm.challenge.domain.CaixaAtm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author lucas
 */
@Service
@Transactional
public class CaixaAtmServiceImpl implements CaixaAtmService {

    @Autowired
    private CaixaAtmDao caixaAtmDao;

    @Override
    public void save(CaixaAtm caixaAtm) {
        caixaAtmDao.save(caixaAtm);
    }

    @Override
    public void update(CaixaAtm caixaAtm) throws Exception {
        // Verifica caixa ATM
        if (caixaAtm == null) {
            throw new Exception("Caixa ATM invalido.");

        } else if (caixaAtmDao.findById(caixaAtm.getId()) == null) {
            throw new Exception("Caixa ATM inexistente.");
        }

        caixaAtmDao.update(caixaAtm);
    }

    @Override
    @Transactional(readOnly = true)
    public CaixaAtm findById(Long id) {
        return caixaAtmDao.findById(id);
    }

    @Override
    public CaixaAtm findByNumeroCaixa(String numero) {
        return caixaAtmDao.findByNumeroCaixa(numero);
    }

    @Override
    @Transactional(readOnly = true)
    public List<CaixaAtm> findAll() {
        return caixaAtmDao.findAll();
    }
}
