package com.wordsmith.Repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.wordsmith.Entity.Novel;

@Repository
public interface NovelRepository extends JpaRepository<Novel,Integer> {
	
	@Query(value = "SELECT * FROM novel WHERE Novel_Name = :novelname", nativeQuery = true)
    Novel  byNovelName(@Param("novelname") String novelname);

}
