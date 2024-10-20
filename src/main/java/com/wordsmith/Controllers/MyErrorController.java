package com.wordsmith.Controllers;

import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.http.HttpServletRequest;


@Controller
public class MyErrorController implements ErrorController {
	
	@RequestMapping("/error")
	public String handleError(HttpServletRequest request, Model model) {
		 Object status = request.getAttribute(RequestDispatcher.ERROR_STATUS_CODE);
		 Integer statusCode = Integer.valueOf(status.toString());
		 String ErrorMessage ="";
		 String Title ="";
		    
		 if (status != null) {
		       	    
		        if(statusCode == HttpStatus.NOT_FOUND.value()) {
		        	Title ="Page Not Found";
		        	ErrorMessage = "Opps, Looks like The Page You Are Looking For Doesn't Exist!";
		        }
		        else {
		        	Title ="Some Thing Went Wrong";
		        	ErrorMessage = "Opps, Looks like Some Thing Went Wrong!";
		        }
		    }
		 model.addAttribute("title", Title);
		 model.addAttribute("errorMessage", ErrorMessage);
		 model.addAttribute("errorCode",status);
	     return "error";
	}

}
