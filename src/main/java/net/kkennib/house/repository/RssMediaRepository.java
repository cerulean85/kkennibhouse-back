package net.kkennib.house.repository;


import net.kkennib.house.model.RssMedia;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;


public interface RssMediaRepository extends JpaRepository<RssMedia, Long> {
    List<RssMedia> findByName(String name);

    @Query("SELECT rm FROM RssMedia rm WHERE rm.name LIKE %:name%")
    List<RssMedia> searchByNameLike(String name);
}
