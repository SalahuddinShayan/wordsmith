<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<!-- Google tag (gtag.js) -->
<script async src="https://www.googletagmanager.com/gtag/js?id=G-0D3MMVLTED"></script>
<script>
  window.dataLayer = window.dataLayer || [];
  function gtag(){dataLayer.push(arguments);}
  gtag('js', new Date());

  gtag('config', 'G-0D3MMVLTED');
</script>
<meta charset="UTF-8" />
        <meta name="viewport" content="width=device-width, initial-scale=1.0" />
        <meta name="google-adsense-account" content="ca-pub-3020770276580291">
        <title>Eastern WordSmith</title>
        <link rel="shortcut icon" type="image/x-icon" href="images/logo2.png">
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css" rel="stylesheet" >
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.3.0/css/all.min.css" >
        <style><%@include file="css/stylesheet.css"%></style>
         <%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>    
		 <%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
		 <script data-grow-initializer="">!(function(){window.growMe||((window.growMe=function(e){window.growMe._.push(e);}),(window.growMe._=[]));var e=document.createElement("script");(e.type="text/javascript"),(e.src="https://faves.grow.me/main.js"),(e.defer=!0),e.setAttribute("data-grow-faves-site-id","U2l0ZTpjMzAxMTE4Mi1jZDdlLTRiYTMtOTkxNy1lMDZhMThiOGFiMjE=");var t=document.getElementsByTagName("script")[0];t.parentNode.insertBefore(e,t);})();</script>
</head>
<body>

	<nav class="navbar navbar-light bg-az navbar-expand-md sticky-top" >
            <a href="https://easternwordsmith.com/" class="navbar-brand" style="vertical-align:center;margin:0px 0px 0px 50px"><img src="images/logo2.png" width="40" height="40"  alt=""></a>
            <button class="navbar-toggler" data-bs-toggle="collapse" data-bs-target="#navbar" >
                <span class="navbar-toggler-icon"></span>
            </button>

            
            <div class="navbar-collapse collapse  pe-2" id="navbar" >
                <ul class="navbar-nav">
                    <li class="nav-item"><a href="https://easternwordsmith.com/" class="nav-link">Home</a></li>
                    <li class="nav-item"><a href="../novels" class="nav-link">All Novels</a></li>
                    <li class="nav-item"><a href="../updates" class="nav-link">Updates</a></li>
                    <li class="nav-item"><a href="../contactus" class="nav-link">Contact Us</a></li>
                </ul>
                <p class="me-auto"><script type='text/javascript' src='https://storage.ko-fi.com/cdn/widget/Widget_2.js'></script><script type='text/javascript'>kofiwidget2.init('Support us on Ko-fi', '#000000', 'Y8Y2163B9B');kofiwidget2.draw();</script></p>          
            </div>
        </nav>
        
        
        <!--Section: Contact v.2-->
<section  id="contact" class="mb-4" style="width:90%; padding: 30px; align-content: center; margin-left: 5%; margin-right: 5%;" >

  <!--Section heading-->
  <h2 class="h1-responsive font-weight-bold text-center my-4">Contact us</h2>
  <!--Section description-->
  <p class="text-center w-responsive mx-auto mb-5">We welcome your feedback. If you've identified a mistake or an issue, or if you have suggestions for improvement, we want to hear from you.
			<br>Don't hesitate to share your thoughts--your input matters!  </p>

  <div class="row">
	  <div class="col-2 d-none d-md-block"></div>
      <!--Grid column-->
      <div class="col-md-8 mb-md-0 mb-5">
          <form id="contact-form" name="contact-form" action="savemessage" method="POST">

              <!--Grid row-->
              <div class="row">

                  <!--Grid column-->
                  <div class="col-md-6" style="margin-bottom: 20px;">
                      <div class="md-form mb-0">
                        <label for="name" class="">Your name</label>  
                        <input type="text" id="name" name="name" class="form-control">
                      </div>
                  </div>
                  <!--Grid column-->

                  <!--Grid column-->
                  <div class="col-md-6" style="margin-bottom: 20px;">
                      <div class="md-form mb-0">
                        <label for="email" class="">Your email</label>
                        <input type="email" pattern="[a-z0-9._%+-]+@[a-z0-9.-]+\.[a-z]{2,63}$" required id="email" name="email" class="form-control">
                      </div>
                  </div>
                  <!--Grid column-->

              </div>
              <!--Grid row-->

              <!--Grid row-->
              <div class="row" style="margin-bottom: 20px;">
                  <div class="col-md-12">
                      <div class="md-form mb-0">
                        <label for="subject" class="">Subject</label>  
                        <input type="text" id="subject" name="subject" class="form-control">
                      </div>
                  </div>
              </div>
              <!--Grid row-->

              <!--Grid row-->
              <div class="row" style="margin-bottom: 20px;">

                  <!--Grid column-->
                  <div class="col-md-12">

                      <div class="md-form">
                        <label for="message">Your message</label>  
                        <textarea type="text" id="message" name="message" rows="2" class="form-control md-textarea"></textarea>
                      </div>

                  </div>
              </div>
              <!--Grid row-->
              <div class="text-center text-md-left">
				<input type="submit" value="Send" class="btn btn-primary" />
			  </div>
          </form>
      </div>
      <!--Grid column-->

      <!--Grid column-->
      <div class="col-2 d-none d-md-block"></div>
      </div>
    </section>
        
        

<footer class ="stm">
         <div  Style = "margin-top: 30px;" class = "row">
          <div class="col-1"></div>
          <div class="col-10 center bdt">
           <a href="imageslogo">Images&Logo</a>
           <a>|</a>
           <a href="novels">Novels</a>
           <a>|</a>
           <a href="privacypolicy">Privacy Policy</a>
           <a>|</a>
           <a href="contactus">Contact Us</a>
          </div>
          <div class="col-1"></div>
         </div>
        </footer>
        
       
        
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/js/bootstrap.bundle.min.js" ></script>
<script type="text/javascript" src="js/script.js"></script>  
</body>
</html>