package com.wordsmith.Repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.wordsmith.Entity.Comment;
import com.wordsmith.Enum.CommentEntityType;

public interface CommentRepository extends JpaRepository<Comment, Long> {
    List<Comment> findByEntityTypeAndEntityId(CommentEntityType entityType, Long entityId);
    
    void deleteByEntityTypeAndEntityId(CommentEntityType entityType, Long entityId);
    
    @Query("SELECT MAX(c.id) FROM Comment c")
    Long findLastCommentId();
}
