package com.wordsmith.Services;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.wordsmith.Entity.Views;
import com.wordsmith.Enum.CommentEntityType;
import com.wordsmith.Repositories.ViewsRepository;

@Service
public class ViewsService {

    private static final Logger log = LoggerFactory.getLogger(ViewsService.class);

    private final ViewsRepository viewsRepository;

    public ViewsService(ViewsRepository viewsRepository) {
        this.viewsRepository = viewsRepository;
    }

    // --------------------------------------------------------------------
    // GET SINGLE ENTITY VIEW COUNT
    // --------------------------------------------------------------------
    public long getViewsCount(Long entityId, CommentEntityType entityType) {
        if (entityId == null || entityType == null) {
            log.warn("VIEWS_GET_COUNT_INVALID_PARAMS entityId={} entityType={}", entityId, entityType);
            return 0;
        }

        long result = viewsRepository.getTotalViewsByEntity(entityId, entityType.name());

        log.debug("VIEWS_GET_COUNT entityId={} entityType={} views={}",
                entityId, entityType, result);

        return result;
    }

    // --------------------------------------------------------------------
    // INCREMENT VIEW COUNTER
    // --------------------------------------------------------------------
    public void incrementViews(Long entityId, CommentEntityType entityType) {

        if (entityId == null || entityType == null) {
            log.warn("VIEWS_INCREMENT_INVALID_PARAMS entityId={} entityType={}", entityId, entityType);
            return;
        }

        log.debug("VIEWS_INCREMENT_ATTEMPT entityId={} entityType={}", entityId, entityType);

        Views viewRecord = viewsRepository.findByEntityTypeAndEntityId(entityType, entityId);

        if (viewRecord == null) {
            log.info("VIEWS_CREATE_NEW_RECORD entityId={} entityType={}", entityId, entityType);

            viewRecord = new Views();
            viewRecord.setEntityId(entityId);
            viewRecord.setEntityType(entityType);
            viewRecord.setViews(1L);
        } else {
            long oldCount = viewRecord.getViews();
            viewRecord.setViews(oldCount + 1);

            log.debug("VIEWS_INCREMENT_SUCCESS entityId={} entityType={} oldViews={} newViews={}",
                    entityId, entityType, oldCount, oldCount + 1);
        }

        saveViewRecord(viewRecord);
    }

    // --------------------------------------------------------------------
    // GET TOTAL VIEWS FOR NOVEL ACROSS ALL CHAPTERS + NOVEL PAGE
    // --------------------------------------------------------------------
    public long getTotalViewsByNovel(String novelName, Long novelId) {

        if (novelName == null || novelId == null) {
            log.warn("VIEWS_TOTAL_NOVEL_INVALID_PARAMS novelName={} novelId={}", novelName, novelId);
            return 0;
        }

        long chapterViews = viewsRepository.totalViewsBynovelName(novelName);
        long novelPageViews = viewsRepository.getTotalViewsByEntity(novelId, "NOVEL");

        long total = chapterViews + novelPageViews;

        log.info("VIEWS_TOTAL_NOVEL novelName={} novelId={} chapterViews={} novelPageViews={} total={}",
                novelName, novelId, chapterViews, novelPageViews, total);

        return total;
    }

    // --------------------------------------------------------------------
    // SAVE VIEW RECORD (Internal use)
    // --------------------------------------------------------------------
    public void saveViewRecord(Views view) {
        if (view == null) {
            log.error("VIEWS_SAVE_NULL_RECORD");
            return;
        }

        log.debug("VIEWS_SAVE_RECORD entityId={} entityType={} views={}",
                view.getEntityId(),
                view.getEntityType(),
                view.getViews()
        );

        viewsRepository.save(view);
    }

    // --------------------------------------------------------------------
    // BATCH FETCH VIEWS FOR CHAPTERS
    // --------------------------------------------------------------------
    public Map<Long, Long> getViewsForChapters(List<Long> chapterIds) {
        if (chapterIds == null || chapterIds.isEmpty()) {
            log.warn("VIEWS_BATCH_CHAPTER_EMPTY_LIST");
            return Collections.emptyMap();
        }

        log.debug("VIEWS_BATCH_CHAPTER_REQUEST count={} ids={}", chapterIds.size(), chapterIds);

        List<Object[]> results =
                viewsRepository.findViewsForEntities(CommentEntityType.CHAPTER, chapterIds);

        Map<Long, Long> map = results.stream()
                .collect(Collectors.toMap(
                        row -> (Long) row[0], // chapterId
                        row -> (Long) row[1]  // views
                ));

        log.debug("VIEWS_BATCH_CHAPTER_RESULT totalEntries={}", map.size());

        return map;
    }

}
