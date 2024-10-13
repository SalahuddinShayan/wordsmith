package com.wordsmith.Repositories;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.wordsmith.Entity.Novel;

@Repository
public interface NovelRepository extends JpaRepository<Novel,Integer> {
	
	@Query(value = "SELECT * FROM novel WHERE Novel_Name = :novelname", nativeQuery = true)
    Novel  byNovelName(@Param("novelname") String novelname);
	
	@Query(value = "select s.* from (select n.* from novel n inner join chapter c on n.novel_name = c.novel_name order by c.posted_on desc LIMIT 0,20) s group by s.novel_name;", nativeQuery = true)
    List<Novel>  NovelUpdates();
	
	@Query(value = "SELECT * FROM novel WHERE LENGTH(Novel_image)>0;", nativeQuery = true)
	List<Novel>  popular();

}
