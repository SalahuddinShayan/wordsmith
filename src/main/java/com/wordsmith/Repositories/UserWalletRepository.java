package com.wordsmith.Repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.wordsmith.Entity.UserWallet;

public interface UserWalletRepository extends JpaRepository<UserWallet, Long> {

    public Optional<UserWallet> findByUsername(String username);

    List<UserWallet> findAllByOrderByUpdatedAtDesc();

}
