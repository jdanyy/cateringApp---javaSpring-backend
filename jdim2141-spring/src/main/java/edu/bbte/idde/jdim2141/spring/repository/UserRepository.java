package edu.bbte.idde.jdim2141.spring.repository;

import edu.bbte.idde.jdim2141.spring.model.domain.User;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByEmail(String email);
}
