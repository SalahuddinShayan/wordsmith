<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8" />
        <meta name="viewport" content="width=device-width, initial-scale=1.0" />
        <title>Eastern WordSmith</title>
        <link rel="shortcut icon" type="image/x-icon" href="images/logo.png">
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css" rel="stylesheet" >
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.3.0/css/all.min.css" >
        <style><%@include file="css/stylesheet.css"%></style>
         <%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>    
		 <%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
</head>
<body>

	<nav class="navbar navbar-light bg-az navbar-expand-md sticky-top" >
            <a href="home" class="navbar-brand" style="vertical-align:center;margin:0px 0px 0px 50px"><img src="images/logo.png" width="40" height="40"  alt=""></a>
            <button class="navbar-toggler" data-bs-toggle="collapse" data-bs-target="#navbar" >
                <span class="navbar-toggler-icon"></span>
            </button>

            
            <div class="navbar-collapse collapse" id="navbar" >
                <ul class="navbar-nav">
                    <li class="nav-item"><a href="home" class="nav-link">Home</a></li>
                    <li class="nav-item"><a href="novels" class="nav-link">All Novels</a></li>
                    <li class="nav-item"><a href="updates" class="nav-link">Updates</a></li>
                    <li class="nav-item"><a href="contactus" class="nav-link">Contact Us</a></li>
                </ul>          
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
           <a href="contactus">Contact Us</a>
          </div>
          <div class="col-1"></div>
         </div>
        </footer>
        
       
        
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/js/bootstrap.bundle.min.js" ></script>
<script type="text/javascript" src="js/script.js"></script>  
</body>
</html>