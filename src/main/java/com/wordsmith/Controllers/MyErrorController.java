package com.wordsmith.Controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.http.HttpServletRequest;

@Controller
public class MyErrorController implements ErrorController {

    private static final Logger logger = LoggerFactory.getLogger(MyErrorController.class);

    @RequestMapping("/error")
    public String handleError(HttpServletRequest request, Model model) {

        Object statusObj = request.getAttribute(RequestDispatcher.ERROR_STATUS_CODE);
        Object uriObj = request.getAttribute(RequestDispatcher.ERROR_REQUEST_URI);
        Object messageObj = request.getAttribute(RequestDispatcher.ERROR_MESSAGE);
        Object exceptionObj = request.getAttribute(RequestDispatcher.ERROR_EXCEPTION);

        Integer statusCode = (statusObj != null) ? Integer.valueOf(statusObj.toString()) : 500;
        String requestUri = (uriObj != null) ? uriObj.toString() : "N/A";
        String errorMessage = (messageObj != null) ? messageObj.toString() : "No message";
        
        // Logging START
        if (statusCode == HttpStatus.NOT_FOUND.value()) {
            logger.warn("⚠️ 404 Not Found — uri={}, message={}", requestUri, errorMessage);
        } else {
            logger.error("❌ Error occurred — status={}, uri={}, message={}", statusCode, requestUri, errorMessage);

            // Log exception stacktrace if available
            if (exceptionObj instanceof Exception ex) {
                logger.error("Exception trace:", ex);
            }
        }
        // Logging END


        // UI Model Setup
        String title;
        String uiMessage;

        if (statusCode == HttpStatus.NOT_FOUND.value()) {
            title = "Page Not Found";
            uiMessage = "Oops, looks like the page you are looking for doesn't exist!";
        } else {
            title = "Something Went Wrong";
            uiMessage = "Oops, looks like something went wrong!";
        }

        model.addAttribute("title", title);
        model.addAttribute("errorMessage", uiMessage);
        model.addAttribute("errorCode", statusCode);

        return "error";
    }
}
