package net.kkennib.house.repository;

import net.kkennib.house.model.StockHolding;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface StockHoldingRepository extends JpaRepository<StockHolding, Long> {
    List<StockHolding> findAll(Sort sort);
}