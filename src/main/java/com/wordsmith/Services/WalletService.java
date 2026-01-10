package com.wordsmith.Services;

import com.wordsmith.Entity.UserWallet;
import com.wordsmith.Entity.CoinLedger;
import com.wordsmith.Repositories.UserWalletRepository;
import com.wordsmith.Repositories.CoinLedgerRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.ZonedDateTime;
import java.util.List;

@Service
public class WalletService {

    private final UserWalletRepository walletRepo;
    private final CoinLedgerRepository ledgerRepo;

    public WalletService(
            UserWalletRepository walletRepo,
            CoinLedgerRepository ledgerRepo
    ) {
        this.walletRepo = walletRepo;
        this.ledgerRepo = ledgerRepo;
    }

    // --------------------------------------------------
    // 🧾 READ
    // --------------------------------------------------
    public long getBalance(String username) {
        return walletRepo.findByUsername(username)
                .map(UserWallet::getBalance)
                .orElse(0L);
    }

    // --------------------------------------------------
    // ➕ CREDIT
    // --------------------------------------------------
    @Transactional
    public void creditCoins(String username,
                            long amount,
                            String reason,
                            String referenceType,
                            String referenceId) {

        if (amount <= 0) {
            throw new IllegalArgumentException("Credit amount must be positive");
        }

        applyDelta(username, amount, reason, referenceType, referenceId);
    }

    // --------------------------------------------------
    // ➖ DEBIT
    // --------------------------------------------------
    @Transactional
    public void debitCoins(String username,
                           long amount,
                           String reason,
                           String referenceType,
                           String referenceId) {

        if (amount <= 0) {
            throw new IllegalArgumentException("Debit amount must be positive");
        }

        applyDelta(username, -amount, reason, referenceType, referenceId);
    }

    // --------------------------------------------------
    // 🔒 INTERNAL CORE
    // --------------------------------------------------
    private void applyDelta(String username,
                            long delta,
                            String reason,
                            String referenceType,
                            String referenceId) {

        UserWallet wallet = getOrCreateWallet(username);

        long newBalance = wallet.getBalance() + delta;

        if (newBalance < 0) {
            throw new IllegalStateException(
                    "Insufficient balance for user: " + username
            );
        }

        wallet.setBalance(newBalance);
        wallet.setUpdatedAt(ZonedDateTime.now());
        walletRepo.save(wallet);

        CoinLedger ledger = new CoinLedger();
        ledger.setUsername(username);
        ledger.setDelta(delta);
        ledger.setReason(reason);
        ledger.setReferenceType(referenceType);
        ledger.setReferenceId(referenceId);
        ledger.setBalanceAfter(newBalance);
        ledger.setCreatedAt(ZonedDateTime.now());

        ledgerRepo.save(ledger);
    }

    public UserWallet getOrCreateWallet(String username) {
        return walletRepo.findByUsername(username)
                .orElseGet(() -> {
                UserWallet wallet = new UserWallet();
                wallet.setUsername(username);
                wallet.setBalance(0L);
                return walletRepo.save(wallet);
                });
    }


    public List<UserWallet> getAllWallets() {
        return walletRepo.findAllByOrderByUpdatedAtDesc();
    }

    public List<CoinLedger> getAllLedgers() {
        return ledgerRepo.findAllByOrderByCreatedAtDesc();
    }
}
