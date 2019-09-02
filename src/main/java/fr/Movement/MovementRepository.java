package fr.Movement;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MovementRepository extends CrudRepository<Movement, Integer> {

    Iterable<Movement> findLast50ByOrderByIdDesc();

    Boolean existsAllByMerchandiseInfo_Reference(Long merchandiseInfo_reference);

    Movement findByMerchandiseInfo_Reference(Long reference);
}
