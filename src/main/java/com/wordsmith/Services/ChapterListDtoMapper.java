package com.wordsmith.Services;

import java.time.Duration;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.wordsmith.Dto.ReleasedChapterDTO;
import com.wordsmith.Dto.StockpileChapterDTO;
import com.wordsmith.Entity.Chapter;

@Service
public class ChapterListDtoMapper {

    private final ChapterAccessService accessService;
    private final ViewsService viewsService;

    public ChapterListDtoMapper(ChapterAccessService accessService, ViewsService viewsService) {
        this.accessService = accessService;
        this.viewsService = viewsService;
    }

    // --------------------------------------------------
    // 📘 RELEASED CHAPTER → DTO
    // --------------------------------------------------
    public ReleasedChapterDTO toReleasedDto(Chapter chapter) {

        ReleasedChapterDTO dto = new ReleasedChapterDTO();

        dto.setChapterId(chapter.getChapterId());
        dto.setChapterNo(chapter.getChapterNo());
        dto.setTitle(chapter.getTitle());

        if(chapter.getReleasedOn()!=null){
        dto.setTimeAgo(
                formatTimeAgo(chapter.getReleasedOn()));
        } else {
            dto.setTimeAgo(
                formatTimeAgo(chapter.getPostedOn()));
        }
        return dto;
    }

    // --------------------------------------------------
    // 🔒 STOCKPILE CHAPTER → DTO
    // --------------------------------------------------
    public StockpileChapterDTO toStockpileDto(
            Chapter chapter,
            String username
    ) {

        StockpileChapterDTO dto = new StockpileChapterDTO();

        dto.setChapterId(chapter.getChapterId());
        dto.setChapterNo(chapter.getChapterNo());
        dto.setTitle(chapter.getTitle());

        dto.setTimeAgo(
                formatTimeAgo(chapter.getPostedOn())
        );

        boolean owned = accessService.isChapterOwned(
                chapter.getChapterId(),
                username
        );

        dto.setOwned(owned);

        if (!owned) {
            dto.setPriceCoins(
                    accessService.getChapterPrice(chapter.getChapterId())
            );
        }

        return dto;
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

    // --------------------------------------------------
    // ✅ NEW: LIST-BASED METHODS (WHAT YOU ASKED FOR)
    // --------------------------------------------------

    /**
     * Maps a list of RELEASED chapters to ReleasedChapterDTOs
     */
    public List<ReleasedChapterDTO> toReleasedChapterDtoList(
            List<Chapter> releasedChapters, Map<Long, Long> chapterViews
    ) {
        return releasedChapters.stream()
                .map(chapter -> {
                ReleasedChapterDTO dto = toReleasedDto(chapter);

                // ✅ Set views
                dto.setViews(
                        chapterViews.getOrDefault(chapter.getChapterId(), 0L)
                );

                return dto;
            })
            .collect(Collectors.toList());
    }

    public List<ReleasedChapterDTO> toReleasedChapterDtoList(
            List<Chapter> releasedChapters
    ) {
        return releasedChapters.stream()
                .map(this::toReleasedDto)
                .collect(Collectors.toList());
    }

    /**
     * Maps a list of STOCKPILE chapters to StockpileChapterDTOs
     */
    public List<StockpileChapterDTO> toStockpileChapterDtoList(List<Chapter> stockpileChapters, String username, Map<Long, Long> chapterViews) {
        return stockpileChapters.stream()
                .map(chapter -> {
                    StockpileChapterDTO dto = toStockpileDto(chapter, username);

                    // ✅ Set views
                    dto.setViews(
                            chapterViews.getOrDefault(chapter.getChapterId(), 0L)
                    );

                    return dto;
                })
                .collect(Collectors.toList());
    }


    public List<StockpileChapterDTO> toStockpileChapterDtoList(List<Chapter> stockpileChapters, String username) {
        return stockpileChapters.stream()
                .map(chapter -> toStockpileDto(chapter, username))
                .collect(Collectors.toList());
    }
}