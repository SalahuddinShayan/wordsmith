package com.wordsmith.Repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.wordsmith.Entity.Message;

@Repository
public interface MessageRepository extends JpaRepository<Message, Long> {

}
