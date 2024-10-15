package com.wordsmith.Controllers;

import java.sql.Timestamp;
import java.time.Instant;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;

import com.wordsmith.Entity.Chapter;
import com.wordsmith.Repositories.ChapterRepository;

@Controller
public class ChapterController {
	
	@Autowired
	ChapterRepository cr;
	
	@RequestMapping("/chapterlist")
	public String chapterList(@RequestParam("NovelName")String novelname, Model model) {
		model.addAttribute("Chapters", cr.byNovelName(novelname));
		model.addAttribute("command", new Chapter());
		model.addAttribute("novelname", novelname);
		return "chapterlist";
	}
	
	@RequestMapping("/savechapter")
    public RedirectView save(@ModelAttribute("chapter")Chapter chapter,RedirectAttributes redirectAttributes){  
		Timestamp instant= Timestamp.from(Instant.now());
		chapter.setPostedOn(instant);
		cr.save(chapter);
		RedirectView redirectView= new RedirectView("/chapterlist",true);
		redirectAttributes.addAttribute("NovelName", chapter.getNovelName());
	    return redirectView;   
    }
	
	@RequestMapping(value="/deletechapter")
	public RedirectView  deletemember(@RequestParam("ChapterId") long id, RedirectAttributes redirectAttributes){
		RedirectView redirectView= new RedirectView("/chapterlist",true);
		redirectAttributes.addAttribute("NovelName",cr.getReferenceById(id).getNovelName());
		cr.deleteById(id);
		return redirectView;
		}
	
	@RequestMapping("/chapter/{chapterId}")
	public String Chapter(@PathVariable long chapterId, Model m) {
		Chapter chapter = cr.getReferenceById(chapterId);
		m.addAttribute("chapter",chapter);
		return "chaptertemplate";
	}
	
	@RequestMapping("chapter-next/{chapterId}")
	public RedirectView nextChapter(@PathVariable long chapterId) {
		Chapter current =cr.getReferenceById(chapterId);
		String novelName= current.getNovelName();
		String nextId = Long.toString(cr.NextChapterId(novelName, chapterId));
		RedirectView redirectView= new RedirectView("/chapter/" + nextId,true);
		return redirectView;
	}
	
	@RequestMapping("chapter-previous/{chapterId}")
	public RedirectView previousChapter(@PathVariable long chapterId) {
		Chapter current =cr.getReferenceById(chapterId);
		String novelName= current.getNovelName();
		String nextId = Long.toString(cr.PreviousChapterId(novelName, chapterId));
		RedirectView redirectView= new RedirectView("/chapter/" + nextId,true);
		return redirectView;
	}
	
	@RequestMapping("/latest/{novelname}")
	public String latestChapters(@PathVariable String novelname, Model model) {
		model.addAttribute("Chapters", cr.Latest(novelname));
		return "lc";
	
	}
	
	@RequestMapping("/latest1/{novelname}")
	public String latestChapter(@PathVariable String novelname, Model model) {
		model.addAttribute("chapter", cr.Latest1(novelname));
		return "ch";
	
	}

}
