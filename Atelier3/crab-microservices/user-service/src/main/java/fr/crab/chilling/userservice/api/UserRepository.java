package fr.crab.chilling.userservice.api;

import fr.crab.chilling.entity.Kuser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<Kuser, Long> {

    Optional<Kuser> findByEmailAddress(String emailAddress);

    Optional<Kuser> findByUserName(String userName);
}