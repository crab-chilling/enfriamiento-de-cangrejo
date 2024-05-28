package fr.crab.chilling.repository;

import fr.crab.chilling.entity.Kuser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AuthRepository extends JpaRepository<Kuser, Long> {
    Optional<Kuser> findByUserName(String username);
}
