package com.wordsmith.Repositories;

import java.time.Instant;
import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.wordsmith.Entity.Chapter;
import com.wordsmith.Enum.ReleaseStatus;

@Repository
public interface ChapterRepository extends JpaRepository<Chapter, Long> {
	
	@Query(value = "SELECT * FROM chapter WHERE novel_name = :novelname order by chapter_id desc", nativeQuery = true)
    List<Chapter>  byNovelName(@Param("novelname") String novelname);
	
	@Query(value = "SELECT * FROM chapter WHERE novel_name = :novelname AND release_status = 'RELEASED' order by chapter_id desc", nativeQuery = true)
    List<Chapter>  byNovelNameReleased(@Param("novelname") String novelname);

	@Query(value = "SELECT * FROM chapter WHERE novel_name = :novelname AND release_status = 'STOCKPILE' ORDER BY chapter_id desc", nativeQuery = true)
	List<Chapter>  byNovelNameStockpile(@Param("novelname") String novelname);
	
	@Query(value = "SELECT * FROM chapter order by chapter_id desc", nativeQuery = true)
    List<Chapter>  AllChapterd();
	
	@Query(value = "SELECT chapter_id FROM chapter WHERE novel_name = :novelname and chapter_no = :chapterno", nativeQuery = true)
    long  findChapterId(@Param("novelname") String novelname, @Param("chapterno") String chapterno);
	
	@Query(value = "select min(chapter_id) from chapter where novel_name = :novelname and chapter_id > :chapterid;", nativeQuery = true)
    long  NextChapterId(@Param("novelname") String novelname, @Param("chapterid") long chapterid);
	
	@Query(value = "select max(chapter_id) from chapter where novel_name = :novelname and chapter_id < :chapterid;", nativeQuery = true)
    long  PreviousChapterId(@Param("novelname") String novelname, @Param("chapterid") long chapterid);
	
	@Query(value = "SELECT * FROM chapter WHERE novel_name = :novelname AND release_status = 'RELEASED' order by chapter_id desc limit 3;", nativeQuery = true)
    List<Chapter>  Latest(@Param("novelname") String novelname);

	@Query(value = "select * from chapter where novel_name = :novelname and release_status = 'STOCKPILE' order by chapter_id desc limit 3;", nativeQuery = true)
	List<Chapter>  stockpileLatest(@Param("novelname") String novelname);
	
	@Query(value = "SELECT * FROM chapter WHERE novel_name = :novelname AND release_status = 'RELEASED' order by chapter_id desc limit 1;", nativeQuery = true)
    Chapter  Latest1(@Param("novelname") String novelname);
	
	@Query(value = "SELECT chapter_id FROM chapter order by chapter_id desc limit 1;", nativeQuery = true)
    long  Last();
	
	@Query(value = "SELECT chapter_id FROM chapter WHERE novel_name = :novelname AND release_status = 'RELEASED' order by chapter_id desc limit 1;", nativeQuery = true)
    long  LastReleased(@Param("novelname") String novelname);
	
	@Query(value = "SELECT chapter_id FROM chapter WHERE novel_name = :novelname AND release_status = 'RELEASED' order by chapter_id limit 1;", nativeQuery = true)
    long  First(@Param("novelname") String novelname);
	
	@Query(value = "SELECT * FROM chapter WHERE release_status = 'RELEASED' order by COALESCE( released_on, posted_on) desc limit 20;", nativeQuery = true)
    List<Chapter>  Latest20();
	
	Chapter findFirstByReleaseStatusAndNovelNameOrderByChapterIdAsc(ReleaseStatus status, String novelName);

	int countByReleaseStatusAndNovelName(ReleaseStatus status, String novelName);

	@Query(value = "SELECT * FROM chapter WHERE novel_name = :novelname AND release_status = 'STOCKPILE' ORDER BY chapter_id ASC", nativeQuery = true)
	List<Chapter> findStockpiledChapters(@Param("novelname") String novelname, Pageable pageable);

	@Query(value = "SELECT COALESCE(released_on, posted_on) FROM chapter WHERE novel_name = :novelname AND release_status = 'RELEASED' ORDER BY COALESCE(released_on, posted_on) DESC LIMIT 1", nativeQuery = true)
	Instant findLastReleasedAt(@Param("novelname") String novelname);

}
