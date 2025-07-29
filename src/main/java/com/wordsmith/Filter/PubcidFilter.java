package com.wordsmith.Filter;

import jakarta.servlet.*;
import jakarta.servlet.http.*;
import org.springframework.stereotype.Component;
import java.io.IOException;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class PubcidFilter implements Filter {

    private static final String PUBCID_COOKIE_NAME = "_pubcid";
    private static final String SYNCED_SESSION_ATTR = "pubcid_synced";
    private static final String SYNC_URL = "https://pubcid.org/sync?pubcid=";

    // Simple in-memory cache to prevent repeated sync per IP
    private final ConcurrentHashMap<String, Long> ipSyncCache = new ConcurrentHashMap<>();

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        if (!(request instanceof HttpServletRequest req) || !(response instanceof HttpServletResponse res)) {
            chain.doFilter(request, response);
            return;
        }

        String pubcid = getCookieValue(req, PUBCID_COOKIE_NAME);
        if (pubcid == null || pubcid.isEmpty()) {
            pubcid = UUID.randomUUID().toString();
            Cookie cookie = new Cookie(PUBCID_COOKIE_NAME, pubcid);
            cookie.setPath("/");
            cookie.setHttpOnly(false);
            cookie.setMaxAge(60 * 60 * 24 * 365); // 1 year
            res.addCookie(cookie);
        }

        HttpSession session = req.getSession(true);
        boolean alreadySynced = session.getAttribute(SYNCED_SESSION_ATTR) != null;

        String userIp = req.getRemoteAddr();
        boolean ipRecentlySynced = ipSyncCache.containsKey(userIp) && (System.currentTimeMillis() - ipSyncCache.get(userIp)) < (12 * 60 * 60 * 1000); // 12 hrs

        if (!alreadySynced && !ipRecentlySynced) {
            syncPubcid(pubcid); // async or non-blocking recommended in production
            session.setAttribute(SYNCED_SESSION_ATTR, true);
            ipSyncCache.put(userIp, System.currentTimeMillis());
        }

        chain.doFilter(request, response);
    }

    private void syncPubcid(String pubcid) {
        try {
            String url = SYNC_URL + pubcid;
            java.net.HttpURLConnection conn = (java.net.HttpURLConnection) new java.net.URL(url).openConnection();
            conn.setConnectTimeout(2000);
            conn.setReadTimeout(2000);
            conn.setRequestMethod("GET");
            conn.getInputStream().close();
        } catch (Exception e) {
            // Log or silently fail to avoid breaking flow
            System.err.println("PubCID sync failed: " + e.getMessage());
        }
    }

    private String getCookieValue(HttpServletRequest request, String name) {
        if (request.getCookies() == null) return null;
        for (Cookie cookie : request.getCookies()) {
            if (name.equals(cookie.getName())) return cookie.getValue();
        }
        return null;
    }
}
