package cpe.asi.cardforge.repository;

import cpe.asi.cardforge.entity.Kuser;
import cpe.asi.cardforge.entity.StoreItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StoreItemRepository extends JpaRepository<StoreItem, Long> {

    List<StoreItem> findAllByUser(Kuser user);
}
