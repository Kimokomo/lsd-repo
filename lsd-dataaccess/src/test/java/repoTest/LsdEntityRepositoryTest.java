package repoTest;

import entity.LsdEntity;
import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import repo.LsdEntityRepository;
import repo.LsdEntityRepositoryImpl;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@Ignore
public class LsdEntityRepositoryTest {

    private EntityManagerFactory emf;
    private EntityManager em;
    private LsdEntityRepository repository;

    @Before
    public void setUp() {
        emf = Persistence.createEntityManagerFactory("myJpaUnit");
        em = emf.createEntityManager();
        repository = new LsdEntityRepositoryImpl(emf); // Correctly instantiate the implementation
        //clearDatabase(); // Clear the database before each test
    }

    @After
    public void tearDown() {
        if (em != null) em.close();
        if (emf != null) emf.close();
    }

    private void clearDatabase() {
        em.getTransaction().begin();
        em.createQuery("DELETE FROM LsdEntity").executeUpdate();
        em.getTransaction().commit();
    }

    @Test
    public void testFindById(){
        LsdEntity foundEntity = repository.findById(33L);
        assertThat(foundEntity)
                .isNotNull()
                .extracting(LsdEntity::getId)
                .isEqualTo(33L);
    }

    @Test
    public void testFindAll() {
        List<LsdEntity> entities = repository.findAll();
        assertThat(entities)
                .isNotNull()
                .hasSize(5);
    }

    @Test
    public void testSave() {
        repository.save(TestData.lsdEntity1);
        LsdEntity savedEntityById = repository.findById(TestData.lsdEntity1.getId());
        assertThat(savedEntityById)
                .isNotNull()
                .extracting(LsdEntity::getCode)
                .isEqualTo("BB");
    }

    @Test
    public void testDelete() {

        LsdEntity entity = LsdEntity.builder()
                .code("UU")
                .build();
        repository.save(entity);

        repository.delete(entity.getId());

        // Verify that the entity no longer exists
        LsdEntity deletedEntity = repository.findById(entity.getId());
        assertThat(deletedEntity).isNull();
    }

    @Test
    public void testUpdate() {
        // Setup and save a test entity
        LsdEntity entity = LsdEntity.builder()
                .code("DDUU")
                .build();
        repository.save(entity);

        // Update the entity's Code
        entity.setCode("Updated CODE");
        repository.update(entity);

        // Fetch the updated entity by ID
        LsdEntity updatedEntity = repository.findById(entity.getId());

        // Verify results
        assertThat(updatedEntity)
                .isNotNull()
                .extracting(LsdEntity::getCode)
                .isEqualTo("Updated CODE");
    }

}
