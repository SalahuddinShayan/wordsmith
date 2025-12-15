package com.wordsmith.Services;

import java.time.ZoneId;
import java.time.ZonedDateTime;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.wordsmith.Entity.Chapter;
import com.wordsmith.Enum.ReleaseStatus;
import com.wordsmith.Repositories.ChapterRepository;

import jakarta.transaction.Transactional;

@Service
public class ScheduledReleaseService {

    private static final Logger log = LoggerFactory.getLogger(ScheduledReleaseService.class);

    private final ChapterRepository chapterRepository;

    public ScheduledReleaseService(ChapterRepository chapterRepository) {
        this.chapterRepository = chapterRepository;
    }

    // --------------------------------------------------
    //  Helper: Release next STOCKPILE chapter for novel
    // --------------------------------------------------
    private void releaseNextChapter(String novelName, String slotLabel) {
        log.info("SCHEDULE_RELEASE_START slot={} novel=\"{}\"", slotLabel, novelName);

        try {
            Chapter nextChapter =
                    chapterRepository.findFirstByReleaseStatusAndNovelNameOrderByChapterIdAsc(
                            ReleaseStatus.STOCKPILE,
                            novelName
                    );

            if (nextChapter == null) {
                log.info("SCHEDULE_NO_STOCKPILE slot={} novel=\"{}\"", slotLabel, novelName);
                return;
            }

            nextChapter.setReleaseStatus(ReleaseStatus.RELEASED);
            ZonedDateTime serverTime = ZonedDateTime.now(ZoneId.systemDefault());
            nextChapter.setReleasedOn(serverTime);
            chapterRepository.save(nextChapter);

            log.info("SCHEDULE_RELEASED slot={} novel=\"{}\" chapterId={} chapterNo={} releasedOn={}",
                    slotLabel,
                    novelName,
                    nextChapter.getChapterId(),
                    nextChapter.getChapterNo(),
                    serverTime);

        } catch (Exception e) {
            log.error("SCHEDULE_RELEASE_ERROR slot={} novel=\"{}\" message={}",
                    slotLabel, novelName, e.getMessage(), e);
        }
    }

    // --------------------------------------------------
    //  Night / Morning slots (UTC)
    // --------------------------------------------------

    @Transactional
    @Scheduled(cron = "0 0 0 * * ?", zone = "UTC") // Every day at Midnight UTC / 5:30AM IST
    public void autoReleaseMidNight() {
        releaseNextChapter("The Demon God Wants to Live Peacefully", "00:00-UTC");
    }

    @Transactional
    @Scheduled(cron = "0 0 1 * * ?", zone = "UTC") // Every day at 1 AM UTC / 6:30AM IST
    public void autoRelease1am() {
        releaseNextChapter("Regression: I Alone Possess Infinite Traits", "01:00-UTC");
    }

    @Transactional
    @Scheduled(cron = "0 0 2 * * ?", zone = "UTC") // Every day at 2 AM UTC / 7:30AM IST
    public void autoRelease2am() {
        releaseNextChapter("Solo Sword Master", "02:00-UTC");
    }

    @Transactional
    @Scheduled(cron = "0 0 3 * * ?", zone = "UTC") // Every day at 3 AM UTC / 8:30AM IST
    public void autoRelease3am() {
        releaseNextChapter("Ability User from a Parallel Dimension", "03:00-UTC");
    }

    @Transactional
    @Scheduled(cron = "0 0 4 * * ?", zone = "UTC") // Every day at 4 AM UTC / 9:30AM IST
    public void autoRelease4am() {
        releaseNextChapter("The Demon King's Game", "04:00-UTC");
    }

    @Transactional
    @Scheduled(cron = "0 0 5 * * ?", zone = "UTC") // Every day at 5 AM UTC / 10:30AM IST
    public void autoRelease5am() {
        releaseNextChapter("The Crazy Villain Regains His Sanity", "05:00-UTC");
    }

    // --------------------------------------------------
    //  Noon / Evening slots (UTC)
    // --------------------------------------------------

    @Transactional
    @Scheduled(cron = "0 0 12 * * ?", zone = "UTC") // Every day at 12 Noon UTC / 5:30PM IST
    public void autoReleasenoon() {
        releaseNextChapter("Sword of Beelzebuth", "12:00-UTC");
    }

    @Transactional
    @Scheduled(cron = "0 0 13 * * ?", zone = "UTC") // Every day at 1 PM UTC / 6:30PM IST
    public void autoRelease1pm() {
        releaseNextChapter("The Skill Collector", "13:00-UTC");
    }

    @Transactional
    @Scheduled(cron = "0 0 14 * * ?", zone = "UTC") // Every day at 2 PM UTC / 7:30PM IST
    public void autoRelease2pm() {
        releaseNextChapter(
                "I Was Judged as Jobless and Banished After Choosing a Supercharged Growth Rate Skill. " +
                "A Skill Maniac rescued me, but I Don’t Want to Get Too Involved",
                "14:00-UTC"
        );
    }

    @Transactional
    @Scheduled(cron = "0 0 15 * * ?", zone = "UTC") // Every day at 3 PM UTC / 8:30PM IST
    public void autoRelease3pm() {
        releaseNextChapter("I Think My Glasses Could Probably Take Over the World", "15:00-UTC");
    }

    @Transactional
    @Scheduled(cron = "0 0 16 * * ?", zone = "UTC") // Every day at 4 PM UTC / 9:30PM IST
    public void autoRelease4pm() {
        releaseNextChapter("Left Behind Swordsman", "16:00-UTC");
    }

    @Transactional
    @Scheduled(cron = "0 0 17 * * ?", zone = "UTC") // Every day at 5 PM UTC / 10:30PM IST
    public void autoRelease5pm() {
        releaseNextChapter(
                "The Sword Saint Reincarnated as a Shota Prince Absolutely Refuses to Let His Former Disciple Find Out!",
                "17:00-UTC"
        );
    }

    @Transactional
    @Scheduled(cron = "0 0 18 * * ?", zone = "UTC") // Every day at 6 PM UTC / 11:30PM IST
    public void autoRelease6pm() {
        releaseNextChapter("The Magician Kunon Sees Everything", "18:00-UTC");
    }
}