package com.wordsmith.Repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.wordsmith.Entity.Views;
import com.wordsmith.Enum.CommentEntityType;

@Repository
public interface ViewsRepository extends JpaRepository<Views,Long>{

    public Views findByEntityTypeAndEntityId(CommentEntityType entityType, Long entityId);

    @Query(value = "select views from views where entity_id = :entityId and entity_type = :entityType;", nativeQuery = true)
    Long getTotalViewsByEntity(@Param("entityId") Long entityId,
                          @Param("entityType") String entityType);

    @Query(value = "select coalesce(sum(v.views), 0) " +
               "from views v " +
               "join chapter c on v.entity_id=c.chapter_id " +
               "where v.entity_type='CHAPTER' and c.novel_name=:novelname",
       nativeQuery = true)
    public Long totalViewsBynovelName(String novelname);



}
