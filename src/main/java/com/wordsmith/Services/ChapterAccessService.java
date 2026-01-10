package com.wordsmith.Services;

import com.wordsmith.Entity.Chapter;
import com.wordsmith.Enum.ChapterAccessResult;
import com.wordsmith.Enum.ReleaseStatus;
import com.wordsmith.Repositories.ChapterRepository;
import com.wordsmith.Repositories.ChapterPurchaseRepository;
import org.springframework.stereotype.Service;

@Service
public class ChapterAccessService {

    private final ChapterRepository chapterRepo;
    private final ChapterPurchaseRepository purchaseRepo;
    private final ChapterPricingService pricingService;

    public ChapterAccessService(
            ChapterRepository chapterRepo,
            ChapterPurchaseRepository purchaseRepo,
            ChapterPricingService pricingService
    ) {
        this.chapterRepo = chapterRepo;
        this.purchaseRepo = purchaseRepo;
        this.pricingService = pricingService;
    }

    // ------------------------------------------------------------------
    // 🔐 CORE ACCESS DECISION
    // ------------------------------------------------------------------
    public ChapterAccessResult decideAccess(
            Chapter chapter,
            String username   // null if not logged in
    ) {

        // 1️⃣ Released chapters are always free
        if (ReleaseStatus.RELEASED.equals(chapter.getReleaseStatus())) {
            return ChapterAccessResult.ALLOWED;
        }

        // 2️⃣ Stockpile + not logged in
        if (username == null) {
            return ChapterAccessResult.LOGIN_REQUIRED;
        }

        // 3️⃣ Stockpile + logged in + purchased
        boolean ownsChapter = purchaseRepo
                .existsByUsernameAndChapterId(username, chapter.getChapterId());

        if (ownsChapter) {
            return ChapterAccessResult.ALLOWED;
        }

        // 4️⃣ Stockpile + logged in + not purchased
        return ChapterAccessResult.PURCHASE_REQUIRED;
    }

    // ------------------------------------------------------------------
    // 🔒 LOCK STATE FOR UI (DTO SUPPORT)
    // ------------------------------------------------------------------
    public boolean isChapterOwned(Long chapterId, String username) {
        if (username == null) return false;

        return purchaseRepo.existsByUsernameAndChapterId(username, chapterId);
    }

    // ------------------------------------------------------------------
    // 💰 PRICE LOOKUP (READ-ONLY)
    // ------------------------------------------------------------------
    public int getChapterPrice(Long chapterId) {
        Chapter chapter = chapterRepo.findById(chapterId)
                .orElseThrow(() -> new IllegalArgumentException("Chapter not found"));
        

        return pricingService.calculatePrice(chapter.getContent().length());
    }
}
