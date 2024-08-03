package root.repo;

import org.springframework.stereotype.Repository;
import root.entity.LsdEntity;

import java.util.List;

public interface LsdEntityRepository {

    void save(LsdEntity lsdEntity);

    LsdEntity findById(Long id);

    List<LsdEntity> findAll();

    void delete(Long id);

    void update(LsdEntity lsdEntity);

}
