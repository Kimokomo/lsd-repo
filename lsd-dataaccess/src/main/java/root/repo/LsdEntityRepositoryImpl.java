package root.repo;

import org.springframework.transaction.annotation.Transactional;
import root.entity.LsdEntity;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

public class LsdEntityRepositoryImpl implements LsdEntityRepository {

    @PersistenceContext(unitName = "myJpaUnit")
    private final EntityManager entityManager;


    public LsdEntityRepositoryImpl(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    @Transactional
    public void save(LsdEntity lsdEntity) {
        entityManager.persist(lsdEntity);
    }

    @Override
    @Transactional
    public LsdEntity findById(Long id) {
        return entityManager.find(LsdEntity.class, id);
    }

    @Override
    @Transactional
    public List<LsdEntity> findAll() {
        return entityManager.createNativeQuery("SELECT * FROM lsd_entity", LsdEntity.class).getResultList();
    }

    @Override
    @Transactional
    public void delete(Long id) {
        LsdEntity lsdEntity = findById(id);
        if (lsdEntity != null) {
            entityManager.remove(lsdEntity);
        }
    }

    @Override
    @Transactional
    public void update(LsdEntity lsdEntity) {
        entityManager.merge(lsdEntity);
    }
}
