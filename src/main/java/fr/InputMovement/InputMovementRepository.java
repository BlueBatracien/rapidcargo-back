package fr.InputMovement;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface InputMovementRepository extends CrudRepository<InputMovement, Integer> {
}
