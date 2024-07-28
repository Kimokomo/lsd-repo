package repo;

import entity.LsdEntity;

import javax.persistence.*;
import java.util.List;

public class LsdEntityRepositoryImpl implements LsdEntityRepository {

    private final EntityManagerFactory emf;

    public LsdEntityRepositoryImpl(EntityManagerFactory emf) {
        this.emf = emf;
    }

    @Override
    public void save(LsdEntity lsdEntity) {
        EntityManager em = emf.createEntityManager();
        EntityTransaction transaction = em.getTransaction();
        try {
            transaction.begin();
            em.persist(lsdEntity);
            transaction.commit();
        } catch (Exception e) {
            if (transaction.isActive()) {
                transaction.rollback();
            }
            e.printStackTrace();
        } finally {
            em.close();
        }
    }

    @Override
    public LsdEntity findById(Long id) {
        EntityManager em = emf.createEntityManager();
        try {
            return em.find(LsdEntity.class, id);
        } finally {
            em.close();
        }
    }

    @Override
    public List<LsdEntity> findAll() {
        EntityManager em = emf.createEntityManager();
        try {
            Query query = em.createNativeQuery("SELECT * FROM lsd_entity", LsdEntity.class);
            return query.getResultList();
        } finally {
            em.close();
        }
    }
}
