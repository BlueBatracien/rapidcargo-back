package fr.Customs;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomsRepository extends CrudRepository<Customs, Integer> {
}
