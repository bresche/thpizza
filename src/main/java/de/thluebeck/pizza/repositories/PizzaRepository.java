package de.thluebeck.pizza.repositories;

import de.thluebeck.pizza.entities.Pizza;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PizzaRepository extends CrudRepository<Pizza, Long> {
    
}
