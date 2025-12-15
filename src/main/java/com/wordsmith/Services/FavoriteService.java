package com.wordsmith.Services;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.wordsmith.Entity.Favorite;
import com.wordsmith.Entity.Novel;
import com.wordsmith.Repositories.FavoriteRepository;
import com.wordsmith.Repositories.NovelRepository;
import com.wordsmith.Util.EmailMasker;

@Service
public class FavoriteService {

    private static final Logger log = LoggerFactory.getLogger(FavoriteService.class);

    private final FavoriteRepository favoriteRepository;
    private final NovelRepository novelRepository;

    public FavoriteService(FavoriteRepository favoriteRepository, NovelRepository novelRepository) {
        this.favoriteRepository = favoriteRepository;
        this.novelRepository = novelRepository;
    }

    /** -----------------------------------------------------------
     *  Toggle Favorite (Add or Remove)
     * ----------------------------------------------------------- */
    public boolean toggleFavorite(String username, Long novelId) {
        String maskedUser = EmailMasker.mask(username);

        log.info("FAVORITE_TOGGLE_ATTEMPT user={} novelId={}", maskedUser, novelId);

        try {
            if (favoriteRepository.existsByUsernameAndNovelId(username, novelId)) {

                favoriteRepository.deleteByUsernameAndNovelId(username, novelId);
                log.info("FAVORITE_REMOVED user={} novelId={}", maskedUser, novelId);

                return false; // unfavorited
            } else {
                Favorite fav = new Favorite();
                fav.setUsername(username);
                fav.setNovelId(novelId);
                favoriteRepository.save(fav);

                log.info("FAVORITE_ADDED user={} novelId={}", maskedUser, novelId);

                return true; // favorited
            }

        } catch (Exception ex) {
            log.error("FAVORITE_TOGGLE_ERROR user={} novelId={} error={}",
                    maskedUser, novelId, ex.getMessage());
            throw ex; // rethrow so controller handles it
        }
    }

    /** -----------------------------------------------------------
     *  Get Favorite Count
     * ----------------------------------------------------------- */
    public long getFavoriteCount(Long novelId) {
        long count = favoriteRepository.countByNovelId(novelId);

        log.debug("FAVORITE_COUNT novelId={} count={}", novelId, count);

        return count;
    }

    /** -----------------------------------------------------------
     *  Check if user favorited a novel
     * ----------------------------------------------------------- */
    public boolean isFavorited(String username, Long novelId) {
        String masked = EmailMasker.mask(username);

        boolean result = favoriteRepository.existsByUsernameAndNovelId(username, novelId);

        log.debug("FAVORITE_CHECK user={} novelId={} result={}",
                masked, novelId, result);

        return result;
    }

    /** -----------------------------------------------------------
     *  Get All Favorite Novels of User
     * ----------------------------------------------------------- */
    public List<Novel> getUserFavoriteNovels(String username) {
        String masked = EmailMasker.mask(username);

        log.info("FAVORITE_LIST_REQUEST user={}", masked);

        List<Favorite> favorites = favoriteRepository.findByUsername(username);
        List<Novel> novels = new ArrayList<>();

        try {
            for (Favorite fav : favorites) {
                Novel novel = novelRepository.findById(fav.getNovelId().intValue()).orElse(null);
                if (novel != null) {
                    novel.setFavoriteCount(getFavoriteCount(fav.getNovelId()));
                    novels.add(novel);
                }
            }

            log.info("FAVORITE_LIST_SUCCESS user={} count={}", masked, novels.size());

        } catch (Exception ex) {
            log.error("FAVORITE_LIST_ERROR user={} error={}", masked, ex.getMessage());
        }

        return novels;
    }
}
