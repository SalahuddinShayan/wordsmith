package com.wordsmith.Services;

import com.wordsmith.Entity.Chapter;
import com.wordsmith.Entity.ChapterPurchase;
import com.wordsmith.Entity.UserWallet;
import com.wordsmith.Enum.ReleaseStatus;
import com.wordsmith.Repositories.ChapterPurchaseRepository;
import com.wordsmith.Repositories.ChapterRepository;
import jakarta.transaction.Transactional;

import java.time.Duration;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import org.springframework.stereotype.Service;

@Service
public class ChapterPurchaseService {

    private final ChapterRepository chapterRepo;
    private final ChapterPurchaseRepository purchaseRepo;
    private final ChapterPricingService pricingService;
    private final WalletService walletService;
    public ChapterPurchaseService(
            ChapterRepository chapterRepo,
            ChapterPurchaseRepository purchaseRepo,
            ChapterPricingService pricingService,
            WalletService walletService) {
        this.chapterRepo = chapterRepo;
        this.purchaseRepo = purchaseRepo;
        this.pricingService = pricingService;
        this.walletService = walletService;
    }

    // =====================================================
    // 🔓 PURCHASE CHAPTER (ATOMIC)
    // =====================================================
    @Transactional
    public void purchaseChapter(String username, Long chapterId) {

        // 1️⃣ Load chapter
        Chapter chapter = chapterRepo.findById(chapterId)
                .orElseThrow(() ->
                        new IllegalArgumentException("Chapter not found")
                );

        // 2️⃣ Ensure chapter is STOCKPILE
        if (!ReleaseStatus.STOCKPILE.equals(chapter.getReleaseStatus())) {
            throw new IllegalStateException("Chapter is already released");
        }

        // 3️⃣ Prevent duplicate purchase
        boolean alreadyPurchased = purchaseRepo
                .existsByUsernameAndChapterId(username, chapterId);

        if (alreadyPurchased) {
            throw new IllegalStateException("Chapter already purchased");
        }

        // 4️⃣ Calculate price
        int coinsRequired = pricingService.calculatePrice(
                chapter.getContent().length()
        );

        // 5️⃣ Ensure wallet exists
        UserWallet wallet = walletService.getOrCreateWallet(username);

        // 6️⃣ Check balance
        if (wallet.getBalance() < coinsRequired) {
            throw new IllegalStateException("Insufficient coin balance");
        }

        // 7️⃣ Debit wallet (ledger handled inside)
        walletService.debitCoins(
                username,
                coinsRequired,
                "CHAPTER_PURCHASE",
                "CHAPTER",
                chapterId.toString()
        );

        // 8️⃣ Record chapter purchase
        ChapterPurchase purchase = new ChapterPurchase();
        purchase.setUsername(username);
        purchase.setChapterId(chapterId);
        purchase.setPrice((long)coinsRequired);
        purchase.setPurchasedAt(ZonedDateTime.now());
        purchase.setNovelName(chapter.getNovelName());
        purchase.setChapterNo(chapter.getChapterNo());

        purchaseRepo.save(purchase);
    }

    // =====================================================
    //Last 5 purchases by user
    // =====================================================
    public List<ChapterPurchase> getLastFivePurchases(String username) {
        List<ChapterPurchase> purchases = purchaseRepo.findTop5ByUsernameOrderByPurchasedAtDesc(username);

        for (ChapterPurchase purchase : purchases) {
            purchase.setTimeAgo(formatTimeAgo(purchase.getPurchasedAt()));
        }

        return purchases;
    }

    // --------------------------------------------------
    // ⏱ Utility: "time ago"
    // --------------------------------------------------
    private String formatTimeAgo(ZonedDateTime time) {
        ZonedDateTime now = ZonedDateTime.now();
        Duration duration = Duration.between(time, now);

        if (duration.toMinutes() < 1) {
            return "Just now"; 
        } else if (duration.toMinutes() < 60) {
            return duration.toMinutes() + " minutes ago";
        } else if (duration.toHours() < 24) {
            return duration.toHours() + " hours ago";
        } else if (duration.toDays() < 7) {
            return duration.toDays() + " days ago";
        } else {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MMM dd yyyy");
            return formatter.format(time);
        }
    }

    public List<ChapterPurchase> findPurchasesByUsername(String username) {
        List<ChapterPurchase> purchases = purchaseRepo.findAllByUsernameOrderByPurchasedAtDesc(username);
        for (ChapterPurchase purchase : purchases) {
            purchase.setTimeAgo(formatTimeAgo(purchase.getPurchasedAt()));
        }

        return purchases;
    }

    public List<ChapterPurchase> findAllPurchases() {
        return purchaseRepo.findAllByOrderByPurchasedAtDesc();
    }

}
