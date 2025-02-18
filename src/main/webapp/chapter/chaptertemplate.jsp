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
<script async src="https://pagead2.googlesyndication.com/pagead/js/adsbygoogle.js?client=ca-pub-3020770276580291" crossorigin="anonymous"></script>
<meta charset="UTF-8" />
        <meta name="viewport" content="width=device-width, initial-scale=1.0" />
        <meta name="google-adsense-account" content="ca-pub-3020770276580291">
        <meta name="keywords" content="${chapter.keywords}">
        <title>${chapter.novelName}-Chapter ${chapter.chapterNo}</title>
        <link rel="icon" type="image/x-icon" href="../images/logo2.png">
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css" rel="stylesheet" >
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.3.0/css/all.min.css" >
        <style><%@include file="../css/stylesheet.css"%></style>
         <%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>    
		 <%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%> 
</head>
<body onload="ChapStyle()">

<nav class="navbar navbar-light bg-az navbar-expand-md" >
            <a href="https://easternwordsmith.com/" class="navbar-brand" style="vertical-align:center;margin:0px 0px 0px 50px"><img src="../images/logo2.png" width="40" height="40"  alt=""></a>
            <button class="navbar-toggler" data-bs-toggle="collapse" data-bs-target="#navbar" >
                <span class="navbar-toggler-icon"></span>
            </button>

            
            <div class="navbar-collapse collapse pe-2" id="navbar" >
                <ul class="navbar-nav">
                    <li class="nav-item"><a href="https://easternwordsmith.com/" class="nav-link">Home</a></li>
                    <li class="nav-item"><a href="../novels" class="nav-link">All Novels</a></li>
                    <li class="nav-item"><a href="../updates" class="nav-link">Updates</a></li>
                    <li class="nav-item"><a href="../contactus" class="nav-link">Contact Us</a></li>
                </ul>
                
                <p class="me-auto"><script type='text/javascript' src='https://storage.ko-fi.com/cdn/widget/Widget_2.js'></script><script type='text/javascript'>kofiwidget2.init('Support us on Ko-fi', '#000000', 'Y8Y2163B9B');kofiwidget2.draw();</script></p>         
            </div>
            
        </nav>
        
        <div class= "center">
        <h3>${chapter.novelName}-Chapter ${chapter.chapterNo}</h3>
        <h3>${chapter.title}</h3>
        </div>
        
         <div  Style = "margin-top: 30px;" class = "row">
         <div class="col-1 col-lg-2 py-3"></div>
         <div class="col-10 col-lg-8 py-3 stm"><a href ="../home">Eastern Word Smith</a><a>/</a><a  href="../novel/${chapter.novelName}">${chapter.novelName}</a><a>/</a><a  href = "${chapter.chapterId}">Chapter ${chapter.chapterNo}</a></div>
         <div class="col-1 col-lg-2 py-3"></div>
         </div>
         
        <div  Style = "margin-top: 30px;" class = "row">
        	<div class="col-1 col-lg-2 py-3 "></div>
        	<div class="col-10 col-lg-8 bd-az">
        	<h5>Message:</h5> 
        	<p>Hello guys,<br>
        	   We are transitioning to a new ad provider to improve revenue and control. During this process, you may encounter some issues. Please inform us immediately if you experience any problems.   
        	</p>
        	<p>As always, please continue to support us. You can show your support in the following ways:
        	</p> 
        	<ul> 
        	<li><strong>Bookmark and Rate Your Favorite Novel:</strong> Head over to Novel Updates, bookmark your favorite stories, rate them, and leave a review. This simple action boosts our visibility and helps us reach more readers like you.</li> 
        	<li><strong>Engage with Our Chapters:</strong> After reading a chapter, please leave a comment or reaction. Your feedback not only lets us know you’re enjoying the story, but it also keeps us motivated.</li> 
        	<li><strong>Support Us on Ko-fi:</strong> As a small operation, we rely on your support to cover our costs. If you can, please consider donating on Ko-fi. Even a small contribution makes a huge difference!</li> 
        	<li><strong>Help Us Improve the Website:</strong> Since we’re still in the early stages, we’d love your feedback on the website’s navigation, design, and user experience. What do you like? What could be better? Are there any features you'd like to see? Share your thoughts with us via the Contact Us form. Your insights are incredibly valuable to us!</li>
        	<li><strong>Do not use Ad-Blocker:</strong> Please know that ads are our only source of revenue right now.</li> 
        	</ul> 
        	
        	
        	</div>
        	<div class="col-1 col-lg-2 py-3 "></div>
        </div>
        
         <div  Style = "margin-top: 30px;" class = "row">
         <div class="col-1 col-lg-2 d-none d-md-block py-3 "></div>
         <div class="col-12 col-lg-8 py-3">
         <div class ="center">
         <a id="DMS" onclick="lightSwitch()" ><img class ="icon" alt="" src="../images/moon.png"></a>
         <a id="Minus" onclick="TextSizeDecrease()" ><img class ="icon" alt="" src="../images/minus.png"></a>
         <a id="Default" onclick="TextSizeDefault()" ><img class ="icon" alt="" src="../images/alpha.png"></a>
         <a id="Plus" onclick="TextSizeIncrease()" ><img class ="icon" alt="" src="../images/plus-sign.png"></a>
         <a href="../chapter-previous/${chapter.chapterId}"><img class ="icon" alt="" src="../images/arrow-left.png"></a>
         <a href="../novel/${chapter.novelName}"><img class ="icon" alt="" src="../images/home.png"></a>
         <a href="../chapter-next/${chapter.chapterId}"><img class ="icon" alt="" src="../images/right-arrow.png"></a>
         </div>
         <section class ="wcontainer" id="CS">
         ${chapter.content}
         </section>
         <div class ="center">
         <a id="DMS" onclick="lightSwitch()" ><img class ="icon" alt="" src="../images/moon.png"></a>
         <a id="Minus" onclick="TextSizeDecrease()" ><img class ="icon" alt="" src="../images/minus.png"></a>
         <a id="Default" onclick="TextSizeDefault()" ><img class ="icon" alt="" src="../images/alpha.png"></a>
         <a id="Plus" onclick="TextSizeIncrease()" ><img class ="icon" alt="" src="../images/plus-sign.png"></a>
         <a href="../chapter-previous/${chapter.chapterId}"><img class ="icon" alt="" src="../images/arrow-left.png"></a>
         <a href="../novel/${chapter.novelName}"><img class ="icon" alt="" src="../images/home.png"></a>
         <a href="../chapter-next/${chapter.chapterId}"><img class ="icon" alt="" src="../images/right-arrow.png"></a><br><br><br>
         <p><script type='text/javascript' src='https://storage.ko-fi.com/cdn/widget/Widget_2.js'></script><script type='text/javascript'>kofiwidget2.init('Support Us on Ko-fi', '#1F8FFF', 'Y8Y2163B9B');kofiwidget2.draw();</script></p> 
         </div>
         </div>
         <div class="col-1 col-lg-2 d-none d-md-block py-3 "></div>
         </div>
        
        <div  Style = "margin-top: 30px;" class = "row">
         <div class="col-1 col-lg-3 py-3 "></div>
         <div class="col-10 col-lg-6 py-3 ">
         <div id="disqus_thread"></div>
         </div>
         <div class="col-1 col-lg-3 py-3 "></div>
         </div>
        
        <footer class ="stm">
         <div  Style = "margin-top: 30px;" class = "row">
          <div class="col-1"></div>
          <div class="col-10 center bdt">
           <a href="../imageslogo">Images&Logo</a>
           <a>|</a>
           <a href="../novels">Novels</a>
           <a>|</a>
           <a href="../privacypolicy">Privacy Policy</a>
           <a>|</a>
           <a href="../contactus">Contact Us</a>
          </div>
          <div class="col-1"></div>
         </div>
        </footer>
       
<script>
    /**
    *  RECOMMENDED CONFIGURATION VARIABLES: EDIT AND UNCOMMENT THE SECTION BELOW TO INSERT DYNAMIC VALUES FROM YOUR PLATFORM OR CMS.
    *  LEARN WHY DEFINING THESE VARIABLES IS IMPORTANT: https://disqus.com/admin/universalcode/#configuration-variables    */
    
    var disqus_config = function () {
    this.page.url = "https://easternwordsmith.com/chapter/${chapter.chapterId}";  // Replace PAGE_URL with your page's canonical URL variable
    this.page.identifier = "Chapter_Id:${chapter.chapterId}"; // Replace PAGE_IDENTIFIER with your page's unique identifier variable
    };
    
    (function() { // DON'T EDIT BELOW THIS LINE
    var d = document, s = d.createElement('script');
    s.src = 'https://easternwordsmith.disqus.com/embed.js';
    s.setAttribute('data-timestamp', +new Date());
    (d.head || d.body).appendChild(s);
    })();
</script>
<noscript>Please enable JavaScript to view the <a href="https://disqus.com/?ref_noscript">comments powered by Disqus.</a></noscript>        
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/js/bootstrap.bundle.min.js" ></script>
<script type="text/javascript" src="../js/script.js"></script>  
</body>
</html>