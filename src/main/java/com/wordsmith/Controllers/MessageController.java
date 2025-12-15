package com.wordsmith.Controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;

import com.wordsmith.Entity.Message;
import com.wordsmith.Repositories.MessageRepository;
import com.wordsmith.Util.EmailMasker;

@Controller
public class MessageController {

    private static final Logger logger = LoggerFactory.getLogger(MessageController.class);

    @Autowired
    private MessageRepository mr;


    // ============================================================
    //  GET — Contact Us Page
    // ============================================================
    @RequestMapping("/contactus")
    public String contactus(Model m) {

        logger.info("Serving Contact Us page");

        m.addAttribute("command", new Message());

        return "contactus";
    }


    // ============================================================
    //  POST — Save message sent from contact form
    // ============================================================
    @RequestMapping("/savemessage")
    public RedirectView savemessage(
            @RequestParam("name") String name,
            @RequestParam("email") String email,
            @RequestParam("subject") String subject,
            @RequestParam("message") String message
    ) {

        String maskedEmail = EmailMasker.mask(email);

        logger.info("Received contact form submission — name={}, emailMasked={}, subject={}",
                name,
                maskedEmail,
                subject);

        try {
            Message mes = new Message();
            long id = mr.Last() + 1;

            mes.setMessageId(id);
            mes.setmName(name);
            mes.setEmail(email);
            mes.setSubject(subject);
            mes.setMessage(message);
            mes.setTimestamp(new java.util.Date());

            mr.save(mes);

            logger.debug("Contact message saved — id={}, emailMasked={}", id, maskedEmail);

        } catch (Exception ex) {
            logger.error("Failed to save contact message — emailMasked={}, error={}",
                    maskedEmail,
                    ex.getMessage(),
                    ex
            );
        }

        return new RedirectView("/sent", true);
    }


    // ============================================================
    //  GET — Sent confirmation page
    // ============================================================
    @RequestMapping("/sent")
    public String sent() {

        logger.debug("Serving sent confirmation page");

        return "sent";
    }


    // ============================================================
    //  DELETE — Delete message (Admin)
    // ============================================================
    @RequestMapping(value="/deletemessage")
    public RedirectView deletemessage(@RequestParam("MessageId") long id) {

        logger.warn("Deleting message — id={}", id);

        mr.deleteById(id);

        return new RedirectView("/messages", true);
    }


    // ============================================================
    //  GET — View all messages (Admin)
    // ============================================================
    @RequestMapping("/messages")
    public String messages(Model m) {

        logger.info("Fetching all messages (admin)");

        m.addAttribute("Messages", mr.findAll());

        logger.debug("Messages loaded for admin panel");

        return "messages";
    }

}
