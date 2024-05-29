package fr.crab.repository;

import fr.crab.entity.Kuser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AuthenticationRepository extends JpaRepository<Kuser, Long> {
    Optional<Kuser> findByUserName(String username);
}
