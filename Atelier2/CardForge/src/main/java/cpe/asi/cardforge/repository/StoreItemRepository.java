package cpe.asi.cardforge.repository;

import cpe.asi.cardforge.entity.StoreItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StoreItemRepository extends JpaRepository<StoreItem, Long> {
}
