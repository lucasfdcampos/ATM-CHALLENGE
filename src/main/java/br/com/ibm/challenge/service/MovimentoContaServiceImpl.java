package br.com.ibm.challenge.service;

import br.com.ibm.challenge.dao.MovimentoContaDao;
import br.com.ibm.challenge.domain.Conta;
import br.com.ibm.challenge.domain.MovimentoConta;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author lucas
 */
@Service
@Transactional
public class MovimentoContaServiceImpl implements MovimentoContaService {

    @Autowired
    private MovimentoContaDao movimentoContaDao;

    @Autowired
    private ContaService contaService;

    @Override
    public void save(MovimentoConta movimentoConta) {
        movimentoContaDao.save(movimentoConta);
    }

    @Override
    public List<MovimentoConta> findAllByConta(Conta conta) throws Exception {

        // Verifica a conta do cliente
        Conta contaPesquisa = contaService.findByCodAgenciaNumConta(conta.getCodigoAgencia(), conta.getNumeroConta());

        if (contaPesquisa == null) {
            throw new Exception("Conta inexistente.");
        }

        List<MovimentoConta> allByConta = movimentoContaDao.findAllByConta(contaPesquisa);

        if (allByConta == null) {
            throw new Exception("Movimento inexistente.");
        }

        return allByConta;
    }
}
