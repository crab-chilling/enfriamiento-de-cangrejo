package cpe.asi.cardforge.repository;

import cpe.asi.cardforge.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByEmailAddress(String emailAddress);

    @Query("select u from User u where u.userName = '%:userName%'")
    List<User> findByUserNameLikeIgnoreCase(String userName);

    Optional<User> findByUserName(String userName);
}
