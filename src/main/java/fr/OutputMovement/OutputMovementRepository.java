package fr.OutputMovement;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OutputMovementRepository extends CrudRepository<OutputMovement, Integer> {
}
