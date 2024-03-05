package edu.bbte.idde.jdim2141.spring.repository;

import edu.bbte.idde.jdim2141.spring.model.domain.Menu;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MenuRepository extends JpaRepository<Menu, Long> {

    Optional<Menu> findByIdAndChef_Id(Long menuId, Long chefId);

    Page<Menu> findAllByMenuTitleContains(String menuTitle, Pageable pageable);

    Page<Menu> findAllByChef_Id(Long chefId, Pageable pageable);

    Page<Menu> findAllByChef_IdAndMenuTitleContains(Long chefId, String menuTitle,
        Pageable pageable);

    Menu findByPrice(Double price);
}
