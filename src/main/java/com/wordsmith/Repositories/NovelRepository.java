package com.wordsmith.Repositories;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.wordsmith.Entity.Novel;

import jakarta.transaction.Transactional;

@Repository
public interface NovelRepository extends JpaRepository<Novel,Integer> {
	
	@Query(value = "SELECT * FROM novel WHERE Novel_Name = :novelname", nativeQuery = true)
    Novel  byNovelName(@Param("novelname") String novelname);
	
	@Query(value = "select s.* from (select n.* from novel n inner join chapter c on n.novel_name = c.novel_name where c.release_status = 'RELEASED' order by COALESCE( c.released_on, c.posted_on) desc LIMIT 0,200) s group by s.novel_name LIMIT 0,12;", nativeQuery = true)
    List<Novel>  NovelUpdates();

	@Query(value = "select s.* from (select n.* from novel n inner join chapter c on n.novel_name = c.novel_name where c.release_status = 'STOCKPILE' order by c.posted_on desc LIMIT 0,200) s group by s.novel_name LIMIT 0,12;", nativeQuery = true)
	List<Novel>  StockpileUpdates();
	
	@Query(value = "select s.* from (select n.* from novel n inner join chapter c on n.novel_name = c.novel_name where c.release_status = 'RELEASED' order by COALESCE( c.released_on, c.posted_on) desc LIMIT 0,10000) s group by s.novel_name;", nativeQuery = true)
    List<Novel>  AllUpdates();

	@Query(value = "select s.* from (select n.* from novel n inner join chapter c on n.novel_name = c.novel_name where c.release_status = 'STOCKPILE' order by c.posted_on desc LIMIT 0,10000) s group by s.novel_name;", nativeQuery = true)
	List<Novel> AllStockpileUpdates();
	
	@Query(value = "SELECT * FROM novel WHERE LENGTH(Novel_image)>0 AND status = 'Ongoing' limit 4;", nativeQuery = true)
	List<Novel>  popular();
	
	@Query(value = "SELECT novel_name FROM novel WHERE status = 'Ongoing' order by novel_id;", nativeQuery = true)
	List<String>  allNovelName();

	@Query(value = "SELECT * FROM novel WHERE status = 'Ongoing' order by novel_id;", nativeQuery = true)
	List<Novel> findOngoing();

	@Transactional
	@Modifying
	@Query(value = "Update novel set status = :status where novel_name = :novelname", nativeQuery = true)
	void updateNovelStatus(@Param("status") String status, @Param("novelname") String novelname);

	@Query(value = "SELECT status FROM novel WHERE novel_name = :novelname", nativeQuery = true)
	String novelStatus(String novelname);

	@Query(value = "SELECT status FROM novel WHERE novel_name = :novelname", nativeQuery = true)
	String findStatusByNovelName(@Param("novelname") String novelname);

}
