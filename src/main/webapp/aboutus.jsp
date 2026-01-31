<%@ page contentType="text/html; charset=UTF-8" %>
<%@ page pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>    

<!DOCTYPE html>
<html lang="en">
<head>
<!-- Google tag (gtag.js) -->
<script async src="https://www.googletagmanager.com/gtag/js?id=G-0D3MMVLTED"></script>
<script>
  window.dataLayer = window.dataLayer || [];
  function gtag(){dataLayer.push(arguments);}
  gtag('js', new Date());

  gtag('config', 'G-0D3MMVLTED');
</script>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>About Us - Eastern Wordsmith</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/stylesheet.css">
    <link rel="shortcut icon" type="image/x-icon" href="images/logo2.png">
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css" rel="stylesheet" >
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.3.0/css/all.min.css" >
        
</head>
<body>
    <%@ include file="nav1.jsp" %>

    <div class="about-container">
        <section class="about-header">
            <h1>About Eastern Wordsmith</h1>
            <p>Your go-to platform for high-quality web novel translations.</p>
        </section>

        <section class="about-mission">
            <h2>Our Mission</h2>
            <p>We aim to provide **accurate and high-quality translations** while preserving the essence of the original works.</p>
        </section>

        <section class="about-team">
            <h2>Meet Our Team</h2>
            <p>We are a team of dedicated **translators, editors, and enthusiasts** working to bring amazing stories to a global audience.</p>
        </section>

        <section class="about-how-it-works">
            <h2>How It Works</h2>
            <ul>
                <li>✔️ Users can read free translations.</li>
                <li>🔒 Early access chapters are available via the **paywall system**.</li>
                <li>💬 Readers can **comment and like** chapters.</li>
                <li>🚀 Regular updates ensure a **steady stream of new content**.</li>
            </ul>
        </section>

        <section class="about-contact">
            <h2>Get In Touch</h2>
            <p>Have questions? Contact us at <a href="mailto:contact@easternwordsmith.com">contact@easternwordsmith.com</a> or visit our <a href="/contactus">Contact Us</a> page.</p>
        </section>
    </div>

    <%@ include file="footer1.jsp" %>

</body>
</html>