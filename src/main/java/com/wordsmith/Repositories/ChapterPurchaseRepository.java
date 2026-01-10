package com.wordsmith.Repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.wordsmith.Entity.ChapterPurchase;

public interface ChapterPurchaseRepository extends JpaRepository<ChapterPurchase, Long> {
    
    boolean existsByUsernameAndChapterId(String username, Long chapterId);

    List<ChapterPurchase> findTop5ByUsernameOrderByPurchasedAtDesc(String username);

    List<ChapterPurchase> findAllByUsernameOrderByPurchasedAtDesc(String username);

    List<ChapterPurchase> findAllByOrderByPurchasedAtDesc();
}
