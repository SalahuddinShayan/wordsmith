package com.wordsmith.Repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.wordsmith.Entity.Views;

@Repository
public interface ViewsRepository extends JpaRepository<Views,Long>{

}
