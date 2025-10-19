package com.wordsmith.Controllers;

import java.io.IOException;
import java.text.ParseException;
import java.time.Duration;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;

import com.wordsmith.Entity.Chapter;
import com.wordsmith.Entity.Comment;
import com.wordsmith.Entity.Novel;
import com.wordsmith.Entity.User;
import com.wordsmith.Enum.CommentEntityType;
import com.wordsmith.Repositories.ChapterRepository;
import com.wordsmith.Repositories.NovelRepository;
import com.wordsmith.Services.CommentService;
import com.wordsmith.Services.FavoriteService;
import com.wordsmith.Services.LikeService;
import com.wordsmith.Services.ViewsService;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Controller
public class NovelController {
	
	private final NovelRepository NovelRepo;
	private final ChapterRepository cr;
	private final CommentService commentService;
	private final ViewsService viewsService;
	private final LikeService likeService;
	private final FavoriteService favoriteService;
	
	public NovelController(CommentService commentService, ChapterRepository cr, NovelRepository NovelRepo, ViewsService viewsService, LikeService likeService, FavoriteService favoriteService) {
		this.commentService = commentService;
		this.cr=cr;
		this.NovelRepo=NovelRepo;
		this.viewsService = viewsService;
		this.likeService = likeService;
		this.favoriteService = favoriteService;
	}
	
	@RequestMapping(value= { "/","/home"})                     
    public String index(Model model) {
		model.addAttribute("Novels",NovelRepo.popular());
		model.addAttribute("Novelsu",NovelRepo.NovelUpdates());
        return "index";           
    }
	
	@RequestMapping("/novellist")
	public String novellist(Model model) {
			model.addAttribute("Novels",NovelRepo.findAll());
			model.addAttribute("command",new Novel());
		    return "novellist";
	    
	}
	
	@RequestMapping("/activenovellist")
	public String activenovellist(Model model) {
			model.addAttribute("Novels",NovelRepo.findOngoing());
			model.addAttribute("command",new Novel());
		    return "novellist";
	}		
	
	
	@GetMapping("/novel-image/{id}")
	@ResponseBody
	void showImage(@PathVariable("id") int id, HttpServletResponse response, Optional<Novel> Novel)
			throws ServletException, IOException {
		Novel = NovelRepo.findById(id);
		response.setContentType("image/jpeg, image/jpg, image/png, image/gif");
		byte[] image = Novel.get().getNovelImage();
		if (image != null && image.length > 0) {
		response.getOutputStream().write(image);}
		response.getOutputStream().flush();
	}
	
	@RequestMapping(value ="/savenovel", method = RequestMethod.POST, consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	public RedirectView  savenovel(@RequestParam("NovelId") int Id, @RequestParam("NovelName") String Name, 
			@RequestParam("OriginalLanguage") String originalLanguage, @RequestParam("Genre") String genre, 
			@RequestParam("Author") String Author, @RequestParam("Description") String description, 
			@RequestParam("Keywords") String keywords, @RequestParam("Pic") MultipartFile pic, @RequestParam("Status") String Status)
					throws IllegalStateException, IOException, ParseException {
		Novel novel = new Novel();
		novel.setNovelId(Id);
		novel.setNovelName(Name);
		novel.setAuthor(Author);
		novel.setOriginalLanguage(originalLanguage);
		novel.setGenre(genre);
		novel.setDescription(description);
		novel.setNovelImage(pic.getBytes());
		novel.setKeywords(keywords);
		novel.setStatus(Status);
		novel.setAddedOn(new java.util.Date());
		NovelRepo.save(novel);
		RedirectView redirectView= new RedirectView("/novellist",true);
	    return redirectView;
		}
	
	@RequestMapping(value="/deletenovel")
	public RedirectView  deletemember(@RequestParam("NovelId") int id){
		NovelRepo.deleteById(id);
		RedirectView redirectView= new RedirectView("/novellist",true);
	    return redirectView;
		}
	
	@RequestMapping("/novel/{novelName}")
	public String Novel(@PathVariable String novelName, Model m, HttpServletRequest request) {
		Novel novel = NovelRepo.byNovelName(novelName);
		var user = (User) request.getSession().getAttribute("loggedInUser");
		if(user != null) {
			var userReaction = likeService.getUserReaction("novel", novel.getNovelId(), user.getUsername());
			novel.setUserReaction(userReaction);
			novel.setFavorited(favoriteService.isFavorited(user.getUsername(), (long) novel.getNovelId()));
		}
		novel.setLoveCount(likeService.getReactionCount(novel.getNovelId(), "novel", com.wordsmith.Enum.LikeEnum.LOVE));
		novel.setAngryCount(likeService.getReactionCount(novel.getNovelId(), "novel", com.wordsmith.Enum.LikeEnum.ANGRY));
		novel.setLaughCount(likeService.getReactionCount(novel.getNovelId(), "novel", com.wordsmith.Enum.LikeEnum.LAUGH));
		novel.setSadCount(likeService.getReactionCount(novel.getNovelId(), "novel", com.wordsmith.Enum.LikeEnum.SAD));
		novel.setWowCount(likeService.getReactionCount(novel.getNovelId(), "novel", com.wordsmith.Enum.LikeEnum.WOW));
		novel.setFireCount(likeService.getReactionCount(novel.getNovelId(), "novel", com.wordsmith.Enum.LikeEnum.FIRE));
		novel.setFavoriteCount(favoriteService.getFavoriteCount((long) novel.getNovelId()));
		m.addAttribute("novel",novel);
		List<Chapter> chapters = cr.byNovelNameReleased(novelName);

		// ✅ Collect IDs for batch query
    	List<Long> chapterIds = chapters.stream()
            .map(Chapter::getChapterId)
            .collect(Collectors.toList());

		// ✅ Fetch all view counts in one go
    	Map<Long, Long> chapterViews = viewsService.getViewsForChapters(chapterIds);	

		for (Chapter chapter : chapters) {
	        if (chapter.getReleasedOn() != null) {
	        	chapter.setTimeAgo(getTimeDifference(chapter.getReleasedOn()));
	        }
	        else {
	        	chapter.setTimeAgo(getTimeDifference(chapter.getPostedOn()));
	        }

			chapter.setViews(chapterViews.getOrDefault(chapter.getChapterId(), 0L));
		}

		m.addAttribute("Chapters", chapters);
		CommentEntityType type = CommentEntityType.NOVEL;
		List<Comment> comments = commentService.getCommentsByEntity(type, (long) novel.getNovelId());

		 for (Comment comment : comments) {
		        if (comment.getCreatedAt() != null) {
		            comment.setTimeAgo(getTimeDifference(comment.getCreatedAt()));
		        }
				comment.setLikeCount(likeService.getLikesCount(comment.getId(), "comment"));
				comment.setDislikeCount(likeService.getDislikesCount(comment.getId(), "comment"));
				if (user != null) {
					var userReaction = likeService.getUserReaction("comment", comment.getId(), user.getUsername());
					comment.setUserReaction(userReaction);
				}
				if (comment.isHasReplies()){
					comment.setReplies(commentService.getCommentsByEntity(CommentEntityType.COMMENT, comment.getId()));
					for (Comment reply : comment.getReplies()) {
						if (reply.getCreatedAt() != null) {
							reply.setTimeAgo(getTimeDifference(reply.getCreatedAt()));
						}
						reply.setLikeCount(likeService.getLikesCount(reply.getId(), "comment"));
						reply.setDislikeCount(likeService.getDislikesCount(reply.getId(), "comment"));
						if (user != null) {
							var userReaction = likeService.getUserReaction("comment", reply.getId(), user.getUsername());
							reply.setUserReaction(userReaction);
						}
					}
				}
				
		    }

		viewsService.incrementViews(Long.valueOf(novel.getNovelId()), CommentEntityType.NOVEL);
		long totalViews = viewsService.getTotalViewsByNovel(novelName, (long) novel.getNovelId());
		m.addAttribute("totalViews", totalViews);
			
		m.addAttribute("comments", comments);
		m.addAttribute("frist", cr.First(novelName));
		m.addAttribute("last", cr.LastReleased(novelName));
		return "noveltemplate";
	}
	
	private String getTimeDifference(ZonedDateTime pastTime) {
	    ZonedDateTime now = ZonedDateTime.now();
	    Duration duration = Duration.between(pastTime, now);

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
	        return formatter.format(pastTime).toString(); // Show full date if older than a week
	    }
	    
	}
	
	@RequestMapping("/novels")
	public String novels(Model model) {
			model.addAttribute("Novels",NovelRepo.findAll());
			return "novels";
	}
	
	@RequestMapping("/updates")
	public String updates(Model model) {
			model.addAttribute("Novels",NovelRepo.AllUpdates());
			return "updates";
	}
	
	@RequestMapping("/imageslogo")
	public String images() {
			return "images&logo";
	}
	
	@RequestMapping("/allchapters")
	public String allChapter(Model model) {
		model.addAttribute("Chapters", cr.AllChapterd());
		model.addAttribute("command", new Chapter());
		model.addAttribute("novelnames", NovelRepo.allNovelName());
		return "allchapters";
	}
	
	@RequestMapping("/privacypolicy")
	public String privacyPolicy() {
			return "privacypolicy";
	}
	
	@RequestMapping("/disclaimer")
	public String disclaimer() {
			return "disclaimer";
	}
	
	@RequestMapping("/aboutus")
	public String aboutUs() {
			return "aboutus";
	}
	
	@RequestMapping("/dashboard")
	public String dashboard() {
			return "dashboard";
	}

	@RequestMapping("/novelstatus")
	public RedirectView updateNovelStatus(@RequestParam("novelname") String novelname, @RequestParam("status") String status, RedirectAttributes redirectAttributes) {
		NovelRepo.updateNovelStatus(status, novelname);
		RedirectView redirectView = new RedirectView("/chapterlist", true);
		redirectAttributes.addAttribute("NovelName", novelname);
		return redirectView;
	}

}
