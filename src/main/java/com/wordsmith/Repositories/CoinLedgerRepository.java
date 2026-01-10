package com.wordsmith.Repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.wordsmith.Entity.CoinLedger;

public interface CoinLedgerRepository extends JpaRepository<CoinLedger, Long> {

    List<CoinLedger> findByUsername(String username);

    List<CoinLedger> findAllByOrderByCreatedAtDesc();

}
