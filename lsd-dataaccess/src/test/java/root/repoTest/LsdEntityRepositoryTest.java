package root.repoTest;

import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import root.config.Config;
import root.entity.LsdEntity;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import root.repo.LsdEntityRepository;

import javax.transaction.Transactional;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = Config.class)
public class LsdEntityRepositoryTest {

    @Autowired
    private LsdEntityRepository repository;


    @Test
    @Transactional
    public void testFindAll() {
        List<LsdEntity> entities = repository.findAll();
        assertThat(entities)
                .isNotNull()
                .hasSize(1);
    }

    @Test
    @Transactional
    public void testSave() {
        LsdEntity entity = TestData.lsdEntity1;
        repository.save(entity);

        Long id = entity.getId();
        assertThat(id).isNotNull();

        LsdEntity savedEntityById = repository.findById(entity.getId());
        assertThat(savedEntityById)
                .isNotNull()
                .extracting(LsdEntity::getCode)
                .isEqualTo("BB");
    }



}
