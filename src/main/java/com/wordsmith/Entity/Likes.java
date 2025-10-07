package com.wordsmith.Entity;

import jakarta.persistence.Transient;

import com.wordsmith.Enum.CommentEntityType;
import com.wordsmith.Enum.LikeEnum;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table
public class Likes {

    @Id
    @Column
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long likeId;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private CommentEntityType entityType;

    @Column
    private Long entityId;

    @Column
    private String username;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private LikeEnum likeStatus;


    public long getLikeId() {
        return likeId;
    }

    public void setLikeId(long likeId) {
        this.likeId = likeId;
    }

    public CommentEntityType getEntityType() {
        return entityType;
    }

    public void setEntityType(CommentEntityType entityType) {
        this.entityType = entityType;
    }

    public long getEntityId() {
        return entityId;
    }

    public void setEntityId(long entityId) {
        this.entityId = entityId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public LikeEnum getLikeStatus() {
        return likeStatus;
    }

    public void setLikeStatus(LikeEnum likeStatus) {
        this.likeStatus = likeStatus;
    }

    public void setLikeId(Long likeId) {
        this.likeId = likeId;
    }

    public void setEntityId(Long entityId) {
        this.entityId = entityId;
    }

}
