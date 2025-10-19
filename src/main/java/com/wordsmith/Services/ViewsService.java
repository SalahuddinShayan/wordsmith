package com.wordsmith.Services;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.wordsmith.Entity.Views;
import com.wordsmith.Enum.CommentEntityType;
import com.wordsmith.Repositories.ViewsRepository;

@Service
public class ViewsService {

    private ViewsRepository viewsRepository;
    
    public ViewsService(ViewsRepository viewsRepository) {
        this.viewsRepository = viewsRepository;
    }

    public long getViewsCount(Long entityId, CommentEntityType entityType) {
        return viewsRepository.getTotalViewsByEntity(entityId, entityType.name());
    }

    public void incrementViews(Long entityId, CommentEntityType entityType) {
        var viewRecord = viewsRepository.findByEntityTypeAndEntityId(entityType, entityId);
        if (viewRecord == null) {
            viewRecord = new Views();
            viewRecord.setEntityId(entityId);
            viewRecord.setEntityType(CommentEntityType.valueOf(entityType.name().toUpperCase()));
            viewRecord.setViews(Long.valueOf(1));
        } else {
            viewRecord.setViews(viewRecord.getViews() + 1);
        }
        saveViewRecord(viewRecord);
    }

    public  long getTotalViewsByNovel(String novelName, Long novelId) {
        System.out.println(viewsRepository.totalViewsBynovelName(novelName));
        System.out.println(novelName + " " + novelId + " " +CommentEntityType.NOVEL);
        System.out.println(viewsRepository.getTotalViewsByEntity(novelId, "NOVEL"));
        return viewsRepository.totalViewsBynovelName(novelName) + viewsRepository.getTotalViewsByEntity(novelId, "NOVEL");
    }

    public void saveViewRecord(Views view) {
        viewsRepository.save(view);
    }

    public Map<Long, Long> getViewsForChapters(List<Long> chapterIds) {
        if (chapterIds == null || chapterIds.isEmpty()) {
            return Collections.emptyMap();
        }

        List<Object[]> results = viewsRepository.findViewsForEntities(CommentEntityType.CHAPTER, chapterIds);

        // Convert to Map<chapterId, views>
        return results.stream()
                .collect(Collectors.toMap(
                        row -> (Long) row[0],
                        row -> (Long) row[1]
                ));
    }

}
