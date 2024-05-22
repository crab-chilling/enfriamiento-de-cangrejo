package cpe.asi.cardforge.repository;

import cpe.asi.cardforge.entity.Kuser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<Kuser, Long> {

    Optional<Kuser> findByEmailAddress(String emailAddress);

    @Query("select u from Kuser u where u.userName = '%:userName%'")
    List<Kuser> findByUserNameLikeIgnoreCase(String userName);

    Optional<Kuser> findByUserName(String userName);
}
