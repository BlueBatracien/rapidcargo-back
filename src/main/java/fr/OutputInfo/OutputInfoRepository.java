package fr.OutputInfo;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OutputInfoRepository extends CrudRepository<OutputInfo, Integer> {
}
