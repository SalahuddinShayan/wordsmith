<%@ page import="java.net.*, java.util.*" %>
<%
    // Check if sync has already been done this session
    if (session.getAttribute("sharedIdSynced") == null) {

        // 1. Get or create pubcid from cookie
        String pubcid = null;
        jakarta.servlet.http.Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (jakarta.servlet.http.Cookie cookie : cookies) {
                if ("_pubcid".equals(cookie.getName())) {
                    pubcid = cookie.getValue();
                    break;
                }
            }
        }

        if (pubcid == null) {
            pubcid = java.util.UUID.randomUUID().toString();
            jakarta.servlet.http.Cookie newCookie = new jakarta.servlet.http.Cookie("_pubcid", pubcid);
            newCookie.setMaxAge(60 * 60 * 24 * 365); // 1 year
            newCookie.setPath("/");
            response.addCookie(newCookie);
        }

        // 2. Get IP address (use X-Forwarded-For if behind proxy)
        String ip = request.getHeader("X-Forwarded-For");
        if (ip == null || ip.isEmpty()) {
            ip = request.getRemoteAddr();
        }

        // 3. Send sync request to Epsilon
        try {
            String syncUrl = "https://proc.ad.cpe.dotomi.com/cvx/server/sync/fpc?ip=" +
                             java.net.URLEncoder.encode(ip, "UTF-8") + "&pubcid=" +
                             java.net.URLEncoder.encode(pubcid, "UTF-8");
            java.net.URL url = new java.net.URL(syncUrl);
            java.net.HttpURLConnection con = (java.net.HttpURLConnection) url.openConnection();
            con.setRequestMethod("GET");
            int responseCode = con.getResponseCode();
            if (responseCode == 204) {
                session.setAttribute("sharedIdSynced", true); //  Mark sync as done for this session
            } else {
                // Optional: log failure
                System.err.println("SharedID sync failed: response code = " + responseCode);
            }
        } catch (Exception e) {
            e.printStackTrace(); // Optional: log error
        }
    }
%>