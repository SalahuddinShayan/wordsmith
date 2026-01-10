package com.wordsmith.Services;

import com.wordsmith.Entity.Chapter;
import com.wordsmith.Entity.NovelReleasePolicy;
import com.wordsmith.Enum.ReleaseStatus;
import com.wordsmith.Repositories.ChapterRepository;
import com.wordsmith.Repositories.NovelReleasePolicyRepository;
import com.wordsmith.Repositories.NovelRepository;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.List;

@Service
public class ChapterReleaseService {

    private static final Logger log =
            LoggerFactory.getLogger(ChapterReleaseService.class);

    private final ChapterRepository chapterRepo;
    private final NovelReleasePolicyRepository policyRepo;
    private final NovelRepository novelRepo;

    public ChapterReleaseService(
            ChapterRepository chapterRepo, NovelReleasePolicyRepository policyRepo, NovelRepository novelRepo
    ) {
        this.chapterRepo = chapterRepo;
        this.policyRepo = policyRepo;
        this.novelRepo = novelRepo;
    }

    // --------------------------------------------------
    // 🚀 MAIN ENTRY POINT (called by scheduler)
    // --------------------------------------------------
    @Transactional
    public void releaseChapters(
            NovelReleasePolicy policy,
            ZonedDateTime now
    ) {

        String novelName = policy.getNovelName();

        String novelStatus = novelRepo.findStatusByNovelName(novelName);
        if("Hiatus".equalsIgnoreCase(novelStatus) || "Dropped".equalsIgnoreCase(novelStatus)) {
            log.info(
                    "RELEASE_SKIPPED_NOVEL_STATUS novel=\"{}\" status={}",
                    novelName,
                    novelStatus
            );
            return;
        }

        // 1️⃣ Count stockpiled chapters
        long stockpileCount =
                chapterRepo.countByReleaseStatusAndNovelName(ReleaseStatus.STOCKPILE, novelName);

        if ("Ongoing".equalsIgnoreCase(novelStatus) && stockpileCount <= policy.getMinStockpileRequired()) {
            log.info(
                "RELEASE_SKIPPED_LOW_STOCKPILE novel=\"{}\" stockpile={} minRequired={}",
                novelName,
                stockpileCount,
                policy.getMinStockpileRequired()
            );
            return;
        }

        // 2️⃣ Fetch next chapters to release
        List<Chapter> chaptersToRelease =
                chapterRepo.findStockpiledChapters(novelName, PageRequest.of(0, policy.getChaptersPerRelease()));

        if (chaptersToRelease.isEmpty()) {
            log.info(
                "RELEASE_SKIPPED_NO_CHAPTERS novel=\"{}\"",
                novelName
            );
            return;
        }

        if (chaptersToRelease.size() < policy.getChaptersPerRelease()) {
            log.info(
                "RELEASE_SKIPPED_INCOMPLETE_BATCH novel=\"{}\" available={} required={}",
                novelName,
                chaptersToRelease.size(),
                policy.getChaptersPerRelease()
            );
            return;
        }


        ZonedDateTime releaseTime =
                now.withZoneSameInstant(ZoneId.systemDefault());

        // 3️⃣ Release chapters
        for (Chapter chapter : chaptersToRelease) {
            chapter.setReleaseStatus(ReleaseStatus.RELEASED);
            chapter.setReleasedOn(releaseTime);
            chapterRepo.save(chapter);

            log.info(
                "CHAPTER_RELEASED novel=\"{}\" chapterId={} chapterNo={} releasedOn={}",
                novelName,
                chapter.getChapterId(),
                chapter.getChapterNo(),
                releaseTime
            );
        }

        // 4️⃣ Update policy last release time
        policy.setLastReleaseAt(releaseTime);
        policyRepo.save(policy);

        log.info(
            "RELEASE_BATCH_COMPLETE novel=\"{}\" releasedCount={}",
            novelName,
            chaptersToRelease.size()
        );
    }
}
