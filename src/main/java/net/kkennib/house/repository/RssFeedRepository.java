package net.kkennib.house.repository;

import net.kkennib.house.model.RssFeed;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface RssFeedRepository extends JpaRepository<RssFeed, Long> {
    @Query("SELECT rf FROM RssFeed rf WHERE rf.title LIKE %:title%")
    List<RssFeed> searchByTitleLike(@Param("title") String title);

    List<RssFeed> findByMediaIdx(String mediaIdx);
}
