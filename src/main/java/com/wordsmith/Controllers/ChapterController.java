package com.wordsmith.Controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
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
    public RedirectView save(@ModelAttribute("chapter")Chapter chapter){    
		cr.save(chapter);
		RedirectView redirectView= new RedirectView("/chapterlist",true);
	    return redirectView;   
    }
	
	@RequestMapping(value="/deletechapter")
	public RedirectView  deletemember(@RequestParam("ChapterId") long id){
		cr.deleteById(id);
		RedirectView redirectView= new RedirectView("/chapterlist",true);
	    return redirectView;
		}
	
	@RequestMapping("/chapter/{novelName}/{chapterNo}")
	public String Chapter(@PathVariable String novelName, @PathVariable String chapterNo, Model m) {
		Chapter chapter = cr.findChapter(novelName, chapterNo);
		m.addAttribute("chapter",chapter);
		System.out.println("chapter");
		return "chaptertemplate";
	}


	
	

}
