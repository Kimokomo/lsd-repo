package repo;

import entity.LsdEntity;

import java.util.List;

public interface LsdEntityRepository {

    void save(LsdEntity lsdEntity);
    LsdEntity findById(Long id);
    List<LsdEntity> findAll();
    void delete(Long id);
    void update(LsdEntity lsdEntity);


}
