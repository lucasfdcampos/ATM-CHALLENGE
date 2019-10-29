package br.com.ibm.challenge.dao;

import br.com.ibm.challenge.domain.Conta;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

/**
 * @author lucas
 */
@Repository
public class ContaDaoImpl implements ContaDao {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public void save(Conta conta) {
        entityManager.persist(conta);
    }

    @Override
    public void update(Conta conta) {
        entityManager.merge(conta);
    }

    @Override
    public Conta findById(Long id) {
        return entityManager.find(Conta.class, id);
    }

    @Override
    public Conta findByCodAgenciaNumConta(String codigoAgencia, String numeroConta) {
        return entityManager
                .createQuery("select c from Conta c where c.conta.cod_agencia = :codigo and " +
                        "c.conta.num_conta = :numero", Conta.class)
                .setParameter("codigo", codigoAgencia)
                .setParameter("conta", numeroConta)
                .getSingleResult();
    }

    @Override
    public List<Conta> findAll() {
        return entityManager
                .createQuery("select c from Conta c", Conta.class)
                .getResultList();
    }
}
