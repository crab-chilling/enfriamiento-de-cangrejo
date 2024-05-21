package cpe.asi.cardforge.repository;

import cpe.asi.cardforge.entity.Card;
import cpe.asi.cardforge.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CardRepository extends JpaRepository<Card, Long> {

    @Query("select c from Card c where c.deck.user = :user")
    List<Card> getCardByUser(User user);
}
