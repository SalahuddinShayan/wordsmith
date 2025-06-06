package com.wordsmith.Controllers;

import java.time.Duration;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;

import com.wordsmith.Entity.Chapter;
import com.wordsmith.Entity.Comment;
import com.wordsmith.Enum.CommentEntityType;
import com.wordsmith.Enum.ReleaseStatus;
import com.wordsmith.Repositories.ChapterRepository;
import com.wordsmith.Services.CommentService;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.http.HttpServletRequest;

@Controller
public class ChapterController {
	
	
	private final ChapterRepository cr;
	
	private final CommentService commentService;
    
	public ChapterController(CommentService commentService, ChapterRepository cr) {
		this.commentService = commentService;
		this.cr=cr;
	}
	
	@RequestMapping("/chapterlist")
	public String chapterList(@RequestParam("NovelName")String novelname, Model model) {
		model.addAttribute("Chapters", cr.byNovelName(novelname));
		model.addAttribute("command", new Chapter());
		model.addAttribute("novelname", novelname);
		return "chapterlist";
	}
	
	
	@RequestMapping("/savechapter")
    public RedirectView save(@ModelAttribute("chapter")Chapter chapter,RedirectAttributes redirectAttributes){  
		Long cid = chapter.getChapterId();
		if (cid == 0) {
		long id = cr.Last() + 1;
		chapter.setChapterId(id);
		ZonedDateTime serverTime = ZonedDateTime.now(ZoneId.systemDefault());
		chapter.setPostedOn(serverTime);
		}
		else {
			Chapter chapter2= cr.getReferenceById(cid);
			chapter.setPostedOn(chapter2.getPostedOn());
		}
		cr.save(chapter);
		RedirectView redirectView= new RedirectView("/chapterlist",true);
		redirectAttributes.addAttribute("NovelName", chapter.getNovelName());
	    return redirectView;   
    }
	
	@RequestMapping("/savechapter2")
    public RedirectView save2(@ModelAttribute("chapter")Chapter chapter,RedirectAttributes redirectAttributes){  
		Long cid = chapter.getChapterId();
		if (cid == 0) {
		long id = cr.Last() + 1;
		chapter.setChapterId(id);
		ZonedDateTime serverTime = ZonedDateTime.now(ZoneId.systemDefault());
		chapter.setPostedOn(serverTime);
		}
		else {
			Chapter chapter2= cr.getReferenceById(cid);
			chapter.setPostedOn(chapter2.getPostedOn());
		}
		
		cr.save(chapter);
		RedirectView redirectView= new RedirectView("/allchapters",true);
	    return redirectView;   
    }
	
	@RequestMapping(value="/deletechapter")
	public RedirectView  deletemember(@RequestParam("ChapterId") long id, RedirectAttributes redirectAttributes){
		RedirectView redirectView= new RedirectView("/chapterlist",true);
		redirectAttributes.addAttribute("NovelName",cr.getReferenceById(id).getNovelName());
		cr.deleteById(id);
		return redirectView;
		}
	
	@RequestMapping(value="/deletechapter2")
	public RedirectView  deletechapter(@RequestParam("ChapterId") long id){
		RedirectView redirectView= new RedirectView("/allchapters",true);
		cr.deleteById(id);
		return redirectView;
		}
	
	@RequestMapping("/chapter/{chapterId}")
	public String Chapter(@PathVariable long chapterId, Model m, HttpServletRequest request) {
		Chapter chapter = cr.getReferenceById(chapterId);
		
		// ✅ Check if the chapter is released
	    if (chapter.getReleaseStatus() != ReleaseStatus.RELEASED) {
	        // Manually forward to error handler with 404
	        request.setAttribute(RequestDispatcher.ERROR_STATUS_CODE, HttpStatus.NOT_FOUND.value());
	        return "forward:/error";
	    }

		
		m.addAttribute("chapter",chapter);
		CommentEntityType type = CommentEntityType.CHAPTER;
		List<Comment> comments = commentService.getCommentsByEntity(type, chapterId);
		 for (Comment comment : comments) {
		        if (comment.getCreatedAt() != null) {
		            comment.setTimeAgo(getTimeDifference(comment.getCreatedAt()));
		        }
		    }
		m.addAttribute("comments", comments);
		return "chaptertemplate";
	}
	
	@RequestMapping("chapter-next/{chapterId}")
	public RedirectView nextChapter(@PathVariable long chapterId) {
		Chapter current =cr.getReferenceById(chapterId);
		String novelName= current.getNovelName();
		try {
		long nId = cr.NextChapterId(novelName, chapterId);
		String nextId = Long.toString(nId);
		RedirectView redirectView= new RedirectView("/chapter/" + nextId,true);
		return redirectView;
		}
		catch(Exception  e) {
			RedirectView redirectView= new RedirectView("/novel/"+ novelName ,true);
			return redirectView;
		}
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
	
	@RequestMapping("chapter-previous/{chapterId}")
	public RedirectView previousChapter(@PathVariable long chapterId) {
		Chapter current =cr.getReferenceById(chapterId);
		String novelName= current.getNovelName();
		try {
		String nextId = Long.toString(cr.PreviousChapterId(novelName, chapterId));
		RedirectView redirectView= new RedirectView("/chapter/" + nextId,true);
		return redirectView;
		}
		catch(Exception  e) {
			RedirectView redirectView= new RedirectView("/novel/"+ novelName ,true);
			return redirectView;
		}
	}
	
	@RequestMapping("/latest/{novelname}")
	public String latestChapters(@PathVariable String novelname, Model model) {
		List<Chapter> chapters = cr.Latest(novelname);
		for (Chapter chapter : chapters) {
	        if (chapter.getReleasedOn() != null) {
	        	chapter.setTimeAgo(getTimeDifference(chapter.getReleasedOn()));
	        }
	        else {
	        	chapter.setTimeAgo(getTimeDifference(chapter.getPostedOn()));
	        }
		}
		model.addAttribute("Chapters", chapters);
		return "lc";
	
	}
	
	@RequestMapping("/latest1/{novelname}")
	public String latestChapter(@PathVariable String novelname, Model model) {
		model.addAttribute("chapter", cr.Latest1(novelname));
		return "ch";
	
	}

}
