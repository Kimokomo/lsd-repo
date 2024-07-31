package repoTest;

import entity.LsdEntity;
import org.junit.Ignore;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import repo.LsdEntityRepository;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
public class LsdEntityRepositoryTest {

    @Autowired
    private LsdEntityRepository repository;

    private void clearDatabase() {
        repository.findAll().forEach(entity -> repository.delete(entity.getId()));
    }

    @Test
    public void testFindById() {
        // Prepare test data
        LsdEntity entity = LsdEntity.builder()
                .code("TEST_CODE")
                .build();
        repository.save(entity);

        // Perform the test
        LsdEntity foundEntity = repository.findById(entity.getId());
        assertThat(foundEntity)
                .isNotNull()
                .extracting(LsdEntity::getId)
                .isEqualTo(entity.getId());
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
