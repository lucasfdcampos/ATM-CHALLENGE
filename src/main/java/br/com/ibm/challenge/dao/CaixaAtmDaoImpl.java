package br.com.ibm.challenge.dao;

import br.com.ibm.challenge.domain.CaixaAtm;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

/**
 * @author lucas
 */
@Repository
public class CaixaAtmDaoImpl implements CaixaAtmDao {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public void save(CaixaAtm caixaAtm) {
        entityManager.persist(caixaAtm);
    }

    @Override
    public void update(CaixaAtm caixaAtm) {
        entityManager.merge(caixaAtm);
    }

    @Override
    public CaixaAtm findById(Long id) {
        return entityManager.find(CaixaAtm.class, id);
    }

    @Override
    public CaixaAtm findByNumeroCaixa(String numero) {
        return entityManager.createQuery("select a from atm a where a.numero_atm = :?1", CaixaAtm.class)
                .setParameter(1,numero)
                .getSingleResult();
    }

    @Override
    public List<CaixaAtm> findAll() {
        return entityManager.createQuery("select a from atm a", CaixaAtm.class)
                .getResultList();
    }
}
