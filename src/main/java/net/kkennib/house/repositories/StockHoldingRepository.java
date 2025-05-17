package net.kkennib.house.repositories;

import net.kkennib.house.models.StockHolding;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface StockHoldingRepository extends JpaRepository<StockHolding, Long> {
    List<StockHolding> findAll(Sort sort);
}