package com.wordsmith.Repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.wordsmith.Entity.Message;

@Repository
public interface MessageRepository extends JpaRepository<Message, Long> {
	
	@Query(value = "SELECT message_id FROM message order by message_id desc limit 1;", nativeQuery = true)
    long  Last();

}
