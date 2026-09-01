package com.pasantes_airtek.pasantestv.repository;

import com.pasantes_airtek.pasantestv.model.Channel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ChannelRepository extends JpaRepository<Channel, Long> {
    @Query("SELECT c FROM Channel c WHERE LOWER(c.name) LIKE LOWER(CONCAT('%', :name, '%')) OR SOUNDEX(c.name) = SOUNDEX(:name)")
    List<Channel> findByName(@Param("name") String name);
    @Query("SELECT c FROM Channel c WHERE c.category = :category")
    List<Channel> findByCategory(@Param("category") String category);
}
