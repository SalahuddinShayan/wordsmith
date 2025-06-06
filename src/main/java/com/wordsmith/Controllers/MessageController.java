package com.wordsmith.Controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;

import com.wordsmith.Entity.Message;
import com.wordsmith.Repositories.MessageRepository;


@Controller
public class MessageController {
	
	@Autowired
	MessageRepository mr;
	
	@RequestMapping("/contactus")
	public String contactus(Model m){
		m.addAttribute("command",new Message());
		return "contactus";
	}
	
	@RequestMapping("/savemessage")
	public RedirectView savemessage(@RequestParam("name") String name, @RequestParam("email") String email, @RequestParam("subject") String subject,
			@RequestParam("message") String message) {
		Message mes = new Message();
		long id =mr.Last() + 1;
		mes.setMessageId(id);
		mes.setmName(name);
		mes.setEmail(email);
		mes.setSubject(subject);
		mes.setMessage(message);
		mes.setTimestamp(new java.util.Date());
		mr.save(mes);
		RedirectView redirectView= new RedirectView("/sent",true);
	    return redirectView;
	}
	
	@RequestMapping("/sent")
	public String sent() {
		return "sent";
	}
	
	
	@RequestMapping(value="/deletemessage")
	public RedirectView  deletemember(@RequestParam("MessageId") long id){
		RedirectView redirectView= new RedirectView("/messages",true);
		mr.deleteById(id);
		return redirectView;
		}
	
	@RequestMapping("/messages")
	public String messaegs(Model m) {
		m.addAttribute("Messages", mr.findAll());
		return "messages";
	}

}
