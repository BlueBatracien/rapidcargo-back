package fr.Warehouse;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WarehouseRepository extends CrudRepository<Warehouse, Integer> {

    Boolean existsByCode(String code);

    Boolean existsByName(String name);

    Warehouse findByName(String name);

    Warehouse findByCode(String code);
}
