package fr.MerchandiseInfo;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MerchandiseInfoRepository extends CrudRepository<MerchandiseInfo, Integer> {
}
