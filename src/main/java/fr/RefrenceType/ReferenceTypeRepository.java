package fr.RefrenceType;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReferenceTypeRepository extends CrudRepository<ReferenceType, Integer> {

    Boolean existsByName(String name);
}
