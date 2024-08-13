package com.wordsmith.Repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.wordsmith.Entity.Genres;

@Repository
public interface GenreRepository extends JpaRepository<Genres,String> {

}
