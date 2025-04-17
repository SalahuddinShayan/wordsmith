package com.wordsmith.Controllers;

import com.wordsmith.Entity.Chapter;
import com.wordsmith.Repositories.ChapterRepository;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.PrintWriter;
import java.time.format.DateTimeFormatter;
import java.util.List;

@RestController
public class RssController {

    @Autowired
    private ChapterRepository cr;

    @GetMapping(value = "/rss.xml", produces = "application/rss+xml")
    public void generateRssFeed(HttpServletResponse response) throws Exception {
        List<Chapter> chapters = cr.Latest20(); // Limit to 10 or 20

        PrintWriter writer = response.getWriter();
        writer.println("<?xml version=\"1.0\" encoding=\"UTF-8\" ?>");
        writer.println("<rss version=\"2.0\">");
        writer.println("<channel>");
        writer.println("<title>Eastern Wordsmith - Chapter Updates</title>");
        writer.println("<link>https://easternwordsmith.com</link>");
        writer.println("<description>Latest chapters posted on Eastern Wordsmith</description>");
        writer.println("<language>en-us</language>");
        writer.println("<atom:link href=\"https://easternwordsmith.com/rss.xml\" rel=\"self\" type=\"application/rss+xml\" xmlns:atom=\"http://www.w3.org/2005/Atom\"/>");

        DateTimeFormatter formatter = DateTimeFormatter.RFC_1123_DATE_TIME;

        for (Chapter chapter : chapters) {
            writer.println("<item>");
            writer.println("<title><![CDATA[" + chapter.getNovelName() + " - " + chapter.getChapterNo() + "]]></title>");
            writer.println("<link>https://easternwordsmith.com/chapter/" + chapter.getChapterId() + "</link>");
            if (chapter.getReleasedOn() != null) {
            	writer.println("<pubDate>" + formatter.format(chapter.getReleasedOn()) + "</pubDate>");
            }
            else {
            	writer.println("<pubDate>" + formatter.format(chapter.getPostedOn()) + "</pubDate>");
            }
            writer.println("<guid>https://easternwordsmith.com/chapter/" + chapter.getChapterId() + "</guid>");
            writer.println("</item>");
        }

        writer.println("</channel>");
        writer.println("</rss>");
        writer.flush();
    }

}
