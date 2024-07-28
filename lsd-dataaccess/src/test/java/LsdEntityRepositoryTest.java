import entity.LsdEntity;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import repo.LsdEntityRepository;
import repo.LsdEntityRepositoryImpl;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class LsdEntityRepositoryTest {

    private EntityManagerFactory emf;
    private LsdEntityRepository repository;

    @Before
    public void setUp() {
        emf = Persistence.createEntityManagerFactory("myJpaUnit");
        repository = new LsdEntityRepositoryImpl(emf); // Correctly instantiate the implementation
    }

    @After
    public void tearDown() {
        if (emf != null) emf.close();
    }

    @Test
    public void testFindAll() {
        List<LsdEntity> entities = repository.findAll();
        assertThat(entities)
                .isNotNull()
                .hasSize(22);
    }

    @Test
    public void saveTest() {
        repository.save(TestData.lsdEntity1);
        LsdEntity savedEntityById = repository.findById(TestData.lsdEntity1.getId());
        assertThat(savedEntityById)
                .isNotNull()
                .extracting(LsdEntity::getCode)
                .isEqualTo("BB");
    }

}
