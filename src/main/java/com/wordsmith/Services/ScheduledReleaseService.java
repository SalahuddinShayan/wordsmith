package com.wordsmith.Services;

import java.time.ZoneId;
import java.time.ZonedDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.wordsmith.Entity.Chapter;
import com.wordsmith.Enum.ReleaseStatus;
import com.wordsmith.Repositories.ChapterRepository;

import jakarta.transaction.Transactional;

@Service
public class ScheduledReleaseService {

    @Autowired
    private ChapterRepository chapterRepository;

    @Transactional
    @Scheduled(cron = "0 0 0 * * ?", zone = "UTC") // Every day at MidNight UTC/ 5:30AM IST
    public void autoReleaseMidNight() {
        Chapter nextChapter = chapterRepository.findFirstByReleaseStatusAndNovelNameOrderByChapterIdAsc(ReleaseStatus.STOCKPILE, "The Demon God Wants to Live Peacefully");
           

        if (nextChapter != null) {
            nextChapter.setReleaseStatus(ReleaseStatus.RELEASED);
            ZonedDateTime serverTime = ZonedDateTime.now(ZoneId.systemDefault());
            nextChapter.setReleasedOn(serverTime);
            chapterRepository.save(nextChapter);
            System.out.println("✅ Auto-released: The Demon God Wants to Live Peacefully " + nextChapter.getChapterNo());
        } else {
            System.out.println("ℹ️ The Demon God Wants to Live Peacefully has no chapters scheduled for release.");
        }
    }
    
    @Transactional
    @Scheduled(cron = "0 0 1 * * ?", zone = "UTC") // Every day at 1 AM UTC/ 6:30AM IST
    public void autoRelease1am() {
        Chapter nextChapter = chapterRepository.findFirstByReleaseStatusAndNovelNameOrderByChapterIdAsc(ReleaseStatus.STOCKPILE, "Regression: I Alone Possess Infinite Traits");
           

        if (nextChapter != null) {
            nextChapter.setReleaseStatus(ReleaseStatus.RELEASED);
            ZonedDateTime serverTime = ZonedDateTime.now(ZoneId.systemDefault());
            nextChapter.setReleasedOn(serverTime);
            chapterRepository.save(nextChapter);
            System.out.println("✅ Auto-released- Regression: I Alone Possess Infinite Traits " + nextChapter.getChapterNo());
        } else {
            System.out.println("ℹ️ Regression: I Alone Possess Infinite Traits has no chapters scheduled for release.");
        }
    }
    
    @Transactional
    @Scheduled(cron = "0 0 2 * * ?", zone = "UTC") // Every day at 2 AM UTC/ 7:30AM IST
    public void autoRelease2am() {
        Chapter nextChapter = chapterRepository.findFirstByReleaseStatusAndNovelNameOrderByChapterIdAsc(ReleaseStatus.STOCKPILE, "The Cop Is Too Strong");
           

        if (nextChapter != null) {
            nextChapter.setReleaseStatus(ReleaseStatus.RELEASED);
            ZonedDateTime serverTime = ZonedDateTime.now(ZoneId.systemDefault());
            nextChapter.setReleasedOn(serverTime);
            chapterRepository.save(nextChapter);
            System.out.println("✅ Auto-released- The Cop Is Too Strong: " + nextChapter.getChapterNo());
        } else {
            System.out.println("ℹ️ The Cop Is Too Strong has no chapters scheduled for release.");
        }
    }
    
    @Transactional
    @Scheduled(cron = "0 0 3 * * ?", zone = "UTC") // Every day at 3 AM UTC/ 8:30AM IST
    public void autoRelease3am() {
        Chapter nextChapter = chapterRepository.findFirstByReleaseStatusAndNovelNameOrderByChapterIdAsc(ReleaseStatus.STOCKPILE, "Ability User from a Parallel Dimension");
           

        if (nextChapter != null) {
            nextChapter.setReleaseStatus(ReleaseStatus.RELEASED);
            ZonedDateTime serverTime = ZonedDateTime.now(ZoneId.systemDefault());
            nextChapter.setReleasedOn(serverTime);
            chapterRepository.save(nextChapter);
            System.out.println("✅ Auto-released- Ability User from a Parallel Dimension " + nextChapter.getChapterNo());
        } else {
            System.out.println("ℹ️ Ability User from a Parallel Dimension has no chapters scheduled for release.");
        }
    }
    
    @Transactional
    @Scheduled(cron = "0 0 4 * * ?", zone = "UTC") // Every day at 4 AM UTC/ 9:30AM IST
    public void autoRelease4am() {
        Chapter nextChapter = chapterRepository.findFirstByReleaseStatusAndNovelNameOrderByChapterIdAsc(ReleaseStatus.STOCKPILE, "The Demon King's Game");
           

        if (nextChapter != null) {
            nextChapter.setReleaseStatus(ReleaseStatus.RELEASED);
            ZonedDateTime serverTime = ZonedDateTime.now(ZoneId.systemDefault());
            nextChapter.setReleasedOn(serverTime);
            chapterRepository.save(nextChapter);
            System.out.println("✅ Auto-released- The Demon King's Game " + nextChapter.getChapterNo());
        } else {
            System.out.println("ℹ️ The Demon King's Game has no chapters scheduled for release.");
        }
    }
    
    
    @Transactional
    @Scheduled(cron = "0 0 5 * * ?", zone = "UTC") // Every day at 5 AM UTC/ 10:30AM IST
    public void autoRelease5am() {
        Chapter nextChapter = chapterRepository.findFirstByReleaseStatusAndNovelNameOrderByChapterIdAsc(ReleaseStatus.STOCKPILE, "The Crazy Villain Regains His Sanity");
           

        if (nextChapter != null) {
            nextChapter.setReleaseStatus(ReleaseStatus.RELEASED);
            ZonedDateTime serverTime = ZonedDateTime.now(ZoneId.systemDefault());
            nextChapter.setReleasedOn(serverTime);
            chapterRepository.save(nextChapter);
            System.out.println("✅ Auto-released- The Crazy Villain Regains His Sanity " + nextChapter.getChapterNo());
        } else {
            System.out.println("ℹ️ The Crazy Villain Regains His Sanity has no chapters scheduled for release.");
        }
    }
    
    
    @Transactional
    @Scheduled(cron = "0 0 12 * * ?", zone = "UTC") // Every day at 12 Noon UTC/ 5:30PM IST
    public void autoReleasenoon() {
        Chapter nextChapter = chapterRepository.findFirstByReleaseStatusAndNovelNameOrderByChapterIdAsc(ReleaseStatus.STOCKPILE, "Sword of Beelzebuth");
           

        if (nextChapter != null) {
            nextChapter.setReleaseStatus(ReleaseStatus.RELEASED);
            ZonedDateTime serverTime = ZonedDateTime.now(ZoneId.systemDefault());
            nextChapter.setReleasedOn(serverTime);
            chapterRepository.save(nextChapter);
            System.out.println("✅ Auto-released - Sword of Beelzebuth: " + nextChapter.getTitle());
        } else {
            System.out.println("ℹ️ Sword of Beelzebuth has no chapters scheduled for release.");
        }
    }
    
    @Transactional
    @Scheduled(cron = "0 0 13 * * ?", zone = "UTC") // Every day at 1 PM UTC/ 6:30PM IST
    public void autoRelease1pm() {
        Chapter nextChapter = chapterRepository.findFirstByReleaseStatusAndNovelNameOrderByChapterIdAsc(ReleaseStatus.STOCKPILE, "The Skill Collector");
           

        if (nextChapter != null) {
            nextChapter.setReleaseStatus(ReleaseStatus.RELEASED);
            ZonedDateTime serverTime = ZonedDateTime.now(ZoneId.systemDefault());
            nextChapter.setReleasedOn(serverTime);
            chapterRepository.save(nextChapter);
            System.out.println("✅ Auto-released - The Skill Collector: " + nextChapter.getTitle());
        } else {
            System.out.println("ℹ️ The Skill Collector has no chapters scheduled for release.");
        }
    }
    
    @Transactional
    @Scheduled(cron = "0 0 14 * * ?", zone = "UTC") // Every day at 2 PM UTC/ 7:30PM IST
    public void autoRelease2pm() {
        Chapter nextChapter = chapterRepository.findFirstByReleaseStatusAndNovelNameOrderByChapterIdAsc(ReleaseStatus.STOCKPILE, "I Was Judged as Jobless and Banished After Choosing a Supercharged Growth Rate Skill. A Skill Maniac rescued me, but I Don’t Want to Get Too Involved");
           

        if (nextChapter != null) {
            nextChapter.setReleaseStatus(ReleaseStatus.RELEASED);
            ZonedDateTime serverTime = ZonedDateTime.now(ZoneId.systemDefault());
            nextChapter.setReleasedOn(serverTime);
            chapterRepository.save(nextChapter);
            System.out.println("✅ Auto-released - I Was Judged as Jobless and Banished After Choosing a Supercharged Growth Rate Skill. A Skill Maniac rescued me, but I Don’t Want to Get Too Involved: " + nextChapter.getTitle());
        } else {
            System.out.println("ℹ️ I Was Judged as Jobless and Banished After Choosing a Supercharged Growth Rate Skill. A Skill Maniac rescued me, but I Don’t Want to Get Too Involved has no chapters scheduled for release.");
        }
    }
    
    @Transactional
    @Scheduled(cron = "0 0 15 * * ?", zone = "UTC") // Every day at 3 PM UTC/ 8:30PM IST
    public void autoRelease3pm() {
        Chapter nextChapter = chapterRepository.findFirstByReleaseStatusAndNovelNameOrderByChapterIdAsc(ReleaseStatus.STOCKPILE, "I Think My Glasses Could Probably Take Over the World");
           

        if (nextChapter != null) {
            nextChapter.setReleaseStatus(ReleaseStatus.RELEASED);
            ZonedDateTime serverTime = ZonedDateTime.now(ZoneId.systemDefault());
            nextChapter.setReleasedOn(serverTime);
            chapterRepository.save(nextChapter);
            System.out.println("✅ Auto-released - I Think My Glasses Could Probably Take Over the World: " + nextChapter.getTitle());
        } else {
            System.out.println("ℹ️ I Think My Glasses Could Probably Take Over the World has no chapters scheduled for release.");
        }
    }
    
    @Transactional
    @Scheduled(cron = "0 0 16 * * ?", zone = "UTC") // Every day at 4 PM UTC/ 9:30PM IST
    public void autoRelease4pm() {
        Chapter nextChapter = chapterRepository.findFirstByReleaseStatusAndNovelNameOrderByChapterIdAsc(ReleaseStatus.STOCKPILE, "Left Behind Swordsman");
           

        if (nextChapter != null) {
            nextChapter.setReleaseStatus(ReleaseStatus.RELEASED);
            ZonedDateTime serverTime = ZonedDateTime.now(ZoneId.systemDefault());
            nextChapter.setReleasedOn(serverTime);
            chapterRepository.save(nextChapter);
            System.out.println("✅ Auto-released - Left Behind Swordsman: " + nextChapter.getTitle());
        } else {
            System.out.println("ℹ️ Left Behind Swordsman has no chapters scheduled for release.");
        }
    }
    
    @Transactional
    @Scheduled(cron = "0 0 17 * * ?", zone = "UTC") // Every day at 5 PM UTC/ 10:30PM IST
    public void autoRelease5pm() {
        Chapter nextChapter = chapterRepository.findFirstByReleaseStatusAndNovelNameOrderByChapterIdAsc(ReleaseStatus.STOCKPILE, "The Sword Saint Reincarnated as a Shota Prince Absolutely Refuses to Let His Former Disciple Find Out!");
           

        if (nextChapter != null) {
            nextChapter.setReleaseStatus(ReleaseStatus.RELEASED);
            ZonedDateTime serverTime = ZonedDateTime.now(ZoneId.systemDefault());
            nextChapter.setReleasedOn(serverTime);
            chapterRepository.save(nextChapter);
            System.out.println("✅ Auto-released - The Sword Saint Reincarnated as a Shota Prince Absolutely Refuses to Let His Former Disciple Find Out!: " + nextChapter.getTitle());
        } else {
            System.out.println("ℹ️ The Sword Saint Reincarnated as a Shota Prince Absolutely Refuses to Let His Former Disciple Find Out! has no chapters scheduled for release.");
        }
    }
    
    
    @Transactional
    @Scheduled(cron = "0 0 18 * * ?", zone = "UTC") // Every day at 6 PM UTC/ 11:30PM IST
    public void autoRelease6pm() {
        Chapter nextChapter = chapterRepository.findFirstByReleaseStatusAndNovelNameOrderByChapterIdAsc(ReleaseStatus.STOCKPILE, "The Magician Kunon Sees Everything");
           

        if (nextChapter != null) {
            nextChapter.setReleaseStatus(ReleaseStatus.RELEASED);
            ZonedDateTime serverTime = ZonedDateTime.now(ZoneId.systemDefault());
            nextChapter.setReleasedOn(serverTime);
            chapterRepository.save(nextChapter);
            System.out.println("✅ Auto-released - The Magician Kunon Sees Everything: " + nextChapter.getTitle());
        } else {
            System.out.println("ℹ️ The Magician Kunon Sees Everything has no chapters scheduled for release.");
        }
    }
}
