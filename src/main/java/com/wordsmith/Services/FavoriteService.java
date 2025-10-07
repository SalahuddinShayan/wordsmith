package com.wordsmith.Services;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.wordsmith.Entity.Favorite;
import com.wordsmith.Entity.Novel;
import com.wordsmith.Repositories.FavoriteRepository;
import com.wordsmith.Repositories.NovelRepository;

@Service
public class FavoriteService {

    private final FavoriteRepository favoriteRepository;
    private final NovelRepository novelRepository;

    public FavoriteService(FavoriteRepository favoriteRepository, NovelRepository novelRepository) {
        this.favoriteRepository = favoriteRepository;
        this.novelRepository = novelRepository;
    }

    public boolean toggleFavorite(String username, Long novelId) {
        if (favoriteRepository.existsByUsernameAndNovelId(username, novelId)) {
            favoriteRepository.deleteByUsernameAndNovelId(username, novelId);
            return false; // unfavorited
        } else {
            Favorite fav = new Favorite();
            fav.setUsername(username);
            fav.setNovelId(novelId);
            favoriteRepository.save(fav);
            return true; // favorited
        }
    }

    public long getFavoriteCount(Long novelId) {
        return favoriteRepository.countByNovelId(novelId);
    }

    public boolean isFavorited(String username, Long novelId) {
        return favoriteRepository.existsByUsernameAndNovelId(username, novelId);
    }

    public List<Novel> getUserFavoriteNovels(String username) {
    List<Favorite> favorites = favoriteRepository.findByUsername(username);
    List<Novel> novels = new ArrayList<>();
    for (Favorite fav : favorites) {
        Novel novel = novelRepository.findById(fav.getNovelId().intValue()).orElse(null);
        if (novel != null) {
            novel.setFavoriteCount(getFavoriteCount(fav.getNovelId()));
            novels.add(novel);
        }
    }
    return novels;
}

}

