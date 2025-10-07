package com.wordsmith.Repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.wordsmith.Entity.Likes;
import com.wordsmith.Enum.CommentEntityType;
import com.wordsmith.Enum.LikeEnum;

@Repository
public interface LikeRepository extends JpaRepository<Likes, Long> {

    public Likes findByEntityTypeAndEntityIdAndUsername(CommentEntityType entityType, long entityId, String username);

    public Long countByEntityIdAndEntityTypeAndLikeStatus(long entityId, CommentEntityType entityType, LikeEnum likeStatus);

    List<Likes> findByUsernameAndEntityTypeAndLikeStatusIn(String username, CommentEntityType entityType, List<LikeEnum> statuses);

    void deleteByEntityTypeAndEntityId(CommentEntityType entityType, Long entityId);



}
