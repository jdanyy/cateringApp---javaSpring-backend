package edu.bbte.idde.jdim2141.spring.repository;

import edu.bbte.idde.jdim2141.spring.model.domain.Dish;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DishRepository extends JpaRepository<Dish, Long> {

    Page<Dish> findAllByNameContains(String name, Pageable pageable);

    Page<Dish> findAllByIdIn(List<Long> id, Pageable pageable);

}
