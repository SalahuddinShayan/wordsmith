package com.wordsmith.Services;

import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.wordsmith.Entity.CoinLedger;
import com.wordsmith.Repositories.CoinLedgerRepository;
import com.wordsmith.Util.EmailMasker;

@Service
public class ProfileService {

    private static final Logger log = LoggerFactory.getLogger(ProfileService.class);

    private final FavoriteService favoriteService;
    private final CommentService commentService;
    private final LikeService likeService;
    private final WalletService walletService;
    private final CoinLedgerRepository coinLedgerRepository;
    private final ChapterPurchaseService chapterPurchaseService;

    public ProfileService(FavoriteService favoriteService, CommentService commentService, LikeService likeService, WalletService walletService, 
                          CoinLedgerRepository coinLedgerRepository, ChapterPurchaseService chapterPurchaseService) {
        this.favoriteService = favoriteService;
        this.commentService = commentService;
        this.likeService = likeService;
        this.walletService = walletService;
        this.coinLedgerRepository = coinLedgerRepository;
        this.chapterPurchaseService = chapterPurchaseService;
    }

    public Map<String, Object> getProfileData(String username) {
        String maskedUser = EmailMasker.mask(username);

        log.info("PROFILE_FETCH_START user={}", maskedUser);

        Map<String, Object> data = new HashMap<>();

        try {
            data.put("favorites", favoriteService.getUserFavoriteNovels(username));
            log.debug("PROFILE_FETCH_FAVORITES user={} count={}", 
                      maskedUser, 
                      ((java.util.List<?>) data.get("favorites")).size());

            data.put("comments", commentService.getUserCommentsExcludingReplies(username));
            log.debug("PROFILE_FETCH_COMMENTS user={} count={}",
                      maskedUser,
                      ((java.util.List<?>) data.get("comments")).size());

            data.put("replies", commentService.getUserReplies(username));
            log.debug("PROFILE_FETCH_REPLIES user={} count={}",
                      maskedUser,
                      ((java.util.List<?>) data.get("replies")).size());

            data.put("likedComments", likeService.getUserLikedOrDislikedComments(username));
            log.debug("PROFILE_FETCH_LIKED_COMMENTS user={} count={}",
                      maskedUser,
                      ((java.util.List<?>) data.get("likedComments")).size());
            
            data.put("wallet", walletService.getBalance(username));
            log.debug("PROFILE_FETCH_WALLET user={} balance={}",
                      maskedUser,
                      data.get("wallet"));
            data.put("LastFivePurchases", chapterPurchaseService.getLastFivePurchases(username));
            log.debug("PROFILE_FETCH_PURCHASES user={} count={}",
                      maskedUser,
                      ((java.util.List<?>) data.get("LastFivePurchases")).size());


        } catch (Exception e) {
            log.error("PROFILE_FETCH_ERROR user={} message={}", maskedUser, e.getMessage(), e);
            throw e;
        }

        log.info("PROFILE_FETCH_COMPLETE user={} summary={{favorites={}, comments={}, replies={}, liked={}}}",
                maskedUser,
                ((java.util.List<?>) data.get("favorites")).size(),
                ((java.util.List<?>) data.get("comments")).size(),
                ((java.util.List<?>) data.get("replies")).size(),
                ((java.util.List<?>) data.get("likedComments")).size());

        return data;
    }

    public List<CoinLedger> getCoinLedger(String username) {
        List<CoinLedger> ledgerEntries = coinLedgerRepository.findByUsername(username);
        for (CoinLedger entry : ledgerEntries) {
            entry.setFormattedCreatedAt(formatDate(entry.getCreatedAt()));
        }
        return ledgerEntries;
    }

    //--------------------------------------------------
    //date formatter for CoinLedger
    //--------------------------------------------------
    private String formatDate(ZonedDateTime time) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MMM dd yyyy HH:mm");
        return formatter.format(time);
    }
}
