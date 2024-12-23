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
		Long cid = chapter.getChapterId();
		if (cid == 0) {
		long id = cr.Last() + 1;
		chapter.setChapterId(id);
		Timestamp instant= Timestamp.from(Instant.now());
		chapter.setPostedOn(instant);
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
		Timestamp instant= Timestamp.from(Instant.now());
		chapter.setPostedOn(instant);
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
	public String Chapter(@PathVariable long chapterId, Model m) {
		Chapter chapter = cr.getReferenceById(chapterId);
		m.addAttribute("chapter",chapter);
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
		model.addAttribute("Chapters", cr.Latest(novelname));
		return "lc";
	
	}
	
	@RequestMapping("/latest1/{novelname}")
	public String latestChapter(@PathVariable String novelname, Model model) {
		model.addAttribute("chapter", cr.Latest1(novelname));
		return "ch";
	
	}

}
