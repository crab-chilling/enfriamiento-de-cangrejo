package fr.crab.store.repository;


import fr.crab.entity.Kuser;
import fr.crab.entity.StoreItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StoreItemRepository extends JpaRepository<StoreItem, Long> {

    List<StoreItem> findAllByUser(Kuser user);
}
