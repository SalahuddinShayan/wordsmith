package com.wordsmith.Repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.wordsmith.Entity.NovelReleasePolicy;

public interface NovelReleasePolicyRepository extends JpaRepository<NovelReleasePolicy, Long> {

    List<NovelReleasePolicy> findByActiveTrue();
    List<NovelReleasePolicy> findByActiveTrueAndReleaseHour(int releaseHour);


}
