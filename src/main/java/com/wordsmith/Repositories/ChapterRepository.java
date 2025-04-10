package com.wordsmith.Repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.wordsmith.Entity.Chapter;

@Repository
public interface ChapterRepository extends JpaRepository<Chapter, Long> {
	
	@Query(value = "SELECT * FROM chapter WHERE novel_name = :novelname order by chapter_id desc", nativeQuery = true)
    List<Chapter>  byNovelName(@Param("novelname") String novelname);
	
	@Query(value = "SELECT * FROM chapter order by chapter_id desc", nativeQuery = true)
    List<Chapter>  AllChapterd();
	
	@Query(value = "SELECT chapter_id FROM chapter WHERE novel_name = :novelname and chapter_no = :chapterno", nativeQuery = true)
    long  findChapterId(@Param("novelname") String novelname, @Param("chapterno") String chapterno);
	
	@Query(value = "select min(chapter_id) from chapter where novel_name = :novelname and chapter_id > :chapterid;", nativeQuery = true)
    long  NextChapterId(@Param("novelname") String novelname, @Param("chapterid") long chapterid);
	
	@Query(value = "select max(chapter_id) from chapter where novel_name = :novelname and chapter_id < :chapterid;", nativeQuery = true)
    long  PreviousChapterId(@Param("novelname") String novelname, @Param("chapterid") long chapterid);
	
	@Query(value = "SELECT * FROM chapter WHERE novel_name = :novelname order by chapter_id desc limit 3;", nativeQuery = true)
    List<Chapter>  Latest(@Param("novelname") String novelname);
	
	@Query(value = "SELECT * FROM chapter WHERE novel_name = :novelname order by chapter_id desc limit 1;", nativeQuery = true)
    Chapter  Latest1(@Param("novelname") String novelname);
	
	@Query(value = "SELECT chapter_id FROM chapter order by chapter_id desc limit 1;", nativeQuery = true)
    long  Last();
	
	@Query(value = "SELECT * FROM chapter order by chapter_id desc limit 20;", nativeQuery = true)
    List<Chapter>  Latest20();

}
