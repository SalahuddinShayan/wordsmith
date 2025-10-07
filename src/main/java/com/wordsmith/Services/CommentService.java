package com.wordsmith.Services;

import java.util.List;
import org.springframework.stereotype.Service;

import com.wordsmith.Entity.Comment;
import com.wordsmith.Enum.CommentEntityType;
import com.wordsmith.Repositories.ChapterRepository;
import com.wordsmith.Repositories.CommentRepository;
import com.wordsmith.Repositories.LikeRepository;
import com.wordsmith.Repositories.NovelRepository;

@Service
public class CommentService {
    private final CommentRepository commentRepository;
    private final ChapterRepository chapterRepository;
    private final NovelRepository novelRepository;
    private final LikeRepository likeRepository;

    public CommentService(CommentRepository commentRepository, ChapterRepository chapterRepository, NovelRepository novelRepository, LikeRepository likeRepository) {
        this.commentRepository = commentRepository;
        this.chapterRepository = chapterRepository;
        this.novelRepository = novelRepository;
        this.likeRepository = likeRepository;
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
        likeRepository.deleteByEntityTypeAndEntityId(CommentEntityType.COMMENT, commentId);
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

    public List<Comment> getCommentsByUser(String userName) {
        return commentRepository.findByUserName(userName);
    }

    public List<Comment> getUserComments(String username) {
    List<Comment> comments = commentRepository.findByUserName(username);
    for (Comment comment : comments) {
        // attach replies
        if (comment.isHasReplies()) {
            comment.setReplies(getCommentsByEntity(CommentEntityType.COMMENT, comment.getId()));
        }
    }
    return comments;
    }

    public List<Comment> getUserReplies(String username) {
    List<Comment> replies = commentRepository.findByUserNameAndEntityType(username, CommentEntityType.COMMENT);

    for (Comment reply : replies) {
        // attach parent comment
        if (reply.getEntityId() != null) {
            Comment parentComment = getCommentById(reply.getEntityId());
            reply.setParentComment(parentComment);
            if(reply.getParentComment().getEntityType() == CommentEntityType.CHAPTER){
                reply.setChapter(chapterRepository.findById(reply.getParentComment().getEntityId()).orElse(null));
            } else if (reply.getParentComment().getEntityType() == CommentEntityType.NOVEL) {
                reply.setNovel(novelRepository.findById(reply.getParentComment().getEntityId() != null ? reply.getParentComment().getEntityId().intValue() : null).orElse(null));
            }
        }
    }
    return replies;
    }

    public List<Comment> getUserCommentsExcludingReplies(String username) {
    List<Comment> comments = commentRepository.findByUserNameExcludingReplies(username);
    for (Comment comment : comments) {
        if(comment.getEntityType() == CommentEntityType.CHAPTER){
            comment.setChapter(chapterRepository.findById(comment.getEntityId()).orElse(null));
        } else if (comment.getEntityType() == CommentEntityType.NOVEL) {
            comment.setNovel(novelRepository.findById(comment.getEntityId() != null ? comment.getEntityId().intValue() : null).orElse(null));
        } 
        // attach replies
        if (comment.isHasReplies()) {
            comment.setReplies(getCommentsByEntity(CommentEntityType.COMMENT, comment.getId()));
        }
    }

    return comments;
    }

}
