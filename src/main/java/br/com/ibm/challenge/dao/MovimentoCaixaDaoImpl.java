package br.com.ibm.challenge.dao;

import br.com.ibm.challenge.domain.CaixaAtm;
import br.com.ibm.challenge.domain.MovimentoCaixa;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

/**
 * @author lucas
 */
@Repository
public class MovimentoCaixaDaoImpl implements MovimentoCaixaDao {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public void save(MovimentoCaixa movimentoCaixa) {
        entityManager.persist(movimentoCaixa);
    }

    @Override
    public List<MovimentoCaixa> findAllByCaixaAtm(CaixaAtm caixaAtm) {
        return entityManager.createQuery("select m from atm_movimento m where m.caixa.id = ?1", MovimentoCaixa.class)
                .setParameter(1, caixaAtm.getId())
                .getResultList();
    }
}
