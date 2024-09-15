package com.wordsmith.Repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.wordsmith.Entity.Chapter;

@Repository
public interface ChapterRepository extends JpaRepository<Chapter, Long> {
	
	@Query(value = "SELECT * FROM Chapter WHERE Novel_Name = :novelname", nativeQuery = true)
    List<Chapter>  byNovelName(@Param("novelname") String novelname);
	
	@Query(value = "SELECT Chapter_Id FROM Chapter WHERE Novel_Name = :novelname and chapter_no = :chapterno", nativeQuery = true)
    long  findChapterId(@Param("novelname") String novelname, @Param("chapterno") String chapterno);
	
	@Query(value = "SELECT Chapter_Id FROM Chapter WHERE Novel_Name = :novelname AND Chapter_Id = (select min(Chapter_Id) from Chapter where Chapter_Id > :chapterid)", nativeQuery = true)
    long  NextChapterId(@Param("novelname") String novelname, @Param("chapterid") long chapterid);
	
	@Query(value = "SELECT Chapter_Id FROM Chapter WHERE Novel_Name = :novelname AND Chapter_Id = (select max(Chapter_Id) from Chapter where Chapter_Id < :chapterid)", nativeQuery = true)
    long  PreviousChapterId(@Param("novelname") String novelname, @Param("chapterid") long chapterid);

}
