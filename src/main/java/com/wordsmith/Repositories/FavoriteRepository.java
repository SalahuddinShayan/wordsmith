package com.wordsmith.Repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.wordsmith.Entity.Favorite;

@Repository
public interface FavoriteRepository extends JpaRepository<Favorite, Long> {

    boolean existsByUsernameAndNovelId(String username, Long novelId);

    void deleteByUsernameAndNovelId(String username, Long novelId);

    long countByNovelId(Long novelId);

    List<Favorite> findByUsername(String username); // For user’s favorites list
}

