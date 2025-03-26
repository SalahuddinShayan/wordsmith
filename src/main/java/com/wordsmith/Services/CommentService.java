package com.wordsmith.Services;

import java.util.List;
import org.springframework.stereotype.Service;

import com.wordsmith.Entity.Comment;
import com.wordsmith.Enum.CommentEntityType;
import com.wordsmith.Repositories.CommentRepository;

@Service
public class CommentService {
    private final CommentRepository commentRepository;

    public CommentService(CommentRepository commentRepository) {
        this.commentRepository = commentRepository;
    }

    public Comment saveComment(Comment comment) {
        return commentRepository.save(comment);
    }

    public List<Comment> getCommentsByEntity(CommentEntityType entityType, Long entityId) {
        return commentRepository.findByEntityTypeAndEntityId(entityType, entityId);
    }
    
    public Comment getCommentById(Long commentId) {
        return commentRepository.findById(commentId).orElse(null);
    }

    public void deleteComment(Long commentId) {
    	Comment comment = commentRepository.findById(commentId).orElse(null);
    	boolean hasReplies = comment.isHasReplies();
    	if(hasReplies){
    		CommentEntityType entityType= CommentEntityType.COMMENT;
    		commentRepository.deleteByEntityTypeAndEntityId(entityType, commentId);
    	}
        commentRepository.deleteById(commentId);
    }

    public void flagComment(Long commentId) {
        Comment comment = commentRepository.findById(commentId).orElse(null);
        if (comment != null) {
            comment.setFlagged(true);
            commentRepository.save(comment);
        }
    }
    
    public Long getLastCommentId() {
        return commentRepository.findLastCommentId();
    }
    
    public List<Comment> findallcomment() {
        return commentRepository.findAll();
    }
}
