package br.com.ibm.challenge.dao;

import br.com.ibm.challenge.domain.Conta;
import br.com.ibm.challenge.domain.MovimentoConta;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

/**
 * @author lucas
 */
@Repository
public class MovimentoContaDaoImpl implements MovimentoContaDao {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public void save(MovimentoConta movimentoConta) {
        entityManager.persist(movimentoConta);
    }

    @Override
    public List<MovimentoConta> findAllByConta(Conta conta) {
        return entityManager.createQuery("select m from conta_movimento m where m.conta.id = ?1")
                .setParameter(1, conta.getId())
                .getResultList();
    }
}
