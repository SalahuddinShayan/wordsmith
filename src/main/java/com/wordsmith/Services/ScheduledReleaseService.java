package com.wordsmith.Services;

import com.wordsmith.Entity.NovelReleasePolicy;
import com.wordsmith.Repositories.ChapterRepository;
import com.wordsmith.Repositories.NovelReleasePolicyRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.wordsmith.Enum.ReleaseType;

import java.time.Instant;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.List;

@Service
public class ScheduledReleaseService {

    private static final Logger log =
            LoggerFactory.getLogger(ScheduledReleaseService.class);

    private final NovelReleasePolicyRepository policyRepo;
    private final ChapterReleaseService chapterReleaseService;
    private final ChapterRepository chapterRepo;

    public ScheduledReleaseService(
            NovelReleasePolicyRepository policyRepo,
            ChapterReleaseService chapterReleaseService,
            ChapterRepository chapterRepo

    ) {
        this.policyRepo = policyRepo;
        this.chapterReleaseService = chapterReleaseService;
        this.chapterRepo = chapterRepo;
    }

    // --------------------------------------------------
    // 🕒 Master Scheduler (Hourly, UTC)
    // --------------------------------------------------
    @Transactional
    @Scheduled(cron = "0 0 * * * ?", zone = "UTC") // Top of every hour (UTC)
    public void runReleaseScheduler() {

        ZonedDateTime nowUtc = ZonedDateTime.now(ZoneId.of("UTC"));
        int currentHourUtc = nowUtc.getHour();

        log.info(
            "AUTO_RELEASE_TICK utcTime={} utcHour={}",
            nowUtc,
            currentHourUtc
        );

        // 🔥 Only fetch policies meant for THIS hour
        List<NovelReleasePolicy> policies =
                policyRepo.findByActiveTrueAndReleaseHour(currentHourUtc);

        for (NovelReleasePolicy policy : policies) {
            try {
                evaluatePolicy(policy, nowUtc);
            } catch (Exception ex) {
                log.error(
                        "AUTO_RELEASE_POLICY_ERROR novelName={} msg={}",
                        policy.getNovelName(),
                        ex.getMessage(),
                        ex
                );
            }
        }
    }

    // --------------------------------------------------
    // 🔍 Policy Evaluation
    // --------------------------------------------------
    private void evaluatePolicy(
            NovelReleasePolicy policy,
            ZonedDateTime nowUtc
    ) {

        log.info(
            "EVALUATING_POLICY novel=\"{}\" releaseType={} lastReleaseAt={}",
            policy.getNovelName(),
            policy.getReleaseType(),
            policy.getLastReleaseAt()
        );

        // DAILY → always allowed at its slot
        if (policy.getReleaseType() == ReleaseType.DAILY) {
            chapterReleaseService.releaseChapters(policy, nowUtc);
            return;
        }

        // WEEKLY / CUSTOM → interval check
        if (!isIntervalSatisfied(policy, nowUtc)) {
            log.info(
                "RELEASE_SKIPPED_INTERVAL novel=\"{}\"",
                policy.getNovelName()
            );
            return;
        }

        chapterReleaseService.releaseChapters(policy, nowUtc);
    }

    // --------------------------------------------------
    // 📆 Interval logic
    // --------------------------------------------------
    private boolean isIntervalSatisfied(
            NovelReleasePolicy policy,
            ZonedDateTime nowUtc
    ) {
        Instant lastInstant = chapterRepo.findLastReleasedAt(policy.getNovelName());
        ZonedDateTime lastReleasedAt = null;

        if(lastInstant != null) {
            lastReleasedAt = ZonedDateTime.ofInstant(lastInstant, ZoneId.of("UTC"));
        }

        return switch (policy.getReleaseType()) {

            case WEEKLY ->
                lastReleasedAt
                      .plusWeeks(1)
                      .isBefore(nowUtc);

            case CUSTOM ->
                lastReleasedAt
                      .plusDays(policy.getCustomIntervalDays())
                      .isBefore(nowUtc);

            default ->
                false;
        };
    }
}
